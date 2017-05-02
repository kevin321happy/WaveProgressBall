package com.fips.huashun.ui.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.fips.huashun.ApplicationEx;
import com.fips.huashun.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by zhangxg on 2016/1/27.
 */
public class AvatarUtil {

    /**
     *  头像文件
     */
    public static final String AVATAR_IMAGE_FILE_NAME_SUFFIX = "_avatar.jpg";


    /**
     * 设置头像.
     * @param imageView
     * @return
     */
    public static void setAvatar(final ImageView imageView , String imagePreUrl, String avatarUrl) {
        if (TextUtils.isEmpty(avatarUrl) || avatarUrl.equals("null")
                || TextUtils.isEmpty(imagePreUrl)) {

            //暂不使用本地图片
            //setLocalAvatar(imageView);
            imageView.setImageDrawable(ApplicationEx.getInstance()
                    .getResources().getDrawable(R.mipmap.android_template));
            return;
        }

        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisk(true).considerExifParams(true).bitmapConfig(Bitmap.Config.RGB_565).build();
        ImageLoader.getInstance().displayImage(imagePreUrl + avatarUrl, imageView, options, new SimpleImageLoadingListener() {

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

                imageView.setImageBitmap(loadedImage);
                //saveBitmap(loadedImage);不使用本地缓存所以不需要缓存到本地
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                //暂不使用本地图片
                //setLocalAvatar(imageView);
                imageView.setImageDrawable(ApplicationEx.getInstance()
                        .getResources().getDrawable(R.mipmap.android_template));
            }
        });
    }

    /**
     * 本地头像文件是否存在
     * @param context
     * @return
     */
    private static boolean localAvatarExists(Context context) {

        try {

            File avatarFile = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                    getAvatarImageFileName());
            if(!avatarFile.exists()) {

                return false;
            }
        }
        catch (Exception e) {

            return false;
        }
        return true;
    }

    /**
     * 本地头像文件存在时设置头像为本地文件
     * @param imageView
     */
    private static void setLocalAvatar(ImageView imageView) {

        if (!localAvatarExists(ApplicationEx.getInstance())) {

            return;
        }

        try {

            Bitmap bitmap = BitmapFactory.decodeFile(new File(imageView.getContext()
                    .getExternalFilesDir(Environment.DIRECTORY_PICTURES), getAvatarImageFileName()).getAbsolutePath());

            imageView.setImageBitmap(bitmap);
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public static byte[] bitmapToBytes(Bitmap bitmap) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static Bitmap bytesToBimap(byte[] bytes) {

        if (bytes.length != 0) {

            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        } else {

            return null;
        }
    }

    /**
     * 从服务器获取头像文件后保存到本地
     * @param bitmap
     */
    public static void saveBitmap(Bitmap bitmap) {

        File file = new File(ApplicationEx.getInstance()
                .getExternalFilesDir(Environment.DIRECTORY_PICTURES), getAvatarImageFileName());
        if (file.exists()) {

            file.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static String getAvatarImageFileName() {

        return System.currentTimeMillis() + AVATAR_IMAGE_FILE_NAME_SUFFIX;
    }

    public static String getAvatarTmpImageFileName() {

        return System.currentTimeMillis()+ "_tmp" + AVATAR_IMAGE_FILE_NAME_SUFFIX;
    }


}
