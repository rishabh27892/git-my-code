package com.rishabh.chauhan.myapplication;
import android.Manifest;
import android.content.Context;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.input.InputManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Level;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

 public class MainActivity extends AppCompatActivity implements SensorEventListener {
    //private static final String TAG = "MyActivity";
    SensorManager sensorMgr;
    TextView text2;

    Sensor sensorLight;
    int lux_public=0;
    WifiManager wifiManager;
    SystemClock systemClock;
    ScanResult scanResult;
    TextView  onoff, result1, result2, result3, result4, result5, result6, result7, result8;
    int[] levelArrayInt= new int[50];
    int[] levelArrayInt_check= new int[50];
    int counter_text=0;
    EditText editText;
    Button display;
    String info1, level1, bssid1, abc;
    int counter = 0, counter2 = 0,counter3=0, scansize = 0,length_array=0,length_array_final=0;
    ListView lv, lv2;
    int xyz = 0;
    int[][] multi_rssi = new int[8][50];
    String[][] multi_bssi = new String[8][50];
    int[][] multi_rssi_avg = new int[8][50];
    int[][] multi_reliability = new int[8][50];
    int[] multi_reliability_check = new int[50];
    int square_check=0;



    String[] AP = { "", "", "", "", "", "", "", "", "", "","", "", "", "", "", "", "", "", "", "",
    "", "", "", "", "", "", "", "", "", "","", "", "", "", "", "", "", "", "", "","", "", "", "", "",
     "", "", "", "", ""};

    String LevelArrayStr[] = { "", "", "", "", "", "", "", "", "", "","", "", "", "", "", "", "", "", "", "",
    "", "", "", "", "", "", "", "", "", "","", "", "", "", "", "", "", "", "", "","", "", "", "", "",
     "", "", "", "", ""};
    String LevelArrayStr_check[] = { "", "", "", "", "", "", "", "", "", "","", "", "", "", "", "", "", "", "", "",
    "", "", "", "", "", "", "", "", "", "","", "", "", "", "", "", "", "", "", "","", "", "", "", "",
     "", "", "", "", ""};

    String LevelArrayStr_check_avg[] = { "", "", "", "", "", "", "", "", "", "","", "", "", "", "", "", "", "", "", "",
            "", "", "", "", "", "", "", "", "", "","", "", "", "", "", "", "", "", "", "","", "", "", "", "",
            "", "", "", "", ""};
    String LevelArrayStr_txt[] = { "", "", "", "", "", "", "", "", "", "","", "", "", "", "", "", "", "", "", "",
            "", "", "", "", "", "", "", "", "", "","", "", "", "", "", "", "", "", "", "","", "", "", "", "",
            "", "", "", "", ""};
    String Diff_str_txt[]={"","","","","","","","","",""};

    int[] Diff=new int[50];
    int[] Count_Diff=new int[50];
    int[] Diff_root=new int[50];
    int[] Txt_array_rssi=new int[50];
     int[] lux_val_arr=new int[8];



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        wifiManager.startScan();
        result1 = (TextView) findViewById(R.id.text1);
        result2 = (TextView) findViewById(R.id.text2);
        result3 = (TextView) findViewById(R.id.text3);
        result4 = (TextView) findViewById(R.id.text4);
        result5 = (TextView) findViewById(R.id.text5);
        result6 = (TextView) findViewById(R.id.text6);
        result7 = (TextView) findViewById(R.id.text7);
        result8 = (TextView) findViewById(R.id.text8);
        display = (Button) findViewById(R.id.display);
        lv = (ListView) findViewById(R.id.your_list_view_id);
        lv2 = (ListView) findViewById(R.id.your_list_view_id2);
        onoff = (TextView) findViewById(R.id.onoff);
        wifiManager.setWifiEnabled(true);
        Arrays.fill(levelArrayInt,8000);
        Log.d("STATE","wassup my man");
        sensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);
        List<Sensor> deviceSensors= sensorMgr.getSensorList(Sensor.TYPE_ALL);

        text2=(TextView)findViewById(R.id.text2);

        sensorLight=sensorMgr.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorMgr.registerListener(this,sensorLight,SensorManager.SENSOR_DELAY_FASTEST);

    }
    //(STEP 1)for making list of available APs, if new AP is available then add to the list.
    public void data(View View) {
        result1.setText("Refreshed");
        result2.setText("Refreshed");
        result3.setText("Refreshed");
        wifiManager.startScan();
        info1 = "";
        String empty = "";
        if (wifiManager.isWifiEnabled()) {
            onoff.setText("Wifi:ON");
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
                    //if (AP1s.size()<6) {
                    if (counter3 == 0&& value<80) {
                        AP[i] = bssid1;
                        length_array = AP1s.size();
                    }
                    if (counter3 != 0&&value<80) {
                        int z;
                        {

                            for (z = 0; z < length_array; z++) {
                                if (bssid1.equals(AP[z])) {
                                    condition_present = true;
                                    break;
                                }
                            }


                                if (condition_present==true) { //IF Z IS LESS THEN ARRAY_LENGTH, NO NEW ELEMENT FOUND. DO NOT ADD TO ARRAY AP[]
                                    result2.setText("no new array found");
                                }
                            //IF CONDITION_PRESENT IS FALSE, IT MEANS THE FOR LOOP DID NOT MATCH AND MAKE THE CONDITION_PRESENT TRUE IN ANY OF THE ITERATIONS
                            // (BSSID WAS NEVER FOUND TO BE EQUAL TO ANY ELEMENT IN ARRAY)
                               if (condition_present==false) {
                                    result3.setText("new array found");
                                    AP[length_array] = bssid1;
                                   length_array = 1 + length_array;
                                }

                        }
                    }
                }
            }
            result1.setText("APs: "+ length_array);
            counter3 = counter3 + 1;
            //in order to keep the value of length array in a seperate safe variable
            length_array_final=length_array;
        }
    }
    //(STEP 2) display the list of APs in the list
    public void display(View View){
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, R.layout.activity_listview, AP);
        lv.setAdapter(adapter1);
        savetextfile(View);

    }
    //(STEP 3) call teh scan function multiple times and update the values in a 2D array, 1 click for one coordinate

    public void scan_final(View View) {

        Arrays.fill(levelArrayInt,8000);
        if (counter2<8){
        String LevelArrayStr_new[] = {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
                "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
                "", "", "", "", ""};
        //
        int no_observation = 100;
        for (int j = 0; j < no_observation; j++) {
            lux_val_arr[counter2]=lux_val_arr[counter2]+lux_public;
            scan2(View);
            for (int i = 0; i < length_array_final; i++) {
                if (counter2 < 8) {
                    //2D ARRAY  POPULATED BY LEVELARRAYINT AND LEVELARRAYSTR, (COUNTER2xi)
                    if(levelArrayInt[i]>=3000&&levelArrayInt[i]<8000){
                        multi_reliability[counter2][i]++;
                    }
                    multi_rssi[counter2][i] = multi_rssi[counter2][i] + levelArrayInt[i];
                }
            }
        }
            lux_val_arr[counter2]=lux_val_arr[counter2]/100;
        for (int i = 0; i < length_array_final; i++) {
            if (multi_reliability[counter2][i]!=0){
                multi_rssi_avg[counter2][i] = multi_rssi[counter2][i] / multi_reliability[counter2][i];
                multi_bssi[counter2][i] = LevelArrayStr[i];
            }
            else{
                multi_rssi_avg[counter2][i]=8000;
            }


        }
//TO POPULATE ANOTHER 2D ARRAY FOR THE LIST TO DISPLAY
        for (int i = 0; i < length_array_final; i++) {
            if (counter2 < 8) {
                LevelArrayStr_new[i] = multi_bssi[counter2][i] + ": " + multi_rssi_avg[counter2][i];
                Txt_array_rssi[i]=multi_rssi_avg[counter2][i];
            }
        }
            for(int i =0;i<length_array_final;i++){
                LevelArrayStr_txt[i]=LevelArrayStr_new[i];
            }
//DISPLAY THE RSSI AND BSSI FOR THE SPECIFIC POINT
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, R.layout.activity_listview, LevelArrayStr_new);
        lv2.setAdapter(adapter1);
        result4.setText("num: " + counter2);
        //COUNTER2 DENOTES THE POINT NUMBER, OR THE COORDINATE(0-8)
    }
        result8.setText("lux val: "+lux_val_arr[counter2]);
        counter2++;

        savetextfile(View);
    }


