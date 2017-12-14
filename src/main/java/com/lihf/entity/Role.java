package com.lihf.entity;

/**
 * 角色权限对象
 * @author lihf
 * @date 2017-12-14
 */
public class Role {
    private Integer id;
    /**
     *权限名称
     */
    private String name;
    /**
     * 与之关联的User对象id
     */
    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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
        this.name = name;
    }
}
