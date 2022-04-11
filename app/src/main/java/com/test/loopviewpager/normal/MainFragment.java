package com.test.loopviewpager.normal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.test.loopviewpager.R;
import com.test.loopviewpager.base.TestFragment0;
import com.test.loopviewpager.base.TestFragment1;
import com.test.loopviewpager.base.TestFragment2;

import java.util.List;

public class MainFragment extends Fragment {

    protected ViewPager vpTest;
    protected ViewPagerAdapter mAdapter;
    protected View view;
    private String test;

    private TextView tvTest;

    public static MainFragment newInstance(String test) {
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
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
        view = inflater.inflate(R.layout.fragment_main, null);
        initView(view);
        return view;
    }

    /**
     * 初始化界面
     * @param
     * @return
     */
    private void initView(View view) {
        if(view != null) {
            vpTest = view.findViewById(R.id.vp_test);
            vpTest.setOverScrollMode(View.OVER_SCROLL_NEVER);
            if (mAdapter == null) {
                mAdapter = new ViewPagerAdapter(getChildFragmentManager(), 3);
            }
            vpTest.setAdapter(mAdapter);
            tvTest = view.findViewById(R.id.tv_test);
            tvTest.setText(test);
        }
    }

    /**
     * 清除界面状态
     * @param
     * @return
     */
    public void clearState() {
        if(tvTest != null) {
            tvTest.setText("clear");
        }
        List<Fragment> fragments = getChildFragmentManager().getFragments();
        if(fragments != null) {
            for(int i = 0; i < fragments.size(); i ++) {
                if (fragments.get(i) instanceof TestFragment0) {
                    ((TestFragment0) (fragments.get(i))).clearState();
                } else if(fragments.get(i) instanceof TestFragment1) {
                    ((TestFragment1) (fragments.get(i))).clearState();
                } else if(fragments.get(i) instanceof TestFragment2) {
                    ((TestFragment2) (fragments.get(i))).clearState();
                }
            }
        }
    }

    /**
     * 重新设置数据
     * @param
     * @return
     */
    public void loadingData(String test) {
        if(!this.test.equals(test)) {
            clearState();
        }
    }

    /**
     * ViewPageAdapter
     */
    private class ViewPagerAdapter extends FragmentPagerAdapter {

        int num;

        public ViewPagerAdapter(FragmentManager fm, int numFragments) {
            super(fm);
            num = numFragments;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = TestFragment0.newInstance("0");
                    break;
                case 1:
                    fragment = TestFragment1.newInstance("1");
                    break;

                case 2:
                    fragment = TestFragment2.newInstance("2");
                    break;
                default:
                    fragment = TestFragment0.newInstance("0");
                    break;
            }
            return fragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "资料";

                case 1:
                    return "视频";

                default:
                    return "资料";
            }
        }

        @Override
        public int getCount() {
            return num;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //如果注释这行，那么不管怎么切换，page都不会被销毁
            // super.destroyItem(container, position, object);
        }
    }
}
