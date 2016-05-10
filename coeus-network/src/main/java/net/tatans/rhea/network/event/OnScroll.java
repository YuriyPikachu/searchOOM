package net.tatans.rhea.network.event;

import android.widget.AbsListView;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Yuriy on 16-04-30.
 *  返回类型 void
 * 传入的参数 AbsListView view, int firstVisibleItem, int visibleItemCount,int totalItemCount
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@EventBase(
        listenerType = AbsListView.OnScrollListener.class,
        listenerSetter = "setOnScrollListener",
        methodName = "onScroll")
public @interface OnScroll {
    int[] value();

    int[] parentId() default 0;
}
