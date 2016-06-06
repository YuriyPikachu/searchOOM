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
        alertDialog.setTitle("ע��ʧ��");
        alertDialog.setMessage("��������״̬��ע�ᡣ���������벦��ͷ��绰��4007778878");
        alertDialog.setPositiveButton("����ע��", new View.OnClickListener() {

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
