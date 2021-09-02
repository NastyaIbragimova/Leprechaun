package com.example.leprechaun;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EndGameActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);
        ImageButton buttonExit = findViewById(R.id.button_exit);
        ImageButton buttonRestart = findViewById(R.id.button_restart);
        buttonRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EndGameActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });
        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });
        TextView result = findViewById(R.id.result);
        int countCoins = getIntent().getIntExtra("CountCoins", 0);
        result.setText(":  " + countCoins);
    }
}
