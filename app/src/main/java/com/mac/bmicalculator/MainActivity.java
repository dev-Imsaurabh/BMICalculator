package com.mac.bmicalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class MainActivity extends AppCompatActivity {
    private EditText height,weight;
    private Spinner kgSpinner,cmSpinner;
    private TextView results;
    private String unit;
    private Button resultBtn;
    private String category ,BMI;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {

            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        height=findViewById(R.id.height);
        weight=findViewById(R.id.weight);
        cmSpinner=findViewById(R.id.cmSpinner);
        kgSpinner=findViewById(R.id.kgSpinner);
        results=findViewById(R.id.results);
        resultBtn=findViewById(R.id.resultBtn);



        String[] heightUnit = new String[]{"cm","m"};
        cmSpinner.setAdapter(new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,heightUnit));
        cmSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                unit=cmSpinner.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        resultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkValidation();


            }
        });
    }

    private void BMIinm() {

        float takeHeight =Float.parseFloat( height.getText().toString());
        float takeWeight =Float.parseFloat(weight.getText().toString());

        float finalResults = takeWeight/(takeHeight*takeHeight);

        BMI = String.valueOf(Math.round(finalResults*100)/100.0);

        getCategory();




        results.setText(BMI + " and you are "+ category +"!");







    }


    private void BMIinCm() {

        float takeHeight =Float.parseFloat( height.getText().toString());
        float takeWeight =Float.parseFloat(weight.getText().toString());

        float convertHeight=takeHeight/100;


        float finalResults = takeWeight/(convertHeight*convertHeight);

        BMI = String.valueOf(Math.round(finalResults*100)/100.0);


        getCategory();


        results.setText(BMI + " and you are "+ category+"!");




    }

    private void getCategory() {
        if(Float.parseFloat(BMI)<18.5) {

            category = "UnderWeight";
        }else if(Float.parseFloat(BMI)>=18.5 && Float.parseFloat(BMI)<=24.9) {
            category = "Normal Healthy weight";
        }else if(Float.parseFloat(BMI)>=25.0 && Float.parseFloat(BMI)<=29.9){
                category="Overweight";

        }else if(Float.parseFloat(BMI)>=30.0 && Float.parseFloat(BMI)<=39.9){
            category="Obese";

        }else if(Float.parseFloat(BMI)>=40){
            category="Morbidly obese";
        }
    }

    private void checkValidation() {
        if (height.getText().toString().isEmpty() || weight.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter height and weight", Toast.LENGTH_SHORT).show();

        } else {

            if(unit.equals("cm")){
                BMIinCm();
            }else{
                BMIinm();
            }

        }

    }



}