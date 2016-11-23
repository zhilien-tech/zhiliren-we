package org.zxc.netty.unstick;

import java.util.Date;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NettyTimeServerHandler extends ChannelHandlerAdapter  {
	
	private static Logger logger = LoggerFactory.getLogger(NettyTimeServerHandler.class) ;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)throws Exception { 
		ByteBuf buffer = (ByteBuf) msg ;
		byte[] req = new byte[buffer.readableBytes()] ;
		buffer.readBytes(req) ;
		String body = new String(req,"UTF-8") ;
		
		logger.info("the time server receive order : " + body);
		
		//响应数据
		String result = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new Date(
				System.currentTimeMillis()).toString() : "BAD ORDER"; 
		ByteBuf resp = Unpooled.copiedBuffer(result.getBytes()) ;
		
		//写入通道
		ctx.write(resp) ;
		
	}
	
    @Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    	ctx.flush() ;
	}

	@Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        cause.printStackTrace();
        ctx.close();
    }
	
}
