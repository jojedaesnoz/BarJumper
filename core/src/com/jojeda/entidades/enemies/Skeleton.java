package com.jojeda.entidades.enemies;

import com.jojeda.entidades.Character;
import com.jojeda.entidades.enemies.Enemy;

import java.util.Random;

import static com.jojeda.entidades.Character.Piece.*;
import static com.jojeda.entidades.Character.State.LEFT;
import static com.jojeda.entidades.Character.State.RIGHT;
import static com.jojeda.util.Constantes.ENEMY_SPEED;

public class Skeleton extends Enemy {


    public Skeleton(float x, float y) {
        super(x, y, 1);

        this.setPiece(BODY, "skeleton");
        if (new Random().nextBoolean()) {
            state = RIGHT;
            velocity.x = - ENEMY_SPEED;
        } else {
            state = LEFT;
            velocity.x = ENEMY_SPEED;
        }
        action = Character.Action.WALKCYCLE;
    }


    @Override
    public void update(float dt) {

    }

    @Override
    public void die() {

    }

    @Override
    public void resurrect() {

    }
}
