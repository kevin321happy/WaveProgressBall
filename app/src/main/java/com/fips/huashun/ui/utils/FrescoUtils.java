package com.fips.huashun.ui.utils;

import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.view.ViewGroup;
import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.postprocessors.IterativeBoxBlurPostProcessor;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.fips.huashun.ApplicationEx;

/**
 * Created by kevin on 2017/3/15. 邮箱：kevin321vip@126.com
 * 公司：锦绣氘(武汉科技有限公司)
 * FrescoUtils图片加载工具
 */

public  class FrescoUtils {

  /**
   * 设置view大小。
   *
   * @param view View。
   * @param width 指定宽。
   * @param width 指定高。
   */
  public static void requestLayout(View view, int width, int height) {
    ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
    if (layoutParams == null) {
      layoutParams = new ViewGroup.LayoutParams(width, height);
      view.setLayoutParams(layoutParams);
    } else {
      view.getLayoutParams().width = width;
      view.getLayoutParams().height = height;
      view.requestLayout();
    }
  }

  /**
   * 显示http或者https远程图片。
   * @param draweeView imageView。
   * @param url 连接地址。
   */
  public static void showUrl(SimpleDraweeView draweeView, String url) {
    try {
      draweeView.setImageURI(Uri.parse(url));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  /**
   * 显示一个本地图片。
   * @param draweeView imageView。
   * @param path       路径。
   * @param width      实际宽。
   * @param height     实际高度。
   */
  public static void showFile(SimpleDraweeView draweeView, String path, int width, int height) {
    try {
      Uri uri = Uri.parse("file://" + path);
      ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
          .setResizeOptions(new ResizeOptions(width, height))
          .build();
      AbstractDraweeController controller = Fresco.newDraweeControllerBuilder()
          .setOldController(draweeView.getController())
          .setImageRequest(request)
          .build();
      draweeView.setController(controller);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  /**
   * 显示本地图片。
   * @param draweeView imageView。
   * @param path       路径。
   */
  public static void showFile(SimpleDraweeView draweeView, String path) {
    try {
      Uri uri = Uri.parse("file://" + path);
      draweeView.setImageURI(uri);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  /**
   * 显示一个Res中的图片。
   *
   * @param draweeView ImageView。
   * @param resId      资源ID。
   */
  public static void showRes(SimpleDraweeView draweeView, @DrawableRes int resId) {
    try {
      // 你没看错，这里是三个///。
      draweeView.setImageURI(Uri.parse("res:///" + resId));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  /**
   * 显示content provider图片。
   *
   * @param draweeView image view。
   * @param path       路径。
   */
  public static void showContentProvider(SimpleDraweeView draweeView, String path) {
    try {
      draweeView.setImageURI(Uri.parse("content://" + path));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  /**
   * 显示Assets中的图片。
   *
   * @param draweeView ImageView.
   * @param path       路径。
   */
  public static void showAsset(SimpleDraweeView draweeView, String path) {
    try {
      draweeView.setImageURI(Uri.parse("asset://" + path));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  /**
   * 以高斯模糊显示。
   *
   * @param draweeView View。
   * @param url        url.
   * @param iterations 迭代次数，越大越魔化。
   * @param blurRadius 模糊图半径，必须大于0，越大越模糊。
   */
  public static void showUrlBlur(SimpleDraweeView draweeView, String url, int iterations, int blurRadius) {
    try {
      Uri uri = Uri.parse(url);
      ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
          .setPostprocessor(new IterativeBoxBlurPostProcessor(iterations, blurRadius))
          .build();
      AbstractDraweeController controller = Fresco.newDraweeControllerBuilder()
          .setOldController(draweeView.getController())
          .setImageRequest(request)
          .build();
      draweeView.setController(controller);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  /**
   * 加载图片成bitmap。
   *
   * @param imageUrl 图片地址。
   */
  public static void loadToBitmap(String imageUrl, BaseBitmapDataSubscriber mDataSubscriber) {
    ImageRequest imageRequest = ImageRequestBuilder
        .newBuilderWithSource(Uri.parse(imageUrl))
        .setProgressiveRenderingEnabled(true)
        .build();

    ImagePipeline imagePipeline = Fresco.getImagePipeline();
    DataSource<CloseableReference<CloseableImage>> dataSource = imagePipeline.fetchDecodedImage
        (imageRequest, ApplicationEx.get());
    dataSource.subscribe(mDataSubscriber, CallerThreadExecutor.getInstance());
  }
}
