package com.webhybird.module.tree.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by wangzhongfu on 2015/5/27.
 */
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "ZtreeEntity")
public class ZtreeEntity implements Serializable {
    @Id
    @Column(length = 32)
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name = "system-uuid",strategy="uuid")
    private String id;
    @Column(length = 32)
    private String pid;
    @Column(length = 128)
    private String name;
    @Column
    private String url;
    @Column
    private Boolean isParent;
    @Column(length = 64)
    private String click;
    @Column(length = 10)
    private String target;
    @Column(length = 32)
    private String type;
    @Column
    private Date createtime;
    @Column(length = 5)
    private Integer compositor;
    @Column(length = 32)
    private String createrid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getIsParent() {
        return isParent;
    }

    public void setIsParent(Boolean isParent) {
        this.isParent = isParent;
    }

    public String getClick() {
        return click;
    }

    public void setClick(String click) {
        this.click = click;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getCompositor() {
        return compositor;
    }

    public void setCompositor(Integer compositor) {
        this.compositor = compositor;
    }

    public String getCreaterid() {
        return createrid;
    }

    public void setCreaterid(String createrid) {
        this.createrid = createrid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ZtreeEntity that = (ZtreeEntity) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
