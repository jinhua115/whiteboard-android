package com.herewhite.demo.test;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.herewhite.demo.R;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.enums.ReadyState;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class WebSocketTestActivity extends AppCompatActivity {
    private static final String TAG = WebSocketTestActivity.class.getSimpleName();

    private TextView display;
    private EditText messageInput;
    private Button connect;
    private Button close;
    private Button send;

    WebSocketClient client;
    private ReadyState readyState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_socket_test);

        display = findViewById(R.id.logDisplay);
        display.setMovementMethod(new ScrollingMovementMethod());

        messageInput = findViewById(R.id.editText);

        send = findViewById(R.id.sendMessage);
        connect = findViewById(R.id.connect);
        close = findViewById(R.id.close);

        connect.setOnClickListener(this::onConnect);
        close.setOnClickListener(this::onClose);
        send.setOnClickListener(this::onSendMessage);
    }

    private void onSendMessage(View view) {
        String message = messageInput.getText().toString();
        if (!message.equals("")) {
            if (client != null && readyState == ReadyState.OPEN) {
                client.send(message);
            }
        }
    }

    private void onClose(View view) {
        if (client != null) {
            client.close();
        }
    }

    private void onConnect(View view) {
        try {
            client = new WebSocketClient(new URI("wss://echo.websocket.org")) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    appendLog("onOpen handshakedata" + handshakedata);
                    readyState = client.getReadyState();
                }

                @Override
                public void onMessage(String message) {
                    appendLog("onMessage message " + message);
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    appendLog("onClose reason " + reason);
                    readyState = ReadyState.CLOSED;
                }

                @Override
                public void onError(Exception ex) {
                    appendLog("onError");
                }
            };
            client.connect();
        } catch (Exception e) {
            appendLog("onConnect Exception :" + e);
        }
    }

    private void appendLog(String log) {
        display.append(log + "\n");
    }
}
