package com.webhybird.framework.dataquery;

import com.webhybird.framework.util.QueryUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Spring 动态查询接口的实现
 * Created by wangzhongfu on 2015/5/15.
 */
public class SpecificationImpl<T> implements Specification<T> {

    private final String OPERATOR_SPLIT = "_";
    private final String DATE_TYPE = "DATE";
    private final String NUMBER_TYPE = "NUMBER";
    /**
     *
     */
    private Map<String,Object> serachCondition;
    /**
     * 日期类型需要制定格式的
     * 参数
     */
    private Map<String,String> type;
    /**
     * 默认构造器
     */
    public SpecificationImpl() {
    }

    /**
     * 根据Map 构造查询条件
     * @param stringObjectMap
     */
    public SpecificationImpl(Map<String,Object> stringObjectMap) {
        this.serachCondition = stringObjectMap;
    }

    /**
     * 传类型
     * @param serachCondition
     * @param type
     */
    public SpecificationImpl(Map<String, Object> serachCondition, Map<String, String> type) {
        this.serachCondition = serachCondition;
        this.type = type;
    }

    /**
     * Creates a WHERE clause for a query of the referenced entity in form of a {@link Predicate} for the given
     * {@link Root} and {@link CriteriaQuery}.
     *
     * @param root
     * @param query
     * @param cb
     * @return a {@link Predicate}, must not be {@literal null}.
     */
    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        //所有条件用and链接
        return cb.and(this.buildPredicate(root, cb));
    }

    /**
     *构建查询条件
     * @param root
     * @param cb
     * @return
     */
    private Predicate[] buildPredicate(Root<T> root, CriteriaBuilder cb){
        List<Predicate> pageableList = new ArrayList<>();
        if(this.serachCondition != null && this.serachCondition.size() > 0){
            for (Map.Entry<String, Object> entry : this.serachCondition.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                String queryOperator = this.getQueryOperator(key);
                if(value != null && !StringUtils.isEmpty(value)){
                    this.add(queryOperator,pageableList,root,cb,value,this.getQueryNamed(key));
                }
            }
        }
        return pageableList.toArray(new Predicate[pageableList.size()]);
    }

    /**
     *
     * @param queryOperator
     * @param pageableList
     * @param root
     * @param cb
     * @param value
     * @param key
     */
    private void add(String queryOperator,List<Predicate> pageableList,Root<T> root, CriteriaBuilder cb,Object value,String key){
        String dateType = this.isDateType(key);
        switch (queryOperator.toUpperCase()) {
          /*  case "EQ":
                if(!StringUtils.isEmpty(dateType)){
                    pageableList.add(cb.equal(cb.lower(root.<String>get(key)),this.getDate(dateType, value.toString())));
                }else if(this.idNumber(key)){
                    pageableList.add(cb.equal(cb.lower(root.<String>get(key)),new BigDecimal((String) value)));
                } else{
                    pageableList.add(cb.equal(cb.lower(root.<String>get(key)), value));
                }
                break;
            case "NE":
                if(!StringUtils.isEmpty(dateType)){
                    pageableList.add(cb.notEqual(cb.lower(root.<String>get(key)), this.getDate(dateType, value.toString())));
                }else if(this.idNumber(key)){
                    pageableList.add(cb.notEqual(cb.lower(root.<String>get(key)), new BigDecimal((String) value)));
                }else{
                    pageableList.add(cb.notEqual(cb.lower(root.<String>get(key)), value));
                }
                break;
            case "LIKE":
                    pageableList.add(cb.like(cb.lower(root.<String>get(key)), QueryUtils.getSearchString((String) value)));
                break;
            case "LT":
                if(!StringUtils.isEmpty(dateType)){
                    pageableList.add(cb.lessThan(cb.lower(root.<String>get(key)), (Comparable)this.getDate(dateType, value.toString())));
                }else if(this.idNumber(key)){
                    pageableList.add(cb.lessThan(cb.lower(root.<String>get(key)), (Comparable) new BigDecimal((String) value)));
                }else{
                    pageableList.add(cb.lessThan(cb.lower(root.<String>get(key)), (Comparable) value));
                }
                break;
            case "GT":
                if(!StringUtils.isEmpty(dateType)){
                    pageableList.add(cb.greaterThan(cb.lower(root.<String>get(key)), (Comparable)this.getDate(dateType, value.toString())));
                }else if(this.idNumber(key)){
                    pageableList.add(cb.greaterThan(cb.lower(root.<String>get(key)), (Comparable) new BigDecimal((String) value)));
                }else{
                    pageableList.add(cb.greaterThan(cb.lower(root.<String>get(key)), (Comparable) value));
                }
                break;
            case "LTE":
                if(!StringUtils.isEmpty(dateType)){
                    pageableList.add(cb.lessThanOrEqualTo(cb.lower(root.<String>get(key)), (Comparable)this.getDate(dateType, value.toString())));
                }else if(this.idNumber(key)){
                    pageableList.add(cb.lessThanOrEqualTo(cb.lower(root.<String>get(key)), (Comparable) new BigDecimal((String) value)));
                }else{
                    pageableList.add(cb.lessThanOrEqualTo(cb.lower(root.<String>get(key)), (Comparable) value));
                }
                break;
            case "GTE":
                if(!StringUtils.isEmpty(dateType)){
                    pageableList.add(cb.greaterThanOrEqualTo(cb.lower(root.<String>get(key)), (Comparable)this.getDate(dateType, value.toString())));
                }else if(this.idNumber(key)){
                    pageableList.add(cb.greaterThanOrEqualTo(cb.lower(root.<String>get(key)), (Comparable) new BigDecimal((String) value)));
                }else{
                    pageableList.add(cb.greaterThanOrEqualTo(cb.lower(root.<String>get(key)), (Comparable) value));
                }
                break;
            case "BEWTEEN":
                String[] values = value.toString().split(",");
                if(values.length == 2){
                    if(!StringUtils.isEmpty(dateType)){
                        pageableList.add(cb.between(cb.lower(root.<String>get(key)), (Comparable)this.getDate(dateType, values[0]),(Comparable)this.getDate(dateType, values[1])));
                    }else if(this.idNumber(key)){
                        pageableList.add(cb.between(cb.lower(root.<String>get(key)), (Comparable) new BigDecimal(values[0]),(Comparable) new BigDecimal( values[1])));
                    }else{
                        pageableList.add(cb.between(cb.lower(root.<String>get(key)),values[0],values[1]));
                    }
                }
                break;
            default:
                if(!StringUtils.isEmpty(dateType)){
                    pageableList.add(cb.equal(cb.lower(root.<String>get(key)),this.getDate(dateType, value.toString())));
                }else if(this.idNumber(key)){
                    pageableList.add(cb.equal(cb.lower(root.<String>get(key)),new BigDecimal((String) value)));
                }else{
                    pageableList.add(cb.equal(cb.lower(root.<String>get(key)), value));
                }*/
                //break;
        }
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
     * 获取查询的属性名
     * @param key
     * @return
     */
    private String getQueryNamed(String key){
        return key.split(this.OPERATOR_SPLIT)[0];
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
