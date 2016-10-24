package com.webhybird.daobase;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangzhongfu on 2015/4/24.
 */
public class SqlUtil {

    private static Map<String,Field[]> classFields = new HashMap<String,Field[]>();

    private static Map<String,String> insertSqls = new HashMap<String,String>();

    private static Map<String,String> updateSqls = new HashMap<String,String>();

    private static Map<String,String> deleteSqls = new HashMap<String,String>();

    private static Map<String,String> findByIdSql = new HashMap<String, String>();

    private static Map<String,String> queryFields = new HashMap<String, String>();

    private static final String INSERT_SQL = "INSERT INTO table() VALUES";

    private static final String UPDATE_BY_ID_SQL = "UPDATE table T_  SET () WHERE T_.? = ?";

    private static final String DELETE_BY_ID_SQL = "DELETE FROM table T_ WHERE T_.? = ? ";

    private static final String QUERY_BY_ID_SQL = "SELECT * FROM TABLE T_ WHERE T_.? = ?";

    public static final String TABLE_ALIAS = "T_";

    /**
     *
     * @param type
     * @return
     */
    public  static String getQueryByIdSql(Class type){
        if(findByIdSql.get(type.getName()) != null){
            return findByIdSql.get(type.getName());
        }else{
            Field[] fields = getFields(type);
            String s = QUERY_BY_ID_SQL;
            s = s.replace("TABLE",getTableName(type)).replaceFirst("\\?", getIdColumnName(fields));
            findByIdSql.put(type.getName(),s);
            return s;
        }
    }

        /**
         * 获取数据更新的Sql
         * @param o
         * @return
         */
        public static String getUpdateByIdSql(Object o){
            Class type = o.getClass();
            Field[] fields = getFields(type);
            String s = UPDATE_BY_ID_SQL;
            s = s.replace("table", getTableName(type))
                    .replaceFirst("\\?", getIdColumnName(fields))
                    .replace("()",getUpdateValues(fields,o));
            updateSqls.put(type.getName(),s);
            return s;
        }

    /**
     * 获取插入数据sql
     * @param o
     * @return
     */
    public static String getInsertSql(Object o){
        Class type = o.getClass();
        Field[] fields = getFields(type);
        String string = INSERT_SQL;
        string = string.replace("table",getTableName(type)).replace("()",columInfo(fields,o)) + values(getColumnCount(objFieldValueMapping(o).size()));
        insertSqls.put(type.getName(),string);
        return string;
    }

    /**
     *
     * @param type
     * @return
     */
    public static String getDeleteByIdSql(Class type){
        if(deleteSqls.get(type.getName()) != null){
            return deleteSqls.get(type.getName());
        }else{
            Field[] fields = getFields(type);
            String s = DELETE_BY_ID_SQL;
            s = s.replace("table", getTableName(type)).replaceFirst("\\?", getIdColumnName(fields));
            deleteSqls.put(type.getName(),s);
            return s;
        }
    }

    /**
     *
     * @param o
     * @return
     */
    private static Map<String,Object> objFieldValueMapping(Object o){
        Map<String,Object> stringObjectMap = new HashMap<String, Object>();
        Class c = o.getClass();
        Field[] field = c.getDeclaredFields();
        for (Field f : field) {
            if(f.isAnnotationPresent(ColAlias.class)){
                Object v = invokeMethod(o, f.getName(), null);
                if(v != null){
                    if(f.getGenericType().toString().equals("class java.lang.String")){
                        if(!"".equals(v)){
                            stringObjectMap.put(f.getName(),v);
                        }
                    }else{
                        stringObjectMap.put(f.getName(),v);
                    }
                }
            }
            /*Object v = invokeMethod(o, f.getName(), null);
            System.out.println(f.getName() + "\t" + v + "\t" + f.getType());*/
        }
        return stringObjectMap;
    }

    /**
     *
     * @param t
     * @return
     */
    public static String getQueryProSql(Class t){
        String s1 = queryFields.get(t.getName());
        if(s1 != null && !"".equals(s1)){
            return s1;
        }
        Field[] fields = getFields(t);
        StringBuilder s = new StringBuilder("");
        for(Field field : fields){
            if(field.isAnnotationPresent(ColAlias.class)){
                s.append(TABLE_ALIAS + "."  + getColumnName(field) + ",");
            }
        }
         s1 = s.substring(0,s.length() - 1);
        return s1;
    }

    /**
     * 获取 形如 t.aaa = ? 的字符串
     * @param fields
     * @return
     */
    private static String getUpdateValues(Field[] fields,Object o){
        StringBuilder s = new StringBuilder("");
        Map<String,Object> map = objFieldValueMapping(o);
        for(Field field : fields){
            if(!field.isAnnotationPresent(Id.class) && field.isAnnotationPresent(ColAlias.class)){
                String columnName = getColumnName(field);
                for(String key : map.keySet()){
                    if(columnName.equals(key) || field.getName() .equals(key)){
                        s.append(TABLE_ALIAS + "."  + columnName + "=? ,");
                    }
                }
            }
        }
        return s.substring(0,s.length() - 1);
    }

    /**
     *
     * @param field
     * @return
     */
    private static String columInfo(Field[] field,Object o){
        StringBuilder builder = new StringBuilder("");
        Map<String,Object> map = objFieldValueMapping(o);
        for (Field f : field) {
            if(f.isAnnotationPresent(ColAlias.class)){
                String columnName = getColumnName(f);
                for(String key : map.keySet()){
                    if(columnName.equals(key) || f.getName().equals(key)){
                        builder.append("".equals(builder.toString())? "(" + columnName + "," : columnName + ",");
                    }
                }
            }
        }
        String coolums = builder.toString();
        return coolums.substring(0,coolums.length() - 1) + ")" ;
    }

