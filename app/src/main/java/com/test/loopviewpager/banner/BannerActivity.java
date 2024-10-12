package com.test.loopviewpager.banner;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.test.loopviewpager.R;
import com.test.loopviewpager.utils.Utils;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;

import java.util.List;

/**
 * banner
 * https://github.com/youth5201314/banner
 * 需要实现的功能：
 * 1.轮播
 * 2.支持R2L
 * 3.动态更新某个item进行显示
 * 4.动态添加，删除
 */
public class BannerActivity extends AppCompatActivity {

    private Banner banner;
    private ImageAdapter mImageAdapter;
    private List<DataBean> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        banner = findViewById(R.id.banner);
    }

    public void onTest(View v){
        banner.addBannerLifecycleObserver(this)//添加生命周期观察者
                .setAdapter(mImageAdapter = new ImageAdapter(mDatas = DataBean.getTestData()))
                .setIndicator(new CircleIndicator(this));
    }

    /**
     * Real对应的是banner实际的数据
     * 比如说需要轮播数据是5个，转换前是5个，转换后是7个，在第0个位置前面插入了最后一条数据，在第5个数据之后插入了第一条数据
     *
     * @param v
     */
    public void onTest1(View v){
        int realCount = banner.getRealCount(); // 原始数据数量，真实的数量
        int itemCount = banner.getItemCount(); // 转换后的数量，真实的数量
        int childCount = banner.getChildCount();
        int index = banner.getCurrentItem(); // 获取到的是当前item转换后的index，不如当前item是第0个，返回1，因为插入了一条假数据在第0条数据前面，真实的
        Utils.log("realCount: " + realCount + "  itemCount: " + itemCount + "  childCount: " + childCount);
        Utils.log("index: " + index);
        int realPosition = mImageAdapter.getRealPosition(index); // 获取banner显示item的显示index，比如指示器显示第0个item，返回0，假的
        Utils.log("realPosition: " + realPosition);
    }

    public void onTest2(View v){
        mDatas.get(0).imageRes = R.drawable.image6;
        mImageAdapter.notifyItemChanged(1);
        mImageAdapter.notifyItemChanged(6);
    }

}
