package net.tatans.coeus.network.tools;

import android.app.Activity;
import android.content.Intent;

/**
 * TatansFrameProject [v 2.0.0]
 * classes : net.tatans.coeus.network.tools.StartActivity
 * Yuriy create at 2016/5/23 16:30
 */
public class StartActivity extends Activity{
    public void TatansStartActivity(Class<?> cls){
        Intent intent = new Intent();
        intent.setClass(this,cls);
        startActivity(intent);
    }
}
