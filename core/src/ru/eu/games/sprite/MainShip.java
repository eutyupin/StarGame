package ru.eu.games.sprite;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.eu.games.base.BaseShip;
import ru.eu.games.math.Rect;

public class MainShip extends BaseShip {

    private static final float HEIGHT = 0.1f;
    private static final float PADDING = 0.01f;
    private static final float MOVE_SCALE = 0.01f;
    private Rect worldBounds;
    private TextureRegion mainShipNormal;
    private TextureRegion mainShipShooted;
    private Texture atlasRegion;

    public MainShip(TextureAtlas atlas) {
        super(atlas.findRegion("main_ship"));
        atlasRegion = atlas.findRegion("main_ship").getTexture();
        mainShipNormal = new TextureRegion(atlasRegion, 916, 95,195,297);
        mainShipShooted = new TextureRegion(atlasRegion, 1111,95,195,297);
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(HEIGHT);
        setBottom(worldBounds.getBottom() + PADDING);
        this.worldBounds = worldBounds;
    }

    @Override
    public boolean touchDragged(Vector2 touch, int pointer) {
        if (isMe(touch)) {
            pos.x = touch.x;
        }
        return super.touchDragged(touch, pointer);
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(
                mainShipNormal,
                getLeft(), getBottom(),
                halfWidth, halfHeight,
                getWidth(), getHeight(),
                scale, scale,
                angle
        );
        checkBounds(this.getLeft(), this.getRight(), worldBounds);
    }

    private void checkBounds(float left, float right, Rect worldBounds) {
        if (left <= worldBounds.getLeft()) this.setLeft(worldBounds.getLeft());
        if (right >= worldBounds.getRight()) this.setRight(worldBounds.getRight());
    }

    @Override
    public void keyDown(int keycode) {
        if (keycode == Keys.LEFT) {
                setLeft(getLeft() - MOVE_SCALE);
        }
        if(keycode == Keys.RIGHT) {
            setLeft(getLeft() + MOVE_SCALE);
        }
    }

    @Override
    public void keyUp(int keycode) {

    }
}