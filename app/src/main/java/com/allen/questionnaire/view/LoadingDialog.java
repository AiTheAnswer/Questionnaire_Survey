package com.allen.questionnaire.view;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.allen.questionnaire.R;

/**
 * 加载中的对话框
 *
 * @author Renjy
 */
public class LoadingDialog {
    private static AlertDialog dialog;

    public static void showLoading(Context context) {
        if (null != dialog) {
            dialog.dismiss();
        } else {
            createDialog(context);
        }
        dialog.show();
    }

    public static void dismissLoading() {
        if (null != dialog) {
            dialog.dismiss();
        }
    }

    /**
     * 创建加载中对话框
     *
     * @param context 上下文
     */
    private static void createDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);
        builder.setView(view);
        dialog = builder.create();
    }
}
