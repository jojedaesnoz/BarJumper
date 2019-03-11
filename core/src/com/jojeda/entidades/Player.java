package com.jojeda.entidades;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.jojeda.managers.SpriteManager;
import com.jojeda.util.Constantes;

import static com.jojeda.entidades.Character.Action.*;
import static com.jojeda.entidades.Character.Piece.*;
import static com.jojeda.util.Constantes.*;

public class Player extends Character {

    Animation<TextureRegion> rightAnimation, leftAnimation, upAnimation;

    public Player(float x, float y, int lives, Vector2 velocity) {
        super(x, y, lives, velocity);

        this.setPiece(BELT, "leather")
                .setPiece(TORSO, "leather_armor_torso")
                .setPiece(LEGS, "pants_greenish")
                .setPiece(FEET, "shoes_brown")
                .setPiece(BODY, "male")
                .setPiece(HEAD, "leather_armor_hat");
        action = SPELLCAST;
    }


    public void jump() {
        velocity.y = Constantes.JUMPING_SPEED;
        isJumping = true;

        // todo: poner sonido al salto
//        if (Configuracion.isSoundEnabled())
//            R.getSound("salto.mp3");
    }



    @Override
    public void update(float dt) {

    }

    @Override
    public void die() {
        if (!isDead()) {
            // Sonido muerte
            lives--;
            dead = true;
            action = HURT;
            velocity.y = JUMPING_SPEED;
            velocity.x = 0;
        }
    }

    @Override
    public void resurrect() {
        dead = false;
        action = WALKCYCLE;
        velocity.y = 0;
    }
}
