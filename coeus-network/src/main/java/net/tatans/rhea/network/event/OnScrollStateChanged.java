package net.tatans.rhea.network.event;

import android.widget.AbsListView;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Yuriy on 16-04-30.
 *  �������� void
 * ����Ĳ��� AbsListView view, int scrollState
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@EventBase(
        listenerType = AbsListView.OnScrollListener.class,
        listenerSetter = "setOnScrollListener",
        methodName = "onScrollStateChanged")
public @interface OnScrollStateChanged {
    int[] value();

    int[] parentId() default 0;
}
