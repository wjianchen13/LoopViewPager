package com.test.loopviewpager.loop;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import com.test.loopviewpager.loop.CircleViewPager.CircleData;
import com.test.loopviewpager.utils.ScreenUtils;

/**
 * Created by rgy on 2021/12/1 0001.
 * special act=聊天列表右下的小图标管理器
 */
public class SpecialActsAssist implements View.OnClickListener {

    private static final String TAG = SpecialActsAssist.class.getSimpleName();

    /**
     * 越小越在底部，按优先级升序排列到容器，翻转布局
     */
    private static final int PRIORITY_WHEEL = 10;
    private static final int PRIORITY_PAY = 11;
    private static final int PRIORITY_POKER = 12;

    /**
     * 循环播放区分
     */
    public static final int ID_FIRST = 1;
    public static final int ID_SECOND = 2;

    @NonNull
    RecyclerView recyclerView;
    @NonNull
    Adapter adapter;
    @NonNull
    Activity activity;

    /**
     * 所有页面
     */
    @NonNull
    private List<SpecialAct<?>> mDatas = new ArrayList<>();

    /**
     * 首充活动
     */
    public static final String FIRST_PAY_TAG = "firstPay_info_cornerWebViewDialogFragment";

    @Nullable SpecialAct<CircleViewPager> mCircleViewPager;

    private CircleData mFirstData;
    private CircleData mSecondData;
    
