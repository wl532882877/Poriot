package com.pay.poriot.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.pay.poriot.R;
import com.pay.poriot.base.BaseFragment;
import com.shizhefei.view.indicator.IndicatorViewPager;
import java.util.List;

public class WalletPageFragmentAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {
    private final int[] TITLES = { R.string.wallet_indicator_private_key, R.string.wallet_indicator_mnemonic,R.string.wallet_indicator_official};
    private List<BaseFragment> fragmentList;
    private FragmentManager fm;
    private Context mContext;
    private LayoutInflater inflater;

    public WalletPageFragmentAdapter(Context context, FragmentManager fragmentManager, List<BaseFragment> fragmentList) {
        super(fragmentManager);
        this.fragmentList = fragmentList;
        this.fm = fm;
        this.mContext = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public Fragment getFragmentForPage(int position) {
        return fragmentList.get(position);
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

    public void setFragments(List<BaseFragment> fragments) {
        if (this.fragmentList != null) {
            FragmentTransaction ft = fm.beginTransaction();
            for (Fragment f : this.fragmentList) {
                ft.remove(f);
            }
            ft.commit();
            ft = null;
            fm.executePendingTransactions();
        }
        this.fragmentList = fragments;
        notifyDataSetChanged();
    }
}
