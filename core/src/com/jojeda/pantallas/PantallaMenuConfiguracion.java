package com.jojeda.pantallas;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.jojeda.managers.Configuracion;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;


public class PantallaMenuConfiguracion implements Screen {

    Stage stage;
    VisTextButton btSonido;
    VisTextButton btMusica;

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
        VisLabel titulo = new VisLabel("BARJUMPER");
        btSonido = new VisTextButton("");
        btMusica = new VisTextButton("");

        actualizarMusica();
        actualizarSonido();
        final VisTextButton btVolver = new VisTextButton("Volver");

        // Colocar componentes
        tabla.row();
        tabla.add(titulo).left().width(200).height(100).pad(5);
        tabla.row();
        tabla.add(btSonido).center().width(200).height(50).pad(5);
        tabla.row();
        tabla.add(btMusica).center().width(200).height(50).pad(5);
        tabla.row();
        tabla.add(btVolver).center().width(200).height(50).pad(5);


        btSonido.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Configuracion.setSoundEnabled(!Configuracion.isSoundEnabled());
                actualizarSonido();
            }
        });

        btMusica.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Configuracion.setMusicEnabled(!Configuracion.isMusicEnabled());
                actualizarMusica();
            }
        });

        btVolver.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new PantallaMenuPrincipal());
                dispose();
            }
        });
    }

    private void actualizarSonido() {
        btSonido.setText(Configuracion.isSoundEnabled() ? "Sonido ON" : "Sonido OFF");
    }

    private void actualizarMusica() {
        btMusica.setText(Configuracion.isMusicEnabled() ? "Música ON" : "Música OFF");
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
