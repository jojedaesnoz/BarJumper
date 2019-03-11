package com.jojeda.entidades.enemies;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.jojeda.managers.SpriteManager;

import static com.jojeda.entidades.Character.Piece.BODY;
import static com.jojeda.entidades.Character.State.DOWN;

public class FallingMan extends Enemy {

    private final float originalX;
    private final float originalY;

    public FallingMan(float x, float y) {
        super(x, y, 1);
        this.originalX = x;
        this.originalY = y;

        setPiece(BODY, "male");
        velocity = new Vector2();
        action = Action.WALKCYCLE;
        state = DOWN;
    }

    @Override
    public void update(float dt, SpriteManager spriteManager) {
        super.update(dt, spriteManager);
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void die() {
        resurrect();
    }

    @Override
    public void resurrect() {
        position.x = originalX;
        position.y = originalY;
        velocity = new Vector2(0, 0);
        dead = false;
    }

    @Override
    protected void verticalCollision(Rectangle tile) {

    }
}
