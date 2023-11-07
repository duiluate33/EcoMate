package kr.ac.yeonsung.seoj.ecomate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar); //툴바를 액션바로 설정
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //뒤로가기 버튼 활성화
    }
}