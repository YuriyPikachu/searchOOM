package net.tatans.rhea.network.register;

import android.content.Context;
import android.view.View;

import net.tatans.coeus.network.tools.TatansApplication;

/**
 * Created by SiLiPing on 2016/5/16.
 */
public class TirisDemo {

    private AlertDialog alertDialog;
    private onTirisListener TirisListener;

    public TirisDemo(Context context) {
        alertDialog = new AlertDialog(context);
        alertDialog.setTitle("注册提示");
        alertDialog.setMessage("请连接网络后在线注册,或者拨打电话给天坦客服:4007778878");
        alertDialog.setPositiveButton("在线注册", new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                TirisListener.onRegistration();
                alertDialog.dismiss();
            }
        });
    }

    public void setOnTirisListener(onTirisListener listener) {
        TirisListener = listener;
    }

}
