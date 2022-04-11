package com.test.loopviewpager.change;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.test.loopviewpager.R;


public class ChangeActivity extends AppCompatActivity {

    private ChangeFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);
        fragment = ChangeFragment.newInstance("main");
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_test, fragment).commit();
    }

}
