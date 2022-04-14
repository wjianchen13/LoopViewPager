package com.test.loopviewpager.loop;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

/**
 * 循环 viewpager
 */
public class CircleViewDinoPager extends ViewPager implements Handler.Callback {
    private static final int DINO_MSG_DELAY = 6; // viewpager延时切换
    private static final boolean DINO_IS_STOP_LOOP = false;
    private static final long DINO_LOOP_TIME = 3000 ; //自动播放时间
    private static final int DINO_MSG_LOOP = 5; // 滚动

    private int currentDinoIndex = 1 ; //当前位置
    private PagerAdapter pagerDinoAdapter;
    private Handler dinoHandler = new Handler(this);
    private OnPageChangeListener onPageChangeDinoListener;

    /**
     * 需要添加一个延时，否则从最后切换到最前会闪一下，或者从最前切换到最后会闪一下
     */
    private void setCurrentIndexDelay() {
        if(dinoHandler != null) {
            Message msg = dinoHandler.obtainMessage(DINO_MSG_DELAY);
            dinoHandler.sendMessageDelayed(msg, 200);
        }
    }

    /**
     * 播放，根据autoplay
     */
    public void autoPlay(){
        if(DINO_IS_STOP_LOOP) {
            stopPlay();
            return ;
        }
        setAutoPlay(true);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopPlay();
        if(dinoHandler != null) {
            dinoHandler.removeCallbacksAndMessages(null);
            dinoHandler = null;
        }
    }

    /**
     * 设置是否自动播放
     */
    public void setAutoPlay(boolean play) {
        //是否自动播放
        if(dinoHandler == null)
            dinoHandler = new Handler(this);
        if(dinoHandler.hasMessages(DINO_MSG_LOOP)) {
            dinoHandler.removeMessages(DINO_MSG_LOOP);
        }
        if (play){
            Message msg = dinoHandler.obtainMessage(DINO_MSG_LOOP);
            dinoHandler.sendMessageDelayed(msg, DINO_LOOP_TIME);
        }
    }

    public CircleViewDinoPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch(action) {
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP: // 这里移动的时候会触发
                break;
            case MotionEvent.ACTION_DOWN:
                stopPlay();
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 设置适配器
     */
    public void setAdapter(PagerAdapter adpter){
        super.setAdapter(adpter);
        pagerDinoAdapter = adpter;
        init();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
//        autoPlay();
    }

    /**
     * 初始化viewpager
     */
    private void init() {
        if (pagerDinoAdapter == null){
            return;
        }
        setCurrentItem(currentDinoIndex);
        setOffscreenPageLimit(10); // 设置红缓存的页面数，活动最大页数 6 + 轮播首尾页数 2 + PK 1 + 旧活动 1
        int pageMargin = 0;
        setPageMargin(pageMargin); // 设置2张图之前的间距。
        setOverScrollMode(View.OVER_SCROLL_NEVER);
        addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,int positionOffsetPixels) {
                if(onPageChangeDinoListener != null) {
                    onPageChangeDinoListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) { // 这个方法有坑呀，如果viewpager 很小，每次移动到viewpager范围之外，这个方法不回调
                if(state == ViewPager.SCROLL_STATE_IDLE){ // 动画还没有结束就回调了这个方法，所以加个延时
                    setCurrentIndexDelay();
                } else if(state == ViewPager.SCROLL_STATE_DRAGGING){
                    stopPlay();
                }
                if(onPageChangeDinoListener != null) {
                    onPageChangeDinoListener.onPageScrollStateChanged(state);
                }
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0){
                    currentDinoIndex = pagerDinoAdapter.getCount() - 2;
                    if(currentDinoIndex != getCurrentItem()) {
                        onPageScrollStateChanged(ViewPager.SCROLL_STATE_IDLE);
                    }
                }else if (position == pagerDinoAdapter.getCount() - 1){
                    currentDinoIndex = 1 ;
                    if(currentDinoIndex != getCurrentItem()) {
                        onPageScrollStateChanged(ViewPager.SCROLL_STATE_IDLE);
                    }
                }else {
                    currentDinoIndex = position ;
                }

                if(onPageChangeDinoListener != null) {
                    onPageChangeDinoListener.onPageSelected(currentDinoIndex - 1); // 因为头部插入了一个，所以-1
                }
            }
        });
    }

    /**
     * 滑动到活动区域外部就不在响应事件处理
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(getLeft() - getWidth() > ev.getX() || getTop() > ev.getY() || getRight() + getWidth() < ev.getX() || getBottom() < ev.getY()) {
            ev.setAction(MotionEvent.ACTION_UP);
            super.onTouchEvent(ev);
            return false;
        }
        
        return super.onTouchEvent(ev);
    }

    public void setViewPager() {
        currentDinoIndex = 1;
    }

    public void setPageChangeListener(OnPageChangeListener onPageChangeListener) {
        this.onPageChangeDinoListener = onPageChangeListener;
    }

    /**
     * 取消播放
     */
    public void stopPlay(){
        setAutoPlay(false);
    }

    @Override
    public boolean handleMessage(@NonNull Message msg) {
        switch (msg.what) {
            case DINO_MSG_DELAY:
                setCurrentItem(currentDinoIndex,false);  // 切换到首尾的时候自动改变
                break;
            case DINO_MSG_LOOP:
                setCurrentItem(++currentDinoIndex);
                autoPlay();
                break;
            default :
                break;
        }
        return false;
    }
}