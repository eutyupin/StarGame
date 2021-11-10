package ru.eu.games.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import java.util.List;

import ru.eu.games.base.BaseScreen;
import ru.eu.games.base.Font;
import ru.eu.games.math.Rect;
import ru.eu.games.pool.BulletPool;
import ru.eu.games.pool.EnemyPool;
import ru.eu.games.pool.ExplosionPool;
import ru.eu.games.sprite.Background;
import ru.eu.games.sprite.Bullet;
import ru.eu.games.sprite.EnemyShip;
import ru.eu.games.sprite.ExitButton;
import ru.eu.games.sprite.GameOver;
import ru.eu.games.sprite.MainShip;
import ru.eu.games.sprite.NewGameButton;
import ru.eu.games.sprite.Star;
import ru.eu.games.util.EnemyEmitter;

public class GameScreen extends BaseScreen {

    private static final int STAR_COUNT = 64;
    private static final String FRAGS = "destroyed: ";
    private static final String HP = "shield: ";
    private static final String LEVEL = "level: ";
    private static final float FONT_SIZE = 0.014f;
    private static final float MARGIN = 0.005f;

    private StringBuilder sbFrags, sbHP, sbLevel;
    private Font font;
    private Game game;

    private TextureAtlas atlas;
    private Texture bg;
    private Background background;
    private GameOver gameOver;
    private NewGameButton newGameButton;
    private ExitButton exitButton;

    private Star[] stars;
    private BulletPool bulletPool;
    private ExplosionPool explosionPool;
    private EnemyPool enemyPool;
    private boolean isGameOver = false;

    private MainShip mainShip;
    private int frags = 0;

    private Music music;
    private Sound laserSound;
    private Sound bulletSound;
    private Sound explosionSound;
    private Sound gameOverSound;

    private EnemyEmitter enemyEmitter;

