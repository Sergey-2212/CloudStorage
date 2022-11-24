package net;



import Handlers.ClientMessageHandler;
import Handlers.CommandMessage;
import Handlers.OnMessageReceived;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.channel.socket.SocketChannel;






public class NettyNet {

    private SocketChannel channel;
    private OnMessageReceived callback;

    public NettyNet(OnMessageReceived callback) {
        this.callback = callback;
        new Thread(() -> {
            EventLoopGroup group = new NioEventLoopGroup();
            Bootstrap bootstrap = new Bootstrap();
            try {
                ChannelFuture future =  bootstrap.channel(NioSocketChannel.class)
                        .group(group)
                        .handler(new ChannelInitializer<SocketChannel>() {

                            @Override
                            protected void initChannel(SocketChannel ch) throws Exception {
                                channel = ch;
                                channel.pipeline().addLast(
                                        new ObjectEncoder(),
                                        new ObjectDecoder(ClassResolvers.cacheDisabled(null)),
                                        new ClientMessageHandler(callback)

                                );
                            }
                        }).connect("localhost", 8189).sync();
                future.channel().closeFuture().sync(); // это блокирующая операция. Поэтому запустили выполнение в отдельном, а не главном потоке
            } catch (InterruptedException e) {
                //log.error("e= ", e);
            } finally {
                group.shutdownGracefully();
            }
        }).start();
    }

    public void sendCommand(CommandMessage message) {
        channel.writeAndFlush(message);

    }
}



