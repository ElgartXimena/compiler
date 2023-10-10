package MainPackage;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Lector_Archivo {

    public Lector_Archivo(){

    }
    public static File selectDir(){
        JFileChooser archivo = new JFileChooser();
        String path = new File("Pruebas").getAbsolutePath();
        archivo.setCurrentDirectory(new File(path));
        archivo.setDialogTitle("SELECT A DIRECTORY WITH SOURCE CODES");
        archivo.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        archivo.showOpenDialog(new JFrame());
        File dir = archivo.getSelectedFile();
        return dir;
    }
    public static char[] Cargar(File codigo) {
        List<String> lines = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(codigo));
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }

        // Unir las líneas en una sola cadena con saltos de línea
        String output = String.join(System.lineSeparator(), lines);
        output = output.concat("$");
        System.out.println("Codigo cargado:\n" + output);

        return output.toCharArray();
    }
}
