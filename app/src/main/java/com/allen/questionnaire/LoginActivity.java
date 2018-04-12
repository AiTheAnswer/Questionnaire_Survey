package com.allen.questionnaire;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.allen.questionnaire.service.datatrasfer.IDataCallBack;
import com.allen.questionnaire.service.model.RespStudent;
import com.allen.questionnaire.service.net.CommonRequest;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initListener();
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
        Map<String,String> params = new HashMap<>();
        params.put("studentId","201495114001");
        params.put("idNumber","612826199401016541");
        IDataCallBack<RespStudent> callback = new IDataCallBack<RespStudent>() {
            @Override
            public void onSuccess(RespStudent result) {
                    showToast(result.getObject().getId());
            }

            @Override
            public void onError(int errorCode, String errorMessage) {
                showToast(errorMessage);
            }
        };
        CommonRequest.postLogin(this,"/student/login",this,params,callback);

    }

}
