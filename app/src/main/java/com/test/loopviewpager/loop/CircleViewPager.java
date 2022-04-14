package com.test.loopviewpager.loop;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.test.loopviewpager.R;
import com.test.loopviewpager.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CircleViewPager extends LinearLayout implements ViewPager.OnPageChangeListener{
    
    private Context mContext;
    
    CircleViewDinoPager vpSpecialActs;

    /**
     * 指示器
     */
    protected LinearLayout llyDinoIndicator;

    /**
     * 当前显示挂件index
     */
    private int currentDinoPos;

    /**
     * 指示器集合
     */
    private ArrayList<ImageView> dinoIndicators;

    private ViewAdapter viewAdapter;
    
    private CircleViewCallBack mCallBack;

    public void setCallBack(CircleViewCallBack mCallBack) {
        this.mCallBack = mCallBack;
    }

    public CircleViewPager(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public CircleViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public CircleViewPager(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(@NonNull Context context) {
        LayoutInflater.from(context).inflate(R.layout.dino_layout_circle_pager_view, this, true);
        vpSpecialActs = findViewById(R.id.vp_special_acts);
        llyDinoIndicator = findViewById(R.id.ll_dino_indicator);
        this.mContext = context;
        vpSpecialActs.setPageChangeListener(this);
        vpSpecialActs.setAdapter(viewAdapter = new ViewAdapter(context));
        initIndicator(viewAdapter.getRealCount());
    }

    /**
     * 初始化指示器
     */
    private void initIndicator(int count) {
        currentDinoPos = 0;
        if (llyDinoIndicator == null || count <= 1 || mContext == null) {
            llyDinoIndicator.setVisibility(View.GONE);
            return;
        }
        llyDinoIndicator.removeAllViews();
        llyDinoIndicator.setVisibility(View.VISIBLE);
        dinoIndicators = new ArrayList<>();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < count; i++) { // 2个页面才显示，1个页面不显示
            int dots_margin = ScreenUtils.dip2px(mContext, 2.5f);
            ImageView indicator = new ImageView(mContext);
            indicator.setLayoutParams(params);
            indicator.setImageResource(R.drawable.dino_bg_action_dot_unselected);
            indicator.setPadding(dots_margin, 0, dots_margin, 0);
            dinoIndicators.add(indicator);
            llyDinoIndicator.addView(indicator);
        }
        selectIndicator(0);
    }


    /**
     * 切换指示器
     */
    private void selectIndicator(int index) {
        try {
            if (dinoIndicators != null && dinoIndicators.size() > 0) {
                dinoIndicators.get(currentDinoPos).setImageResource(R.drawable.dino_bg_action_dot_unselected);
                if (index >= 0 && index < dinoIndicators.size()) {
                    currentDinoPos = index;
                    dinoIndicators.get(currentDinoPos).setImageResource(R.drawable.dino_bg_action_dot_selected);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkShowPage(CircleData page) {
        if (null != viewAdapter) {
            if (viewAdapter.checkShowPage(page)) {
                vpSpecialActs.setCurrentItem(viewAdapter.getCount() - 1);
            }
            selectIndicator(viewAdapter.getRealCount() - 1);
        }
    }

    public boolean checkHidePage(CircleData page) {
        if (null != viewAdapter) {
            boolean b = viewAdapter.checkHidePage(page);
            selectIndicator(viewAdapter.getRealCount() - 1);
            return b;
        }
        return true;
    }
    
    public void changeViewData(int id, String s) {
        if (null != viewAdapter) {
            viewAdapter.changeViewData(id, s);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        
    }

    @Override
    public void onPageSelected(int position) {
        selectIndicator(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public static class CircleData {
        public int mId;
        public Object data;
        
        public CircleData() {
            
        }
        
        public CircleData(int id) {
            this.mId = id;
        }

        public CircleData(CircleData data) {
            if(data != null) {
                this.mId = data.mId;
                this.data = data.data;
            }
        }

        public Object getData() {
            return data;
        }
        
        public static CircleData copy(CircleData data) {
            return data != null ? new CircleData(data) : null;
        }
        
    }

    private class ViewAdapter extends PagerAdapter {
        private final Context context;
        private final List<CircleData> mDatas = new ArrayList<>();
        private final List<CircleData> datas = new ArrayList<>();
        private final List<View> views = new ArrayList<>();
        private final Map<Integer, List<View>> mViews = new HashMap<>();

        private ViewAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            if(mCallBack != null && datas != null && datas.size() > 0) {
                CircleData data = datas.get(position);
                View view = mCallBack.getView(mContext, data);
                views.add(view);
                if (mViews.get(data.mId) == null) {
                    mViews.put(data.mId, new ArrayList<View>());
                }
                mViews.get(data.mId).add(view);
                container.addView(view);
                return view;
            } 
            return null;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            if (object instanceof View && views != null) {
                container.removeView((View) object);
                views.remove(object);
            }
        }

        /**
         * 返回值有三种，
         * POSITION_UNCHANGED  默认值，位置没有改变
         * POSITION_NONE       item已经不存在
         * position            item新的位置
         * 当position发生改变时这个方法应该返回改变后的位置，以便页面刷新。
         */
        @Override
        public int getItemPosition(@NonNull Object object) {
            for (int i = 0; i < views.size(); i++) {
                if (views.get(i) == object) {
                    return i;
                }
            }
            return POSITION_NONE;
        }
        
        public int getRealCount() {
            return mDatas != null ? mDatas.size() : 0;
        }

        public boolean checkShowPage(CircleData data) {
            if (mViews != null && !isContains(data.mId)) {
                mDatas.add(data);
                addCircleData();
                notifyDataSetChanged();
                initIndicator(getRealCount());
                if(mDatas.size() > 1)
                    vpSpecialActs.autoPlay();
                return true;
            }
            return false;
        }

        public boolean checkHidePage(CircleData page) {
            if (page != null && isContains(page.mId)) {
                mDatas.remove(page);
                addCircleData();

//                page.destroy();
                initIndicator(getRealCount());
                notifyDataSetChanged();
                if(mDatas.size() <= 1)  
                    vpSpecialActs.autoPlay();
            }
            return 0 == getCount();
        }
        
        private void addCircleData() {
            if(mDatas != null && mDatas.size() > 0 && datas != null) {
                if(mDatas.size() > 1) {
                    datas.clear();
                    datas.addAll(mDatas);
                    datas.add(0, CircleData.copy(mDatas.get(mDatas.size() - 1)));
                    datas.add(CircleData.copy(mDatas.get(0)));
                } else {
                    datas.clear();
                    datas.addAll(mDatas);
                }
                if(views != null) {
                    views.clear();
                }
                if(mViews != null) {
                    mViews.clear();
                }
            } else {
                datas.clear();
            }
        }
        
        private boolean isContains(int id) {
            if(mDatas != null && mDatas.size() > 0) {
                for(int i = 0; i < mDatas.size(); i ++) {
                    CircleData data = mDatas.get(i);
                    if(data != null && data.mId == id)
                        return true;
                }
            }
            return false;
        }

        public void changeViewData(int id, String s) {
            if(mViews != null && mViews.size() > 0) {
                List<View> vs = mViews.get(id);
                if(vs != null && vs.size() > 0) {
                    for(int i = 0; i < vs.size(); i ++) {
                        View v = vs.get(i);
                        if(v != null && v instanceof IViews) {
                            ((IViews)v).setTip(s);
                        }
                    }
                    
                }
            }
                
        }
        
    }
    
    public interface CircleViewCallBack {
        View getView(Context context, CircleData data);
        void destroyView(View view);
    }
}
