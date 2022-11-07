package com.stmikbanisaleh.pemantauantidur;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ChooseGender extends AppCompatActivity implements View.OnClickListener {

    EditText bornDate;
    CardView genderman;
    CardView genderwoman;
    DatePicker datePicker;
    DatePickerDialog datePickerDialog;
    final Calendar myCalendar = Calendar.getInstance();
    Button btnSubmit;
    String Gender1; String Gender2, born;
    TextView man, woman;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_gender);
        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#000000\">" + getString(R.string.choseGender) + "</font>"));

        genderman = findViewById(R.id.Man);
        genderwoman = findViewById(R.id.Woman);
        genderman.setOnClickListener(this::onClick);
        genderwoman.setOnClickListener(this::onClick);


        man = findViewById(R.id.textMan);
        woman = findViewById(R.id.texWoman);


        btnSubmit = findViewById(R.id.btnSumbitGender);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Born = bornDate.getText().toString();
                String nama = getIntent().getStringExtra("nameET");
                String email = getIntent().getStringExtra("emailET");
                String phone = getIntent().getStringExtra("phoneET");
                String password = getIntent().getStringExtra("passwordET");
                Intent i = new Intent(ChooseGender.this, ChooseGoal.class);
                i.putExtra("born", Born);
                i.putExtra("man", Gender1);
                i.putExtra("woman", Gender2);
                i.putExtra("name", nama);
                i.putExtra("emaail", email);
                i.putExtra("phone", phone);
                i.putExtra("password", password);
                startActivity(i); finish();
            }
        });

        bornDate = findViewById(R.id.bornDate);
        bornDate.setText(getTodayDate());

        inidatePicker();


    }

    private String getTodayDate(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        month = month+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return makeDataString(day, month, year);
    }
    private void inidatePicker(){
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month+1;
                String date = makeDataString(day, month, year);
                bornDate.setText(date);
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);


    }

    private String makeDataString(int day, int month, int year) {
        return   day+ "-" + getFormat(month) +  "-" +year;
    }

    private String getFormat(int month){
        if(month ==1)
            return  "JAN";
        if(month ==2)
            return  "FEB";
        if(month ==3)
            return  "MAR";
        if(month ==4)
            return  "APR";
        if(month ==5)
            return  "MAY";
        if(month ==6)
            return  "JUN";
        if(month ==7)
            return  "JUL";
        if(month ==8)
            return  "AUG";
        if(month ==9)
            return  "SEPT";
        if(month ==10)
            return  "OCT";
        if(month ==11)
            return  "NOV";
        if(month ==12)
            return  "DEC";
        return "JAN";

    }
    public void openDatePicker(View view){
        datePickerDialog.show();

    }

    //getvalue from cardview


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.Man: Gender1 = man.getText().toString(); genderman.setBackgroundColor(getColor(R.color.SignIn)); genderwoman.setBackgroundColor(getColor(R.color.colorPrimary));break;
            case R.id.Woman :  Gender2 = woman.getText().toString(); genderwoman.setBackgroundColor(getColor(R.color.SignIn)); genderman.setBackgroundColor(getColor(R.color.colorPrimary));break;
        }

    }

//        private void updateLabel(){
//            Locale localeIndonesia = new Locale("id", "ID");
//            String myFormat="MM/dd/yy";
//            SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, localeIndonesia);
//            bornDate.setText(dateFormat.format(myCalendar.getTime()));
//        }

}