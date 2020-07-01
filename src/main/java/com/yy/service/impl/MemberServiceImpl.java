package com.yy.service.impl;

import com.yy.comm.DataSource;
import com.yy.mapper.MemberMapper;
import com.yy.model.Member;
import com.yy.service.IMemberService;
import net.sf.ehcache.CacheException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


import java.util.List;
@Service
public class MemberServiceImpl implements IMemberService{
    @Autowired
    private MemberMapper memberMapper;

    @Override
    @Cacheable(value="yyCache",key="'user_'+#uuid")
    public List<Member> selectAll(String uuid) {
        //若找不到缓存将打印出提示语句
        System.err.println("没有走缓存！"+uuid);
        return memberMapper.selectAll(uuid);
    }
    //更新用户数据
    @CachePut(value = "yyCache",key = "'user_'+#user.getId()")
    public List<Member> update(Member user) throws CacheException {
        Member user1 = memberMapper.selectAll(user.getId()).get(0);
        if (null == user1){
            throw new  CacheException("Not Find");
        }
        user1.setAge(user.getAge());
        user1.setSname(user.getSname());
        memberMapper.update(user1);
        List<Member> members = memberMapper.selectAll(user1.getId());
        System.out.println("更新操作完成");
        return members;
    }

    @Override
    public void insert(Member member) {
        memberMapper.insert(member);
    }
    @CacheEvict(value = "yyCache",key = "'user_'+#uuid")//这是清除缓存
    public void delete(String uuid){
        memberMapper.delete(uuid);
    }

    @Override
    @DataSource
    public String getToken(String appId) {
        return memberMapper.findIdBySname(appId);
    }
}
