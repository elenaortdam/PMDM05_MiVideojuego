package com.eos.spatialracoon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class GameOverActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_over);
		View racoon = findViewById(R.id.game_over);
		//Button startButton = findViewById(R.id.start_button);
		racoon.setOnClickListener(v -> this.startActivity(new Intent(this, GameActivity.class)));

	}

	//TODO: elena coger el marcador del usuario
	//TODO: poner el record
	//TODO: elena poner el fondo del mapache muerto
	//TODO: elena botón de reintentar
	//TODO: elena hacer lógica para cargar el juego

}