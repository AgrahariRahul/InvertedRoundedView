package rahul.agrahari.invertedroundedview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Xfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by rahul agrahari on 5/17/2017.
 */
public class InvertedLinearLayout extends LinearLayout {
    private int strokewidth = 20;
    private int strokeColor = 0x000000;
    private int roundRadious = 50;
    private int background;
    int viewWidth;
    int viewHeight;

    public InvertedLinearLayout(Context context) {
        super(context);
    }

    public InvertedLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        getAttribute(context, attrs);
    }

    public InvertedLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttribute(context, attrs);
    }

    private void getAttribute(Context context, AttributeSet attributeSet) {
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.InvertedRoundedView);
        strokeColor = typedArray.getColor(R.styleable.InvertedRoundedView_strokeColor, strokeColor);
        strokewidth = typedArray.getDimensionPixelSize(R.styleable.InvertedRoundedView_strokeWidth, strokewidth);
        roundRadious = typedArray.getDimensionPixelSize(R.styleable.InvertedRoundedView_radious, roundRadious);
        background = typedArray.getResourceId(R.styleable.InvertedRoundedView_backGround, 0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewWidth = getMeasuredWidth();
        viewHeight = getMeasuredHeight();
    }


    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        canvas.drawColor(Color.TRANSPARENT);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStrokeCap(Paint.Cap.SQUARE);      // set the paint cap to round too
        paint.setStrokeWidth(strokewidth);
        paint.setShader(new Shader());
        paint.setPathEffect(new PathEffect());
        paint.setColor(strokeColor);
        if (viewWidth > 2 * roundRadious && viewHeight > 2 * roundRadious) {
            Path path = new Path();
            path.moveTo(roundRadious + getPaddingLeft(), getPaddingTop());
            path.lineTo(viewWidth - roundRadious - getPaddingRight(), getPaddingTop());

            int pointx1 = viewWidth - roundRadious - getPaddingRight();
            int pointy1 = getPaddingTop();

            int pointx3 = viewWidth - getPaddingRight();
            int pointy3 = getPaddingTop() + roundRadious;

            path.cubicTo(pointx1, pointy1, (pointx1 + pointx3) / 2 - (roundRadious * .5f), (pointy1 + pointy3) / 2 + (roundRadious * .5f), pointx3, pointy3);
            path.lineTo(pointx3, getHeight() - getPaddingBottom() - roundRadious);


            int pointx4 = pointx3;
            int pointy4 = getHeight() - getPaddingBottom() - roundRadious;


            int pointx6 = getWidth() - getPaddingRight() - roundRadious;
            int pointy6 = getHeight() - getPaddingBottom();

            path.cubicTo(pointx4, pointy4, (pointx4 + pointx6) / 2 - (roundRadious * .5f), (pointy4 + pointy6) / 2 - (roundRadious * .5f), pointx6, pointy6);
            path.lineTo(getPaddingLeft() + roundRadious, pointy6);


            int pointx7 = getPaddingLeft() + roundRadious;
            int pointy7 = pointy6;

            int pointx9 = getPaddingLeft();
            int pointy9 = pointy4;

            path.cubicTo(pointx7, pointy7, (pointx7 + pointx9) / 2 + (roundRadious * .5f), (pointy7 + pointy9) / 2 - (roundRadious * .5f), pointx9, pointy9);
            path.lineTo(getPaddingLeft(), pointy3);

            int pointx10 = getPaddingLeft();
            int pointy10 = pointy3;

            int pointx12 = roundRadious + getPaddingLeft();
            int pointy12 = getPaddingTop();

            path.cubicTo(pointx10, pointy10, (pointx10 + pointx12) / 2 + (roundRadious * .5f), (pointy10 + pointy12) / 2 + (roundRadious * .5f), pointx12, pointy12);
            path.lineTo(roundRadious + getPaddingLeft(), getPaddingTop());
            path.setLastPoint(roundRadious + getPaddingLeft(), getPaddingTop());
            canvas.drawPath(path, paint);
            Rect dest = new Rect(0, 0, getWidth(), getHeight());
            Paint bgpaint = new Paint();
            bgpaint.setStyle(Paint.Style.FILL_AND_STROKE);
            bgpaint.setAntiAlias(true);
            bgpaint.setDither(true);
            bgpaint.setStrokeWidth(5);
            bgpaint.setStrokeJoin(Paint.Join.MITER);    // set the join to round you want
            bgpaint.setStrokeCap(Paint.Cap.SQUARE);      // set the paint cap to round too
            canvas.clipPath(path);
            if (background != 0) {
                Drawable drawable = ContextCompat.getDrawable(getContext(), background);
                if (drawable instanceof ColorDrawable) {
                    bgpaint.setColor(((ColorDrawable) drawable).getColor());
                    canvas.drawRect(dest, bgpaint);
                } else if (drawable instanceof BitmapDrawable) {
                    Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                    if (bitmap.getWidth() > viewWidth || bitmap.getHeight() > viewHeight) {
                        bitmap = Bitmap.createScaledBitmap(bitmap, viewWidth, viewHeight, false);
                    }
                    BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
                    bgpaint.setShader(bitmapShader);
                    bgpaint.setFilterBitmap(true);
                    canvas.drawBitmap(bitmap, null, dest, null);
                    bitmap.recycle();
                }
            }

        }
        super.dispatchDraw(canvas);
    }


    @Override
    public int getPaddingRight() {
        return strokewidth;
    }

    @Override
    public int getPaddingLeft() {
        return strokewidth;
    }

    @Override
    public int getPaddingTop() {
        return strokewidth;
    }


    @Override
    public int getPaddingBottom() {
        return strokewidth;
    }


    /**
     * @param strokewidth set strokewidth
     */
    public void setStrokewidth(int strokewidth) {
        this.strokewidth = strokewidth;
        invalidate();
    }

    /**
     * @param strokeColor set border color ex.Red, Green etc.
     */
    public void setStrokeColor(int strokeColor) {
        this.strokeColor = strokeColor;
        invalidate();
    }


    /**
     * @param roundRadious set corner radious
     */
    public void setRoundRadious(int roundRadious) {
        this.roundRadious = roundRadious;
        invalidate();
    }

    /**
     * @param resourceid
     */
    public void setBackground(int resourceid) {
        this.background = resourceid;
        invalidate();
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        super.setPadding(roundRadious, roundRadious, roundRadious, roundRadious);
    }


}

