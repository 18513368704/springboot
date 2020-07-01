package com.yy;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.protobuf.InvalidProtocolBufferException;
import com.yy.model.Person2;
import com.yy.model.PersonProtos;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        System.out.println(Arrays.toString( args)+"111");
        PersonProtos.Person.Builder builder =PersonProtos.Person.newBuilder();
        PersonProtos.Person person =builder.setName("张三").setEmail("1231").setId(123).build();

        PersonProtos.Person.PhoneNumber.Builder phone = PersonProtos.Person.PhoneNumber.newBuilder();
        phone.setNumber("18610000000");
        //序列化(通过protobuf生成的java类的内部方法进行序列化)
        byte[] bytes = person.toByteArray();

        //反序列化(通过protobuf生成的java类的内部方法进行反序列化)
        try {
            PersonProtos.Person parseFrom = PersonProtos.Person.parseFrom(bytes);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }

        Person2 person2 = new Person2();
        person2.setId(123);
        person2.setEmail("12312312131231231231231231");
        person2.setName("张三阿斯达四胜多负少的方式大所");
        String s = JSON.toJSONString(person2);


        try {
        //第二种序列化：粘包,将一个或者多个protobuf对象字节写入stream。
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//生成一个由：[字节长度][字节数据]组成的package。特别适合RPC场景
        person.writeDelimitedTo(byteArrayOutputStream);
//反序列化，从steam中读取一个或者多个protobuf字节对象
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        PersonProtos.Person   result = PersonProtos.Person.parseDelimitedFrom(byteArrayInputStream);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
