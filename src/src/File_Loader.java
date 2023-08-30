
import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Scanner;

public class File_Loader {

    public File_Loader(){

    }

    public static char[] Load(){
        String output="";
        try {
            JFileChooser select_file = new JFileChooser();
            select_file.setCurrentDirectory(new File("C:\\"));
            select_file.setDialogTitle("Select a source code");
            select_file.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            select_file.showOpenDialog(new JFrame());
            File source_code = select_file.getSelectedFile();

            BufferedReader reader = new BufferedReader(new FileReader(source_code));
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
