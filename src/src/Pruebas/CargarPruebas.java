package Pruebas;

import AnalizadorLexico.Analizador_Lexico;
import MainPackage.Lector_Archivo;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CargarPruebas {
    public static ArrayList<Prueba> cargar() throws IOException {
        JFileChooser archivo = new JFileChooser();
        archivo.setCurrentDirectory(new File("C:/Users/leole/Desktop/Facultad/4to 2c/Diseño de Compiladores/TP/compiler/src/src/Pruebas"));
        archivo.setDialogTitle("SELECT A DIRECTORY WITH SOURCE CODES");
        archivo.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        archivo.showOpenDialog(new JFrame());
        File dir = archivo.getSelectedFile();
        ArrayList <String> results = CargarResultados.cargar();//todos los resultados en formato [[1,2,3],[1,2,3],[1,2,3]]
        ArrayList<Prueba> pruebas = new ArrayList<>();
        int index = 0;
        for (File f: dir.listFiles()){
            System.out.println("Resultado cargado: "+results.get(index));
            Prueba p = new Prueba(ConvertirStringInt.convertir(results.get(index)), String.valueOf(index));
            p.setCodigo(Lector_Archivo.Cargar(f));
            pruebas.add(p);
            index++;
        }
        return pruebas;
    }
}
