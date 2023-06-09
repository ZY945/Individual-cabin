package com.cabin.utils.commonUtil;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author 伍六七
 * @date 2022/12/21 11:18
 */
public class Base64Util {
    /**
     * 加密
     * @param code
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String getEncoderByUtf8(String code) throws UnsupportedEncodingException {
        String encode = Base64.getEncoder().encodeToString(code.getBytes( StandardCharsets.UTF_8));
        return encode;
    }

    public static String getDecoderByUtf8(String code) throws UnsupportedEncodingException {
        byte[] decode = Base64.getDecoder().decode(code);
        return new String(decode, StandardCharsets.UTF_8);
    }
}
