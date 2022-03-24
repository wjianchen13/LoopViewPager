package com.test.loopviewpager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AutoScrollViewPager viewPager ;
    private ViewPagerAdapter adapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (AutoScrollViewPager) findViewById(R.id.viewpager);
        adapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(adapter);
        viewPager.setAutoPlay(true);
    }

    public void onTest(View v){
        List<String> contents = new ArrayList<>();
        contents.add("1");
        contents.add("2");
        contents.add("3");
        contents.add("4");
        contents.add("5");
        String str = "6";
        if(contents.contains(str))
            contents.remove(str);
        System.out.println("====================> contents: " + contents.toString());

        startActivity(new Intent(MainActivity.this, OhterActivity.class));
    }
}
