package com.allen.questionnaire.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.allen.questionnaire.R;
import com.allen.questionnaire.service.ApiManager;
import com.allen.questionnaire.service.datatrasfer.IDataCallBack;
import com.allen.questionnaire.service.model.RespStudent;
import com.allen.questionnaire.service.net.CommonRequest;
import com.allen.questionnaire.utils.SharedPreferenceUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.login_edt_student_id)
    EditText mEdtUserName;
    @BindView(R.id.login_edt_id_number)
    EditText mEdtPassword;
    @BindView(R.id.btn_login)
    Button mBtnLogin;
    @BindView(R.id.login_user_name_clear)
    ImageButton mBtnClearUserName;
    @BindView(R.id.login_clear_password)
    ImageButton mBtnClearPassword;
    private SharedPreferenceUtils preferenceUtils;
    private static final String TOKEN = "token";
    private String mToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initData();
        initListener();
    }

    private void initData() {
        preferenceUtils = SharedPreferenceUtils.getInstance(this);
        mToken = preferenceUtils.getPreferenceString(TOKEN,"");
        if(!TextUtils.isEmpty(mToken)){
            showHomeActivity();
            finish();
        }
    }

    private void initListener() {
        mBtnLogin.setOnClickListener(this);
        mBtnClearUserName.setOnClickListener(this);
        mBtnClearPassword.setOnClickListener(this);
        mEdtUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    mBtnClearUserName.setVisibility(View.VISIBLE);
                } else {
                    mBtnClearUserName.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mEdtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    mBtnClearPassword.setVisibility(View.VISIBLE);
                } else {
                    mBtnClearPassword.setVisibility(View.INVISIBLE);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mBtnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_user_name_clear://清空用户名
                mEdtUserName.setText("");
                break;
            case R.id.login_clear_password://清空密码
                mEdtPassword.setText("");
                break;
            case R.id.btn_login://登录
                doLogin();
                break;
        }
    }

    /**
     * 登录
     */
    private void doLogin() {
        String userName = mEdtUserName.getText().toString();
        String passWord = mEdtPassword.getText().toString();
        if (TextUtils.isEmpty(userName)) {
            showToast("请输入用户名");
            return;
        }
        if (TextUtils.isEmpty(passWord)) {
            showToast("请输入密码");
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("studentId", userName);
        params.put("idNumber", passWord);
        IDataCallBack<RespStudent> callback = new IDataCallBack<RespStudent>() {
            @Override
            public void onSuccess(RespStudent result) {
                if (null != result && result.OK()) {
                    preferenceUtils.setPreference(TOKEN, result.getObject().getId());
                    showHomeActivity();
                    finish();
                } else if (null != result && null != result.getReason()) {
                    showToast(result.getReason());
                } else {
                    showToast("网络请求失败");
                }
            }

            @Override
            public void onError(int errorCode, String errorMessage) {
                if (TextUtils.isEmpty(errorMessage)) {
                    errorMessage = "网络请求失败";
                }
                showToast(errorMessage);
            }
        };
        ApiManager.postLogin(this, "/student/login", this, params, callback);
    }

    /**
     * 显示首页Activity
     */
    private void showHomeActivity() {
        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
    }

}
