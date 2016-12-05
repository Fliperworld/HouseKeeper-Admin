package com.smart.cloud.fire.view;

/**
 * Created by Administrator on 2016/8/4.
 */


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hrsst.housekeeper.R;
import com.p2p.core.update.UpdateManager;
import com.smart.cloud.fire.global.ConstantValues;
import com.smart.cloud.fire.global.MyApp;
import com.smart.cloud.fire.utils.TestAuthorityUtil;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;


public class NormalDialog {
    Context context;
    String title_str, content_str, btn1_str, btn2_str;
    AlertDialog dialog;
    @Bind(R.id.bind_smoke_id)
    EditText bindSmokeId;
    @Bind(R.id.bind_camera_id)
    EditText bindCameraId;
    @Bind(R.id.button1_text)
    TextView button1Text;
    @Bind(R.id.button2_text)
    TextView button2Text;
    private int style = 999;
    String[] list_data = new String[]{};
    private OnButtonOkListener onButtonOkListener;
    private OnButtonCancelListener onButtonCancelListener;
    private OnItemClickListener onItemClickListener;
    private OnCancelListener onCancelListener;
    private String message;
    private String urlStr;

    public NormalDialog(Context context, String title, String content,
                        String btn1, String btn2) {
        this.context = context;
        this.title_str = title;
        this.content_str = content;
        this.btn1_str = btn1;
        this.btn2_str = btn2;
    }

    public NormalDialog(Context context, String title,
                        String btn1, String btn2) {
        this.context = context;
        this.title_str = title;
        this.btn1_str = btn1;
        this.btn2_str = btn2;
    }

    public NormalDialog(Context context,String message, final String urlStr) {
        this.context = context;
        this.message = message;
        this.urlStr = urlStr;
    }