// (STEP 3.1) scans all the available APs to create a database of signal strength
    public void scan2(View View) {
        wifiManager.startScan();
        Boolean yes_no=false;
        info1 = " ";
        if (wifiManager.isWifiEnabled()) {
            onoff.setText("Wifi:ON");
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
                        if(bssid1.equals(AP[l])) {
                            yes_no = true;

                            //To make sure to assume all the values above 80=80.
                            if(value<80&&value>=30) {
                                //THE RSSI VALUE
                                levelArrayInt[l] = 100*value;
                                //THE BSSI VALUE
                                LevelArrayStr[l] = bssid1;
                            }
                            if(value>=80&&value<30){
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
        result4.setText("COUNTER: "+counter);
    }
    //(STEP4) ENTER A NUMBER 0-8 IN EDIT TEXT
    //(STEP5) save the value into variables and convert , also hide the keyboard after this
    public void save(View view){
        //yet to put restriction to enter only numbers(0-8)
        abc = editText.getText().toString();
        xyz = Integer.parseInt(abc);
        InputMethodManager immr=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        immr.hideSoftInputFromWindow(editText.getWindowToken(),0);

    }








//(STEP6) MATCHING
     public void check(View view)
     {
         int[] high_freq=new int[5];
         int[]high_lux=new int[5];
         Arrays.fill(levelArrayInt_check,8000);
         int[] levelArrayInt_check_loop=new int[50];
         int[] levelArrayInt_check_loop_avg=new int[50];
         int lux_check=0;
         int []lux_check_arr=new int[8];


         int difference,final_difference,final_difference_sqr;
         //TO STORE THE DIFFERENCE SUM OF THE OBSERVED APs TO THE COLLECTED APs
         String Diff_str[]={"","","","","","","","","","",""};
         systemClock.sleep(50);

         //////
         int no_observation=100;
         result5.setText("observation: "+no_observation);
         for (int j=0;j<no_observation;j++)
         {
             lux_check=lux_check+lux_public;
         wifiManager.startScan();
         info1 = " ";
         if (wifiManager.isWifiEnabled()) {
             onoff.setText("Wifi:ON");
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
                             if(value<80&&value>=30) {
                                 //THE RSSI VALUE
                                 levelArrayInt_check[l] = 100*value;
                                 //THE BSSI VALUE
                                 LevelArrayStr_check[l] = bssid1;
                                 multi_reliability_check[l]++;
                             }
                             if(value>=80&&value<30){
                                 //THE RSSI VALUE
                                 levelArrayInt_check[l] = 8000;
                                 //THE BSSI VALUE
                                 LevelArrayStr_check[l] = bssid1;
                             }
                         }
                     }
                 }
             }
         }

             for (int i = 0; i < length_array_final; i++) {

                     //2D ARRAY  POPULATED BY LEVELARRAYINT AND LEVELARRAYSTR, (COUNTER2xi)
                     levelArrayInt_check_loop[i] = levelArrayInt_check_loop[i]+levelArrayInt_check[i];

             }

         }
         lux_check=lux_check/100;

         for(int i=0;i<length_array_final;i++){
             if (multi_reliability_check[i]!=0){
                 levelArrayInt_check_loop_avg[i] = levelArrayInt_check_loop[i] / multi_reliability_check[i];
                 LevelArrayStr_check_avg[i]=" "+ levelArrayInt_check_loop_avg[i];
             }
             else{
                 levelArrayInt_check_loop[i]=8000;
                 LevelArrayStr_check_avg[i]=" "+ levelArrayInt_check_loop_avg[i];
             }



         }

         for(int k =0;k<8;k++){
             final_difference=0;
             final_difference_sqr=0;
             int Counter_difference=0;
             difference=0;
             if (lux_val_arr[k]>lux_check){
                 lux_check_arr[k]=lux_val_arr[k]-lux_check;
             }
             if(lux_val_arr[k]<=lux_check){
                 lux_check_arr[k]=lux_check-lux_val_arr[k];
             }
             for (int l = 0; l < length_array_final; l++){

                 //to ensure that the value of l doesnt go beyond 50
                 if (l<50){
                     if(levelArrayInt_check_loop_avg[l]<8000){
                         if(levelArrayInt_check_loop_avg[l]>=3000){
                             if(multi_rssi_avg[k][l]<8000){
                                 if(multi_rssi_avg[k][l]>=3000){
                                     // to ensure that the difference is not negative
                                     if(levelArrayInt_check_loop_avg[l] <= multi_rssi_avg[k][l]){
                                         difference=levelArrayInt_check_loop_avg[l]-multi_rssi_avg[k][l];
                                         Counter_difference++;
                                     }
                                     if(multi_rssi_avg[k][l] > levelArrayInt_check_loop_avg[l]){
                                         difference=multi_rssi_avg[k][l]-levelArrayInt_check_loop_avg[l];
                                         Counter_difference++;
                                     }

                                 }

                             }

                         }

                     }
                     square_check=difference*difference;
                     final_difference_sqr=final_difference_sqr+square_check;
                     final_difference=final_difference+difference;
                 }

             }
             Count_Diff[k]=Counter_difference;
             //final_difference=final_difference/Counter_difference;
             //assigning final_differnece in an array, this will contain one value of data deviation of the new readins with the database
             double root=Math.sqrt(final_difference_sqr);
             double root_abs=Math.abs(root);
             int final_root_abs=(int)root_abs;
             if(Count_Diff[k]!=0){
                 Diff_root[k]=final_root_abs/Count_Diff[k];
                 Diff[k]=final_difference/Count_Diff[k];
             }
         }
         for(int i=0;i<8;i++){
             Diff_str[i]="point: "+ i +"Square root: "+Diff_root[i]+" difference: " +Diff[i];
         }

         int number=10000000,lux_number=10000,number_diff=0, location1=0,location2=0,location3=0, x_temp=0,x_temp_lux=0;