    /**
     * 获取ID字段
     * @param fields
     * @return
     */
    private static String getIdColumnName(Field[] fields){
        for(Field field : fields){
            if(field.isAnnotationPresent(Id.class)){
                ColAlias column = field.getAnnotation(ColAlias.class);
                return column != null && !"".equals(column.value()) ? column.value() : field.getName();
            }
        }
        return null;
    }

    /**
     *
     * @param type
     * @return
     */
    private static Field[] getFields(Class type){
        if(classFields.get(type.getName()) != null){
            return classFields.get(type.getName());
        }
        classFields.put(type.getName(),type.getDeclaredFields());
        return type.getDeclaredFields();
    }


    /**
     * 获取类映射的table名称
     * @param type
     * @return
     */
    private static String getTableName(final Class type){
        String tableName = "";
        if(type.isAnnotationPresent(Entity.class)){
            if(type.isAnnotationPresent(Table.class)){

                Table table = (Table) type.getAnnotation(Table.class);
                tableName = table.value() != null && !"".equals(table.value()) ? table.value() : type.getSimpleName();

            }else{
                tableName = type.getSimpleName();
            }
        }
        return tableName;
    }

    /**
     * 获取列名称
     * @param field
     * @return
     */
    private static String getColumnName(Field field){
        ColAlias column = field.getAnnotation(ColAlias.class);
        return column.value() != null && !"".equals(column.value()) ? column.value() : field.getName();
    }

    /**
     *
     * @param fieldLength
     * @return
     */
    private static String values(int fieldLength){
        StringBuilder builder = new StringBuilder("");
        for(int i = 0 ; i < fieldLength ; i++){
            builder.append( i == 0 ? "(?," : "?,");
        }
        String s = builder.toString();
        return s.substring(0,s.length() - 1) + ")";
    }

    /**
     * 获取值数组,id的值在最后
     * @param o
     * @return
     */
    public static Object[] valuesIdLast(Object o){
        Map<String,Object> map = objFieldValueMapping(o);
        Field[] fields = o.getClass().getDeclaredFields();
        Object[] objects = new Object[map.size()];
        int index = 0;
        for(int i = 0 ; i < fields.length ; i++){
            Field field = fields[i];
            if(field.isAnnotationPresent(ColAlias.class) && !field.isAnnotationPresent(Id.class)){
                for(String key : map.keySet()){
                    if(key.equals(field.getName())){
                        Object v = map.get(key);
                        if(v != null){
                            objects[index] = v;
                            index ++;
                        }
                    }
                }
               // objects[i] = invokeMethod(o, field.getName(), null);
            }
        }
        for(int i = 0 ; i < fields.length ; i++){
            Field field = fields[i];
            if(field.isAnnotationPresent(Id.class)){
                objects[index] = invokeMethod(o, field.getName(), null);
            }
        }
        return objects;
    }

    /**
     * 获取对象值数组
     * @param o
     * @return
     */
    public static Object[] values(Object o){
        Map<String,Object> map = objFieldValueMapping(o);
        Field[] fields = o.getClass().getDeclaredFields();
        Object[] objects = new Object[map.size()];
        int index = 0;
        for(int i = 0 ; i < fields.length ; i++){
            Field field = fields[i];
            if(field.isAnnotationPresent(ColAlias.class)){
                for(String key : map.keySet()){
                    if(key.equals(field.getName())){
                        Object v = map.get(key);
                        if(v != null){
                            objects[index] = v;
                            index ++;
                        }
                    }
                }
                //objects[i] = invokeMethod(o, field.getName(), null);
            }
        }
        return objects;
    }

    /**
     * 获取id的值
     * @param o
     * @return
     */
    public static Object[] valuesId(Object o){
        Field[] fields = o.getClass().getDeclaredFields();
        Object[] objects = new Object[1];
        for(int i = 0 ; i < fields.length ; i++){
            Field field = fields[i];
            if(field.isAnnotationPresent(Id.class)){
                objects[1] = invokeMethod(o, field.getName(), null);
                return objects;
            }
        }
        return null;
    }

    /**
     * 获得一个对象各个属性的字节流
     */
    @SuppressWarnings("unchecked")
    public static void getProperty(Object entityName) throws Exception {
        Class c = entityName.getClass();
        Field[] field = c.getDeclaredFields();
        for (Field f : field) {
            /*if(f.isAnnotationPresent(ColAlias.class)){

            }*/
            Object v = invokeMethod(entityName, f.getName(), null);
            System.out.println(f.getName() + "\t" + v + "\t" + f.getType());
        }
    }

    /**
     *
     * @param owner
     * @param methodName
     * @param args
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    private static Object invokeMethod(Object owner, String methodName,
                                       Object[] args) {
        Class ownerClass = owner.getClass();
        methodName = methodName.substring(0, 1).toUpperCase()
                + methodName.substring(1);
        Method method = null;
        try {
            method = ownerClass.getMethod("get" + methodName);
        } catch (SecurityException e) {
        } catch (NoSuchMethodException e) {
            return " can't find 'get" + methodName + "' method";
        }
        try {
            return method.invoke(owner);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取列数
     * @return
     */
    private static int getColumnCount(int size){
        int i = 0;
        for(int t = 0 ; t < size ; t++){
            i ++;
        }
        return i;
    }

}
