package com.yc.baisibudejie.utils;

import android.app.Application;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.yc.baisibudejie.GlobalApp;
import com.yc.baisibudejie.R;
import com.yc.baisibudejie.base.BaseTextView;

/**
 * Toast 工具类
 */
public class ToastUtil {

    private static final GlobalApp sGlobalApp = GlobalApp.getInstance();

    public static void showShortToast(String message) {
        showShortToast(message, true);
    }

    public static void showShortToast(String message, boolean isPortrait) {
        LayoutInflater inflater = (LayoutInflater) sGlobalApp.getSystemService(Application.LAYOUT_INFLATER_SERVICE);
        View rootView = inflater.inflate(R.layout.toast_normal, null);
        BaseTextView messageTv = (BaseTextView) rootView.findViewById(R.id.commToastMessageTv);
        messageTv.setText(message);
        messageTv.setTextSize(isPortrait
                ? TextDisplayUtil.fixSpValue(R.dimen.text_size_common_txt_42)
                : TextDisplayUtil.fixLandScapeSpValue(R.dimen.text_size_common_txt_42));
        Toast toast = new Toast(sGlobalApp);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(rootView);
        toast.show();
    }

    public static void showShortToast(int resId) {
        showShortToast(resId, true);
    }

    public static void showShortToast(int resId, boolean isPortrait) {
        LayoutInflater inflater = (LayoutInflater) sGlobalApp.getSystemService(Application.LAYOUT_INFLATER_SERVICE);
        View rootView = inflater.inflate(R.layout.toast_normal, null);
        BaseTextView messageTv = (BaseTextView) rootView.findViewById(R.id.commToastMessageTv);
        messageTv.setText(resId);
        messageTv.setTextSize(isPortrait
                ? TextDisplayUtil.fixSpValue(R.dimen.text_size_common_txt_42)
                : TextDisplayUtil.fixLandScapeSpValue(R.dimen.text_size_common_txt_42));
        Toast toast = new Toast(sGlobalApp);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(rootView);
        toast.show();
    }

    public static void showLongToast(String message) {
        LayoutInflater inflater = (LayoutInflater) sGlobalApp.getSystemService(Application.LAYOUT_INFLATER_SERVICE);
        View rootView = inflater.inflate(R.layout.toast_normal, null);
        BaseTextView messageTv = (BaseTextView) rootView.findViewById(R.id.commToastMessageTv);
        messageTv.setText(message);
        messageTv.setTextSize(TextDisplayUtil.fixSpValue(R.dimen.text_size_common_txt_42));
        Toast toast = new Toast(sGlobalApp);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(rootView);
        toast.show();
    }

    public static void showLongToast(String msg1, String msg2) {
        LayoutInflater inflater = (LayoutInflater) sGlobalApp.getSystemService(Application.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.toast_long, null);
        BaseTextView txt1 = (BaseTextView) layout.findViewById(R.id.toastLongTxt1);
        BaseTextView txt2 = (BaseTextView) layout.findViewById(R.id.toastLongTxt2);
        txt1.setTextSize(TextDisplayUtil.fixSpValue(R.dimen.text_size_common_txt_42));
        txt2.setTextSize(TextDisplayUtil.fixSpValue(R.dimen.text_size_common_txt_42));
        txt1.setText(msg1);
        txt2.setText(msg2);
        Toast toast = new Toast(sGlobalApp);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    public static void showShortGravityToast(String message, int gravity) {
        LayoutInflater inflater = (LayoutInflater) sGlobalApp.getSystemService(Application.LAYOUT_INFLATER_SERVICE);
        View rootView = inflater.inflate(R.layout.toast_normal, null);
        BaseTextView messageTv = (BaseTextView) rootView.findViewById(R.id.commToastMessageTv);
        messageTv.setText(message);
        messageTv.setTextSize(TextDisplayUtil.fixSpValue(R.dimen.text_size_common_txt_42));
        Toast toast = new Toast(sGlobalApp);
        toast.setGravity(gravity, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(rootView);
        toast.show();
    }
}
