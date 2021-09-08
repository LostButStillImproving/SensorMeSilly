package com.example.sensormesilly;

import com.example.sensormesilly.network.ClientConnector;
import com.example.sensormesilly.network.ServiceRequest;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class HelloController {
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        Thread t1 = new Thread(() ->
        {
            try {
                Thread.sleep(1000);
                Socket clientConnection = ClientConnector.getConnection("127.0.0.1", 1234);
                Future<String> answer = executorService.submit(new ServiceRequest(clientConnection, "get"));
                String value = answer.get();
                /*Map<String, Object> json = new Gson().fromJson(
                        value, new TypeToken<HashMap<String, Object>>() {}.getType()
                );
                Platform.runLater(() -> welcomeText.setText((String) json.get("time")));*/

            } catch (InterruptedException | ExecutionException | IOException e) {
                Thread.currentThread().interrupt();
            }

        });
        t1.start();
    }

    @FXML
    protected void onShutdownClick() throws IOException {
        Socket clientConnection = ClientConnector.getConnection("127.0.0.1", 1234);
        executorService.submit(new ServiceRequest(clientConnection, "shutdown"));
    }
}