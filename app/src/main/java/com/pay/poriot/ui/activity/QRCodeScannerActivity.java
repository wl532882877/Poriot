package com.pay.poriot.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.Settings;
import android.view.View;
import com.pay.poriot.R;
import com.pay.poriot.base.BaseActivity;
import com.pay.poriot.base.IPresenter;
import com.pay.poriot.util.BitmapUtils;
import com.pay.poriot.util.RxUtil;
import com.pay.poriot.util.ViewUtil;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.qrcode.core.BarcodeType;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

/**
 * 二维码扫描
 */
public class QRCodeScannerActivity extends BaseActivity implements QRCodeView.Delegate,View.OnClickListener{
    @BindView(R.id.zxingview)
    ZXingView mZXingView;
    @Override
    protected IPresenter getPresenter() {
        return null;
    }

    public static void startActivity(Context activity) {
        Intent intent = new Intent(activity, QRCodeScannerActivity.class);
        ViewUtil.startActivity(activity, intent);
        ViewUtil.right2LeftIn(activity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_scanner);
        initVariable();
        initViews();
    }

    private void initVariable() {
        ButterKnife.bind(this);
    }

    private void initViews() {
        mZXingView.setDelegate(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                finish();
                break;
            case R.id.iv_flash_light:
                mZXingView.openFlashlight();
                break;
        }
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        vibrate();
        Intent intent = new Intent();
        intent.putExtra("scan_result", result);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onCameraAmbientBrightnessChanged(boolean isDark) {
        String tipText = mZXingView.getScanBoxView().getTipText();
        String ambientBrightnessTip = getString(R.string.turn_on_flash);
        if (isDark) {
            if (!tipText.contains(ambientBrightnessTip)) {
                mZXingView.getScanBoxView().setTipText(tipText + ambientBrightnessTip);
            }
        } else {
            if (tipText.contains(ambientBrightnessTip)) {
                tipText = tipText.substring(0, tipText.indexOf(ambientBrightnessTip));
                mZXingView.getScanBoxView().setTipText(tipText);
            }
        }
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        showToast(getString(R.string.error_opening_camera));
    }

    @Override
    protected void onStart() {
        super.onStart();
        requestCodeQRCodePermissions();
        mZXingView.startCamera();
        mZXingView.changeToScanQRCodeStyle();
        mZXingView.setType(BarcodeType.ONLY_QR_CODE, null); // 只识别 QR_CODE
        mZXingView.startSpotAndShowRect();   // 显示扫描框，并开始识别
    }

    private void requestCodeQRCodePermissions() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
                .compose(RxUtil.ioMain())
                .subscribe(granted -> {
                    if (!granted) {
                        Intent localIntent = new Intent();
                        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        localIntent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        localIntent.setData(Uri.fromParts("package", getPackageName(), null));
                        startActivity(localIntent);
                    }
                    mZXingView.startCamera();
                    mZXingView.startSpotAndShowRect();   // 显示扫描框，并开始识别
                });
    }

    @Override
    protected void onStop() {
        mZXingView.stopCamera(); // 关闭摄像头预览，并且隐藏扫描框
        super.onStop();
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    protected void onDestroy() {
        mZXingView.onDestroy(); // 销毁二维码扫描控件
        super.onDestroy();
    }
}
