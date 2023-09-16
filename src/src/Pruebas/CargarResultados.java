package Pruebas;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class CargarResultados {
    public static ArrayList<String> cargar() throws IOException {
        JFileChooser arch_results = new JFileChooser();
        arch_results.setCurrentDirectory(new File("C:\\Users\\leole\\Desktop\\Facultad\\4to 2c\\Diseño de Compiladores\\TP\\compiler\\src\\src\\Pruebas"));
        arch_results.setDialogTitle("SELECT RESULTS FILE");
        arch_results.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        arch_results.showOpenDialog(new JFrame());
        File results = arch_results.getSelectedFile();

        BufferedReader reader = new BufferedReader(new FileReader(results));
        String line;
        ArrayList<String> result = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            result.add(line);
        }
        return result;
    }

}
