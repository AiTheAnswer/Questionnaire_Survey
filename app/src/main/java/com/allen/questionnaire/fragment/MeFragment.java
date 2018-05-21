package com.allen.questionnaire.fragment;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allen.questionnaire.R;
import com.allen.questionnaire.activity.HomeActivity;
import com.allen.questionnaire.utils.Constant;
import com.allen.questionnaire.utils.SharedPreferenceUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 我的Fragment
 *
 * @author Renjy
 */

public class MeFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.img_fragment_me_icon)
    ImageView mImgIcon;
    @BindView(R.id.txt_fragment_me_user_name)
    TextView mTxtUserName;
    @BindView(R.id.txt_fragment_me_user_studentId)
    TextView mTxtrStudentId;
    @BindView(R.id.txt_fragment_me_version_code)
    TextView mTxtVersionCode;
    @BindView(R.id.txt_fragment_me_tel)
    TextView mTxtTel;
    @BindView(R.id.txt_fragment_me_clear_cache)
    TextView mTxtClearCache;
    @BindView(R.id.txt_fragment_me_exit)
    TextView mTxtExit;
    Unbinder unbinder;
    @BindView(R.id.layout_fragment_me_userInfo)
    RelativeLayout mLayoutUserInfo;
    @BindView(R.id.layout_fragment_me_version_code)
    RelativeLayout mLayoutVersionCode;
    @BindView(R.id.layout_fragment_me_tel)
    RelativeLayout mLayoutTel;
    @BindView(R.id.layout_fragment_me_feed_back)
    RelativeLayout mLayoutFeedBack;
    @BindView(R.id.layout_fragment_me_clear_cache)
    RelativeLayout mLayoutClearCache;
    @BindView(R.id.layout_fragment_me_scan)
    RelativeLayout mLayoutScan;
    @BindView(R.id.layout_fragment_me_about)
    RelativeLayout mLayoutAbout;
    private View view;
    private HomeActivity mActivity;
    private SharedPreferenceUtils preferenceUtils;
    //用户性别 0：男   1：女
    private int sex;
    //用户姓名
    private String studentName;
    //学号
    private String studentId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_me, null);
        unbinder = ButterKnife.bind(this, view);
        initData();
        initListener();
        return view;
    }

    private void initListener() {
        mLayoutUserInfo.setOnClickListener(this);
        mLayoutVersionCode.setOnClickListener(this);
        mLayoutTel.setOnClickListener(this);
        mLayoutFeedBack.setOnClickListener(this);
        mLayoutClearCache.setOnClickListener(this);
        mLayoutScan.setOnClickListener(this);
        mLayoutAbout.setOnClickListener(this);
        mTxtExit.setOnClickListener(this);
    }

    private void initData() {
        mActivity = (HomeActivity) getActivity();
        preferenceUtils = SharedPreferenceUtils.getInstance(mActivity);
        sex = preferenceUtils.getPreferenceInteger(Constant.USER_SEX, 0);
        studentName = preferenceUtils.getPreferenceString(Constant.USER_NAME, "");
        studentId = preferenceUtils.getPreferenceString(Constant.STUDENT_ID, "");
        if (0 == sex) {//男
            mImgIcon.setImageResource(R.mipmap.boy);
        } else {
            mImgIcon.setImageResource(R.mipmap.girl);
        }
        mTxtUserName.setText(studentName);
        mTxtrStudentId.setText(String.format("%s: %s", "学号", studentId));
        if (!TextUtils.isEmpty(getVersionCode())) {
            mTxtVersionCode.setText(String.format("v %s", getVersionCode()));
        }
        mTxtTel.setText(String.format("%s", "400-995-6050"));
    }

    /**
     * 获取版本名称
     *
     * @return 版本名称
     */
    private String getVersionCode() {
        String versionName = "";
        try {
            versionName = mActivity.getPackageManager().getPackageInfo(mActivity.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_fragment_me_userInfo://用户详情
                mActivity.showToast("暂无用户详情功能，敬请期待");
                break;
            case R.id.layout_fragment_me_version_code://版本号
                //检测版本更新
                mActivity.showToast("暂无检测版本更新功能");
                break;
            case R.id.layout_fragment_me_tel://客服电话
                //拨号
                break;
            case R.id.layout_fragment_me_feed_back://意见反馈
                mActivity.showToast("暂无意见反馈功能，敬请期待");
                break;
            case R.id.layout_fragment_me_clear_cache://清理缓存
                mActivity.showToast("暂无清理缓存功能，敬请期待");
                break;
            case R.id.layout_fragment_me_scan://扫一扫
                mActivity.showToast("暂无扫一扫的功能，敬请期待");
                break;
            case R.id.layout_fragment_me_about://关于
                mActivity.showToast("暂无此功能，敬请期待");
                break;
            case R.id.txt_fragment_me_exit://退出登录
                preferenceUtils.setPreference(Constant.TOKEN, "");
                mActivity.finish();
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
