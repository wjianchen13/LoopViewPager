package com.test.loopviewpager.custom;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.test.loopviewpager.R;
import com.test.loopviewpager.custom.view.MessageFragment1;
import com.test.loopviewpager.custom.view.MessageFragment2;
import com.test.loopviewpager.custom.view.MessageFragment3;

import java.util.List;

public class CustomViewPagerActivity extends AppCompatActivity {

    private List<String> titleList;
    private MyViewPager viewPager;
    private TextView tv1;
    private TextView tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customviewpager);
        viewPager = (MyViewPager)findViewById(R.id.vp_test);
        viewPager.setAdapter(new MessageViewPagerAdapter(getSupportFragmentManager(), titleList));
        viewPager.setOffscreenPageLimit(1);
        viewPager.addOnPageChangeListener(onPageChangeListener);
        tv1 = (TextView)findViewById(R.id.tv_1);
        tv2 = (TextView)findViewById(R.id.tv_2);
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0);
            }
        });
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1);
            }
        });
    }

    private static class MessageViewPagerAdapter extends MyFragmentPagerAdapter {
        private List<String> titleList;

        MessageViewPagerAdapter(FragmentManager fm, List<String> titleList) {
            super(fm);
            this.titleList = titleList;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:  // 消息
                    return MessageFragment1.newInstance();

                case 1:  // 好友
                    return MessageFragment2.newInstance();

                case 2:  // 好友
                    return MessageFragment3.newInstance();

                default:
                    return MessageFragment1.newInstance();
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titleList.get(position).toUpperCase();
        }

        @Override
        public int getCount() {
            return titleList == null ? 3 : titleList.size();
        }
    }

    private MyViewPager.OnPageChangeListener onPageChangeListener = new MyViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//            System.out.println("============================> position: " + position + "  positionOffset: " + positionOffset + "   positionOffsetPixels: " + positionOffsetPixels);
        }

        @Override
        public void onPageSelected(int position) {
//            System.out.println("============================> position: " + position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            System.out.println("============================> state: " + (state == MyViewPager.SCROLL_STATE_IDLE ? "SCROLL_STATE_IDLE"
                    : state == MyViewPager.SCROLL_STATE_DRAGGING ? "SCROLL_STATE_DRAGGING"
                    : "SCROLL_STATE_SETTLING"));
        }
    };
}
