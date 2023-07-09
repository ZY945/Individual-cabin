package com.cabin.empty.notice;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author 伍六七
 * @date 2023/7/8 18:15
 */
@Data
@Component()
public class NoticeInfo {

    @Value(value = "${notice.email}")
    private String userEmail;
}
