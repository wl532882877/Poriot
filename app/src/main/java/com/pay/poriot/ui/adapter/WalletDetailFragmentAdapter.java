package com.pay.poriot.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.pay.poriot.R;
import com.pay.poriot.ui.fragment.WalletDetailFragment;
import com.shizhefei.view.indicator.IndicatorViewPager;

public class WalletDetailFragmentAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {
    private final int[] TITLES = { R.string.all, R.string.step,R.string.forward,R.string.other};
    private FragmentManager fm;
    private Context mContext;
    private LayoutInflater inflater;

    public WalletDetailFragmentAdapter(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);
        this.mContext = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return TITLES.length;
    }

    @Override
    public Fragment getFragmentForPage(int position) {
        return WalletDetailFragment.newInstance(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return -2;
    }


    @Override
    public View getViewForTab(int position, View convertView, ViewGroup container) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.wallet_indicator_tab, container, false);
        }
        TextView textView = (TextView) convertView;
        textView.setText(TITLES[position]);
        return textView;
    }
}
