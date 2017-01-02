package com.rishabh.chauhan.wifitry2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView text;
    Button btn;
    private static final String TAG="Wifi";
    WifiManager wifi;
    BroadcastReceiver reciever;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text =(TextView) findViewById(R.id.text);
        btn =(Button) findViewById(R.id.btn);
        //get wifi status
        wifi =(WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo info= wifi.getConnectionInfo();
        text.append("\n\nwifi status :" + info.toString());
        //list available network
        List<WifiConfiguration> configurations=wifi.getConfiguredNetworks();
        for(WifiConfiguration configuration:configurations){
            text.append("\n\n"+ configuration.toString());
        }
//register broadcast receiver
        if(reciever==null){
            reciever=new WifiScaner(this);
        }
        registerReceiver(reciever, new IntentFilter(
                WifiManager.SCAN_RESULTS_AVAILABLE_ACTION
        ));
        Log.d(TAG,"oncreate()");
    }

    @Override
    protected void onStop() {
        // TODO auto-generated method stub
        unregisterReceiver(reciever);
    }




    public void action (View view){
        Toast.makeText(getApplicationContext(),"All Network searched",Toast.LENGTH_SHORT).show();
        if(view.getId()==R.id.btn){
            Log.d(TAG,"onCreate() wifi.startscan()");
            wifi.startScan();
        }

    }


}
