package com.cabin.common.config;

import com.github.bingoohuang.patchca.color.ColorFactory;
import com.github.bingoohuang.patchca.color.SingleColorFactory;
import com.github.bingoohuang.patchca.custom.ConfigurableCaptchaService;
import com.github.bingoohuang.patchca.filter.predefined.CurvesRippleFilterFactory;
import com.github.bingoohuang.patchca.font.RandomFontFactory;
import com.github.bingoohuang.patchca.service.Captcha;
import com.github.bingoohuang.patchca.service.CaptchaService;
import com.github.bingoohuang.patchca.word.RandomWordFactory;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author 伍六七
 * @date 2023/5/22 22:44
 */
public class PatchcaConfig {
    private static final String DEFAULT_CHARACTERS = "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"; //
    private static int DEFAULT_FONT_SIZE = 25;
    private static int DEFAULT_WORD_LENGTH = 6;
    private static int DEFAULT_WIDTH = 80;
    private static int DEFAULT_HEIGHT = 35;

    public static CaptchaService create(int fontSize, int wordLength, String characters, int width, int height) {
        ConfigurableCaptchaService service = null;
        // 字体大小设置
        RandomFontFactory ff = new RandomFontFactory();
        ff.setMinSize(fontSize);
        ff.setMaxSize(fontSize);
        // 生成的单词设置
        RandomWordFactory rwf = new RandomWordFactory();
        rwf.setCharacters(characters);
        rwf.setMinLength(wordLength);
        rwf.setMaxLength(wordLength);
        // 干扰线波纹
        CurvesRippleFilterFactory crff = new CurvesRippleFilterFactory();
        // 处理器
        service = new ExConfigurableCaptchaService();
        service.setFontFactory(ff);
        service.setWordFactory(rwf);
        service.setFilterFactory(crff);
        // 生成图片大小（像素）
        service.setWidth(width);
        service.setHeight(height);
        return service;
    }

    public static CaptchaService create() {
        return create(DEFAULT_FONT_SIZE, DEFAULT_WORD_LENGTH, DEFAULT_CHARACTERS, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    // 随机变色处理
    static class ExConfigurableCaptchaService extends ConfigurableCaptchaService {
        private static final Random RANDOM = new Random();
        private List<SingleColorFactory> colorList = new ArrayList<>(); // 为了性能

        public ExConfigurableCaptchaService() {
            colorList.add(new SingleColorFactory(Color.blue));
            colorList.add(new SingleColorFactory(Color.black));
            colorList.add(new SingleColorFactory(Color.red));
            colorList.add(new SingleColorFactory(Color.pink));
            colorList.add(new SingleColorFactory(Color.orange));
            colorList.add(new SingleColorFactory(Color.green));
            colorList.add(new SingleColorFactory(Color.magenta));
            colorList.add(new SingleColorFactory(Color.cyan));
        }

        public ColorFactory nextColorFactory() {
            int index = RANDOM.nextInt(colorList.size());
            return (ColorFactory) colorList.get(index);
        }

        @Override
        public Captcha getCaptcha() {
            ColorFactory cf = nextColorFactory();
            CurvesRippleFilterFactory crff = (CurvesRippleFilterFactory) this.getFilterFactory();
            crff.setColorFactory(cf);
            this.setColorFactory(cf);
            return super.getCaptcha();
        }
    }
}
