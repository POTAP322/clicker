package com.example.clicker;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ThemeSwitchActivity extends AppCompatActivity {

    private Button goBackBtn;
    private Button themeBTN1;
    private Button themeBTN2;
    private Button themeBTN3;

    private Button screamerBTN;

    private MediaPlayer mediaPlayer;

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


        screamerBTN = findViewById(R.id.screamerBTN);

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
                MainActivity.curThemeCode = 2;
                stopMusic();
                // Сохраняем изменения в SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("themeChanged", true);
                editor.apply();

                // Возвращаемся в MainActivity
                Intent intent = new Intent(ThemeSwitchActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        themeBTN2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.scoreMultiplier = 3;
                MainActivity.curThemeCode = 3;
                stopMusic();
                // Сохраняем изменения в SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("themeChanged", true);
                editor.apply();

                // Возвращаемся в MainActivity
                Intent intent = new Intent(ThemeSwitchActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        themeBTN3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.scoreMultiplier = 10;
                MainActivity.curThemeCode = 4;
                stopMusic();
                // Сохраняем изменения в SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("themeChanged", true);
                editor.apply();

                // Возвращаемся в MainActivity
                Intent intent = new Intent(ThemeSwitchActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        screamerBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.curThemeCode = 1;
                Dialog dialog = new Dialog(ThemeSwitchActivity.this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                dialog.setContentView(R.layout.screamer_image);

                ImageView fullScreenImageView = dialog.findViewById(R.id.fullScreenImageView);
                dialog.show();
                stopMusic();
                playScreamer();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fullScreenImageView.setImageResource(R.drawable.screamer);

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                // Сохраняем изменения в SharedPreferences
                                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putBoolean("themeChanged", true);
                                editor.apply();

                                // Возвращаемся в MainActivity
                                Intent intent = new Intent(ThemeSwitchActivity.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }
                        }, 2000);
                    }
                }, 2600); // Задержка в 2600 миллисекунд


            }
        });
    }

    private void updateThemeButtonActivity() {
        if (MainActivity.score >= 100) {
            themeBTN1.setEnabled(true);
        }
        if (MainActivity.score >= 1000) {
            themeBTN2.setEnabled(true);
        }
        if (MainActivity.score >= 10000) {
            themeBTN3.setEnabled(true);
        }
        if (MainActivity.score >= 50000) {
            screamerBTN.setEnabled(true);
        }

    }

    private void stopMusic() {
        if (MainActivity.mediaPlayer != null) {
            MainActivity.mediaPlayer.stop();
            MainActivity.mediaPlayer.release();
            MainActivity.mediaPlayer = null;
        }
    }

    private void playScreamer() {
        mediaPlayer = MediaPlayer.create(this, R.raw.screamer);
        mediaPlayer.setVolume(1.0f, 1.0f);
        mediaPlayer.start();
    }


}