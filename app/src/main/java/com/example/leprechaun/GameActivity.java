package com.example.leprechaun;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {

    private LeprechaunView leprechaunView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leprechaun);
        leprechaunView = findViewById(R.id.leprechaun_view);
        Leprechaun leprechaun = leprechaunView.getLeprechaun();
        ImageButton jump = findViewById(R.id.jump);
        jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leprechaun.jump();
            }
        });
    }
}
