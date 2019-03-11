package com.jojeda.entidades.items;

import com.badlogic.gdx.math.Vector2;

public abstract class Item {

    public Vector2 position;

    public Item(float x, float y) {
        position = new Vector2(x, y);
    }
}
