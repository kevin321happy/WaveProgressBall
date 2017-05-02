package com.fips.huashun.widgets;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * description: 自定义虚线 autour: Kevin company:锦绣氘(武汉)科技有限公司 date: 2017/4/21 14:43 update: 2017/4/21
 * version: 1.21 站在峰顶 看世界 落在谷底 思人生
 */
public class CustomDashedLineView extends View {
  public CustomDashedLineView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }
  //绘制虚线
  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    paint.setStyle(Paint.Style.STROKE);
    paint.setColor(Color.DKGRAY);
    Path path = new Path();
    path.moveTo(0, 5);
    path.lineTo(this.getWidth(), 5);
    DashPathEffect pathEffect = new DashPathEffect(new float[]{8,8}, 1);
    paint.setPathEffect(pathEffect);
    canvas.drawPath(path, paint);
  }
}
