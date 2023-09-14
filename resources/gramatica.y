%{

%}

/* YACC DECLARATIONS */
%token ID CTE CADENA IF ELSE END_IF PRINT CLASS VOID SHORT ULONG DOUBLE FOR IN RANGE IMPL INTERFACE IMPLEMENT RETURN MENOS_IGUAL MAYOR_IGUAL MENOR_IGUAL IGUAL DISTINTO 

/* Grammar definition */
%%
programa: '{' bloque_sentencias '}' 
;

bloque_sentencias   : bloque_sentencias sentencia
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
        | CTE 
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