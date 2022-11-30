package com.etc.utils;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.Locale;


/**
 * @author :Create by Xia燚
 * 时间：2022/6/17
 * 邮箱：XiahaotianV@163.com
 * TTS 语音工具类
 **/
public class TTSUtils {
    static long LastTime = 0;
    private static TextToSpeech t;
    private static boolean xunfeiIsOk;

    public static void init(final Context context) {
        if (t == null) {
            t = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if (status == TextToSpeech.SUCCESS) {
                        if (t == null) {
                            t = new TextToSpeech(context, this);
                            return;
                        }
                        int result = t.setLanguage(Locale.CHINESE);
                        if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                            Log.d("xunFei", "讯飞语音不可用");
                            xunfeiIsOk = false;
                        } else {
                            Log.d("xunFei", "讯飞语音可用");
                            t.setLanguage(Locale.US);
                            xunfeiIsOk = true;
                        }
                    }
                }
            });
        }
    }

    public static void unInit() {
        if (t != null) {
            t.stop();
            t.shutdown();
            t = null;
        }
    }

    public static boolean IsXunfeiIsOk() {
        return xunfeiIsOk;
    }

    //最多五秒重复一次
    public static void speak(String text) {
        t.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }
}
