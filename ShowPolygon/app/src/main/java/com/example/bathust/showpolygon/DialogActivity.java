package com.example.bathust.showpolygon;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;


public class DialogActivity extends Activity {
    private SeekBar seekBarR;
    private SeekBar seekBarG;
    private SeekBar seekBarB;
    private Button sureButton;
    private int colorR;
    private int colorG;
    private int colorB;





    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        initDialog();
        seekBarR.setOnSeekBarChangeListener(seekBarChange);
        seekBarG.setOnSeekBarChangeListener(seekBarChange);
        seekBarB.setOnSeekBarChangeListener(seekBarChange);
        sureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();

                intent.putExtra("colorR", colorR);
                intent.putExtra("colorG", colorG);
                intent.putExtra("colorB", colorB);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private void initDialog() {
        seekBarR=(SeekBar)findViewById(R.id.seekBarR);
        seekBarG=(SeekBar)findViewById(R.id.seekBarG);
        seekBarB=(SeekBar)findViewById(R.id.seekBarB);
        sureButton=(Button)findViewById(R.id.sureButton);
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_select, menu);
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
}
