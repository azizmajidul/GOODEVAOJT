package com.stmikbanisaleh.pemantauantidur;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OTP_Verification extends AppCompatActivity {
    private TextView otpVerify; String otp;
    private String getNama, getEmail, getPassword;
    private EditText otpET1, otpET2, otpET3, otpET4, otpET5, otpET6;
    private String verificationId;
    ProgressBar progressBar;

    //true after 60 secon
    private boolean resendEnabled = false;

    private int resendTIME = 60;
    private Button btnVerify;
    private TextView resendButton;
    private int selectedETPosition = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p__verification);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#000000\">" + getString(R.string.otp) + "</font>"));

        getEmail = getIntent().getStringExtra("emialET");
        getNama = getIntent().getStringExtra("nameET");
        getPassword = getIntent().getStringExtra("passwordET");
        otp = getIntent().getStringExtra("phoneET");

        otpVerify = findViewById(R.id.phoneET);

        otpVerify.setText(String.format("+62-%s", otp));

        progressBar = findViewById(R.id.progressBar);
        otpET1 = findViewById(R.id.otpET1);
        otpET2 = findViewById(R.id.otpET2);
        otpET3 = findViewById(R.id.otpET3);
        otpET4 = findViewById(R.id.otpET4);
        otpET5 = findViewById(R.id.otpET5);
        otpET6 = findViewById(R.id.otpET6);

         btnVerify = findViewById(R.id.verifyButton);
         resendButton = findViewById(R.id.resendButton);
        otpET1.addTextChangedListener(textWatcher);
        otpET2.addTextChangedListener(textWatcher);
        otpET3.addTextChangedListener(textWatcher);
        otpET4.addTextChangedListener(textWatcher);
        otpET5.addTextChangedListener(textWatcher);
        otpET6.addTextChangedListener(textWatcher);

        //default open keyboard at otpET1
        showKeyboard(otpET1);

        //star resend count dwown timer
        startCountDownTimer();

        verificationId = getIntent().getStringExtra("verificationId");

        //
        resendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(resendEnabled){
                    startCountDownTimer();
                }

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+62" +getIntent().getStringExtra("phoneET"),
                        60,
                        TimeUnit.SECONDS,
                        OTP_Verification.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {

                                Toast.makeText(OTP_Verification.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onCodeSent(@NonNull String newVerificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                               verificationId = newVerificationId;
                               Toast.makeText(OTP_Verification.this, "OTP Sent", Toast.LENGTH_SHORT).show();
                            }
                        }
                );
            }
        });
        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String generateOTP =
                        otpET1.getText().toString()
                                +otpET2.getText().toString()
                                +otpET3.getText().toString()
                                +otpET4.getText().toString()
                                +otpET5.getText().toString()
                                +otpET6.getText().toString();

                if(verificationId != null){
                    progressBar.setVisibility(View.VISIBLE);
                    btnVerify.setVisibility(View.INVISIBLE);
                    PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                            verificationId,
                            generateOTP
                    );
                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressBar.setVisibility(View.GONE);
                                    btnVerify.setVisibility(View.VISIBLE);
                               if(task.isSuccessful()){
                                   Intent intent = new Intent(getApplicationContext(), ChooseGender.class);
                                   intent.putExtra("nameET", getNama);
                                   intent.putExtra("emailET", getEmail);
                                   intent.putExtra("phoneET", otp);
                                   intent.putExtra("passwordET", getPassword);
                                   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                   startActivity(intent);
                               }else{
                                   Toast.makeText(OTP_Verification.this, "The Verification entered was invalid", Toast.LENGTH_SHORT).show();
                               }
                                }
                            });
                }



            }
        });

    }

    private  void showKeyboard(EditText otpET){
        otpET.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(otpET, InputMethodManager.SHOW_IMPLICIT);
    }

    private void startCountDownTimer(){
        resendEnabled = false;
        resendButton.setTextColor(Color.parseColor("#99000000"));

        new CountDownTimer(resendTIME*1000, 1000){

            @Override
            public void onTick(long l) {
                resendButton.setText("Resend Code("+(l/1000)+")");
            }

            @Override
            public void onFinish() {
                resendEnabled = true;
                resendButton.setText("Resend Code ");
                resendButton.setTextColor(getResources().getColor(R.color.SignIn));

            }
        }.start();

    }


    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }


        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {

            if(editable.length() > 0){
                if(selectedETPosition == 0){
                    selectedETPosition =1;
                    showKeyboard(otpET2);

                }else  if(selectedETPosition == 1){
                    selectedETPosition = 2;
                    showKeyboard(otpET3);

                }else  if(selectedETPosition == 2){
                    selectedETPosition = 3;
                    showKeyboard(otpET4);

                } else if(selectedETPosition ==3){
                    selectedETPosition = 4;
                    showKeyboard(otpET5);
                } else if(selectedETPosition ==4){
                    selectedETPosition =5;
                    showKeyboard(otpET6);
                }
            }

        }
    };

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_DEL){
            if(selectedETPosition == 5){
                selectedETPosition = 4;
                showKeyboard(otpET5);
            } else if(selectedETPosition ==4){
                selectedETPosition = 3;
                showKeyboard(otpET4);
            } else
            if(selectedETPosition == 3){
                selectedETPosition =2;
                showKeyboard(otpET3);

            } else if(selectedETPosition == 2){
                selectedETPosition = 1;
                showKeyboard(otpET2);

            } else if(selectedETPosition == 1){
                selectedETPosition = 0;
                showKeyboard(otpET1);
            }

            return  true;

        }else {
            return super.onKeyUp(keyCode, event);

        }

    }
}