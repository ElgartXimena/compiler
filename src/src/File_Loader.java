
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class File_Loader {

    public File_Loader(){

    }

    public static String Load(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese ruta al programa: ");
        String file_name = sc.nextLine();
        String output="";
        try {
            File source_code = new File(file_name);
            BufferedReader reader = new BufferedReader(new FileReader(source_code));
            String line;
            int counter = 1;

            while ((line = reader.readLine()) != null) {
                System.out.println(String.valueOf(counter)+ " " + line);
                output +=line;
                counter++;
            }
            sc.close();

        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
        return output;
    }
}
