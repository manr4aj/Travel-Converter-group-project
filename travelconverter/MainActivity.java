package com.example.android.travelconverter;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static String user_language = "", user_currency = "";

    Spinner LangSpinner;
    Spinner CurrencySpinner;
    Toast mToast = null;

    public void setSpinners()
    {
        LangSpinner = findViewById(R.id.lag_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.language_list,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        LangSpinner.setAdapter(adapter);
        LangSpinner.setOnItemSelectedListener(this);
        CurrencySpinner = findViewById(R.id.curr_spinner);
        ArrayAdapter<CharSequence> adapterTwo = ArrayAdapter.createFromResource(this, R.array.currency_list,android.R.layout.simple_spinner_item);
        adapterTwo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        CurrencySpinner.setAdapter(adapterTwo);
        CurrencySpinner.setOnItemSelectedListener(this);

    }

    public void showAToast(String message){
        if (mToast != null) {
            mToast.cancel();
        }
            mToast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
            mToast.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSpinners();

        Button mainScreenButton =  findViewById(R.id.confirm_options);
        mainScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(LangSpinner.getSelectedItemPosition() > 0 && CurrencySpinner.getSelectedItemPosition() > 0)
                {
                    setUserPreferences();
                    Intent mainScreen = new Intent(MainActivity.this, Main2Activity.class);
                    startActivity(mainScreen);
                }
                else { showAToast("Please selected options!"); }//Prevents the toast from being displayed more than Toast.LENGTH_SHORT time
            }
        });
    }

    private void setUserPreferences()
    {
        user_language = String.valueOf(LangSpinner.getSelectedItem());
        user_currency = String.valueOf(CurrencySpinner.getSelectedItem());
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {}

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

}
