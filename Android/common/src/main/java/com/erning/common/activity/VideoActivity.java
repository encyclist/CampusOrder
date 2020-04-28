package com.erning.common.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.erning.common.App;
import com.erning.common.R;

import cn.jzvd.JZDataSource;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

/**
 * 看视频页面
 * 需要在intent中传入：
 * url：视频url
 * thumb：缩略图，用于播放前加载时显示（可选）
 * title：标题（可选）
 */
public class VideoActivity extends AppCompatActivity {
    private JzvdStd jzVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        boolean looping = getIntent().getBooleanExtra("looping",false);
        String url = getIntent().getStringExtra("url");
        String thumb = getIntent().getStringExtra("thumb");
        String title = getIntent().getStringExtra("title");
        if (title == null)
            title = "";
        if (thumb == null)
            thumb = "";

        jzVideo= findViewById(R.id.jz_video);
        Jzvd.WIFI_TIP_DIALOG_SHOWED=true;
        String proxyUrl = App.getProxy().getProxyUrl(url);
        JZDataSource dataSource = new JZDataSource(proxyUrl,title);
        dataSource.looping = looping;
        jzVideo.setUp(dataSource,Jzvd.SCREEN_NORMAL);

        jzVideo.thumbImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        Glide.with(this).load(thumb).into(jzVideo.thumbImageView);

        jzVideo.startVideo();
    }

    @Override
    protected void onResume() {
        super.onResume();

        JzvdStd.goOnPlayOnResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JzvdStd.goOnPlayOnPause();
    }

    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }
}
