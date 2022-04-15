package com.test.loopviewpager.custom;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.test.loopviewpager.R;

public class CustomViewPagerMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_viewpager_main);
    }
    
    public void onTest(View v) {
        startActivity(new Intent(CustomViewPagerMainActivity.this, CustomViewPagerActivity.class));
    }
}
