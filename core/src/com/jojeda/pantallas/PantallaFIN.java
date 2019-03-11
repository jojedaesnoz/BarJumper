package com.jojeda.pantallas;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;

public class PantallaFIN implements Screen {

    OrthographicCamera camara;
    Stage stage;

    public PantallaFIN() {
        camara = new OrthographicCamera();
        camara.setToOrtho(false, 1024, 768);
    }

    @Override
    public void show() {

        if (!VisUI.isLoaded()){
            VisUI.load();
        }

        stage = new Stage();
        VisTable tabla = new VisTable();
        tabla.setFillParent(true);
        stage.addActor(tabla);
        Gdx.input.setInputProcessor(stage);

        VisTextButton btSalir = new VisTextButton("Salir");
        VisTextButton btMenuPrincipal = new VisTextButton("Volver al menú principal");



        tabla.row();
        tabla.add(new VisLabel("FIN DEL JUEGO")).center().width(200).height(50).pad(5);
        tabla.row();
        tabla.add(btMenuPrincipal).center().width(200).height(50).pad(5);
        tabla.row();
        tabla.add(btSalir).center().width(200).height(50).pad(5);


        btSalir.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                System.exit(0);
            }
        });

        btMenuPrincipal.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new PantallaMenuPrincipal());
            }
        });
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0.3f, 0.6f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
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
        stage.dispose();
    }
}
