package com.collega.sprotsmaster.util;

import android.os.CountDownTimer;
import android.widget.Button;

/**
 * Created by dd on 2018/4/20.
 */

public class MyCountDownTimer extends CountDownTimer {
    private Button getCodeButton;
    public MyCountDownTimer(long millisInFuture, long countDownInterval,Button getCodeButton) {
        super(millisInFuture, countDownInterval);
        this.getCodeButton=getCodeButton;
    }

    //计算时间过程
    @Override
    public void onTick(long l) {
        //不可点击，样式改变
        getCodeButton.setText((l/1000+"s后再次发送"));
        getCodeButton.setClickable(false);
        //TODO 样式改变为不可点击

    }
    //计算时间结束过程
    @Override
    public void onFinish() {
        //样式改变为蓝色，可点击的样式
        getCodeButton.setText("重新获取");
        getCodeButton.setClickable(true);
        //TODO 样式改变为可点击


    }
}
