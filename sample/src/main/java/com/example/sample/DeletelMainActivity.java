/*
 * Copyright (C) 2015 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.sample;


import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import net.tatans.coeus.network.tools.BaseActivity;
import net.tatans.coeus.network.tools.TatansPreferences;
import net.tatans.rhea.network.event.OnClick;
import net.tatans.rhea.network.view.ContentView;
import net.tatans.rhea.network.view.ViewIoc;

import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

@ContentView(R.layout.main_activity)
public class DeletelMainActivity extends BaseActivity {
  private static String TAG="QQQQ";
  private Map mapDefaultSettingApp;
  @ViewIoc(R.id.async_task)
  Button bt_single;
  @ViewIoc(R.id.tv_show)
  TextView textView;
  String Imei;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.getPackageName();
  }
  @OnClick(R.id.async_task)
  public void aVoid(){
    DataCleanManager.cleanUserData("");
    Log.d("QQQQ", "TatansPreferences:");
  }
  @OnClick(R.id.async_task2)
  public void aVoid2(){
    DataCleanManager.cleanInternalCache("");
    Log.d("QQQQ", "TatansPreferences:2");
  }
  @Override
  protected void onResume() {
    super.onResume();
    Log.d("QQQQ", "TatansPreferences:"+ TatansPreferences.get("package","aaa"));
  }
}


