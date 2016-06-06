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
        alertDialog.setTitle("注册失败");
        alertDialog.setMessage("请在联网状态下注册。出现问题请拨打客服电话：4007778878");
        alertDialog.setPositiveButton("重新注册", new View.OnClickListener() {

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