/////// STEP TO REARRANGE THE CRITERIA TO SELECT THE BEST OUTPUT
         //to check which 5 values of k has least deviation in the form of high priority difference
         for(int j=0;j<8;j++){
             //GETTING THE ONE WITH THE LEAST DIFFERENCE
             if(number>Diff[j]){
                 number=Diff[j];
                 location1=j;
             }
             //GETTING TOP 5 MATCH DUE TO LIGHT(SIMILARITY)
             if(x_temp_lux<5){
                 if(j==0){
                     if(lux_number>lux_check_arr[j]){
                         lux_number=lux_check_arr[j];
                         high_lux[x_temp_lux]=j;
                         x_temp_lux++;
                     }
                 }
                 if(j!=high_freq[0]||j!=high_freq[1]||j!=high_freq[2]||j!=high_freq[3]||j!=high_freq[4]){
                     /*
                     if(number_diff==Count_Diff[j]){
                         high_freq[x_temp]=j;
                         x_temp++;
                     }
                     */
                     if(lux_number>lux_check_arr[j]){
                         lux_number=lux_check_arr[j];
                         high_lux[x_temp_lux]=j;
                         x_temp_lux++;
                     }
                 }

             }
             //NOW WE NEED TO FOCUS OUR SEARCH TO THE TOP 5 FROM LIGHT AND NARROW OUR SEARCH BECAUSE LIGHT RESULT IS MORE ACCURATE

             //GETTING TOP 5 MATCHES DUE TO FREQUENCY OF OCCURANCE(Reliability)

             if(x_temp<5){

                 if(j==0){
                     if(number_diff<Count_Diff[j]){
                         number_diff=Count_Diff[j];
                         high_freq[x_temp]=j;
                         x_temp++;
                     }
                 }
                 if(j!=high_freq[0]||j!=high_freq[1]||j!=high_freq[2]||j!=high_freq[3]||j!=high_freq[4]){
                     if(number_diff==Count_Diff[j]){
                         high_freq[x_temp]=j;
                         x_temp++;
                     }
                     if(number_diff<Count_Diff[j]){
                         number_diff=Count_Diff[j];
                         high_freq[x_temp]=j;
                         x_temp++;
                     }
                 }
             }

             //least deviation means it matches the most with the real value
         }
         int rnd_number=100000;
         for(int j=0;j<5;j++){
             if(rnd_number>Diff[high_freq[j]]){
                 rnd_number=Diff[high_freq[j]];
                 location2=high_freq[j];
             }
         }





             //least deviation means it matches the most with the real value


         result6.setText("L_D:"+location1);
         result7.setText("L_R:"+location2);
         result8.setText("L_L:"+location3);
         ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, R.layout.activity_listview, LevelArrayStr_check_avg);
         lv.setAdapter(adapter1);
         ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.activity_listview, Diff_str);
         lv2.setAdapter(adapter2);
