package com.eos.spatialracoon.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.eos.spatialracoon.R;

public class GameOverActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_over);
		View racoon = findViewById(R.id.wasted_raccoon);
		View retry = findViewById(R.id.retry);
		retry(racoon);
		retry(retry);
	}

	private void retry(View retry) {
		retry.setOnClickListener(v -> this.startActivity(new Intent(this, GameActivity.class)));
	}

	//TODO: elena coger el marcador del usuario
	//TODO: poner el record
}