package Handlers;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientMessageHandler extends SimpleChannelInboundHandler<AbstractMessage>{

    private OnMessageReceived callback;

    public ClientMessageHandler(OnMessageReceived callback) {
        this.callback = callback;
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, AbstractMessage abstractMessage) throws Exception {
        callback.onReceive(abstractMessage);
    }
}
