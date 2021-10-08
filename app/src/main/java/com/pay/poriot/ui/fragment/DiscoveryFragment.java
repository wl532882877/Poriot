package com.pay.poriot.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pay.poriot.R;
import com.pay.poriot.base.BaseFragment;
import com.pay.poriot.bean.DiscoveryBean;
import com.pay.poriot.presenter.DiscoveryPre;
import com.pay.poriot.ui.view.DiscoveryView;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 发现
 */
public class DiscoveryFragment extends BaseFragment<DiscoveryPre> implements DiscoveryView, View.OnClickListener {
    @BindView(R.id.refrsh_discover)
    SmartRefreshLayout refreshDiscover;
    @BindView(R.id.recy_discover)
    RecyclerView recyDiscover;
    private List<DiscoveryBean> discoveryBeanList;
    private DiscoveryAdapter discoveryAdapter;

    public static DiscoveryFragment newInstance() {
        return new DiscoveryFragment();
    }

    @Override
    protected DiscoveryPre getPresenter() {
        return new DiscoveryPre(getActivity(), this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        initVariables();
        View mRootView = inflater.inflate(R.layout.fragment_discovery, container, false);
        initViews(mRootView);
        return mRootView;
    }

    private void initVariables() {
        discoveryBeanList = new ArrayList<>();
    }

    private void initViews(final View view) {
        ButterKnife.bind(this, view);
        discoveryAdapter = new DiscoveryAdapter();
        recyDiscover.setLayoutManager(new LinearLayoutManager(getContext()));
        recyDiscover.setAdapter(discoveryAdapter);

        refreshDiscover.setRefreshHeader(new ClassicsHeader(getContext()));
        refreshDiscover.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000);
            }
        });
        setData();
    }

    private void setData() {
        DiscoveryBean discoveryBean = new DiscoveryBean();
        discoveryBean.setName("DeFi Era");
        discoveryBean.setContent("一个公平、智能的互助系统");

        DiscoveryBean discoveryBean1 = new DiscoveryBean();
        discoveryBean1.setName("Lucky Charma");
        discoveryBean1.setContent("一款区块链上的幸运抽奖回馈游戏");

        DiscoveryBean discoveryBean2 = new DiscoveryBean();
        discoveryBean2.setName("Free Cash");
        discoveryBean2.setContent("一个去中心化的财务(DeFi )分配系统");

        discoveryBeanList.clear();
        discoveryBeanList.add(discoveryBean);
        discoveryBeanList.add(discoveryBean1);
        discoveryBeanList.add(discoveryBean2);
        discoveryAdapter.replaceData(discoveryBeanList);
        discoveryAdapter.notifyDataSetChanged();
    }

    public class DiscoveryAdapter extends BaseQuickAdapter<DiscoveryBean, BaseViewHolder> {
        public DiscoveryAdapter() {
            super(R.layout.item_discovery);
        }

        @Override
        protected void convert(BaseViewHolder viewHolder, DiscoveryBean bean) {
            viewHolder.setText(R.id.tv_name, bean.getName())
                    .setText(R.id.tv_content, bean.getContent());
            ImageView icon = viewHolder.getView(R.id.iv_icon);
            if ("DeFi Era".equals(bean.getName())) {
                icon.setBackgroundResource(R.mipmap.bg_defi);
            } else if ("Lucky Charma".equals(bean.getName())) {
                icon.setBackgroundResource(R.mipmap.bg_lucky);
            } else {
                icon.setBackgroundResource(R.mipmap.bg_free);
            }
        }
    }

    @Override
    public void onClick(View view) {

    }
}
