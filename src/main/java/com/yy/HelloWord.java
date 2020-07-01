package com.yy;

import com.yy.model.Member;
import com.yy.service.IMemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class HelloWord {

    private static final Logger logger = LoggerFactory.getLogger(HelloWord.class);
    @Autowired
    private IMemberService memberService;

    @RequestMapping("/index")
    public String helloWord(){
        return "hello word";
    }
    @RequestMapping("/insert")
    public void insert() {
        Member member = new Member();
        member.setSname("zhangsan");
        member.setId("11");
        memberService.insert(member);
    }
    @RequestMapping("/selectAll")
    public void selectAll() {
          /*  List<Member> members = memberService.selectAll("11");
           for(int j = 0;j<members.size();j++) {
              System.out.println(members.get(j).getAge());
           }*/
        logger.debug("进行Encache缓存测试");
        System.out.println("====生成第一个用户====");
        Member member = new Member();
        String u1_uuid = UUID.randomUUID().toString();
        //去掉 UUID 的 - 符号
        String uuid1 = u1_uuid.substring(0,8)+u1_uuid.substring(9,13)+u1_uuid.substring(14,18)+u1_uuid.substring(19,23)+u1_uuid.substring(24);
        member.setSname("张三");
        member.setAge(18);
        member.setId(uuid1);
        //删除缓存
        memberService.insert(member);
        //第一次查询
        System.out.println(memberService.selectAll(uuid1));
        //通过缓存查询
        System.out.println(memberService.selectAll(uuid1));

       /* System.out.println("====修改数据====");
        Member user2 = new Member();
        user2.setSname("李四-update");
        user2.setAge(22);
        user2.setId(uuid1);
        memberService.update(user2);
        System.out.println("====查====");
        System.out.println(memberService.selectAll(uuid1));*/
        System.out.println("====删除====");
        memberService.delete(uuid1);
        System.out.println("====删除缓存后再查====");
        System.out.println(memberService.selectAll(uuid1));
    }
    @RequestMapping("/findIdByName")
    public void testReadFromMaster() {
        memberService.getToken("zhangsan");
    }

}
