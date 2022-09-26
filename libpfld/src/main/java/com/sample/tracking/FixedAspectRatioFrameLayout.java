package com.sample.tracking;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Region;
import android.util.AttributeSet;
import android.widget.FrameLayout;


public class FixedAspectRatioFrameLayout extends FrameLayout {
    private int mAspectRatioWidth = 480 / 2;
    private int mAspectRatioHeight = 640 / 2;

    private Path path = new Path();

    public FixedAspectRatioFrameLayout(Context context) {
        super(context);
    }

    public FixedAspectRatioFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FixedAspectRatioFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int originalWidth = MeasureSpec.getSize(widthMeasureSpec);
        int originalHeight = MeasureSpec.getSize(heightMeasureSpec);

        int calculatedHeight = originalWidth * mAspectRatioHeight / mAspectRatioWidth;
        int finalWidth, finalHeight;

        if (calculatedHeight > originalHeight) {
            finalWidth = originalHeight * mAspectRatioWidth / mAspectRatioHeight;
            finalHeight = originalHeight;
        } else {
            finalWidth = originalWidth;
            finalHeight = calculatedHeight;
        }

        super.onMeasure(
                MeasureSpec.makeMeasureSpec(finalWidth, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(finalHeight, MeasureSpec.EXACTLY));
    }

//    @Override
//    protected void onDraw(Canvas canvas) {
//        //设置裁剪的圆心，半径
//        path.addCircle(getHeight() / 2, getHeight() / 2, getHeight() / 2, Path.Direction.CCW);
//        //裁剪画布，并设置其填充方式
//        canvas.clipPath(path, Region.Op.INTERSECT);
//        super.draw(canvas);
//    }
}
