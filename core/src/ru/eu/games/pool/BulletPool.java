package ru.eu.games.pool;

import ru.eu.games.base.SpritesPool;
import ru.eu.games.sprite.Bullet;

public class BulletPool extends SpritesPool<Bullet> {
    @Override
    protected Bullet newObject() {
        return new Bullet();
    }
}
