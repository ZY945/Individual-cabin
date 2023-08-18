package com.cabin.controller;


import com.cabin.service.impl.HttpServiceImpl;

/**
 * @author 伍六七
 * @date 2023/8/17 17:13
 */
public class HttpController {

    public static void main(String[] args) throws Exception {
        HttpServiceImpl httpService = new HttpServiceImpl();
        String responseContent = httpService.getResponseContent("https://jsonplaceholder.typicode.com/todos/1");
        System.out.println(responseContent);
    }
}
