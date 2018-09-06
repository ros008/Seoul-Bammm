package com.example.kimsujin.seoul_moon_01;

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Recommendation_board extends AppCompatActivity {
    final static String TAG = "MainActivity";

    Toolbar toolbar;

    ImageView moon_image;
    ImageView img;

    TextView moon_num;
    TextView gu, filter, region_detail, explanation;

    int click_num = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation_board);

        // toolbar 설정
        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        moon_num = findViewById(R.id.recommendation_num);
        moon_image = findViewById(R.id.moon_btn);

        img = findViewById(R.id.img);
        gu = findViewById(R.id.gu);
        filter = findViewById(R.id.filter);
        region_detail = findViewById(R.id.region_detail);
        explanation = findViewById(R.id.explanation);

        moon_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(click_num == 0){
                    String num = (String) moon_num.getText();
                    int num_1 = Integer.parseInt(num);
                    num_1++;
                    num = Integer.toString(num_1);
                    moon_num.setText(num);
                    click_num = 1;
                }
                else{
                    String num = (String) moon_num.getText();
                    int num_1 = Integer.parseInt(num);
                    num_1--;
                    num = Integer.toString(num_1);
                    moon_num.setText(num);
                    click_num = 0;
                }
            }
        });

        Intent intent = getIntent();
        String region = intent.getStringExtra("region");
        popIntent(region);
    }

    public void popIntent(String region_name) {
        InputStream is = getResources().openRawResource(R.raw.region_data);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader reader = new BufferedReader(isr);

        StringBuffer sb = new StringBuffer();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            Log.i(TAG, "sb : " + sb.toString());

            JSONObject jsonObject = new JSONObject(sb.toString());
            JSONArray jsonArray = new JSONArray(jsonObject.getString("region_info"));

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                String _name = jsonObject1.getString("name");
                Log.i(TAG, "_name : " + _name);

                if(region_name.equals(_name) == false){
                    continue;
                }
                else {
                    String _gu = jsonObject1.getString("gu");
                    Log.i(TAG, "_gu : " + _gu);
                    gu.setText(_gu);

                    String _img = jsonObject1.getString("img");
                    Log.i(TAG, "_img : " + _img);
                    //

                    String _filter = jsonObject1.getString("filter");
                    Log.i(TAG, "_filter : " + _filter);
                    filter.setText(_filter);

                    JSONObject jsonObject2 = jsonObject1.getJSONObject("detail");
                    String _region_detail = jsonObject2.getString("region_detail");
                    Log.i(TAG, "_region_detail : " + _region_detail);
                    region_detail.setText(_region_detail);

                    String _explanation = jsonObject2.getString("explanation");
                    Log.i(TAG, "_explanation : " + _explanation);
                    explanation.setText(_explanation);

                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null)
                    reader.close();
                if (isr != null)
                    isr.close();
                if (is != null)
                    is.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: { //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
