package com.rishabh.chauhan.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements OnClickListener
{ Button button1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1 =(Button)findViewById(R.id.button1);
        button1.setOnClickListener(this);
    }
    private void Do(){
        button1Click();

    }
    private void button1Click(){
       // startActivity(new Intent("com.rishabh.chauhan.myapplication.class23"));


    }

    @Override
    public void onClick(View v) {
       switch(v.getId())
       {
           case R.id.button1:
               button1Click();
               break;


       }
    }
}
