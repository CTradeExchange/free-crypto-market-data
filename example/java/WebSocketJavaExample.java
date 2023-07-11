
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;

import java.net.URISyntaxException;

import javax.websocket.*;


@ClientEndpoint

public class WebSocketJavaExample {

    

    private Session session;

    

    @OnOpen

    public void onOpen(Session session) {

        System.out.println("Connected to server");

        this.session = session;

    }

    

    @OnMessage

    public void onMessage(String message) {

        System.out.println("Received message: " + message);

    }

    

    @OnClose

    public void onClose(Session session, CloseReason closeReason) {

        System.out.println("Disconnected from server");

    }

    

    @OnError

    public void onError(Throwable throwable) {

        System.err.println("Error: " + throwable.getMessage());

    }

    

    public void sendMessage(String message) throws Exception {

        this.session.getBasicRemote().sendText(message);

    }

    

    public static void main(String[] args) throws Exception, URISyntaxException, DeploymentException, IOException, IllegalArgumentException, SecurityException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        WebSocketContainer container = ContainerProvider.getWebSocketContainer();

        URI uri = new URI("wss://quote.aatest.online/quote-b-ws-api?token=a9037628-30ae-4ffa-bd3c-9f7beaf1d44d-1688712831666"); // Replace with your websocket endpoint URL

        WebSocketJavaExample client = new WebSocketJavaExample();

        container.connectToServer(client, uri);

        

        // Send messages to the server using the sendMessage() method

        //如果希望长时间运行，除了需要发送订阅之外，还需要修改代码，定时发送心跳，避免连接断开，具体查看接口文档
        client.sendMessage("{\"cmd_id\": 22002, \"seq_id\": 123,\"trace\":\"3baaa938-f92c-4a74-a228-fd49d5e2f8bc\",\"data\":{\"symbol_list\":[{\"code\": \"BTCUSDT\",\"depth_level\": 5},{\"code\": \"ETHUSDT\",\"depth_level\": 5}]}}");
        

        // Wait for the client to be disconnected from the server (or until the user presses Enter)

        System.in.read(); // Wait for user input before closing the program

    }

}

