package com.test.loopviewpager.change;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.test.loopviewpager.R;
import com.test.loopviewpager.base.TestFragment0;
import com.test.loopviewpager.base.TestFragment1;

import java.util.ArrayList;
import java.util.List;


public class ChangeFragment extends Fragment implements View.OnClickListener{

    protected ViewPager vpTest;
    protected CommonPageAdapter mAdapter;
    protected View view;
    private String test;
    private List<Fragment> fragments = new ArrayList<>();

    private Button btnUpdate;
    private Button btnAdd1;
    private Button btnAdd2;

    private Button btnDel1;
    private Button btnDel2;

    public static ChangeFragment newInstance(String test) {
        Bundle args = new Bundle();
        ChangeFragment fragment = new ChangeFragment();
        args.putString("test", test);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            test = bundle.getString("test");
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_change, null);
        initData();
        initView(view);
        return view;
    }

    private void initData() {
//        fragments = new ArrayList<>();
//        fragments.add(TestFragment0.newInstance("0"));
        fragments.clear();
        fragments.add(TestFragment1.newInstance("1"));
//        fragments.add(TestFragment2.newInstance("2"));
    }
    
    /**
     * 初始化界面
     * @param
     * @return
     */
    private void initView(View view) {
        if(view != null) {
            vpTest = view.findViewById(R.id.vp_test);

            btnUpdate = view.findViewById(R.id.btn_update);
            btnUpdate.setOnClickListener(this);
            btnAdd1 = view.findViewById(R.id.btn_add1);
            btnAdd1.setOnClickListener(this);
            btnAdd2 = view.findViewById(R.id.btn_add2);
            btnAdd2.setOnClickListener(this);

            btnDel1 = view.findViewById(R.id.btn_del1);
            btnDel1.setOnClickListener(this);
            btnDel2 = view.findViewById(R.id.btn_del2);
            btnDel2.setOnClickListener(this);
            vpTest.setOverScrollMode(View.OVER_SCROLL_NEVER);

            if (mAdapter == null) {
                mAdapter = new CommonPageAdapter(getChildFragmentManager(), fragments);
            }
            vpTest.setAdapter(mAdapter);
        }
    }
    
    /**
     * 删除一个fragment
     */
    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_update:
                fragments = new ArrayList<>();
                fragments.clear();
                fragments.add(TestFragment0.newInstance("0"));
                fragments.add(TestFragment1.newInstance("1"));
                mAdapter.updatePage(fragments);
            
                break;
            case R.id.btn_add1:
                mAdapter.addPage(0, TestFragment0.newInstance("0"));
                vpTest.setCurrentItem(0);
                break;
                
            case R.id.btn_add2:
                mAdapter.addPage(TestFragment0.newInstance("0"));
                break;
                
            case R.id.btn_del1:
                mAdapter.delPage(0);
                break;
                
            case R.id.btn_del2:
                mAdapter.delPage(1);
                break;
        }
    }
    
//    /**
//     * ViewPageAdapter
//     */
//    private class ViewPagerAdapter extends FragmentPagerAdapter {
//
//        int num;
//
//        public ViewPagerAdapter(FragmentManager fm, int numFragments) {
//            super(fm);
//            num = numFragments;
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            Fragment fragment = null;
//            switch (position) {
//                case 0:
//                    fragment = TestFragment0.newInstance("0");
//                    break;
//                case 1:
//                    fragment = TestFragment1.newInstance("1");
//                    break;
//
//                case 2:
//                    fragment = TestFragment2.newInstance("2");
//                    break;
//                default:
//                    fragment = TestFragment0.newInstance("0");
//                    break;
//            }
//            return fragment;
//        }
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            switch (position) {
//                case 0:
//                    return "资料";
//
//                case 1:
//                    return "视频";
//
//                default:
//                    return "资料";
//            }
//        }
//
//        @Override
//        public int getCount() {
//            return num;
//        }
//
//        @Override
//        public void destroyItem(ViewGroup container, int position, Object object) {
//            //如果注释这行，那么不管怎么切换，page都不会被销毁
//            // super.destroyItem(container, position, object);
//        }
//    }
}
