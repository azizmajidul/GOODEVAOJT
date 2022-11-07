package com.stmikbanisaleh.pemantauantidur;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class OnBoarding1 extends AppCompatActivity {
    private ViewPager2 pager;
    IntroViewPager introViewPager;
    TabLayout tabIndicator;
    private LinearLayout linearLayoutBoarding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding1);

//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        //tab indicator
        linearLayoutBoarding = findViewById(R.id.indicatorTab);
        final TextView SignImage = findViewById(R.id.SignInBtn);
        final Button btnStarted = findViewById(R.id.button);
        SignImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OnBoarding1.this, Login.class); startActivity(intent);
            }
        });
        btnStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OnBoarding1.this, Register.class); startActivity(intent);

            }
        });



        //fill list
        List<ScreenItem> mList = new ArrayList<>();
        mList.add(new ScreenItem("Welcome \n to Goodeva Apps", "Goodva has workouts on demand that you " +
                "can find based on how much time you have", R.drawable.wfh));
        mList.add(new ScreenItem("Manage Your Rest Time", "Goodva has workouts on demand that you " +
                "can find based on how much time you have", R.drawable.wfh2));
        mList.add(new ScreenItem("Manage Your Rest Time", "Goodva has workouts on demand that you" + "can find based on how much time you have", R.drawable.working));

        //set viewpager
        pager = findViewById(R.id.viewPager);
        introViewPager = new IntroViewPager(this, mList);
        pager.setAdapter(introViewPager);

        setUpTabIndivcator();
        setTabIndicatorTrue(0);
        pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                setTabIndicatorTrue(position);
            }
        });

    }
    private void setUpTabIndivcator(){
        ImageView[] indicator = new ImageView[introViewPager.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8,8,8,8);

        for(int i =0; i< indicator.length; i++){
            indicator[i] = new ImageView(getApplicationContext());
            indicator[i].setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext(), R.drawable.onboarding_indikator_in_active
            ));
           indicator[i].setLayoutParams(layoutParams);
            linearLayoutBoarding.addView(indicator[i]);

        }
    }

    private void setTabIndicatorTrue(int index){
        int chilCount = linearLayoutBoarding.getChildCount();
        for(int i=0; i< chilCount; i++){
            ImageView imageView = (ImageView) linearLayoutBoarding.getChildAt(i);
            if(i== index){
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.onboarding_indicator));
            }else {
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.onboarding_indikator_in_active));

            }
        }


    }
}