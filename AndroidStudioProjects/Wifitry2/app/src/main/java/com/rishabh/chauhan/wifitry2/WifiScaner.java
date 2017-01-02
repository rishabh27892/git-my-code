package com.rishabh.chauhan.wifitry2;

import android.bluetooth.le.ScanResult;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;

import java.util.List;

/**
 * Created by chauhan on 3/3/2016

 */
public class WifiScaner extends BroadcastReceiver {
    private static final String TAG ="WifiscnReceiver";
    MainActivity main;


    public WifiScaner(MainActivity main) {
        super();
        this.main=main;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        List<ScanResult>results=main.wifi.getScanResults();
        ScanResult bestsignal =null;
        for(ScanResult result:results){
            if (bestsignal==null)||WifiManager.compareSignalLevel(bestsignal.)
        }
    }
}
