package com.stmikbanisaleh.pemantauantidur;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kevalpatel2106.rulerpicker.RulerValuePicker;
import com.kevalpatel2106.rulerpicker.RulerValuePickerListener;

public class FitYourBody extends AppCompatActivity {
    TextView txtHeight, txtWeight;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fit_your_body);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#000000\">" + getString(R.string.fitBody) + "</font>"));
        txtHeight = findViewById(R.id.txtHight);
        txtWeight = findViewById(R.id.txtweight);
        btnSubmit = findViewById(R.id.sumbitFit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FitYourBody.this, Login.class); startActivity(i); finish();
            }
        });

        final RulerValuePicker heightPicker = findViewById(R.id.heightRuler);
        heightPicker.selectValue(200);
        heightPicker.setValuePickerListener(new RulerValuePickerListener() {
            @Override
            public void onValueChange(int selectedValue) {
                txtHeight.setText(selectedValue + "Cm");

            }

            @Override
            public void onIntermediateValueChange(int selectedValue) {
                txtHeight.setText(selectedValue + "Cm");

            }
        });

        final RulerValuePicker weightPicker = findViewById(R.id.weightRuler);
        weightPicker.selectValue(200);
        weightPicker.setValuePickerListener(new RulerValuePickerListener() {
            @Override
            public void onValueChange(int selectedValue) {
                txtWeight.setText(selectedValue+ "Kgs");

            }

            @Override
            public void onIntermediateValueChange(int selectedValue) {
                txtWeight.setText(selectedValue+ "Kgs");

            }
        });
    }
}