    public GameScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music _asteroid.mp3"));
        music.setLooping(true);
        music.play();
        laserSound = Gdx.audio.newSound(Gdx.files.internal("sounds/quadlaser.mp3"));
        bulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/xwingfire.mp3"));
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion_sound.mp3"));
        gameOverSound = Gdx.audio.newSound(Gdx.files.internal("sounds/hyperdrive_trouble.mp3"));
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        bg = new Texture("textures/bg.jpg");
        background = new Background(bg);
        gameOver = new GameOver(atlas);
        newGameButton = new NewGameButton(atlas);
        exitButton = new ExitButton(atlas);
        stars = new Star[STAR_COUNT];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(atlas);
        }
        bulletPool = new BulletPool();
        explosionPool = new ExplosionPool(atlas, explosionSound);
        enemyPool = new EnemyPool(bulletPool, explosionPool, worldBounds, bulletSound);

        mainShip = new MainShip(atlas, bulletPool, explosionPool, laserSound);

        enemyEmitter = new EnemyEmitter(enemyPool, worldBounds, atlas);
        sbFrags = new StringBuilder();
        sbHP = new StringBuilder();
        sbLevel = new StringBuilder();
        font = new Font("fonts/jedi_font.fnt", "fonts/jedi_font.png");
        font.setSize(FONT_SIZE);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        checkCollisions();
        freeAllDestroyed();
        draw();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
        mainShip.resize(worldBounds);
        gameOver.resize(worldBounds);
        newGameButton.resize(worldBounds);
        exitButtonResize();
    }

    @Override
    public void dispose() {
        super.dispose();
        bg.dispose();
        atlas.dispose();
        bulletPool.dispose();
        explosionPool.dispose();
        enemyPool.dispose();
        music.dispose();
        laserSound.dispose();
        bulletSound.dispose();
        explosionSound.dispose();
        gameOverSound.dispose();
        font.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        mainShip.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        mainShip.keyUp(keycode);
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if (!mainShip.isDestroyed()) {
            mainShip.touchDown(touch, pointer, button);
        } else  {
            newGameButton.touchDown(touch, pointer, button);
            exitButton.touchDown(touch, pointer, button);
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (!mainShip.isDestroyed()) {
            mainShip.touchUp(touch, pointer, button);
        } else {
            newGameButton.touchUp(touch, pointer, button, this);
            exitButton.touchUp(touch, pointer, button);
        }
        return false;
    }

    private void update(float delta) {
        for (Star star : stars) {
            star.update(delta);
        }
        if (!mainShip.isDestroyed()) {
            bulletPool.updateActiveObjects(delta);
            enemyPool.updateActiveObjects(delta);
            mainShip.update(delta);
            enemyEmitter.generate(delta, frags);
        }
        explosionPool.updateActiveObjects(delta);
    }


    private void checkCollisions() {
        if (mainShip.isDestroyed()) {
            return;
        }
        List<EnemyShip> enemyShipList = enemyPool.getActiveObjects();
        for (EnemyShip enemyShip : enemyShipList) {
            float minDist = mainShip.getWidth();
            if (!enemyShip.isDestroyed()
                    && mainShip.pos.dst(enemyShip.pos) < minDist) {
                enemyShip.destroy();
                mainShip.damage(enemyShip.getDamage() * 2);
            }
        }
        List<Bullet> bulletList = bulletPool.getActiveObjects();
        for (Bullet bullet : bulletList) {
            if (bullet.isDestroyed()) {
                continue;
            }
            if (bullet.getOwner() != mainShip) {
                if (mainShip.isBulletCollision(bullet)) {
                    mainShip.damage(bullet.getDamage());
                    bullet.destroy();
                }
                continue;
            }
            for (EnemyShip enemyShip : enemyShipList) {
                if (enemyShip.isBulletCollision(bullet)) {
                    enemyShip.damage(bullet.getDamage());
                    if (enemyShip.isDestroyed()) frags++;
                    bullet.destroy();
                }
            }
        }
    }

    private void freeAllDestroyed() {
        bulletPool.freeAllDestroyed();
        explosionPool.freeAllDestroyed();
        enemyPool.freeAllDestroyed();
    }

    private void draw() {
        batch.begin();
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        if (!mainShip.isDestroyed()) {
            bulletPool.drawActiveObjects(batch);
            enemyPool.drawActiveObjects(batch);
            mainShip.draw(batch);
        } else {
            gameOverDraw(batch);
        }
        explosionPool.drawActiveObjects(batch);
        printInfo();
        batch.end();
    }

    private void printInfo() {
        sbFrags.setLength(0);
        font.draw(batch, sbFrags.append(FRAGS).append(frags), worldBounds.getLeft() + MARGIN,
                worldBounds.getTop() - MARGIN, Align.left);
        sbHP.setLength(0);
        font.draw(batch, sbHP.append(HP).append(mainShip.getHP()), worldBounds.getRight() - MARGIN,
                worldBounds.getTop() - MARGIN, Align.right);
        sbLevel.setLength(0);
        font.draw(batch, sbLevel.append(LEVEL).append(enemyEmitter.getLevel()), worldBounds.pos.x,
                worldBounds.getTop() - MARGIN, Align.center);
    }

    public void startNewGame() {
        frags = 0;
        bulletPool.freeAllActiveObjects();
        enemyPool.freeAllActiveObjects();
        explosionPool.freeAllActiveObjects();
        mainShip.resetShip();
        isGameOver = false;
        music.play();
    }

    private void gameOverDraw(SpriteBatch batch) {
        gameOver.draw(batch);
        newGameButton.draw(batch);
        exitButton.draw(batch);
        if (!isGameOver) {
            music.pause();
            gameOverSound.play();
            isGameOver = true;
        }
    }

    private void exitButtonResize() {
        exitButton.pos.x = worldBounds.pos.x;
        exitButton.setTop(newGameButton.getBottom() - newGameButton.getDefaultMARGIN());
        exitButton.setHeightProportion(newGameButton.getHeight());
    }
}
