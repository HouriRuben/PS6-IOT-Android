package com.code_save_the_queen.ps6;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class WebSocketServer extends Thread {

    public Handler mHandler;
    Socket socket;

    public WebSocketServer(Handler handler){
        mHandler = handler;
    }

    @Override
    public void run() {
        try {
            // create ServerSocket using specified port
            ServerSocket serverSocket = new ServerSocket(7777);

                System.out.println("STARTED");
                // block the call until connection is created and return
                // Socket object
                socket = serverSocket.accept();
                String orchestratorIp = socket.getRemoteSocketAddress().toString();
                Log.d("SOCKET", "ip : " + orchestratorIp);
                Message readMsg = mHandler.obtainMessage(
                        1, orchestratorIp.getBytes().length, -1);
                Bundle bundle = new Bundle();
                bundle.putString("READ_MSG_KEY", orchestratorIp);
                readMsg.setData(bundle);
                readMsg.sendToTarget();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void cancel(){
        try {
            socket.close();
        } catch (IOException e) {
            Log.e("CONNECTION_CANCELLED", "Could not close the connect socket", e);
        }
    }
}
