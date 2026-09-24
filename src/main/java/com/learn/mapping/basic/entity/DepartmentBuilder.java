package com.learn.mapping.basic.entity;

public class DepartmentBuilder {
    private Long id;
    private String name;
    private String code;
    private String description;

    public DepartmentBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public DepartmentBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public DepartmentBuilder setCode(String code) {
        this.code = code;
        return this;
    }

    public DepartmentBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public Department createDepartment() {
        return new Department(id, name, code, description);
    }
}