package ru.eu.games;

import com.badlogic.gdx.Game;

import ru.eu.games.screen.MenuScreen;

public class StarFighter extends Game {

	@Override
	public void create () {
		setScreen(new MenuScreen(this));
	}

}
