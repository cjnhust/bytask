package com.example.view;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Picture extends Activity {
    private GridView gridView;
    ArrayList<HashMap<String,Object>> lists=new ArrayList<HashMap<String, Object>>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picture);
        gridView=(GridView)findViewById(R.id.gridView);


        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            File imagePath=Environment.getExternalStorageDirectory();
            File[] files=imagePath.listFiles();
            getFile(files);
            SimpleAdapter simpleAdapter=new SimpleAdapter(Picture.this,lists,R.layout.items,
                    new String[] {"name","image"}, new int[]{R.id.title, R.id.image});
            gridView.setAdapter(simpleAdapter);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    finish();

                }
            });


        }


    }
    private void getFile(File[] files ){
        if(files!=null){
            for(File file:files){
                if (file.isDirectory()){
                    getFile(file.listFiles());
                }
                else {
                    String fileName=file.getName();
                    if (fileName.endsWith(".jpg")){
                        String filePath=file.getAbsolutePath();
                        HashMap<String,Object> map=new HashMap<String, Object>();
                        map.put("image",filePath);
                        map.put("name",fileName);
                        lists.add(map);


                    }
                }

            }
        }
    }



}

