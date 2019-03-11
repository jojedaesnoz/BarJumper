package com.jojeda.entidades;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.jojeda.entidades.enemies.Skeleton;
import com.jojeda.managers.SpriteManager;
import com.jojeda.util.Constantes;

import java.io.File;
import java.util.HashMap;

import static com.jojeda.entidades.Character.State.*;
import static com.jojeda.entidades.Character.Action.*;
import static com.jojeda.util.Constantes.*;

public abstract class Character extends Animated {

    public enum State {
        RIGHT, LEFT, UP, DOWN
    }

    public enum Action {
        BOW, HURT, SLASH, SPELLCAST, THRUST, WALKCYCLE, IDLE
    }

    public enum Piece {
        BEHIND, BODY, FEET, LEGS, TORSO, BELT, HEAD, HANDS, WEAPON
    }

    public Vector2 velocity;
    public int lives;
    public boolean isJumping;
    public Array<Animation<TextureRegion>> animations;
    public State state;
    public Action action;
    public static HashMap<State, String> states;
    public static HashMap<Action, String> actions;
    public HashMap<Piece, String> pieces;


    static {
        states = new HashMap<>();
        states.put(UP, "part00.png");
        states.put(LEFT, "part01.png");
        states.put(DOWN, "part02.png");
        states.put(RIGHT, "part03.png");

        actions = new HashMap<>();
        actions.put(BOW, "bow");
        actions.put(HURT, "hurt");
        actions.put(SLASH, "slash");
        actions.put(SPELLCAST, "spellcast");
        actions.put(THRUST, "thrust");
        actions.put(WALKCYCLE, "walkcycle");
        actions.put(IDLE, "walkcycle");
    }

    public Character(float x, float y, int lives, Vector2 velocity) {
        super(x, y);
        this.velocity = velocity;
        this.lives = lives;
        isJumping = false;
        animations = new Array<>();
        pieces = new HashMap<>();
        rect.set(x, y, 32, 64);

        state = DOWN;
        action = IDLE;
    }

    public void checkCollisions(SpriteManager spriteManager) {
        checkCollisionsY(spriteManager);
        checkCollisionsX(spriteManager);
    }

    private void checkCollisionsY(SpriteManager spriteManager) {
        int startY, endY, startX, endX;

        startX = (int) position.x;
        endX = (int) (position.x + rect.width);

        startY = endY = velocity.y > 0 ?
                (int) (position.y + rect.height + velocity.y) :
                (int) (position.y + velocity.y);

        spriteManager.getCollisionTiles(this, startX, endX, startY, endY);

        rect.y += velocity.y;
        for (Rectangle tile : spriteManager.tiles) {
            if (rect.overlaps(tile)) {
                verticalCollision(tile);
                return;
            }
        }
    }

    protected void verticalCollision(Rectangle tile) {
        if (velocity.y > 0) {
            position.y  = tile.y - rect.getHeight();
        } else {
            position.y = tile.y + TILE_HEIGHT;
            isJumping = false;
        }

        velocity.y = 0;
        rect.y = position.y;
    }

    private void checkCollisionsX(SpriteManager spriteManager) {
        int startY, endY, startX, endX;

        startX = endX = velocity.x > 0 ?
                (int) (position.x + rect.width + velocity.x) :
                (int) (position.x + velocity.x);

        startY = (int) position.y;
        endY = (int) (position.y + rect.height);

        spriteManager.getCollisionTiles(this, startX, endX, startY, endY);
        rect.x += velocity.x;

        for (Rectangle tile : spriteManager.tiles) {
            if (rect.overlaps(tile)) {
                horizontalCollision(tile);
                return;
            }
        }

        rect.x = position.x;
    }

    protected void horizontalCollision(Rectangle tile) {
        if (this instanceof Skeleton) {
            state = state.equals(LEFT) ? RIGHT : LEFT;
            velocity.x = -velocity.x;
        } else {
            velocity.x = 0;
        }
    }

    public Character setPiece(Piece piece, String name) {
        pieces.put(piece, name);
        return this;
    }

    public void update(float dt, SpriteManager spriteManager) {
        stateTime += dt;

        // actualizar posicion y comprobar colisiones
        velocity.y -= Constantes.GRAVITY * dt;
        velocity.scl(dt);
        rect.x = position.x;
        rect.y = position.y;

        if (!isDead()) {
            checkCollisions(spriteManager);
        }

        velocity.scl(1/dt);
        position.add(velocity);

        // Comprobar bordes
        if (position.x < 0) {
            position.x = 0;
        }

//        if (position.y < 0 && !isDead()) {
//            die();
//        }

        updateAnimations();
    }

    private void updateAnimations() {
        animations.clear();
        for (Piece piece : Piece.values()) {
            if (pieces.containsKey(piece)) {
                Array<TextureRegion> animationFrames = new Array<>();
                Texture sprites = new Texture(
                        SPRITES + File.separator + actions.get(action)
                                + File.separator + piece + "_" + pieces.get(piece)
                                + File.separator + states.get(state));

                TextureRegion[][] tmp = TextureRegion.split(sprites, sprites.getWidth() / 9, sprites.getHeight());
                if (action.equals(IDLE)) {
                    animationFrames.add(tmp[0][0]);
                } else {
                    for (TextureRegion[] textureRegions : tmp)
                        for (TextureRegion textureRegion : textureRegions) animationFrames.add(textureRegion);
                }
                animations.add(new Animation<>(0.15f, animationFrames));
            }
        }
    }

    @Override
    public void render(Batch batch) {
        for (Animation<TextureRegion> animation : animations) {
            // Hay que pintar los personajes ligeramente a la izquierda porque las texturas no estan bien cuadradas
            batch.draw(animation.getKeyFrame(stateTime, true), position.x - rect.width/2, position.y);
        }
    }
}
