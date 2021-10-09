package com.pay.poriot.ui.activity;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pay.poriot.R;
import com.pay.poriot.base.BaseActivity;
import com.pay.poriot.base.IPresenter;
import com.pay.poriot.util.BitmapUtils;
import com.pay.poriot.util.DensityUtils;
import com.pay.poriot.util.GlideUtil;
import com.pay.poriot.util.RxUtil;
import com.pay.poriot.util.ViewUtil;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;
import io.reactivex.Single;

/**
 * 收款码
 */
public class QRCollectionActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.wallet_name)
    TextView mWalletName;
    @BindView(R.id.qr_code)
    ImageView mQrCode;
    @BindView(R.id.wallet_address)
    TextView mWalletAddress;
    private String walletName;
    private String walletAddress;
    private File mPhotoFile;

    @Override
    protected IPresenter getPresenter() {
        return null;
    }

    public static void startActivity(Context activity, String name, String address) {
        Intent intent = new Intent(activity, QRCollectionActivity.class);
        intent.putExtra("wallet_name", name);
        intent.putExtra("wallet_address", address);
        ViewUtil.startActivity(activity, intent);
        ViewUtil.right2LeftIn(activity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_qr_code);
        initVariable();
        initViews();
    }

    private void initVariable() {
        ButterKnife.bind(this);
    }

    private void initViews() {
        super.initTitle();
        setTitleText(getString(R.string.collection));
        setTitleLeftIcon(R.drawable.btn_back_black, this);
        walletName = getIntent().getStringExtra("wallet_name");
        walletAddress = getIntent().getStringExtra("wallet_address");
        mWalletName.setText(walletName);
        mWalletAddress.setText(walletAddress);
        initAddressQRCode();
    }

    private void initAddressQRCode() {
        Single.fromCallable(
                () -> {
                    return QRCodeEncoder.syncEncodeQRCode(walletAddress, DensityUtils.dip2px(270), Color.parseColor("#000000"));
                }
        ).subscribe(bitmap -> GlideUtil.loadBmpImage(mQrCode, bitmap, -1));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_title_left:
                finish();
                break;
            case R.id.wallet_address:
            case R.id.tv_copy:
                copyWalletAddress();
                break;
            case R.id.bt_save:
                onWalletSave();
                break;
            default:
                break;
        }
    }

    private void copyWalletAddress() {
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        if (cm != null) {
            ClipData mClipData = ClipData.newPlainText("Label", walletAddress);
            cm.setPrimaryClip(mClipData);
        }
        showToast(getString(R.string.qrcode_copy_success));
    }

    public void onWalletSave() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .compose(RxUtil.ioMain())
                .subscribe(granted -> {
                    if (!granted) {
                        Intent localIntent = new Intent();
                        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        localIntent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        localIntent.setData(Uri.fromParts("package", getPackageName(), null));
                        startActivity(localIntent);
                    }
                    try {
                        mPhotoFile = createImageFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    BitmapUtils.saveBitmap2Jpg(this, BitmapUtils.getBitmapFromView(mQrCode), mPhotoFile);
                    showToast(getString(R.string.saved_successfully));
                });
    }

    private File createImageFile() throws IOException {
        return createImageFile(false);
    }

    private File createImageFile(boolean isTemp) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (!isTemp) {
            storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        }
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        return image;
    }

    @Override
    public void finish() {
        super.finish();
        ViewUtil.left2RightOut(this);
    }
}
