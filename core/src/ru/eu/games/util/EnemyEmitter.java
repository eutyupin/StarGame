package ru.eu.games.util;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.eu.games.math.Rect;
import ru.eu.games.math.Rnd;
import ru.eu.games.pool.EnemyPool;
import ru.eu.games.sprite.EnemyShip;
import ru.eu.games.sprite.MainShip;

public class EnemyEmitter {

    private static final float GENERATE_INTERVAL = 4.3f;

    private static final float ENEMY_SMALL_HEIGHT = 0.08f;
    private static final float ENEMY_SMALL_BULLET_HEIGHT = 0.03f;
    private static final int ENEMY_SMALL_BULLET_DAMAGE = 1;
    private static final float ENEMY_SMALL_RELOAD_INTERVAL = 3f;
    private static final int ENEMY_SMALL_HP = 2;

    private static final float ENEMY_MEDIUM_HEIGHT = 0.15f;
    private static final float ENEMY_MEDIUM_BULLET_HEIGHT = 0.05f;
    private static final int ENEMY_MEDIUM_BULLET_DAMAGE = 3;
    private static final float ENEMY_MEDIUM_RELOAD_INTERVAL = 4f;
    private static final int ENEMY_MEDIUM_HP = 5;

    private static final float ENEMY_BIG_HEIGHT = 0.25f;
    private static final float ENEMY_BIG_BULLET_HEIGHT = 0.07f;
    private static final int ENEMY_BIG_BULLET_DAMAGE = 5;
    private static final float ENEMY_BIG_RELOAD_INTERVAL = 1f;
    private static final int ENEMY_BIG_HP = 10;
    private final MainShip mainShip;
    private float scaleWeight;

    private final EnemyPool enemyPool;
    private final Rect worldBounds;
    private final TextureRegion bulletRegion;

    private final TextureRegion[] enemySmallRegions;
    private final TextureRegion[] enemyMediumRegions;
    private final TextureRegion[] enemyBigRegions;

    private final Vector2 enemySmallV = new Vector2(0f, -0.2f);
    private final Vector2 enemyMediumV = new Vector2(0f, -0.03f);
    private final Vector2 enemyBigV = new Vector2(0f, -0.005f);
    private final Vector2 enemySmallBulletV = new Vector2(0f, -0.3f);
    private final Vector2 enemyMediumBulletV = new Vector2(0f, -0.25f);
    private final Vector2 enemyBigBulletV = new Vector2(0f, -0.3f);
    private float generateTimer;
    private int level;

    public EnemyEmitter(EnemyPool enemyPool, Rect worldBounds, TextureAtlas atlas, MainShip mainShip) {
        this.enemyPool = enemyPool;
        this.worldBounds = worldBounds;
        bulletRegion = atlas.findRegion("enemyBullet");
        enemySmallRegions = Regions.split(atlas.findRegion("enemyShip0"), 1, 2,2);
        enemyMediumRegions = Regions.split(atlas.findRegion("enemyShip1"), 1, 2,2);
        enemyBigRegions = Regions.split(atlas.findRegion("enemyShip2"), 1, 2,2);
        this.mainShip = mainShip;
    }

    public void generate(float delta, int frags) {
        int tempLevel = level;
        level = frags / 10 + 1;
        if (level > tempLevel && mainShip.getReloadInterval() > 0.15f) {
            mainShip.setReloadInterval(mainShip.getReloadInterval() - (scaleWeight / 20));
        }
        if (scaleWeight > 1.0f) scaleWeight = ((float) level) / 3;
        generateTimer += delta;
        if (generateTimer >= GENERATE_INTERVAL - scaleWeight) {
            generateTimer = 0f;
            EnemyShip enemy = enemyPool.obtain();
            float type = (float) Math.random();
            if (type < 0.5f) {
                enemy.set(
                        enemySmallRegions,
                        enemySmallV,
                        bulletRegion,
                        ENEMY_SMALL_BULLET_HEIGHT,
                        enemySmallBulletV,
                        ENEMY_SMALL_BULLET_DAMAGE * level,
                        ENEMY_SMALL_HP,
                        ENEMY_SMALL_RELOAD_INTERVAL,
                        ENEMY_SMALL_HEIGHT
                );
            } else if (type < 0.8f) {
                enemy.set(
                        enemyMediumRegions,
                        enemyMediumV,
                        bulletRegion,
                        ENEMY_MEDIUM_BULLET_HEIGHT,
                        enemyMediumBulletV,
                        ENEMY_MEDIUM_BULLET_DAMAGE * level,
                        ENEMY_MEDIUM_HP,
                        ENEMY_MEDIUM_RELOAD_INTERVAL,
                        ENEMY_MEDIUM_HEIGHT
                );
            } else {
                enemy.set(
                        enemyBigRegions,
                        enemyBigV,
                        bulletRegion,
                        ENEMY_BIG_BULLET_HEIGHT,
                        enemyBigBulletV,
                        ENEMY_BIG_BULLET_DAMAGE * level,
                        ENEMY_BIG_HP,
                        ENEMY_BIG_RELOAD_INTERVAL,
                        ENEMY_BIG_HEIGHT
                );
            }
            enemy.pos.x = Rnd.nextFloat(
                    worldBounds.getLeft() + enemy.getHalfWidth(),
                    worldBounds.getRight() - enemy.getHalfWidth()
            );
            enemy.setBottom(worldBounds.getTop());
        }
    }

    public int getLevel() {
        return level;
    }
}
