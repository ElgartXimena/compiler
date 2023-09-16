package Pruebas;

import AnalizadorLexico.Analizador_Lexico;
import MainPackage.Lector_Archivo;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;

public class EjecutorPruebas {
    public EjecutorPruebas() {
    }
    public static void run(ArrayList<Prueba> pruebas){
        for (Prueba p: pruebas){
            p.run();
        }
    }
}
