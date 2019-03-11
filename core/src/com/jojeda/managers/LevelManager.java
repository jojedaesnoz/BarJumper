package com.jojeda.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.jojeda.entidades.StandingNPC;
import com.jojeda.entidades.Player;
import com.jojeda.entidades.enemies.FallingMan;
import com.jojeda.entidades.enemies.Skeleton;

import javax.management.MXBean;
import java.io.File;
import java.io.FileFilter;

import static com.jojeda.util.Constantes.*;

public class LevelManager {
    public static final int MAX_LEVEL;
    private SpriteManager spriteManager;
    TiledMap map;
    TiledMapTileLayer collisionLayer;
    MapLayer objectLayer;
    public OrthogonalTiledMapRenderer mapRenderer;
    public Batch batch;
    public Rectangle goal;
    public int currentLevel;
    TiledMapTileMapObject playerSpawn;

    static {
        File[] levelFiles = new File("levels").listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().contains("level");
            }
        });
        MAX_LEVEL = levelFiles != null ? levelFiles.length : 0;
    }

    public LevelManager(SpriteManager spriteManager, int currentLevel) {
        this.spriteManager = spriteManager;
        this.currentLevel = currentLevel <= MAX_LEVEL ? currentLevel : MAX_LEVEL;
    }

    public void loadCurrentLevel() {
        loadLevel();
        loadPlayer();
        loadNPCS();
        loadGoal();
        loadEnemies();
        playCurrentLevelMusic();
    }

    private void playCurrentLevelMusic() {
        if (Configuracion.isMusicEnabled()) {
            spriteManager.music = Gdx.audio.newMusic(Gdx.files.internal("musics/background_music.ogg"));
            spriteManager.music.setLooping(true);
            spriteManager.music.setVolume(.2f);
            spriteManager.music.play();
        }
    }

    private void loadNPCS() {
        for (MapObject mapObject : objectLayer.getObjects()) {
            if (mapObject.getProperties().containsKey("type")) {
                if (mapObject.getProperties().get("type").equals("standing_npc")) {
                    TiledMapTileMapObject npc = (TiledMapTileMapObject)mapObject;
                    spriteManager.npcs.add(new StandingNPC(npc.getX(), npc.getY()));
                }
            }
        }
    }

    public void restartCurrentLevel() {
        spriteManager.enemies.clear();
        spriteManager.tiles.clear();

        spriteManager.player.position.set(playerSpawn.getX(), playerSpawn.getY());
        spriteManager.player.resurrect();
        loadEnemies();
    }

    private void loadEnemies() {
        MapLayer enemyLayer = map.getLayers().get("enemies");
        for (MapObject mapObject : enemyLayer.getObjects()) {
            TiledMapTileMapObject enemyTile = (TiledMapTileMapObject) mapObject;
            String enemyType = (String) enemyTile.getProperties().get("type");
            switch (enemyType) {
                case "skeleton":
                    spriteManager.enemies.add(new Skeleton(enemyTile.getX(), enemyTile.getY()));
                    break;
                case "generator":
                    spriteManager.enemies.add(new FallingMan(enemyTile.getX(), enemyTile.getY()));
                    break;
            }
        }
    }

    private void loadLevel() {
        map = new TmxMapLoader().load("levels" + File.separator + "level" + currentLevel + ".tmx");
        collisionLayer = (TiledMapTileLayer) map.getLayers().get("terrain");
        objectLayer = map.getLayers().get("objects");
        mapRenderer = new OrthogonalTiledMapRenderer(map);
        batch = mapRenderer.getBatch();
    }

    public void loadPlayer() {
        playerSpawn = ((TiledMapTileMapObject) objectLayer.getObjects().get("player_spawn"));
        spriteManager.player = new Player(playerSpawn.getX(), playerSpawn.getY(), 2, new Vector2(0, 0));
    }

    public void loadGoal() {
        TiledMapTileMapObject aux = ((TiledMapTileMapObject) objectLayer.getObjects().get("goal"));
        goal = new Rectangle(aux.getX(), aux.getY(), TILE_WIDTH * 2, TILE_HEIGHT * 2);
    }
}
