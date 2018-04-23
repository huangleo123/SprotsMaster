package com.collega.sprotsmaster.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.collega.sprotsmaster.MainActivity;
import com.collega.sprotsmaster.R;
import com.mob.MobSDK;

import org.json.JSONObject;

import java.util.HashMap;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

/**注册的APP
 * Created by dd on 2018/4/16.
 */

public class RegisteredActivity extends Activity implements View.OnClickListener{
    private RelativeLayout registerRl;
    private RelativeLayout loginRl;
    private TextView registerTv;
    private TextView loginTv;
    ///以上是UI相关/////
    private ImageView wechatLogin;
    private ImageView qqLogin;
    private Button loginButton;
    private Button toRegisterButton;
    private Button getCodeButton;
    //////////////以上是响应按钮//////
    private EditText loginPassword;
    private EditText loginPhone;
    private EditText etCode;
    private EditText regPhone;
    private EditText regPassword;
    ///////////////以上是输入框的/////////////


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MobSDK.init(this);
        setContentView(R.layout.ui_login);

        getCodeButton =findViewById(R.id.bt_register_code);

        ///////////////////////////////////输入框////////////////////////////
        regPassword=findViewById(R.id.registered_password);
        regPhone=findViewById(R.id.registered_phone);
        etCode=findViewById(R.id.et_registered_code);
        /////////////////////////////////////////UI显示与隐藏///////////////////
        loginRl= (RelativeLayout) findViewById(R.id.login_Layout);
        registerRl= (RelativeLayout) findViewById(R.id.registered_layout);
        registerTv = (TextView) findViewById(R.id.registered);
        loginTv= (TextView) findViewById(R.id.login_in);
        //////////////////////////////////////登陆与注册确认按钮////////////////////////////
        loginButton= (Button) findViewById(R.id.login_button);
        toRegisterButton= (Button) findViewById(R.id.registered_button);
        init();

    }

    private void init() {
        registerTv.setOnClickListener(this);
        loginTv.setOnClickListener(this);
        loginButton.setOnClickListener(this);
        toRegisterButton.setOnClickListener(this);
        getCodeButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.registered:       //点击注册，显示注册界面

                loginRl.setVisibility(View.INVISIBLE);
                registerRl.setVisibility(View.VISIBLE);
                break;
            case R.id.login_in:         //点击登录，显示登录界面

                registerRl.setVisibility(View.INVISIBLE);
                loginRl.setVisibility(View.VISIBLE);

                break;
            case R.id.login_button:     //点击登录按钮

                Toast.makeText(getApplicationContext(),"点击登录",Toast.LENGTH_SHORT).show();

                break;
            case R.id.registered_button: //点击注册按钮

                toRegisterDetail();
                Toast.makeText(getApplicationContext(), "点击注册", Toast.LENGTH_SHORT).show();

                break;
            case R.id.bt_register_code: //点击获取验证码按钮
                getCodeDetail();
                break;

        }
    }

    public void toRegisterDetail(){

        Toast.makeText(getApplicationContext(), "点击注册", Toast.LENGTH_SHORT).show();
        String country = "86";
        String phone = regPhone.getText().toString();
        String code = etCode.getText().toString();
        String password =regPassword.getText().toString();
        if (TextUtils.isEmpty(code)) {
            Toast.makeText(getApplicationContext(),"验证码不能为空", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(phone)) {
            Toast.makeText(getApplicationContext(),"手机号码不能为空", Toast.LENGTH_SHORT).show();
        }else if(password.length()<6){
            Toast.makeText(getApplicationContext(),"请输入6位以上的密码", Toast.LENGTH_SHORT).show();
        }else{
            // 注册一个事件回调，用于处理提交验证码操作的结果
            SMSSDK.registerEventHandler(eventHandler); //evenHandler 中有4个方法，只用到了afterEvent ，在这个方法里面将事务移交到主线程中
            // 触发操作
            SMSSDK.submitVerificationCode(country, phone, code);
        }
    }

    public void getCodeDetail(){
        String country ="86";//没有扩展全球的手机，只支持中国的手机
        String phone=regPhone.getText().toString();
        //按钮不可点击，开始计时间
        //MyCountDownTimer myCountDownTimer =new MyCountDownTimer(30000,1000);
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(getApplicationContext(), "手机号码不能为空", Toast.LENGTH_SHORT).show();

        }else if (isMobileNO(phone)){

            //getCodeButton.setClickable(false);//TODO 换一个样式,增加倒数
            SMSSDK.registerEventHandler(eventHandler);
            SMSSDK.getVerificationCode(country,phone);
        }else {
            Toast.makeText(getApplicationContext(), "请填写正确的手机号码", Toast.LENGTH_SHORT).show();
        }

    }
    EventHandler eventHandler = new EventHandler() {
        //evenHandler 中有4个方法，只用到了afterEvent ，在这个方法里面将事务移交到主线程中
        @Override public void afterEvent(int event, int result, Object data) {
            Message msg = new Message();
            msg.arg1 = event;
            msg.arg2 = result;
            msg.obj = data;
            myHandler.sendMessage(msg);
            //将以上信息移交到myandler 中处理
        }
    };

    Handler myHandler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == -1) { //修改控件文本进行倒计时 i 以60秒倒计时为例
                getCodeButton.setText(" s"); }
            else if (msg.what == -2) {
                //修改控件文本，进行重新发送验证码
                getCodeButton.setText("重新发送");
                getCodeButton.setClickable(true);
                //i = 60;
            } else {
                int event = msg.arg1;
                int result = msg.arg2;
                Object data = msg.obj;

                //判断顺序result->event  result表示成功与否 event表示事件类型，包括提交验证码，验证 验证码 等。。
                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {   //value=3
                        //向数据库提交账号密码
                        Intent intent  =new Intent();
                        intent.setClass(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "注册成功，正在登陆。。", Toast.LENGTH_SHORT).show();

                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        //获取验证码成功
                        Toast.makeText(getApplicationContext(),"获取验证码成功",Toast.LENGTH_LONG).show();
                    } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                        //返回支持发送验证码的国家列表
                    }
                }
                if (result == SMSSDK.RESULT_ERROR) {
                    try {
                        Throwable throwable = (Throwable) data;
                        throwable.printStackTrace();
                        JSONObject object = new JSONObject(throwable.getMessage());
                        String des = object.optString("detail");//错误描述
                        int status = object.optInt("status");//错误代码
                        if (status > 0 && !TextUtils.isEmpty(des)) {
                            Toast.makeText(getApplicationContext(), des, Toast.LENGTH_SHORT).show();
                            return;
                        }
                    } catch (Exception e) { //do something

                    }
                }
            }
        }
    };

    private boolean isMobileNO(String phone) {
       /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        String telRegex = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(phone)) return false;
        else return phone.matches(telRegex);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //用完回调要注销掉，否则可能会出现内存泄露
        SMSSDK.unregisterAllEventHandler();
        finish();
    }
}
