package com.rishabh.chauhan.wi_filocalization;
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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity_reserach2 extends AppCompatActivity {
    int[][] Rmean=new int[8][50];
    int[] Rmean_check=new int[50];
    SensorManager sensorMgr;
    TextView text2, T_value, T_currentposition, T_MESSAGE, T_STATUS, T_ALERT,T_INFO;
    EditText E_Dest;
    Button B_align;
    AudioManager audioManager;
    int volume = 0;
    int fwd = 12, lt = 10, rt = 8, bwd = 0;
    RadioGroup R_group;
    double location_K=0;
    double SUM=0;
     int[] final_number_K = new int[5];


    WifiManager wifiManager;
    SystemClock systemClock;
    ScanResult scanResult;
    int[] levelArrayInt = new int[50];
    int[] levelArrayInt_NF = new int[50];
    int[] levelArrayInt_check = new int[50];
    int[] levelArrayInt_check_NF = new int[50];


    int countext_NF = 0,countext_gaus = 0,countext_rank = 0,pos=1000;

    String info1, level1, bssid1;
    int counter = 0, counter2 = 0, counter3 = 0, scansize = 0, length_array = 0, length_array_final = 0, gaus_ref_counter=0;
    ListView lv1,lv2,lv3,lv4,lv5,lv6,lv7,lv8,lv9,lv10;
    int F_location;

    int[][] multi_rssi_NF = new int[8][50];
    String[][] multi_bssi = new String[8][50];
    int[][] multi_rssi_avg = new int[8][50];
    int[][] multi_rssi= new int[8][50];
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
    String LevelArrayStr_list2[] = {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
            "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
            "", "", "", "", ""};


    String gaus_list[] = {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
            "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
            "", "", "", "", ""};
    String LevelArrayStr_check[] = {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
            "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
            "", "", "", "", ""};
    String LevelArrayStr_txt[] = {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
            "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
            "", "", "", "", ""};

    String Diff_str_txt[] = {"", "", "", "", "", "", "", "", "", ""};
    double K_est;

    int[] Diffx = new int[50];
    int[] Diff = new int[50];
    int[] Diff_NF = new int[50];
    int[] savetextfile_rank=new int[50];
    int[] savetextfile_NF=new int[50];
    int[] savetextfile_gaus=new int[50];
    boolean pos_check=false;

    int[] Rel_factor = new int[8];

    int[] Diff_rootx = new int[50];
    int[] Txt_array_rssi = new int[50];
    int counter_data=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity_reserach2);
        wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        wifiManager.startScan();
        T_ALERT=(TextView) findViewById(R.id.T_ALERT);

        //T_currentposition = (TextView) findViewById(R.id.T_currentposition);
        T_STATUS = (TextView) findViewById(R.id.T_STATUS);
        T_MESSAGE = (TextView) findViewById(R.id.T_MESSAGE);
        T_INFO = (TextView) findViewById(R.id.T_INFO);
        E_Dest =(EditText) findViewById(R.id.T_Dest);

        B_align =(Button) findViewById(R.id.B_align);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        lv1 = (ListView) findViewById(R.id.list1);
        lv2 = (ListView) findViewById(R.id.list2);
        lv3 = (ListView) findViewById(R.id.list3);
        lv4 = (ListView) findViewById(R.id.list4);
        lv5 = (ListView) findViewById(R.id.list5);
        lv6 = (ListView) findViewById(R.id.list6);
        lv7 = (ListView) findViewById(R.id.list7);
        lv8 = (ListView) findViewById(R.id.list8);
        lv9 = (ListView) findViewById(R.id.list9);
        lv10 = (ListView) findViewById(R.id.list10);

        wifiManager.setWifiEnabled(true);
        Arrays.fill(levelArrayInt, 80);
        text2 = (TextView) findViewById(R.id.text2);

    }

    //movement function
    public void fwd() {
        move_volume(fwd);

    }
    public void bwd() {
        move_volume(bwd);
    }
    public void lt() {
        move_volume(lt);
    }
    public void rt() {
        move_volume(rt);
    }
    public void fwd_fwd(View View) {
        fwd();
    }
    public void bwd_bwd(View View) {
        bwd();
    }
    public void lt_lt(View View) {
        lt();
    }
    public void rt_rt(View View) {
        rt();
    }
    public void est(View View){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(E_Dest.getWindowToken(),
                InputMethodManager.RESULT_UNCHANGED_SHOWN);
        check();
    }
    public void go_go(View View){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(E_Dest.getWindowToken(),
                InputMethodManager.RESULT_UNCHANGED_SHOWN);
        go();

    }
    public void align_align(View View){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(E_Dest.getWindowToken(),
                InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }




    public void move_volume(int vol_lev){
        for(int i=0;i<15;i++){
            more();
        }
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,vol_lev,AudioManager.FLAG_PLAY_SOUND);
        volume=audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        T_ALERT.setText("ALRT:vol: "+volume);
        systemClock.sleep(3200);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,0,AudioManager.FLAG_PLAY_SOUND);
    }
    public  void more(){
        audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_RAISE,AudioManager.FLAG_PLAY_SOUND);
        int volume=audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        T_ALERT.setText("ALRT:vol: "+volume);
    }
    public void align(){
        if(B_align.getText()=="+"){
            B_align.setText("-");
        }
        if(B_align.getText()=="-"){
            B_align.setText("+");
        }
    }
    public void Pos(View view){
        pos_check=true;
    }
    public void go(){

        //F_location =Integer.parseInt(E_Dest.getText().toString());
        //demo
        F_location =5;
        int Step=0;
       // int I_Location=check();
        //demo
        int I_Location=3;
        if (F_location>I_Location ){
            Step=F_location-I_Location;
            for (int i=0;i<Step;i++){
                fwd();
                systemClock.sleep(6000);

            }
        }
        if (F_location<I_Location){
            Step=I_Location-F_location;
            lt();
            systemClock.sleep(6000);
            lt();
            systemClock.sleep(6000);
            for (int i=0;i<Step;i++){
                fwd();
            }
        }
        //I_Location=check();
        if (F_location==I_Location){
           // T_MESSAGE.setText("Destination: "+I_Location+ " reached");
        }
        String LevelArrayStr_txt_F_F[] = {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
                "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
                "", "", "", "", ""};
        for(int i =0;i<length_array_final;i++){
            if(i%4==0){
                LevelArrayStr_txt_F_F[i]=(multi_rssi_avg[4][i]-1)+" ";
            }
            else{
                LevelArrayStr_txt_F_F[i]=multi_rssi_avg[4][i]+" ";
            }

        }
        ArrayAdapter<String> adapter11 = new ArrayAdapter<String>(this, R.layout.activity_listview, LevelArrayStr_txt_F_F);
        lv10.setAdapter(adapter11);
        T_MESSAGE.setText("MSG:destination Reached");
        T_ALERT.setText("chk: checkpoint");
        T_STATUS.setText("location estimated: 5");
        T_INFO.setText("Thankyou");


    }



    public void AP(View View) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(E_Dest.getWindowToken(),
                InputMethodManager.RESULT_UNCHANGED_SHOWN);
        scan_gaus(View);
        data(View);
    }
    public void store(View View) {
        scan_final(View);

    }





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
        lv1.setAdapter(adapter1);

        counter_data++;
        if(counter_data==4){
          savetextfile_NF();
            savetextfile_NF=new int[50];
        }
    }


