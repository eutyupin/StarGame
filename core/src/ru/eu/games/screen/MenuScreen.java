package ru.eu.games.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.eu.games.base.BaseScreen;
import ru.eu.games.math.Rect;
import ru.eu.games.sprite.AnimatedShip;
import ru.eu.games.sprite.Background;
import ru.eu.games.sprite.ExitButton;
import ru.eu.games.sprite.PlayButton;
import ru.eu.games.sprite.Stamp;
import ru.eu.games.sprite.Star;

public class MenuScreen extends BaseScreen {

    private static final int STAR_COUNT = 256;

    private final Game game;

    private TextureAtlas atlas;
    private Texture bg;
    private AnimatedShip animatedShip;
    private float animatedShipHeight = 0.1f;
    private float animatedStampHeight = 0.5f;
    private Sound falconSound;
    private Vector2 shipDirection;
    private boolean canButtonsDraw;



    private Background background;
    private Stamp animatedStamp;
    private Star[] stars;

    private ExitButton exitButton;
    private PlayButton playButton;

    public MenuScreen(Game game) {
        this.game = game;
        shipDirection = new Vector2(0.004f,0.008f);
        canButtonsDraw = false;
    }

    @Override
    public void show() {
        super.show();
        atlas = new TextureAtlas("textures/menuAtlas.tpack");
        bg = new Texture("textures/bg.jpg");
        animatedStamp = new Stamp(atlas, worldBounds);
        background = new Background(bg);
        animatedShip = new AnimatedShip(atlas);
        falconSound = Gdx.audio.newSound(Gdx.files.internal("sounds/falcon.mp3"));
        stars = new Star[STAR_COUNT];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(atlas);
        }
        exitButton = new ExitButton(atlas);
        playButton = new PlayButton(atlas, game);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
        exitButton.resize(worldBounds);
        playButton.resize(worldBounds);
        animatedShip.resize(worldBounds);
        animatedStamp.resize(worldBounds);
        falconSound.play();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();
    }

    @Override
    public void dispose() {
        super.dispose();
        bg.dispose();
        atlas.dispose();
        falconSound.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if (canButtonsDraw) {
            exitButton.touchDown(touch, pointer, button);
            playButton.touchDown(touch, pointer, button);
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (canButtonsDraw) {
            exitButton.touchUp(touch, pointer, button);
            playButton.touchUp(touch, pointer, button);
        }

        return false;
    }

    private void update(float delta) {
        for (Star star : stars) {
            star.update(delta);
        }
    }

    private void draw() {
        batch.begin();
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        shipAnimateDraw(batch);
        stampAnimateDraw(batch);
        if (canButtonsDraw) {
            exitButton.draw(batch);
            playButton.draw(batch);
        }
        batch.end();

    }

    private void shipAnimateDraw(SpriteBatch batch) {
        animatedShip.draw(batch);
        if (animatedShip.pos.y > worldBounds.pos.y) {
            animatedShip.pos.sub(shipDirection);
            animatedShipHeight += 0.003f;
            animatedShip.setHeightProportion(animatedShipHeight);
        } else canButtonsDraw = true;
    }
    private void stampAnimateDraw(SpriteBatch batch) {
        animatedStamp.draw(batch);
        if (animatedStamp.getHeight() > 0.2f) {
            animatedStampHeight -= 0.01f;
            animatedStamp.setHeightProportion(animatedStampHeight);
        }
    }
}