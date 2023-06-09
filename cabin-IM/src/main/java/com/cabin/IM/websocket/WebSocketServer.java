package com.cabin.IM.websocket;

import com.cabin.IM.empty.SessionWrap;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 伍六七
 * @date 2023/6/1 22:53
 */
@ServerEndpoint(value = "/api/websocket/{from}/{to}")
@Component
public class WebSocketServer {
    private static List<SessionWrap> sessionList = new ArrayList<>();
    private String from;
    private String to;

    @OnOpen
    public void onOpen(Session session, @PathParam("from") String from, @PathParam("to") String to) {
        this.from = from;
        this.to = to;
        for (SessionWrap item : sessionList) {
            if (item.getFrom().equals(from) && item.getTo().equals(to)) {
                item.setSession(session);
                item.setLastTime(new Date());
                return;
            }
        }
        SessionWrap sessionWrap = new SessionWrap();
        sessionWrap.setFrom(from);
        sessionWrap.setTo(to);
        sessionWrap.setSession(session);
        sessionWrap.setLastTime(new Date());
        sessionList.add(sessionWrap);
    }

    @OnClose
    public void onClose() {
        ArrayList<SessionWrap> newSessionList = new ArrayList<>();
        for (SessionWrap item : sessionList) {
            if (item.getFrom().equals(from) && item.getTo().equals(to)) {
                continue;
            }
            newSessionList.add(item);
        }
        sessionList = newSessionList;
    }


    @OnMessage
    public void onMessage(Session session, String message) {
        try {
            //发送消息
            for (SessionWrap item : sessionList) {
                if (item.getFrom().equals(to) && item.getTo().equals(from)) {
                    item.getSession().getBasicRemote().sendText(message);
                    System.out.println(from + " to " + to + message);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
