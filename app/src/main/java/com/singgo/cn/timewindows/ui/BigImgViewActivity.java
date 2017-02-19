package com.singgo.cn.timewindows.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.os.EnvironmentCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.singgo.cn.timewindows.R;
import com.singgo.cn.timewindows.app.AppContext;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by hxz on 2017/1/8.
 */

public class BigImgViewActivity extends FragmentActivity implements PhotoViewAttacher.OnPhotoTapListener,ViewPager.OnPageChangeListener {
    // 保存图片
    private TextView tv_save_big_image;
    // 接收传过来的uri地址
    List<String> imageuri;
    // 接收穿过来当前选择的图片的数量
    int code;
    // 用于判断是头像还是文章图片 1:头像 2：文章大图
    int selet;

    // 用于管理图片的滑动
    ViewPager very_image_viewpager;
    // 当前页数
    private int page;

    /**
     * 显示当前图片的页数
     */
    TextView very_image_viewpager_text;
    /**
     * 用于判断是否是加载本地图片
     */
    private boolean isLocal;

    ViewPageApapter adapter;

    /**
     * 本应用图片的id
     */
    private int imageId;
    /**
     * 是否是本应用中的图片
     */
    private boolean isApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img_view_page);
        initView();
    }

    /**
     * 初始化
     */
    private void initView() {
        very_image_viewpager_text = (TextView) findViewById(R.id.very_image_viewpager_text);
        tv_save_big_image = (TextView) findViewById(R.id.tv_save_big_image);
        very_image_viewpager = (ViewPager) findViewById(R.id.very_image_viewpager);

        tv_save_big_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppContext.showToast(BigImgViewActivity.this,"开始下载图片");
                if(isApp){
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(),imageId);
                    if(bitmap!=null){
                        saveImageToGallery(BigImgViewActivity.this, bitmap);
                        AppContext.showToast(BigImgViewActivity.this,"保存成功");
                    }
                }else{
                    final BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            // 子线程获得图片路径
                            final String imagePath = getImagePath(imageuri.get(page));
                            BigImgViewActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (imagePath != null) {
                                        Bitmap bitmap = BitmapFactory.decodeFile(imagePath, bmOptions);
                                        if (bitmap != null) {
                                            saveImageToGallery(BigImgViewActivity.this, bitmap);
                                            AppContext.showToast(BigImgViewActivity.this,"已保存至"+Environment.getExternalStorageDirectory().getAbsolutePath()+"/myApp相册");
//                                            Toast.makeText(ViewBigImageActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            });
                        }
                    }).start();
                }
            }
        });


        Bundle bundle = getIntent().getExtras();
        code = bundle.getInt("code");
        selet = bundle.getInt("selet");
        isLocal = bundle.getBoolean("isLocal", false);
        imageuri = bundle.getStringArrayList("imageuri");
        /**是否是本应用中的图片*/
        isApp = bundle.getBoolean("isApp", false);
        /**本应用图片的id*/
        imageId = bundle.getInt("id", 0);
        /**
         * 设置适配器
         */
        if(isApp){
            MyPageAdapter myAdapter = new MyPageAdapter();
            very_image_viewpager.setAdapter(myAdapter);
            very_image_viewpager.setEnabled(false);
        }else{
            adapter = new ViewPageApapter();
            very_image_viewpager.setAdapter(adapter);
            very_image_viewpager.setCurrentItem(code);
            page = code;
            very_image_viewpager.setOnPageChangeListener(this);
            very_image_viewpager.setEnabled(false);
            // 设定当前的页数和总页数
            if (selet == 2) {
                very_image_viewpager_text.setText((code + 1) + " / " + imageuri.size());
            }
        }
    }
    /**
     * Glide 获得图片缓存路径
     */
    private String getImagePath(String imgUrl) {
        String path = null;
        FutureTarget<File> future = Glide.with(BigImgViewActivity.this)
                .load(imgUrl)
                .downloadOnly(500, 500);
        try {
            File cacheFile = future.get();
            path = cacheFile.getAbsolutePath();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return path;
    }

    /**
     * 保存图片到相册
     * @param
     * @param bitmap
     */
    private void saveImageToGallery(Context context, Bitmap bitmap) {
        File appDir = new File(Environment.getExternalStorageDirectory(),"myApp的相册");
        if(!appDir.exists()){
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getAbsoluteFile())));

    }


    class MyPageAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = getLayoutInflater().inflate(R.layout.viewpager_very_image,container,false);
            PhotoView photoView = (PhotoView) view.findViewById(R.id.zoom_image_view);
            ProgressBar spinner = (ProgressBar) view.findViewById(R.id.loading);
            spinner.setVisibility(View.GONE);
            if(imageId == 0){
                photoView.setImageResource(imageId);
            }
            photoView.setOnPhotoTapListener(BigImgViewActivity.this);
            container.addView(view, 0);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View)object;
            container.removeView(view);
        }
    }

    class ViewPageApapter extends PagerAdapter{
        LayoutInflater inflater;
        ViewPageApapter(){
            inflater = getLayoutInflater();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = inflater.inflate(R.layout.viewpager_very_image, container, false);
            final PhotoView zoom_image_view = (PhotoView) view.findViewById(R.id.zoom_image_view);
            final ProgressBar spinner = (ProgressBar) view.findViewById(R.id.loading);
            // 保存网络图片的路径
            String adapter_image_Entity = (String) getItem(position);
            String imageUrl;
            if(isLocal){
                imageUrl = "file://" + adapter_image_Entity;
                tv_save_big_image.setVisibility(View.GONE);
            }else{
                imageUrl = adapter_image_Entity;
            }
            spinner.setVisibility(View.VISIBLE);
            spinner.setClickable(false);
            Glide.with(BigImgViewActivity.this).load(imageUrl).crossFade(700)
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            AppContext.showToast(BigImgViewActivity.this,"资源加载异常");
                            spinner.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            spinner.setVisibility(View.GONE);

                            /**这里应该是加载成功后图片的高*/
                            int height = zoom_image_view.getHeight();

                            int wHeight = getWindowManager().getDefaultDisplay().getHeight();
                            if (height > wHeight) {
                                zoom_image_view.setScaleType(ImageView.ScaleType.CENTER_CROP);
                            } else {
                                zoom_image_view.setScaleType(ImageView.ScaleType.FIT_CENTER);
                            }
                            return false;
                        }
                    }).into(zoom_image_view);
            zoom_image_view.setOnPhotoTapListener(BigImgViewActivity.this);
            container.addView(view, 0);
            return view;
        }

        @Override
        public int getCount() {
            if(imageuri == null || imageuri.size() ==0){
                return 0;
            }
            return imageuri.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }

        Object getItem(int position) {
            return imageuri.get(position);
        }
    }

    @Override
    public void onPhotoTap(View view, float x, float y) {
        finish();
    }

    @Override
    public void onOutsidePhotoTap() {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        // 每当页数发生改变时重新设定一遍当前的页数和总页数
        very_image_viewpager_text.setText((position + 1) + " / " + imageuri.size());
        page = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}
