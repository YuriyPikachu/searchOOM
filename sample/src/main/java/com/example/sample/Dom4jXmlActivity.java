
package com.example.sample;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import net.tatans.coeus.network.tools.BaseActivity;
import net.tatans.rhea.network.event.OnClick;
import net.tatans.rhea.network.view.ContentView;
import net.tatans.rhea.network.view.ViewIoc;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@ContentView(R.layout.main_activity)
public class Dom4jXmlActivity extends BaseActivity {
  private static final String FILE_NAME = Environment.getExternalStorageDirectory().toString()  + "/tatans/data/defaultSettingApp.xml";
  private static String TAG="QQQQ";
  private Map<String,String> mapDefaultSettingApp;
  @ViewIoc(R.id.async_task)
  Button bt_single;
  @ViewIoc(R.id.tv_show)
  TextView textView;
  String Imei;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }
  @OnClick(R.id.async_task)
  public void aVoid(){
    getDefaultAppXml("");
    //DataCleanManager.cleanUserData("me.ele");
  }
  @OnClick(R.id.async_task2)
  public void aVoid2(){
  //  DataCleanManager.cleanInternalCache("me.ele");
    try {
      Context otherAppContext = this.createPackageContext("com.example.yulia.myapplication", Context.CONTEXT_IGNORE_SECURITY);
      SharedPreferences sharedPreferences =otherAppContext.getSharedPreferences("preferences",Context.MODE_PRIVATE);
      SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
      editor.putString("name", "qqqa");
      editor.commit();//提交修改
      textView.setText(sharedPreferences.getString("name","aaaa"));
    } catch (SecurityException e) {
      e.printStackTrace();
      Log.d("qqqq",e.toString());
    } catch (Exception e) {
      e.printStackTrace();
      Log.d("qqqq",e.toString());
    }
  }
  @Override
  protected void onResume() {
    super.onResume();
   // Log.d("QQQQ", "TatansPreferences:"+ TatansPreferences.get("package","aaa"));
  }
  private String getDefaultAppXml(String key){
    if (null==mapDefaultSettingApp){
      readTatansManifest();
    }
   return mapDefaultSettingApp.get(key);
  }
  private void removeDefaultAppXml(String key,String value){
    mapDefaultSettingApp = new HashMap<String,String>();
    SAXReader reader = new SAXReader();
    Document document= null;
    try {
      document = reader.read(new File(FILE_NAME));
      Element node = document.getRootElement();
      List nodes = node.elements("string");
      for (Iterator it = nodes.iterator(); it.hasNext();) {
        Element elm = (Element) it.next();
        if (elm.attributeValue("name").equals(key)){
          document.remove(elm);
          saveDocument(document);
        }
        mapDefaultSettingApp.put(elm.attributeValue("name"),elm.getText());
      }
    } catch (DocumentException e) {
      Log.e("TatansMainService", "DocumentException: "+e.toString());
    }
  }
  private void putDefaultAppXml(String key,String value){
    mapDefaultSettingApp = new HashMap<String,String>();
    SAXReader reader = new SAXReader();
    Document document= null;
    try {
      document = reader.read(new File(FILE_NAME));
      Element node = document.getRootElement();
      List nodes = node.elements("string");
      for (Iterator it = nodes.iterator(); it.hasNext();) {
        Element elm = (Element) it.next();
        if (elm.attributeValue("name").equals(key)){
          elm.setText(value);
          saveDocument(document);
        }
        mapDefaultSettingApp.put(elm.attributeValue("name"),elm.getText());
      }
    } catch (DocumentException e) {
      Log.e("TatansMainService", "DocumentException: "+e.toString());
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
      XMLWriter writer = new XMLWriter(new FileOutputStream(FILE_NAME),format);
      writer.write(doc);
      writer.close();
    }catch (IOException e){
      Log.d(TAG, "IOException: " + e.toString());
    }
  }
  private void readTatansManifest(){
    mapDefaultSettingApp = new HashMap<String,String>();
    SAXReader reader = new SAXReader();
    Document document= null;
    try {
      document = reader.read(new File(FILE_NAME));
      Element node = document.getRootElement();
      List nodes = node.elements("string");
      for (Iterator it = nodes.iterator(); it.hasNext();) {
        Element elm = (Element) it.next();
        mapDefaultSettingApp.put(elm.attributeValue("name"),elm.getText());
      }
    } catch (DocumentException e) {
      Log.e("TatansMainService", "DocumentException: "+e.toString());
    }
  }
}


