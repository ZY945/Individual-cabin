package com.cabin.IM.chat.websocket;

import jakarta.websocket.Session;

import java.util.Date;

/**
 * @author 伍六七
 * @date 2023/6/1 22:56
 */
public class SessionWrap {

    private String from;
    private String to;
    private Session session;
    private Date lastTime;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }
}
