package com.example.clicker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView scoreField;
    private ImageButton clickerBtn;
    private Button themeSwitchMenuBtn;

    private static final String SCORE_KEY = "score";
    private int score;
    private List<String> themes = new ArrayList<>(Arrays.asList("firstLevel","secondLevel"));
    private String curTheme;

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

        if (savedInstanceState != null) {
            score = savedInstanceState.getInt(SCORE_KEY);
            String curScore = String.valueOf(score);
            scoreField.setText(curScore);
        }

        scoreField = findViewById(R.id.scoreField);

        themeSwitchMenuBtn = findViewById(R.id.themeSwitchMenuBtn);
        clickerBtn = findViewById(R.id.clickerBtn);



        clickerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                score+=1;
                String curScore = String.valueOf(score);
                scoreField.setText(curScore);
            }
        });

        themeSwitchMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToTeThemeMenuActivity();

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
}