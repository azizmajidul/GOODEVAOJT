package com.stmikbanisaleh.pemantauantidur;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.stmikbanisaleh.pemantauantidur.Session.SessionManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class FTW extends AppCompatActivity implements View.OnClickListener {
    //Button btn1, btn2, btn3, btn4, btn5, btn6;
    private TimePicker timePicker1;
    private TimePicker timePicker2;
    private RadioGroup que1, que2, que3;
    private RadioButton btn1, btn2, btn3, btn4, btn5, btn6;
    SessionManager sessionManager;
    private EditText min, max;

    private Calendar calendar;
    private String format = "";
    private TextView time, time2, time3;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f_t_w);
        sessionManager = new SessionManager(this);

        btnSubmit = findViewById(R.id.submit);

        que1 = findViewById(R.id.question1);
        que2 = findViewById(R.id.question2);
        que3 = findViewById(R.id.question3);
        min = findViewById(R.id.min);
        max = findViewById(R.id.max);
        que1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int cekId) {
                switch (cekId){
                    case R.id.btn1:
                        btn1.getText(); break;
                    case R.id.btn2: btn2.getText();
                }
            }
        });

        que2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int cekid) {
                switch(cekid){
                    case R.id.btn3: btn3.getText(); break;
                    case  R.id.btn4: btn4.getText(); break;
                }
            }
        });
        que3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.btn5: btn5.getText();
                    case R.id.btn6: btn6.getText();
                        String value = btn5.getText().toString();
                }
            }
        });

        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5  = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);

        btn1.setOnClickListener(this::onClick);
        btn2.setOnClickListener(this::onClick);
        btn3.setOnClickListener(this::onClick);
        btn4.setOnClickListener(this::onClick);
        btn5.setOnClickListener(this::onClick);
        btn6.setOnClickListener(this::onClick);
        String cek  ;

        timePicker1 = (TimePicker) findViewById(R.id.timePicker1);
        timePicker2 = (TimePicker) findViewById(R.id.timepicker2);
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        timePicker1.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                time.setText( + hourOfDay + " : " + minute);
            }
        });
        timePicker2.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                time2.setText( + hourOfDay + " : " + minute);
            }
        });
        time = (TextView) findViewById(R.id.time1);
        time2 = (TextView) findViewById(R.id.time2);
        time3 = (TextView) findViewById(R.id.time3);
        SimpleDateFormat format = new SimpleDateFormat("hh:mm", Locale.US);
        try {
            Date date1 = format.parse(String.valueOf("08:10"));
            Date date2 = format.parse(String.valueOf("4:10"));
            long millis = date1.getTime() - date2.getTime();
            int hours = (int) (millis / (1000 * 60 * 60));
            int mins = (int) ((millis / (1000 * 60)) % 60);
            String diff = hours + ":" + mins;
            System.out.println(diff);
            time3.setText(diff);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actFTW();
            }
        });
    }
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn1 : btn1.setBackgroundColor( getResources().getColor(R.color.questionYes)); btn2.setBackgroundColor(getResources().getColor(R.color.noChoice)); break;
            case R.id.btn2 : btn2.setBackgroundColor(getResources().getColor(R.color.questionNo)); btn1.setBackgroundColor(getResources().getColor(R.color.noChoice)); break;
            case R.id.btn3 : btn3.setBackgroundColor( getResources().getColor(R.color.questionYes)); btn4.setBackgroundColor(getResources().getColor(R.color.noChoice)); break;
            case R.id.btn4 : btn4.setBackgroundColor(getResources().getColor(R.color.questionNo)); btn3.setBackgroundColor(getResources().getColor(R.color.noChoice)); break;
            case R.id.btn5 : btn5.setBackgroundColor( getResources().getColor(R.color.questionYes)); btn6.setBackgroundColor(getResources().getColor(R.color.noChoice)); break;
            case R.id.btn6 : btn6.setBackgroundColor(getResources().getColor(R.color.questionNo)); btn5.setBackgroundColor(getResources().getColor(R.color.noChoice)); break;
        }
    }

    private void actFTW(){

        String URL = "https://ojt.globaldeva.com/public/api/auth/tambah-fit-to-work";
        String mToken = sessionManager.getStringData("access_token");
        final String sleep_start = (String) time.getText();
        final String sleep_end = (String) time2.getText();
        final String total_slep = "30";
        final String heartMin = String.valueOf(min.getText());
        final String heartMax = String.valueOf(max.getText());
        final int Average = Integer.parseInt(heartMax) - Integer.parseInt(heartMin);
        final  String question2 = String.valueOf(que2.getCheckedRadioButtonId());
        final  String question1 = String.valueOf(que1.getCheckedRadioButtonId());
        final  String question3 = String.valueOf(que3.getCheckedRadioButtonId());
        final String shif = "1";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(FTW.this, "Succes Add Data Customer", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(FTW.this, MainActivity.class);startActivity(intent); finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(FTW.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                //headers.put("Content-Type", "application/json; charset=UTF-8");
                headers.put("Authorization", "Bearer" + mToken);
//                Log.d("token", "hasilnya Adalah   " + mToken);
                return headers;
            }
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> form = new HashMap<>();
                form.put("time_sleep_start", sleep_start);
                form.put("time_sleep_end", sleep_end);
                form.put("total_sleep", total_slep);
                form.put("heart_rate_min",heartMin);
                form.put("heart_rate_max", heartMax);
                form.put("average_bpm", String.valueOf(Average));
                form.put("quest1", question1);
                form.put("quest2", question2);
                form.put("ques3", question3);
                form.put("shift", shif);
                Log.d("tag", form.toString());
                return form;
            }

        };
        requestQueue.add(request);






    }


}