package ru.eu.games.screen;

import com.badlogic.gdx.Game;
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
import ru.eu.games.sprite.Star;

public class MenuScreen extends BaseScreen {

    private static final int STAR_COUNT = 256;

    private final Game game;

    private TextureAtlas atlas;
    private Texture bg;
    private AnimatedShip animatedShip;
    private float animatedShipHeight = 0.1f;


    private Background background;
    private Star[] stars;

    private ExitButton exitButton;
    private PlayButton playButton;

    public MenuScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        atlas = new TextureAtlas("textures/menuAtlas.tpack");
        bg = new Texture("textures/bg.png");
        background = new Background(bg);
        animatedShip = new AnimatedShip(atlas, worldBounds);
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
//        animatedShip.setRight(worldBounds.getRight());
//        animatedShip.setTop(worldBounds.getTop());
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
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        exitButton.touchDown(touch, pointer, button);
        playButton.touchDown(touch, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        exitButton.touchUp(touch, pointer, button);
        playButton.touchUp(touch, pointer, button);
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
        exitButton.draw(batch);
        playButton.draw(batch);
        shipAnimateDraw(batch);
        batch.end();

    }

    private void shipAnimateDraw(SpriteBatch batch) {
        animatedShip.draw(batch);
        if (animatedShip.pos.y > 0) {
            animatedShip.pos.sub(0.004f,0.008f);
            animatedShipHeight += 0.003f;
            animatedShip.setHeightProportion(animatedShipHeight);
        }
    }

}
