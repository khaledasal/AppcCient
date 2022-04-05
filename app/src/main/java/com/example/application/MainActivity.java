package com.example.application;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    EditText ET;
    Button connectbtn;
    Button sendbtn;
    TextView TV;
    Socket clientSocket;
    DataInputStream inFromServer;
    DataOutputStream outToServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ET=findViewById(R.id.ET);
        TV=findViewById(R.id.TV);
        connectbtn=findViewById(R.id.connectbtn);
        sendbtn=findViewById(R.id.sendbtn);

        connectbtn.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View view) {
                try {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    clientSocket = new Socket("192.168.1.2", 7000);
                    inFromServer = new DataInputStream(clientSocket.getInputStream());
                    outToServer = new DataOutputStream(clientSocket.getOutputStream());
                    TV.setText("Connected\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        sendbtn.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View view) {
                try {
                    String sentence=ET.getText().toString().trim();
                    outToServer.writeUTF(sentence);
                    String modifiedSentence = inFromServer.readUTF();
                    ET.setText("");
                    TV.setText("Server: "+modifiedSentence);
                    outToServer.flush();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


    }
}