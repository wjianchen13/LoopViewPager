package com.test.loopviewpager.banner;

import com.test.loopviewpager.R;

import java.util.ArrayList;
import java.util.List;

public class DataBean {

    public int imageRes;

    public DataBean(int imageRes) {
        this.imageRes = imageRes;
    }

    public static List<DataBean> getTestData(){
        List<DataBean> dataBeans = new ArrayList<>();
        dataBeans.add(new DataBean(R.drawable.image1));
        dataBeans.add(new DataBean(R.drawable.image2));
        dataBeans.add(new DataBean(R.drawable.image3));
        dataBeans.add(new DataBean(R.drawable.image4));
        dataBeans.add(new DataBean(R.drawable.image5));
        return dataBeans;
    }

}
