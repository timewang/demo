package com.webhybird.framework.dataquery;

import com.webhybird.framework.util.QueryUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by wangzhongfu on 2015/5/15.
 */
public class QueryParameter implements Serializable {

    private final String OPERATOR_SPLIT = "_";
    private final String DATE_TYPE = "DATE";
    private final String NUMBER_TYPE = "NUMBER";
    public static final String HQL = "hql";
    public static final String VALUES = "values";
    /**
     * 参数名 参数值
     */
    private Map<String,Object> parameters;
    /**
     * 查询类型
     */
    private Map<String,String> type;

    public Map<String, String> getType() {
        return type;
    }

    public void setType(Map<String, String> type) {
        this.type = type;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    /**
     * 生成条件查询 hql 条件值
     * @param pageable
     * @return
     */
    public Map<String,Object> getSerachCondition(Pageable pageable){
        Map<String,Object> stringObjectMap = new HashMap<>();
        StringBuilder stringBuilder = new StringBuilder();
        List<Object> objectList = new ArrayList<>();

        if(this.parameters != null && this.parameters.size() > 0) {
            for (Map.Entry<String, Object> entry : parameters.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                String queryOperator = this.getQueryOperator(key);
                if(value != null && !StringUtils.isEmpty(value)){
                    this.add(stringBuilder,objectList,this.getQueryNamed(key),value,queryOperator);
                }
            }
        }
        this.orders(stringBuilder,pageable);
        stringObjectMap.put(HQL,stringBuilder.toString());
        stringObjectMap.put(VALUES,objectList);
        return stringObjectMap;
    }

    /**
     * 获取查询的属性名
     * @param key
     * @return
     */
    private String getQueryNamed(String key){
        return key.split(this.OPERATOR_SPLIT)[0];
    }

    /**
     * 排序字段
     * @param stringBuilder
     * @param pageable
     */
    private void orders(StringBuilder stringBuilder,Pageable pageable){
        Sort sort = pageable.getSort();
        if(sort != null){
            int i = 0;
            for(org.springframework.data.domain.Sort.Order order : sort){
                // log.info("ORDER_PROPERTY-------->" + order.getProperty());
                // log.info("ORDER_TYPE-------->" + order.getDirection());
                if(i == 0){
                    stringBuilder.append(" order by " + order.getProperty() + " " + order.getDirection().name() + ",");
                }else{
                    stringBuilder.append(" , " + order.getProperty() + " " + order.getDirection().name());
                }
                i = 1;
            }
        }
    }

    /**
     *
     * @param stringBuilder
     * @param objectList
     * @param key
     * @param value
     * @param queryOperator
     */
    private void add(StringBuilder stringBuilder,List<Object> objectList,String key,Object value,String queryOperator){
        switch (queryOperator.toUpperCase()) {
            case "EQ":
                stringBuilder.append( " and " +  key + " = ? ");
                this.addValue(objectList,key,value);
                break;
            case "NE":
                stringBuilder.append( " and " +  key + " != ? ");
                this.addValue(objectList, key, value);
                break;
            case "LIKE":
                stringBuilder.append( " and " +  key + " like ? ");
                objectList.add(QueryUtils.getSearchString(value.toString()));
                break;
            case "LT":
                stringBuilder.append( " and " +  key + " > ? ");
                this.addValue(objectList, key, value);
                break;
            case "GT":
                stringBuilder.append( " and " +  key + " < ? ");
                this.addValue(objectList, key, value);
                break;
            case "LTE":
                stringBuilder.append( " and " +  key + " >= ? ");
                this.addValue(objectList, key, value);
                break;
            case "GTE":
                stringBuilder.append( " and " +  key + " <= ? ");
                this.addValue(objectList, key, value);
                break;
            default:
                stringBuilder.append( " and " +  key + " = ? ");
                this.addValue(objectList,key,value);
                break;
        }
    }

    /**
     *
     * @param objectList
     * @param key
     * @param value
     */
    private void addValue(List<Object> objectList,String key,Object value){
        String dateType = this.isDateType(key);
        if(!StringUtils.isEmpty(dateType)){
            objectList.add(this.getDate(dateType,value.toString()));
        }else if(this.idNumber(key)){
            objectList.add(new BigDecimal((String) value));
        } else{
            objectList.add(value);
        }
    }

    /**
     *获取时间
     * @param dateType
     * @param datevalue
     * @return
     */
    private Date getDate(String dateType,String datevalue){
        switch (dateType.toUpperCase()) {
            case "DATE_YYYY":
                return this.getDateByFormate(DateType.DATE_YYYY,datevalue);
            case "DATE_YYYY_MM":
                return this.getDateByFormate(DateType.DATE_YYYY_MM,datevalue);
            case "DATE_YYYY_MM_DD":
                return this.getDateByFormate(DateType.DATE_YYYY_MM_DD,datevalue);
            case "DATETIME_HH":
                return this.getDateByFormate(DateType.DATETIME_HH,datevalue);
            case "DATETIME_HH_MM":
                return this.getDateByFormate(DateType.DATETIME_HH_MM,datevalue);
            case "DATETIME_HH_MM_SS":
                return this.getDateByFormate(DateType.DATETIME_HH_MM_SS,datevalue);
            default:
                return this.getDateByFormate(DateType.DATETIME_HH_MM_SS,datevalue);
        }
    }

    /**
     *根据格式化类型 获得时间
     * @param dateType
     * @param datevalue
     * @return
     */
    private Date getDateByFormate(DateType dateType,String datevalue){
        SimpleDateFormat df = new SimpleDateFormat(dateType.getDateformat());
        try {
            return df.parse(datevalue);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param key
     * @return
     */
    private boolean idNumber(String key){
        if(this.type != null && this.type.size() > 0){
            String dataType = this.type.get(key);
            if(!StringUtils.isEmpty(dataType) && dataType.equals(this.NUMBER_TYPE)){
                return true;
            }
        }
        return false;
    }

    /**
     * 判断字段是否是日期类型
     * @param key
     * @return
     */
    private String isDateType(String key){
        if(this.type != null && this.type.size() > 0){
            String dataType = this.type.get(key);
            if(!StringUtils.isEmpty(dataType) && dataType.contains(this.DATE_TYPE)){
                return dataType;
            }
        }
        return "";
    }

    /**
     * 获取查询逻辑 默认为 EQ 既 等于
     * @param key
     * @return
     */
    private String getQueryOperator(String key){
        String[] strings = key.split(this.OPERATOR_SPLIT);
        if(strings.length != 2){
            return Operator.EQ.name();
        }
        return strings[1];
    }

}
