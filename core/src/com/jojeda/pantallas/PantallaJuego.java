package com.jojeda.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.jojeda.entidades.Character;
import com.jojeda.entidades.StandingNPC;
import com.jojeda.entidades.enemies.Enemy;
import com.jojeda.managers.LevelManager;
import com.jojeda.managers.SpriteManager;

import static com.jojeda.util.Constantes.*;

public class PantallaJuego implements Screen {

    private Batch spriteBatch;
    SpriteManager spriteManager;
    LevelManager levelManager;
    OrthographicCamera camera;

    public PantallaJuego(int level) {
        spriteManager = new SpriteManager();
        levelManager = new LevelManager(spriteManager, level);
        levelManager.loadCurrentLevel();
        spriteManager.levelManager = levelManager;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, TILES_IN_CAMERA_WIDTH * TILE_WIDTH, TILES_IN_CAMERA_HEIGHT * TILE_HEIGHT);
        camera.update();


        // todo: crear cameraManager
        levelManager.mapRenderer.setView(camera);
        spriteBatch = levelManager.batch;

    }


    private void handleCamera() {
        if (spriteManager.player.position.x < TILES_IN_CAMERA_WIDTH * TILE_WIDTH / 2) {
            camera.position.set(TILES_IN_CAMERA_WIDTH * TILE_WIDTH / 2,
                    TILES_IN_CAMERA_HEIGHT * TILE_WIDTH / 2 - TILE_WIDTH, 0);
        } else {
            camera.position.set(spriteManager.player.position.x,
                    TILES_IN_CAMERA_HEIGHT * TILE_WIDTH / 2 - TILE_WIDTH, 0);
        }


        camera.update();
        levelManager.mapRenderer.setView(camera);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0.3f, 0.6f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        spriteManager.handleInput();
        handleCamera();

        levelManager.mapRenderer.render();

        spriteManager.update(delta);

        spriteBatch.begin();
        spriteManager.player.render(spriteBatch);
        for (Enemy enemy : spriteManager.enemies)
            enemy.render(spriteBatch);

        for (StandingNPC npc : spriteManager.npcs) {
            npc.render(spriteBatch);
        }
        spriteManager.font.draw(spriteBatch, "Vidas: " + spriteManager.player.lives,
                3 * TILE_WIDTH, (TILES_IN_CAMERA_HEIGHT - 2) * TILE_HEIGHT);
        spriteBatch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
