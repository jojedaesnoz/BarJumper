package com.jojeda.pantallas;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.*;

import static com.jojeda.BarJumper.getJuego;

public class PantallaMenuPrincipal implements Screen {

    Stage stage;

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
        VisTextButton btJugar = new VisTextButton("Jugar");
        VisTextButton btAjustes = new VisTextButton("Ajustes");
        VisTextButton btSalir = new VisTextButton("Salir");

        // Colocar componentes
        tabla.row();
        tabla.add(titulo).left().width(200).height(100).pad(5);
        for (VisTextButton boton : new VisTextButton[]{btJugar, btAjustes, btSalir}) {
            tabla.row();
            tabla.add(boton).center().width(200).height(50).pad(5);
        }

        // Listener JUGAR
        btJugar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
//                VisDialog dialog = new VisDialog("Elige el nivel de juego");
//                dialog.button("Nivel 1");
//                dialog.button("Nivel 2");
//
//                VisTextButton btNivel1 = new VisTextButton("Nivel 1");
//                VisTextButton btNivel2 = new VisTextButton("Nivel 2");
//
//                dialog.show(stage);

                ((Game) Gdx.app.getApplicationListener())
                        .setScreen(new PantallaJuego(1));
                dispose();
            }
        });

        // Listener AJUSTES
        btAjustes.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener())
                        .setScreen(new PantallaMenuConfiguracion());
                dispose();
            }
        });


        // Listener SALIR
        btSalir.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                System.exit(0);
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
    public void dispose() {
        stage.dispose();
        Gdx.app.getApplicationListener().dispose();
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
