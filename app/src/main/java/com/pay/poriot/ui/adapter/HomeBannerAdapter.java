package com.pay.poriot.ui.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.pay.poriot.R;
import com.pay.poriot.dao.ETHWallet;
import com.pay.poriot.ui.activity.QRCodeShareActivity;
import com.pay.poriot.ui.activity.QRCollectionActivity;
import com.pay.poriot.ui.activity.SecurityInfoActivity;

import java.util.List;

/**
 * 首页钱包适配器
 */
public class HomeBannerAdapter extends PagerAdapter {
    private Activity mActivity;
    private List<ETHWallet> ethWallets;

    public HomeBannerAdapter(Activity mActivity, List<ETHWallet> ethWallets) {
        this.mActivity = mActivity;
        this.ethWallets = ethWallets;
    }

    @Override
    public int getCount() {
        if (null == ethWallets) {
            return 0;
        }
        if (ethWallets.size() > 1) {
            return Integer.MAX_VALUE;
        } else {
            return ethWallets.size();
        }
    }

    public int getSize() {
        if (ethWallets != null) {
            return ethWallets.size();
        } else {
            return 0;
        }
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.view_home_wallet, null);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        LinearLayout mIvSetting = view.findViewById(R.id.wallet_setting);
        LinearLayout mIvCode = view.findViewById(R.id.wallet_code);
        TextView mTvName = view.findViewById(R.id.wallet_name);
        TextView mTvAddress = view.findViewById(R.id.wallet_address);

        final ETHWallet bean = ethWallets.get(position % getSize());
        mTvName.setText(bean.getName());
        mTvAddress.setText(bean.getAddress());

        mIvSetting.setOnClickListener(v -> {
            SecurityInfoActivity.startActivity(mActivity, bean.getName(), bean.getAddress());
        });
        mIvCode.setOnClickListener(v -> {
            QRCollectionActivity.startActivity(mActivity,bean.getName(), bean.getAddress());
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
