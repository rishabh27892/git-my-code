package com.rishabh.chauhan.final_aplication;
import android.content.Context;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    //private static final String TAG = "MyActivity";
    SensorManager sensorMgr;
    TextView text2, T_value, T_currentposition, T_MESSAGE, T_STATUS, T_ALERT,T_INFO;
    Button R_0, R_1, R_2, R_3, R_4, R_5, R_6, R_7;
    AudioManager audioManager;
    int volume = 0;
    int fwd = 14, lt = 13, rt = 12, bwd = 11;
    RadioGroup R_group;


    WifiManager wifiManager;
    SystemClock systemClock;
    ScanResult scanResult;
    int[] levelArrayInt = new int[50];
    int[] levelArrayInt_check = new int[50];
    int[] levelArrayInt_check_NF = new int[50];


    int counter_text = 0,pos=1000;

    String info1, level1, bssid1;
    int counter = 0, counter2 = 0, counter3 = 0, scansize = 0, length_array = 0, length_array_final = 0, gaus_ref_counter=0;
    ListView lv;

    int[][] multi_rssi = new int[8][50];
    String[][] multi_bssi = new String[8][50];
    int[][] multi_rssi_avg = new int[8][50];
    int[][] multi_rssi_F= new int[8][50];
    int[][] multi_reliability = new int[8][50];
    int[] multi_reliability_check = new int[50];
    int square_check = 0;
    int pos_array[]={0,1,2,3,4,5,6,7,8,9};


    String[] AP = {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
            "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
            "", "", "", "", ""};

    String LevelArrayStr[] = {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
            "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
            "", "", "", "", ""};
    String LevelArrayStr_check[] = {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
            "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
            "", "", "", "", ""};

    String LevelArrayStr_check_avg[] = {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
            "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
            "", "", "", "", ""};
    String LevelArrayStr_txt[] = {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
            "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
            "", "", "", "", ""};
    String LevelArrayStr_txt_F[] = {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
            "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
            "", "", "", "", ""};
    String Diff_str_txt[] = {"", "", "", "", "", "", "", "", "", ""};

    int[] Diffx = new int[50];
    int[] Diff = new int[50];
    int[] Diff_NF = new int[50];
    int[] savetextfile=new int[50];
    int[] savetextfile_NF=new int[50];

    int[] Count_Diff = new int[50];
    int[] Diff_rootx = new int[50];
    int[] Txt_array_rssi = new int[50];
    int counter_data=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        wifiManager.startScan();
        T_ALERT=(TextView) findViewById(R.id.T_ALERT);

        T_currentposition = (TextView) findViewById(R.id.T_currentposition);
        T_STATUS = (TextView) findViewById(R.id.T_STATUS);
        T_MESSAGE = (TextView) findViewById(R.id.T_MESSAGE);
        T_INFO = (TextView) findViewById(R.id.T_INFO);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        lv = (ListView) findViewById(R.id.list);
        wifiManager.setWifiEnabled(true);
        Arrays.fill(levelArrayInt, 8000);
        text2 = (TextView) findViewById(R.id.text2);

    }
    ///////////////////////MAIN PROGRAM///////////////////
                     ///DIRECT ACTION STEPS////
    void back(){
        move_volume(lt);
        move_volume(fwd);
    }
    void forward(){
        move_volume(fwd);

    }
    public void go(View View) {
        boolean satisfied = false;
        while (satisfied == false) {
            systemClock.sleep(1500);
            int check_pos=check(View);
//IF THE PREQUIRED POSITION IS REACHED, THE CONDITION SATISFIED WILL BECOME TRUE
            if(check_pos>pos){
                back();
            }
            if(check_pos<pos){
                forward();

            }

            if(check_pos==pos){
                satisfied=true;
                T_MESSAGE.setText("MSG: Destination reached");
            }
            else{
                //code to be written
            }
        }
    }

    public void AP(View View) {
        data(View);
    }
    //Commands to move the vehicle
    public void fwd(View View) {
        move_volume(fwd);
    }

    public void bwd(View View) {
        move_volume(bwd);
    }

    public void lt(View View) {
        move_volume(lt);
    }

    public void rt(View View) {
        move_volume(rt);
    }

    public void store(View View) {
        scan_final(View);
    }

    public void show_current(View View) {
        check(View);
    }





