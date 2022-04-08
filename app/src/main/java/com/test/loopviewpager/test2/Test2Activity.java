package com.test.loopviewpager.test2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.test.loopviewpager.R;
import com.test.loopviewpager.test1.OhterActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * https://github.com/yanzm/LoopViewPager
 * 好像是直接创建出一个新的View，这对于内部有数据管理的View来说会导致数据不同步
 */
public class Test2Activity extends AppCompatActivity {
    private LoopViewPager viewPager ;
    private LoopViewPagerAdapter adapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
        viewPager = findViewById(R.id.viewpager);
        adapter = new LoopViewPagerAdapter(this);
        viewPager.setAdapter(adapter);
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

        startActivity(new Intent(Test2Activity.this, OhterActivity.class));
    }
}
