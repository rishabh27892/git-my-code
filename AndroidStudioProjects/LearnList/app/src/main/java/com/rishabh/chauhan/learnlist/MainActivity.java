package com.rishabh.chauhan.learnlist;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class MainActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[] num={"1","2","3","4","5"};
        ArrayAdapter<String>adapter =ArrayAdapter<String>(this,R.layout.activity_listview.list_item,num);
    }
}
