package com.sample.tracking;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Region;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceView;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;

public class RoundSurfaceView extends SurfaceView {
    private int width;
    private int height;

    private Path path = new Path();

    //构造函数部分：此处我们使用默认的就行了，不用作修改
    public RoundSurfaceView(Context context) {
        super(context);
    }
    public RoundSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public RoundSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public RoundSurfaceView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }



//    //重写 onMeasure()，用来指定view的大小
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        width = getScreenWidth(getContext());
//        height = width;
//
//        setMeasuredDimension((int) width, (int) height);
//    }
//
//    //获取屏幕的screen宽度
//    private int getScreenWidth(Context context) {
//        WindowManager windowManager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
//        DisplayMetrics outMetrics = new DisplayMetrics();// 创建了一张白纸
//        windowManager.getDefaultDisplay().getMetrics(outMetrics);// 给白纸设置宽高
//        return outMetrics.widthPixels;
//    }
//
//    //重写 draw()，在此处画个圆，注意得在 xml文件中指定相应的 background
//    @Override
//    public void draw(Canvas canvas) {
//        //主要实现 draw方法
//        path.addCircle(width / 2F, height / 2F, width / 2.5F, Path.Direction.CCW);
//        //设置裁剪的圆心，半径
//
//        //裁剪画布，并设置其填充方式
//        if (Build.VERSION.SDK_INT >= 26) {
//            canvas.clipPath(path);
//        } else {
//            canvas.clipPath(path, Region.Op.REPLACE);
//        }
//
//        super.draw(canvas);
//    }
}