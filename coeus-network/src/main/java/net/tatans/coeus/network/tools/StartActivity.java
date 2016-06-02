package net.tatans.coeus.network.tools;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import net.tatans.rhea.network.register.RegCertificateUtil;

/**
 * TatansFrameProject [v 2.0.0]
 * classes : net.tatans.coeus.network.tools.StartActivity
 * Yuriy create at 2016/5/23 16:30
 */
public class StartActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RegCertificateUtil regCertificateUtil = new RegCertificateUtil(this);
    }

    public void TatansStartActivity(Class<?> cls){
        Intent intent = new Intent();
        intent.setClass(this,cls);
        startActivity(intent);
    }
}
