package com.wgc.instance.entity;

import java.io.Serializable;

/*
*Serializable是一个标准接口 */
public class PUser implements Serializable {
    private Integer id;

    private String name;

    //transient关键字，中文意思排除、短暂的，在序列化的是时候排除某些字段
    transient private String secret;
    public PUser() {}

    public PUser(String name) {
        this.name = name;
    }
    public PUser(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {

        this.name = name == null ? null : name.trim();
    }

    @Override
    public String toString() {
        return "PUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}