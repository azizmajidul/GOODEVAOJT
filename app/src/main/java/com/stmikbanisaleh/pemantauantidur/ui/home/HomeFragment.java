package com.stmikbanisaleh.pemantauantidur.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.lzyzsd.circleprogress.DonutProgress;
import com.stmikbanisaleh.pemantauantidur.FTW;
import com.stmikbanisaleh.pemantauantidur.ModelDashboard;
import com.stmikbanisaleh.pemantauantidur.R;
import com.stmikbanisaleh.pemantauantidur.Session.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {
    private Button btnFTW;
    private TextView textView1, textView2;
    private DonutProgress progressBar;
    private SessionManager sessionManager;
    private List<ModelDashboard> list;


    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        sessionManager = new SessionManager(getContext());
        btnFTW = v.findViewById(R.id.buttonFTW);
        progressBar = v.findViewById(R.id.progressChart);
        textView1 = v.findViewById(R.id.textHour);
        textView2 = v.findViewById(R.id.textHeart);


        btnFTW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), FTW.class);startActivity(intent);
            }
        });
        getDataDashboard();
        return v;
    }

    private void getDataDashboard(){

        String URL = "https://ojt.globaldeva.com/public/api/auth/dashboard";
        String mToken =   sessionManager.getStringData("access_token");;
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    String cart = object.getString("data_chart");
                    String total_sleep = object.getString("total_sleep");
                    String bpm = object.getString("bpm");

                    progressBar.setText(cart);
                    textView1.setText(total_sleep);
                    textView2.setText(bpm);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json; charset=UTF-8");
                headers.put("Authorization", "Bearer" + mToken);
                Log.d("token", "hasilnya Adalah   " + mToken);
                return headers;
            }
        };
        requestQueue.add(request);
    }
}