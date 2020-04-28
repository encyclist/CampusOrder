package com.erning.common.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.erning.common.R;
import com.erning.common.utils.FileUtil;
import com.erning.common.utils.LogUtils;
import com.erning.common.view.ViewPagerIndicator;
import com.facebook.binaryresource.BinaryResource;
import com.facebook.binaryresource.FileBinaryResource;
import com.facebook.cache.common.CacheKey;
import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.DraweeHolder;
import com.facebook.imagepipeline.cache.DefaultCacheKeyFactory;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.core.ImagePipelineFactory;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.relex.photodraweeview.OnViewTapListener;
import me.relex.photodraweeview.PhotoDraweeView;

/**
 * 看图页面，支持gif，支持手势操作
 * 需要在intent中传入：
 * imgs：图片url数组（String[]类型）
 * index：首先展示第几张图
 * clickFinish：boolean类型 单击是否返回 默认true 可选
 * longClickSave：boolean类型 长按是否保存 默认true 可选
 * text：文字 可选
 */
@SuppressWarnings({"UnusedReturnValue", "unused"})
public class ImageActivity extends AppCompatActivity {
    public static final String TAG = "ImageActivity";

    private LinearLayout linear_image;
    private ViewPager viewPager_image;
    private TextView text_image;
    private String[] imgs;
    private String text;
    private List<View> viewList = new ArrayList<>();
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        viewPager_image = findViewById(R.id.viewPager_image);
        linear_image = findViewById(R.id.linear_image);
        text_image = findViewById(R.id.text_image);

        imgs = getIntent().getStringArrayExtra("imgs");
        boolean clickFinish = getIntent().getBooleanExtra("clickFinish",true);
        boolean clickSave = getIntent().getBooleanExtra("longClickSave",true);
        if (imgs == null){
            imgs = new String[]{getIntent().getStringExtra("url")};
        }
        text = getIntent().getStringExtra("text");
        int index = getIntent().getIntExtra("index", 0);

        if (text==null || text.isEmpty()){
            text_image.setVisibility(View.GONE);
        }else{
            text_image.setText(text);
        }
        for (final String item : imgs){
            View view = getLayoutInflater().inflate(R.layout.item_image,null,false);

            final PhotoDraweeView img_image = view.findViewById(R.id.img_image);

            if (item.endsWith(".gif") || item.endsWith(".GIF")){
                PipelineDraweeControllerBuilder controller = Fresco.newDraweeControllerBuilder();
                controller.setUri(item);//设置图片url
                controller.setAutoPlayAnimations(true);
                controller.setOldController(img_image.getController());
                controller.setControllerListener(new BaseControllerListener<ImageInfo>() {
                    @Override
                    public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                        super.onFinalImageSet(id, imageInfo, animatable);
                        if (imageInfo != null) {
                            img_image.update(imageInfo.getWidth(), imageInfo.getHeight());
                        }
                    }
                });
                img_image.setController(controller.build());
            }else{
                img_image.setPhotoUri(Uri.parse(item));
            }

            //点击返回
            img_image.setOnViewTapListener(new OnViewTapListener() {
                @Override
                public void onViewTap(View view, float x, float y) {
                    if (clickFinish)
                        finish();
                }
            });

            img_image.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (clickSave)
                        saveImage();
                    return true;
                }
            });

            viewList.add(view);
        }
        viewPager_image.setAdapter(new PageAdapter());

        if (imgs.length>1) {
            viewPager_image.addOnPageChangeListener(new ViewPagerIndicator(this, linear_image, imgs.length));
        }else{
            ViewGroup.LayoutParams params = linear_image.getLayoutParams();
            params.height = 0;
            linear_image.setVisibility(View.GONE);
        }
        viewPager_image.setCurrentItem(index);
    }

    class PageAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return imgs.length;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            container.addView(viewList.get(position));
            return viewList.get(position);
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }
    }

    private void saveImage(){
        String url = imgs[viewPager_image.getCurrentItem()];
        path = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
        if(url.toLowerCase().endsWith(".gif")){
            saveGifFromMainFileCache(url,path + "/"+System.currentTimeMillis()+".gif");
        }else{
            downLoadImg(url);
        }
    }

    private boolean saveGifFromMainFileCache(String url, String localSavePath) {
        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url)).setProgressiveRenderingEnabled(true).build();
        CacheKey cacheKey = DefaultCacheKeyFactory.getInstance().getEncodedCacheKey(imageRequest, this);
        if (ImagePipelineFactory.getInstance().getMainFileCache().hasKey(cacheKey)) {
            BinaryResource resource = ImagePipelineFactory.getInstance().getMainFileCache().getResource(cacheKey);
            File cacheFile = ((FileBinaryResource) resource).getFile();
//          ImageFormat imageFormat = ImageFormatChecker.getImageFormat(new FileInputStream(cacheFile));
            Toast.makeText(getApplicationContext(), "已保存：" + localSavePath, Toast.LENGTH_LONG).show();
            return FileUtil.copyFile(cacheFile, localSavePath);//把缓存文件复制到要保存的位置上  返回保存是否成功
        } else {
            //保存失败处理
            Toast.makeText(getApplicationContext(), "保存失败", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    private void downLoadImg(final String uri) {
        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(uri)).setProgressiveRenderingEnabled(true).build();
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        DataSource<CloseableReference<CloseableImage>> dataSource = imagePipeline.fetchDecodedImage(imageRequest, this);
        dataSource.subscribe(new BaseBitmapDataSubscriber() {
            @Override
            public void onNewResultImpl(@Nullable Bitmap bitmap) {
                //bitmap即为下载所得图片
                String hzm = ".png";
                String[] hzms = uri.split("\\.");
                if (hzms.length > 0){
                    hzm = "." + hzms[hzms.length - 1];
                }
                String path2 = path + "/"+System.currentTimeMillis()+hzm;
                FileUtil.saveBitmap(bitmap,path2);
                //toast必须在主线程
                runOnUiThread(() -> Toast.makeText(getApplicationContext(), "已保存：" + path2, Toast.LENGTH_LONG).show());
            }

            @Override
            public void onFailureImpl(DataSource dataSource) {

            }
        }, CallerThreadExecutor.getInstance());

        // 忘了下面是啥了……
        GenericDraweeHierarchy hierarchy = new GenericDraweeHierarchyBuilder(getResources())
                .setFadeDuration(300)
//                .setPlaceholderImage(defaultDrawable)
//                .setFailureImage(defaultDrawable)
                .setProgressBarImage(new ProgressBarDrawable())
                .build();
        DraweeHolder<GenericDraweeHierarchy> draweeHolder = DraweeHolder.create(hierarchy, this);

        PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                .setOldController(draweeHolder.getController())
                .setImageRequest(imageRequest)
                .build();
        controller.onClick();
    }

    @Override
    protected void onDestroy() {
        try{
            if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                //SD卡已装入
                fileScan(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath());
            }
        }catch (Exception e){
            LogUtils.e(TAG,"没有权限");
        }
        super.onDestroy();
    }

    /**
     * 通知手机扫描媒体文件
     * @param file 要扫描的文件夹
     */
    public void fileScan(String file){
        Uri data = Uri.parse("file://" + file);
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, data));
    }
}
