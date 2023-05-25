package com.cabin.controller;

import com.cabin.common.config.PatchcaConfig;
import com.cabin.common.response.Result;
import com.cabin.utils.jsonUtil.JsonUtil;
import com.github.bingoohuang.patchca.service.CaptchaService;
import com.github.bingoohuang.patchca.utils.encoder.EncoderHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileOutputStream;

/**
 * @author 伍六七
 * @date 2023/5/22 22:47
 */
@RestController
@RequestMapping("/util")
public class UtilController {

    /**
     * 获取验证码(.png)
     */
    @GetMapping("/getcaptach")
    public void captach(HttpServletRequest request, HttpServletResponse response) throws Exception {
        CaptchaService service = PatchcaConfig.create();
        FileOutputStream fos = null;
        try {
            String token = EncoderHelper.getChallangeAndWriteImage(service, "png", response.getOutputStream());

            System.out.println(token);
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (Exception e) {
                }
            }
        }
    }

    /**
     * 格式化json
     *
     * @param olDJson 需要格式化的json
     * @return String newJson 已格式化json
     */
    @GetMapping("/format/json")
    public Result<String> formatJson(@RequestBody String olDJson) {
        String newJson = JsonUtil.formatJson(olDJson);
        return Result.success(newJson, "格式化的json");
    }
}
