package com.example.newpost.dialog;

import android.content.Context;
import android.view.WindowManager;

import com.example.newpost.R;


/**
 * Created: qgl
 * 2020/10/19
 * 描述:弹框
 */
public class LoadingDialog extends CustomDialog {

    public LoadingDialog(Context context) {
        this(context, false);
    }

    public LoadingDialog(Context context, boolean dimEnabled) {
        super(context, dimEnabled);
    }

    @Override
    protected void onCreateView(WindowManager windowManager) {
        setDialogWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        setDialogHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        setContentView(R.layout.dialog_loding);
        setCanceledOnTouchOutside(false);
    }
}