    public void showVersionDialog(){
        View view = LayoutInflater.from(context).inflate(
                R.layout.dialog_update, null);
        TextView title = (TextView) view.findViewById(R.id.title_text);
        WebView content = (WebView) view.findViewById(R.id.content_text);
        TextView button1 = (TextView) view
                .findViewById(R.id.button1_text);
        TextView button2 = (TextView) view
                .findViewById(R.id.button2_text);
        ImageView minddle_image = (ImageView) view
                .findViewById(R.id.minddle_image);
        RelativeLayout cancel_rela_dialog = (RelativeLayout) view
                .findViewById(R.id.cancel_rela_dialog);
        title.setText("更新");
        if(urlStr==null){
            content.setBackgroundColor(context.getResources().getColor(R.color.update_message)); // 设置背景色
            content.getBackground().setAlpha(255); // 设置填充透明度 范围：0-255
            content.loadDataWithBaseURL(null, "已是最新版本！", "text/html", "utf-8",
                    null);
            minddle_image.setVisibility(View.GONE);
            cancel_rela_dialog.setVisibility(View.GONE);
            button2.setText("确定");
            button2.setTextColor(Color.BLACK);
            button2.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub
                    if (null != dialog) {
                        dialog.cancel();
                    }
                }
            });
        }else{
            content.setBackgroundColor(Color.WHITE); // 设置背景色
            content.getBackground().setAlpha(100); // 设置填充透明度 范围：0-255
            content.loadDataWithBaseURL(null, message, "text/html", "utf-8",
                    null);
            button1.setText("立即更新");
            button2.setText("下次再说");
            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != dialog) {
                        dialog.dismiss();
                        dialog = null;
                    }
                    if (UpdateManager.getInstance().getIsDowning()) {
                        return;
                    }
                    MyApp.app.showDownNotification(
                            UpdateManager.HANDLE_MSG_DOWNING, 0);
                    new Thread() {
                        public void run() {
                            UpdateManager.getInstance().downloadApk(getHandler(), ConstantValues.Update.SAVE_PATH,
                                    ConstantValues.Update.FILE_NAME, urlStr);
                        }
                    }.start();
                }
            });
            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != dialog) {
                        dialog.cancel();
                    }
                }
            });
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        dialog = builder.create();
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        dialog.setContentView(view);
    }

    private Handler getHandler(){
        Handler handler = new Handler(context.getMainLooper()) {
            long last_time;

            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                int value = msg.arg1;
                switch (msg.what) {
                    case UpdateManager.HANDLE_MSG_DOWNING:
                        if ((System.currentTimeMillis() - last_time) > 1000) {
                            MyApp.app.showDownNotification(
                                    UpdateManager.HANDLE_MSG_DOWNING, value);
                            last_time = System.currentTimeMillis();
                        }
                        break;
                    case UpdateManager.HANDLE_MSG_DOWN_SUCCESS:
                        MyApp.app.hideDownNotification();
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        File file = new File(Environment.getExternalStorageDirectory()
                                + "/" + ConstantValues.Update.SAVE_PATH + "/"
                                + ConstantValues.Update.FILE_NAME);
                        if (!file.exists()) {
                            return;
                        }
                        intent.setDataAndType(Uri.fromFile(file),
                                ConstantValues.Update.INSTALL_APK);
                        context.startActivity(intent);
                        break;
                    case UpdateManager.HANDLE_MSG_DOWN_FAULT:
                        break;
                }
            }
        };
        return handler;
    }

    public NormalDialog(Context context) {
        this.context = context;
    }

    public static final int DIALOG_STYLE_NORMAL = 1;
    public static final int DIALOG_STYLE_LOADING = 2;
    public static final int DIALOG_STYLE_UPDATE = 3;
    public static final int DIALOG_STYLE_DOWNLOAD = 4;
    public static final int DIALOG_STYLE_PROMPT = 5;

    public void showDialog() {
        showNormalDialog();
    }

    public void showNormalDialog() {
        if (!TestAuthorityUtil.testPhone(context)) {
            return;
        }
        View view = LayoutInflater.from(context).inflate(
                R.layout.dialog_normal, null);
        TextView title = (TextView) view.findViewById(R.id.title_text);
        TextView content = (TextView) view.findViewById(R.id.content_text);
        TextView button1 = (TextView) view.findViewById(R.id.button1_text);
        TextView button2 = (TextView) view.findViewById(R.id.button2_text);
        title.setText(title_str);
        content.setText(content_str);
        button1.setText(btn1_str);
        button2.setText(btn2_str);
        button1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != dialog) {
                    dialog.dismiss();
                    Intent phoneIntent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + content_str));
                    phoneIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(phoneIntent);
                }
            }
        });
        button2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null == onButtonCancelListener) {
                    if (null != dialog) {
                        dialog.cancel();
                    }
                } else {
                    onButtonCancelListener.onClick();
                }
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        dialog = builder.create();
        dialog.show();
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(true);
    }

    public void showLoadingDialog(String tv) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.dialog_loading, null);
        TextView title = (TextView) view.findViewById(R.id.title_text);
        title.setText(tv);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
    }

    public void bindDialog() {
        final View view = LayoutInflater.from(context).inflate(
                R.layout.dialog_bind, null);
        ButterKnife.bind(this, view);
        button2Text.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ButterKnife.unbind(view);
                dismiss();
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
    }


    public void setTitle(String title) {
        this.title_str = title;
    }

    public void setTitle(int id) {
        this.title_str = context.getResources().getString(id);
    }

    public void setListData(String[] data) {
        this.list_data = data;
    }

    public void setCanceledOnTouchOutside(boolean bool) {
        dialog.setCanceledOnTouchOutside(bool);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setCancelable(boolean bool) {
        dialog.setCancelable(bool);
    }

    public void cancel() {
        dialog.cancel();
    }

    public void dismiss() {
        dialog.dismiss();
    }

    public boolean isShowing() {
        return dialog.isShowing();
    }

    public void setBtnListener(TextView btn1, TextView btn2) {

    }

    public void setStyle(int style) {
        this.style = style;
    }

    public interface OnButtonOkListener {
        public void onClick();
    }

    public interface OnButtonCancelListener {
        public void onClick();
    }

    public void setOnButtonOkListener(OnButtonOkListener onButtonOkListener) {
        this.onButtonOkListener = onButtonOkListener;
    }

    public void setOnButtonCancelListener(
            OnButtonCancelListener onButtonCancelListener) {
        this.onButtonCancelListener = onButtonCancelListener;
    }

    public void setOnCancelListener(OnCancelListener onCancelListener) {
        this.onCancelListener = onCancelListener;
        Log.i("dxsSMTP", "setlistener");
    }

    private ListView listrView, alarm_statusList;


    OnKeyListener keylistener = new OnKeyListener() {
        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                return true;
            } else {
                return false;
            }
        }
    };


}

