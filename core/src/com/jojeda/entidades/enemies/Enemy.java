package com.jojeda.entidades.enemies;

import com.badlogic.gdx.math.Vector2;
import com.jojeda.entidades.Character;

import static com.jojeda.entidades.Character.Piece.*;
import static com.jojeda.entidades.Character.Piece.HEAD;
import static com.jojeda.util.Constantes.ENEMY_SPEED;

public abstract class Enemy extends Character {


    public Enemy(float x, float y, int lives) {
        super(x, y, lives, new Vector2(ENEMY_SPEED, 0));
    }
}
