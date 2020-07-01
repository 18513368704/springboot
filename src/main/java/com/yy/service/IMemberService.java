package com.yy.service;

import com.yy.model.Member;
import net.sf.ehcache.CacheException;

import java.util.List;

public interface IMemberService {
    public void insert(Member member);

    public List<Member> selectAll(String id);

    public String getToken(String appId);

    public  List<Member> update(Member user) throws CacheException;

    public void delete(String uuid);
}
