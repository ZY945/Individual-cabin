package com.cabin.common.Task;

import com.cabin.service.UtilService;
import com.cabin.utils.BeanUtils.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
 * @author 伍六七
 * @date 2023/6/2 10:17
 */
@Service
public class ShortTask {


    @Autowired
    private UtilService utilService;

    @Async("shortLinkExecutor")
    public CompletableFuture<String> redirect(String shortUrl) {
        //TODO 获取长链接
        String url = utilService.getShortUrl(shortUrl);
        ObjectUtil.checkStrNonEmpty(url, "url");
        return CompletableFuture.completedFuture(url);
    }
}
