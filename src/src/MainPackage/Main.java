package MainPackage;

import AnalizadorLexico.Analizador_Lexico;
import AnalizadorSintactico.Parser;
import Pruebas.CargarPruebas;
import Pruebas.EjecutorPruebas;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        JFileChooser archivo = new JFileChooser();
        archivo.setCurrentDirectory(new File("C:/Users/Delfina/Documents/compiler/src/src/Pruebas"));
        archivo.setDialogTitle("SELECT A DIRECTORY WITH SOURCE CODES");
        archivo.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        archivo.showOpenDialog(new JFrame());
        File dir = archivo.getSelectedFile();
        Analizador_Lexico al = new Analizador_Lexico(Lector_Archivo.Cargar(dir));
        int tk = -1;
        while (tk != 0) {
            //System.out.println(al.getTablaSimbolos().toString());
            tk = al.yylex();
        }
        System.exit(0);
    }
}