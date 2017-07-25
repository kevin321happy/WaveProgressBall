package com.example.kevin321vip.waveprogressball.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.kevin321vip.waveprogressball.R;

/**
 * Created by kevin321vip on 2017/7/23.
 * 自定义的球型进度加载控件
 */

public class WaveProgressBall extends View {

    private static final int DRAWING = 0X777;
    private Bitmap mBackGroundBitmap;
    private Paint mWavePaint;
    private Paint mTextPaint;
    private String mWaveColor = "#00C799";
    private String mWaveColorEnd = "#EEAF55";
    private String mTextColor = "#ffffff";
    private float mWaveY;//上一次波浪中线的Y轴坐标
    private float mWaveMidY;//当前的Y轴坐标

    private float mWaveHeight = 20f;//波浪的高度
    private float mWaveHalfWidth = 80f;//波浪的宽度

    private int mMaxProgress = 100;
    private int mCurrentProgress = 0;
    private float mDistance = 0;//ｘ轴的偏移量，为了使水波动起来，每次绘制时都要向左进行一段偏移。

    private int mWidth;
    private int mHeight;
    private float mTexrSize = 16;
    private Path mWavepath;
    private float mWaveSpeed = 50;
    private String mTextContent = "0";//文字的内容
    private boolean mAllowProgressInBothDirections = false;

    private Paint mCirclepaint;
    private int[] mColors1;
    private int[] mColors2;
    private RectF mOval;

    private long mRefreshGap = 10;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case DRAWING:
                    invalidate();
                    sendEmptyMessageDelayed(DRAWING, mRefreshGap);
            }
        }
    };


    public WaveProgressBall(Context context) {
        this(context, null);
    }

    public WaveProgressBall(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public WaveProgressBall(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        //先获取背景
        if (null == getBackground()) {
            throw new IllegalArgumentException(String.format("background is null."));
        } else {
            mBackGroundBitmap = getBitmapFromDrawable(getBackground());
        }
        // 如果手机版本在4.0以上,则开启硬件加速
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }

        mColors1 = new int[]{Color.parseColor(mWaveColorEnd), Color.parseColor("#FF95C700"), Color.parseColor("#FF8FC700"), Color.parseColor("#FF00C717"),
                Color.parseColor("#FF00C75D"), Color.parseColor(mWaveColor)};
        mColors2 = new int[]{Color.parseColor(mWaveColor), Color.parseColor("#FF00C75D"), Color.parseColor("#FF00C717"), Color.parseColor("#FF8FC700"),
                Color.parseColor("#FF95C700"), Color.parseColor(mWaveColorEnd)};
        mWavepath = new Path();
        //波浪的画笔
        mWavePaint = new Paint();
        mWavePaint.setAntiAlias(true);
        mWavePaint.setStyle(Paint.Style.FILL);

        //设置颜色渐变的效果
        Shader shader = new LinearGradient(100, 100, 100, 300, mColors1, null, Shader.TileMode.CLAMP);
        mWavePaint.setShader(shader);
        mWavePaint.setColor(Color.parseColor(mWaveColor));
        //文字进度的画笔
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        //绘制外层圆环的画笔
        mCirclepaint = new Paint();
        mCirclepaint.setAntiAlias(true);
        mCirclepaint.setStrokeWidth(20);
        mCirclepaint.setStyle(Paint.Style.STROKE);
        //handle发送消息执行重绘的动作
        mHandler.sendEmptyMessageDelayed(DRAWING, 100);
    }

    /**
     * 测量的方法
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mWaveY = mHeight = MeasureSpec.getSize(heightMeasureSpec);
        //RectF对象绘制圆弧时用到
        mOval = new RectF();
        mOval.left = 0 + 10;                              //左边
        mOval.top = 0 + 10;                                   //上边
        mOval.right = mWidth - 10;                             //右边
        mOval.bottom = mHeight - 10;
    }

    /**
     * 绘制的方法
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        if (mBackGroundBitmap != null) {
            canvas.drawBitmap(creatIma(), 0, 0, null);
        }
    }

    /**
     * 将波浪、背景和文字华仔画布上面
     */

    private Bitmap creatIma() {

        mTextPaint.setColor(Color.parseColor(mTextColor));
        mTextPaint.setTextSize(mTexrSize);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap bitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
        /**
         * 得到一个同样大小的画布
         */
        Canvas canvas = new Canvas(bitmap);


        /**
         * 绘制波浪
         */
        mWaveMidY = mHeight * (mMaxProgress - mCurrentProgress) / mMaxProgress;
        if (mAllowProgressInBothDirections || mWaveY > mWaveMidY) {
            mWaveY = mWaveY - (mWaveY - mWaveMidY) / 10;
        }
        //水波纹不停的波动
        mWavepath.reset();
        mWavepath.moveTo(0 - mDistance, mWaveMidY);
        //波浪的数量
        int waveNum = mWidth / ((int) mWaveHalfWidth * 2) + 1;
        // mPath.quadTo(x1, y1, x2, y2) (x1,y1) 为控制点，(x2,y2)为结束点。
        int multiplier = 0;
        for (int i = 0; i < waveNum; i++) {
            mWavepath.quadTo(mWaveHalfWidth * (multiplier + 1) - mDistance, mWaveY - mWaveHeight, mWaveHalfWidth * (multiplier + 2) - mDistance, mWaveY);
            mWavepath.quadTo(mWaveHalfWidth * (multiplier + 3) - mDistance, mWaveY - mWaveHeight, mWaveHalfWidth * (multiplier + 4) - mDistance, mWaveY);
            multiplier += 4;
        }
        mDistance += mWaveHalfWidth / mWaveSpeed;
        mDistance = mDistance % (mWaveHalfWidth * 4);

        mWavepath.lineTo(mWidth, mHeight);
        mWavepath.lineTo(0, mHeight);
        mWavepath.close();
        canvas.drawPath(mWavepath, mWavePaint);
        /**
         *给图片进行缩放
         */
        int min = Math.min(mWidth, mHeight);
        Bitmap.createScaledBitmap(mBackGroundBitmap, min, min, false);

        /**
         * 使用DST_ATOP，取上层非交集部分与下层交集部分 。
         */
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP));

        /**
         * 绘制图片
         */
        canvas.drawBitmap(mBackGroundBitmap, 0, 0, paint);
        canvas.drawText(mTextContent, mWidth / 2, mHeight / 2, mTextPaint);
        /**
         * 绘制外环
         */
        //  扫描渐变效果
        Shader shader = new SweepGradient(mWidth / 2, mHeight / 2, mColors2, null);
        mCirclepaint.setShader(shader);
