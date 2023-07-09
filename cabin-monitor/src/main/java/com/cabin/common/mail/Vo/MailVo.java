package com.cabin.common.mail.Vo;

import com.dingtalk.api.request.OapiRobotSendRequest.Markdown;
import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class MailVo {

    private String to;

    private String sub;

    private Markdown markdown;

    private String text;

    private String filePath;//资源路径

    private String imgPath;//图片
}
