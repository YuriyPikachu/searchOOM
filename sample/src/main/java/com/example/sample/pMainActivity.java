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
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import net.tatans.coeus.network.tools.BaseActivity;
import net.tatans.coeus.network.tools.TatansLog;
import net.tatans.coeus.network.tools.TatansPreferences;
import net.tatans.rhea.network.event.OnClick;
import net.tatans.rhea.network.view.ContentView;
import net.tatans.rhea.network.view.ViewIoc;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.List;

@ContentView(R.layout.main_activity)
public class pMainActivity extends BaseActivity {
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
    try {
      Context otherAppContext = this.createPackageContext("com.example.yulia.myapplication", Context.CONTEXT_IGNORE_SECURITY);
      SharedPreferences sharedPreferences =otherAppContext.getSharedPreferences("preferences",Context.MODE_PRIVATE);
      SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
      editor.putString("package", "qqqa");
      editor.commit();//提交修改
      textView.setText(sharedPreferences.getString("name","aaaa"));
    } catch (SecurityException e) {
      e.printStackTrace();
      Log.d("qqqq",e.toString());
    } catch (Exception e) {
      e.printStackTrace();
      Log.d("qqqq",e.toString());
    }
   // Log.d(TAG, "root: "+upgradeRootPermission(getPackageCodePath()));

    //TatansPreferences.put("777","fadfd");
  /*  SAXReader reader = new SAXReader();
     document=null;
    try{
      //data/data/net.tatans.coeus.launcher2/tatans.xml
      ///storage/sdcard0/tatans.xml
      document = reader.read(new File(SAVE_XMLFILE_PATH));
      Element node = document.getRootElement();
      listNodes(node);
    //  saveDocument(createXMLDocument());
    }catch (Exception e){
      Log.d(TAG, "onCreate: "+e.toString());
    }*/
  }
  public void listNodes(Element node) {
    System.out.println("当前节点的名称：：" + node.getName());
    List<Attribute> list = node.attributes();
    for (Attribute attr : list) {
      Log.d(TAG, "listNodes: "+attr.getText() + "-----" + attr.getName()
              + "---" + attr.getValue());
    }

    if (!(node.getTextTrim().equals(""))) {
      Log.d(TAG, "getText: " + node.getText());
      node.setText("getText: ");
      saveDocument(document);
      Log.d(TAG, "saveDocument: " + node.getText());
    }
    Iterator<Element> it = node.elementIterator();
    while (it.hasNext()) {
      Element e = it.next();
      listNodes(e);
    }
  }
  /**
   * 保存XML文档
   * @param doc
   * @throws IOException
   */
  public void saveDocument(Document doc)  {
    try {
      OutputFormat format = OutputFormat.createPrettyPrint();
      XMLWriter writer = new XMLWriter(new FileOutputStream(Environment.getDataDirectory()+"/data/com.example.sample/shared_prefs/tatans2.xml"),format);
      writer.write(doc);
      writer.close();
    }catch (IOException e){
      Log.d(TAG, "IOException: " + e.toString());
    }

  }
  public Document createXMLDocument(){
    Document doc = null;
    doc = DocumentHelper.createDocument();
    doc.addComment("edited with XMLSpy v2005 rel. 3 U (http://www.altova.com) by  ()");
//  doc.addDocType("class","//By Jack Chen","saveXML.xsd");
    Element root = doc.addElement("class");
    Element company = root.addElement("company");
    Element person = company.addElement("person");
    person.addAttribute("id","11");
    person.addElement("name").setText("Jack Chen");
    person.addElement("sex").setText("男");
    person.addElement("date").setText("2001-04-01");
    person.addElement("email").setText("chen@163.com");
    person.addElement("QQ").setText("2366001");
    return doc;
  }

  public static boolean upgradeRootPermission(String pkgCodePath) {
    Process process = null;
    DataOutputStream os = null;
    try {
      String cmd="chmod 777 " + pkgCodePath;
      process = Runtime.getRuntime().exec("su"); //切换到root帐号
      os = new DataOutputStream(process.getOutputStream());
      os.writeBytes(cmd + "\n");
      os.writeBytes("exit\n");
      os.flush();
      process.waitFor();
    } catch (Exception e) {
      return false;
    } finally {
      try {
        if (os != null) {
          os.close();
        }
        process.destroy();
      } catch (Exception e) {
      }
    }
    return true;
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


