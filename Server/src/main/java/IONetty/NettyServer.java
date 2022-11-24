package IONetty;

import Handlers.MessageHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class NettyServer {


    public static void main(String[] args) {
            Path rootDir = Paths.get("Storage/");
            EventLoopGroup auth = new NioEventLoopGroup(1); // Executor для одного потока
            EventLoopGroup worker = new NioEventLoopGroup(); //Executor для всех оставшихся потоков
            // Т.к. это Executor то их необходимо корректно закрыть. Оборачиваем в try-finally
            try {
                if (!Files.exists(rootDir)) {
                    Files.createDirectory(rootDir);
                }
                ServerBootstrap bootstrap = new ServerBootstrap();
                //Код ниже можно вызывать последовательно. Формат через точку возможет т.к. каждый из методов возвоащает this
                // В комменте указан идентичный последовательный код для простоты понимания.
                ChannelFuture future = bootstrap.group(auth, worker)
                        .channel(NioServerSocketChannel.class)
                        .childHandler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel channel) throws Exception {
                                channel.pipeline().addLast(
                                        new ObjectDecoder(ClassResolvers.cacheDisabled(null)),
                                        new ObjectEncoder(),
                                        new MessageHandler() //длф обработки сообщений от консольного клиента
                                );
                            }
                        }).bind(8189).sync();

                log.debug("NettyServer started");
                future.channel().closeFuture().sync(); // Блокирующая операция. Дальше программа не выполняется
            } catch (InterruptedException e) {
                log.error("e", e);
            } catch (IOException e) {
                log.error("Make storage DIR error: ", e);
            } finally {
                auth.shutdownGracefully();
                worker.shutdownGracefully();
            }
    }

}

