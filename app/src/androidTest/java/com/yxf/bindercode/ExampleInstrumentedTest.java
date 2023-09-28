package com.yxf.bindercode;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URL;

import okhttp3.HttpUrl;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.yxf.bindercode", appContext.getPackageName());
    }

    interface Massage {
        int massage();
    }

    interface Wash {
        void wash();
    }

    class Lucy implements Massage, Wash {
        @Override
        public int massage() {
            System.out.println("Lucy =====massage====");
            return 1;
        }

        @Override
        public void wash() {
            System.out.println("Lucy =====wash====");
        }
    }

    class LiLi implements Massage {
        @Override
        public int massage() {
            System.out.println("LILI massage====");
            return 1;
        }
    }

    @Test
    public void testProxy() {
        Object subjectObj = new Lucy();
        Object subjectObj2 = new LiLi();
        Object object = Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{Massage.class, Wash.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) {
                try {
                    Object invoke = method.invoke(subjectObj, args);
                    System.out.println("invoke==" + invoke);
                    return 3;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
        Massage massage = (Massage) object;
        Object res = massage.massage();
        System.out.println("res:" + res);
        Wash wash = (Wash) object;
        wash.wash();
    }

    @Test
    public void testHttpUrl() {
        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("https")
                .host("www.wanandroid.com")
                .addPathSegment("user/login")
                .addQueryParameter("name", "yxf123")
                .addQueryParameter("password", "123456")
                .build();
        HttpUrl httpUrl1 = HttpUrl.get("https://www.wanandroid.com/user/login?username=yxf1234&password=123456");
        HttpUrl httpUrl2 = HttpUrl.get("https://www.wanandroid.com/");
        HttpUrl httpUrl3 = httpUrl2.newBuilder().addQueryParameter("name", "yxf123")
                .addQueryParameter("password", "123456").build();
        System.out.println("------");
    }
}