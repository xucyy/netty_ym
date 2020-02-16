package io.netty.example.myexample;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Author xucy
 * @Date 2020-02-16 17:37
 * @Description
 **/

public class Server {

    static NioEventLoopGroup bossEvent=new NioEventLoopGroup();
    static NioEventLoopGroup workEvent=new NioEventLoopGroup(2);

    static ServerHandler serverHandler=new ServerHandler();

    public static void main(String[] args) {
        ServerBootstrap bootstrap=new ServerBootstrap();
        try{
        bootstrap.group(bossEvent,workEvent)
                .channel(NioServerSocketChannel.class)
                .childOption(ChannelOption.TCP_NODELAY,true)
                .childHandler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(serverHandler);
                    }
                });
        ChannelFuture f = bootstrap.bind(8000).sync();
        f.channel().closeFuture().sync();
        }catch (Exception e){
           e.printStackTrace();
        }finally {
            bossEvent.shutdownGracefully();
            workEvent.shutdownGracefully();
        }

    }
}
