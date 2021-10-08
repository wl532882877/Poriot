package com.pay.poriot.util;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;
import com.pay.poriot.config.Config;
import java.util.Locale;

public class LanguageUtil {
    public static void changeAppLanguage(Context context, Locale locale, boolean persistence) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(locale);
        } else {
            configuration.locale = locale;
        }
        resources.updateConfiguration(configuration, metrics);
        if (persistence) {
            saveLanguageSetting(locale);
        }
    }


    public static void saveLanguageSetting(Locale locale) {
        SharedPreferenceUtil.saveValue(Config.KEY_GLOBAL_FILE, Config.KEY_APP_LANGUAGE, locale.getLanguage());
    }

    public static String getAppLanguage() {
        return SharedPreferenceUtil.getStringValueByKey(Config.KEY_GLOBAL_FILE, Config.KEY_APP_LANGUAGE);
    }

    public static Locale getAppLocale() {
        String lang = SharedPreferenceUtil.getStringValueByKey(Config.KEY_GLOBAL_FILE, Config.KEY_APP_LANGUAGE);
        if (!lang.equals(Locale.SIMPLIFIED_CHINESE.getLanguage()) && !lang.equals(Locale.US.getLanguage())) {
            lang = Locale.SIMPLIFIED_CHINESE.getLanguage();
        }
        Locale locale = new Locale(lang);
        return locale;
    }
}
