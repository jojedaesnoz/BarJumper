package com.jojeda.pantallas;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.jojeda.BarJumper;
import com.jojeda.managers.Configuracion;
import com.jojeda.managers.SpriteManager;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;

import static com.jojeda.util.Constantes.DEATH_SOUND;

public class PantallaGameOver implements Screen {

    final Game juego;
    OrthographicCamera camara;
    Stage stage;
    private SpriteManager spriteManager;

    public PantallaGameOver(SpriteManager spriteManager) {
        this.spriteManager = spriteManager;
        // Todo: ver si queda mejor con fuente.draw o con una tabla
        this.juego = ((Game) Gdx.app.getApplicationListener());

        camara = new OrthographicCamera();
        camara.setToOrtho(false, 1024, 768);
    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0.3f, 0.6f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();

    }

    @Override
    public void dispose() {
        stage.dispose();
        juego.dispose();
    }

    @Override
    public void show() {
        if (!VisUI.isLoaded()) {
            VisUI.load();
        }

        // Preparar contenedor
        stage = new Stage();
        VisTable tabla = new VisTable();
        tabla.setFillParent(true);
        stage.addActor(tabla);
        Gdx.input.setInputProcessor(stage);

        // Crear los componentes
        VisTextButton btJugar = new VisTextButton("Volver a intentarlo");
        VisTextButton btSalir = new VisTextButton("Salir");
        VisLabel lbPuntuacion = new VisLabel("GAME OVER");

        tabla.row();
        tabla.add(lbPuntuacion).center().width(200).height(50).pad(5);
        tabla.row();
        tabla.add(btJugar).center().width(200).height(50).pad(5);
        tabla.row();
        tabla.add(btSalir).center().width(200).height(50).pad(5);

        btJugar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
//                juego.inicializarJuego();
                if (Configuracion.isSoundEnabled()) {
                    spriteManager.sounds.get(DEATH_SOUND).stop();
                }

                if (Configuracion.isMusicEnabled()) {
                    spriteManager.music.stop();
                }

                ((Game) (Gdx.app.getApplicationListener())).setScreen(new PantallaJuego(spriteManager.levelManager.currentLevel));
            }
        });

        btSalir.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                if (Configuracion.isSoundEnabled()) {
                    spriteManager.sounds.get(DEATH_SOUND).stop();
                }

                if (Configuracion.isMusicEnabled()) {
                    spriteManager.music.stop();
                }
                System.exit(0);
            }
        });
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
}
