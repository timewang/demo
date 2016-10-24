package com.webhybird.module.label.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.internal.NotNull;
import com.webhybird.module.user.entity.User;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * 修改时会清空创建人 创建时间等信息
 * 目前能想到的办法就是 查出来 放到一个缓存中 保存时再将这些信息带回去
 * 开启了动态更新后 就不会再更新这些信息
 * 可以考虑写个切面类 在查询出数据后 afterReturn 获取这些信息
 * 在brfore 更新前 将这些信息设置回去
 * 更新时先查出来 再set回去
 *
 *  JOIN FETCH和缓存

 如果在命名查询中使用JOIN FETCH：

 @NamedQuery(name="findAll", query="SELECT s FROM StockPriceEagerLazyImpl s " + "JOIN FETCH s.optionsPrices ORDER BY s.id.symbol")

  * Created by wangzhongfu on 2015/5/5.
 */
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "LABEL")
@EntityListeners(AuditingEntityListener.class)
//@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include= JsonTypeInfo.As.PROPERTY, property="@class")
public class Label {
    @Id
    @Column
    private String id;
    @Column(name = "value_")
    private String value;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "CREATE_DATE")
    private Date createDate;
    @Column
    private Integer pageView;
    @Column
    private Float price;

    @CreatedBy
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    @NotNull
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})//防止JSON转化时出错
    private User createdBy;

    @LastModifiedBy
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    @NotNull
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})//防止JSON转化时出错
    private User lastModifiedBy;

    @Column
    private String test;


    @CreatedDate
    @NotNull
    //@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private Date createdDate;

    @LastModifiedDate
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
   // @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private Date lastModifiedDate;
    /**
     * 该字段将不作为数据局字段映射
     */
    @Transient
    private String fieldNoMapping;

    public Label(String id, String value) {
        this.id = id;
        this.value = value;
    }

    public String getFieldNoMapping() {
        return fieldNoMapping;
    }

    public void setFieldNoMapping(String fieldNoMapping) {
        this.fieldNoMapping = fieldNoMapping;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public User getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(User lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getPageView() {
        return pageView;
    }

    public void setPageView(Integer pageView) {
        this.pageView = pageView;
    }

    public Label() {
    }

    public Label(String value) {
        this.value = value;

    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Label label = (Label) o;

        return id.equals(label.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
