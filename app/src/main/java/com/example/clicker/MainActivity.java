package com.example.clicker;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.media.MediaPlayer;

public class MainActivity extends AppCompatActivity {
    private TextView scoreField;
    private ImageButton clickerBtn;
    private Button themeSwitchMenuBtn;
    private Button restartBtn;
    private ConstraintLayout mainLayout;

    private static final String SCORE_KEY = "score";
    public static int score;
    public static int scoreMultiplier = 100;
    public static int curThemeCode = 1;
    private Map<Integer, Integer> musicMap;
    private Map<Integer, Integer> imageMap;
    private Map<Integer, Integer> bgColourMap;


    private List<String> themes = new ArrayList<>(Arrays.asList("firstLevel","secondLevel"));
    private String curTheme;
    private int restartClicks;

    public static MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            score = savedInstanceState.getInt(SCORE_KEY);
            String curScore = String.valueOf(score);
            scoreField.setText(curScore);
        }

        initializeMusicMap();
        initializeImageMap();
        initializeBGcolourMap();


        scoreField = findViewById(R.id.scoreField);

        themeSwitchMenuBtn = findViewById(R.id.themeSwitchMenuBtn);
        clickerBtn = findViewById(R.id.clickerBtn);
        restartBtn = findViewById(R.id.restartBtn);

        mainLayout = findViewById(R.id.main);

        mediaPlayer = MediaPlayer.create(this, musicMap.get(curThemeCode));
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        updateTheme();



        clickerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AnimatorSet animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(MainActivity.this, R.animator.scale_animation);
                animatorSet.setTarget(clickerBtn);
                animatorSet.start();
                score+=1*scoreMultiplier;
                String curScore = String.valueOf(score);
                scoreField.setText(curScore);
                restartClicks =0;

            }
        });

        themeSwitchMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToTeThemeMenuActivity();

            }
        });

        restartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restartClicks +=1;

                if(restartClicks == 1){
                    restartBtn.setBackgroundColor(Color.parseColor("#DBD128"));
                }
                if(restartClicks == 2){
                    restartBtn.setBackgroundColor(Color.parseColor("#C52525"));
                }
                if ( restartClicks == 3){
                    score = 0;
                    scoreField.setText(String.valueOf(score));
                    restartClicks = 0;
                    scoreMultiplier = 1;
                    curThemeCode = 1;

                    clickerBtn.setImageResource(imageMap.get(1));

                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = MediaPlayer.create(MainActivity.this, musicMap.get(curThemeCode));
                    mediaPlayer.setLooping(true);
                    mediaPlayer.start();

                    mainLayout.setBackgroundColor(Color.parseColor("#2A8AA8"));


                    mediaPlayer.setLooping(true);
                    mediaPlayer.start();

                    restartBtn.setBackgroundColor(Color.parseColor("#56C32A"));
                }
            }
        });

    }
    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(SCORE_KEY, score);
        editor.apply();
    }
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        score = preferences.getInt(SCORE_KEY, score);
        String curScore = String.valueOf(score);
        scoreField.setText(curScore);
        updateTheme();
    }
    private void updateTheme() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        boolean themeChanged = sharedPreferences.getBoolean("themeChanged", false);
        if (themeChanged) {


            clickerBtn.setImageResource(imageMap.get(curThemeCode));

            mainLayout.setBackgroundColor(bgColourMap.get(curThemeCode));

            // Сбрасываем флаг после применения изменений
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("themeChanged", false);

            editor.apply();
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SCORE_KEY,score);
    }

    public void switchToTeThemeMenuActivity(){
        Intent intent = new Intent(this, ThemeSwitchActivity.class);
        startActivity(intent);
    }
    private void initializeMusicMap() {
        musicMap = new HashMap<>();
        musicMap.put(1, R.raw.mrbeast);
        musicMap.put(2, R.raw.sovietmarh);
        musicMap.put(3,R.raw.caramellag);
        musicMap.put(4,R.raw.mongol);

    }
    private void initializeImageMap(){
        imageMap = new HashMap<>();
        imageMap.put(1,R.drawable.mrbst);
        imageMap.put(2,R.drawable.star);
        imageMap.put(3,R.drawable.caramella);
        imageMap.put(4,R.drawable.tugarin);
    }
    private void initializeBGcolourMap(){
        bgColourMap = new HashMap<>();
        bgColourMap.put(1,Color.parseColor("#2A8AA8"));
        bgColourMap.put(2,Color.parseColor("#FF5733"));
        bgColourMap.put(3,Color.parseColor("#FFB6C1"));
        bgColourMap.put(4,Color.parseColor("#FFD700"));

    }


}