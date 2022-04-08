package com.test.loopviewpager.test1;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.test.loopviewpager.R;

import java.util.ArrayList;
import java.util.List;

public class OhterActivity extends AppCompatActivity {

    private View vTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);
        vTest = findViewById(R.id.v_test);
    }

    public void onTest(View v){
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)vTest.getLayoutParams();


        System.out.println("====================> width: " + params.width  + "  height: " + params.height);

        System.out.println("====================> density: " + (getResources().getDisplayMetrics().density));
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        return (int) (dpValue * getDensity(context) + 0.5f);
    }

    public static float getDensity(Context context) {
        try {
            return context.getResources().getDisplayMetrics().density;
        } catch (Exception e){
            e.printStackTrace();
        }
        return 2.0f;
    }
}
