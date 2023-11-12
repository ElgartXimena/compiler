%{
package AnalizadorSintactico;
import AnalizadorLexico.*;
import GeneracionCodigoIntermedio.*;
import java.util.ArrayList;
import java.util.HashMap;
%}

/* YACC DECLARATIONS */
%token ID CTE CADENA IF ELSE END_IF PRINT CLASS VOID SHORT ULONG DOUBLE FOR IN RANGE IMPL INTERFACE IMPLEMENT RETURN MENOS_IGUAL MAYOR_IGUAL MENOR_IGUAL IGUAL DISTINTO
%start programa
/* Grammar definition */
%%
programa    : '{' bloque_sentencias '}' 
            {
                if (!variables.isEmpty()){
                    System.out.print("WARNING. Existen variables sin utilizar del lado derecho: ");
                    for (String lexema : variables.keySet()) {
                        System.out.print(lexema+" ");
                    }
                }
                System.out.println("");
            }
            | '{' bloque_sentencias  {System.out.println("ERROR EN PROGRAMA. Linea: " + Analizador_Lexico.cantLineas + " se esperaba '}'");}
            | bloque_sentencias '}' {System.out.println("ERROR EN PROGRAMA. Linea: " + Analizador_Lexico.cantLineas + " se esperaba '{'");}
            | bloque_sentencias {System.out.println("ERROR EN PROGRAMA. Linea: " + Analizador_Lexico.cantLineas + " se esperaban llaves");}
;

bloque_sentencias   : bloque_sentencias sentencia
                    | sentencia
;

sentencia       : sentencia_declarativa
                | sentencia_ejecutable
                | error ',' {System.out.println("ERROR EN SENTENCIA. Linea: " + Analizador_Lexico.cantLineas);}
;

bloque_declarativo  : bloque_declarativo sentencia_declarativa
                    | sentencia_declarativa
;

bloque_ejecutable   : bloque_ejecutable sentencia_ejecutable
                    | sentencia_ejecutable
;

sentencia_declarativa   : declaracion_variables
                        | declaracion_funcion  
                        | declaracion_clase
                        | declaracion_distribuida
                        | declaracion_interfaz
;

