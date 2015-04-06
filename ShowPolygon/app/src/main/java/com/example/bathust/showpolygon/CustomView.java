package com.example.bathust.showpolygon;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


public class CustomView extends View {
    private int step=2;
    private int edgeNumber;
    private float degree;
    private float scale;
    private float strokeWidth;
    private int edgeColor;
    private Bitmap bitmap;
    private Paint paintPicture;
    private Path path;
    private float translateX;
    private float translateY;



    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paintPicture = new Paint();
        path = new Path();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomView);
        edgeColor = typedArray.getColor(R.styleable.CustomView_edgeColor, 0x000000);
        strokeWidth = typedArray.getFloat(R.styleable.CustomView_strokeWidth, 3);
        edgeNumber = typedArray.getInteger(R.styleable.CustomView_edgeNumber,0);
        typedArray.recycle();
        paintPicture.setAntiAlias(true);
        paintPicture.setStrokeWidth(strokeWidth);
        paintPicture.setColor(edgeColor);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int canvasHeight = this.getHeight();
        int canvasWidth = this.getWidth();
        canvas.translate(translateX,translateY);



        if(step == 0) {
            Bitmap fitBitmap = Bitmap.createScaledBitmap(bitmap,canvasWidth,canvasHeight,true);
            BitmapShader bitmapShader = new BitmapShader(fitBitmap, Shader.TileMode.REPEAT,
                    Shader.TileMode.REPEAT);
            paintPicture.setShader(bitmapShader);

        }
        if(step == 1) {
            paintPicture.setShader(null);

        }
        canvas.scale(scale, scale, canvasWidth/2, canvasWidth/2);
        canvas.rotate(degree, canvasHeight/2, canvasWidth/2);
        drawPicture(canvas,edgeNumber,canvasWidth,canvasHeight);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        translateX=event.getX();
        translateY=event.getY();
        invalidate();
        return true;
    }

    public void setPaintColor(int clR, int clG, int clB) {
        paintPicture.setColor(Color.rgb(clR,clG,clB));
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void setDegree(float degree) {

        this.degree = degree;
    }

    public void setEdgeNumber(int edgeNumber) {
        this.edgeNumber = edgeNumber;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public void setBitmap(String savePath) {
        this.bitmap = BitmapFactory.decodeFile(savePath).copy(Bitmap.Config.ARGB_8888,true);
    }
    private void drawPicture(Canvas canvas, int edgeNum, float canvasWidth, float canvasHeight)
    {
        float edgeLong = Math.min(canvasHeight, canvasWidth);
        switch (edgeNum) {
            case 0:break;
            case 1:break;
            case 2:break;
            case 4:
               // canvas.scale(scale,scale,canvasWidth/2,canvasHeight/2);
                //canvas.rotate(degree,canvasWidth/2,canvasHeight/2);
                canvas.drawRect(canvasWidth/2-edgeLong/4, edgeLong/4, canvasWidth/2+edgeLong/4, 3*edgeLong/4, paintPicture);
                break;
            case 3:

                /**
                 * 设置画笔路径
                 */
                double triangleHeight = (Math.sqrt(3) * (edgeLong / 4));
                path.moveTo(edgeLong / 2, (float) (edgeLong/2-triangleHeight));
                path.lineTo(edgeLong/4, (float) triangleHeight);
                path.lineTo(3*edgeLong/4, (float) triangleHeight);
                path.close();
                canvas.drawPath(path, paintPicture);
                canvas.scale(scale,scale);
                canvas.rotate(degree,canvasWidth/2,canvasHeight/2);
                break;
            default:
                canvas.drawCircle(canvasWidth / 2, canvasHeight / 2, edgeLong / 4, paintPicture);


        }
    }
}