for(int i=0;i<8;i++){
    Diff_str_txt[i]=Diff_str[i];
}

     }
    public void savetextfile(View view){
        String[] numbers={ "", "", "", "", "", "", "", "", "", "","", "", "", "", "", "", "", "", "", "",
                "", "", "", "", "", "", "", "", "", "","", "", "", "", "", "", "", "", "", "","", "", "", "", "",
                "", "", "", "", ""};

        if (counter_text==0){
            for(int i=0;i<length_array_final;i++){
                numbers[i]=AP[i]+",   ";
            }

        }
        if (counter_text>0&&counter_text<10){
            for(int i=0;i<length_array_final;i++){
                numbers[i]=Txt_array_rssi[i]+"  ";
                        //+multi_reliability[counter_text-1][i]+"  " ;
            }
        }
        if (counter_text==10){
            for(int i=0;i<length_array_final;i++){
                numbers[i]=LevelArrayStr_check_avg[i]+"  ";
            }
        }
        if (counter_text==11){
            for(int i=0;i<8;i++){
                numbers[i]=Diff_str_txt[i]+"  ";
            }
        }

        if (counter_text==12){
            for(int i=0;i<8;i++){
                numbers[i]=Count_Diff[i]+"  ";
            }
        }
        if(counter_text==13){
            for(int i=0;i<8;i++){
                numbers[i]=lux_val_arr+"  ";
            }
        }
        Date now =new Date();
        String file_name="file_num- "+counter_text;
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);
        FileOutputStream outputStream;
        String mPath = Environment.getExternalStorageDirectory().toString()+"/Pictures/"+file_name+".txt";
        File text=new File(mPath);
        try {
            outputStream =new FileOutputStream(text);
            //outputStream = openFileOutput(filename, Context.MODE_APPEND);

            for (String s : numbers) {

                outputStream.write(s.getBytes());
            }
            outputStream.flush();
            outputStream.close();
        }
        catch(Exception e){
                e.printStackTrace();

            }
        onoff.setText("txt: "+counter_text);
        counter_text++;
        }



    public void screenshot(View View) {
        Date now =new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);
        String Now="Date: "+now;
        try {

        String mPath = Environment.getExternalStorageDirectory().toString()+"/Pictures/"+now+".jpg";
        View v1=getWindow().getDecorView().getRootView();
        v1.setDrawingCacheEnabled(true);
        Bitmap bitmap=Bitmap.createBitmap(v1.getDrawingCache());
        v1.setDrawingCacheEnabled(false);
        File imageFile=new File(mPath);
            //File imageFile=new File(context.getFilesDir(),Now);

            FileOutputStream outputStream =new FileOutputStream(imageFile);
            int quality=100;
            bitmap.compress(Bitmap.CompressFormat.JPEG,quality,outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }


     @Override
     public void onSensorChanged(SensorEvent event) {
         lux_public=(int) event.values[0];
     }

     @Override
     public void onAccuracyChanged(Sensor sensor, int accuracy) {

     }
 }

