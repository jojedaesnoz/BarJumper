package com.jojeda.entidades;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import static com.jojeda.entidades.Character.Piece.*;
import static com.jojeda.entidades.Character.Piece.HEAD;
import static com.jojeda.entidades.Character.State.DOWN;

public class StandingNPC extends Character {

    final BitmapFont bitmapFont;

    public final String[] frases = {
            "HEEEEEEEEEEY",
            "Cuidado con los esqueletos",
            "Hace buen día, ¿eh?",
            "Cuidado con los hombres cayendo",
            "El objetivo está por la derecha, ¿sabes?"
    };

    public int index;
    public boolean overlapped;


    public StandingNPC(float x, float y) {
        super(x, y, 1, new Vector2(0, 0));
        bitmapFont = new BitmapFont();

        this.setPiece(BELT, "leather")
                .setPiece(TORSO, "plate_armor_arms_shoulders")
                .setPiece(LEGS, "plate_armor_pants")
                .setPiece(FEET, "plate_armor_shoes")
                .setPiece(BODY, "skeleton")
                .setPiece(HEAD, "plate_armor_helmet");

        overlapped = false;
        state = DOWN;
    }

    public boolean isOverlapped() {
        return overlapped;
    }

    @Override
    public void render(Batch batch) {
        super.render(batch);
        bitmapFont.draw(batch, frases[index], position.x, position.y + rect.height);
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
