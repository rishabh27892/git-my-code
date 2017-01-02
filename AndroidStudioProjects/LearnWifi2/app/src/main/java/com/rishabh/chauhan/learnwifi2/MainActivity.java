package com.rishabh.chauhan.learnwifi2;
import android.content.Context;
import android.hardware.input.InputManager;
import android.net.Uri;
import android.view.inputmethod.InputMethodManager;
import android.net.wifi.*;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.view.Menu;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

public class MainActivity extends AppCompatActivity {

    WifiManager wifiManager;
    SystemClock systemClock;
    System system;
    ScanResult scanResult;
    TextView textView, textViewcount, onoff, result1, result2, result3, result4, result5, result6, result7, signal;
    String Result[] = {"result1", "result2", "result3", "result4", "result5", "result6", "result7"};
    // String Result[];
    EditText editText;
    Button button, button0, button1, button2, button3, button4, button5, button6, button7, button8, timecheck;
    String info1, level1, bssid1, abc;
    int counter = 0, counter2 = 0, scansize = 0, ap0 = 0, ap1 = 0, ap2 = 0, ap3 = 0, ap4 = 0, ap5 = 0, ap6 = 0;

    ListView lv, lv2;
    int j = 0, k = 0, l = 0, n = 0, m = 0, o = 0, p = 0, xyz = 0;
    int[][] multi = new int[10][10];
    int[] FullArray = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
    String AP[] = {"18:64:72:df:b9:c3", "18:64:72:df:b9:c1", "18:64:72:df:b9:c0", "9c:1c:12:97:90:40",
            "9c:1c:12:97:90:41", "9c:1c:12:97:90:43", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
    int APV[] = {0, 0, 0, 0, 0, 0, 0};
    int V[] = {0, 0, 0, 0, 0, 0, 0};
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        wifiManager.startScan();
        editText = (EditText) findViewById(R.id.edittext);
        textView = (TextView) findViewById(R.id.wifi_Text);
        signal = (TextView) findViewById(R.id.signal);
        result1 = (TextView) findViewById(R.id.result1);
        result2 = (TextView) findViewById(R.id.result2);
        result3 = (TextView) findViewById(R.id.result3);
        result4 = (TextView) findViewById(R.id.result4);
        result5 = (TextView) findViewById(R.id.result5);
        result6 = (TextView) findViewById(R.id.result6);
        result7 = (TextView) findViewById(R.id.result7);
        button = (Button) findViewById(R.id.Wifi_Button);
        button0 = (Button) findViewById(R.id.button0);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button5 = (Button) findViewById(R.id.button5);
        button6 = (Button) findViewById(R.id.button6);
        button7 = (Button) findViewById(R.id.button7);
        button8 = (Button) findViewById(R.id.button8);
        timecheck = (Button) findViewById(R.id.timeccheck);
        lv = (ListView) findViewById(R.id.your_list_view_id);
        lv2 = (ListView) findViewById(R.id.your_list_view_id2);
        textViewcount = (TextView) findViewById(R.id.count);
        onoff = (TextView) findViewById(R.id.onoff);
        wifiManager.setWifiEnabled(true);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    public void save(View View) {
        info1 = "";
        String empty = "";
        if (wifiManager.isWifiEnabled()) {
            onoff.setText("Wifi:ON");
            List<ScanResult> AP1s = wifiManager.getScanResults();
            for (int i = 0; i < AP1s.size(); i++) {
                scanResult = AP1s.get(i);
                boolean condition = (scanResult == null);
                if (condition == false) {
                    level1 = " ";
                    bssid1 = " ";
                    info1 = scanResult.toString();
                    int b = info1.indexOf("BSSID");
                    int c = info1.indexOf("capabilities");
                    int l = info1.indexOf("level");
                    level1 = info1.substring(l + 8, l + 10);
                    bssid1 = info1.substring(b + 7, c - 2);
                    int value = Integer.parseInt(level1);
                    //Threshold of 70, and limit of 7 access points
                    //if (AP1s.size()<6) {
                    AP[i] = bssid1;
                    //}
                }
            }
            signal.setText("Wifi Aps Saved");
        }
    }

    public void scan1(View View) {
        for (int j = 0; j < 7; j++) {
            APV[j] = 0;
        }
        //int a=7;
        //String Result[]= new String[7];
        //ArrayAdapter<String> adapter2;
        //adapter2 = new ArrayAdapter<String>(this, R.layout.activity_listview, Result);
        //lv2.setAdapter(adapter2);
        ap0 = 0;
        ap1 = 0;
        ap2 = 0;
        ap3 = 0;
        ap4 = 0;
        ap5 = 0;
        ap6 = 0;
        k = 0;
        int div = 2;
        int V[] = {0, 0, 0, 0, 0, 0, 0};
        for (int i = 0; i < div; i++) {
            scan2(View);
            k = 0;
            for (int j = 0; j < 7; j++) {
                k = V[j];
                V[j] = APV[j] + k;
            }


        }
        for (int j = 0; j < 7; j++) {
            V[j] = V[j] / div;

        }
/*
            j=ap0+j;
            k=ap1+k;
            l=ap2+l;
            m=ap3+m;
            n=ap4+n;
            o=ap5+o;
            p=ap6+p;
            if (j==0){
                j=3600;
            }
            if (k==0){
                k=3600;
            }if (l==0){
                l=3600;
            }if (m==0){
                m=3600;
            }if (n==0){
                n=3600;
            }if (o==0){
                o=3600;
            }
            if (p==0){
                p=3600;
            }

        }
*/
        for (int k = 0; k < 7; k++) {
            if (V[k] == 0) {
                V[k] = 90;
            }
            Result[k] = AP[k] + ": " + V[k];
            //multi[counter2][k]=V[k]/40;
        }


        result1.setText(AP[0] + ": " + V[0]);
        result2.setText(AP[1] + ": " + V[1]);
        result3.setText(AP[2] + ": " + V[2]);
        result4.setText(AP[3] + ": " + V[3]);
        result5.setText(AP[4] + ": " + V[4]);
        result6.setText(AP[5] + ": " + V[5]);
        result7.setText(AP[6] + ": " + V[6]);


        multi[counter2][0] = V[0];
        multi[counter2][1] = V[1];
        multi[counter2][2] = V[2];
        multi[counter2][3] = V[3];
        multi[counter2][4] = V[4];
        multi[counter2][5] = V[5];
        multi[counter2][6] = V[6];

        textViewcount.setText("Count:" + counter2);
        counter2 = counter2 + 1;


    }


    public void check(View view) {
        abc = editText.getText().toString();
        xyz = Integer.parseInt(abc);
        int sum = 0;
        for (int i = 0; i < 6; i++) {
            sum = multi[xyz][i] + sum;
        }
        reset(view);

        if (xyz == 0) {
            button0.setText("!!");

        }
        if (xyz == 1) {
            button1.setText("!!");

        }
        if (xyz == 2) {
            button2.setText("!!");

        }
        if (xyz == 3) {
            button3.setText("!!");

        }
        if (xyz == 4) {
            button4.setText("!!");

        }
        if (xyz == 5) {
            button5.setText("!!");

        }
        if (xyz == 6) {
            button6.setText("!!");

        }
        if (xyz == 7) {
            button7.setText("!!");

        }
        if (xyz == 8) {
            button8.setText("!!");

        }


        signal.setText("Signal strength sum for the Mean value of each AP at this point is" + sum + "and the point is" + xyz);
        editText.clearFocus();
        //this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public void scan2(View View) {
        systemClock.sleep(50);

        info1 = " ";
        if (wifiManager.isWifiEnabled()) {
            onoff.setText("Wifi:ON");
            List<ScanResult> AP1s = wifiManager.getScanResults();
            int scanLength = AP1s.size();
            int levelArrayInt[] = new int[scanLength];
            String LevelArrayStr[] = new String[scanLength];
            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, R.layout.activity_listview, LevelArrayStr);
            lv.setAdapter(adapter1);
            scansize = AP1s.size();
            for (int i = 0; i < AP1s.size(); i++) {
                scanResult = AP1s.get(i);
                boolean condition = (scanResult == null);
                if (condition == false) {
                    level1 = " ";
                    bssid1 = " ";
                    int ixb = 0, ixl = 0, ixc = 0;
                    info1 = scanResult.toString();
                    ixb = info1.indexOf("BSSID");
                    ixc = info1.indexOf("capabilities");
                    ixl = info1.indexOf("level");
                    level1 = info1.substring(ixl + 8, ixl + 10);
                    bssid1 = info1.substring(ixb + 7, ixc - 2);
                    int value = Integer.parseInt(level1);
                    //Integer values of level stored
                    levelArrayInt[i] = value;
                    //Final array
                    LevelArrayStr[i] = "level: " + value + " bssid:" + bssid1;
                    //for (int j=0;i<7;i++){
                    if (bssid1.equals(AP[i] + "") && i < 7) {
                        APV[i] = levelArrayInt[i];
                    }

                    //}

/*
                if (bssid1.equals(AP[0]+"")) {
                    ap0 = value;

                }
                if (bssid1.equals(AP[1])) {
                    ap1 = value;

                }
                if (bssid1.equals(AP[2])) {
                    ap2 = value;

                }
                    if (bssid1.equals(AP[3])) {
                        ap3 = value;

                    }
                    if (bssid1.equals(AP[4])) {
                        ap4 = value;

                    }
                    if (bssid1.equals(AP[5])) {
                        ap5 = value;

                    }
                    if (bssid1.equals(AP[6])) {
                        ap6 = value;

                    }
*/
                }
            }
            textView.setText("No of Aps: " + scanLength);
            wifiManager.setWifiEnabled(false);
            onoff.setText("Wifi:OFF");
            counter = counter + 1;
            wifiManager.setWifiEnabled(true);
            wifiManager.startScan();
            onoff.setText("Wifi:ON");
        }
    }

    public void reset(View View) {
        button0.setText("0");
        button1.setText("1");
        button2.setText("2");
        button3.setText("3");
        button4.setText("4");
        button5.setText("5");
        button6.setText("6");
        button7.setText("7");
        button8.setText("8");
    }

    public void update(View View) {
        button0.setText(multi[counter2 - 1][0]);
        button1.setText(multi[counter2 - 1][1]);
        button2.setText(multi[counter2 - 1][2]);
        button3.setText(multi[counter2 - 1][3]);
        button4.setText(multi[counter2 - 1][4]);
        button5.setText(multi[counter2 - 1][5]);
        button6.setText(multi[counter2 - 1][6]);
    }


    public void start_scan(View View) {
        if (wifiManager.isWifiEnabled() == true) {
            wifiManager.setWifiEnabled(false);
            onoff.setText("Wifi:OFF");
        } else {
            wifiManager.setWifiEnabled(true);
            onoff.setText("Wifi:ON");
            wifiManager.startScan();
        }
    }

}