package com.jsrinfotech.wallpaparapp.globle;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Window;
import android.view.WindowManager;

import com.jsrinfotech.wallpaparapp.R;

/**
 * Created by Admin on 25-01-2019.
 */

public class CommonUtils {

    public static boolean isChecked = false;
    static Context context;


    public static Dialog createCustomLoader(Context mContext, boolean isCancelable) {
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(isCancelable);
        dialog.setContentView(R.layout.custom_loader);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        return dialog;
    }


    //check internet connection
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager
                cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null
                && activeNetwork.isConnected();
    }
}
