package com.rishabh.chauhan.sensor;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

 public class MainActivity extends AppCompatActivity implements SensorEventListener{
    SensorManager sensorMgr;
    TextView text1,text2;
    Button button;
    Sensor sensorLight;
    float lux_public=0;
     int lux_public_check=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);
        List<Sensor> deviceSensors= sensorMgr.getSensorList(Sensor.TYPE_ALL);
        text1=(TextView)findViewById(R.id.text1);
        text2=(TextView)findViewById(R.id.text2);
        button=(Button)findViewById(R.id.button);
        sensorLight=sensorMgr.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorMgr.registerListener(this,sensorLight,SensorManager.SENSOR_DELAY_NORMAL);


    }
    /*
    class SensorActivity extends Activity implements SensorEventListener{
        @Override
        public final void onAccuracyChanged(Sensor sensor, int accuracy) {
            // Do something here if sensor accuracy changes.
        }
        @Override
        public final void onSensorChanged(SensorEvent event) {
            // The light sensor returns a single value.
            // Many sensors return 3 values, one for each axis.
            float lux = event.values[0];
            // Do something with this sensor value.
            int lux_int=(int)lux;
            if(lux_int!=0){
                lux_public=1;
            }


        }

        @Override
        protected void onResume() {
            super.onResume();
            sensorMgr.registerListener(this, sensorLight, SensorManager.SENSOR_DELAY_NORMAL);
        }

        @Override
        protected void onPause() {
            super.onPause();
            sensorMgr.unregisterListener(this);
        }
    }



        @Override



        // Do something here if sensor accuracy changes.



public void check(View View){
    text2.setText("saved value: "+lux_public);

}
*/
    public void click(View View){
        lux_public_check=0;
        if(sensorMgr.getDefaultSensor(Sensor.TYPE_LIGHT)!=null){
            text1.setText("Light Sensor is present");
            String name=sensorLight.getName();

            for(int i=0;i<100;i++){
lux_public_check=lux_public_check+(int)lux_public;
            }
            lux_public_check=lux_public_check/100;
            text2.setText("sensor value: "+lux_public_check);
        }
        else{
            text1.setText("NO LIGHT SENSOR FOUND");
        }







    }



     @Override
     public void onSensorChanged(SensorEvent event) {
         lux_public=event.values[0];
         text1.setText("value: "+ lux_public);
     }

     @Override
     public void onAccuracyChanged(Sensor sensor, int accuracy) {

     }
 }

