package com.pay.poriot.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pay.poriot.R;
import com.pay.poriot.base.BaseActivity;
import com.pay.poriot.base.IPresenter;
import com.pay.poriot.dao.ETHWallet;
import com.pay.poriot.dialog.PagerBottomPopup;
import com.pay.poriot.interact.FetchWalletInteract;
import com.pay.poriot.util.ViewUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 选择地址
 */
public class SelectAddressActivity extends BaseActivity implements View.OnClickListener {
    private static final int QRCODE_SCANNER_REQUEST = 1100;
    @BindView(R.id.recy_wallet)
    RecyclerView walletRecy;
    private WalletAdapter walletAdapter;
    FetchWalletInteract fetchWalletInteract;
    private List<ETHWallet> walletList;

    @Override
    protected IPresenter getPresenter() {
        return null;
    }

    public static void startActivity(Context activity) {
        Intent intent = new Intent(activity, SelectAddressActivity.class);
        ViewUtil.startActivity(activity, intent);
        ViewUtil.right2LeftIn(activity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_address);
        initVariable();
        initViews();
    }

    private void initVariable() {
        ButterKnife.bind(this);
        walletList = new ArrayList<>();
        fetchWalletInteract = new FetchWalletInteract();
        fetchWalletInteract.fetch().subscribe(this::showWallet);
    }

    public void showWallet(final List<ETHWallet> ethWallets) {
        walletList.clear();
        walletList.addAll(ethWallets);
        walletAdapter.replaceData(walletList);
        walletAdapter.notifyDataSetChanged();
    }

    private void initViews() {
        super.initTitle();
        setTitleText(getString(R.string.select_address));
        setTitleLeftIcon(R.drawable.btn_back_black, this);
        setTitleRightIcon(R.mipmap.bg_scan, this);

        walletAdapter = new WalletAdapter();
        walletRecy.setLayoutManager(new LinearLayoutManager(this));
        walletRecy.setAdapter(walletAdapter);
        walletAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<ETHWallet> wallet = adapter.getData();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_title_left:
                finish();
                break;
            case R.id.ll_title_right:
                Intent intent = new Intent(mContext, QRCodeScannerActivity.class);
                startActivityForResult(intent, QRCODE_SCANNER_REQUEST);
                break;
        }
    }

    public class WalletAdapter extends BaseQuickAdapter<ETHWallet, BaseViewHolder> {
        public WalletAdapter() {
            super(R.layout.select_item_wallet);
        }

        @Override
        protected void convert(BaseViewHolder viewHolder, ETHWallet bean) {
            ImageView mIvWallet = viewHolder.getView(R.id.wallet_icon);
            int position = viewHolder.getAdapterPosition();
            switch (position) {
                case 0:
                    mIvWallet.setImageResource(R.mipmap.bg_wallet_heade1);
                    break;
                case 1:
                    mIvWallet.setImageResource(R.mipmap.bg_wallet_heade2);
                    break;
                case 2:
                    mIvWallet.setImageResource(R.mipmap.bg_wallet_heade3);
                    break;
                case 3:
                    mIvWallet.setImageResource(R.mipmap.bg_wallet_heade4);
                    break;
                case 4:
                    mIvWallet.setImageResource(R.mipmap.bg_wallet_heade5);
                    break;
                case 5:
                    mIvWallet.setImageResource(R.mipmap.bg_wallet_heade6);
                    break;
                case 6:
                    mIvWallet.setImageResource(R.mipmap.bg_wallet_heade7);
                    break;
                default:
                    mIvWallet.setImageResource(R.mipmap.bg_wallet_heade1);
                    break;
            }
            viewHolder.setText(R.id.wallet_name, bean.getName())
                    .setText(R.id.wallet_address, bean.getAddress());
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == QRCODE_SCANNER_REQUEST) {
            if (data != null) {
                String scanResult = data.getStringExtra("scan_result");
            }
        }
    }
}
