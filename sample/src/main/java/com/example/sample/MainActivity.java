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


import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import net.tatans.coeus.core.test.ContentView;
import net.tatans.coeus.core.test.ViewUtils;
import net.tatans.coeus.core.test.event.OnClick;
import net.tatans.coeus.core.test.event.OnLongClick;
import net.tatans.coeus.core.test.event.OnTouch;
import net.tatans.coeus.network.tools.TatansLog;

@ContentView(R.layout.main_activity)
public class MainActivity extends Activity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ViewUtils.inject(this);
    //  x.view().inject(this);
    /*Button button = (Button)findViewById(R.id.async_task);
    button.setOnLongClickListener(new View.OnLongClickListener(){

      @Override
      public boolean onLongClick(View view) {
        TatansLog.e("3333333");
        return false;
      }
    });*/
   /* Button button = (Button)findViewById(R.id.async_task);
      button.setOnClickListener(new View.OnClickListener(){
          @Override
          public void onClick(View view) {
              TatansLog.e("3333333");
          }
      });*/
      /*button.setAccessibilityDelegate(new View.AccessibilityDelegate(){
          @Override
          public void onInitializeAccessibilityEvent(View host, AccessibilityEvent event) {
              super.onInitializeAccessibilityEvent(host, event);
              //焦点进入所在的view
              if(event.getEventType() == AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED){
                  TatansLog.e("22222222");
              }
              //焦点离开所在的view
              else if(event.getEventType() == AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUS_CLEARED){
                  TatansLog.e("3333333");
              }
          }
      });*/
  }
//  @OnTouch(R.id.async_task)
  @OnLongClick(R.id.async_task)
  public boolean aVoid(View v){
    TatansLog.e("11111111111");
    return false;
  }
    /* @OnInitializeAccessibility(R.id.async_task)
     public void aVoid(View host, AccessibilityEvent event){
       TatansLog.e("55555555555");
     }*/
 /* @OnTouch(R.id.async_task)
  public void aVoid(View v, MotionEvent event){
    TatansLog.e("7777777");
  }*/
 /* @OnFocusChange(R.id.async_task)
  public void  aVoid(View v, boolean hasFocus){

  }*/
 /* void startAsyncTask() {
    // This async task is an anonymous class and therefore has a hidden reference to the outer
    // class MainActivity. If the activity gets destroyed before the task finishes (e.g. rotation),
    // the activity instance will leak.
    new AsyncTask<Void, Void, Void>() {
      @Override protected Void doInBackground(Void... params) {
        // Do some slow work in background
        SystemClock.sleep(20000);
        return null;
      }
    }.execute();
  }*/
}


