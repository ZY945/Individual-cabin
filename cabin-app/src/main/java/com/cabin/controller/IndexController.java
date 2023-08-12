package com.cabin.controller;

import com.cabin.common.annotation.AccessLimit;
import com.cabin.common.config.PatchcaConfig;
import com.cabin.service.UtilService;
import com.cabin.utils.beanUtils.ObjectUtil;
import com.cabin.utils.commonUtil.StringUtil;
import com.cabin.utils.jsonUtil.JsonUtil;
import com.cabin.utils.response.Result;
import com.github.bingoohuang.patchca.service.CaptchaService;
import com.github.bingoohuang.patchca.utils.encoder.EncoderHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.FileOutputStream;

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
     *
     * @param url 原链接
     * @return 短链接
     */
    @AccessLimit(seconds = 10, maxLimit = 1)
    @GetMapping("/shortUrl")
    public Result<String> generatesShortUrl(@RequestParam String url) {
        String shortUrl = utilService.setShortUrl(url);
        return Result.success(shortUrl, "短链接");
    }

//    /**
//     * 根据短链接重定向1.0--后端重定向
//     * @param shortUrl 短链接
//     */
//    @GetMapping("/{shortUrl}")
//    public void redirect(@PathVariable String shortUrl, HttpServletResponse response) throws IOException {
//        if (StringUtil.isNullOrEmpty(shortUrl)) {
//            throw new RuntimeException("请输入正确格式");
//        }
//        String url = utilService.getShortUrl(shortUrl);
//        ObjectUtil.checkStrNonEmpty(url, "url");
//        response.sendRedirect(url);
//    }

    /**
     * 根据短链接重定向2.0--按照302的规范，把url放在响应头的Location
     *
     * @param shortUrl 短链接
     */
    @GetMapping("/{shortUrl}")
    public void redirect(@PathVariable String shortUrl, HttpServletResponse response) {
        if (StringUtil.isNullOrEmpty(shortUrl)) {
            throw new RuntimeException("请输入正确格式");
        }
        String url = utilService.getShortUrl(shortUrl);
        ObjectUtil.checkStrNonEmpty(url, "url");
        response.setStatus(HttpServletResponse.SC_FOUND); // 设置状态码为302
        response.setHeader("Location", url); // 将新页面的URL放入响应头中
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

    @GetMapping("/test")
    public Result<String> test() {
        return Result.success("test", "test");
    }
}
