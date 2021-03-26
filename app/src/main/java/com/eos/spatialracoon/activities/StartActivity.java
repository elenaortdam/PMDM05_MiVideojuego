package com.eos.spatialracoon.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.eos.spatialracoon.R;

public class StartActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		View racoon = findViewById(R.id.mapache_nave);
		racoon.setOnClickListener(v -> this.startActivity(new Intent(this, GameActivity.class)));
		TextView record = findViewById(R.id.record_start);
		SharedPreferences sharedPreferences = this.getSharedPreferences(getResources().getString(R.string.app_name),
																		Context.MODE_PRIVATE);
		int topScore = sharedPreferences.getInt("SCORE", 0);
		record.setText(String.valueOf(topScore));
	}

	//TODO: elena animación mapache (?)
	//TODO: elena record encima del mapache
	//TODO: elena botón instrucciones
	//TODO: elena animación click

}