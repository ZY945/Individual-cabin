package com.cabin.utils.commonUtil;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * @author 伍六七
 * @date 2022/12/21 11:18
 */
public class Base64Util {
    public static String getEncoderByUtf8(String code) throws UnsupportedEncodingException {
        String encode = Base64.getEncoder().encodeToString(code.getBytes("utf-8"));
        return encode;
    }

    public static String getDecoderByUtf8(String code) throws UnsupportedEncodingException {
        byte[] decode = Base64.getDecoder().decode(code);
        return new String(decode, "utf-8");
    }
}
