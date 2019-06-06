package com.code_save_the_queen.ps6;

import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.TextView;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    String ipreal = getIPAddress(true);
    Handler mHandler;
    WebSocketServer server;

    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://"+ipreal);
        } catch (URISyntaxException e) {}
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // globally
        TextView ip = (TextView)findViewById(R.id.ip);
        final TextView socket = (TextView)findViewById(R.id.socket);

        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                if(msg.what == 1){
                    String orchestratorIp =  msg.getData().getString("READ_MSG_KEY");
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    intent.putExtra("IP", orchestratorIp);
                    server.cancel();
                    startActivity(intent);
                    finish();
                }
            }
        };

        server = new WebSocketServer(mHandler);
        server.start();

        ip.setText(getIPAddress(true));

        //mSocket.on("connexion",getIp);
        //mSocket.connect();
    }

    private Emitter.Listener getIp = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String id;
                    try {
                        id = data.getString("ip");
                    } catch (JSONException e) {
                        return;
                    }

                    //socket.setText(id);

                }
            });
        }
    };

    public static String getIPAddress(boolean useIPv4) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress();
                        //boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                        boolean isIPv4 = sAddr.indexOf(':')<0;

                        if (useIPv4) {
                            if (isIPv4)
                                return sAddr;
                        } else {
                            if (!isIPv4) {
                                int delim = sAddr.indexOf('%'); // drop ip6 zone suffix
                                return delim<0 ? sAddr.toUpperCase() : sAddr.substring(0, delim).toUpperCase();
                            }
                        }
                    }
                }
            }
        } catch (Exception ignored) { } // for now eat exceptions
        return "";
    }


}
