package com.jojeda.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.jojeda.util.Constantes;

import static com.jojeda.util.Constantes.MUSICA;
import static com.jojeda.util.Constantes.SONIDO;

public class Configuracion {
    private static Preferences prefs = Gdx.app.getPreferences(Constantes.NOMBRE_APP);

    public static boolean isSoundEnabled() {
        return prefs.getBoolean(SONIDO);
    }

    public static void setSoundEnabled(boolean enabled) {
        prefs.putBoolean(SONIDO, enabled);
        prefs.flush();
    }

    public static boolean isMusicEnabled() {
        return prefs.getBoolean(MUSICA);
    }

    public static void setMusicEnabled(boolean enabled) {
        prefs.putBoolean(MUSICA, enabled);
        prefs.flush();
    }
}
