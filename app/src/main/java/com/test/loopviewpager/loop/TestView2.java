package com.test.loopviewpager.loop;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.test.loopviewpager.R;

/**
 * 转盘提示，倒计时，计数
 */
public class TestView2 extends FrameLayout implements View.OnClickListener, IViews {

    public static final int STATUS_WHEEL_DEFAULT = 0;
    public static final int STATUS_WHEEL_WAITING = 1;
    public static final int STATUS_WHEEL_DRAWING = 2;
    
    private Context dinoContext;
    
    TextView tvTip;

    /**
     * 字体大小
     */
    private int mTextSize;

    /**
     * 字体颜色
     */
    private int mTextColor = Color.BLACK;
    
    private BaccaratBean mData;
    
    private WheelTipCallBack mCallBack;
    private int mStatus = STATUS_WHEEL_DEFAULT;
    private String mText = "";

    public void setCallBack(WheelTipCallBack mCallBack) {
        this.mCallBack = mCallBack;
    }

    public TestView2(Context dinoContext) {
        super(dinoContext);
        initView(dinoContext, null);
    }

    public TestView2(Context dinoContext, AttributeSet attrs) {
        super(dinoContext, attrs);
        initView(dinoContext, attrs);
    }

    public TestView2(Context dinoContext, AttributeSet attrs, int defStyleAttr) {
        super(dinoContext, attrs, defStyleAttr);
        initView(dinoContext, attrs);
    }
    
    private void initView(Context context, AttributeSet attrs) {
        this.dinoContext = context;
        View view = inflate(context, R.layout.view_test2, this);
        tvTip = findViewById(R.id.tv_tip);
    }
    
    public void setText(String text) {
        if(tvTip != null)
            tvTip.setText(text);
    }

    @Override
    public void onClick(View view) {
        if(mCallBack != null)
            mCallBack.onClick(view);
    }

    @Override
    public void setTip(String s) {
        setText(s);
    }

    public interface WheelTipCallBack {
        void onClick(View v);
    }
    
    public class BaccaratBean {
        private int status;
        private String text;

        public BaccaratBean(int status, String text) {
            
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}