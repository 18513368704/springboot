package com.yy.model;

public class Person2 {

    private String name;
    private Integer id ;
    private String email;

    public String getEmail() {
        return email;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
    class  PhoneNumber {
         String number;
         PhoneType type = PhoneType.HOME;
    }
    enum PhoneType {
        MOBILE,HOME,WORK

    }

    @Override
    public String toString() {
        return super.toString();
    }
}
