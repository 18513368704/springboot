package com.yy.webStock;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint("/connectWebSocket/{userId}")
public class WebSocket {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private  static  Long onLine=0L;//在线人数
    private static Map<String,WebSocket> client = new ConcurrentHashMap<>();//客户端
    private Session session;
    private String userId;
    /*
    *建立连接
    */
    @OnOpen
    public void onOpen(@PathParam("userId")String userId, Session session){
        onLine++;
        this.userId = userId;
        this.session = session;
        //通知所有人我上线了 1代表上线 2代表下线 3代表在线名单 4代表普通消息
      /*  Map<String,Object> map1 = new HashMap<>();
        map1.put("megType",1);
        map1.put("userId",userId);
        sendMessageAll(JSON.toJSONString(map1),userId);*/
        //加入聊天室
        client.put(userId,this);
        //获取在线名单
        Map<String,Object> map2 = new HashMap<>();
        map2.put("messageType",3);
        //移除掉自己
        Set<String> set = client.keySet();
        map2.put("onlineUsers",set);
        try {
            sendMessageTo(JSON.toJSONString(map2),userId);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 收到客户端的消息
     *
     * @param message 消息
     * @param session 会话
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        try {
            JSONObject jsonObject = JSON.parseObject(message);
            String toUserId = jsonObject.getString("to");
            String msg = jsonObject.getString("msg");
            logger.info("来自客户端消息：" + msg+"客户端的id是："+session.getId()+"用户id"+userId+"发送至:"+toUserId);
            //如果不是发给所有，那么就发给某一个人
            //messageType 1代表上线 2代表下线 3代表在线名单  4代表普通消息
            Map<String,Object> map1 = new HashMap<>();
            map1.put("messageType",4);
            map1.put("textMessage",msg);
            map1.put("fromuserId",userId);
            if(toUserId.equals("All")){
                map1.put("touserId","所有人");
                sendMessageAll(JSON.toJSONString(map1),userId);
            }
            else{
                map1.put("touserId",toUserId);
                System.out.println("开始推送消息给"+toUserId);
                //session.getAsyncRemote().sendText(JSON.toJSONString(map1));
                sendMessageTo(JSON.toJSONString(map1),toUserId);
            }
        }
        catch (Exception e){

            e.printStackTrace();
            logger.info("发生了错误了");
        }

    }


    @OnError
    public void onError(Session session, Throwable error) {
        logger.info("服务端发生了错误"+error.getMessage());
        //error.printStackTrace();
    }
    /**
     * 连接关闭
     */
    @OnClose
    public void onClose() {

        onLine--;
        client.remove(userId);
        try {
            Map<String,Object> map1 = new HashMap<>();
            map1.put("messageType",2);
            map1.put("onlineUsers",client.keySet());
            map1.put("userId",userId);
            sendMessageAll(JSON.toJSONString(map1),userId);
        }catch (Exception e){
            logger.info(userId+"下线的时候通知所有人发生了错误");
        }
        logger.info("有连接关闭！ 当前在线人数" + onLine);
    }


    private void sendMessageTo(String s, String userId) throws IOException {
        for (String key: client.keySet()) {
            if (key.equals(userId) ) {
                client.get(key).session.getBasicRemote().sendText(s);
                break;
            }
        }

    }

    private void sendMessageAll(String s, String userId) {
        for (String key:client.keySet()) {
           client.get(key).session.getAsyncRemote().sendText(s);
        }

    }

    public static synchronized Long getOnlineCount() {
        return onLine;
    }
}
