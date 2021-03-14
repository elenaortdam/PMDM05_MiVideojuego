package com.eos.spatialracoon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class StartActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		View racoon = findViewById(R.id.mapache_nave);
		//Button startButton = findViewById(R.id.start_button);
		racoon.setOnClickListener(v -> {
			this.startActivity(new Intent(this, GameActivity.class));
		});
	}

	//TODO: elena ocultar barra de arriba
	//TODO: elena cargar letras tipo flapoy birds
	//TODO: elena poner mapache
	//TODO: elena poner el fondo
	//TODO: elena animación mapache
	//TODO: elena record encima del mapache
	//TODO: elena botón instrucciones
	//TODO: elena texto "pulsa para comenzar"

}