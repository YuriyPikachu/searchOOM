package net.tatans.rhea.network.view;


import android.app.Activity;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceGroup;


import net.tatans.coeus.network.tools.TatansLogUtils;
import net.tatans.rhea.network.event.EventBase;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class TatansIoc {

    private TatansIoc() {
    }

    public static void inject(android.view.View view) {
        injectObject(view, new ViewFinder(view));
    }

    public static void inject(Activity activity) {
        injectObject(activity, new ViewFinder(activity));
    }

    public static void inject(PreferenceActivity preferenceActivity) {
        injectObject(preferenceActivity, new ViewFinder(preferenceActivity));
    }

    public static void inject(Object handler, android.view.View view) {
        injectObject(handler, new ViewFinder(view));
    }

    public static void inject(Object handler, Activity activity) {
        injectObject(handler, new ViewFinder(activity));
    }

    public static void inject(Object handler, PreferenceGroup preferenceGroup) {
        injectObject(handler, new ViewFinder(preferenceGroup));
    }

    public static void inject(Object handler, PreferenceActivity preferenceActivity) {
        injectObject(handler, new ViewFinder(preferenceActivity));
    }

    @SuppressWarnings("ConstantConditions")
    private static void injectObject(Object handler, ViewFinder finder) {

        Class<?> handlerType = handler.getClass();

        // inject ContentView
        ContentView contentView = handlerType.getAnnotation(ContentView.class);
        if (contentView != null) {
            try {
                Method setContentViewMethod = handlerType.getMethod("setContentView", int.class);
                setContentViewMethod.invoke(handler, contentView.value());
            } catch (Throwable e) {
                TatansLogUtils.e(e.getMessage(), e);
            }
        }

        // inject view
        Field[] fields = handlerType.getDeclaredFields();
        if (fields != null && fields.length > 0) {
            for (Field field : fields) {
                ViewIoc viewIocInject = field.getAnnotation(ViewIoc.class);
                if (viewIocInject != null) {
                    try {
                        android.view.View view = finder.findViewById(viewIocInject.value(), viewIocInject.parentId());
                        if (view != null) {
                            field.setAccessible(true);
                            field.set(handler, view);
                        }
                    } catch (Throwable e) {
                        TatansLogUtils.e(e.getMessage(), e);
                    }
                } else {
                    ResInject resInject = field.getAnnotation(ResInject.class);
                    if (resInject != null) {
                        try {
                            Object res = ResLoader.loadRes(
                                    resInject.type(), finder.getContext(), resInject.id());
                            if (res != null) {
                                field.setAccessible(true);
                                field.set(handler, res);
                            }
                        } catch (Throwable e) {
                            TatansLogUtils.e(e.getMessage(), e);
                        }
                    } else {
                        PreferenceInject preferenceInject = field.getAnnotation(PreferenceInject.class);
                        if (preferenceInject != null) {
                            try {
                                Preference preference = finder.findPreference(preferenceInject.value());
                                if (preference != null) {
                                    field.setAccessible(true);
                                    field.set(handler, preference);
                                }
                            } catch (Throwable e) {
                                TatansLogUtils.e(e.getMessage(), e);
                            }
                        }
                    }
                }
            }
        }

        // inject event
        Method[] methods = handlerType.getDeclaredMethods();
        if (methods != null && methods.length > 0) {
            for (Method method : methods) {
                Annotation[] annotations = method.getDeclaredAnnotations();
                if (annotations != null && annotations.length > 0) {
                    for (Annotation annotation : annotations) {
                        Class<?> annType = annotation.annotationType();
                        if (annType.getAnnotation(EventBase.class) != null) {
                            method.setAccessible(true);
                            try {
                                // ProGuard£º-keep class * extends java.lang.annotation.Annotation { *; }
                                Method valueMethod = annType.getDeclaredMethod("value");
                                Method parentIdMethod = null;
                                try {
                                    parentIdMethod = annType.getDeclaredMethod("parentId");
                                } catch (Throwable e) {
                                }
                                Object values = valueMethod.invoke(annotation);
                                Object parentIds = parentIdMethod == null ? null : parentIdMethod.invoke(annotation);
                                int parentIdsLen = parentIds == null ? 0 : Array.getLength(parentIds);
                                int len = Array.getLength(values);
                                for (int i = 0; i < len; i++) {
                                    ViewInjectInfo info = new ViewInjectInfo();
                                    info.value = Array.get(values, i);
                                    info.parentId = parentIdsLen > i ? (Integer) Array.get(parentIds, i) : 0;
                                    EventListenerManager.addEventMethod(finder, info, annotation, handler, method);
                                }
                            } catch (Throwable e) {
                                TatansLogUtils.e(e.getMessage(), e);
                            }
                        }
                    }
                }
            }
        }
    }

}
