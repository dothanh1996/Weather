package com.example.dothanh.weather;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.dothanh.weather.AsyncTask.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {

    private CurrentWeatherAsyncTask mCurrentWeather;
    private TextView txtJson;
    private TextView txtLast;
    private File fileWriter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtJson = (TextView) findViewById(R.id.txt_ThongTin);
        txtLast = (TextView) findViewById(R.id.txt_ThoiGian);
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();

        fileWriter = new File(path+"/Download/current_weather.json");
        Log.e("PATH", path);
        mCurrentWeather = new CurrentWeatherAsyncTask(this);
        mCurrentWeather.execute();
        mCurrentWeather.setCallBack(new CurrentWeatherAsyncTask.CallBack() {
            @Override
            public void onFinish(String body) {
                txtJson.setText(body);

                try {
                    FileWriter fileWriter1 = new FileWriter(fileWriter);
                    fileWriter1.write(body);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                txtLast.setText(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(fileWriter.lastModified()));
            }
        });
    }
}
