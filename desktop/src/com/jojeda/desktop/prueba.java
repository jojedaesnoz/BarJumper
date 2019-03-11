package com.jojeda.desktop;

public class prueba {
    enum asd{
        hola, que, ase
    }

    public static void main(String[] args) {
        for (asd a : asd.values()) {
            System.out.println(a);
        }
    }
}
