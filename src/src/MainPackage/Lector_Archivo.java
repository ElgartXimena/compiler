package MainPackage;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Lector_Archivo {

    public Lector_Archivo(){

    }

    public static char[] Cargar(){
        String output="";
        try {
            JFileChooser archivo = new JFileChooser();
            archivo.setCurrentDirectory(new File("C:\\"));
            archivo.setDialogTitle("Select a source code");
            archivo.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            archivo.showOpenDialog(new JFrame());
            File codigo = archivo.getSelectedFile();

            BufferedReader reader = new BufferedReader(new FileReader(codigo));
            String line;

            while ((line = reader.readLine()) != null) {
                output +=line;
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
        return output.toCharArray();
    }
}
