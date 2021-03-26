package com.eos.spatialracoon.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.eos.spatialracoon.R;
import com.eos.spatialracoon.game.Game;

public class GameOverActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_over);
		View racoon = findViewById(R.id.wasted_raccoon);
		View retry = findViewById(R.id.retry);
		TextView record = findViewById(R.id.record);
		TextView score = findViewById(R.id.score);
		record.setText(String.valueOf(Game.getTopScore()));
		score.setText(String.valueOf(Game.getScore()));
		//TODO: cuando se da al retry reiniciar score, y nivel
		retry(racoon);
		retry(retry);
	}

	private void retry(View retry) {
		retry.setOnClickListener(v -> this.startActivity(new Intent(this, GameActivity.class)));
	}
}