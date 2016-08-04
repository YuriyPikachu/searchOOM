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


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.system.ErrnoException;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import net.tatans.coeus.network.tools.BaseActivity;
import net.tatans.coeus.network.tools.TatansLog;
import net.tatans.rhea.network.event.OnClick;
import net.tatans.rhea.network.view.ContentView;
import net.tatans.rhea.network.view.ViewIoc;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@ContentView(R.layout.main_activity)
public class AndroidXmlMainActivity extends BaseActivity {
  private static String TAG="QQQQ";
  private static String SAVE_XMLFILE_PATH=Environment.getDataDirectory()+"/data/com.example.yulia.myapplication/shared_prefs/tatans.xml";
  @ViewIoc(R.id.async_task)
  Button bt_single;
  @ViewIoc(R.id.tv_show)
  TextView textView;
  String Imei;
  Document document=null;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    XmlUtils xmlUtils =null;

  }
  @OnClick(R.id.async_task)
  public void aVoid(){
    Log.w(TAG, "map:"+tatansDefaultlApp().get("package"));

    /*}else {
      Log.w(TAG, "not");
    }*/
  }
  private Map tatansDefaultlApp(){
    Map map = null;
    File mFile = new File(Environment.getExternalStorageDirectory().toString()  + "/"+"tatans/"+getPackageName()+"/dafaultaApp.xml");
    //if (mFile.canRead()) {
    BufferedInputStream str = null;
    try {
      str = new BufferedInputStream(
              new FileInputStream(mFile), 16*1024);
      map = XmlUtils.readMapXml(str);
    } catch (XmlPullParserException e) {
      Log.w(TAG, "getSharedPreferences", e);
    } catch (FileNotFoundException e) {
      Log.w(TAG, "getSharedPreferences", e);
    } catch (IOException e) {
      Log.w(TAG, "getSharedPreferences", e);
    } finally {
      //   IoUtils.closeQuietly(str);
    }
    return map;
  }
}


