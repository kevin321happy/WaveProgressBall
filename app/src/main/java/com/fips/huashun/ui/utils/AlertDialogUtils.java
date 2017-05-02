package com.fips.huashun.ui.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.fips.huashun.R;


/**
 * Created by zallds on 2016/3/21.
 */
public class AlertDialogUtils {
    /**
     * 一个按钮的提示框
     *
     * @param context
     * @param content
     * @return
     */
    public static AlertDialog showOneBtnDialog(Activity context, String content) {
        View view = View.inflate(context, R.layout.dialog_alert, null);
        final AlertDialog dlg = new AlertDialog.Builder(context).create();
        dlg.setView(view);
        if (context.isFinishing()) {
            return null;
        }
        dlg.show();
        Window window = dlg.getWindow();
        window.setContentView(R.layout.dialog_alert);
        TextView contentView = (TextView) window.findViewById(R.id.alert_content);
        contentView.setText(content);
        window.findViewById(R.id.alert_cancle).setVisibility(View.GONE); //隐藏取消button
        window.findViewById(R.id.alert_space).setVisibility(View.GONE); //隐藏button间的分割线
        View btnOk = window.findViewById(R.id.alert_ok);
        btnOk.setBackgroundResource(R.drawable.selector_dialog_one_button); //改变按钮的背景图
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlg.cancel();
            }
        });
        dlg.setCancelable(true);
        dlg.setCanceledOnTouchOutside(true);
        return dlg;
    }
    /**
     * 一个按钮的提示框
     *
     * @param context
     * @param content
     * @return
     */
    public static AlertDialog showOneBtnDialog(Activity context, String content, final DialogSingleButton listener) {
        View view = View.inflate(context, R.layout.dialog_alert, null);
        final AlertDialog dlg = new AlertDialog.Builder(context).create();
        dlg.setView(view);
        if (context.isFinishing()) {
            return null;
        }
        dlg.show();
        Window window = dlg.getWindow();
        window.setContentView(R.layout.dialog_alert);
        TextView contentView = (TextView) window.findViewById(R.id.alert_content);
        contentView.setText(content);
        window.findViewById(R.id.alert_cancle).setVisibility(View.GONE); //隐藏取消button
        window.findViewById(R.id.alert_space).setVisibility(View.GONE); //隐藏button间的分割线
        View btnOk = window.findViewById(R.id.alert_ok);
        btnOk.setBackgroundResource(R.drawable.selector_dialog_one_button); //改变按钮的背景图
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null) {
                    listener.submit(dlg);
                } else {
                    dlg.cancel();
                }
            }
        });
        dlg.setCancelable(true);
        dlg.setCanceledOnTouchOutside(false);
        return dlg;
    }

    /**
     * 二个按钮的提示框
     *
     * @param context
     * @param content
     * @return
     */
    public static AlertDialog showOneBtnDialog(Activity context, String content, final DialogClickInter listener) {
        View view = View.inflate(context, R.layout.dialog_alert, null);
        final AlertDialog dlg = new AlertDialog.Builder(context).create();
        dlg.setView(view);
        if (context.isFinishing()) {
            return null;
        }
        dlg.show();
        Window window = dlg.getWindow();
        window.setContentView(R.layout.dialog_alert);
        TextView contentView = (TextView) window.findViewById(R.id.alert_content);
        contentView.setText(content);
//        window.findViewById(R.id.alert_cancle).setVisibility(View.GONE); //隐藏取消button
//        window.findViewById(R.id.alert_space).setVisibility(View.GONE); //隐藏button间的分割线
        View btnOk = window.findViewById(R.id.alert_ok);
        btnOk.setBackgroundResource(R.drawable.selector_dialog_one_button); //改变按钮的背景图
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.rightClick(dlg);
                } else {
                    dlg.cancel();
                }
            }
        });
        window.findViewById(R.id.alert_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.leftClick(dlg);
                } else {
                    dlg.cancel();
                }
            }
        });
        dlg.setCancelable(true);
        dlg.setCanceledOnTouchOutside(true);
        return dlg;
    }



    public interface DialogClickInter {
        void leftClick(AlertDialog dialog);

        void rightClick(AlertDialog dialog);
    }
    public interface DialogInputInter{

        void submit(AlertDialog dialog, String inputPwd);
    }

    public interface DialogSingleButton{

        void submit(AlertDialog dialog);
    }

    /**
     * 显示有两个按钮的提示框
     *
     * @param context
     * @param content
     * @param dmDialogListener
     */
    public static AlertDialog showTowBtnDialog(Activity context, String content, String leftText, String rightText,
                                               final DialogClickInter dmDialogListener) {
        View view = View.inflate(context, R.layout.dialog_alert, null);
        final AlertDialog dlg = new AlertDialog.Builder(context).create();
        dlg.setView(view);
        if(context.isFinishing()) {
            return null;
        }
        dlg.show();
        Window window = dlg.getWindow();
        window.setContentView(R.layout.dialog_alert);
        TextView contentView = (TextView) window.findViewById(R.id.alert_content);
        contentView.setText(content);
        window.findViewById(R.id.alert_cancle).setVisibility(View.VISIBLE);
        window.findViewById(R.id.alert_space).setVisibility(View.VISIBLE);
        TextView lBtn = (TextView) window.findViewById(R.id.alert_cancle);
        TextView rBtn = (TextView) window.findViewById(R.id.alert_ok);
        lBtn.setText(leftText);
        rBtn.setText(rightText);
        lBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dmDialogListener != null) {
                    dmDialogListener.leftClick(dlg);
                } else {
                    dlg.cancel();
                }

            }
        });
        rBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dmDialogListener != null) {
                    dmDialogListener.rightClick(dlg);
                } else {
                    dlg.cancel();
                }

            }
        });
        dlg.setCancelable(true);
        dlg.setCanceledOnTouchOutside(false);
        return dlg;
    }

}
