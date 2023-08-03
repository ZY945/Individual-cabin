package com.cabin.common.util.file;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * @author 伍六七
 * @date 2023/8/3 13:49
 */
@Slf4j
public class UploadUtil {
    /**
     * 获取上传文件的md5
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static String getMd5(MultipartFile file) {
        try {
            //获取文件的byte信息
            byte[] uploadBytes = file.getBytes();
            // 拿到一个MD5转换器
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] digest = md5.digest(uploadBytes);
            //转换为16进制
            return new BigInteger(1, digest).toString(16);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

}