//////////////////////BACKGROUND STEPS////////
    //(STEP 1)for making list of available APs, if new AP is available then add to the list.

    public void data(View View) {
        T_ALERT.setText("ALRT: STATUS CHANGED");
        wifiManager.startScan();
        info1 = "";
        if (wifiManager.isWifiEnabled()) {

            List<ScanResult> AP1s = wifiManager.getScanResults();
            for (int i = 0; i < AP1s.size(); i++) {
                boolean condition_present = false;
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
                    if (counter3 == 0 && value < 90) {
                        AP[i] = bssid1;
                        length_array = AP1s.size();
                    }
                    if (counter3 != 0 && value < 90) {
                        int z;
                        {

                            for (z = 0; z < length_array; z++) {
                                if (bssid1.equals(AP[z])) {
                                    condition_present = true;
                                    break;
                                }
                            }


                            if (condition_present == true) { //IF Z IS LESS THEN ARRAY_LENGTH, NO NEW ELEMENT FOUND. DO NOT ADD TO ARRAY AP[]
                                T_MESSAGE.setText("MSG: NO NEW ARRAY FOUND");
                                T_ALERT.setText("ALRT: STATUS NOT CHANGED");
                            }
                            //IF CONDITION_PRESENT IS FALSE, IT MEANS THE FOR LOOP DID NOT MATCH AND MAKE THE CONDITION_PRESENT TRUE IN ANY OF THE ITERATIONS
                            // (BSSID WAS NEVER FOUND TO BE EQUAL TO ANY ELEMENT IN ARRAY)
                            if (condition_present == false) {
                                T_MESSAGE.setText("MSG: NEW ARRAY FOUND");
                                AP[length_array] = bssid1;
                                length_array = 1 + length_array;
                            }

                        }
                    }
                }
            }
            T_STATUS.setText("STS: AP COUNT IS " + length_array);
            counter3 = counter3 + 1;
            //in order to keep the value of length array in a seperate safe variable
            length_array_final = length_array;
        }
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, R.layout.activity_listview, AP);
        lv.setAdapter(adapter1);

        counter_data++;
        if(counter_data==4){
            savetextfile2(View);
        }
    }


    //(STEP 2) call teh scan function multiple times and update the values in a 2D array, 1 click for one coordinate

    public void scan_final(View View) {

        Arrays.fill(levelArrayInt, 8000);
        if (counter2 < 8) {
            String LevelArrayStr_new[] = {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
                    "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
                    "", "", "", "", ""};
            String LevelArrayStr_new_F[] = {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
                    "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
                    "", "", "", "", ""};
            //
            int no_observation = 100;
            for (int j = 0; j < no_observation; j++) {

                scan2(View);
                for (int i = 0; i < length_array_final; i++) {
                    if (counter2 < 8) {
                        //WITH FILTER/////
                        if (levelArrayInt[i] >= 3000 && levelArrayInt[i] < 8000) {
                            multi_reliability[counter2][i]++;
                            multi_rssi_F[counter2][i] = multi_rssi_F[counter2][i] + levelArrayInt[i];
                        }

                        //WITHOUT FILTER

                        multi_rssi[counter2][i] = multi_rssi[counter2][i] + levelArrayInt[i];
                        //////
                    }
                }
            }

            for (int i = 0; i < length_array_final; i++) {
                multi_rssi[counter2][i]= multi_rssi[counter2][i]/100;
                multi_bssi[counter2][i] = LevelArrayStr[i];
                if (multi_reliability[counter2][i] != 0) {

                    multi_rssi_avg[counter2][i] = multi_rssi_F[counter2][i] / multi_reliability[counter2][i];
                    if(multi_rssi_avg[counter2][i]<3000||multi_rssi_avg[counter2][i]>8000){
                        multi_rssi_avg[counter2][i]=8000;
                    }
                } else {
                    multi_rssi_avg[counter2][i] = 8000;
                }


            }
//TO POPULATE ANOTHER 2D ARRAY FOR THE LIST TO DISPLAY
            //NON FILTERED DATA OBSERVATION
            for (int i = 0; i < length_array_final; i++) {
                if (counter2 < 8) {
                    Txt_array_rssi[i] = multi_rssi[counter2][i];
                    LevelArrayStr_new[i] = multi_bssi[counter2][i] + ": " + multi_rssi[counter2][i];
                    savetextfile_NF[i]=multi_rssi[counter2][i];
                }
            }

            //FILTERED DATA OBSERVATION
            for (int i = 0; i < length_array_final; i++) {
                if (counter2 < 8) {
                    LevelArrayStr_new_F[i] = multi_bssi[counter2][i] + ": " + multi_rssi_avg[counter2][i];
                    Txt_array_rssi[i] = multi_rssi_avg[counter2][i];
                    savetextfile[i]=multi_rssi_avg[counter2][i];
                }
            }
            savetextfile(View);
            savetextfile=new int[50];
            savetextfile_NF=new int[50];



            for (int i = 0; i < length_array_final; i++) {
                LevelArrayStr_txt_F[i] = LevelArrayStr_new_F[i];
                LevelArrayStr_txt[i]=LevelArrayStr_new[i];
            }
//DISPLAY THE RSSI AND BSSI FOR THE SPECIFIC POINT
            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, R.layout.activity_listview, LevelArrayStr_new_F);
            lv.setAdapter(adapter1);
            T_MESSAGE.setText("MSG: GPOS: " + counter2);
            counter2++;
            //COUNTER2 DENOTES THE POINT NUMBER, OR THE COORDINATE(0-8)
        }


    }


    // (STEP 2.1) scans all the available APs to create a database of signal strength
    public void scan2(View View) {
        wifiManager.startScan();
        Boolean yes_no = false;
        info1 = " ";
        if (wifiManager.isWifiEnabled()) {
            List<ScanResult> AP2s = wifiManager.getScanResults();
            scansize = AP2s.size();
            for (int i = 0; i < AP2s.size(); i++) {
                scanResult = AP2s.get(i);
                boolean condition = (scanResult == null);
                if (condition == false) {

                    level1 = " ";
                    bssid1 = " ";
                    info1 = scanResult.toString();
                    int ixb = info1.indexOf("BSSID");
                    int ixc = info1.indexOf("capabilities");
                    int ixl = info1.indexOf("level");
                    level1 = info1.substring(ixl + 8, ixl + 10);
                    bssid1 = info1.substring(ixb + 7, ixc - 2);
                    int value = Integer.parseInt(level1);

                    for (int l = 0; l < length_array_final; l++) {
                        if (bssid1.equals(AP[l])) {
                            yes_no = true;

                            //To make sure to assume all the values above 80=80.
                            if (value < 80 && value >= 30) {
                                //THE RSSI VALUE
                                levelArrayInt[l] = 100 * value;
                                //THE BSSI VALUE
                                LevelArrayStr[l] = bssid1;
                            }
                            if (value >= 80 && value < 30) {
                                //THE RSSI VALUE
                                levelArrayInt[l] = 8000;
                                //THE BSSI VALUE
                                LevelArrayStr[l] = bssid1;
                            }
                        }
                    }
                }
            }

        }

        counter = counter + 1;
    }
    //(STEP3) MATCHING

    public int check(View view) {
        int[] high_freq = new int[5];
        Arrays.fill(levelArrayInt_check, 8000);
        int[] levelArrayInt_check_loop = new int[50];
        int[] levelArrayInt_check_loop_NF = new int[50];
        int[] levelArrayInt_check_loop_avg = new int[50];


        int difference, final_difference, final_differencex_sqr,differencex, final_differencex,difference_NF,final_difference_NF;
        //TO STORE THE DIFFERENCE SUM OF THE OBSERVED APs TO THE COLLECTED APs
        String Diff_str[] = {"", "", "", "", "", "", "", "", "", "", ""};
        systemClock.sleep(50);

        //////
        int no_observation = 100;
        for (int j = 0; j < no_observation; j++) {

            wifiManager.startScan();
            info1 = " ";
            if (wifiManager.isWifiEnabled()) {

                List<ScanResult> AP2s = wifiManager.getScanResults();
                scansize = AP2s.size();
                for (int i = 0; i < AP2s.size(); i++) {
                    scanResult = AP2s.get(i);
                    boolean condition = (scanResult == null);
                    if (condition == false) {
                        level1 = " ";
                        bssid1 = " ";
                        info1 = scanResult.toString();
                        int ixb = info1.indexOf("BSSID");
                        int ixc = info1.indexOf("capabilities");
                        int ixl = info1.indexOf("level");
                        level1 = info1.substring(ixl + 8, ixl + 10);
                        bssid1 = info1.substring(ixb + 7, ixc - 2);
                        int value = (Integer.parseInt(level1));
                        for (int l = 0; l < length_array_final; l++) {
                            if (bssid1.equals(AP[l])) {
                                if (value < 80 && value >= 30) {
                                    //THE RSSI VALUE
                                    levelArrayInt_check[l] = 100 * value;
                                    //THE BSSI VALUE
                                    multi_reliability_check[l]++;
                                }
                                if (value >= 80 && value < 30) {
                                    //THE RSSI VALUE
                                    levelArrayInt_check[l] = 8000;
                                    //THE BSSI VALUE;
                                }
                                levelArrayInt_check_NF[l] =value;
                                //THE BSSI VALUE
                                LevelArrayStr_check[l] = bssid1;
                            }
                        }
                    }
                }
            }

            for (int i = 0; i < length_array_final; i++) {


                levelArrayInt_check_loop[i] = levelArrayInt_check_loop[i] + levelArrayInt_check[i];
                levelArrayInt_check_loop_NF[i] = levelArrayInt_check_loop_NF[i] + levelArrayInt_check_NF[i];
                savetextfile_NF[i]=levelArrayInt_check_loop_NF[i];
            }

        }

        for (int i = 0; i < length_array_final; i++) {
            if (multi_reliability_check[i] != 0) {
                levelArrayInt_check_loop_avg[i] = levelArrayInt_check_loop[i] / multi_reliability_check[i];
                if(levelArrayInt_check_loop_avg[i]>8000||levelArrayInt_check_loop_avg[i]<3000){
                    levelArrayInt_check_loop_avg[i]=8000;
                }
                LevelArrayStr_check_avg[i] = " " + levelArrayInt_check_loop_avg[i];

            } else {
                levelArrayInt_check_loop[i] = 8000;
                LevelArrayStr_check_avg[i] = " " + levelArrayInt_check_loop_avg[i];
            }


        }
        for (int i = 0; i < length_array_final; i++) {
            savetextfile[i]= levelArrayInt_check_loop_avg[i];
        }
        savetextfile(view);
        savetextfile=new int[50];
        savetextfile_NF=new int[50];

        for (int k = 0; k < 8; k++) {
            final_difference = 0;
            final_differencex = 0;
            final_difference_NF=0;
            final_differencex_sqr = 0;
            int Counter_difference = 0;
            difference = 0;
            differencex = 0;
            difference_NF=0;

            for (int l = 0; l < length_array_final; l++) {

                //to ensure that the value of l doesnt go beyond 50
                if (l < 50) {
                    ///WITH RELIABILITY FACTOR
                    if (levelArrayInt_check_loop_avg[l] < 8000) {
                        if (levelArrayInt_check_loop_avg[l] > 3000) {
                            if (multi_rssi_avg[k][l] < 8000) {
                                if (multi_rssi_avg[k][l] > 3000) {
                                    // to ensure that the difference is not negative
                                    if (levelArrayInt_check_loop_avg[l] <= multi_rssi_avg[k][l]) {
                                        differencex = levelArrayInt_check_loop_avg[l] - multi_rssi_avg[k][l];
                                        Counter_difference++;
                                    }
                                    if (multi_rssi_avg[k][l] > levelArrayInt_check_loop_avg[l]) {
                                        differencex = multi_rssi_avg[k][l] - levelArrayInt_check_loop_avg[l];
                                        Counter_difference++;
                                    }

                                }

                            }

                        }

                    }
                    //////

                    ///WITHOUT RELIABILITY FACTOR
                    if (levelArrayInt_check_loop_NF[l] <= multi_rssi[k][l]) {
                        //WITHOUT FILTER
                        difference_NF = levelArrayInt_check_loop_NF[l] - multi_rssi[k][l];
                        //WITHOUT FILTER(OBSERVATION TIME)
                        difference = levelArrayInt_check_loop_avg[l] - multi_rssi_avg[k][l];
                    }
                    if (multi_rssi[k][l] >= levelArrayInt_check_loop_avg[l]) {
                        //WITHOUT FILTER
                        difference_NF = multi_rssi[k][l] - levelArrayInt_check_loop_NF[l];
                        //WITHOUT FILTER
                        difference = multi_rssi_avg[k][l] - levelArrayInt_check_loop_avg[l];

                    }
                    /////
                    //NO FILTER, NO RELIABILITY FACTOR,DIFFERENCE
                    final_difference_NF = final_difference_NF + difference_NF;
                    //FILTER, NO RELIABILITY FACTOR,DIFFERENCE
                    final_difference=final_difference+difference;
                    //FILTER, RELIABILITY FACTOR,DIFFERENCE
                    final_differencex = final_differencex + differencex;
                    //FILTER,RELIABILITY FACTOR,SQR DIFF
                    square_check = differencex * differencex;
                    final_differencex_sqr = final_differencex_sqr + square_check;


                }

            }
            Count_Diff[k] = Counter_difference;
            //final_difference=final_difference/Counter_difference;
            //assigning final_differnece in an array, this will contain one value of data deviation of the new readins with the database
            double root = Math.sqrt(final_differencex_sqr);
            double root_abs = Math.abs(root);
            int final_root_abs = (int) root_abs;
            if (Count_Diff[k] != 0) {
                Diff_rootx[k] = final_root_abs / Count_Diff[k];
                Diffx[k] = final_differencex / Count_Diff[k];
                Diff[k]=final_difference;
                Diff_NF[k]=final_difference_NF/100;
            }
        }
        for (int i = 0; i < 8; i++) {
            Diff_str[i] = "point: " + i + " SQR RT(R n F): " + Diff_rootx[i] + " DIFF(R n F): " + Diffx[i]+ " DIFF(R n NF)"+Diff_NF[i];
        }

        int number = 1000000,location_d_filter=0, number_diff = 0, location_d = 0, location2 = 0, locationx_d = 1000000, x_temp = 0,locationx_sqr = 1000000;
/////// STEP TO REARRANGE THE CRITERIA TO SELECT THE BEST OUTPUT
        //to check which 5 values of k has least deviation in the form of high priority difference

        //LOCATION BY SIMPLE DIFFERENCE WITHOUT FILTER(R)
        for (int j = 0; j < 8; j++) {
            //GETTING THE ONE WITH THE LEAST DIFFERENCE
            if (number > Diff_NF[j]) {
                number = Diff_NF[j];
                location_d = j + 1;
            }
        }
        //LOCATION BY SIMPLE DIFFERENCE WITH FILTER(R)
        number = 1000000;
        for (int j = 0; j < 8; j++) {
            //GETTING THE ONE WITH THE LEAST DIFFERENCE
            if (number > Diffx[j]) {
                number = Diffx[j];
                location_d_filter = j + 1;
            }
        }

//RANK METHOD DIFFERENCE
        {


            int[] final_numberx = new int[50];

            int numberx = 0;
            for (int j = 0; j < 8; j++) {
                if (numberx < Count_Diff[j]) {
                    numberx = Count_Diff[j];
                    final_numberx[0] = j;
                }
            }
            numberx = 0;
            for (int j = 0; j < 8; j++) {
                if (j != final_numberx[0]) {
                    if (numberx < Count_Diff[j]) {
                        numberx = Count_Diff[j];
                        final_numberx[1] = j;
                    }
                }
            }
            numberx = 0;
            for (int j = 0; j < 8; j++) {
                if (j != final_numberx[0] || j != final_numberx[1]) {
                    if (numberx < Count_Diff[j]) {
                        numberx = Count_Diff[j];
                        final_numberx[2] = j;
                    }
                }

            }
            numberx = 0;
            for (int j = 0; j < 8; j++) {
                if (j != final_numberx[0] || j != final_numberx[1] || j != final_numberx[2]) {
                    if (numberx < Count_Diff[j]) {
                        numberx = Count_Diff[j];
                        final_numberx[3] = j;
                    }
                }

            }
            numberx = 0;
            for (int j = 0; j < 8; j++) {
                if (j != final_numberx[0] || j != final_numberx[1] || j != final_numberx[2] || j != final_numberx[3]) {
                    if (numberx < Count_Diff[j]) {
                        numberx = Count_Diff[j];
                        final_numberx[4] = j;
                    }
                }

            }
            number=1000000;
            for (int j = 0; j < 5; j++) {
                //GETTING THE ONE WITH THE LEAST DIFFERENCE
                if (number > Diffx[final_numberx[j]]) {
                    number = Diffx[final_numberx[j]];
                    locationx_d = final_numberx[j] + 1;
                }
            }
        }


        //RANK METHOD SQUARE DIFFERENCE
        {
            int[] final_numberx_sqr = new int[50];

            int numberx = 0;
            for (int j = 0; j < 8; j++) {
                if (numberx < Count_Diff[j]) {
                    numberx = Count_Diff[j];
                    final_numberx_sqr[0] = j;
                }
            }
            numberx = 0;
            for (int j = 0; j < 8; j++) {
                if (j != final_numberx_sqr[0]) {
                    if (numberx < Count_Diff[j]) {
                        numberx = Count_Diff[j];
                        final_numberx_sqr[1] = j;
                    }
                }
            }
            numberx = 0;
            for (int j = 0; j < 8; j++) {
                if (j != final_numberx_sqr[0] || j != final_numberx_sqr[1]) {
                    if (numberx < Count_Diff[j]) {
                        numberx = Count_Diff[j];
                        final_numberx_sqr[2] = j;
                    }
                }

            }
            numberx = 0;
            for (int j = 0; j < 8; j++) {
                if (j != final_numberx_sqr[0] || j != final_numberx_sqr[1] || j != final_numberx_sqr[2]) {
                    if (numberx < Count_Diff[j]) {
                        numberx = Count_Diff[j];
                        final_numberx_sqr[3] = j;
                    }
                }

            }
            numberx = 0;
            for (int j = 0; j < 8; j++) {
                if (j != final_numberx_sqr[0] || j != final_numberx_sqr[1] || j != final_numberx_sqr[2] || j != final_numberx_sqr[3]) {
                    if (numberx < Count_Diff[j]) {
                        numberx = Count_Diff[j];
                        final_numberx_sqr[4] = j;
                    }
                }

            }
            number=1000000;
            for (int j = 0; j < 5; j++) {
                //GETTING THE ONE WITH THE LEAST DIFFERENCE
                if (number > Diffx[final_numberx_sqr[j]]) {
                    number = Diffx[final_numberx_sqr[j]];
                    locationx_sqr = final_numberx_sqr[j] + 1;
                }
            }

        }
        T_MESSAGE.setText("MSG:L_D(NF): " + location_d);
        T_ALERT.setText("ALRT:L_R(D): " + locationx_d);
        T_STATUS.setText("STS:L_R(SQR)"+ locationx_sqr);
        T_INFO.setText("INFO:L_D(F)"+ location_d_filter);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.activity_listview, Diff_str);
        lv.setAdapter(adapter2);

        for (int i = 0; i < 8; i++) {
            Diff_str_txt[i] = Diff_str[i]+" ";
        }
        savetextfile(view);
        savetextfile(view);
        return location2;
    }
    //SAVE OBSERVATIONS IN THE FORM OF TEXT FILES IN THE PHONE
    public void savetextfile(View view){
        String[] numbers={ "", "", "", "", "", "", "", "", "", "","", "", "", "", "", "", "", "", "", "",
                "", "", "", "", "", "", "", "", "", "","", "", "", "", "", "", "", "", "", "","", "", "", "", "",
                "", "", "", "", ""};
        String[] numbers_NF={ "", "", "", "", "", "", "", "", "", "","", "", "", "", "", "", "", "", "", "",
                "", "", "", "", "", "", "", "", "", "","", "", "", "", "", "", "", "", "", "","", "", "", "", "",
                "", "", "", "", ""};

        if (counter_text==0){
            for(int i=0;i<length_array_final;i++){
                numbers[i]=AP[i]+",   ";
            }
        }
        if (counter_text>0&&counter_text<9){
            for(int i=0;i<length_array_final;i++){
                numbers[i]=savetextfile[i]+"  ";
                numbers_NF[i]=savetextfile_NF[i]+"  ";

            }
        }
        if (counter_text==9){
            for(int i=0;i<length_array_final;i++){
                numbers[i]=savetextfile[i]+"  ";
                numbers_NF[i]=savetextfile_NF[i]+"  ";
            }
        }
        if (counter_text==10){
            for(int i=0;i<8;i++){
                numbers[i]=Diff_str_txt[i]+"  ";
            }
        }

        if (counter_text==11){
            for(int i=0;i<8;i++){
                numbers[i]=Count_Diff[i]+"  ";
            }
        }
        String file_name="file_num- "+counter_text;
        FileOutputStream outputStream,outputStream_NF;
        String mPath_NF = Environment.getExternalStorageDirectory().toString()+"/Pictures/"+file_name+"_NF.txt";
        String mPath = Environment.getExternalStorageDirectory().toString()+"/Pictures/"+file_name+".txt";
        File text_NF=new File(mPath_NF);
        File text=new File(mPath);
        try {
            outputStream_NF =new FileOutputStream(text_NF);
            outputStream =new FileOutputStream(text);

            for (String s : numbers) {

                outputStream.write(s.getBytes());
            }
            outputStream.flush();
            outputStream.close();
            for (String s_NF : numbers_NF) {

                outputStream_NF.write(s_NF.getBytes());
            }
            outputStream_NF.flush();
            outputStream_NF.close();
        }
        catch(Exception e){
            e.printStackTrace();

        }

        counter_text++;
    }
    public void savetextfile2(View view){
        String[] numbers={ "", "", "", "", "", "", "", "", "", "","", "", "", "", "", "", "", "", "", "",
                "", "", "", "", "", "", "", "", "", "","", "", "", "", "", "", "", "", "", "","", "", "", "", "",
                "", "", "", "", ""};
        String[] numbers_NF={ "", "", "", "", "", "", "", "", "", "","", "", "", "", "", "", "", "", "", "",
                "", "", "", "", "", "", "", "", "", "","", "", "", "", "", "", "", "", "", "","", "", "", "", "",
                "", "", "", "", ""};

        if (counter_text==0){
            for(int i=0;i<length_array_final;i++){
                numbers[i]=AP[i]+",   ";
            }
        }
        if (counter_text>0&&counter_text<9){
            for(int i=0;i<length_array_final;i++){
                numbers[i]=savetextfile[i]+"  ";


            }
        }
        /*
        if (counter_text==9){
            for(int i=0;i<length_array_final;i++){
                numbers[i]=savetextfile[i]+"  ";
                numbers_NF[i]=savetextfile_NF[i]+"  ";
            }
        }
        if (counter_text==10){
            for(int i=0;i<8;i++){
                numbers[i]=Diff_str_txt[i]+"  ";
            }
        }

        if (counter_text==11){
            for(int i=0;i<8;i++){
                numbers[i]=Count_Diff[i]+"  ";
            }
        }
        */
        String file_name="file_num- "+counter_text;
        FileOutputStream outputStream,outputStream_NF;
        String mPath_NF = Environment.getExternalStorageDirectory().toString()+"/Pictures/"+file_name+"_NF.txt";
        String mPath = Environment.getExternalStorageDirectory().toString()+"/Pictures/"+file_name+".txt";
        File text_NF=new File(mPath_NF);
        File text=new File(mPath);
        try {
            outputStream_NF =new FileOutputStream(text_NF);
            outputStream =new FileOutputStream(text);

            for (String s : numbers) {

                outputStream.write(s.getBytes());
            }
            outputStream.flush();
            outputStream.close();
            for (String s_NF : numbers_NF) {

                outputStream_NF.write(s_NF.getBytes());
            }
            outputStream_NF.flush();
            outputStream_NF.close();
        }
        catch(Exception e){
            e.printStackTrace();

        }

        counter_text++;
    }





    public  void more(){
        audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_RAISE,AudioManager.FLAG_PLAY_SOUND);
        int volume=audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        T_ALERT.setText("ALRT:vol: "+volume);
    }
    public  void less(){
        audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_LOWER,AudioManager.FLAG_PLAY_SOUND);
        int volume=audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        T_ALERT.setText("ALRT:vol: "+volume);
    }
    public void move_volume(int vol_lev){
            for(int i=0;i<15;i++){
                more();
            }
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,vol_lev,AudioManager.FLAG_PLAY_SOUND);
        volume=audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        T_ALERT.setText("ALRT:vol: "+volume);
        systemClock.sleep(3000);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,0,AudioManager.FLAG_PLAY_SOUND);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


}

