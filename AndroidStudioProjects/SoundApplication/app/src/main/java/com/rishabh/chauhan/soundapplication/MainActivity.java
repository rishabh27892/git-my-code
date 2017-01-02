package com.rishabh.chauhan.soundapplication;

import android.content.Context;
import android.media.AudioManager;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
TextView textView, text2;
    SystemClock systemClock;
    int volume=0;
    AudioManager audioManager;
    Boolean aBoolean=false;
    int fwd=14,lt=13,rt=12,bwd=11;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=(TextView)findViewById(R.id.text1);
        text2 =(TextView)findViewById(R.id.text2);
        audioManager= (AudioManager) getSystemService(Context.AUDIO_SERVICE);




    }
    public void check(View View){
        if (audioManager.isBluetoothA2dpOn()) {
            textView.setText("bluetooth is present");
            // Adjust output for Bluetooth.
        } else if (audioManager.isSpeakerphoneOn()) {
            textView.setText("speaker is present");
            // Adjust output for Speakerphone.
        } else if (audioManager.isWiredHeadsetOn()) {
            textView.setText("headset is present");
            // Adjust output for headsets
        } else {
            textView.setText("wierd");
            // If audio plays and noone can hear it, is it still playing?
        }

    }
    public void setVolume(View view){
        //audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,);
    }
    public  void more(View View){
        audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_RAISE,AudioManager.FLAG_PLAY_SOUND);
        int volume=audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        text2.setText("volume: "+volume);
    }
    public  void less(View View){
        audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_LOWER,AudioManager.FLAG_PLAY_SOUND);
        int volume=audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        text2.setText("volume: "+volume);
    }
    public void volume(View View){
        int volume=audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        text2.setText("volume: "+volume);

    }
    public void fwd(View View){
        while (audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)!=15){
            more(View);
        }
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,fwd,AudioManager.FLAG_PLAY_SOUND);
        volume=audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        text2.setText("volume: "+volume);
        systemClock.sleep(2500);
        while (audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)!=0){
            less(View);
        }
    }
    public void bwd(View View){
        while (audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)!=15){
            more(View);
        }
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,bwd,AudioManager.FLAG_PLAY_SOUND);
        volume=audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        text2.setText("volume: "+volume);
    }
    public void lt(View View){

        //audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,15,AudioManager.FLAG_PLAY_SOUND);

        while (audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)!=15){
            more(View);
        }
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,lt,AudioManager.FLAG_PLAY_SOUND);
        volume=audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        text2.setText("volume: "+volume);
    }
    public void rt(View View){
        while (audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)!=15){
            more(View);
        }
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,rt,AudioManager.FLAG_PLAY_SOUND);
        volume=audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        text2.setText("volume: "+volume);
    }
}
