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
import android.telephony.TelephonyManager;
import android.widget.Button;
import android.widget.TextView;

import net.tatans.coeus.network.tools.BaseActivity;
import net.tatans.coeus.network.tools.TatansLog;
import net.tatans.rhea.network.event.OnClick;
import net.tatans.rhea.network.view.ContentView;
import net.tatans.rhea.network.view.ViewIoc;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@ContentView(R.layout.main_activity)
public class MainActivity extends BaseActivity {

  @ViewIoc(R.id.async_task)
  Button bt_single;
  @ViewIoc(R.id.tv_show)
  TextView textView;
  String Imei;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    textView.setText(md5(Imei));
  }
  @OnClick(R.id.async_task)
  public void aVoid(){
    TatansLog.e("11111111111");
  }
  public static String md5(String string) {
    byte[] hash;
    try {
      hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException("Huh, MD5 should be supported?", e);
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException("Huh, UTF-8 should be supported?", e);
    }

    StringBuilder hex = new StringBuilder(hash.length * 2);
    for (byte b : hash) {
      if ((b & 0xFF) < 0x10) hex.append("0");
      hex.append(Integer.toHexString(b & 0xFF));
    }
    return hex.toString();
  }

}


