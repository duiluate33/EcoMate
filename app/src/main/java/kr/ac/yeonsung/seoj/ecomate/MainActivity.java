package kr.ac.yeonsung.seoj.ecomate;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout.DrawerListener ActionBarDrawerToggle;
    ImageView imageView;
    File file;
    Bitmap bitmap;
    Button btncamera, btngallery;

    String mCurrentPhotoPath;
    static final int REQUEST_TAKE_PHOTO = 1 ;

    public static Context mContext;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //File sdcard = Environment.getExternalStorageDirectory();
        file = new File(Environment.getExternalStorageDirectory().getAbsolutePath());

        imageView = findViewById(R.id.imageView);
        btncamera = findViewById(R.id.btn_camera);
        btngallery = findViewById(R.id.btn_gallery);


        btngallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 10);
            }
        });

        btncamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,0);
            }
        });

        mContext = this;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.app_guide:
                return true;
            case R.id.trash_guide:
                return true;
        }
        return false;
    }

    void getPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(android.Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.CAMERA},11);

            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==11){
            if(grantResults.length>0){
                if(grantResults[0]!= PackageManager.PERMISSION_GRANTED){
                    this.getPermission();
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == 10) {
            if (data != null) {
                Uri uri = data.getData();

                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    imageView.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
        else if(requestCode==12){
            bitmap = (Bitmap)data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //카메라로 촬영한 이미지를 파일로 저장해주는 함수 생성
    private File createImageFile() throws IOException{
        //create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File StorageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                StorageDir
        );
        //save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    //카메라 인텐트를 실행하는 부분을 별도 함수로 생성
    public void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        //ensure that's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            //찍은 이미지 파일을 어디로 보낼지 생성
            File photofile = null;
            try{
                File photoFile = createImageFile();
            }
            catch (IOException ex) {
                // create file했을 때 발생하는 에러
            }

            //파일이 성공적으로 생성됐을 때 continue
            if (photofile != null) {
                Uri photoURI = FileProvider.getUriForFile(this, "kr.ac.yeonsung.seoj.fileprovider", photofile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent,REQUEST_TAKE_PHOTO);
            }

        }
    }

}