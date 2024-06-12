package com.threec.test.utils;

import javax.websocket.*;
import java.net.URI;
import java.util.concurrent.TimeUnit;

/**
 * Class WebSocketUtils1.
 * <p>
 * websocket连接测试
 * </p>
 *
 * @author laven
 * @version 1.0
 * @since 12/6/24
 */
public class WebSocketUtils1 {
    public static void main(String[] args) {
        URI uri = URI.create("ws://127.0.0.1:52469/api/Websocket/connect?userId=1415");
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();

        try {
            Session session = container.connectToServer(WebSocketUtils.MyWebSocketClient.class, uri);
            System.out.println("WebSocket连接已建立");

            // 保持连接10分钟
            TimeUnit.MINUTES.sleep(10);

            session.close();
            System.out.println("WebSocket连接已关闭");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ClientEndpoint
    public static class MyWebSocketClient {
        @OnOpen
        public void onOpen(Session session) {
            System.out.println("WebSocket 已连接");
        }

        @OnMessage
        public void onMessage(String message) {
            System.out.println("收到消息: " + message);
        }

        @OnClose
        public void onClose(CloseReason reason) {
            System.out.println("WebSocket 连接已关闭, 原因: " + reason.getCloseCode() + " - " + reason.getReasonPhrase());
        }

        @OnError
        public void onError(Throwable t) {
            System.out.println("WebSocket 连接出错: " + t.getMessage());
        }
    }
}
