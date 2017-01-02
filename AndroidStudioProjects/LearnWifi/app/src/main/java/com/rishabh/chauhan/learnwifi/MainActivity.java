package com.rishabh.chauhan.learnwifi;
//Libraries which are used in the program, they are all imported
import android.net.wifi.ScanResult;
import android.content.Context;
import android.net.wifi.*;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import java.util.List;
import java.util.TimerTask;
import java.util.Timer;
import android.view.KeyEvent;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.content.Intent;

//the main activity under which everything happens, extends AppCompatActivity means all the
// properties of AppCompatActivity is used in the main activity hence their is not need to redefine them
public class MainActivity extends AppCompatActivity {
    //Declaring variables which are used
    WifiManager myWifiManager;
    TextView text1,text2;
    WifiInfo info;
    ScanResult scanR;
    int abc;
    boolean wasEnabled;
//commands taking place when the activity is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //defining a class is done by "classname=(classtype)"
        myWifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        //classname.command executes command for that class
        //boolean defines if wifi is enabled
        wasEnabled=myWifiManager.isWifiEnabled();
        //wifi is set enabled
        myWifiManager.setWifiEnabled(true);
        //while wifi is kept enabled
        while(!myWifiManager.isWifiEnabled()){        }

        text1 =(TextView) findViewById(R.id.text1);
        //info= myWifiManager.getConnectionInfo();
        //text2 =(TextView) findViewById(R.id.text2);
        //myWifiManager.startScan();

    }


public void scan(View View){

    if(myWifiManager.isWifiEnabled()){
        myWifiManager.startScan();
        if(myWifiManager.startScan()){
            //List available APs
            List<ScanResult> scans=myWifiManager.getScanResults();
            if(scans!=null && !scans.isEmpty()){
                for(ScanResult scan:scans){
                    int level =WifiManager.calculateSignalLevel(scan.level,20);
                    text1.setText(level);
                }
            }
        }
    }

    //int level = WifiManager.calculateSignalLevel(info.getRssi(), 5);
    //text1.append("\n\nWiFi Status: " + info.toString());
    //text2.setText(level);

    // List available networks
    //List<WifiConfiguration> configs = wifi.getConfiguredNetworks();


}

}
