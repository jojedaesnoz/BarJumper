package com.jojeda.managers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import java.io.File;

import static com.jojeda.util.Constantes.TEXTURE_ATLAS;

public class R {
    public static AssetManager assets = new AssetManager();
    public static TextureAtlas textureAtlas;

    public static void loadAllResources() {
//        assets.load(TEXTURE_ATLAS, TextureAtlas.class);
//        textureAtlas = new TextureAtlas(TEXTURE_ATLAS);

        loadSounds();
        loadMusics();

    }

    public static boolean update() {
        return assets.update();
    }

    public static void finishLoading() {
        assets.finishLoading();
    }

    public static void loadSounds() {
//        assets.load("sounds" + File.separator + "game_begin.wav", Sound.class);
        assets.load("sounds" + File.separator + "jump.ogg", Sound.class);
    }

    public static void loadMusics() {
        assets.load("musics" + File.separator + "background_music.ogg", Music.class);
    }

    public static TextureRegion getRegion(String name) {
        return assets.get(TEXTURE_ATLAS, TextureAtlas.class).findRegion(name);
    }

    public static TextureRegion getRegion(String name, int position) {
        return assets.get(TEXTURE_ATLAS, TextureAtlas.class).findRegion(name, position);
    }

    public static Array<TextureAtlas.AtlasRegion> getRegions(String name) {
        return assets.get(TEXTURE_ATLAS, TextureAtlas.class).findRegions(name);
    }

    public void prueba() {
    }

    public static Sound getSound(String name) {
        return assets.get(name, Sound.class);
    }

    public static Music getMusic(String name) {
//        return assets.get("musics" + File.separator + name, Music.class);
        return assets.get("musics" + File.separator + "background_music.ogg", Music.class);
    }

}
