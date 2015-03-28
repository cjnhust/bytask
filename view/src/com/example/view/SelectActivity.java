package com.example.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;


public class SelectActivity extends Activity {
    private SeekBar seekBarR,seekBarG,seekBarB;
    private Button ensure;

    public static int getColorR() {
        return colorR;
    }

    public static int getColorG() {
        return colorG;
    }

    public static int getColorB() {
        return colorB;
    }

    private static int colorR,colorG,colorB;





    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.select);
        seekBarR=(SeekBar)findViewById(R.id.seekBarR);
        seekBarG=(SeekBar)findViewById(R.id.seekBarG);
        seekBarB=(SeekBar)findViewById(R.id.seekBarB);
        ensure=(Button)findViewById(R.id.ensure);
        seekBarR.setOnSeekBarChangeListener(seekBarChange);
        seekBarG.setOnSeekBarChangeListener(seekBarChange);
        seekBarB.setOnSeekBarChangeListener(seekBarChange);
        ensure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyAvtivityMethod.myActivity.setImageViewColor(colorR, colorG, colorB);//设置预览图



                finish();
            }
        });
    }
    private SeekBar.OnSeekBarChangeListener seekBarChange = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            /**
             * 获取当前ｓｅｅｋｂａｒ的位置从而得到代表的ｒｇｂ值
              */
            colorR=seekBarR.getProgress();
            colorB=seekBarB.getProgress();
            colorG=seekBarG.getProgress();

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
        }
    };
}