package com.jojeda.desktop;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class MiniPacker {

    private final ProcessBuilder processBuilder;
    private final String ENTRADA = "C:\\Users\\Javi\\IdeaProjects\\BarJumper\\RES\\lpc_entry\\png";
    private final String SALIDA = "C:\\Users\\Javi\\IdeaProjects\\BarJumper\\RES\\lpc_entry\\sprites";

    public static void main(String[] args) {
        new MiniPacker();

    }

    public MiniPacker() {
        processBuilder = new ProcessBuilder();
        processBuilder.directory(new File("C:\\Users\\Javi\\Desktop\\Magick Image"));

        File RES = new File(ENTRADA);

        try {
            operar(RES);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void operar(File entrada) throws IOException {
        if (entrada.isDirectory()) {
            File[] files = entrada.listFiles();
            if (files.length > 0) {
                for (File file : files) {
                    operar(file);
                }
            } else {
                entrada.delete();
            }
        } else {
            String salida = entrada.getAbsolutePath();
            salida = salida.replace(ENTRADA, SALIDA);
            if (salida.split("[.]").length > 0)
                salida = salida.split("[.]")[0];


            new File(salida).mkdirs();
            processBuilder.command(
                    "CMD", "/c",
                    "convert " + entrada.getAbsolutePath() + " -crop 100%x25% +repage +adjoin " + salida + File.separator + "part%02d.png"
            );
            processBuilder.start();
        }
    }

}
