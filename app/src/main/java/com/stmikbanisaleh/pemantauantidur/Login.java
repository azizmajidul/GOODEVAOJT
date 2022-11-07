package com.stmikbanisaleh.pemantauantidur;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
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
import com.stmikbanisaleh.pemantauantidur.Session.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    ProgressBar progressBar;
    private boolean passwordShow = false;
    Button loginButton ;
     EditText usernameET ;
     EditText passwordET ;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        sessionManager = new SessionManager(this);
         final ImageView passwordIcon = findViewById(R.id.passwordIcon);
         final TextView signUpBtn = findViewById(R.id.signUp);
         loginButton =  findViewById(R.id.btnSignIn);
         usernameET = findViewById(R.id.usernameET);
         passwordET = findViewById(R.id.passwordET);

         progressBar = findViewById(R.id.progressBar);

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
         signUpBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent i = new Intent(Login.this, Register.class); startActivity(i);

             }
         });
         loginButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 String textEmail = usernameET.getText().toString();
                 String textPassword = passwordET.getText().toString();
                 if(!textEmail.isEmpty() || !textPassword.isEmpty()){
                     loginAction(textEmail, textPassword);
                 }else{
                     usernameET.setError("Please Enter Username!!!");
                     passwordET.setError("Plase Enter Your Password!!!");

                 }

             }
         });

    }

    private void loginAction(String Email, String Password){
        progressBar.setVisibility(View.VISIBLE);
        loginButton.setVisibility(View.INVISIBLE);
        String URL_LOGIN = "https://ojt.globaldeva.com/public/api/auth/login";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.POST, URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(Login.this, "Success", Toast.LENGTH_SHORT).show();
//                progressBar.setVisibility(View.GONE);
//                loginButton.setVisibility(View.VISIBLE);
                try {
                    JSONObject jobj = new JSONObject(response);
                    String message = jobj.getString("msg");
                    String token = jobj.getString("access_token");
                    System.out.println(token);
                    sessionManager.saveStringData("access_token", jobj.getString("access_token"));
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(Login.this, "Login"+message, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent); finish();

                } catch (JSONException e) {
                    e.printStackTrace();
                    progressBar.setVisibility(View.GONE);
                    loginButton.setVisibility(View.VISIBLE);
                    Toast.makeText(Login.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                loginButton.setVisibility(View.VISIBLE);
                Toast.makeText(Login.this, "Gagal", Toast.LENGTH_SHORT).show();
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
                Map<String, String> params = new HashMap<>();
                params.put("email", Email);
                params.put("password", Password);
                //params.put("Bearer", sessionManager.getStringData("access_token"));
                Log.d("tag", params.toString());
                return params;
            }

        };
        requestQueue.add(request);






    }
}