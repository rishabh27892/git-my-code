package com.rishabh.chauhan.myapplication;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView textView = new TextView(this);
        textView.setText("something new");
        textView.setTextColor(Color.RED);
        textView.setTextSize(48);
        textView.setAllCaps(true);
        setContentView(textView);

    }
}
