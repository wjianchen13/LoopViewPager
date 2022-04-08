package com.test.loopviewpager.test1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.test.loopviewpager.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 实现方式：
 * 在数据第一个位置添加最后一个数据，最后一个位置添加第一个数据
 * 切换到最后一个View时自动切换第二个页面，切换到第一个页面是自动切回倒数第二个页面
 */
public class Test1Activity extends AppCompatActivity {

    private AutoScrollViewPager viewPager ;
    private ViewPagerAdapter adapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);
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

        startActivity(new Intent(Test1Activity.this, OhterActivity.class));
    }
}
