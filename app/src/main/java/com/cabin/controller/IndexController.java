package com.cabin.controller;

import com.cabin.common.config.PatchcaConfig;
import com.cabin.entity.response.Result;
import com.cabin.service.UtilService;
import com.cabin.utils.BeanUtils.ObjectUtil;
import com.cabin.utils.jsonUtil.JsonUtil;
import com.github.bingoohuang.patchca.service.CaptchaService;
import com.github.bingoohuang.patchca.utils.encoder.EncoderHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author 伍六七
 * @date 2023/5/22 22:47
 */
@RestController
@RequestMapping("/util")
public class IndexController {

    @Autowired
    private UtilService utilService;

    /**
     * 短链接获取
     * @param url 原链接
     * @return 短链接
     */
    @GetMapping("/shortUrl")
    public Result<String> generatesShortUrl(@RequestParam String url) {
        String shortUrl = utilService.setShortUrl(url);
        return Result.success(shortUrl, "短链接");
    }

    /**
     * 根据短链接重定向
     * @param shortUrl 短链接
     */
    @GetMapping("/{shortUrl}")
    public void redirect(@PathVariable String shortUrl, HttpServletResponse response) throws IOException {
        String url = utilService.getShortUrl(shortUrl);
        ObjectUtil.checkStrNonEmpty(url, "url");
        response.sendRedirect(url);
    }


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
