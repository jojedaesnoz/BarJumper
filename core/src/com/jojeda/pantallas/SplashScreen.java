package com.jojeda.pantallas;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.jojeda.managers.R;

public class SplashScreen implements Screen {
    private Texture splashTexture;
    private Image splashImage;
    private Stage stage;
    private boolean splashDone = false;

    public SplashScreen() {
        splashTexture = new Texture(Gdx.files.internal("badlogic.jpg"));
        splashImage = new Image(splashTexture);
    }

    @Override
    public void show() {
        stage = new Stage();
        Table table = new Table();
        table.setFillParent(true);
        table.center();

        // Muestra la imagen de SplashScreen como una animacion
        splashImage.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(1f), Actions.delay(1.5f), Actions.run(new Runnable() {
            @Override
            public void run() {
                splashDone = true;
            }
        })));
//        splashDone = true;

        table.row().height(splashTexture.getHeight());
        table.add(splashImage).center();
        stage.addActor(table);

        // Lanza la carga de recursos
        R.loadAllResources();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();

        // Comprueba si se han cargado los recursos
        if (R.update()) {
            // Si la animacion ha terminado se muestra el menu principal
            if (splashDone) {
                // TODO: lanzar la pantalla de menu principal
                ((Game) Gdx.app.getApplicationListener())
                        .setScreen(new PantallaMenuPrincipal());
//                ((Game) Gdx.app.getApplicationListener())
//                        .setScreen(new PantallaJuego());
            }
        }
//        R.finishLoading();
//        ((Game)(Gdx.app.getApplicationListener())).setScreen(new PantallaMenuPrincipal());
    }

    @Override
    public void resize(int width, int height) {
//        stage.setViewport(width, height);
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
        splashTexture.dispose();
        stage.dispose();
    }
}
