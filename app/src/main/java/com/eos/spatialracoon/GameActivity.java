package com.eos.spatialracoon;

import android.os.Build;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {

	private GestureDetector gestureDetector;

	Game game;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		gestureDetector = new GestureDetector(this, new Gesture());
		game = new Game(this);
		hideSystemUI();
		setContentView(game);

	}

//	TODO:elena cargar fondo
//	TODO:elena cargar fondo 2,3 para dar sensacion de movimiento
//	TODO:elena cargar personaje principal
//	TODO:elena cargar botones
//	TODO:elena cargar asteroides random
//	TODO:elena hacer la lógica

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

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		//return super.onTouchEvent(event);
		return gestureDetector.onTouchEvent(event); //captura con detector de gestos
	}

}