package com.eos.spatialracoon.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.eos.spatialracoon.R;

public class StartActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		ImageView raccoon = findViewById(R.id.mapache_nave);
		raccoon.setOnClickListener(v -> this.startActivity(new Intent(this, GameActivity.class)));
		TextView record = findViewById(R.id.record_start);
		SharedPreferences sharedPreferences = this.getSharedPreferences(getResources().getString(R.string.app_name),
																		Context.MODE_PRIVATE);
		int topScore = sharedPreferences.getInt("SCORE", 0);
		record.setText(String.valueOf(topScore));
		ImageView touch = findViewById(R.id.touch);

		TranslateAnimation clickAnimation = new TranslateAnimation(0, -80,
																   0, 80);
		clickAnimation.setDuration(1500);  // duración de la animación
		clickAnimation.setRepeatCount(Animation.INFINITE);
		clickAnimation.setRepeatMode(Animation.REVERSE);
		touch.startAnimation(clickAnimation);

		TranslateAnimation raccoonAnimation = new TranslateAnimation(0, 0,
																	 0, 50);
		raccoonAnimation.setDuration(1000);  // duración de la animación
		raccoonAnimation.setRepeatCount(Animation.INFINITE);
		raccoonAnimation.setRepeatMode(Animation.REVERSE);
		raccoon.startAnimation(raccoonAnimation);

	}
	//TODO: elena botón instrucciones
}