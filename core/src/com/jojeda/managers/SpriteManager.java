package com.jojeda.managers;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.loaders.BitmapFontLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.jojeda.entidades.Character;
import com.jojeda.entidades.StandingNPC;
import com.jojeda.entidades.enemies.Enemy;
import com.jojeda.entidades.Player;
import com.jojeda.entidades.enemies.FallingMan;
import com.jojeda.entidades.items.Item;
import com.jojeda.pantallas.PantallaFIN;
import com.jojeda.pantallas.PantallaGameOver;
import com.jojeda.pantallas.PantallaJuego;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;

import static com.jojeda.entidades.Character.Action.IDLE;
import static com.jojeda.entidades.Character.Action.SPELLCAST;
import static com.jojeda.entidades.Character.State.*;
import static com.jojeda.util.Constantes.*;

public class SpriteManager {

    public Array<Rectangle> tiles;
    public Array<Enemy> enemies;
    public Array<Item> items;
    public Array<StandingNPC> npcs;
    public LevelManager levelManager;
    public Player player;
    public Music music;
    public HashMap<String, Sound> sounds;
    public BitmapFont font;

    {
        tiles = new Array<>();
        enemies = new Array<>();
        items = new Array<>();
        npcs = new Array<>();
        sounds = new HashMap<>();

        font = new BitmapFont();

        if (Configuracion.isSoundEnabled()) {
            sounds.put(JUMP_SOUND, Gdx.audio.newSound(Gdx.files.internal("sounds" + File.separator + "jump.ogg")));
            sounds.put(DEATH_SOUND, Gdx.audio.newSound(Gdx.files.internal("sounds" + File.separator + "death.ogg")));
            sounds.put(FALL_SOUND, Gdx.audio.newSound(Gdx.files.internal("sounds" + File.separator + "fall.wav")));
            sounds.put(SCREAM_SOUND, Gdx.audio.newSound(Gdx.files.internal("sounds" + File.separator + "scream.wav")));
        }
    }

    public void handleInput() {
        // Movimiento horizontal
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            player.velocity.x = PLAYER_SPEED;
            player.state = Character.State.RIGHT;
            player.action = Character.Action.WALKCYCLE;
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            player.velocity.x = - PLAYER_SPEED;
            player.state = Character.State.LEFT;
            player.action = Character.Action.WALKCYCLE;
        } else {
            player.velocity.x = 0;
            player.action = IDLE;
            player.state = DOWN;
        }

        // Saltos
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            if (!player.isJumping) {
                player.jump();
                if (Configuracion.isSoundEnabled()) {
                    sounds.get(JUMP_SOUND).play();
                }
            }
        }
    }

    public void update(float dt) {
        updatePlayer(dt);
        updateEnemies(dt);
        updateNPCS(dt);
    }

    private void updateNPCS(float dt) {
        for (StandingNPC npc : npcs) {
            npc.update(dt, this);
            if (npc.rect.overlaps(player.rect)) {
                if (!npc.isOverlapped()) {
                    if (npc.index < npc.frases.length - 1) {
                        npc.index++;
                    } else {
                        npc.index = 0;
                    }
                    npc.overlapped = true;
                }
            } else {
                npc.overlapped = false;
            }
        }
    }


    private void updateEnemies(float dt) {
        Iterator<Enemy> iterEnemies = enemies.iterator();
        while (iterEnemies.hasNext()) {
            Enemy enemy = iterEnemies.next();

            enemy.update(dt, this);

            if (enemy.position.y <= 0 && !enemy.isDead()) {
                enemy.die();
                if (enemy instanceof FallingMan && Configuracion.isSoundEnabled()) {
                    sounds.get(FALL_SOUND).play(0.1f);
                }
            }

            // Si no ha resucitado al morir, lo quita de memoria
            if (enemy.isDead()) {
                iterEnemies.remove();
                continue;
            }

            if (enemy.rect.overlaps(player.rect) && !player.isDead()) {
                player.die();
                sounds.get(SCREAM_SOUND).play();
            }
        }
    }

    private void updatePlayer(float dt) {
        player.update(dt, this);

        if (player.position.y <= 0 && !player.isDead()) {
            player.die();
        }

        if (levelManager.goal != null) {
            if (player.rect.overlaps(levelManager.goal)) {
                if (Configuracion.isMusicEnabled()) {
                    music.stop();
                }
                Game juego = ((Game) (Gdx.app.getApplicationListener()));
                if (levelManager.currentLevel < LevelManager.MAX_LEVEL) {
                    juego.getScreen().dispose();
                    juego.setScreen(new PantallaJuego(levelManager.currentLevel + 1));
                } else {
                    juego.getScreen().dispose();
                    juego.setScreen(new PantallaFIN());
                }
            }
        }

        if ((player.isDead()) && (player.position.y < -player.rect.height)) {
            if (player.lives > 0) {
                levelManager.restartCurrentLevel();
            } else {
                if (Configuracion.isMusicEnabled()) {
                    music.stop();
                }

                if (Configuracion.isSoundEnabled()) {
                    sounds.get(DEATH_SOUND).play();
                }

                ((Game)(Gdx.app.getApplicationListener())).setScreen(new PantallaGameOver(this));
            }
        }
    }

    public void getCollisionTiles(Character character, int startX, int endX, int startY, int endY) {
        tiles.clear();

        int xCell, yCell;
        for (int y = startY; y <= endY; y++) {
            for (int x = startX; x <= endX; x++) {
                xCell = (int) (x / TILE_WIDTH);
                yCell = (int) (y / TILE_HEIGHT);
                TiledMapTileLayer.Cell cell = levelManager.collisionLayer.getCell(xCell, yCell);
                if ((cell != null) ) {
                    Rectangle rect = new Rectangle();
                    rect.set((int) (Math.floor(x / TILE_WIDTH) * TILE_WIDTH),
                            (int) (Math.floor(y / TILE_HEIGHT) * TILE_HEIGHT),
                            TILE_WIDTH, TILE_HEIGHT);

                    tiles.add(rect);

                    if (cell.getTile().getProperties().containsKey("death")) {
                        if (character instanceof Player) {
                            character.die();
                            break;
                        }
                    }
                }
            }
        }
    }
}
