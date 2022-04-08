package com.test.loopviewpager.test2;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;

import com.test.loopviewpager.R;


/**
 * Created by WZH on 2018/4/14.
 */

public class LoopViewPagerAdapter extends PagerAdapter {
    private String[] resIds = {
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523772064850&di=dd3588fdf916fce1527f7b53cc536293&imgtype=0&src=http%3A%2F%2Fimage.tianjimedia.com%2FuploadImages%2F2012%2F088%2F9KF35LBMFO74.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523772064854&di=165a16c1167b38b067b3c82cb830c347&imgtype=0&src=http%3A%2F%2Fattachments.gfan.com%2Fforum%2Fattachments2%2F201303%2F18%2F110902j0e5zjwj0yutt00j.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523772064877&di=dac68b830070fb6dbf1a9d71fc6b2772&imgtype=0&src=http%3A%2F%2Fi3.download.fd.pchome.net%2Fg1%2FM00%2F0D%2F18%2FoYYBAFS8sGGIUf9wAAJfpTlwpsgAACPGwFaL6cAAl-9928.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523772294591&di=9633b23326710637bda3b511c87e0fdb&imgtype=jpg&src=http%3A%2F%2Fimg0.imgtn.bdimg.com%2Fit%2Fu%3D1163084700%2C3470735618%26fm%3D214%26gp%3D0.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523772064849&di=7502440dd81352d3e3d584eb810e0acc&imgtype=0&src=http%3A%2F%2Fimage.tianjimedia.com%2FuploadImages%2F2012%2F265%2F5432CKKPF561_1000x500.jpg",
    };

    private Context context ;
    public LoopViewPagerAdapter(Context context) {
        this.context = context ;
    }

    @Override
    public int getCount() {
        return resIds.length;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = View.inflate(context, R.layout.vp_item, null);
        TextView textView = view.findViewById(R.id.tv_test);
        if(position % 2 == 0) {
            view.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent));
        } else {
            view.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
        }
        textView.setText("页面" +  position);
        //添加到容器中
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
}
