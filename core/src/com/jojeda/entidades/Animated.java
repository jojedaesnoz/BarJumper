package com.jojeda.entidades;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Animated {

    public Vector2 position;
    public float stateTime;
    public TextureRegion currentFrame;
    public boolean dead;
    public Rectangle rect;

    public Animated(float x, float y) {
        position = new Vector2(x, y);
        rect = new Rectangle();
    }

    public void render(Batch batch) {
        if (currentFrame != null) {
            batch.draw(currentFrame, position.x, position.y);
        }
    }

    public abstract void update(float dt);

    public boolean isDead() {
        return dead;
    }

    public abstract void die();

    public abstract  void resurrect();
}