//        canvas.drawCircle(mWidth / 2, mHeight / 2,mWidth/2-10,mCirclepaint);
        //绘制圆弧
        float sweepAngle = (float) (mCurrentProgress * 360.0 / 100);
        canvas.drawArc(mOval, 0, sweepAngle, false, mCirclepaint);    //绘制圆弧
//        Bitmap resource = BitmapFactory.decodeResource(getResources(), R.drawable.bg_out_ring);
//        BitmapShader bitmapShader = new BitmapShader(resource, Shader.TileMode.MIRROR, Shader.TileMode.MIRROR);
//        mCirclepaint.setShader(bitmapShader);
        return bitmap;
    }

    //从图片中获取背景
    private Bitmap getBitmapFromDrawable(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        try {
            Bitmap bitmap;
            //创建位图
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;

        } catch (Exception e) {
            return null;
        }
    }
//对外提供的设置方法

    /**
     * 波浪的颜色
     *
     * @param waveColor
     */
    public void setWaveColor(String waveColor) {
        mWaveColor = waveColor;
    }

    /**
     * 字体的颜色
     *
     * @param textColor
     */
    public void setTextColor(String textColor) {
        mTextColor = textColor;
    }

    /**
     * 字体的大小
     *
     * @param texrSize
     */
    public void setTexrSize(float texrSize) {
        mTexrSize = texrSize;
    }

    /**
     * 波浪的速度
     *
     * @param waveSpeed
     */
    public void setWaveSpeed(float waveSpeed) {
        mWaveSpeed = waveSpeed;
    }

    /**
     * 当前显示的文字
     *
     * @param textContent
     */
    public void setTextContent(String textContent) {
        mTextContent = textContent;
    }

    /**
     * 设置当前的状态
     *
     * @param currentProgress
     * @param currentText
     */
    public void setCurrent(int currentProgress, String currentText) {
        this.mCurrentProgress = currentProgress;
        this.mTextContent = currentText;
        invalidate();
    }

    /**
     * 设置波浪的宽高
     *
     * @param mWaveHight
     * @param mWaveWidth
     */
    public void setWave(float mWaveHight, float mWaveWidth) {
        this.mWaveHeight = mWaveHight;
        this.mWaveHalfWidth = mWaveWidth / 2;
    }

    /**
     * 设置最大值
     *
     * @param maxProgress
     */
    public void setMaxProgress(int maxProgress) {
        this.mMaxProgress = maxProgress;
    }

}
