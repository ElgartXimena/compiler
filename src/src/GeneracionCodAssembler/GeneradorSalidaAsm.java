package GeneracionCodAssembler;

import MainPackage.Lector_Archivo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static MainPackage.Lector_Archivo.fileName;

public class GeneradorSalidaAsm {

    public static void run(StringBuilder codigo){
        File archivo = new File("Pruebas/SalidasAssembler", fileName+".asm");
        try {
            // Abrir un flujo de salida al archivo
            FileOutputStream fos = new FileOutputStream(archivo);
            // Convertir el StringBuilder a un arreglo de bytes
            byte[] bytes = codigo.toString().getBytes();
            // Escribir el arreglo de bytes al archivo
            fos.write(bytes);
            // Cerrar el flujo de salida
            fos.close();
            // Mostrar un mensaje de éxito
            System.out.println("Archivo " + fileName + " generado con éxito.");
        } catch (IOException e) {
            // Mostrar un mensaje de error
            System.out.println("Error al generar el archivo " + fileName + ": " + e.getMessage());
        }
    }

}
