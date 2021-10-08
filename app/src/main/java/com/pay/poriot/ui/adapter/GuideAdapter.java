package com.pay.poriot.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import com.pay.poriot.R;
import com.pay.poriot.ui.activity.CreateWalletActivity;
import com.pay.poriot.ui.activity.ImportWalletActivity;

import java.util.List;

public class GuideAdapter extends PagerAdapter {

    private List<Integer> mResIds;
    private Activity mActivity;

    public GuideAdapter(Activity activity, List<Integer> resIds) {
        this.mResIds = resIds;
        this.mActivity = activity;
    }

    @Override
    public int getCount() {
        if (mResIds != null) {
            return mResIds.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View arg0, @NonNull Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = View.inflate(mActivity, R.layout.item_guide, null);
        ImageView ivGuide = view.findViewById(R.id.iv_guide);
        LinearLayout mRlGuide = view.findViewById(R.id.ll_guide);
        Button mBtCreat = view.findViewById(R.id.bt_creat);
        Button mBtImport = view.findViewById(R.id.bt_import);
        ivGuide.setImageResource(mResIds.get(position));
        if (position == mResIds.size() - 1) {
            mRlGuide.setVisibility(View.VISIBLE);
        } else {
            mRlGuide.setVisibility(View.GONE);
        }
        mBtCreat.setOnClickListener(v -> {
            CreateWalletActivity.startActivity(mActivity);
        });
        mBtImport.setOnClickListener(v -> {
            ImportWalletActivity.startActivity(mActivity);
        });
        container.addView(view);
        return view;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}

