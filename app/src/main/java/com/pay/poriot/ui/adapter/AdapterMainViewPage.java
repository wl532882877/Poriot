package com.pay.poriot.ui.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.pay.poriot.ui.fragment.MainFragment;
import com.pay.poriot.ui.fragment.MineFragment;
import com.pay.poriot.ui.fragment.DiscoveryFragment;
import java.util.ArrayList;
import java.util.List;

public class AdapterMainViewPage extends FragmentStateAdapter {

    public AdapterMainViewPage(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(MainFragment.newInstance());
        fragments.add(DiscoveryFragment.newInstance());
        fragments.add(MineFragment.newInstance());
        return fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
