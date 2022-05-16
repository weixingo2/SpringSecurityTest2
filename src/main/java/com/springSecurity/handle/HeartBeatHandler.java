package com.springSecurity.handle;

import io.netty.channel.*;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @Author Sakura
 * @Date 5/8/2019
 * 用于检测channel心跳的handler
 **/
public class HeartBeatHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        //判断evt是否是IdleStateEvent(用于触发用户事件，包含读空闲/写空闲/读写空闲)
        if(evt instanceof IdleState){
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;

            if(idleStateEvent.state() == IdleState.READER_IDLE){
                System.out.println("进入读空闲...");
            }else if(idleStateEvent.state() == IdleState.WRITER_IDLE){
                System.out.println("进入写空闲...");
            }else if(idleStateEvent.state() == IdleState.ALL_IDLE){
                System.out.println("进入读写空闲...");

                Channel channel = ctx.channel();
                //关闭无用channel，避免浪费资源
                channel.close();
            }
        }
    }
}
