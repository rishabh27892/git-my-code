package com.rishabh.chauhan.fibonacci;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity_fibonacci extends AppCompatActivity {
    Button check;
    TextView f1,f2,print;
    EditText number;
    double GoldRat=1.618034;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity_fibonacci);
        check=(Button)findViewById(R.id.check);
        f1=(TextView) findViewById(R.id.f1);
        f2=(TextView) findViewById(R.id.f2);
        print=(TextView) findViewById(R.id.print);
        number=(EditText) findViewById(R.id.number);
    }


    void check (View view){
        int prime_check,num=10;
        boolean prime=true;

        double number_do;
        int number_int;

       number_int=Integer.parseInt(number.getText().toString());
        number_do=number_int;
//Golden rule
        int fib_gold= (int) ((Math.pow(GoldRat,number_do)-Math.pow((1-GoldRat),number_do))/Math.sqrt(5));
//Iteration rule
        int a=0,b=1,x=1;
        for (int i=0;i<number_int;i++){
            if(i>0){
                x=a+b;
                a=b;
                b=x;
            }
        }
        int fib_it=x;
        f1.setText("FIB_GOLD: "+fib_gold);
        f2.setText("FIB_IT: "+fib_it);
//check if
        if(fib_it%3==0){
            print.setText("Print : Buzz");
            num=0;
        }
        if(fib_it%5==0){
            print.setText("Print : Fizz");
            num=0;
        }
        if(fib_it%15==0){
            print.setText("Print : BuzzFizz");
            num=0;
        }
        if(num!=0){
            print.setText("Print : "+fib_it);
        }

        for(int i=2;i<=fib_it/2;i++){
            prime_check=fib_it%i;
            if(prime_check==0){
                prime=false;
            }
        }
        if(prime==true){
            print.setText("Print : FizzBuzz");
        }

    }


}
