package com.eos.spatialracoon.activities;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.eos.spatialracoon.Game;

public class GameActivity extends AppCompatActivity {

	Game game;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		game = new Game(this);
		hideSystemUI();
		setContentView(game);

	}

// TODO: elena cargar fondo sin cosas (I)
// TODO: elena musica al pulsar el botón
//	TODO: elena musica de fondo (?)
//	TODO: música al explotar enemigo
//	TODO: animación explotar enemigo

	private void hideSystemUI() {
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
			//A partir de kitkat
			game.setSystemUiVisibility(
					View.SYSTEM_UI_FLAG_LAYOUT_STABLE
							| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
							| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
							| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
							| View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
							| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
			//cuando se presiona volumen, por ejemplo, se cambia la visibilidad, hay que volver
			//a ocultar
			game.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
				@Override
				public void onSystemUiVisibilityChange(int visibility) {
					hideSystemUI();
				}
			});
		}
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB &&
				Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
			// Pre-Jelly Bean, hay que ocultar la action bar
			getActionBar().hide();
		}
		getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}

	//TODO: elena FINAL refactor

}