package com.cabin.IM.empty;

import jakarta.websocket.Session;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author 伍六七
 * @date 2023/6/1 22:56
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionWrap {

    private String from;
    private String to;
    private Session session;
    private Date lastTime;
}
