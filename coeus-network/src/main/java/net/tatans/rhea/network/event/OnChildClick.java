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

package net.tatans.rhea.network.event;

import android.widget.ExpandableListView;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Yuriy on 16-04-30.
 *   返回类型 boolean
 *   @return True if the click was handled
 *  传入的参数 ExpandableListView parent, ViewIoc v, int groupPosition,int childPosition, long id
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@EventBase(
        listenerType = ExpandableListView.OnChildClickListener.class,
        listenerSetter = "setOnChildClickListener",
        methodName = "onChildClick")
public @interface OnChildClick {
    int[] value();

    int[] parentId() default 0;
}
