package Handlers;

import Commands.Command;
import IONetty.NettyServer;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
public class MessageHandler extends SimpleChannelInboundHandler<AbstractMessage> {
    private Path currentDir;
    //public MessageHandler(Path currentDir){
    //    this.currentDir = currentDir;
    // }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.debug("Connection isteblished.");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.debug("Connection closed.");
    }

    protected void messageReceived(ChannelHandlerContext ctx, AbstractMessage msg) throws Exception {
        log.debug("Received: {}", msg);
        if(msg instanceof CommandMessage) {
            processCommandMessage(ctx,msg);
        }



    }

    private void processCommandMessage(ChannelHandlerContext ctx, AbstractMessage msg) {
        CommandMessage cms = (CommandMessage) msg;
        Command command = cms.getCommand();
        switch (command) {
            case DELETE_FILE -> deleteFile(ctx, cms.getPath());
            case CREATE_DIRECTORY -> createDir(ctx, cms.getPath());
            case RENAME_DIRECTORY -> renameDir(ctx, cms.getPath(), cms.getNewPath());
        }
    }

    private void renameDir(ChannelHandlerContext ctx, Path path, Path newPath) {
        //TODO
    }

    private void createDir(ChannelHandlerContext ctx, Path path) {
        Path dir = path;
        try {
            Files.createDirectory(path);
        } catch (IOException e) {
            log.error("Mkdir error. {}", e);
        }
    }

    private void deleteFile(ChannelHandlerContext ctx, Path path) {
        Path file = currentDir.resolve(path);
        try {
            Files.delete(file);
        } catch (IOException e) {
            log.error("Deleting file error: {}", e);
        }
    }
}
