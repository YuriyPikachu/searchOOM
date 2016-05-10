/*
 * Copyright (c) 2013. wyouflf (wyouflf@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.tatans.rhea.network.view;

import android.view.View;


import net.tatans.coeus.network.tools.TatansLogUtils;
import net.tatans.rhea.network.event.EventBase;
import net.tatans.coeus.network.tools.TatansLog;

import java.lang.annotation.Annotation;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;

public class EventListenerManager {

    private EventListenerManager() {
    }

    /**
     * k1: viewInjectInfo
     * k2: interface Type
     * value: listener
     */
    private final static DoubleKeyValueMap<ViewInjectInfo, Class<?>, Object> listenerCache =
            new DoubleKeyValueMap<ViewInjectInfo, Class<?>, Object>();

    public static void addEventMethod(
            ViewFinder finder,
            ViewInjectInfo info,
            Annotation eventAnnotation,
            Object handler,
            Method method) {
        try {
            View view = finder.findViewByInfo(info);
            if (view != null) {
                EventBase eventBase = eventAnnotation.annotationType().getAnnotation(EventBase.class);
                Class<?> listenerType = eventBase.listenerType();
                String listenerSetter = eventBase.listenerSetter();
                String methodName = eventBase.methodName();
                boolean addNewMethod = false;
                Object listener = listenerCache.get(info, listenerType);
                DynamicHandler dynamicHandler = null;
                if (listener != null) {
                    dynamicHandler = (DynamicHandler) Proxy.getInvocationHandler(listener);
                    addNewMethod = handler.equals(dynamicHandler.getHandler());
                    if (addNewMethod) {
                        dynamicHandler.addMethod(methodName, method);
                    }
                }
                if (!addNewMethod) {
                    dynamicHandler = new DynamicHandler(handler);
                    dynamicHandler.addMethod(methodName, method);
                    TatansLog.e("newProxyInstance->listenerType:"+listenerType);
                    listener = Proxy.newProxyInstance(
                            listenerType.getClassLoader(),
                            new Class<?>[]{listenerType},
                            dynamicHandler);
                    TatansLog.e("是否存在该回调，没有就不会执行："+listener.toString());
                    listenerCache.put(info, listenerType, listener);
                }
                TatansLog.d("view.getClass().getMethod->a1:"+listenerSetter);
                TatansLog.d("view.getClass().getMethod->a2:"+listenerType);
                Method setEventListenerMethod = view.getClass().getMethod(listenerSetter, listenerType);
                TatansLog.d("view:"+view.toString());
                TatansLog.d("listener:"+listener.toString());
                TatansLog.d("getReturnType:"+setEventListenerMethod.getReturnType());
                setEventListenerMethod.invoke(view, listener);
            }
        } catch (Throwable e) {
            TatansLogUtils.e(e.getMessage(), e);
        }
    }

    public static class DynamicHandler implements InvocationHandler {
        private WeakReference<Object> handlerRef;
        private final HashMap<String, Method> methodMap = new HashMap<String, Method>(1);

        public DynamicHandler(Object handler) {
            this.handlerRef = new WeakReference<Object>(handler);
        }

        public void addMethod(String name, Method method) {
            TatansLog.e("DynamicHandler->addMethod->name:"+name+"   method:"+method.toString());
            methodMap.put(name, method);
            TatansLog.e("DynamicHandler->methodMap:"+methodMap.toString());
        }

        public Object getHandler() {
            TatansLog.e("DynamicHandler->getHandler:");
            return handlerRef.get();
        }

        public void setHandler(Object handler) {
            TatansLog.e("DynamicHandler->setHandler:");
            this.handlerRef = new WeakReference<Object>(handler);
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Object handler = handlerRef.get();
            if (handler != null) {
                String methodName = method.getName();
                method = methodMap.get(methodName);
                if ("toString".equals(methodName)) {//toString特殊处理
                    return DynamicHandler.class.getSimpleName();
                }
                TatansLog.d("DynamicHandler->invoke:"+methodName);
                TatansLog.d("DynamicHandler->invoke:"+method);
                TatansLog.d("DynamicHandler->invoke:"+method.getParameterTypes().length);
                TatansLog.d("DynamicHandler->invoke:"+method.getReturnType());
                if (method != null) {
                    //当要调用的方法无参时，将args置空
                    boolean noParams = method.getParameterTypes().length == 0;
                    args = noParams? null :args;
                    Object o=null;
                    try {
                         o=method.invoke(handler, args);
                    }catch (Exception e){
                        TatansLogUtils.e(e.toString());
                    }
                    return o;
                }
            }
            return null;
        }
    }
}
