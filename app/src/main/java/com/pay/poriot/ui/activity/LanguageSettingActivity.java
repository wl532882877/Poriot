package com.pay.poriot.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import com.pay.poriot.R;
import com.pay.poriot.base.BaseActivity;
import com.pay.poriot.base.IPresenter;
import com.pay.poriot.bean.EventBusModel;
import com.pay.poriot.config.Config;
import com.pay.poriot.util.BusUtil;
import com.pay.poriot.util.LanguageUtil;
import com.pay.poriot.util.ViewUtil;
import java.util.Locale;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 语言设置
 */
public class LanguageSettingActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.cb_cn)
    CheckBox mCbCn;
    @BindView(R.id.cb_en)
    CheckBox mCbEn;
    public static void startActivity(Context activity) {
        Intent intent = new Intent(activity, LanguageSettingActivity.class);
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
        setContentView(R.layout.activity_language_setting);
        initVariable();
        initViews();
    }

    private void initVariable() {
        ButterKnife.bind(this);
    }

    private void initViews() {
        super.initTitle();
        setTitleText(getString(R.string.language_settings));
        setTitleLeftIcon(R.drawable.btn_back_black, this);

        String lan = LanguageUtil.getAppLanguage();
        if (lan.equals("en")) {
            mCbCn.setVisibility(View.GONE);
            mCbEn.setVisibility(View.VISIBLE);
        } else {
            mCbCn.setVisibility(View.VISIBLE);
            mCbEn.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_title_left:
                finish();
                break;
            case R.id.rl_cn:
                mCbCn.setVisibility(View.VISIBLE);
                mCbEn.setVisibility(View.GONE);
                changeAppLanguage();
                break;
            case R.id.rl_en:
                mCbCn.setVisibility(View.GONE);
                mCbEn.setVisibility(View.VISIBLE);
                changeAppLanguage();
                break;
            default:
                break;
        }
    }

    private void changeAppLanguage() {
        Locale appLocale = LanguageUtil.getAppLocale();
        LanguageUtil.changeAppLanguage(this, appLocale, true);
        BusUtil.post(new EventBusModel(Config.KEY_APP_LANGUAGE, null));
        recreate();
    }
}
