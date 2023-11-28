package kr.ac.yeonsung.seoj.ecomate;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.widget.Toolbar;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ResultActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar); //툴바를 액션바로 설정
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //뒤로가기 버튼 활성화

        Button btn_result_main = findViewById(R.id.btn_result_gomain);
        Button btn_result_selectimage = findViewById(R.id.btn_result_selectimage);
        Button btn_result_camera = findViewById(R.id.btn_result_camera);

        btn_result_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        btn_result_selectimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 10);
            }
        });

        btn_result_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,0);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        try{
            switch (requestCode) {
                case REQUEST_TAKE_PHOTO: {
                    if (resultCode == RESULT_OK) {
                        File file = new File(mCurrentPhotoPath);
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.fromFile(file));
                        if (bitmap != null) {
                            imageView.setImageBitmap(bitmap);
                        }
                    }
                    break;
                }
            }
        } catch (IOException error) {
            error.printStackTrace();
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_result_camera:
                ((MainActivity)MainActivity.mContext).dispatchTakePictureIntent();
                break;
        }
    }


}