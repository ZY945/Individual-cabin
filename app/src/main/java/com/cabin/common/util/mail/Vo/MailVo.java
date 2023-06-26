package com.cabin.common.util.mail.Vo;

import com.dingtalk.api.request.OapiRobotSendRequest.Markdown;
import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class MailVo {

    //    @ApiModelProperty("邮件（多个邮箱则用逗号\",\"隔开）")
    private String to;

    //    @ApiModelProperty("标题")
    private String sub;

    private Markdown markdown;

    //    @ApiModelProperty("内容")
    private String text;

    //    @ApiModelProperty("邮件附件路径")
    private String filePath;//资源路径

    //    @ApiModelProperty("图片")
    private String imgPath;//图片
}
