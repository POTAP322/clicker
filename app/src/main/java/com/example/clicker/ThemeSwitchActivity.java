package com.example.clicker;

import android.content.Intent;
import android.os.Bundle;
import android.os.Trace;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.clicker.MainActivity;

public class ThemeSwitchActivity extends AppCompatActivity {

    private Button goBackBtn;
    private Button themeBTN1;
    private Button themeBTN2;
    private Button themeBTN3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_theme_switch);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        goBackBtn = findViewById(R.id.goBackBtn);
        themeBTN1 = findViewById(R.id.themeBTN1);
        themeBTN2 = findViewById(R.id.themeBTN2);
        themeBTN3 = findViewById(R.id.themeBTN3);

        updateThemeButtonActivity();


        goBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //нормально закрывает активити текущий и кидает на главный экран( при этом сохранённые данные работают)
                finish();
            }
        });
        themeBTN1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.scoreMultiplier = 2;
            }
        });
        themeBTN2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.scoreMultiplier = 3;
            }
        });
        themeBTN3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.scoreMultiplier = 4;
            }
        });
    }

    private void updateThemeButtonActivity() {
        if (MainActivity.score >= 10) {
            themeBTN1.setEnabled(true);
        }
        if (MainActivity.score >= 100) {
            themeBTN2.setEnabled(true);
        }
        if (MainActivity.score >= 200) {
            themeBTN3.setEnabled(true);
        }

    }


}