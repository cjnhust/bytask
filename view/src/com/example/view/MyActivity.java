package com.example.view;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.lang.annotation.ElementType;

public class MyActivity extends Activity {

    private Button setBn;
    private MyView myView;
    private EditText edge,revolve,zoom;
    private static int step;
    private ToggleButton toggleButton;
    private final static int ImageCode=1;
    private ImageView imageView;
    public static int getStep() {
        return step;
    }
    private static String savePath;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);
        MyAvtivityMethod.myActivity=this;
        edge=(EditText)findViewById(R.id.edge);
        setBn=(Button)findViewById(R.id.setbutton);
        myView=(MyView)findViewById(R.id.myView);
        imageView=(ImageView)findViewById(R.id.imageView);
        toggleButton=(ToggleButton)findViewById(R.id.toggleButton);
        revolve=(EditText)findViewById(R.id.revolve);
        zoom=(EditText)findViewById(R.id.zoom);
        setBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 生成按钮点击后重绘自定义ｖｉｅｗ
                  */
                String strEdge = edge.getText().toString();
                String strDegree=revolve.getText().toString();
                String strRatio=zoom.getText().toString();
                System.out.print(strDegree);
                /**
                 * 防止未输入数值报错
                 */
               if (strDegree.equals("")||strEdge.equals("")||strRatio.equals("")){
                   Toast.makeText(MyActivity.this,"请输入数值",Toast.LENGTH_SHORT).show();}

                else {
                 myView.setEdgeNumber(Integer.parseInt(strEdge));
                       myView.setDegree(Float.parseFloat(strDegree));
                       myView.setRatio(Float.parseFloat(strRatio));
                       myView.invalidate();}
               }



        });
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            /**
             * 判断是图片还是颜色
             */
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                toggleButton.setChecked(isChecked);
                step=(isChecked?1:2);
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            /**
             * 如果是颜色跳转到ＲＧＢ选择界面
              */
            public void onClick(View v) {
                imageView.setImageBitmap(null);
                imageView.setBackgroundColor(Color.WHITE);
                if(step==1){Intent intentStartColor=new Intent(MyActivity.this,SelectActivity.class);

                    startActivity(intentStartColor);
                    }
                if(step==2){
                    /**
                     * 跳转到系统自带图库
                      */
                    Intent i = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    startActivityForResult(i, ImageCode);//ImageCode用于区别不同activity的请求
                }
                }


        });
    }
public void setImageViewColor(int colorR, int colorG, int colorB)
{

    imageView.setBackgroundColor(Color.rgb(colorR,colorG,colorB));
}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ImageCode && resultCode == RESULT_OK && null != data) {
            Uri imageData = data.getData();//getData方法用于取回当前intent正在操作的数据
            String[] pathColumn = { MediaStore.Images.Media.DATA };//MediaStore.Images.Media.DATA是一个数据流
            Cursor cursor = getContentResolver().query(imageData,
                    pathColumn, null, null, null);//查询当前数据，getContentResolver获取Contentprovider封装的数据
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA);//获取索引
             savePath = cursor.getString(columnIndex);
            cursor.close();
            Bitmap bitmap=BitmapFactory.decodeFile(savePath);
            imageView.setImageBitmap(bitmap);
            myView.setBitmap(savePath);

        }


    }

}
