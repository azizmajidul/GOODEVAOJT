package com.stmikbanisaleh.pemantauantidur;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ChooseGoal extends AppCompatActivity {

    private Button submitGoal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_goal);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#000000\">" + getString(R.string.choseGender) + "</font>"));
        submitGoal= findViewById(R.id.submitGoals);
        submitGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ChooseGoal.this, "coab", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ChooseGoal.this, FitYourBody.class); startActivity(intent); finish();
            }
        });
    }
}