package ru.eu.games.base;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public abstract class BaseShip extends Sprite{

    public BaseShip(TextureRegion region) {
        super(region);
    }

    public abstract void keyDown(int keycode);
    public abstract void keyUp(int keycode);
}
