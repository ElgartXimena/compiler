%{

%}

/* YACC DECLARATIONS */
%token ID CTE CADENA IF ELSE END_IF PRINT CLASS VOID SHORT ULONG DOUBLE FOR IN RANGE IMPL INTERFACE IMPLEMENT RETURN MENOS_IGUAL MAYOR_IGUAL MENOR_IGUAL IGUAL DISTINTO 

/* Grammar definition */
%%
programa: '{' bloque_sentencias '}' 
;

bloque_sentencias   : bloque_sentencias sentencia ','
                    | sentencia ','
;

sentencia       : sentencia_declarativa 
                | sentencia_ejecutable 
                | sentencia_control 
                | RETURN 
;

bloque_declarativo  : sentencia_declarativa 
                    | '{' bloque_declarativo sentencia_declarativa '}'
;

sentencia_declarativa   : tipo lista_variables
                        | declaracion_funcion 
                        | declaracion_clase 
                        | declaracion_distribuida
                        | declaracion_interfaz 
;

declaracion_clase   : CLASS ID '{' bloque_declarativo '}' /* accion para ID que establezca como tipo “CLASE” */
                    | CLASS ID IMPLEMENT ID '{' bloque_declarativo '}' /* accion que chequea si ID_2 es una interfaz y asignar tipo CLASE a ID_1 */
;
declaracion_distribuida : IMPL FOR ID ':' '{' declaracion_funcion '}' 
; /* accion para fijarse que id sea una clase */

declaracion_interfaz    : INTERFACE ID '{' metodos_interfaz '}' 
; /* accion para ID que establezca como tipo “INTERFACE” */

metodos_interfaz    : metodo_interfaz ','
	                | metodos_interfaz metodo_interfaz
;

metodo_interfaz : VOID ID '(' tipo ID ')' 
                | VOID ID '(' ')' 
;

declaracion_funcion : VOID ID '(' ')' '{' cuerpo_funcion '}' 
                    | VOID ID '(' tipo ID ')' '{' cuerpo_funcion '}' 
;

cuerpo_funcion  : sentencia 
                | cuerpo_funcion sentencia
;

tipo    : SHORT 
        | ULONG 
        | DOUBLE  
        | ID 
; /* accion que chequee si el uso es “CLASE” */

lista_variables : ID 
                | lista_variables ';' ID
;

sentencia_ejecutable    : asignacion 
                        | invocacion_funcion 
                        | seleccion 
                        | imprimir 
                        | ref_clase '(' ')' 
;

ref_clase   : ID '.' ID
	        | ref_clase '.' ID 
;

asignacion  : ID '=' expresion 
            | ID MENOS_IGUAL expresion 
            | ref_clase '=' expresion
            | ref_clase MENOS_IGUAL expresion 
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
        | CTE   {   chequeoRango($1.sval);
                    al.getTablaSimbolos().getAtributos($1.sval).sumarUso();
                }
        | '-' CTE {
                    chequeoRango("-"+$2.sval);
                    if (al.getTablaSimbolos().getAtributos($2.sval).isCero()){
                        al.getTablaSimbolos().modificarClave($2.sval, "-"+$2.sval);
                    } else {
                        if (!al.getTablaSimbolos().existeSimbolo("-"+$2.sval)){
                            al.getTablaSimbolos().insertarSimbolo("-"+$2.sval,new AtributosLexema());
                        }
                        al.getTablaSimbolos().getAtributos("-"+$2.sval).sumarUso();
                    }
                  }/* hay que ir a la tabla de simbolos a cambiar el signo, en caso de ser necesario */
;

invocacion_funcion  : ID '(' expresion ')' 
                    | ID '(' ')' 
;

seleccion   : IF '(' condicion ')' bloque_ejecutable ELSE bloque_ejecutable END_IF 
	        | IF '(' condicion ')' bloque_ejecutable END_IF 
;

condicion   : expresion MAYOR_IGUAL expresion 
            | expresion MENOR_IGUAL expresion
            | expresion '<' expresion
            | expresion '>' expresion 
            | expresion IGUAL expresion
            | expresion DISTINTO expresion 
;

bloque_ejecutable   : sentencia_ejecutable
                    | '{' bloque_ejecutable sentencia_ejecutable '}' 
;

imprimir    : PRINT CADENA
;

sentencia_control   : FOR ID IN RANGE '(' CTE ; CTE ; CTE ')' bloque_ejecutable
;
%%
/* CODE SECTION */

public void chequeoRango(String cte){
    if (cte.contains("_s")){
        int cteint = Integer.parseInt(cte.substring(0, cte.length()-2));
        double min = Math.pow(-2,7);
        double max = Math.pow(2,7)-1;
        if ((cteint < min)||(cteint>max)){
            System.out.println("Constante fuera de rango");
        }
    } else if (cte.contains("_ul")) {
        long cteint_largo = Integer.parseInt(cte.substring(0, cte.length()-3));
        double min = 0;
        double max = Math.pow(2,32)-1;
        if ((cteint_largo < min)||(cteint_largo>max)){
            System.out.println("Constante fuera de rango");
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
            System.out.println("Constante fuera de rango");
        }
    }
}

public int yylex(Analizador_Lexico al){
    int tok = al.yylex();
    yylval = new ParserVal(al.getBuffer());
    return tok;
}
public void yyerror(String s){}