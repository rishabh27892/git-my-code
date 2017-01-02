package com.rishabh.chauhan.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE ="com.rishabh.chauhan.myapplication"
    public void sendMessage(View View){
        Intent intent =new Intent(this, displayMessageActivity.class);
        EditText editText =(EditText) findViewByID(R.id.edit_message);
        String message =editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);// do something, we will see later
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
