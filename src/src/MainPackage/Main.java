package MainPackage;

import AnalizadorLexico.Analizador_Lexico;
import AnalizadorLexico.Tabla_Simbolos;
import AnalizadorSintactico.Analizador_Sintactico;
import GeneracionCodAssembler.GeneradorAssembler;
import GeneracionCodAssembler.GeneradorSalidaAsm;
import GeneracionCodigoIntermedio.GeneradorCod;
import GeneracionCodigoIntermedio.Pila;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        File f = Lector_Archivo.selectDir();
        Analizador_Sintactico as = new Analizador_Sintactico(Lector_Archivo.Cargar(f));
        as.iniciar();
        System.out.println(GeneradorCod.to_String());
        System.out.println("Errores detectados: "+GeneradorCod.cantErrores);
        if (GeneradorCod.cantErrores==0){
            GeneradorAssembler genAsm = new GeneradorAssembler();
            GeneradorSalidaAsm.run(genAsm.generarAssembler());
        }
        System.exit(0);
    }
}