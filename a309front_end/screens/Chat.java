package com.coms309.a309front_end.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.coms309.a309front_end.R;
import com.coms309.a309front_end.utils.GlobalData;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Chat room for users
 * @author  Rachel Owens
 * taken from COM S 309 Tutorial for Websockets
 */
public class Chat extends AppCompatActivity {

    private WebSocketClient webSocketClient;
    private Button connect, disconnect, send;
    private TextView output;
    private EditText input;

    private String DM="";


    private void connectWebSocket() {
        URI uri;
        try {

            uri = new URI("http://10.24.226.19:8080/websocket/" + GlobalData.username);

            //uri = new URI("ws://echo.websocket.org");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        webSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                Log.i("Websocket", "Opened");
            }

            @Override
            public void onMessage(String message) {
                //Log.d("", "run() returned: " + message);
                Log.i("Websocket", "Message received");

                if(DM.equals("")){
                    output.append("\n" + message);
                } else {
                    if(message.contains(":") && message.contains("@")) {
                        output.append("\n" + message.substring(message.indexOf("@")));
                    } else {
                        output.append("\n" + message);
                    }
                }
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                Log.i("Websocket", "Closed" + reason);

            }

            @Override
            public void onError(Exception ex) {
                Log.i("Websocket", "Error" + ex.getMessage());

            }
        };

        webSocketClient.connect();
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webSocketClient.close();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //connect = (Button) findViewById(R.id.b_connect);
        //disconnect = (Button) findViewById(R.id.b_Disconnect);
        send = (Button) findViewById(R.id.b_sendMessage);
        output = (TextView) findViewById(R.id.m_output);
        input = (EditText) findViewById(R.id.m_input);

        getPassedValues();

        connectWebSocket();


        //input.setText("@" + this.DM + " " );

        send.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                final String prefix = DM;
                String message  = input.getText().toString();

                if(!DM.equals("")){
                    message = "@" + prefix + " " + message;
                }

                if (message != null && message.length() > 0) {
                    webSocketClient.send(message);
                }
                input.setText("");
            }
        });

    }

    private void getPassedValues() {

        Intent intent = getIntent();
        if(intent == null){
            return;
        }

        Bundle b = intent.getExtras();

        if(b != null && b.containsKey("DM")){
            this.DM = b.getString("DM");
        }




    }

    @Override
    public void onPause() {

        super.onPause();
        if (isFinishing()) {
            webSocketClient.close();

        }
    }
}