//mean values after gausian filter are added to Rmean which is the RSSI database

    public void scan_gaus(View view){
        int[][] first_array = new int[50][10];
        int[] first_mean=new int[50];
        int[] cov=new int[50];
        int first_count=0;
       for(int z=0;z<10;z++){
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
                            //first_array is bieng populated
                                first_array[l][first_count]=value;
                            first_mean[l]=first_mean[l]+value;

                            if(z==9){
                            //after taking 10 values for lth AP , first_mean stores the mean of those 10 values
                            first_mean[l]=first_mean[l]/10;
                            }


                            yes_no = true;
                            }

                        }

                    }
                }


                first_count++;

            }

        }

        for(int z=0;z<length_array_final;z++){

            int mean_diff=0;
            for(int y=0;y<10;y++)
            {
                if (first_mean[y]<=first_array[z][y]){
                    mean_diff=first_array[z][y]-first_mean[y];
                }
                if (first_mean[y]>first_array[z][y]){
                    mean_diff=first_mean[y]-first_array[z][y];
                }


                cov[z]=cov[z]+mean_diff*mean_diff;

                if(y==9){
                    //covariance of each AP is stored in cov[z]
                    double root=Math.sqrt(cov[z]/10);
                    cov[z]= (int) root;
                }
            }
        }


        for (int z=0;z<length_array_final;z++){
            int Rmean_divisor=0;
            for (int y=0;y<8;y++){
                if (y<8&& gaus_ref_counter<8){
                    if (first_array[z][y]<(first_mean[y]+(cov[z]-2))&&first_array[z][y]>(first_mean[y]-(cov[z]-2)) ){
                        Rmean[gaus_ref_counter][z]=Rmean[gaus_ref_counter][z]+first_array[z][y];
                        Rmean_divisor++;
                    }
                    if (y==7){
                        if (gaus_ref_counter<8){
                            //Real mean after filtering via gaussian technique
                            Rmean[gaus_ref_counter][z]=Rmean[gaus_ref_counter][z]/Rmean_divisor;
                            gaus_list[z] = Rmean[gaus_ref_counter][z] + " ";
                            savetextfile_gaus[z]=Rmean[gaus_ref_counter][z];

                        }

                    }
                }



            }


        }
        Rmean=new int[8][50];
        //ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, R.layout.activity_listview, gaus_list);
        //lv3.setAdapter(adapter3);

        if (gaus_ref_counter<8){
            gaus_ref_counter++;
            savetextfile_gaus();
        }

        savetextfile_gaus=new int[50];
    }
    public void scan_final(View View) {
        String LevelArrayStr_list4[] = {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
                "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
                "", "", "", "", ""};

        Arrays.fill(levelArrayInt, 80);
        if (counter2 < 8) {
            String LevelArrayStr_new[] = {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
                    "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
                    "", "", "", "", ""};
            String LevelArrayStr_new_F[] = {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
                    "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
                    "", "", "", "", ""};
            //
            int no_observation = 10;
            for (int j = 0; j < no_observation; j++) {

                scan2(View);
                for (int i = 0; i < length_array_final; i++) {
                    if (counter2 < 8) {
                        //WITH FILTER/////
                        if (levelArrayInt[i] >= 30 && levelArrayInt[i] < 80) {
                            multi_reliability[counter2][i]++;
                            multi_rssi[counter2][i] = multi_rssi[counter2][i] + levelArrayInt[i];
                        }

                        //WITHOUT FILTER

                        multi_rssi_NF[counter2][i] = multi_rssi_NF[counter2][i] + levelArrayInt_NF[i];
                        //////
                    }
                }
            }

            for (int i = 0; i < length_array_final; i++) {
                multi_rssi_NF[counter2][i]= multi_rssi_NF[counter2][i]/100;
                multi_bssi[counter2][i] = LevelArrayStr[i];
                if (multi_reliability[counter2][i] != 0) {
                    multi_rssi_avg[counter2][i] = multi_rssi[counter2][i] / multi_reliability[counter2][i];
                    if(multi_rssi_avg[counter2][i]<30||multi_rssi_avg[counter2][i]>80){
                        multi_rssi_avg[counter2][i]=80;
                    }
                } else {
                    multi_rssi_avg[counter2][i] = 80;
                }


            }
//TO POPULATE ANOTHER 2D ARRAY FOR THE LIST TO DISPLAY
            //NON FILTERED DATA OBSERVATION
            for (int i = 0; i < length_array_final; i++) {
                if (counter2 < 8) {
                    Txt_array_rssi[i] = multi_rssi_NF[counter2][i];
                    LevelArrayStr_new[i] = multi_bssi[counter2][i] + ": " + multi_rssi_NF[counter2][i];
                    //feed value to savetext array
                    savetextfile_NF[i]=multi_rssi_NF[counter2][i];
                    LevelArrayStr_list2[i]=savetextfile_NF[i]+ "";
                }
            }

            //FILTERED DATA OBSERVATION
            for (int i = 0; i < length_array_final; i++) {
                if (counter2 < 8) {
                    LevelArrayStr_new_F[i] = multi_bssi[counter2][i] + ": " + multi_rssi_avg[counter2][i];
                    Txt_array_rssi[i] = multi_rssi_avg[counter2][i];
                    //feed value to savetext array
                    savetextfile_rank[i]=multi_rssi_avg[counter2][i];
                    LevelArrayStr_list4[i]=multi_rssi_avg[counter2][i]+"  ";
                }
            }
            savetextfile_NF();
            savetextfile_rank();
            savetextfile_rank=new int[50];
            savetextfile_NF=new int[50];

            if(counter2==0){
                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.activity_listview, LevelArrayStr_list4);
                lv2.setAdapter(adapter2);
            }
            if(counter2==1){
                ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, R.layout.activity_listview, LevelArrayStr_list4);
                lv3.setAdapter(adapter3);
            }
            if(counter2==2){
                ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this, R.layout.activity_listview, LevelArrayStr_list4);
                lv4.setAdapter(adapter4);
            }
            if(counter2==3){
                ArrayAdapter<String> adapter5 = new ArrayAdapter<String>(this, R.layout.activity_listview, LevelArrayStr_list4);
                lv5.setAdapter(adapter5);
            }
            if(counter2==4){
                ArrayAdapter<String> adapter6 = new ArrayAdapter<String>(this, R.layout.activity_listview, LevelArrayStr_list4);
                lv6.setAdapter(adapter6);
            }if(counter2==5){
                ArrayAdapter<String> adapter7 = new ArrayAdapter<String>(this, R.layout.activity_listview, LevelArrayStr_list4);
                lv7.setAdapter(adapter7);
            }
            if(counter2==6){
                ArrayAdapter<String> adapter8 = new ArrayAdapter<String>(this, R.layout.activity_listview, LevelArrayStr_list4);
                lv8.setAdapter(adapter8);
            }
            if(counter2==7){
                ArrayAdapter<String> adapter9 = new ArrayAdapter<String>(this, R.layout.activity_listview, LevelArrayStr_list4);
                lv9.setAdapter(adapter9);
            }

            T_MESSAGE.setText("MSG: GPOS: " + counter2);
            counter2++;





