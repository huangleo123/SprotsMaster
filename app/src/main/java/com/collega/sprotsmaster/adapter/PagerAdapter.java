package com.collega.sprotsmaster.adapter;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.List;

/**PagerAdapter
 * Created by dd on 2018/4/11.
 */

public class PagerAdapter extends android.support.v4.view.PagerAdapter  {
    private List<View> mViews;
    private Activity mContext;

    public PagerAdapter(Activity context, List<View> views){
        this.mViews = views;
        this.mContext = context;

    }

    @Override
    public int getCount() {
        return mViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }
    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        ((ViewPager) arg0).removeView(mViews.get(arg1));
    }
    @Override
            public Object instantiateItem(View arg0, int arg1) {
                 ((ViewPager) arg0).addView(mViews.get(arg1), 0);
              /*    if (arg1 == mViews.size() - 1) {
                          Button enterBtn = (Button) arg0.findViewById(R.id.bt_go);
                     enterBtn.setOnClickListener(new View.OnClickListener() {
                               @Override
                                    public void onClick(View v) {
                                          // 将isShow保存为true，并进入登录界面
                                       //PreferenceUtil.setBoolean(mContext, PreferenceUtil.SHOW_GUIDE, true);
                                      //   initLog();
                                   //跳转到登录界面

                                    }
                           });
                   }*/
                return mViews.get(arg1);
                }





}
