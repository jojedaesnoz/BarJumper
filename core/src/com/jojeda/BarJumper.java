package com.jojeda;

import com.badlogic.gdx.Game;
import com.jojeda.pantallas.SplashScreen;

//public class BarJumper extends ApplicationAdapter {
public class BarJumper extends Game {
	private static BarJumper instance = null;

	public static BarJumper getJuego() {
		if (instance == null) {
			instance = new BarJumper();
		}
		return instance;
	}

	
	@Override
	public void create () {
		setScreen(new SplashScreen());

	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {

	}

	public void inicializarJuego() {

	}
}