declaracion_variables   : tipo lista_variables ',' 
                        {
                            System.out.println("Linea: " + Analizador_Lexico.cantLineas + " Declaracion de VARIABLE/S de TIPO " + $1.sval);
                        }
                        | tipoclase lista_variables ',' 
                        {
                            System.out.println("Linea: " + Analizador_Lexico.cantLineas + " Declaracion de VARIABLE/S de TIPO " + $1.sval);
                        }
                        | tipo lista_variables error {System.out.println("ERROR EN DECLARACION DE VARIABLES. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                        | tipoclase lista_variables error {System.out.println("ERROR EN DECLARACION DE VARIABLES. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
;

tipo    : SHORT {tipo = $1.sval;}
        | ULONG {tipo = $1.sval;}
        | DOUBLE {tipo = $1.sval;}
;

tipoclase : ID {
                if (Tabla_Simbolos.getAtributos($1.sval+"#main").isUso("CLASE")){
                    tipo = $1.sval;    
                    Tabla_Simbolos.borrarSimbolo($1.sval);
                } else {
                    System.out.println("ERROR EN DECLARACION DE VARIABLES. Linea: "+Analizador_Lexico.cantLineas+": "+$1.sval+" no es tipo CLASE");    
                }
            }
;
lista_variables : ID 
                {
                    variables.put(concatenarAmbito($1.sval,pilaAmbito.getElements()),0);
                    Tabla_Simbolos.getAtributos($1.sval).setTipo(tipo);
                    Tabla_Simbolos.getAtributos($1.sval).setUso("VARIABLE");
                    if (!isDeclarada($1.sval,pilaAmbito.getElements()).equals("")){
                        System.out.println("ERROR. SOBREESCRITURA DE ATRIBUTO. Linea: " + Analizador_Lexico.cantLineas);    
                    }
                    if (!setAmbito($1.sval)){ 
                        //setAmbito modifica la clave, concatenando el ambito. Si ya existia arroja error, y sino, la setea
                        System.out.println("ERROR. REDECLARACION DE NOMBRE. Linea: " + Analizador_Lexico.cantLineas);
                    } 
                }
                | lista_variables ';' ID 
                {   
                    variables.put(concatenarAmbito($3.sval,pilaAmbito.getElements()),0);
                    Tabla_Simbolos.getAtributos($3.sval).setTipo(tipo);
                    Tabla_Simbolos.getAtributos($3.sval).setUso("VARIABLE");
                    if (!isDeclarada($3.sval,pilaAmbito.getElements()).equals("")){
                        System.out.println("ERROR. SOBREESCRITURA DE ATRIBUTO. Linea: " + Analizador_Lexico.cantLineas);    
                    }
                    if (!setAmbito($3.sval)){
                        System.out.println("ERROR. REDECLARACION DE NOMBRE. Linea: " + Analizador_Lexico.cantLineas);
                    }
                    
                }
;

declaracion_funcion : encabezado_funcion cuerpo_funcion ',' 
                    {
                        String am = (String) pilaAmbito.desapilar();//sino queda f1#main#f1, y necesito f1#main para los atributos
                        AtributosLexema att = Tabla_Simbolos.getAtributos(concatenarAmbito(am, pilaAmbito.getElements()));
                        isImpl = att.isImplementado(); 
                        att.setImplementado(true);
                        GeneradorCod.borrarFlag();
                        //pone en True el booleano isImplementado para las funciones. 
                        //Sirve para saber si:
                        //--> una clase implemento la funcion o solo declaro el encabezado. 
                        //--> la funcion puede usarse (tiene que tener cuerpo)
                        //--> una clase que implementa una interfaz, implemento todos los metodos
                    }
                    | encabezado_funcion cuerpo_funcion error {System.out.println("ERROR EN DECLARACION DE FUNCION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
;


encabezado_funcion: VOID ID '(' ')'  
                    {
                        //poner ambito a IDfuncion, apilar nuevo ambito
                        System.out.println("Linea: " + Analizador_Lexico.cantLineas + " Declaracion funcion VOID " + $2.sval);
                        Tabla_Simbolos.getAtributos($2.sval).setUso("FUNCION");
                        funcionImpl = $2.sval;
                        isDecl = isDeclarada($2.sval,pilaAmbito.getElements());
                        if (!setAmbito($2.sval)){//si ya estaba declarado entra al if
                            if (Tabla_Simbolos.getAtributos(concatenarAmbito($2.sval, pilaAmbito.getElements())).isImplementado()){
                                System.out.println("ERROR. REDECLARACION DE NOMBRE. Linea: " + Analizador_Lexico.cantLineas);
                            } 
                        } 
                        GeneradorCod.setFlagFuncion(concatenarAmbito($2.sval,pilaAmbito.getElements()));
                        pilaAmbito.apilar($2.sval);
                    }
                    | VOID ID '(' tipo ID ')'
                    {
                        System.out.println("Linea: " + Analizador_Lexico.cantLineas + " Declaracion funcion VOID " + $2.sval);
                        Tabla_Simbolos.getAtributos($2.sval).setUso("FUNCION");
                        Tabla_Simbolos.getAtributos($2.sval).setParametro(concatenarAmbito($5.sval,pilaAmbito.getElements()));
                        Tabla_Simbolos.getAtributos($2.sval).setTipoParametro($4.sval);
                        Tabla_Simbolos.getAtributos($5.sval).setUso("PARAMETRO");
                        Tabla_Simbolos.getAtributos($5.sval).setTipo($4.sval);
                        funcionImpl = $2.sval;
                        isDecl = isDeclarada($2.sval,pilaAmbito.getElements());
                        if (!setAmbito($2.sval)){//si ya estaba declarado entra al if
                            if (Tabla_Simbolos.getAtributos(concatenarAmbito($2.sval, pilaAmbito.getElements())).isImplementado()){
                                System.out.println("ERROR. REDECLARACION DE NOMBRE. Linea: " + Analizador_Lexico.cantLineas);
                            } 
                        } 
                        GeneradorCod.setFlagFuncion(concatenarAmbito($2.sval,pilaAmbito.getElements()));
                        pilaAmbito.apilar($2.sval);
                        setAmbito($5.sval); //para el parametro formal
                    }
                    | VOID ID '(' tipo ID  {System.out.println("ERROR EN DECLARACION DE FUNCION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ')'");}
                    | VOID ID tipo ID ')'  {System.out.println("ERROR EN DECLARACION DE FUNCION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba '('");}
                    | VOID ID '(' ID ')' error {System.out.println("ERROR EN DECLARACION DE FUNCION. Linea: " + Analizador_Lexico.cantLineas + " falta el tipo de " + $4.sval);}
;


cuerpo_funcion  : '{' bloque_funcion '}' 
                | '{'  '}' error {System.out.println("ERROR EN DECLARACION DE FUNCION. Linea: " + Analizador_Lexico.cantLineas + " no se puede declarar una funcion sin cuerpo");}
;

bloque_funcion  : bloque_funcion sentencia_funcion 
                | sentencia_funcion 
;

sentencia_funcion   : declaracion_variables
                    | declaracion_funcion
                    | sentencia_ejecutable
;

declaracion_clase   : encabezado_clase cuerpo_clase ',' {pilaAmbito.desapilar();}
                    | encabezado_clase cuerpo_clase error {System.out.println("ERROR EN DECLARACION DE CLASE. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
;

encabezado_clase   : CLASS ID 
                    {
                        System.out.println("Linea: " + Analizador_Lexico.cantLineas + " Declaracion CLASE " + $2.sval);
                        Tabla_Simbolos.getAtributos($2.sval).setUso("CLASE");
                        if (!setAmbito($2.sval)){
                            System.out.println("ERROR. REDECLARACION DE NOMBRE. Linea: " + Analizador_Lexico.cantLineas);
                        };
                        pilaAmbito.apilar($2.sval);

                    }
                    | CLASS ID IMPLEMENT ID
                    {
                        System.out.println("Linea: " + Analizador_Lexico.cantLineas + " Declaracion CLASE " + $2.sval);
                        if (Tabla_Simbolos.getAtributos($4.sval+"#main").isUso("INTERFAZ")){
                            Tabla_Simbolos.getAtributos($2.sval).setUso("CLASE");
                            Tabla_Simbolos.getAtributos($2.sval).setImplementa($4.sval);
                            if (!setAmbito($2.sval)){
                                System.out.println("ERROR. REDECLARACION DE NOMBRE. Linea: " + Analizador_Lexico.cantLineas);
                            };
                            pilaAmbito.apilar($2.sval);
                        } else {
                            System.out.println("ERROR EN DECLARACION DE CLASE. Linea "+Analizador_Lexico.cantLineas+": "+$4.sval+" no es un INTERFACE");
                        }
                        //se borra ID de la tabla de simbolos porque el lexico lo inserta al reconocer un identificador, 
                        //y como el original tiene el nombre cambiado por el ambito, existiran ambos en la TS
                        Tabla_Simbolos.borrarSimbolo($4.sval);
                    }
                    | CLASS ID IMPLEMENT  error {System.out.println("ERROR EN DECLARACION DE CLASE. Linea: " + Analizador_Lexico.cantLineas + " falta el identificador de la interfaz");}
;

cuerpo_clase    : '{' bloque_clase '}'
                | '{' '}' error {System.out.println("ERROR EN DECLARACION DE CLASE. Linea: " + Analizador_Lexico.cantLineas + " no se puede definir una clase sin cuerpo");}
;

bloque_clase    : bloque_clase sentencia_clase 
                | sentencia_clase 
;

sentencia_clase : declaracion_variables
                | declaracion_funcion 
                | ID ',' 
                {
                    if (Tabla_Simbolos.getAtributos($1.sval+"#main").isUso("CLASE")){
                        Tabla_Simbolos.getAtributos((String)pilaAmbito.getTope()+"#main").setHereda($1.sval);
                        Tabla_Simbolos.borrarSimbolo($1.sval);
                        //si ID es una clase, entonces la clase donde se define esta linea debe heredar de ID.
                    }
                }
                | encabezado_funcion ',' 
                {
                    pilaAmbito.desapilar();
                }
;

declaracion_distribuida : encabezado_dec_dist ':' cuerpo_dec_dist ',' 
                        {
                            AtributosLexema att = Tabla_Simbolos.getAtributos(concatenarAmbito(funcionImpl,pilaAmbito.getElements()));
                            if (!isDecl.equals("")){
                                if (isImpl){
                                    System.out.println("ERROR EN DECLARACION DISTRIBUIDA. FUNCION YA IMPLEMENTADA. Linea: " + Analizador_Lexico.cantLineas);
                                } else {
                                    att.setImplementado(true);
                                }
                            } else {
                                Tabla_Simbolos.borrarSimbolo(concatenarAmbito(funcionImpl,pilaAmbito.getElements()));
                                System.out.println("ERROR EN DECLARACION DISTRIBUIDA. FUNCION NO DECLARADA. Linea: " + Analizador_Lexico.cantLineas);
                            }
                            //se borra ID de la tabla de simbolos porque el lexico lo inserta al reconocer un identificador, 
                            //y como el original tiene el nombre cambiado por el ambito, existiran ambos en la TS
                            Tabla_Simbolos.borrarSimbolo(funcionImpl); 
                        }
                        | encabezado_dec_dist cuerpo_dec_dist ',' error {System.out.println(". Linea: " + Analizador_Lexico.cantLineas + " se esperaba ':'");}
                        | encabezado_dec_dist ':' cuerpo_dec_dist error {System.out.println("ERROR EN DECLARACION DISTRIBUIDA. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
;

encabezado_dec_dist :   IMPL FOR ID 
                        {
                            System.out.println("Linea: " + Analizador_Lexico.cantLineas + " Declaracion DISTRIBUIDA para " + $3.sval);
                            AtributosLexema atributos = Tabla_Simbolos.getAtributos($3.sval+"#main");
                            if ((atributos != null) && (atributos.isUso("CLASE"))){
                                pilaAmbito.apilar($3.sval);
                            } else {
                                System.out.println("ERROR EN DECLARACION DISTRIBUIDA. Linea: " + Analizador_Lexico.cantLineas + " " + $3.sval + " no es una clase ");
                            }
                            Tabla_Simbolos.borrarSimbolo($3.sval);
                        }
                        | IMPL ID  error {System.out.println("ERROR EN DECLARACION DISTRIBUIDA. Linea: " + Analizador_Lexico.cantLineas + " falta palabra reservada FOR");}
;

cuerpo_dec_dist : '{' declaracion_funcion '}'
                | '{' '}' error {System.out.println("ERROR EN DECLARACION DISTRIBUIDA. Linea: " + Analizador_Lexico.cantLineas + " no se puede definir una declaracion distribuida sin cuerpo");}
;

declaracion_interfaz    : encabezado_interfaz cuerpo_interfaz ',' {pilaAmbito.desapilar();}
                        | encabezado_interfaz cuerpo_interfaz error {System.out.println("ERROR EN INTERFAZ. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
;

encabezado_interfaz : INTERFACE ID 
                    {
                        System.out.println("Linea: " + Analizador_Lexico.cantLineas + " Declaracion INTERFAZ " + $2.sval);
                        Tabla_Simbolos.getAtributos($2.sval).setUso("INTERFAZ");
                        if (!setAmbito($2.sval)){
                            System.out.println("ERROR. REDECLARACION DE NOMBRE. Linea: " + Analizador_Lexico.cantLineas);
                        };
                        pilaAmbito.apilar($2.sval);
                    }
;

cuerpo_interfaz : '{' metodos_interfaz '}' 
                | '{' '}' error {System.out.println("ERROR EN INTERFAZ. Linea: " + Analizador_Lexico.cantLineas + " no se puede declarar una interfaz sin metodos");}
;

metodos_interfaz    : encabezado_funcion ',' {pilaAmbito.desapilar();}
	                | metodos_interfaz encabezado_funcion ',' {pilaAmbito.desapilar();}
	                | encabezado_funcion {System.out.println("ERROR EN METODO DE INTERFAZ. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
	                | metodos_interfaz encabezado_funcion {System.out.println("ERROR EN METODO DE INTERFAZ. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
;

sentencia_ejecutable    : asignacion {System.out.println("Linea: " + Analizador_Lexico.cantLineas + " ASIGNACION");}
                        | invocacion_funcion ',' {System.out.println("Linea: " + Analizador_Lexico.cantLineas + " INVOCACION FUNCION");}
                        | invocacion_funcion error {GeneradorCod.cantErrores++; System.out.println("ERROR EN INVOCACION A LA FUNCION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                        | seleccion ',' {System.out.println("Linea: " + Analizador_Lexico.cantLineas + " Sentencia IF");}
                        | seleccion error {GeneradorCod.cantErrores++; System.out.println("ERROR EN SENTENCIA IF. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                        | imprimir ',' {System.out.println("Linea: " + Analizador_Lexico.cantLineas + " SENTENCIA DE IMPRESION");}
                        | imprimir error {GeneradorCod.cantErrores++; System.out.println("ERROR EN SENTENCIA DE IMPRESION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                        | ref_clase '(' expresion ')' ',' 
                        {
                            System.out.println("Linea: " + Analizador_Lexico.cantLineas + " REFERENCIA A CLASE");
                            if (!$1.sval.equals("")){
                                AtributosLexema att = Tabla_Simbolos.getAtributos($1.sval);
                                if (!att.tieneParametro()){
                                    GeneradorCod.cantErrores++;
                                    System.out.println("ERROR EN REFERENCIA A CLASE. Linea: " + Analizador_Lexico.cantLineas + " el numero de parametros no coincide");
                                } else{
                                    if (!att.coincideTipoParametro($3.sval)){
                                        GeneradorCod.cantErrores++;
                                        System.out.println("ERROR EN REFERENCIA A CLASE. Linea: " + Analizador_Lexico.cantLineas + " el tipo de parametro no coincide");
                                    } else {
                                        GeneradorCod.agregarTerceto("=",att.getParametro(),(String) $3.obj); //realizaria el copia valor
                                        GeneradorCod.agregarTerceto("CALL", $1.sval);
                                    }
                                }
                            }
                        }
                        | ref_clase '(' expresion ')' error {GeneradorCod.cantErrores++; System.out.println("ERROR EN REFERENCIA A CLASE. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                        | ref_clase '(' ')' ',' 
                        {
                            System.out.println("Linea: " + Analizador_Lexico.cantLineas + " REFERENCIA A CLASE");
                            if (!$1.sval.equals("")){
                                AtributosLexema att = Tabla_Simbolos.getAtributos($1.sval);
                                if (att.tieneParametro()){
                                    GeneradorCod.cantErrores++;
                                    System.out.println("ERROR EN REFERENCIA A CLASE. Linea: " + Analizador_Lexico.cantLineas + " el numero de parametros no coincide");
                                } else {
                                    GeneradorCod.agregarTerceto("CALL", $1.sval);
                                }
                            }
                        }
                        | ref_clase '(' ')' error {GeneradorCod.cantErrores++; System.out.println("ERROR EN REFERENCIA A CLASE. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                        | sentencia_control ','
                        | sentencia_control error {GeneradorCod.cantErrores++; System.out.println("ERROR EN SENTENCIA FOR. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                        | RETURN ',' {GeneradorCod.agregarTerceto("RETURN","");}
                        | RETURN error {GeneradorCod.cantErrores++; System.out.println("ERROR EN RETURN. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
;

asignacion  : ID '=' expresion ',' 
            {
                String var = isDeclarada($1.sval, pilaAmbito.getElements());
                if (var.equals("")){
                    System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " variable "+$1.sval+" no declarada.");
                    GeneradorCod.cantErrores++;
                } else {
                    if (!Tabla_Simbolos.getAtributos(var).isUso("VARIABLE")){
                        System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas +$1.sval+" no es una variable.");
                        GeneradorCod.cantErrores++;
                    } else {
                        String tID = Tabla_Simbolos.getAtributos(var).getTipo();
                        String tipoResultado = Conversor.getTipo(tID,$3.sval,"a");
                        if (tipoResultado.equals("error")){
                            System.out.println("ERROR DE INCOMPATIBILIDAD DE TIPOS. Linea: " + Analizador_Lexico.cantLineas + " no se puede asignar "+$3.sval+" a "+tID);
                            GeneradorCod.cantErrores++;
                        } else {
                            $$.sval = tipoResultado;
                            String exp = (String) $3.obj;
                            String refTerceto;
                            String t;
                            if (exp.contains("[")){
                                t = "["+GeneradorCod.agregarTerceto("=", var, exp, tipoResultado) + "]";
                            } else {
                                Terceto tConv = Conversor.getTercetoConversion("a", var, exp);
                                if (tConv != null){
                                    refTerceto = "["+GeneradorCod.agregarTerceto(tConv) + "]";
                                    t = "["+GeneradorCod.agregarTerceto("=", var, refTerceto, tipoResultado) + "]";
                                } else {
                                    t = "["+GeneradorCod.agregarTerceto("=", var, exp, tipoResultado) + "]";
                                }
                            }
                            $$.obj = t;
                        }
                    }
                }
                Tabla_Simbolos.borrarSimbolo($1.sval);
            }
            | ID MENOS_IGUAL expresion ','
            {
                String var = isDeclarada($1.sval, pilaAmbito.getElements());
                if (var.equals("")){
                    System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " variable "+$1.sval+" no declarada.");
                    GeneradorCod.cantErrores++;
                } else {
                    if (!Tabla_Simbolos.getAtributos(var).isUso("VARIABLE")){
                        System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas +$1.sval+" no es una variable.");
                        GeneradorCod.cantErrores++;
                    } else {
                        String tID = Tabla_Simbolos.getAtributos(var).getTipo();
                        String tipoResultado = Conversor.getTipo(tID,$3.sval,"a");//SEPARAR EN PRIMERO OP y DESPUES ASIG
                        if (tipoResultado.equals("error")){
                            System.out.println("ERROR DE INCOMPATIBILIDAD DE TIPOS. Linea: " + Analizador_Lexico.cantLineas + " no se puede asignar "+$3.sval+" a "+tID);
                            GeneradorCod.cantErrores++;
                        } else {
                            $$.sval = tipoResultado;
                            String exp = (String) $3.obj;
                            String refTerceto;
                            if (exp.contains("[")){
                                refTerceto = "[" +GeneradorCod.agregarTerceto("-", var, exp, tipoResultado)+"]";
                            } else {
                                refTerceto = obtenerTerceto("o", "-", var, (String) $3.obj, tipoResultado);
                            }
                            String t = "["+GeneradorCod.agregarTerceto("=", var, refTerceto, tipoResultado) + "]";
                            $$.obj = t;
                        }
                    }
                }
                Tabla_Simbolos.borrarSimbolo($1.sval);
            }
            | ref_clase '=' expresion ',' 
            {
                if (!claseRef.equals("")){
                    //esta asignando a una clase
                    System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " no se puede realizar una asignacion a una clase");
                    GeneradorCod.cantErrores++;
                } else {
                    if (!Tabla_Simbolos.getAtributos($1.sval).isUso("VARIABLE")){
                        System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas +$1.sval+" no es una variable.");
                        GeneradorCod.cantErrores++;
                    } else {
                        String tID = Tabla_Simbolos.getAtributos($1.sval).getTipo();
                        String tipoResultado = Conversor.getTipo(tID,$3.sval,"a");//SEPARAR EN PRIMERO OP y DESPUES ASIG
                        if (tipoResultado.equals("error")){
                            System.out.println("ERROR DE INCOMPATIBILIDAD DE TIPOS. Linea: " + Analizador_Lexico.cantLineas + " no se puede asignar "+$3.sval+" a "+tID);
                            GeneradorCod.cantErrores++;
                        } else {
                            $$.sval = tipoResultado;
                            String exp = (String) $3.obj;
                            String refTerceto;
                            String t;
                            if (exp.contains("[")){
                                t = "["+GeneradorCod.agregarTerceto("=", $1.sval, exp, tipoResultado) + "]";
                            } else {
                                Terceto tConv = Conversor.getTercetoConversion("a", $1.sval, exp);
                                if (tConv != null){
                                    refTerceto = "["+GeneradorCod.agregarTerceto(tConv) + "]";
                                    t = "["+GeneradorCod.agregarTerceto("=", $1.sval, refTerceto, tipoResultado) + "]";
                                } else {
                                    t = "["+GeneradorCod.agregarTerceto("=", $1.sval, exp, tipoResultado) + "]";
                                }
                            }
                            $$.obj = t;
                        }
                    }
                }
            }
            | ref_clase MENOS_IGUAL expresion ','
            {
                if (!claseRef.equals("")){
                    //esta asignando a una clase
                    System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " no se puede realizar una asignacion a una clase");
                    GeneradorCod.cantErrores++;
                } else {
                    if (!Tabla_Simbolos.getAtributos($1.sval).isUso("VARIABLE")){
                        System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas +" "+$1.sval+" no es una variable.");
                        GeneradorCod.cantErrores++;
                    } else {
                        String tID = Tabla_Simbolos.getAtributos($1.sval).getTipo();
                        String tipoResultado = Conversor.getTipo(tID,$3.sval,"a");//SEPARAR EN PRIMERO OP y DESPUES ASIG
                        if (tipoResultado.equals("error")){
                            System.out.println("ERROR DE INCOMPATIBILIDAD DE TIPOS. Linea: " + Analizador_Lexico.cantLineas + " no se puede asignar "+$3.sval+" a "+tID);
                            GeneradorCod.cantErrores++;
                        } else {
                            $$.sval = tipoResultado;
                            String exp = (String) $3.obj;
                            String refTerceto;
                            if (exp.contains("[")){
                                refTerceto = "[" +GeneradorCod.agregarTerceto("-", $1.sval, exp, tipoResultado)+"]";
                            } else {
                                refTerceto = obtenerTerceto("o", "-", $1.sval, (String) $3.obj, tipoResultado);
                            }
                            String t = "["+GeneradorCod.agregarTerceto("=", $1.sval, refTerceto, tipoResultado) + "]";
                            $$.obj = t;
                        }
                    }
                }
            }
            | ref_clase'(' ')' '=' expresion ',' {GeneradorCod.cantErrores++; System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " no se puede realizar una asignacion a una funcion");}
            | ref_clase'(' expresion ')' '=' expresion ',' {GeneradorCod.cantErrores++; System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " no se puede realizar una asignacion a una funcion");}
            | ref_clase'(' ')' MENOS_IGUAL expresion ',' {GeneradorCod.cantErrores++; System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " no se puede realizar una asignacion a una funcion");}
            | ref_clase'(' expresion ')' MENOS_IGUAL expresion ',' {GeneradorCod.cantErrores++; System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " no se puede realizar una asignacion a una funcion");}
            | ID ',' error  {GeneradorCod.cantErrores++; System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " Se esperaba expresion");}
            | ID MENOS_IGUAL ',' error  {GeneradorCod.cantErrores++; System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " Se esperaba expresion");}
            | ref_clase ','  error {GeneradorCod.cantErrores++; System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " Se esperaba expresion");}
            | ref_clase MENOS_IGUAL ','  error {GeneradorCod.cantErrores++; System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " Se esperaba expresion");}
            | ID '=' expresion error {GeneradorCod.cantErrores++; System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
            | ID MENOS_IGUAL expresion error {GeneradorCod.cantErrores++; System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
            | ref_clase '=' expresion error {GeneradorCod.cantErrores++; System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
            | ref_clase MENOS_IGUAL expresion error {GeneradorCod.cantErrores++; System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
;

expresion   : expresion '+' termino 
            {
                System.out.println("Linea: " + Analizador_Lexico.cantLineas + " SUMA");
                $$.obj = compatibilidadTipos("o", "+", $1.sval, $3.sval, (String) $1.obj, (String) $3.obj, $$.sval, $$.obj);
            }
            | expresion '-' termino 
            {
                System.out.println("Linea: " + Analizador_Lexico.cantLineas + " RESTA");
                $$.obj = compatibilidadTipos("o", "-", $1.sval, $3.sval, (String) $1.obj, (String) $3.obj, $$.sval, $$.obj);
            }
            | termino 
            {
                $$.sval = $1.sval;
                $$.obj = $1.obj;
            }
;

termino : termino '*' factor 
        {
            System.out.println("Linea: " + Analizador_Lexico.cantLineas + " MULTIPLICACION");
            $$.obj = compatibilidadTipos("o", "*", $1.sval, $3.sval, (String) $1.obj, (String) $3.obj, $$.sval, $$.obj);
        }
        | termino '/' factor 
        {
            System.out.println("Linea: " + Analizador_Lexico.cantLineas + " DIVISION");
            $$.obj = compatibilidadTipos("o", "/", $1.sval, $3.sval, (String) $1.obj, (String) $3.obj, $$.sval, $$.obj);
        }
        | factor 
        {
            $$.sval = $1.sval;
            $$.obj = $1.obj;
        }
;

factor  : ID 
        {
            String lexema = isDeclarada($1.sval, pilaAmbito.getElements());
            
            if (lexema.equals("")){
                System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " variable "+$1.sval+" no declarada.");
                GeneradorCod.cantErrores++;
            } else {
                $$.sval=Tabla_Simbolos.getAtributos(lexema).getTipo();
                $$.obj = lexema;
            }
            variables.remove(lexema);
            Tabla_Simbolos.borrarSimbolo($1.sval);
        }
        | constante 
        {
            $$.sval=$1.sval;
            $$.obj = $1.obj;
        }
;

constante   : CTE   { 
                    chequeoRango($1.sval);
                    AtributosLexema att = Tabla_Simbolos.getAtributos($1.sval);
                    att.sumarUso();
                    att.setUso("CONSTANTE");
                    $$.sval=att.getTipo();
                    $$.obj = $1.sval;
                    }
            | '-' CTE {
                    chequeoRango("-"+$2.sval);
                    AtributosLexema att = Tabla_Simbolos.getAtributos($2.sval);
                    if (att.isCero()){
                        Tabla_Simbolos.modificarClave($2.sval, "-"+$2.sval);
                    } else {
                        if (!Tabla_Simbolos.existeSimbolo("-"+$2.sval)){
                            Tabla_Simbolos.insertarSimbolo("-"+$2.sval,new AtributosLexema());
                            Tabla_Simbolos.getAtributos("-"+$2.sval).setUso("CONSTANTE");
                        }
                    }
                    Tabla_Simbolos.getAtributos("-"+$2.sval).sumarUso();
                    $$.sval=Tabla_Simbolos.getAtributos("-"+$2.sval).getTipo();
                    String l = "-"+$2.sval;
                    $$.obj = l;
                  }/* hay que ir a la tabla de simbolos a cambiar el signo, en caso de ser necesario */
;

invocacion_funcion  : ID '(' expresion ')'
                    {
                        String fun = isDeclarada($1.sval, pilaAmbito.getElements());
                        if (fun.equals("")){
                            System.out.println("ERROR EN INVOCACION A FUNCION. Linea: " + Analizador_Lexico.cantLineas + " la funcion "+$1.sval+" no esta declarada.");
                            GeneradorCod.cantErrores++;
                        } else {
                            AtributosLexema at = Tabla_Simbolos.getAtributos(fun); 
                            if (at.isUso("FUNCION")){
                                //CHEQUEO DE CANTIDAD de PARAMETROS
                                if (at.tieneParametro()){
                                    if (at.coincideTipoParametro($3.sval)){
                                        //la ref a terceto esta en obj
                                        GeneradorCod.agregarTerceto("=",Tabla_Simbolos.getAtributos(fun).getParametro(),(String) $3.obj); //realizaria el copia valor
                                        GeneradorCod.agregarTerceto("CALL", fun);
                                    } else {
                                        System.out.println("ERROR EN INVOCACION A FUNCION. Linea: " + Analizador_Lexico.cantLineas + " no coincide el tipo del parametro.");
                                        GeneradorCod.cantErrores++;
                                    }
                                } else {
                                    System.out.println("ERROR EN INVOCACION A FUNCION. Linea: " + Analizador_Lexico.cantLineas + " no coincide la cantidad de parametros.");
                                    GeneradorCod.cantErrores++;
                                } 
                            } else {
                                System.out.println("ERROR EN INVOCACION A FUNCION. Linea: " + Analizador_Lexico.cantLineas +$1.sval+" no es una funcion");
                                GeneradorCod.cantErrores++;
                            }
                        }
                        Tabla_Simbolos.borrarSimbolo($1.sval);
                    }
                    | ID '(' ')'
                    {
                        String fun = isDeclarada($1.sval, pilaAmbito.getElements());
                        if (fun.equals("")){
                            System.out.println("ERROR EN INVOCACION A FUNCION. Linea: " + Analizador_Lexico.cantLineas + " la funcion "+$1.sval+" no esta declarada.");
                            GeneradorCod.cantErrores++;
                        } else {
                            AtributosLexema at = Tabla_Simbolos.getAtributos(fun); 
                            if (at.isUso("FUNCION")){
                                //CHEQUEO DE CANTIDAD de PARAMETROS
                                if (at.tieneParametro()){
                                    System.out.println("ERROR EN INVOCACION A FUNCION. Linea: " + Analizador_Lexico.cantLineas + " no coincide la cantidad de parametros.");
                                    GeneradorCod.cantErrores++;
                                } else {
                                    GeneradorCod.agregarTerceto("CALL", fun);
                                }
                            } else {
                                System.out.println("ERROR EN INVOCACION A FUNCION. Linea: " + Analizador_Lexico.cantLineas +$1.sval+" no es una funcion.");
                                GeneradorCod.cantErrores++;
                            }
                        }
                        Tabla_Simbolos.borrarSimbolo($1.sval);
                    }
;

seleccion   : IF condicion cuerpo_if END_IF
            | IF condicion cuerpo_if {GeneradorCod.cantErrores++; System.out.println("ERROR EN SENTENCIA IF. Linea: " + Analizador_Lexico.cantLineas +" se esperaba END_IF");}
;

condicion   : '(' comparacion ')' 
            {
                Terceto t = new Terceto("BF", "");
                pilaTercetos.apilar(t);
                GeneradorCod.agregarTerceto(t);
            }
	        | '(' ')' error {GeneradorCod.cantErrores++;  System.out.println("ERROR EN SENTENCIA IF. Linea: " + Analizador_Lexico.cantLineas + " falta condicion");}
;

comparacion : expresion MAYOR_IGUAL expresion {compatibilidadTipos("o", ">=", $1.sval, $3.sval, (String) $1.obj, (String) $3.obj, $$.sval, $$.obj);}
            | expresion MENOR_IGUAL expresion {compatibilidadTipos("o", ">=", $1.sval, $3.sval, (String) $1.obj, (String) $3.obj, $$.sval, $$.obj);}
            | expresion '<' expresion {compatibilidadTipos("o", "<", $1.sval, $3.sval, (String) $1.obj, (String) $3.obj, $$.sval, $$.obj);}
            | expresion '>' expresion {compatibilidadTipos("o", ">", $1.sval, $3.sval, (String) $1.obj, (String) $3.obj, $$.sval, $$.obj);}
            | expresion IGUAL expresion {compatibilidadTipos("o", "==", $1.sval, $3.sval, (String) $1.obj, (String) $3.obj, $$.sval, $$.obj);}
            | expresion DISTINTO expresion {compatibilidadTipos("o", "!!", $1.sval, $3.sval, (String) $1.obj, (String) $3.obj, $$.sval, $$.obj);}
;

cuerpo_if   : cuerpo_then_else cuerpo_else {GeneradorCod.agregarTercetoLabel();}
            | cuerpo_then
;

cuerpo_then_else    : '{' bloque_ejecutable '}' 
                    {
                        //obtener el nro de terceto a completar
                        Terceto bf = (Terceto) pilaTercetos.desapilar();
                        //ponerle en operando 1, el nro de terceto actual +2
                        bf.setOperando_1("["+ String.valueOf(GeneradorCod.getIndexActual()+2)+"]");
                        //generar terceto BI con operando1 incompleto
                        Terceto bi = new Terceto("BI", "");
                        //apilar nro de terceto BI
                        pilaTercetos.apilar(bi);
                        GeneradorCod.agregarTerceto(bi);
                        GeneradorCod.agregarTercetoLabel();
                        
                    }
                    | '{' '}' error {GeneradorCod.cantErrores++; System.out.println("ERROR EN SENTENCIA IF. Linea: " + Analizador_Lexico.cantLineas + " cuerpo de IF vacio");}
;

cuerpo_then : '{' bloque_ejecutable '}' 
            {
                //obtener el nro de terceto a completar
                Terceto bf = (Terceto) pilaTercetos.desapilar();
                //ponerle en operando 1, el nro de terceto actual +2
                bf.setOperando_1("["+ String.valueOf(GeneradorCod.getIndexActual()+1)+"]");
                GeneradorCod.agregarTercetoLabel();
            }
	        | '{' '}' error {GeneradorCod.cantErrores++; System.out.println("ERROR EN SENTENCIA IF. Linea: " + Analizador_Lexico.cantLineas + " cuerpo de IF vacio");}
;

cuerpo_else : ELSE '{' bloque_ejecutable '}' 
            {
                //obtener nro de terceto de BI
                Terceto bi = (Terceto) pilaTercetos.desapilar();
                //completar con nro terceto actual +1
                bi.setOperando_1("["+String.valueOf(GeneradorCod.getIndexActual()+1)+"]");
            }
            | ELSE '{' '}' error {GeneradorCod.cantErrores++; System.out.println("ERROR EN SENTENCIA IF. Linea: " + Analizador_Lexico.cantLineas + " cuerpo de ELSE vacio");}
;

imprimir    : PRINT CADENA 
            { 
                String cad = $2.sval;
                cad = cad.substring(1, cad.length()-1);
                GeneradorCod.agregarTerceto("PRINT", cad);
            }
            | PRINT error {GeneradorCod.cantErrores++; System.out.println("ERROR EN SENTENCIA DE IMPRESION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba una cadena de caracteres");}
;

ref_clase   : ID '.' ID 
            {
                claseRef = "";
                String id1 = isDeclarada($1.sval, pilaAmbito.getElements());
                String id2 = isDeclarada($3.sval, pilaAmbito.getElements());
                $$.sval = id2;
                if (!id1.equals("")){
                    String nombreClase = Tabla_Simbolos.getAtributos(id1).getTipo();
                    AtributosLexema atributos = Tabla_Simbolos.getAtributos(nombreClase+"#main");
                    if ((atributos != null) && (atributos.isUso("CLASE"))){
                        if (!id2.equals("")){
                            //es una clase
                            if (hereda(nombreClase, $3.sval)){
                                claseRef = $3.sval;
                            } else {
                                System.out.println("ERROR EN REFERENCIA A CLASE. Linea: " + Analizador_Lexico.cantLineas +" "+nombreClase+" no hereda de "+$3.sval);        
                            }
                        } else {
                            if (!Tabla_Simbolos.existeSimbolo($3.sval + "#main#"+nombreClase)){
                                System.out.println("ERROR EN REFERENCIA A CLASE. Linea: " + Analizador_Lexico.cantLineas +" "+$3.sval+" no esta declarado dentro de "+$1.sval);        
                            } else {
                                $$.sval = $3.sval + "#main#" + nombreClase;
                            }
                        }
                    } else {
                        System.out.println("ERROR EN REFERENCIA A CLASE. Linea: " + Analizador_Lexico.cantLineas +" "+$1.sval+" no es de tipo clase");
                    }
                } else {
                    System.out.println("ERROR EN REFERENCIA A CLASE. Linea: " + Analizador_Lexico.cantLineas + " variable "+$1.sval+" no declarada.");
                }
                Tabla_Simbolos.borrarSimbolo($1.sval);
                Tabla_Simbolos.borrarSimbolo($3.sval);
            }   
	        | ref_clase '.' ID 
            {
                String id2 = isDeclarada($3.sval, pilaAmbito.getElements());
                $$.sval = id2;
                if (!id2.equals("")){
                    System.out.println("ERROR EN REFERENCIA A CLASE. Linea: " + Analizador_Lexico.cantLineas + " " + $3.sval + " no es un metodo o atributo de " + claseRef);        
                } else {
                    String lexema = $3.sval + "#main#" +claseRef;
                    $$.sval = lexema;
                    if (!Tabla_Simbolos.existeSimbolo(lexema)){
                        System.out.println("ERROR EN REFERENCIA A CLASE. Linea: " + Analizador_Lexico.cantLineas + " " + $3.sval + " no esta declarado dentro de " + claseRef);        
                    }
                    claseRef = "";
                }
                Tabla_Simbolos.borrarSimbolo($3.sval);
            }
;

sentencia_control   : encabezado_for condicion_for cuerpo_for 
                    {
                        Terceto incr = (Terceto) pilaIndices.desapilar();
                        Terceto bf1 = (Terceto) pilaTercetos.desapilar(); //me da el terceto de bif incompleto
                        if (incr!=null && bf1 != null){
                            String indx = "[" + GeneradorCod.agregarTerceto(incr)+"]";
                            GeneradorCod.agregarTerceto("=", incr.getOperando_1(), indx, incr.getTipo());
                            //hasta aca, puse el incremento y asignacion

                            
                            String inicial = (String) pilaTercetos.desapilar(); //me da el terceto indice inicial

                            GeneradorCod.agregarTerceto("BI", inicial);
                            bf1.setOperando_1("["+String.valueOf(GeneradorCod.getIndexActual()+1)+"]");
                            GeneradorCod.agregarTercetoLabel();
                        }
                    }
                    | encabezado_for cuerpo_for error {GeneradorCod.cantErrores++; System.out.println("ERROR EN SENTENCIA FOR. Linea: " + Analizador_Lexico.cantLineas + " falta condicion");}
                    | encabezado_for condicion_for error {GeneradorCod.cantErrores++; System.out.println("ERROR EN SENTENCIA FOR. Linea: " + Analizador_Lexico.cantLineas + " no se puede definir un FOR sin cuerpo");}
;

encabezado_for  : FOR ID IN RANGE 
                {
                    System.out.println("Linea: " + Analizador_Lexico.cantLineas + " Sentencia FOR");
                    String id = isDeclarada($2.sval, pilaAmbito.getElements());
                    if (id.equals("")){
                        System.out.println("ERROR EN SENTENCIA FOR. Linea: " + Analizador_Lexico.cantLineas + " " + $2.sval + " no esta declarada");
                        GeneradorCod.cantErrores++;
                    } else {
                        if (Tabla_Simbolos.getAtributos(id).isTipo("DOUBLE")){
                            GeneradorCod.cantErrores++; 
                            System.out.println("ERROR EN INDICE FOR. Linea: " + Analizador_Lexico.cantLineas + " el indice debe ser de tipo ULONG o SHORT");
                        } else {
                            pilaIndices.apilar(id);
                        }   
                    }
                    Tabla_Simbolos.borrarSimbolo($2.sval);
                }
                | FOR IN RANGE error {GeneradorCod.cantErrores++; System.out.println("ERROR EN SENTENCIA FOR. Linea: " + Analizador_Lexico.cantLineas + " se esperaba un identificador");}
                | FOR ID RANGE {GeneradorCod.cantErrores++; System.out.println("ERROR EN SENTENCIA FOR. Linea: " + Analizador_Lexico.cantLineas + " falta la palabra reservada IN");}
                | FOR ID IN {GeneradorCod.cantErrores++; System.out.println("ERROR EN SENTENCIA FOR. Linea: " + Analizador_Lexico.cantLineas + " falta la palabra reservada RANGE");}
;       

condicion_for  : '(' constante ';' constante ';' constante ')' 
                {
                    System.out.println("Linea: " + Analizador_Lexico.cantLineas + " ENCABEZADO FOR");
                    String c1 = $2.sval;
                    String c2 = $4.sval;
                    String c3 = $6.sval;
                    String index = (String) pilaIndices.desapilar();
                    if (index!=null){
                        String tipo = Tabla_Simbolos.getAtributos(index).getTipo();
                        if (!c1.equals(tipo) || !c2.equals(tipo) || !c3.equals(tipo)){
                            GeneradorCod.cantErrores++; 
                            System.out.println("ERROR EN CONDICION FOR. Linea: " + Analizador_Lexico.cantLineas + " las constantes deben ser del mismo tipo que el indice");
                        } else {
                            
                            String incremento = (String) $6.obj;
                            GeneradorCod.agregarTerceto("=", index, (String) $2.obj, tipo); //inicializacion index
                            GeneradorCod.agregarTercetoLabel();
                            if (incremento.contains("-")){
                                //es negativo
                                GeneradorCod.agregarTerceto(">", index, (String) $4.obj); //condicion for
                            } else {
                                GeneradorCod.agregarTerceto("<", index, (String) $4.obj); //condicion for
                            }
                            pilaTercetos.apilar("[" + GeneradorCod.getIndexActual()+"]"); //apila el numero de terceto inicial

                            Terceto incr = new Terceto("+", index, incremento, tipo);
                            pilaIndices.apilar(incr);

                            Terceto t = new Terceto("BF", "");
                            pilaTercetos.apilar(t);  //apila primer bifurcacion incompleta
                            GeneradorCod.agregarTerceto(t); //agrea la bif incompleta a los tercetos
                        }
                    }
                }
                | '(' constante ';' constante ')' error {GeneradorCod.cantErrores++;System.out.println("ERROR EN ENCABEZADO FOR. Linea: " + Analizador_Lexico.cantLineas + " falta una constante");}
                | '(' constante ')' error {GeneradorCod.cantErrores++;System.out.println("ERROR EN ENCABEZADO FOR. Linea: " + Analizador_Lexico.cantLineas + " faltan constantes");}
;

cuerpo_for : '{' bloque_ejecutable '}'
;

%%
/* CODE SECTION */
public String tipo = "";
public Pila pilaAmbito = new Pila("main");
public String claseRef = "";
public String funcionImpl = "";
public boolean isImpl = false;
public String isDecl = "";
public HashMap<String, Integer> variables = new HashMap<>();
public Pila pilaTercetos = new Pila();
public Pila pilaIndices = new Pila();

public void chequeoRango(String cte){
    if (cte.contains("_s")){
        int cteint = Integer.parseInt(cte.substring(0, cte.length()-2));
        double min = Math.pow(-2,7);
        double max = Math.pow(2,7)-1;
        if ((cteint < min)||(cteint>max)){
            System.out.println("ERROR. Linea "+Analizador_Lexico.cantLineas+": Constante fuera de rango");
            GeneradorCod.cantErrores++;
        }
    } else if (cte.contains("_ul")) {
        long cteint_largo = Integer.parseInt(cte.substring(0, cte.length()-3));
        double min = 0;
        double max = Math.pow(2,32)-1;
        if ((cteint_largo < min)||(cteint_largo>max)){
            System.out.println("ERROR. Linea "+Analizador_Lexico.cantLineas+": Constante fuera de rango");
            GeneradorCod.cantErrores++;
        }
    } else {
        double max_pos = Math.pow(1.7976931348623157, 308);
        double min_pos = Math.pow(2.2250738585072014, -308);
        double max_neg = -Math.pow(2.2250738585072014, -308);
        double min_neg = -Math.pow(1.7976931348623157, 308);

        double num = 0;
        cte = cte.toUpperCase();
        if (cte.contains("D")){
            cte = cte.replace("D","E");
        }
        num = Double.parseDouble(cte);
        if (!((min_pos < num && num < max_pos) || (min_neg < num && num < max_neg) || num == 0.0)){
            System.out.println("ERROR. Linea "+Analizador_Lexico.cantLineas+": Constante fuera de rango");
            GeneradorCod.cantErrores++;
        }
    }
}

public boolean hereda(String cHija, String cPadre){
    String hereda_ch = Tabla_Simbolos.getAtributos(cHija+"#main").getHereda();
    if (hereda_ch.equals("")){
        return false;
    } else if (hereda_ch.equals(cPadre)){
        return true;
    } else {
        return hereda(hereda_ch,cPadre);
    }
}

public String concatenarAmbito(String lexema, ArrayList<Object> elements){
    String nuevoAmb = lexema;
    String delimitador = "#";
    for(Object o: elements){
        nuevoAmb = nuevoAmb.concat(delimitador);
        nuevoAmb = nuevoAmb.concat((String)o);
    }
    return nuevoAmb;
}

public boolean setAmbito(String lexema){
    return Tabla_Simbolos.modificarClave(lexema, concatenarAmbito(lexema, pilaAmbito.getElements())); 
    //la clave en la tabla de simbolos sera el lexema + el ambito. 
    //Ej: var1 --> var1#main#f1#f2 es una variable declarada en f2, quien esta definida en f1, y en general, dentro de main
    //Si la clave ya existia (variable ya definida en el ambito), devuelve false y no inserta
    //caso contrario, devuelve true habiendo insertado la nueva clave con ambito
}

public String isDeclarada(String variable, ArrayList<Object> ambito){
    //devuelve la entrada a la tabla de simbolos, en caso de estar declarada
    String out = concatenarAmbito(variable, ambito);
    if (ambito.isEmpty()){ //la variable no esta definida en ningun ambito
        return "";
    } else if (Tabla_Simbolos.existeSimbolo(out)) {
        return out; //la variable esta definida en alguno de los ambitos alcanzables
    } else {
        String amb = (String)ambito.remove(ambito.size()-1);
        // para cuando se usa un atributo de una clase padre
        AtributosLexema att = Tabla_Simbolos.getAtributos(amb+"#main");
        if ((att != null)&&(att.isUso("CLASE"))){
            String clasePadre = Tabla_Simbolos.getAtributos(amb+"#main").getHereda();
            if (!clasePadre.equals("")){
                ambito.add(clasePadre);
            }
        }
        return isDeclarada(variable, ambito); //continua la busqueda en el ambito superior, quitando el ambito actual
    }
}

public String obtenerTerceto(String operacion, String operador, String lexOp1, String lexOp2, String tipoResultado){
    Terceto tConversion = Conversor.getTercetoConversion(operacion, lexOp1, lexOp2);
    if (tConversion!=null){
        String refTerceto = "[" + GeneradorCod.agregarTerceto(tConversion) + "]";
        if (Conversor.operandoConvertido.equals("1")){
            return "["+GeneradorCod.agregarTerceto(operador, refTerceto, lexOp2, tipoResultado) + "]";
        } else {
            return "["+GeneradorCod.agregarTerceto(operador, lexOp1, refTerceto, tipoResultado) + "]";
        }
    } else {
        return "["+GeneradorCod.agregarTerceto(operador, lexOp1, lexOp2, tipoResultado) + "]";
    }
}

public String compatibilidadTipos(String operacion, String operador, String tipo_op1, String tipo_op2, String op1, String op2, String sval, Object obj){
    String tipoResultado = Conversor.getTipo(tipo_op1,tipo_op2,operacion); //en el sval esta el tipo
    if (tipoResultado.equals("error")){
        System.out.println("ERROR DE INCOMPATIBILIDAD DE TIPOS. Linea: " + Analizador_Lexico.cantLineas + " no se puede operar entre "+tipo_op1+" y "+tipo_op2);
        GeneradorCod.cantErrores++;
    } else {
        sval = tipoResultado;
        return obtenerTerceto(operacion, operador, op1, op2, tipoResultado);
    }
    return "";
}

public int yylex(){
    int tok = Analizador_Lexico.yylex();
    yylval = new ParserVal(Analizador_Lexico.buffer);
    return tok;
}
public void yyerror(String s){}