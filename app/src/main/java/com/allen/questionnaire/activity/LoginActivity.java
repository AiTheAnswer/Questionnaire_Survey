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
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.allen.questionnaire.R;
import com.allen.questionnaire.service.ApiManager;
import com.allen.questionnaire.service.datatrasfer.IDataCallBack;
import com.allen.questionnaire.service.model.RespStudent;
import com.allen.questionnaire.service.model.RespStudents;
import com.allen.questionnaire.service.model.Student;
import com.allen.questionnaire.utils.Constant;
import com.allen.questionnaire.utils.SharedPreferenceUtils;
import com.allen.questionnaire.view.StudentListPopupWindow;

import java.util.HashMap;
import java.util.List;
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
    @BindView(R.id.img_login_icon)
    ImageView mImgIcon;
    private SharedPreferenceUtils preferenceUtils;
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
        mToken = preferenceUtils.getPreferenceString(Constant.TOKEN, "");
        if (!TextUtils.isEmpty(mToken)) {
            showHomeActivity();
            finish();
        }
    }

    private void initListener() {
        mBtnLogin.setOnClickListener(this);
        mBtnClearUserName.setOnClickListener(this);
        mBtnClearPassword.setOnClickListener(this);
        mImgIcon.setOnClickListener(this);
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

    private int onClickIconCount = 0;
    private long clickDate = 0;

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
            case R.id.img_login_icon://应用图标，连续点击五次可显示所有学生列表
                long currentTimeMillis = System.currentTimeMillis();
                if (onClickIconCount == 5) {
                    getAllStudents();
                    clickDate = 0;
                } else if (currentTimeMillis - clickDate < 500) {
                    onClickIconCount++;
                } else {
                    onClickIconCount = 0;
                }
                clickDate = currentTimeMillis;
                break;
        }
    }

    /**
     * 获取所有的学生信息
     */
    private void getAllStudents() {
        Map<String, String> params = new HashMap<>();

        IDataCallBack<RespStudents> callback = new IDataCallBack<RespStudents>() {
            @Override
            public void onSuccess(RespStudents result) {
                if (null != result && result.OK()) {
                    List<Student> students = result.getObject();
                    showStudentListPop(students);

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
        ApiManager.postAllStudents(this, "/student/getAll", this, params, callback);
    }

    /**
     * 显示所有学生信息列表的PopupWindow
     */
    private void showStudentListPop(List<Student> studentList) {
        final StudentListPopupWindow popupWindow = new StudentListPopupWindow(this,studentList);
        popupWindow.show();
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Student student = popupWindow.getSelectedStudent();
                mEdtUserName.setText(student.getStudentId());
                mEdtPassword.setText(student.getIdNumber());
                doLogin();
            }
        });
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
                    preferenceUtils.setPreference(Constant.TOKEN, result.getObject().getId());
                    preferenceUtils.setPreference(Constant.USER_NAME, result.getObject().getName());
                    preferenceUtils.setPreference(Constant.STUDENT_ID, result.getObject().getStudentId());
                    preferenceUtils.setPreference(Constant.USER_SEX, result.getObject().getSex());//0: 男  1：女
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
