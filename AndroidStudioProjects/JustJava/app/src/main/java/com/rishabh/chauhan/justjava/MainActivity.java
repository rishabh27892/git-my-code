package com.rishabh.chauhan.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import java.lang.*;
import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends ActionBarActivity {
    int quant =0;
    int price =5;
    int extra=0;
    int checksug=0;
    int checkcarm=0;
    int checkwhip=0;

    String add ="no topping";
    String drink="Regular coffee(5$/qty)";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        Editable name;
        EditText enter = (EditText) findViewById(R.id.enter);
        if (add==" "&&add=="  "&&add=="   "){
            add= "no toppin";

        }
        name =enter.getText();

        int totalPrice=quant*(price+extra);
        if (add.contains("")){
            add.concat("no topping");
        }
        String price_msg=name + " your total price for " + quant + drink + "with "+add +" is "+ totalPrice;
        display(quant);
        //displayPrice(price_msg);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for" + name);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

        displayMessage(price_msg);


    }
    public void increment(View view) {
        quant=quant+1;
        display(quant);
    }
    public void cap(View view) {
        price=6;
        drink="Cappacino Coffee(6$/qty)";
        Button capp = (Button) findViewById(R.id.cap);
        capp.setText("Cappacino Added");
    }
    public void exp(View view) {
        price=7;
        drink="Expresso Coffee(7$/qty)";
        Button exp = (Button) findViewById(R.id.exp);
        exp.setText("Expresso Added");
    }
    public void col(View view) {
        price=8;
        drink="Cold-Coffee(8$/qty)";
        Button col = (Button) findViewById(R.id.col);
        col.setText("Cold-Coffee Added");
    }
    public void decrement(View view) {
        if (quant==0) {
            display(quant);
        }
        else {
           quant=quant-1;
            display(quant);
        }
    }
    public void sug(View view) {

        if (add=="no topping"){
            add="";
        }
        CheckBox check1 = (CheckBox) findViewById(R.id.check1);
        if (check1.isChecked()) {
            boolean a = add.contains("extra sugar");
            if (a) {
                checksug = 0;

            } else {
                checksug = 1;

            }
        }
        else{
            checksug=0;
            add=add.replace("extra sugar","");
            extra=extra-1;

        }


    if (checksug==1){
        add=add+"extra sugar ";
        extra=extra+1;
    }

    }


    public void whip(View view) {
        if (add=="no topping"){
            add="";
        }
        CheckBox check1 = (CheckBox) findViewById(R.id.check2);
        if (check1.isChecked()) {
            boolean a = add.contains("whip topping");
            if (a) {
                checkwhip = 0;

            } else {
                checkwhip = 1;
            }
        }
        else{
            checkwhip=0;
            add=add.replace("whip topping ","");
            extra=extra-1;

        }


        if (checkwhip==1){
            add=add+"whip topping ";
            extra=extra+1;
        }
    }
    public void carm(View view) {
        if (add=="no topping"){
            add="";
        }
        CheckBox check1 = (CheckBox) findViewById(R.id.check3);
        if (check1.isChecked()) {
            boolean a = add.contains("carmel shot");
            if (a) {
                checkcarm = 0;
                extra=extra-1;
            } else {
                checkcarm = 1;
            }
        }
        else{
            checkcarm=0;
            add=add.replace("carmel shot","");
            extra=extra-1;

        }


        if (checkcarm==1){
            add=add+"carmel shot ";
            extra=extra+1;
        }
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.price_text_view);
        orderSummaryTextView.setText(message);
    }/**
     * This method displays the given price on the screen.
     */
    private void displayPrice(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }
}