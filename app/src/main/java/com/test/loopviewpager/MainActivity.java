package com.test.loopviewpager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.test.loopviewpager.change.ChangeActivity;
import com.test.loopviewpager.custom.CustomViewPagerActivity;
import com.test.loopviewpager.loop.LoopActivity;
import com.test.loopviewpager.normal.ViewPagerActivity;
import com.test.loopviewpager.test1.AutoScrollViewPager;
import com.test.loopviewpager.test1.OhterActivity;
import com.test.loopviewpager.test1.Test1Activity;
import com.test.loopviewpager.test1.ViewPagerAdapter;
import com.test.loopviewpager.test2.Test2Activity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onTest1(View v){
        startActivity(new Intent(MainActivity.this, Test1Activity.class));
    }

    public void onTest2(View v){
        startActivity(new Intent(MainActivity.this, Test2Activity.class));
    }

    /**
     * 清除内容
     * @param
     * @return
     */
    public void onNormal(View v) {
        startActivity(new Intent(MainActivity.this, ViewPagerActivity.class));
    }

    /**
     * 改变内容
     * @param
     * @return
     */
    public void onChange(View v) {
        startActivity(new Intent(MainActivity.this, ChangeActivity.class));
    }

    /**
     * 入口循环ViewPager
     * @param
     * @return
     */
    public void onLoop(View v) {
        startActivity(new Intent(MainActivity.this, LoopActivity.class));
    }

    /**
     * ViewPage源码查看
     * @param
     * @return
     */
    public void onCustom(View v) {
        startActivity(new Intent(MainActivity.this, CustomViewPagerActivity.class));
    }

}


