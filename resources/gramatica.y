%{

%}

/* YACC DECLARATIONS */
%token ID CTE CADENA IF ELSE END_IF PRINT CLASS VOID SHORT ULONG DOUBLE FOR IN RANGE IMPL INTERFACE IMPLEMENT RETURN MENOS_IGUAL MAYOR_IGUAL MENOR_IGUAL IGUAL DISTINTO
%start programa

/* Grammar definition */
%%
programa    : '{' bloque_sentencias '}'
            | '{' bloque_sentencias  {System.out.println("ERROR EN PROGRAMA. Linea: " + Analizador_Lexico.cantLineas + " se esperaba '}'");}
            | bloque_sentencias '}' {System.out.println("ERROR EN PROGRAMA. Linea: " + Analizador_Lexico.cantLineas + " se esperaba '{'");}
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

sentencia_declarativa   : tipo lista_variables ',' 
                        {
                            System.out.println("Linea: " + Analizador_Lexico.cantLineas + " Declaracion de VARIABLE/S de TIPO " + $1.sval);
                        }
                        | ID lista_variables ',' 
                        {
                            if (Tabla_Simbolos.getAtributos($1.sval).isUso("CLASE")){
                                tipo = $1.sval;    
                            } else {
                                System.out.println("ERROR EN DECLARACION DE VARIABLES. Linea: "+Analizador_Lexico.cantLineas+": "+$1.sval+" no es tipo CLASE");    
                            }
                            System.out.println("Linea: " + Analizador_Lexico.cantLineas + " Declaracion de VARIABLE/S de TIPO " + $1.sval);
                        }
                        | tipo lista_variables error {System.out.println("ERROR EN DECLARACION DE VARIABLES. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                        | ID lista_variables error {System.out.println("ERROR EN DECLARACION DE VARIABLES. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                        | declaracion_funcion  
                        | declaracion_clase
                        | declaracion_distribuida
                        | declaracion_interfaz
;

tipo    : SHORT {tipo = $1.sval;}
        | ULONG {tipo = $1.sval;}
        | DOUBLE {tipo = $1.sval;}
;

lista_variables : ID {Tabla_Simbolos.getAtributos($1.sval).setTipo(tipo);}
                | lista_variables ';' ID {Tabla_Simbolos.getAtributos($3.sval).setTipo(tipo);}
;

declaracion_funcion : VOID ID '(' ')' '{' bloque_funcion '}' ',' {System.out.println("Linea: " + Analizador_Lexico.cantLineas + " Declaracion funcion VOID " + $2.sval);}
                    | VOID ID '(' tipo ID ')' '{' bloque_funcion '}' ',' {System.out.println("Linea: " + Analizador_Lexico.cantLineas + " Declaracion funcion VOID " + $2.sval);}
                    | VOID ID '(' tipo ID '{' bloque_funcion '}' ',' {System.out.println("ERROR EN DECLARACION DE FUNCION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ')'");}
                    | VOID ID tipo ID ')' '{' bloque_funcion '}' ',' {System.out.println("ERROR EN DECLARACION DE FUNCION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba '('");}
                    | VOID ID '(' ID ')' '{' bloque_funcion '}' ',' error {System.out.println("ERROR EN DECLARACION DE FUNCION. Linea: " + Analizador_Lexico.cantLineas + " falta el tipo de " + $4.sval);}
                    | VOID ID '(' tipo ID ')' '{'  '}' ',' error {System.out.println("ERROR EN DECLARACION DE FUNCION. Linea: " + Analizador_Lexico.cantLineas + " no se puede declarar una funcion sin cuerpo");}
                    | VOID ID '(' ')' '{'  '}' ',' error {System.out.println("ERROR EN DECLARACION DE FUNCION. Linea: " + Analizador_Lexico.cantLineas + " no se puede declarar una funcion sin cuerpo");}
                    | VOID ID '(' ')' '{' bloque_funcion '}' error {System.out.println("ERROR EN DECLARACION DE FUNCION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                    | VOID ID '(' tipo ID ')' '{' bloque_funcion '}' error {System.out.println("ERROR EN DECLARACION DE FUNCION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
;

bloque_funcion  : bloque_funcion sentencia_funcion 
                | sentencia_funcion 
;

sentencia_funcion   : tipo lista_variables ','
                    | ID lista_variables ','
                    | tipo lista_variables error {System.out.println("ERROR EN SENTENCIA DE FUNCION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                    | ID lista_variables error {System.out.println("ERROR EN SENTENCIA DE FUNCION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                    | declaracion_funcion
                    | sentencia_ejecutable
;

declaracion_clase   : CLASS ID '{' bloque_clase '}' ',' 
                    {
                        System.out.println("Linea: " + Analizador_Lexico.cantLineas + " Declaracion CLASE " + $2.sval);
                        Tabla_Simbolos.getAtributos($2.sval).setUso("CLASE");
                    }
                    | CLASS ID IMPLEMENT ID '{' bloque_clase '}' ',' 
                    {
                        System.out.println("Linea: " + Analizador_Lexico.cantLineas + " Declaracion CLASE " + $2.sval);
                        if (Tabla_Simbolos.getAtributos($4.sval).isUso("INTERFACE")){
                            Tabla_Simbolos.getAtributos($2.sval).setUso("CLASE");
                            Tabla_Simbolos.getAtributos($2.sval).setImplementa($4.sval);
                        } else {
                            System.out.println("ERROR EN DECLARACION DE CLASE. Linea "+Analizador_Lexico.cantLineas+": "+$4.sval+" no es un INTERFACE");
                        }
                    }
                    | CLASS ID '{' '}' ',' error {System.out.println("ERROR EN DECLARACION DE CLASE. Linea: " + Analizador_Lexico.cantLineas + " no se puede definir una clase sin cuerpo");}
                    | CLASS ID IMPLEMENT ID '{' '}' ',' error {System.out.println("ERROR EN DECLARACION DE CLASE. Linea: " + Analizador_Lexico.cantLineas + " no se puede definir una clase sin cuerpo");}
                    | CLASS ID IMPLEMENT '{' bloque_clase '}' ',' error {System.out.println("ERROR EN DECLARACION DE CLASE. Linea: " + Analizador_Lexico.cantLineas + " falta el identificador de la interfaz");}
                    | CLASS ID '{' bloque_clase '}' error {System.out.println("ERROR EN DECLARACION DE CLASE. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                    | CLASS ID IMPLEMENT ID '{' bloque_clase '}' error {System.out.println("ERROR EN DECLARACION DE CLASE. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
;

bloque_clase    : bloque_clase sentencia_clase 
                | sentencia_clase 
;

sentencia_clase : tipo lista_variables ','
                | ID lista_variables ','
                | tipo lista_variables error {System.out.println("ERROR EN SENTENCIA DE CLASE. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                | ID lista_variables error {System.out.println("ERROR EN SENTENCIA DE CLASE. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                | declaracion_funcion
;

declaracion_distribuida : IMPL FOR ID ':' '{' declaracion_funcion '}' ',' 
                        {
                            if (Tabla_Simbolos.getAtributos($3.sval).isUso("CLASE")){
                                ambito = $3.sval;
                            } else {
                                System.out.println("ERROR EN DECLARACION DISTRIBUIDA. Linea: " + Analizador_Lexico.cantLineas + " " + $3.sval + " no es una clase ");
                            }
                            System.out.println("Linea: " + Analizador_Lexico.cantLineas + " Declaracion DISTRIBUIDA para " + $3.sval);
                        }
                        | IMPL FOR ID '{' declaracion_funcion '}' ',' error {System.out.println("ERROR EN DECLARACION DISTRIBUIDA. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ':'");}
                        | IMPL FOR ID ':' '{' '}' ',' error {System.out.println("ERROR EN DECLARACION DISTRIBUIDA. Linea: " + Analizador_Lexico.cantLineas + " no se puede definir una declaracion distribuida sin cuerpo");}
                        | IMPL ID ':' '{' declaracion_funcion '}' ',' error {System.out.println("ERROR EN DECLARACION DISTRIBUIDA. Linea: " + Analizador_Lexico.cantLineas + " falta palabra reservada FOR");}
                        | IMPL FOR ID ':' '{' declaracion_funcion '}' error {System.out.println("ERROR EN DECLARACION DISTRIBUIDA. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
;

declaracion_interfaz    : INTERFACE ID '{' metodos_interfaz '}' ',' 
                        {
                            System.out.println("Linea: " + Analizador_Lexico.cantLineas + " Declaracion INTERFAZ " + $2.sval);
                            Tabla_Simbolos.getAtributos($2.sval).setUso("INTERFACE");
                        }
                        | INTERFACE ID '{' '}' ',' error {System.out.println("ERROR EN INTERFAZ. Linea: " + Analizador_Lexico.cantLineas + " no se puede declarar una interfaz sin metodos");}
                        | INTERFACE ID '{' metodos_interfaz '}' error {System.out.println("ERROR EN INTERFAZ. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
;

metodos_interfaz    : metodo_interfaz ','
	                | metodos_interfaz metodo_interfaz ','
	                | metodo_interfaz {System.out.println("ERROR EN METODO DE INTERFAZ. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
	                | metodos_interfaz metodo_interfaz {System.out.println("ERROR EN METODO DE INTERFAZ. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
;

metodo_interfaz : VOID ID '(' tipo ID ')' {System.out.println("Linea: " + Analizador_Lexico.cantLineas + " Declaracion metodo VOID " + $2.sval);}
                | VOID ID '(' ')' {System.out.println("Linea: " + Analizador_Lexico.cantLineas + " Declaracion metodo VOID " + $2.sval);}
                | VOID ID '(' tipo ID {System.out.println("ERROR EN METODO DE INTERFAZ. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ')'");}
                | VOID ID tipo ID ')' {System.out.println("ERROR EN METODO DE INTERFAZ. Linea: " + Analizador_Lexico.cantLineas + " se esperaba '('");}
                | VOID ID '(' ID ')' error {System.out.println("ERROR EN METODO DE INTERFAZ. Linea: " + Analizador_Lexico.cantLineas + " falta el tipo de " + $4.sval);}
;

sentencia_ejecutable    : asignacion {System.out.println("Linea: " + Analizador_Lexico.cantLineas + " ASIGNACION");}
                        | invocacion_funcion ',' {System.out.println("Linea: " + Analizador_Lexico.cantLineas + " INVOCACION FUNCION");}
                        | invocacion_funcion error {System.out.println("ERROR EN INVOCACION A LA FUNCION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                        | seleccion ',' {System.out.println("Linea: " + Analizador_Lexico.cantLineas + " Sentencia IF");}
                        | seleccion error {System.out.println("ERROR EN SENTENCIA IF. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                        | imprimir ',' {System.out.println("Linea: " + Analizador_Lexico.cantLineas + " SENTENCIA DE IMPRESION");}
                        | imprimir error {System.out.println("ERROR EN SENTENCIA DE IMPRESION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                        | ref_clase '(' ')' ',' {System.out.println("Linea: " + Analizador_Lexico.cantLineas + " REFERENCIA A CLASE");}
                        | ref_clase '(' ')' error {System.out.println("ERROR EN REFERENCIA A CLASE. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                        | sentencia_control ','
                        | sentencia_control error {System.out.println("ERROR EN SENTENCIA FOR. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                        | RETURN ','
                        | RETURN error {System.out.println("ERROR EN RETURN. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
;

asignacion  : ID '=' expresion ','
            | ID MENOS_IGUAL expresion ','
            | ref_clase '=' expresion ','
            | ref_clase MENOS_IGUAL expresion ','
            | ID ',' error  {System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " Se esperaba expresion");}
            | ID MENOS_IGUAL ',' error  {System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " Se esperaba expresion");}
            | ref_clase ','  error {System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " Se esperaba expresion");}
            | ref_clase MENOS_IGUAL ','  error {System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " Se esperaba expresion");}
            | ID '=' expresion error {System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
            | ID MENOS_IGUAL expresion error {System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
            | ref_clase '=' expresion error {System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
            | ref_clase MENOS_IGUAL expresion error {System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
;

expresion   : expresion '+' termino {System.out.println("Linea: " + Analizador_Lexico.cantLineas + " SUMA");}
            | expresion '-' termino {System.out.println("Linea: " + Analizador_Lexico.cantLineas + " RESTA");}
            | termino
;

termino : termino '*' factor {System.out.println("Linea: " + Analizador_Lexico.cantLineas + " MULTIPLICACION");}
        | termino '/' factor {System.out.println("Linea: " + Analizador_Lexico.cantLineas + " DIVISION");}
        | factor
;

factor  : ID
        | constante

constante   : CTE   { chequeoRango($1.sval);
                    Tabla_Simbolos.getAtributos($1.sval).sumarUso();
                    }
            | '-' CTE {
                    chequeoRango("-"+$2.sval);
                    if (Tabla_Simbolos.getAtributos($2.sval).isCero()){
                        Tabla_Simbolos.modificarClave($2.sval, "-"+$2.sval);
                    } else {
                        if (!Tabla_Simbolos.existeSimbolo("-"+$2.sval)){
                            Tabla_Simbolos.insertarSimbolo("-"+$2.sval,new AtributosLexema());
                        }
                    }
                    Tabla_Simbolos.getAtributos("-"+$2.sval).sumarUso();
                  }/* hay que ir a la tabla de simbolos a cambiar el signo, en caso de ser necesario */
;

invocacion_funcion  : ID '(' expresion ')'
                    | ID '(' ')'
;

seleccion   : IF '(' condicion ')' '{' bloque_ejecutable '}' ELSE '{' bloque_ejecutable '}' END_IF
	        | IF '(' condicion ')' '{' bloque_ejecutable '}' END_IF
	        | IF '(' ')' '{' bloque_ejecutable '}' END_IF  error {System.out.println("ERROR EN SENTENCIA IF. Linea: " + Analizador_Lexico.cantLineas + " falta condicion");}
            | IF '(' ')' '{' bloque_ejecutable '}' ELSE '{' bloque_ejecutable '}' END_IF  error {System.out.println("ERROR EN SENTENCIA IF. Linea: " + Analizador_Lexico.cantLineas + " falta condicion");}
	        | IF '(' condicion ')' END_IF  error {System.out.println("ERROR EN SENTENCIA IF. Linea: " + Analizador_Lexico.cantLineas + " cuerpo de IF vacio");}
	        | IF '(' condicion ')' ELSE '{' bloque_ejecutable '}' END_IF error {System.out.println("ERROR EN SENTENCIA IF. Linea: " + Analizador_Lexico.cantLineas + " cuerpo de IF vacio");}

;

condicion   : expresion MAYOR_IGUAL expresion
            | expresion MENOR_IGUAL expresion
            | expresion '<' expresion
            | expresion '>' expresion
            | expresion IGUAL expresion
            | expresion DISTINTO expresion
;

imprimir    : PRINT CADENA
            | PRINT error {System.out.println("ERROR EN SENTENCIA DE IMPRESION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba una cadena de caracteres");}
;

ref_clase   : ID '.' ID
	        | ref_clase '.' ID
;

sentencia_control   : FOR ID IN RANGE '(' encabezado_for ')' '{' bloque_ejecutable '}' {System.out.println("Linea: " + Analizador_Lexico.cantLineas + " Sentencia FOR");}
                    | FOR ID IN RANGE '(' ')' '{' bloque_ejecutable '}' {System.out.println("ERROR EN SENTENCIA FOR. Linea: " + Analizador_Lexico.cantLineas + " falta encabezado");}
                    | FOR IN RANGE '(' encabezado_for ')' '{' bloque_ejecutable '}' error {System.out.println("ERROR EN SENTENCIA FOR. Linea: " + Analizador_Lexico.cantLineas + " se esperaba un identificador");}
                    | FOR ID RANGE '(' encabezado_for ')' '{' bloque_ejecutable '}' {System.out.println("ERROR EN SENTENCIA FOR. Linea: " + Analizador_Lexico.cantLineas + " falta la palabra reservada IN");}
                    | FOR ID IN '(' encabezado_for ')' '{' bloque_ejecutable '}' {System.out.println("ERROR EN SENTENCIA FOR. Linea: " + Analizador_Lexico.cantLineas + " falta la palabra reservada RANGE");}
                    | FOR ID IN RANGE '(' encabezado_for ')' error {System.out.println("ERROR EN SENTENCIA FOR. Linea: " + Analizador_Lexico.cantLineas + " no se puede definir un FOR sin cuerpo");}
;

encabezado_for  : constante ';' constante ';' constante {System.out.println("Linea: " + Analizador_Lexico.cantLineas + " ENCABEZADO FOR");}
                | constante ';' constante error {System.out.println("ERROR EN ENCABEZADO FOR. Linea: " + Analizador_Lexico.cantLineas + " falta una constante");}
                | constante error {System.out.println("ERROR EN ENCABEZADO FOR. Linea: " + Analizador_Lexico.cantLineas + " faltan constantes");}



%%
/* CODE SECTION */
public String tipo = "";
public String ambito = "";

public void chequeoRango(String cte){
    if (cte.contains("_s")){
        int cteint = Integer.parseInt(cte.substring(0, cte.length()-2));
        double min = Math.pow(-2,7);
        double max = Math.pow(2,7)-1;
        if ((cteint < min)||(cteint>max)){
            System.out.println("ERROR. Linea "+Analizador_Lexico.cantLineas+": Constante fuera de rango");
        }
    } else if (cte.contains("_ul")) {
        long cteint_largo = Integer.parseInt(cte.substring(0, cte.length()-3));
        double min = 0;
        double max = Math.pow(2,32)-1;
        if ((cteint_largo < min)||(cteint_largo>max)){
            System.out.println("ERROR. Linea "+Analizador_Lexico.cantLineas+": Constante fuera de rango");
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
        }
    }
}

public int yylex(){
    int tok = Analizador_Lexico.yylex();
    yylval = new ParserVal(Analizador_Lexico.buffer);
    return tok;
}
public void yyerror(String s){}