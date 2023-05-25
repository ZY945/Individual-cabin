package com.cabin.IM;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author 伍六七
 * @date 2023/5/22 20:03
 */
public class IMServer {
    public static void start() {
        //设置两个线程组boosGroup和workerGroup
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();


        //创建服务端的启动对象，设置参数
        ServerBootstrap bootstrap = new ServerBootstrap();


        bootstrap.group(boss, worker)
                //设置服务端通道实现类型
                .channel(NioServerSocketChannel.class)
                //使用匿名内部类的形式初始化通道对象
                .childHandler(new ChannelInitializer<>() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {
                        ChannelPipeline pipeline = channel.pipeline();
                        //添加http编码解码器
                        pipeline.addLast(new HttpServerCodec())
                                .addLast(new ChunkedWriteHandler())
                                // 对http消息做聚合操作,FullHttpRequest、FullHttpResponse
                                .addLast(new HttpObjectAggregator(1024 * 64))
                                //websocket
                                .addLast(new WebSocketServerProtocolHandler("/"))
                                .addLast(new WebSocketHandler());

                    }
                });

        try {
            //绑定端口
            bootstrap.bind(8080).sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
