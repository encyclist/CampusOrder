package com.erning.common.activity

import android.app.Activity
import android.content.Intent
import android.graphics.SurfaceTexture
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.TextureView
import cn.simonlee.xcodescanner.core.*
import com.erning.common.R
import com.erning.common.utils.LogUtils
import kotlinx.android.synthetic.main.activity_custom_capture.*
import org.jetbrains.anko.toast

/**
 * 二维码条形码扫面页面，打开即用
 * onActivityResult中的data里result字段即是扫描到的内容
 */
open class CustomCaptureActivity : AppCompatActivity(), TextureView.SurfaceTextureListener, CameraScanner.CameraListener, GraphicDecoder.DecodeListener {
    companion object {
        private const val TAG = "CustomCaptureActivity"
    }

    private lateinit var mCameraScanner: CameraScanner
    private var mGraphicDecoder: GraphicDecoder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_capture)

        mTextureView.surfaceTextureListener = this
        /*
        * 注意，SDK21的设备是可以使用NewCameraScanner的，但是可能存在对新API支持不够的情况，比如红米Note3（双网通Android5.0.2）
        * 开发者可自行配置使用规则，比如针对某设备型号过滤，或者针对某SDK版本过滤
        * */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mCameraScanner = NewCameraScanner(this)
        } else {
            mCameraScanner = OldCameraScanner(this)
        }
    }

    override fun onPause() {
        mCameraScanner.closeCamera()
        super.onPause()
    }

    override fun onRestart() {
        if (mTextureView.isAvailable) {
            //部分机型转到后台不会走onSurfaceTextureDestroyed()，因此isAvailable()一直为true，转到前台后不会再调用onSurfaceTextureAvailable()
            //因此需要手动开启相机
            mCameraScanner.setPreviewTexture(mTextureView.surfaceTexture)
            mCameraScanner.setPreviewSize(mTextureView.width, mTextureView.height)
            mCameraScanner.openCamera(this.applicationContext)
        }
        super.onRestart()
    }

    override fun onDestroy() {
        mCameraScanner.setGraphicDecoder(null);
        if (mGraphicDecoder != null) {
            mGraphicDecoder!!.setDecodeListener(null)
            mGraphicDecoder!!.detach()
        }
        mCameraScanner.detach()
        super.onDestroy()
    }

    override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture?, width: Int, height: Int) {
        LogUtils.e(TAG, ".onSurfaceTextureSizeChanged() width = $width , height = $height")
        // TODO 当View大小发生变化时，要进行调整。
//        mTextureView.setImageFrameMatrix();
//        mCameraScanner.setPreviewSize(width, height);
//        mCameraScanner.setFrameRect(mScannerFrameView.getLeft(), mScannerFrameView.getTop(), mScannerFrameView.getRight(), mScannerFrameView.getBottom());
    }

    // 每有一帧画面，都会回调一次此方法
    override fun onSurfaceTextureUpdated(surface: SurfaceTexture?) {
    }

    override fun onSurfaceTextureDestroyed(surface: SurfaceTexture?): Boolean {
        LogUtils.e(TAG, ".onSurfaceTextureDestroyed()")
        return true
    }

    override fun onSurfaceTextureAvailable(surface: SurfaceTexture?, width: Int, height: Int) {
        mCameraScanner.setPreviewTexture(surface)
        mCameraScanner.setPreviewSize(width, height)
        mCameraScanner.openCamera(this)
    }

    override fun cameraDisconnected() {
        toast("与相机断开连接")
    }

    override fun openCameraError() {
        toast("打开摄像头失败")
    }

    override fun noCameraPermission() {
        toast("没有权限")
    }

    override fun openCameraSuccess(surfaceWidth: Int, surfaceHeight: Int, surfaceDegree: Int) {
        mTextureView.setImageFrameMatrix(surfaceWidth, surfaceHeight, surfaceDegree)
        if (mGraphicDecoder == null) {
            mGraphicDecoder = ZBarDecoder(this)//可使用二参构造方法指定条码识别的类型
        }
        //调用setFrameRect方法会对识别区域进行限制，注意getLeft等获取的是相对于父容器左上角的坐标，实际应传入相对于TextureView左上角的坐标。
        mCameraScanner.setFrameRect(mScannerFrameView.left, mScannerFrameView.top, mScannerFrameView.right, mScannerFrameView.bottom)
        mCameraScanner.setGraphicDecoder(mGraphicDecoder)
    }

    override fun cameraBrightnessChanged(brightness: Int) {
    }

    override fun decodeComplete(result: String?, type: Int, quality: Int, requestCode: Int) {
        LogUtils.d(TAG, "[类型$type/精度$quality]$result")
        if (result != null && result.isNotEmpty()){
            val data = Intent()
            data.putExtra("result",result)
            data.putExtra("type",type)
            data.putExtra("quality",quality)
            setResult(Activity.RESULT_OK,data)
            finish()
        }
    }

}
