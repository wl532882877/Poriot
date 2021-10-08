package com.pay.poriot.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.viewpager2.widget.ViewPager2;
import com.pay.poriot.R;
import com.pay.poriot.base.BaseActivity;
import com.pay.poriot.base.IPresenter;
import com.pay.poriot.bean.EventBusModel;
import com.pay.poriot.config.Config;
import com.pay.poriot.ui.adapter.AdapterMainViewPage;
import com.pay.poriot.util.LanguageUtil;
import com.pay.poriot.util.ViewUtil;
import java.util.Locale;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {
    private static final long EXIT_APP_TIME = 2000;
    @BindView(R.id.rg_tab)
    RadioGroup rgTab;
    @BindView(R.id.vp_fragment)
    ViewPager2 vpFragment;
    private long mExitTime;

    public static void startActivity(Context activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        ViewUtil.startActivity(activity, intent);
        ViewUtil.right2LeftIn(activity);
    }


    @Override
    protected IPresenter getPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLange();
        setContentView(R.layout.activity_main);
        initVariable();
        initViews();
    }

    private void initVariable() {
        ButterKnife.bind(this);
    }

    public void setLange() {
        String lan = LanguageUtil.getAppLanguage();
        if (lan.equals("en")) {
            setLocale(Locale.US);
        } else if (lan.equals("zh")) {
            setLocale(Locale.SIMPLIFIED_CHINESE);
        }
    }

    public void setLocale(Locale myLocale) {
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        LanguageUtil.saveLanguageSetting(myLocale);
    }

    private void initViews() {
        rgTab.setOnCheckedChangeListener(new onChanged());
        vpFragment.setAdapter(new AdapterMainViewPage(this));
        vpFragment.setOffscreenPageLimit(1);
        vpFragment.setCurrentItem(0);
        vpFragment.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        ((RadioButton) findViewById(R.id.rb_home)).setChecked(true);
                        break;
                    case 1:
                        ((RadioButton) findViewById(R.id.rb_discover)).setChecked(true);
                        break;
                    case 2:
                        ((RadioButton) findViewById(R.id.rb_mine)).setChecked(true);
                        break;
                    default:
                        ((RadioButton) findViewById(R.id.rb_home)).setChecked(true);
                        break;
                }
            }
        });
    }

    private class onChanged implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.rb_home) {
                vpFragment.setCurrentItem(0);
            } else if (checkedId == R.id.rb_discover) {
                vpFragment.setCurrentItem(1);
            } else {
                vpFragment.setCurrentItem(2);
            }
        }
    }

    @Override
    public void onEventMainThread(EventBusModel model) {
        switch (model.getEventBusAction()) {
            case Config.KEY_APP_LANGUAGE:
                changeLange();
                break;
            default:
              break;
        }
    }

    private void changeLange(){
        String lan = LanguageUtil.getAppLanguage();
        if(lan.equals("zh")){
            setLocale(Locale.US);
        }else if(lan.equals("en")){
            setLocale(Locale.SIMPLIFIED_CHINESE);
        }
        recreate();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - mExitTime > EXIT_APP_TIME) {
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void finish() {
        super.finish();
        ViewUtil.left2RightOut(this);
    }
}
