package ch.rootkit.varoke.network;

import ch.rootkit.varoke.utils.Configuration;
import ch.rootkit.varoke.utils.Logger;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.FixedRecvByteBufAllocator;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class ConnectionListener {

	private static ServerBootstrap bootstrap;
	public static void startBootstrap() throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(10);
        bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup);
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
        	@Override
        	public void initChannel(SocketChannel ch) throws Exception {
        		ch.pipeline().addLast("messageDecoder", (ChannelHandler) new MessageDecoder());
        		ch.pipeline().addLast(new ConnectionHandler());
        	}
        });
        bootstrap.childOption(ChannelOption.TCP_NODELAY, true);
        bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.childOption(ChannelOption.SO_REUSEADDR, true);
        bootstrap.childOption(ChannelOption.SO_RCVBUF, 5120);
        bootstrap.childOption(ChannelOption.RCVBUF_ALLOCATOR, new FixedRecvByteBufAllocator(5120));
        bootstrap.childOption(ChannelOption.ALLOCATOR, new PooledByteBufAllocator());
        bootstrap.bind(Configuration.get("tcp.ip"), Integer.parseInt(Configuration.get("tcp.port")));
        Logger.printVarokeLine("Listening for Connections on " + Configuration.get("tcp.ip") + ":" +  Configuration.get("tcp.port"));
	}
	public static void stopBootstrap() {
		bootstrap.group().shutdownGracefully();
	}
}
