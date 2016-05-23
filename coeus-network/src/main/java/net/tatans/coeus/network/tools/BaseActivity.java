package net.tatans.coeus.network.tools;

import android.app.Activity;
import android.os.Bundle;

import net.tatans.rhea.network.view.TatansIoc;

/**
 * SearchOOM [v 2.0.0]
 * classes : net.tatans.coeus.network.tools.BaseActivity
 * yulia create at 2016/5/10 10:47
 */
public class BaseActivity extends StartActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TatansIoc.inject(this);
    }
}
