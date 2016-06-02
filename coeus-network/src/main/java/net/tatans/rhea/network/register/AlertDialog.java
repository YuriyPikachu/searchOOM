package net.tatans.rhea.network.register;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.tatans.coeus.network.R;

/**
 * Created by SiLiPing on 2016/6/2.
 * �Զ���AlertDialog����������ע��5s������
 */
public class AlertDialog {

    private android.app.AlertDialog alertDialog;
    private TextView titleView;
    private TextView messageView;
    private LinearLayout buttonLayout;
    private Button button_confirm,button_cancel;

    public AlertDialog(Context context) {
        // TODO Auto-generated constructor stub
        alertDialog=new android.app.AlertDialog.Builder(context).create();
        alertDialog.setCancelable(false);
        alertDialog.show();
        //�ؼ������������,ʹ��window.setContentView,�滻�����Ի��򴰿ڵĲ���
        Window window = alertDialog.getWindow();
        window.setContentView(R.layout.alertdialog);
        titleView = (TextView)window.findViewById(R.id.title);
        messageView = (TextView)window.findViewById(R.id.message);
        buttonLayout = (LinearLayout)window.findViewById(R.id.buttonLayout);
        button_confirm = new Button(context);
        button_cancel = new Button(context);
    }

    public void setTitle(String title) {
        titleView.setText(title);
    }
    public void setMessage(String message) {
        messageView.setText(message);
    }

    private int recLen = 5;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            recLen --;
            if (recLen<=0){
                handler.removeCallbacks(runnable);
                button_confirm.setEnabled(true);
                button_confirm.setText("����ע��");
                button_confirm.setContentDescription("����ע��");
                button_confirm.setBackgroundResource(R.drawable.fillet_btn_black);
            }else{
                button_confirm.setText("����ע��("+ recLen +")");
                button_confirm.setContentDescription("����ע��"+ recLen +"������");
            }
            handler.postDelayed(this, 1000);
        }
    };

    /**
     * ����button_confirm��ť
     * @param text
     * @param listener
     */
    public void setPositiveButton(String text,final View.OnClickListener listener) {
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(20, 10, 20, 10);
        button_confirm.setLayoutParams(params);
        button_confirm.setBackgroundResource(R.drawable.fillet_btn_gray);
        button_confirm.setText(text);
        button_confirm.setEnabled(false);
        handler.postDelayed(runnable, 5*1000);
        button_confirm.setTextColor(Color.WHITE);
        button_confirm.setTextSize(25);
        button_confirm.setOnClickListener(listener);
        buttonLayout.addView(button_confirm);
    }

    /**
     * ����button_cancel��ť
     * @param text
     * @param listener
     */
    public void setNegativeButton(String text,final View.OnClickListener listener) {
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(20, 10, 20, 10);
        button_cancel.setLayoutParams(params);
        button_cancel.setBackgroundResource(R.drawable.fillet_btn_black);
        button_cancel.setText(text);
        button_cancel.setTextColor(Color.WHITE);
        button_cancel.setTextSize(25);
        button_cancel.setOnClickListener(listener);
        if(buttonLayout.getChildCount()>0) {
            button_cancel.setLayoutParams(params);
            buttonLayout.addView(button_cancel, 1);
        }else{
            button_cancel.setLayoutParams(params);
            buttonLayout.addView(button_cancel);
        }
    }
    /**
     * �رնԻ���
     */
    public void dismiss() {
        alertDialog.dismiss();
    }
}