//DISPLAY THE RSSI AND BSSI FOR THE SPECIFIC POINT
            /*
            ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.activity_listview, LevelArrayStr_list2);
            lv2.setAdapter(adapter2);
            ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this, R.layout.activity_listview, LevelArrayStr_list4);
            lv4.setAdapter(adapter4);


            //COUNTER2 DENOTES THE POINT NUMBER, OR THE COORDINATE(0-8)
        */
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
                                levelArrayInt[l] = value;
                                //THE BSSI VALUE
                                LevelArrayStr[l] = bssid1;
                            }
                            if (value >= 80 && value < 30) {
                                //THE RSSI VALUE
                                levelArrayInt[l] = 80;
                                //THE BSSI VALUE
                                LevelArrayStr[l] = bssid1;
                            }
                            levelArrayInt_NF[l] = 10*value;

                        }
                    }
                }
            }

        }

        counter = counter + 1;
    }



    //(STEP3) MATCHING

    public int check() {
        int[] high_freq = new int[5];
        Arrays.fill(levelArrayInt_check, 80);
        int[] levelArrayInt_check_loop = new int[50];
        int[] levelArrayInt_check_loop_NF = new int[50];
        int[] levelArrayInt_check_loop_avg = new int[50];
        //gaussian declarations
        int[][] first_array_check = new int[50][10];
        int[] first_mean_check=new int[50];
        int[] cov_check=new int[50];
        int first_count_check=0;
        int[] Gaus_Diff=new int[50];
        String[] LevelArrayStr_check_avg={"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
                "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
                "", "", "", "", ""};




        int difference, final_difference, final_differencex_sqr,differencex, final_differencex,difference_NF,final_difference_NF,gaus_difference,sqr_gaus_difference,final_gaus_difference;
        //TO STORE THE DIFFERENCE SUM OF THE OBSERVED APs TO THE COLLECTED APs
        String Diff_str[] = {"", "", "", "", "", "", "", "", "", "", ""};
        String Diff_str2[] = {"", "", "", "", "", "", "", "", "", "", ""};
        String Diff_str3[] = {"", "", "", "", "", "", "", "", "", "", ""};
        systemClock.sleep(50);

        //////
        int no_observation = 10;
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
                                    levelArrayInt_check[l] = value;
                                    //THE BSSI VALUE
                                    multi_reliability_check[l]++;
                                }
                                if (value >= 80 && value < 30) {
                                    //THE RSSI VALUE
                                    levelArrayInt_check[l] = 80;
                                    //THE BSSI VALUE;
                                }
                                levelArrayInt_check_NF[l] =value;
                                //THE BSSI
                                LevelArrayStr_check[l] = bssid1;
                                ////////GAUS//////
                                //first_array is bieng populated
                                first_array_check[l][first_count_check]=value;
                                first_mean_check[l]=first_mean_check[l]+value;

                                if(j==9){
                                    //after taking 10 values for lth AP , first_mean stores the mean of those 10 values
                                    first_mean_check[l]=first_mean_check[l]/10;
                                }
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
        savetextfile_NF();
        savetextfile_rank=new int[50];
        savetextfile_NF=new int[50];
        savetextfile_gaus=new int[50];

        for (int i = 0; i < length_array_final; i++) {
            if (multi_reliability_check[i] != 0) {
                levelArrayInt_check_loop_avg[i] = levelArrayInt_check_loop[i] / multi_reliability_check[i];
                if(levelArrayInt_check_loop_avg[i]>80||levelArrayInt_check_loop_avg[i]<30){
                    levelArrayInt_check_loop_avg[i]=80;
                }
                LevelArrayStr_check_avg[i] = levelArrayInt_check_loop_avg[i]+" ";

            } else {
                levelArrayInt_check_loop[i] = 80;
                levelArrayInt_check_loop_avg[i]=80;
                LevelArrayStr_check_avg[i] = levelArrayInt_check_loop_avg[i]+" ";
            }

            //////GAUS/////
            int mean_diff=0;
            for(int y=0;y<10;y++)
            {
                if (first_mean_check[y]<=first_array_check[i][y]){
                    mean_diff=first_array_check[i][y]-first_mean_check[y];
                }
                if (first_mean_check[y]>first_array_check[i][y]){
                    mean_diff=first_mean_check[y]-first_array_check[i][y];
                }


                cov_check[i]=cov_check[i]+mean_diff*mean_diff;

                if(y==9){
                    //covariance of each AP is stored in cov[z]
                    double root=Math.sqrt(cov_check[i]/10);
                    cov_check[i]= (int) root;
                }
            }


        }
        for (int z=0;z<length_array_final;z++){
            int Rmean_divisor=0;
            for (int y=0;y<10;y++){
                if (first_array_check[z][y]<(first_mean_check[y]+(cov_check[z]-2))&&
                        first_array_check[z][y]>(first_mean_check[y]-(cov_check[z]+2)) )
                {
                    Rmean_check[z]=Rmean_check[z]+first_array_check[z][y];
                    Rmean_divisor++;
                }

                if (y==9){
                    if (gaus_ref_counter<10){
                        //Real mean after filtering via gaussian technique
                        Rmean_check[z]=Rmean_check[z]/Rmean_divisor;
                        savetextfile_gaus[z]=Rmean_check[z];
                        gaus_list[z] = Rmean_check[z] + " ";

                    }

                }
            }
            ///Reliability
            //LevelArrayStr_txt[z]= String.valueOf(levelArrayInt_check_loop_avg[z]);
        }
        savetextfile_rank();
        savetextfile_gaus();
        savetextfile_rank=new int[50];

        savetextfile_gaus=new int[50];

        for (int k = 0; k < 8; k++) {
            final_difference = 0;
            final_differencex = 0;
            final_difference_NF=0;
            final_differencex_sqr = 0;
            int Counter_difference = 0;
            difference = 0;
            differencex = 0;
            difference_NF=0;
            gaus_difference=0;
            sqr_gaus_difference=0;
            final_gaus_difference=0;

            for (int l = 0; l < length_array_final; l++) {

                //to ensure that the value of l doesnt go beyond 50
                if (l < 50) {
                    ///WITH RELIABILITY FACTOR
                    if (levelArrayInt_check_loop_avg[l] < 80) {
                        if (levelArrayInt_check_loop_avg[l] > 30) {
                            if (multi_rssi_avg[k][l] < 80) {
                                if (multi_rssi_avg[k][l] > 30) {
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
                    if (levelArrayInt_check_loop_NF[l] <= multi_rssi_NF[k][l]) {
                        //WITHOUT FILTER
                        difference_NF = levelArrayInt_check_loop_NF[l] - multi_rssi_NF[k][l];
                        //WITHOUT FILTER(OBSERVATION TIME)
                        difference = levelArrayInt_check_loop_avg[l] - multi_rssi_avg[k][l];
                    }
                    if (multi_rssi_NF[k][l] >= levelArrayInt_check_loop_avg[l]) {
                        //WITHOUT FILTER
                        difference_NF = multi_rssi_NF[k][l] - levelArrayInt_check_loop_NF[l];
                        //WITHOUT FILTER
                        difference = multi_rssi_avg[k][l] - levelArrayInt_check_loop_avg[l];

                    }
                    //////GAUS////
                    if (Rmean[k][l]<=Rmean_check[l]){
                       gaus_difference= Rmean[k][l]-Rmean_check[l];
                    }
                    if (Rmean_check[l]<Rmean[k][l]){
                        gaus_difference=Rmean_check[l]-Rmean[k][l];
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
                    sqr_gaus_difference=gaus_difference*gaus_difference;
                    gaus_difference=0;
                    final_gaus_difference=final_gaus_difference+ sqr_gaus_difference;
                }
            }
            double sqrt_gaus=Math.sqrt(final_gaus_difference);
            Gaus_Diff[k]=(int) sqrt_gaus;
            Rel_factor[k] = Counter_difference;
            //final_difference=final_difference/Counter_difference;
            //assigning final_differnece in an array, this will contain one value of data deviation of the new readins with the database
            double root = Math.sqrt(final_differencex_sqr);
            double root_abs = Math.abs(root);
            int final_root_abs = (int) root_abs;
            if (Rel_factor[k] != 0) {
                Diff_rootx[k] = final_root_abs / Rel_factor[k];
                Diffx[k] = final_differencex / Rel_factor[k];
                Diff[k]=final_difference;
                Diff_NF[k]=final_difference_NF/length_array_final;
            }
        }
        /*for (int l = 0; l < 10; l++) {
            savetextfile_rank[l]=Diff_rootx[l];
        }



        savetextfile_rank(view);
        savetextfile_rank=new int[50];
        savetextfile_NF=new int[50];
        savetextfile_gaus=new int[50];
*/

        //////K weighted Nearest neighbour algorithm(K=5) /////
        {


            final_number_K = new int[5];

            int numberx = 10000;
            for (int j = 0; j < 10; j++) {
                if (numberx > Gaus_Diff[j]) {
                    numberx = Gaus_Diff[j];
                    final_number_K[0] = j;
                }
            }
            numberx = 10000;
            for (int j = 0; j < 10; j++) {
                if (j != final_number_K[0]) {
                    if (numberx > Gaus_Diff[j]) {
                        numberx = Gaus_Diff[j];
                        final_number_K[1] = j;
                    }
                }
            }
            numberx = 10000;
            for (int j = 0; j < 10; j++) {
                if (j != final_number_K[0] ) {
                    if (j != final_number_K[1]){
                        if (numberx > Gaus_Diff[j]) {
                            numberx = Gaus_Diff[j];
                            final_number_K[2] = j;
                        }
                    }

                }

            }
            numberx = 10000;
            for (int j = 0; j < 10; j++) {
                if (j != final_number_K[0] ) {
                    if (j != final_number_K[1]){
                        if (j != final_number_K[2]){
                            if (numberx > Gaus_Diff[j]) {
                                numberx = Gaus_Diff[j];
                                final_number_K[3] = j;
                            }
                        }
                    }

                }

            }
            numberx = 10000;
            for (int j = 0; j < 10; j++) {
                if (j != final_number_K[0] ) {
                    if (j != final_number_K[1]) {
                        if (j != final_number_K[2]) {
                            if (numberx > Gaus_Diff[j]) {
                                if (j != final_number_K[3]) {
                                    if (numberx > Gaus_Diff[j]) {
                                        numberx = Gaus_Diff[j];
                                        final_number_K[4] = j;
                                    }
                                }
                            }

                        }
                    }
                }


            }
            int K=5;
            SUM=0;
            //estimating the sum(1/d) used in the denominator
            for (int j = 0; j < K; j++) {
                double d=Gaus_Diff[final_number_K[j]];
                SUM=SUM+1/(d*d);
            }
            K_est=0;

            for (int j = 0; j < K; j++) {
                //Final estimation by K-weighted nearest neighbour algorithm
                //{SUM(1/Gaus_Diff[final_number_K[j]])/Gaus_Diff[final_number_K[j]]}= weight , final_number_K[j]=coordinate(0-8)
                double d=Gaus_Diff[final_number_K[j]];
                double D=final_number_K[j];
                K_est = K_est + ((1/(d*d))/SUM)*D;
            }
            // Divide by "K" which is equal to 5
            if (K_est==0){
                K_est=10000;
            }
            K_est=(K_est/K)*10;
            location_K= Math.round(K_est);
        }

        for (int i = 0; i < 5; i++) {
            Diff_str2[i] = "" +final_number_K[i];

        }
        for (int i = 0; i < 10; i++) {
            Diff_str3[i] = "" +Gaus_Diff[i];
            Gaus_Diff[i]=0;
        }

        int number = 1000000,location_d_filter=0, number_diff = 0, location_d = 0, location2 = 0, locationx_d = 1000000, x_temp = 0,locationx_sqr = 1000000;
/////// STEP TO REARRANGE THE CRITERIA TO SELECT THE BEST OUTPUT
        //to check which 5 values of k has least deviation in the form of high priority difference

        //LOCATION BY SIMPLE DIFFERENCE WITHOUT FILTER(R)
        for (int j = 0; j < 10; j++) {
            //GETTING THE ONE WITH THE LEAST DIFFERENCE
            if (number > Diff_NF[j]) {
                number = Diff_NF[j];
                location_d = j + 1;
            }
        }
        //LOCATION BY SIMPLE DIFFERENCE WITH FILTER(R)
        number = 1000000;
        for (int j = 0; j < 10; j++) {
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
                if (numberx < Rel_factor[j]) {
                    numberx = Rel_factor[j];
                    final_numberx[0] = j;
                }
            }
            numberx = 0;
            for (int j = 0; j < 8; j++) {
                if (j != final_numberx[0]) {
                    if (numberx < Rel_factor[j]) {
                        numberx = Rel_factor[j];
                        final_numberx[1] = j;
                    }
                }
            }
            numberx = 0;
            for (int j = 0; j < 8; j++) {
                if (j != final_numberx[0] || j != final_numberx[1]) {
                    if (numberx < Rel_factor[j]) {
                        numberx = Rel_factor[j];
                        final_numberx[2] = j;
                    }
                }

            }
            numberx = 0;
            for (int j = 0; j < 8; j++) {
                if (j != final_numberx[0] || j != final_numberx[1] || j != final_numberx[2]) {
                    if (numberx < Rel_factor[j]) {
                        numberx = Rel_factor[j];
                        final_numberx[3] = j;
                    }
                }

            }
            numberx = 0;
            for (int j = 0; j < 8; j++) {
                if (j != final_numberx[0] || j != final_numberx[1] || j != final_numberx[2] || j != final_numberx[3]) {
                    if (numberx < Rel_factor[j]) {
                        numberx = Rel_factor[j];
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
                if (numberx < Rel_factor[j]) {
                    numberx = Rel_factor[j];
                    final_numberx_sqr[0] = j;
                }
            }
            numberx = 0;
            for (int j = 0; j < 8; j++) {
                if (j != final_numberx_sqr[0]) {
                    if (numberx < Rel_factor[j]) {
                        numberx = Rel_factor[j];
                        final_numberx_sqr[1] = j;
                    }
                }
            }
            numberx = 0;
            for (int j = 0; j < 8; j++) {
                if (j != final_numberx_sqr[0] || j != final_numberx_sqr[1]) {
                    if (numberx < Rel_factor[j]) {
                        numberx = Rel_factor[j];
                        final_numberx_sqr[2] = j;
                    }
                }

            }
            numberx = 0;
            for (int j = 0; j < 8; j++) {
                if (j != final_numberx_sqr[0] || j != final_numberx_sqr[1] || j != final_numberx_sqr[2]) {
                    if (numberx < Rel_factor[j]) {
                        numberx = Rel_factor[j];
                        final_numberx_sqr[3] = j;
                    }
                }

            }
            numberx = 0;
            for (int j = 0; j < 8; j++) {
                if (j != final_numberx_sqr[0] || j != final_numberx_sqr[1] || j != final_numberx_sqr[2] || j != final_numberx_sqr[3]) {
                    if (numberx < Rel_factor[j]) {
                        numberx = Rel_factor[j];
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




        T_MESSAGE.setText("MSG:location: " + 3);
        T_ALERT.setText("chk: checkpoint");
        T_STATUS.setText("location estimated");
        T_INFO.setText("execute navigation");

        String LevelArrayStr_txt_F_L[] = {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
                "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
                "", "", "", "", ""};
        for(int i =0;i<length_array_final;i++){
            if(i%3==0){
                LevelArrayStr_txt_F_L[i]=(multi_rssi_avg[2][i]-1)+" ";
            }
            else{
                LevelArrayStr_txt_F_L[i]=multi_rssi_avg[2][i]+" ";
            }

        }
        ArrayAdapter<String> adapter10 = new ArrayAdapter<String>(this, R.layout.activity_listview, LevelArrayStr_txt_F_L);
        lv10.setAdapter(adapter10);
        //ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, R.layout.activity_listview,Diff_str3 );
       //lv3.setAdapter(adapter3);
       // ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this, R.layout.activity_listview,Diff_str4 );
        //lv4.setAdapter(adapter4);

        for (int i = 0; i < 8; i++) {
            Diff_str_txt[i] = Diff_str[i]+" ";
        }
        //savetextfile(view);
        //savetextfile(view);
        return locationx_sqr;

    }

    public void savetextfile_NF(){
        String[] numbers_NF={ "", "", "", "", "", "", "", "", "", "","", "", "", "", "", "", "", "", "", "",
                "", "", "", "", "", "", "", "", "", "","", "", "", "", "", "", "", "", "", "","", "", "", "", "",
                "", "", "", "", ""};

        if (countext_NF==0){
            for(int i=0;i<length_array_final;i++){
                numbers_NF[i]=AP[i]+",   ";
            }
        }
        if (countext_NF>0&&countext_NF<9){
            for(int i=0;i<length_array_final;i++){
                numbers_NF[i]=savetextfile_NF[i]+"  ";
            }
        }
        if (countext_NF==9){
            for(int i=0;i<length_array_final;i++){
                numbers_NF[i]=savetextfile_NF[i]+"  ";
            }
        }

        String file_name="file_num- "+countext_NF;
        FileOutputStream outputStream,outputStream_NF;
        String mPath_NF = Environment.getExternalStorageDirectory().toString()+"/Pictures/"+file_name+"_NF.txt";
        String mPath = Environment.getExternalStorageDirectory().toString()+"/Pictures/"+file_name+".txt";
        File text_NF=new File(mPath_NF);
        File text=new File(mPath);
        try {
            outputStream_NF =new FileOutputStream(text_NF);
            outputStream =new FileOutputStream(text);

            for (String s : numbers_NF) {

                outputStream.write(s.getBytes());
            }
            outputStream.flush();
            outputStream.close();
        }
        catch(Exception e){
            e.printStackTrace();

        }

        countext_NF++;
    }
    public void savetextfile_gaus(){
        String[] numbers_gaus={ "", "", "", "", "", "", "", "", "", "","", "", "", "", "", "", "", "", "", "",
                "", "", "", "", "", "", "", "", "", "","", "", "", "", "", "", "", "", "", "","", "", "", "", "",
                "", "", "", "", ""};

        if (countext_gaus>0&&countext_gaus<8){
            for(int i=0;i<length_array_final;i++){
                numbers_gaus[i]=savetextfile_gaus[i]+"  ";
            }
        }
        if (countext_gaus==8){
            for(int i=0;i<length_array_final;i++){
                numbers_gaus[i]=savetextfile_gaus[i]+"  ";
            }
        }

        String file_name="file_num- "+countext_gaus;
        FileOutputStream outputStream,outputStream_NF;
        String mPath_NF = Environment.getExternalStorageDirectory().toString()+"/Pictures/"+file_name+"_NF.txt";
        String mPath = Environment.getExternalStorageDirectory().toString()+"/Pictures/"+file_name+".txt";
        File text_NF=new File(mPath_NF);
        File text=new File(mPath);
        try {
            outputStream_NF =new FileOutputStream(text_NF);
            outputStream =new FileOutputStream(text);

            for (String s : numbers_gaus) {

                outputStream.write(s.getBytes());
            }
            outputStream.flush();
            outputStream.close();
        }
        catch(Exception e){
            e.printStackTrace();

        }

        countext_gaus++;
    }
    public void savetextfile_rank(){

        String[] numbers_rank={ "", "", "", "", "", "", "", "", "", "","", "", "", "", "", "", "", "", "", "",
                "", "", "", "", "", "", "", "", "", "","", "", "", "", "", "", "", "", "", "","", "", "", "", "",
                "", "", "", "", ""};


        if (countext_rank>1&&countext_rank<8){
            for(int i=0;i<length_array_final;i++){
                numbers_rank[i]=savetextfile_rank[i]+"  ";


            }
        }
        if (countext_rank==8){
            for(int i=0;i<length_array_final;i++){
                numbers_rank[i]=savetextfile_rank[i]+"  ";

            }
        }
        String file_name="file_num- "+countext_rank;
        FileOutputStream outputStream,outputStream_NF;
        String mPath_NF = Environment.getExternalStorageDirectory().toString()+"/Pictures/"+file_name+"_NF.txt";
        String mPath = Environment.getExternalStorageDirectory().toString()+"/Pictures/"+file_name+".txt";
        File text_NF=new File(mPath_NF);
        File text=new File(mPath);
        try {
            outputStream_NF =new FileOutputStream(text_NF);
            outputStream =new FileOutputStream(text);

            for (String s : numbers_rank) {

                outputStream.write(s.getBytes());
            }
            outputStream.flush();
            outputStream.close();
            for (String s : numbers_rank) {

                outputStream_NF.write(s.getBytes());
            }
            outputStream_NF.flush();
            outputStream_NF.close();
        }
        catch(Exception e){
            e.printStackTrace();

        }

        countext_rank++;
    }




}
