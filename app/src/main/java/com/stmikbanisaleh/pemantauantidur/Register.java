package com.stmikbanisaleh.pemantauantidur;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.WindowMetrics;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.stmikbanisaleh.pemantauantidur.Session.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Register extends AppCompatActivity {
    private boolean passwordShow = false;
    EditText nameET, emailET, phoneET, passwordET;
    Button registerBtn; TextView SignIn;
    ImageView passwordIcon;
    private ProgressBar progressBar;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        sessionManager = new SessionManager(this);

        getSupportActionBar().hide();
        nameET = findViewById(R.id.nameET);
        emailET = findViewById(R.id.emailET);
        phoneET =  findViewById(R.id.phoneET);
        passwordET = findViewById(R.id.passwordET);
        registerBtn = findViewById(R.id.SubmitRegister);
        SignIn = findViewById(R.id.signIn);
        passwordIcon = findViewById(R.id.passwordIcon);
        progressBar = findViewById(R.id.progressBar);

        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);finish();
            }
        });
        passwordIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(passwordShow){
                    passwordShow = false;
                    passwordET.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordIcon.setImageResource(R.drawable.showeye);
                }else {
                    passwordShow= true;
                    passwordET.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    passwordIcon.setImageResource(R.drawable.hideenpassword);
                }
                passwordET.setSelection(passwordET.length());
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBar.setVisibility(View.VISIBLE);
                registerBtn.setVisibility(View.INVISIBLE);
                final String getPhoneNumber = phoneET.getText().toString();
                final String getName = nameET.getText().toString();
                final String getEmail = emailET.getText().toString();
                final String getPassword = passwordET.getText().toString();

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+62" + phoneET.getText().toString(),
                        60,
                        TimeUnit.SECONDS,
                        Register.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                progressBar.setVisibility(View.GONE);
                                registerBtn.setVisibility(View.VISIBLE);

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                progressBar.setVisibility(View.GONE);
                                registerBtn.setVisibility(View.VISIBLE);
                                Toast.makeText(Register.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                progressBar.setVisibility(View.GONE);
                                registerBtn.setVisibility(View.VISIBLE);
                                Register();

                                Intent intent = new Intent(Register.this, OTP_Verification.class);
                                intent.putExtra("phoneET", getPhoneNumber);
//                                intent.putExtra("nameET", getName);
//                                intent.putExtra("emialET", getEmail);
//                                intent.putExtra("passwordET", getPassword);
                                intent.putExtra("verificationId", verificationId);
                                startActivity(intent); finish();
                            }
                        }
                );





            }
        });



    }
//    private void computeWindowSizeClasses() {
//        WindowMetrics metrics =
//
//        // Use widthWindowSizeClass and heightWindowSizeClass.
//    }
    private void ActRegister(){
        String Nama = nameET.getText().toString().trim();
        String Phone = phoneET.getText().toString().trim();
        String Email = emailET.getText().toString().trim();
        String Password = phoneET.getText().toString().trim();
        String Role = "User";

        //String URL = "http://192.168.1.239/Monitoring-Sleep/public/api/auth/register";
        String URL = "https://ojt.globaldeva.com/public/api/auth/register";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(Register.this, "Succes Registrasi", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Register.this, OTP_Verification.class);startActivity(intent); finish();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Register.this, "Gagal Registrasi", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> form = new HashMap<>();
                form.put("name", Nama);
                form.put("email", Email);
                form.put("password", Password);
                form.put("tlp", Phone);
                form.put("role", Role);
                Log.d("Tag", form.toString());
                return form;
            }
        };
        requestQueue.add(request);
    }

    private void Register(){
        final String Nama = nameET.getText().toString();
        final String Email = emailET.getText().toString();
        final String Phone = phoneET.getText().toString();
        final  String Password = passwordET.getText().toString();
        final String Role = "User";
        String URLRegistrasi = "https://ojt.globaldeva.com/public/api/auth/register";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, URLRegistrasi, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                sessionManager.createSession(Nama, Email, Phone, Role);
                Toast.makeText(Register.this, "Berhasil", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse response = error.networkResponse;
                if (error instanceof ServerError && response != null){
                    try {
                        String res = new String(response.data,
                                HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        JSONObject obj = new JSONObject(res);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> form = new HashMap<>();
                form.put("name", Nama);
                form.put("email", Email);
                form.put("tlp", Phone);
                form.put("password", Password);
                form.put("role", Role);
                Log.d("Tag", form.toString());
                return form;
            }
        };
        requestQueue.add(request);


    }

}