package com.yy.mapper;

import com.yy.model.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface MemberMapper {
    List<Member> selectAll(String id);

    void insert(Member member);

    String findIdBySname(String Sname);

    void update(Member member);

    void delete(String id);
}
