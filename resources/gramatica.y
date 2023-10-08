%{

%}

/* YACC DECLARATIONS */
%token ID CTE CADENA IF ELSE END_IF PRINT CLASS VOID SHORT ULONG DOUBLE FOR IN RANGE IMPL INTERFACE IMPLEMENT RETURN MENOS_IGUAL MAYOR_IGUAL MENOR_IGUAL IGUAL DISTINTO

/* Grammar definition */
%%
programa    : '{' bloque_sentencias '}'
            | '{' bloque_sentencias  {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba '}'");}
            | bloque_sentencias '}' {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba '{'");}
;

bloque_sentencias   : bloque_sentencias sentencia ','
                    | sentencia ','
                    | sentencia {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba ','");}
;

sentencia       : sentencia_declarativa 
                | sentencia_ejecutable 
                | sentencia_control 
                | RETURN
;

bloque_declarativo  : sentencia_declarativa 
                    | '{' bloque_declarativo sentencia_declarativa '}'
                    | '{' bloque_declarativo sentencia_declarativa {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba '}'");}
                    | bloque_declarativo sentencia_declarativa '}' {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba '{'");}
;

sentencia_declarativa   : tipo lista_variables
                        | declaracion_funcion 
                        | declaracion_clase 
                        | declaracion_distribuida
                        | declaracion_interfaz
;

declaracion_clase   : CLASS ID '{' bloque_declarativo '}' {
                                                            System.out.println("Linea: " + al.getLinea() + " Declaracion CLASE " + $2.sval);
                                                            /* accion para ID que establezca como tipo “CLASE” */
                                                            al.getTablaSimbolos().getAtributos($2.sval).setTipo("CLASE");
                                                            }
                    | CLASS ID IMPLEMENT ID '{' bloque_declarativo '}' {
                                                                        System.out.println("Linea: " + al.getLinea() + " Declaracion CLASE " + $2.sval);
                                                                        /* accion que chequea si ID_2 es una interfaz y asignar tipo CLASE a ID_1 */
                                                                            if (al.getTablaSimbolos().getAtributos($4.sval).isTipo("INTERFACE")){
                                                                                al.getTablaSimbolos().getAtributos($2.sval).setTipo("CLASE");
                                                                            } else {
                                                                                System.out.println("Error linea "+al.getLinea()+": "+$4.sval+" no es un INTERFACE");
                                                                            }
                                                                        }
                    | CLASS ID '{' bloque_declarativo {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba '}'");}
                    | CLASS ID bloque_declarativo '}' {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba '{'");}
                    | CLASS ID IMPLEMENT ID '{' bloque_declarativo {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba '}'");}
                    | CLASS ID IMPLEMENT ID bloque_declarativo '}' {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba '{'");}
                    | CLASS ID '{' '}' error {System.out.println("ERROR. Linea: " + al.getLinea() + " no se puede definir una clase sin cuerpo");}
                    | CLASS ID IMPLEMENT ID '{' '}' error {System.out.println("ERROR. Linea: " + al.getLinea() + " no se puede definir una clase sin cuerpo");}
                    | CLASS error {System.out.println("ERROR. Linea: " + al.getLinea() + " revisar declaracion de clase);}

;
declaracion_distribuida : IMPL FOR ID ':' '{' declaracion_funcion '}' {
                                                                        System.out.println("Linea: " + al.getLinea() + " Declaracion DISTRIBUIDA para " + $3.sval);
                                                                        /* accion para fijarse que id sea una clase */
                                                                            if (!al.getTablaSimbolos().getAtributos($3.sval).isTipo("CLASE")){
                                                                                System.out.println("Error linea "+al.getLinea()+": "+$3.sval+" no es una clase");
                                                                            }
                                                                        }
                        | IMPL FOR ID ':' '{' declaracion_funcion {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba '}'");}
                        | IMPL FOR ID ':' declaracion_funcion '}' {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba '{'");}
                        | IMPL FOR ID '{' declaracion_funcion '}' {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba ':'");}
                        | IMPL FOR ID ':' '{' '}' error {System.out.println("ERROR. Linea: " + al.getLinea() + " no se puede definir una declaracion distribuida sin cuerpo");}
                        | IMPL ID ':' '{' declaracion_funcion '}' error {System.out.println("ERROR. Linea: " + al.getLinea() + " falta palabra reservada FOR");}
;

declaracion_interfaz    : INTERFACE ID '{' metodos_interfaz '}' {
                                                                    System.out.println("Linea: " + al.getLinea() + " Declaracion INTERFAZ " + $2.sval);
                                                                    /* accion para ID que establezca como tipo “INTERFACE” */
                                                                    al.getTablaSimbolos().getAtributos($2.sval).setTipo("INTERFACE");
                                                                }
                        | INTERFACE ID '{' metodos_interfaz {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba '}'");}
                        | INTERFACE ID metodos_interfaz '}' {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba '{'");}
                        | INTERFACE ID '{' '}' error {System.out.println("ERROR. Linea: " + al.getLinea() + " no se puede declarar una interfaz sin metodos");}
;

metodos_interfaz    : metodo_interfaz ','
	                | metodos_interfaz metodo_interfaz ','
	                | metodo_interfaz {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba ','");}
	                | metodos_interfaz metodo_interfaz {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba ','");}
;

metodo_interfaz : VOID ID '(' tipo ID ')' {System.out.println("Linea: " + al.getLinea() + " Declaracion metodo VOID " + $2.sval);}
                | VOID ID '(' ')' {System.out.println("Linea: " + al.getLinea() + " Declaracion metodo VOID " + $2.sval);}
                | VOID ID '(' tipo ID {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba ')'");}
                | VOID ID tipo ID ')' {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba '('");}
                | VOID ID '(' ID ')' error {System.out.println("ERROR. Linea: " + al.getLinea() + " falta el tipo de " + $4.sval);}
;

declaracion_funcion : VOID ID '(' ')' '{' cuerpo_funcion '}' {System.out.println("Linea: " + al.getLinea() + " Declaracion funcion VOID " + $2.sval);}
                    | VOID ID '(' tipo ID ')' '{' cuerpo_funcion '}' {System.out.println("Linea: " + al.getLinea() + " Declaracion funcion VOID " + $2.sval);}
                    | VOID ID '(' tipo ID '{' cuerpo_funcion '}' {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba ')'");}
                    | VOID ID tipo ID ')' '{' cuerpo_funcion '}' {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba '('");}
                    | VOID ID '(' ID ')' '{' cuerpo_funcion '}' error {System.out.println("ERROR. Linea: " + al.getLinea() + " falta el tipo de " + $4.sval);}
                    | VOID ID '(' tipo ID ')' '{'  '}' error {System.out.println("ERROR. Linea: " + al.getLinea() + " no se puede declarar una funcion sin cuerpo;}
                    | VOID ID '(' ')' '{'  '}' error {System.out.println("ERROR. Linea: " + al.getLinea() + " no se puede declarar una funcion sin cuerpo;}
;

cuerpo_funcion  : sentencia 
                | cuerpo_funcion sentencia
;

tipo    : SHORT 
        | ULONG 
        | DOUBLE  
        | ID 
        {
         /* accion que chequee si el tipo es “CLASE” (id no puede ser un tipo si no es una clase)*/
         if (!al.getTablaSimbolos().getAtributos($1.sval).isTipo("CLASE")){
            System.out.println("Error linea "+al.getLinea()+": "+$1.sval+" no es tipo CLASE");
         }
        }
;

lista_variables : ID 
                | lista_variables ';' ID
                | lista_variables error {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba ';'");}
;

sentencia_ejecutable    : asignacion {System.out.println("Linea: " + al.getLinea() + " ASIGNACION");}
                        | invocacion_funcion {System.out.println("Linea: " + al.getLinea() + " INVOCACION FUNCION");}
                        | seleccion {System.out.println("Linea: " + al.getLinea() + " Sentencia IF");}
                        | imprimir 
                        | ref_clase '(' ')'
                        | ref_clase {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaban '( )'");}
;

ref_clase   : ID '.' ID
	        | ref_clase '.' ID
	        | ID error {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba '.'");}
	        | ref_clase error {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba '.'");}
;

asignacion  : ID '=' expresion 
            | ID MENOS_IGUAL expresion 
            | ref_clase '=' expresion
            | ref_clase MENOS_IGUAL expresion
            | ID error {System.out.println("ERROR. Linea: " + al.getLinea() + " asignacion mal definida");}
            | ref_clase error {System.out.println("ERROR. Linea: " + al.getLinea() + " asignacion mal definida");}
;

expresion   : expresion '+' termino
            | expresion '-' termino 
            | termino 
;

termino : termino '*' factor 
        | termino '/' factor 
        | factor 
;

factor  : ID 
        | CTE   {   chequeoRango($1.sval, al);
                    al.getTablaSimbolos().getAtributos($1.sval).sumarUso();
                }
        | '-' CTE {
                    chequeoRango("-"+$2.sval, al);
                    if (al.getTablaSimbolos().getAtributos($2.sval).isCero()){
                        al.getTablaSimbolos().modificarClave($2.sval, "-"+$2.sval);
                    } else {
                        if (!al.getTablaSimbolos().existeSimbolo("-"+$2.sval)){
                            al.getTablaSimbolos().insertarSimbolo("-"+$2.sval,new AtributosLexema());
                        }
                    }
                    al.getTablaSimbolos().getAtributos("-"+$2.sval).sumarUso();
                  }/* hay que ir a la tabla de simbolos a cambiar el signo, en caso de ser necesario */
;

invocacion_funcion  : ID '(' expresion ')' 
                    | ID '(' ')' 
;

seleccion   : IF '(' condicion ')' bloque_ejecutable ELSE bloque_ejecutable END_IF 
	        | IF '(' condicion ')' bloque_ejecutable END_IF
	        | IF '(' ')' bloque_ejecutable END_IF {System.out.println("ERROR. Linea: " + al.getLinea() + " falta condicion");}
            | IF '(' condicion ')' bloque_ejecutable ELSE bloque_ejecutable END_IF {System.out.println("ERROR. Linea: " + al.getLinea() + " falta condicion");}
	        | IF '(' condicion ')' error {System.out.println("ERROR. Linea: " + al.getLinea() + " cuerpo de IF vacio");}
;

condicion   : expresion MAYOR_IGUAL expresion 
            | expresion MENOR_IGUAL expresion
            | expresion '<' expresion
            | expresion '>' expresion 
            | expresion IGUAL expresion
            | expresion DISTINTO expresion
            | expresion error {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba una comparacion");}
;

bloque_ejecutable   : sentencia_ejecutable
                    | '{' bloque_ejecutable sentencia_ejecutable '}'
                    | '{' bloque_ejecutable sentencia_ejecutable {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba '}'");}
                    | bloque_ejecutable sentencia_ejecutable '}' {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba '{'");}
;

imprimir    : PRINT CADENA
            | PRINT error {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba una cadena de caracteres");}
;

sentencia_control   : FOR ID IN RANGE '(' CTE ; CTE ; CTE ')' bloque_ejecutable {System.out.println("Linea: " + al.getLinea() + " Sentencia FOR");}
                    | FOR ID IN RANGE '(' CTE ; CTE ')' bloque_ejecutable {System.out.println("ERROR. Linea: " + al.getLinea() + " falta una constante");}
                    | FOR ID IN RANGE '(' CTE ')' bloque_ejecutable {System.out.println("ERROR. Linea: " + al.getLinea() + " faltan constantes");}
                    | FOR ID IN RANGE '(' CTE ; CTE ; CTE ')' bloque_ejecutable {System.out.println("ERROR. Linea: " + al.getLinea() + " faltan constantes");}
                    | FOR IN RANGE '(' CTE ; CTE ; CTE ')' bloque_ejecutable error {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba un identificador");}
                    | FOR ID RANGE '(' CTE ; CTE ; CTE ')' bloque_ejecutable {System.out.println("ERROR. Linea: " + al.getLinea() + " falta la palabra reservada IN");}
                    | FOR ID IN '(' CTE ; CTE ; CTE ')' bloque_ejecutable {System.out.println("ERROR. Linea: " + al.getLinea() + " falta la palabra reservada RANGE");}
                    | FOR ID IN RANGE '(' CTE ; CTE ; CTE ')' error {System.out.println("ERROR. Linea: " + al.getLinea() + " no se puede definir un FOR sin cuerpo");}
;
%%
/* CODE SECTION */

public void chequeoRango(String cte, Analizador_Lexico al){
    if (cte.contains("_s")){
        int cteint = Integer.parseInt(cte.substring(0, cte.length()-2));
        double min = Math.pow(-2,7);
        double max = Math.pow(2,7)-1;
        if ((cteint < min)||(cteint>max)){
            System.out.println("ERROR. Linea "+al.getLinea()+": Constante entera fuera de rango");
        }
    } else if (cte.contains("_ul")) {
        long cteint_largo = Integer.parseInt(cte.substring(0, cte.length()-3));
        double min = 0;
        double max = Math.pow(2,32)-1;
        if ((cteint_largo < min)||(cteint_largo>max)){
            System.out.println("ERROR. Linea "+al.getLinea()+": Constante entera larga fuera de rango");
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
            System.out.println("ERROR. Linea "+al.getLinea()+": Constante flotante fuera de rango");
        }
    }
}

public int yylex(Analizador_Lexico al){
    int tok = al.yylex();
    yylval = new ParserVal(al.getBuffer());
    return tok;
}
public void yyerror(String s){}