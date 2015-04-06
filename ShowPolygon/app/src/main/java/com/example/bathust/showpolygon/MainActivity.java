package com.example.bathust.showpolygon;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.apache.commons.logging.Log;


public class MainActivity extends Activity implements View.OnClickListener{
    private Button createBtn;
    private EditText editDegree;
    private EditText editScale;
    private EditText editEdge;
    private ToggleButton tglBtn;
    private ImageButton imgBtn;
    private final static int IMAGE_CODE=1;
    private final static int COLOR_CODE=2;
    private int step;
    private CustomView customView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        initLayout();
        createBtn.setOnClickListener(this);
        imgBtn.setOnClickListener(this);
        tglBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                step=(isChecked ? 1:0);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void initLayout() {
        createBtn = (Button) findViewById(R.id.createBtn);
        editDegree = (EditText) findViewById(R.id.editDegree);
        editEdge = (EditText) findViewById(R.id.editEdge);
        editScale = (EditText) findViewById(R.id.editScale);
        tglBtn = (ToggleButton) findViewById(R.id.tglBtn);
        imgBtn = (ImageButton)findViewById(R.id.imgBtn);
        customView = (CustomView)findViewById(R.id.customView);


    }


    @Override
    public void onClick(View v) {
        int itemId=v.getId();
        switch (itemId) {
            case R.id.createBtn:
                String strEdge = editEdge.getText().toString();
                String strDegree = editDegree.getText().toString();
                String strScale = editScale.getText().toString();
                float degree = 0;
                float scale = 1.0f;

                if (!strDegree.equals("")) {
                    degree= Float.parseFloat(strDegree);
                }

                if (!strScale.equals("")) {
                    scale= Float.parseFloat(strScale);
                }

                if (!strEdge.equals("")) {
                    customView.setEdgeNumber(Integer.parseInt(strEdge));

                }
                else {
                    Toast.makeText(MainActivity.this, getString(R.string.edge_input), Toast.LENGTH_SHORT).show();
                }
                customView.setDegree(degree);
                customView.setScale(scale);
                customView.invalidate();
                break;
            case R.id.imgBtn:
                imgBtn.setImageBitmap(null);
                imgBtn.setBackgroundResource(R.color.black);

                if (step == 0) {
                    customView.setStep(step);
                    Intent i = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, IMAGE_CODE);//ImageCode用于区别不同activity的请求
                }
                if (step == 1) {

                    Intent intent=new Intent(MainActivity.this, DialogActivity.class);
                    startActivityForResult(intent, COLOR_CODE);
                    customView.setStep(step);


                }

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && data != null && requestCode == IMAGE_CODE) {
                Uri imageData = data.getData();//getData方法用于取回当前intent正在操作的数据
                String[] pathColumn = {MediaStore.Images.Media.DATA};//MediaStore.Images.Media.DATA是一个数据流
                Cursor cursor = getContentResolver().query(imageData,
                        pathColumn, null, null, null);//查询当前数据，getContentResolver获取Contentprovider封装的数据
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA);//获取索引
                String savePath = cursor.getString(columnIndex);
                cursor.close();
                Bitmap bitmap = BitmapFactory.decodeFile(savePath);
                imgBtn.setImageBitmap(bitmap);
                customView.setBitmap(savePath);
            }
        if (requestCode == COLOR_CODE && resultCode == RESULT_OK) {
            int returnColorR = data.getIntExtra("colorR",0);
            int returnColorG = data.getIntExtra("colorG",0);
            int returnColorB = data.getIntExtra("colorB",0);

            imgBtn.setBackgroundColor(Color.rgb(returnColorR, returnColorG, returnColorB));
            customView.setPaintColor(returnColorR, returnColorG, returnColorB);
        }

        }
    }

