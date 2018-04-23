package com.collega.sprotsmaster.util;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.collega.sprotsmaster.activity.LoginActivity;
import com.collega.sprotsmaster.R;
import com.collega.sprotsmaster.adapter.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**首次启动欢迎界面，只有首次启动才出现 VP加圆点
 * Created by dd on 2018/4/11.
 */

public class GuideActivity extends Activity {
    private ViewPager viewPager;
    private List<View> views;
    private PagerAdapter myPagerAdapter;
    private Button gobutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        LayoutInflater inflater = LayoutInflater.from(GuideActivity.this);

        View view1=inflater.inflate(R.layout.guide_pager_layout_1,null);
        View view2=inflater.inflate(R.layout.guide_pager_layout_2,null);
        View view3=inflater.inflate(R.layout.guide_pager_layout_3,null);
        viewPager=findViewById(R.id.vp_guide);
        views = new ArrayList<View>();
        views.add(view1);
        views.add(view2);
        views.add(view3);

        myPagerAdapter = new PagerAdapter(this, views);
        viewPager.setAdapter(myPagerAdapter);
        view3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent();
                intent2.setClass(GuideActivity.this, LoginActivity.class);
                startActivity(intent2);
                finish();
            }
        });





    }
}
