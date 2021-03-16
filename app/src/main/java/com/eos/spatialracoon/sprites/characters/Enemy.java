package com.eos.spatialracoon.sprites.characters;

import com.eos.spatialracoon.Game;
import com.eos.spatialracoon.Level;
import com.eos.spatialracoon.constants.Figure;

import java.util.List;

public class Enemy {

	private int speed;
	private float x, y;
	private List<Figure> figures;
	private Level level;

	private int lifes;
	private Game game;
	private String left, right; //algoritmo para determinar si viene de la derecha o izquierda

}
