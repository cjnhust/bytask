package com.example.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.*;
import android.graphics.drawable.ShapeDrawable;
import android.util.AttributeSet;
import android.view.View;


public class MyView extends View {
    private int edgeColor;
    private int edgeNumber;
    private float pictureStrokeWidth;
    public void setBitmap(String filePath) {
        this.bitmap = BitmapFactory.decodeFile(filePath).copy(Bitmap.Config.ARGB_8888,true);
    }

    private Bitmap bitmap;



    private float degree;
    private float ratio;

    public void setRatio(float ratio) {
        this.ratio = ratio;
    }

    public void setDegree(float degree) {
        this.degree =degree;
    }
    public void setEdgeNumber(int edgeNumber) {

        this.edgeNumber = edgeNumber;

    }


    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        /**
         *获取自定义ｖｉｅｗ的属性并设置默认值
          */
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyView);
        edgeColor = typedArray.getColor(R.styleable.MyView_edgeColor, 0xffffff);
        pictureStrokeWidth = typedArray.getFloat(R.styleable.MyView_pictureStrokeWidth, 3);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int mHeight = this.getHeight();
        int mWidth = this.getWidth();
        int edgeLong = Math.min(mHeight, mWidth);
        if (MyActivity.getStep() == 1) {
          Paint mPaintPicture = new Paint();
        mPaintPicture.setARGB(255,SelectActivity.getColorR(),SelectActivity.getColorG(),SelectActivity.getColorB());
            mPaintPicture.setAntiAlias(true);
            /**
             * 在点击颜色按钮的情况下画图
              */
            mPaintPicture.setStyle(Paint.Style.FILL);
           mPaintPicture.setStrokeWidth(pictureStrokeWidth);
            switch (edgeNumber) {
                case 0:break;
                case 1:break;
                case 2:break;
                case 4:
                    canvas.scale(ratio,ratio,mWidth/2,mHeight/2);
                    canvas.rotate(degree,mWidth/2,mHeight/2);
                    canvas.drawRect(mWidth/2-edgeLong/4, edgeLong/4, mWidth/2+edgeLong/4, 3*edgeLong/4, mPaintPicture);
                    break;
                case 3:
                    Path mPath = new Path();
                    /**
                     * 设置画笔路径
                      */
                    double triangleHeight = (Math.sqrt(3) * (edgeLong / 4));
                    mPath.moveTo(edgeLong / 2, (float) (edgeLong/2-triangleHeight));
                    mPath.lineTo(edgeLong/4, (float) triangleHeight);
                    mPath.lineTo(3*edgeLong/4, (float) triangleHeight);
                    mPath.close();
                    canvas.scale(ratio,ratio,edgeLong/2,(float) (2*triangleHeight/3));
                    canvas.rotate(degree,edgeLong/2, (float) (2*triangleHeight/3));
                    canvas.drawPath(mPath, mPaintPicture);
                    break;
                default:
                    canvas.scale(ratio,ratio,mWidth/2,mHeight/2);
                    canvas.drawCircle(mWidth / 2, mHeight / 2, edgeLong / 4, mPaintPicture);


            }




        }
        if(MyActivity.getStep()==2){

              Paint paintPhoto=new Paint();
            Bitmap mBitmap= Bitmap.createScaledBitmap(bitmap,mWidth,mHeight,true);
            paintPhoto.setColor(edgeColor);
            paintPhoto.setAntiAlias(true);
            paintPhoto.setStrokeWidth(pictureStrokeWidth);
            BitmapShader bitmapShader = new BitmapShader(mBitmap, Shader.TileMode.REPEAT,
                    Shader.TileMode.REPEAT);
            paintPhoto.setShader(bitmapShader);
            switch (edgeNumber) {
                case 0:break;
                case 1:break;
                case 2:break;
            case 4:
                canvas.scale(ratio,ratio,mWidth/2,mHeight/2);
                canvas.rotate(degree, mWidth / 2, mHeight / 2);
                canvas.drawRect(mWidth/2-edgeLong/4, edgeLong/4, mWidth/2+edgeLong/4, 3*edgeLong/4,paintPhoto);

                break;
            case 3:
                Path path = new Path();
                /**
                 * 设置画笔路径
                 */
               double triangleHeight = (Math.sqrt(3) * (edgeLong / 4));
                path.moveTo(edgeLong / 2, (float) (edgeLong / 2 - triangleHeight));
               path.lineTo(edgeLong / 4, (float) triangleHeight);
               path.lineTo(3 * edgeLong / 4, (float) triangleHeight);
               path.close();
                canvas.scale(ratio,ratio,edgeLong/2,(float) (2*triangleHeight/3));
                canvas.rotate(degree,edgeLong/2, (float) (2*triangleHeight/3));
                canvas.drawPath(path, paintPhoto);
                break;
            default:
                canvas.scale(ratio, ratio, mWidth/ 2, mHeight / 2);
                canvas.rotate(degree,mWidth/2, mHeight/2);
                canvas.drawCircle(mWidth/2,mHeight/2, edgeLong/4, paintPhoto);
        }


    }
}

}