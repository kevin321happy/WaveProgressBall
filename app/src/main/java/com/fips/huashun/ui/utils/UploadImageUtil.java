package com.fips.huashun.ui.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.fips.huashun.Config;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Created by Administrator on 2016/4/15.
 */
public class UploadImageUtil {
    //相机
    public static final int GET_IMAGE_VIA_CAMERA = 5;
    //相册
    public static final int GET_IMAGE_VIA_LOCAL = 7;
    // 裁剪图片
    private static final int IMAGE_CROP_CAPTURE = 6;
    //拍照
    public static void getPictureFromCamera(Context context, String fileName) {
        // 先验证手机是否有sdcard
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            try {
                File dir = new File(Config.TEMP_SDCARD_DIR);
                if (!dir.exists())
                    dir.mkdirs();

                Intent intent = new Intent(
                        MediaStore.ACTION_IMAGE_CAPTURE);
                File f = new File(dir, fileName);// localTempImgDir和localTempImageFileName是自己定义的名字
                Uri u = Uri.fromFile(f);
                intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, u);
                ((Activity) context).startActivityForResult(intent,
                        GET_IMAGE_VIA_CAMERA);
            } catch (ActivityNotFoundException e) {
                // TODO Auto-generated catch block
                Toast.makeText(context, "没有找到储存目录", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(context, "没有储存", Toast.LENGTH_LONG).show();
        }
    }

    public static void getPictureFormLocal(Context context) {

        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        ((Activity) context).startActivityForResult(
                intent,GET_IMAGE_VIA_LOCAL);


    }

    /**
     * 创建目录
     *
     * @param path
     */
    public static void setMkdir(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
            Log.e("file", "目录不存在  创建目录    ");
        } else {
            Log.e("file", "目录存在");
        }
    }
    public static String getImageTmpDir() {
        return Environment.getExternalStorageDirectory().getPath() + "/tmp/";
    }
    public static Bitmap getimage(String srcPath, float hi,float wh) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath,newOpts);//此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = hi;//这里设置高度为800f
        float ww = wh;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return compressImage(bitmap);//压缩好比例大小后再进行质量压缩
    }

    public static Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) {
            // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();// 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;// 每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        return bitmap;
    }
}
