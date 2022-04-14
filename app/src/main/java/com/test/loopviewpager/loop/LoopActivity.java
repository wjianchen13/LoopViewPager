package com.test.loopviewpager.loop;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.test.loopviewpager.R;


public class LoopActivity extends AppCompatActivity implements CircleViewPager.CircleViewCallBack {

    private CircleViewPager cvTest;
    private RecyclerView rvTest;
    SpecialActsAssist as;

    private CircleViewPager.CircleData mFirstData;
    private CircleViewPager.CircleData mSecondData;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loop);
        cvTest = findViewById(R.id.cv_test);
        rvTest = findViewById(R.id.rv_test);
        cvTest.setCallBack(this);
        as = new SpecialActsAssist(this, rvTest);
        mFirstData = new CircleViewPager.CircleData(SpecialActsAssist.ID_FIRST);
        mSecondData = new CircleViewPager.CircleData(SpecialActsAssist.ID_SECOND);
    }
    
    public void onAdd1(View v) {
        cvTest.checkShowPage(mFirstData);
    }

    public void onAdd2(View v) {
        cvTest.checkShowPage(mSecondData);
    }

    public void onDel1(View v) {
        cvTest.checkHidePage(mFirstData);
    }

    public void onDel2(View v) {
        cvTest.checkHidePage(mSecondData);
    }

    public void onAdd3(View v) {
        as.checkFirstShowBean();
    }

    public void onAdd4(View v) {
        as.checkSecondShowBean();
    }

    public void onDel3(View v) {
        as.hideFirstBean();
    }

    public void onDel4(View v) {
        as.hideSecondBean();
    }

    public void onChange1(View v) {
        as.changeFirstBean("first");
    }

    public void onChange2(View v) {
        as.changeSecondtBean("second");
    }

    public void onChange3(View v) {

    }

    public void onChange4(View v) {

    }

    @Override
    public View getView(Context context, CircleViewPager.CircleData data) {
        if(data != null) {
            if(data.mId == SpecialActsAssist.ID_FIRST) {
                TestView1 v1 = new TestView1(context);
//                v1.setOnClickListener();

                return v1;
            } else if(data.mId == SpecialActsAssist.ID_SECOND) {
                TestView2 v2 = new TestView2(context);
//                v2.setOnClickListener(SpecialActsAssist.this);

                return v2;
            }

        }
        return null;
    }

    @Override
    public void destroyView(View view) {
        if(view != null) { // 添加统一接口处理
//                                view.setCallback(null);
        }
    }

}
