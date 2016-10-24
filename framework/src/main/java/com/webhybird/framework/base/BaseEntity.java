package com.webhybird.framework.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.internal.NotNull;
import com.webhybird.module.user.entity.User;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by wangzhongfu on 2016/1/12.
 */
@Entity
@DynamicUpdate
@DynamicInsert
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
    @Id
    @Column(length = 32)
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name = "system-uuid",strategy="uuid")
    private String id;

   /* */
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

    @CreatedDate
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    //@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private Date createdDate;

    @LastModifiedDate
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    // @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private Date lastModifiedDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
