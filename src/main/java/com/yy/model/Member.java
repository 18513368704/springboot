package com.yy.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Member implements Serializable {
    private String id;
    private String sname;
    private Integer age;
}
