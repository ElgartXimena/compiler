package MainPackage;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Lector_Archivo {

    public Lector_Archivo(){

    }

    public static char[] Cargar(File codigo){
        String output="";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(codigo));
            String line;
            while ((line = reader.readLine()) != null) {
                output +=line;
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
        System.out.println("Codigo cargado: "+output);
        return output.toCharArray();
    }
}