    public SpecialActsAssist(@NonNull Activity activity,  @NonNull RecyclerView recyclerView){
        this.activity = activity;
        this.recyclerView = recyclerView;
        initRecyclerView(recyclerView);
    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 排序、重新检查显示的页面
     */
    private void _checkPage(boolean isShow, @Nullable SpecialAct bean){

        if (bean == null)
            return;

        int index = -1;

        if (isShow){
            if (!mDatas.contains(bean)){
                /**
                 * 加入队列必须是一个一个执行，找一个比自己小的元素放到这个元素对应的位置即可
                 */
                if (!mDatas.isEmpty()){
                    for (int i = 0; i < mDatas.size(); i++) {
                        if (bean.priority() < mDatas.get(i).priority()) {
                            mDatas.add(i, bean);
                            index = i;
                            break;
                        }
                    }

                    if (index == -1){
                        mDatas.add(bean);
                        index = mDatas.size() - 1;
                    }

                } else {
                    mDatas.add(bean);
                    index = 0;
                }
            }

            if (index > -1){
                recyclerView.setVisibility(View.VISIBLE);
                adapter.notifyItemInserted(index);
            }

        } else {
            index = mDatas.indexOf(bean);
            mDatas.remove(bean);

            if (index > -1){
                recyclerView.setVisibility(View.VISIBLE);
                adapter.notifyItemRemoved(index);
            }
        }


        if (mDatas.isEmpty()){
            recyclerView.removeAllViews();
            recyclerView.setVisibility(View.GONE);
            /**
             * 如果都隐藏，那么重新布局
             * recyclerview上的item不会自动刷新
             */
        }

    }

    private void checkShowPage(@Nullable SpecialAct<?> bean){
        _checkPage(true, bean);
    }

    private void checkHidePage(@Nullable SpecialAct<?> bean) {
        _checkPage(false, bean);
    }

    @NonNull
    private SpecialAct<?> getByViewType(int viewType){
        for (int i = 0; i < mDatas.size(); i++) {
            if (mDatas.get(i).priority() == viewType)
                return mDatas.get(i);
        }
        return new SpecialAct<View>(){

            @NonNull
            @Override
            public View _createView(@NonNull Context context, int index) {
                return new View(context);
            }

            @Override
            public int priority() {
                return 0;
            }

            @Override
            protected void recycle(@NonNull View view) {

            }
        };
    }

    public void clear(){

    }
    
    /***********************************************************************************************
     * 测试循环轮播，状态保持，动态添加和移除item
     **********************************************************************************************/
    // 显示ViewPager
    private void checkCircleViewPager(final CircleData data) {
        if (mCircleViewPager == null) {
            mCircleViewPager = new SpecialAct<CircleViewPager>() {
                @NonNull
                @Override
                protected CircleViewPager _createView(@NonNull final Context context, int index) {
                    CircleViewPager circleViewPager = new CircleViewPager(context);
                    circleViewPager.checkShowPage(data);
                    circleViewPager.setCallBack(new CircleViewPager.CircleViewCallBack() {
                        @Override
                        public View getView(Context context1, CircleData data) {
                            if(data != null) {
                                if(data.mId == ID_FIRST) {
                                    TestView1 v1 = new TestView1(context1);
                                    v1.setOnClickListener(SpecialActsAssist.this);

                                    return v1;
                                } else if(data.mId == ID_SECOND) {
                                    TestView2 v2 = new TestView2(context1);
                                    v2.setOnClickListener(SpecialActsAssist.this);

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
                    });
                    return circleViewPager;
                }

                @Override
                public int priority() {
                    return PRIORITY_WHEEL;
                }

                @Override
                protected void recycle(@NonNull CircleViewPager view) {

                }
            };
        }
        checkShowPage(mCircleViewPager);
    }
    
    /**
     * 需要显示、接收数据时
     */
    public void checkFirstShowBean() {
        if (mFirstData == null){
            mFirstData = new CircleData(ID_FIRST);
        }
        checkCircleViewPager(mFirstData);
        checkShowPage(mFirstData);
    }

    public void hideFirstBean() {
        if (checkHidePage(mFirstData)) {
            hideCircleViewPager();
        }
    }

    /**
     * 需要显示、接收数据时
     */
    public void checkSecondShowBean(){
        if (mSecondData == null){
            mSecondData = new CircleData(ID_SECOND);
        }
        checkCircleViewPager(mSecondData);
        checkShowPage(mSecondData);
    }
    
    public void hideSecondBean() {
        if (checkHidePage(mSecondData)) {
            hideCircleViewPager();
        }
    }


    public void checkShowPage(CircleData page) {
        if (null != mCircleViewPager) {
            CircleViewPager view = mCircleViewPager.getView();
            if (null != view) {
                view.checkShowPage(page);
            }
        }
    }

    public boolean checkHidePage(CircleData page) {
        if (null != mCircleViewPager) {
            CircleViewPager view = mCircleViewPager.view;
            if (null != view) {
                return view.checkHidePage(page);
            }
        }
        return true;
    }
    
    public void changeFirstBean(String s) {
        if (null != mCircleViewPager) {
            CircleViewPager view = mCircleViewPager.getView();
            if (null != view) {
                view.changeViewData(ID_FIRST, s);
            }
        }
    }

    public void changeSecondtBean(String s) {
        if (null != mCircleViewPager) {
            CircleViewPager view = mCircleViewPager.getView();
            if (null != view) {
                view.changeViewData(ID_SECOND, s);
            }
        }
    }
    
    private void hideCircleViewPager() {
        checkHidePage(mCircleViewPager);
    }

    /************************************************************************************************
     *
     * 初始化recyclerView
     *
     ***********************************************************************************************/
    private void initRecyclerView(@NonNull RecyclerView view){
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, true){
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }

            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        view.setLayoutManager(layoutManager);

        view.addItemDecoration(new SpacesItemDecoration(ScreenUtils.dip2px(activity, 10)));
        view.setItemAnimator(getItemAnimator());

        view.setAdapter(adapter = new Adapter());
    }

    private class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

        private final String A_TAG = TAG + "_adapter";

        private class ViewHolder extends RecyclerView.ViewHolder {
            View view;
            public ViewHolder(View view) {
                super(view);
                this.view = view;
            }
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getByViewType(viewType).createView(parent.getContext(), viewType);
//            ViewParent p = view.getParent();
//            if (p != null)
//                ((ViewGroup)p).removeView(view);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        @Override
        public int getItemViewType(int position) {
            return mDatas.get(position).priority();
        }

        @Override
        public void onViewRecycled(@NonNull ViewHolder holder) {
            super.onViewRecycled(holder);
        }

        @Override
        public void onViewAttachedToWindow(@NonNull ViewHolder holder) {
            super.onViewAttachedToWindow(holder);
        }
    }

    private static class SpacesItemDecoration extends RecyclerView.ItemDecoration {

        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, RecyclerView parent, @NonNull RecyclerView.State state) {
//            outRect.left = space;
//            outRect.right = space;
//            outRect.bottom = space;

            // Add top margin only for the first item to avoid double space between items
            if (parent.getChildAdapterPosition(view) > 0) {
                outRect.bottom = space;
            } else if (parent.getChildAdapterPosition(view) == -1){
                if (view.getTag() instanceof Integer){
                    outRect.bottom = (int) view.getTag();
                }
            }

            view.setTag(outRect.bottom);
        }
    }

    private RecyclerView.ItemAnimator getItemAnimator(){
        RecyclerView.ItemAnimator animator = new DefaultItemAnimator();
//        animator.setAddDuration(100);
//        animator.setChangeDuration(100);
        animator.setMoveDuration(160);
//        animator.setRemoveDuration(100);
        return animator;
    }

    /************************************************************************************************
     *
     * 相关类，接口，优先级
     *
     ***********************************************************************************************/
    private static abstract class SpecialAct<T extends View> {

        @Nullable
        private T view;

        @NonNull
        public T createView(@NonNull Context context, int index){

            if (view != null){
                ViewParent parent = view.getParent();
                if (parent != null)
                    ((ViewGroup)parent).removeView(view);

                recycle(view);
            }

            return view = _createView(context, index);
        }

        @NonNull
        protected abstract T _createView(@NonNull Context context, int index);

        public abstract int priority();

        /**
         * 被废弃的view
         * @param view
         */
        protected abstract void recycle(@NonNull T view);

        @Nullable
        public T getView(){
            return view;
        }
    }
}
