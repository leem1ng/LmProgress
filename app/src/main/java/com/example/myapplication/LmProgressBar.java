package com.example.myapplication;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import androidx.annotation.Nullable;





public class LmProgressBar extends View {
    private  static  final  float DEF_RATIO =0.5f;
    private  static  final  int DEF_REACHED_COLOR= Color.RED;
    private  static  final  int DEF_UNREACHED_COLOR= Color.BLACK;
    private  float mRatio;
    private  int   mReachedAreaColor;
    private  int   mUnreachedAreaColor;
    private RectF mReachedRect;
    private Paint mReachedPaint;
    private RectF mUnreachedRect;
    private Paint mUnrachedPaint;
    private  RectF mProgressBarBorder;
    private  Paint mProgressBarBorderPaint;
    private  Bitmap mFlowerBitmap;
    private  RectF mFlowerBitmapRectf;
    public    Integer mAnimatedValue;
    public    boolean isJump;
    public LmProgressBar(Context context) {
        this( context , null );
    }

    public LmProgressBar(Context context, @Nullable AttributeSet attrs) {
        this( context, attrs ,0 );
    }

    public LmProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super( context, attrs, defStyleAttr );
        TypedArray typedArray =getContext().obtainStyledAttributes( attrs,R.styleable.LmProgressBar);
        mRatio=typedArray.getFloat( R.styleable.LmProgressBar_ratio,DEF_RATIO );
        mReachedAreaColor =typedArray.getInt( R.styleable.LmProgressBar_reached_area, DEF_REACHED_COLOR);
        mUnreachedAreaColor =typedArray.getInt( R.styleable.LmProgressBar_unreached_area, DEF_UNREACHED_COLOR);
        mReachedRect =new RectF(  );
        mReachedPaint = new Paint(  );
        mReachedPaint.setColor( mReachedAreaColor );
        mUnreachedRect = new RectF(  );
        mUnrachedPaint =new Paint(  );
        mUnrachedPaint.setColor( mUnreachedAreaColor     );
        mProgressBarBorder =new RectF(  );
        mProgressBarBorderPaint =new Paint( );
        mProgressBarBorderPaint.setStyle( Paint.Style.STROKE );
        mProgressBarBorderPaint.setStrokeWidth( 30);
        mProgressBarBorderPaint.setColor( Color.WHITE );
        mFlowerBitmapRectf =new RectF(  );

        ValueAnimator animator = ValueAnimator.ofInt(1,100);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setDuration(300);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.setRepeatCount(Integer.MAX_VALUE);
        animator.addUpdateListener( new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if(isJump){
                    mAnimatedValue = (Integer) animation.getAnimatedValue();
                    invalidate();
                }
            }
        } );

        animator.start();


        typedArray.recycle();;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case  MotionEvent.ACTION_DOWN:
                mReachedPaint.setColor( mReachedAreaColor );
                mUnrachedPaint.setColor( mUnreachedAreaColor);
                Log.d( "liming" ,"action down" );
                //刷新UI
                invalidate();
                break;
            case  MotionEvent.ACTION_UP:
                mReachedPaint.setColor( mUnreachedAreaColor );
                mUnrachedPaint.setColor( mReachedAreaColor);
                Log.d( "liming" ,"action up" );
                invalidate();
                break;
        }

        return super.onTouchEvent( event );
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure( widthMeasureSpec, heightMeasureSpec );
        int widthMode= MeasureSpec.getMode( widthMeasureSpec );
        int heightMode =MeasureSpec.getMode( heightMeasureSpec );
        int widthSize = MeasureSpec.getSize( widthMeasureSpec );

        int heightSize =MeasureSpec.getSize( heightMeasureSpec );
        int width =0;
        int height =0;

        switch (widthMode){
            case MeasureSpec.EXACTLY:
                width =widthSize;
                break;
            case MeasureSpec.AT_MOST:
                width = Math.min(getSuggestedMinimumWidth(),widthSize);
                break;
            case MeasureSpec.UNSPECIFIED:
                width =getSuggestedMinimumWidth();
        }
        switch (heightMode){
            case MeasureSpec.EXACTLY:
                height =heightSize;
                break;
            case MeasureSpec.AT_MOST:
                height = Math.min(getSuggestedMinimumHeight(),heightSize);
                break;
            case MeasureSpec.UNSPECIFIED:
                height =getSuggestedMinimumHeight();
        }
        setMeasuredDimension( width,height );
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw( canvas );
        mReachedRect.set( 0,0,getWidth()*mRatio ,getHeight());
        mProgressBarBorder.set( 0,0,getWidth(),getHeight() );
        mUnreachedRect.set( getWidth()*mRatio,0, getWidth(),getHeight() );
        canvas.drawRect( mReachedRect,mReachedPaint );
        canvas.drawRect( mUnreachedRect,mUnrachedPaint );
        canvas.drawRoundRect( mProgressBarBorder,60,60 ,mProgressBarBorderPaint);
        if(mFlowerBitmap ==null){

            mFlowerBitmap =BitmapFactory.decodeResource( getResources(),R.drawable.ic_launcher);
        }
        int mLeft = (int) (getWidth()*mRatio -mFlowerBitmap.getWidth()/4);
        int mRight = (int) (getWidth()*mRatio +mFlowerBitmap.getWidth()/4);
        if (mAnimatedValue != null) {
            Log.d( "liming" ,"1" );
            mFlowerBitmapRectf.set(mLeft, -mAnimatedValue, mRight, getHeight() - mAnimatedValue);
        } else {
            Log.d( "liming" ,"2" );
            mFlowerBitmapRectf.set(mLeft, 0, mRight, getHeight());

        }
        canvas.drawBitmap( mFlowerBitmap,null,mFlowerBitmapRectf,null );
    }

    @Override
    protected int getSuggestedMinimumHeight() {
        return 80;
    }

    @Override
    protected int getSuggestedMinimumWidth() {
        return 100;
    }
    public  void  setRadio(float radio){
        this.mRatio =radio;
        invalidate();
    }


    public void setJump(boolean jump) {
        Log.d( "liming4", isJump +"" );
        isJump = jump;
    }
}
//class AnimatrUpdateListener1 implements ValueAnimator.AnimatorUpdateListener{
//
//    private LmProgressBar bar;
//    public AnimatrUpdateListener1(LmProgressBar bar){
//
//        this.bar = bar;
//    }
//
//    public AnimatrUpdateListener1() {
//
//    }
//
//    @Override
//    public void onAnimationUpdate(ValueAnimator animation) {
//        Log.d( "liming3","" );
//
//        if (bar.isJump) {
//            Integer mAnimatedValue = (Integer) animation.getAnimatedValue();
//            bar.invalidate();
//        }
//    }
//
//}
