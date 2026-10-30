package com.example.sensoresnavdrawer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        TextView logo = findViewById(R.id.txtLogo);

        logo.setAlpha(0f);
        logo.setScaleX(0.5f);
        logo.setScaleY(0.5f);

        logo.animate()
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(1500)
                .withEndAction(() -> {

                    new Handler(Looper.getMainLooper())
                            .postDelayed(() -> {

                                Intent intent = new Intent(
                                        SplashActivity.this,
                                        MainActivity.class);

                                startActivity(intent);
                                finish();

                            }, 1000);
                })
                .start();
    }
}
