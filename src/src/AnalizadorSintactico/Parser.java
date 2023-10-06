//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 2 "gramatica.y"

//#line 19 "Parser.java"

package AnalizadorSintactico;
import AnalizadorLexico.Analizador_Lexico;
import AnalizadorLexico.AtributosLexema;
import AnalizadorLexico.Tabla_Simbolos;

public class Parser
{

    boolean yydebug;        //do I want debug output?
    int yynerrs;            //number of errors so far
    int yyerrflag;          //was there an error?
    int yychar;             //the current working character

    //########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
    void debug(String msg)
    {
        if (yydebug)
            System.out.println(msg);
    }

    //########## STATE STACK ##########
    final static int YYSTACKSIZE = 500;  //maximum stack size
    int statestk[] = new int[YYSTACKSIZE]; //state stack
    int stateptr;
    int stateptrmax;                     //highest index of stackptr
    int statemax;                        //state when highest index reached
    //###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
    final void state_push(int state)
    {
        try {
            stateptr++;
            statestk[stateptr]=state;
        }
        catch (ArrayIndexOutOfBoundsException e) {
            int oldsize = statestk.length;
            int newsize = oldsize * 2;
            int[] newstack = new int[newsize];
            System.arraycopy(statestk,0,newstack,0,oldsize);
            statestk = newstack;
            statestk[stateptr]=state;
        }
    }
    final int state_pop()
    {
        return statestk[stateptr--];
    }
    final void state_drop(int cnt)
    {
        stateptr -= cnt;
    }
    final int state_peek(int relative)
    {
        return statestk[stateptr-relative];
    }
    //###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
    final boolean init_stacks()
    {
        stateptr = -1;
        val_init();
        return true;
    }
    //###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
    void dump_stacks(int count)
    {
        int i;
        System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
        for (i=0;i<count;i++)
            System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
        System.out.println("======================");
    }


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


    String   yytext;//user variable to return contextual strings
    ParserVal yyval; //used to return semantic vals from action routines
    ParserVal yylval;//the 'lval' (result) I got from yylex()
    ParserVal valstk[];
    int valptr;
    //###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
    void val_init()
    {
        valstk=new ParserVal[YYSTACKSIZE];
        yyval=new ParserVal();
        yylval=new ParserVal();
        valptr=-1;
    }
    void val_push(ParserVal val)
    {
        if (valptr>=YYSTACKSIZE)
            return;
        valstk[++valptr]=val;
    }
    ParserVal val_pop()
    {
        if (valptr<0)
            return new ParserVal();
        return valstk[valptr--];
    }
    void val_drop(int cnt)
    {
        int ptr;
        ptr=valptr-cnt;
        if (ptr<0)
            return;
        valptr = ptr;
    }
    ParserVal val_peek(int relative)
    {
        int ptr;
        ptr=valptr-relative;
        if (ptr<0)
            return new ParserVal();
        return valstk[ptr];
    }
    final ParserVal dup_yyval(ParserVal val)
    {
        ParserVal dup = new ParserVal();
        dup.ival = val.ival;
        dup.dval = val.dval;
        dup.sval = val.sval;
        dup.obj = val.obj;
        return dup;
    }
    //#### end semantic value section ####
    public final static short ID=257;
    public final static short CTE=258;
    public final static short CADENA=259;
    public final static short IF=260;
    public final static short ELSE=261;
    public final static short END_IF=262;
    public final static short PRINT=263;
    public final static short CLASS=264;
    public final static short VOID=265;
    public final static short SHORT=266;
    public final static short ULONG=267;
    public final static short DOUBLE=268;
    public final static short FOR=269;
    public final static short IN=270;
    public final static short RANGE=271;
    public final static short IMPL=272;
    public final static short INTERFACE=273;
    public final static short IMPLEMENT=274;
    public final static short RETURN=275;
    public final static short MENOS_IGUAL=276;
    public final static short MAYOR_IGUAL=277;
    public final static short MENOR_IGUAL=278;
    public final static short IGUAL=279;
    public final static short DISTINTO=280;
    public final static short YYERRCODE=256;
    final static short yylhs[] = {                           -1,
            0,    1,    1,    2,    2,    2,    2,    6,    6,    3,
            3,    3,    3,    3,   10,   10,   11,   12,   13,   13,
            14,   14,    9,    9,   15,   15,    7,    7,    7,    7,
            8,    8,    4,    4,    4,    4,    4,   20,   20,   16,
            16,   16,   16,   21,   21,   21,   22,   22,   22,   23,
            23,   23,   17,   17,   18,   18,   24,   24,   24,   24,
            24,   24,   25,   25,   19,    5,
    };
    final static short yylen[] = {                            2,
            3,    3,    2,    1,    1,    1,    1,    1,    4,    2,
            1,    1,    1,    1,    5,    7,    7,    5,    2,    2,
            6,    4,    7,    9,    1,    2,    1,    1,    1,    1,
            1,    3,    1,    1,    1,    1,    3,    3,    3,    3,
            3,    3,    3,    3,    3,    1,    3,    3,    1,    1,
            1,    2,    4,    3,    8,    6,    3,    3,    3,    3,
            3,    3,    1,    4,    2,   10,
    };
    final static short yydefred[] = {                         0,
            0,    0,    0,    0,    0,    0,    0,   27,   28,   29,
            0,    0,    0,    7,    0,    0,    4,    5,    6,    0,
            11,   12,   13,   14,   33,   34,   35,   36,    0,    0,
            0,    0,    0,    0,   65,    0,    0,    0,    0,    0,
            1,    0,    3,   31,    0,    0,    0,    0,    0,   50,
            51,    0,    0,    0,   49,   54,    0,   38,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    2,    0,    0,
            37,   39,    0,   52,    0,    0,    0,    0,   53,    0,
            0,    0,    0,    0,    0,    0,    0,   30,    0,    8,
            0,    0,    0,    0,    0,    0,    0,    0,   32,    0,
            0,   47,   48,    0,    0,    0,    0,    0,    0,    0,
            0,   63,    0,    0,    0,   15,    0,    0,    0,    0,
            0,   18,   20,   19,    0,    0,   56,    0,    0,   25,
            0,    0,    0,    0,    0,    0,    0,   16,    9,   23,
            26,    0,    0,   17,   22,    0,   64,   55,    0,    0,
            0,   24,    0,   21,   66,
    };
    final static short yydgoto[] = {                          2,
            15,  130,   17,   18,   19,   91,   20,   45,   21,   22,
            23,   24,   97,   98,  131,   25,   26,   27,   28,   29,
            53,   54,   55,   61,  113,
    };
    final static short yysindex[] = {                      -111,
            -174,    0,  -23,  -22, -237, -232, -215,    0,    0,    0,
            -213, -223, -204,    0,  -93,   14,    0,    0,    0, -202,
            0,    0,    0,    0,    0,    0,    0,    0,    1,  -16,
            -40, -189,  -16,  -16,    0, -117,   30, -198, -179,  -26,
            0,   62,    0,    0,   50,  -16,   70, -147,  -16,    0,
            0, -146,   -6,  -11,    0,    0,   18,    0,   -6,   11,
            78, -137,  -59,  118, -145,   63, -141,    0, -132,   -6,
            0,    0,   -6,    0,  -16,  -16,  -16,  -16,    0,  -16,
            -16,  -16,  -16,  -16,  -16, -116,    4,    0,  -59,    0,
            3,    8, -128,   93,   17, -115, -114,   99,    0,  -11,
            -11,    0,    0,   -6,   -6,   -6,   -6,   -6,   -6,  -23,
            -116,    0, -185,  -59, -150,    0, -174,  104, -112, -113,
            108,    0,    0,    0, -247, -116,    0,   28,   29,    0,
            -76,   32, -102,   33,  122,   36, -100,    0,    0,    0,
            0, -174,  -92,    0,    0,  -88,    0,    0,  134,  124,
            137,    0, -116,    0,    0,
    };
    final static short yyrindex[] = {                         0,
            0,    0,  -74,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,  105,    0,    0,    0,    0,    0,
            0,    0,   35,  -41,    0,    0,    0,    0,   52,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,   69,
            0,    0,   86,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,  -17,
            7,    0,    0,  144,  145,  154,  159,  160,  161,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,
    };
    final static short yygindex[] = {                         0,
            0,   19,  -49,  -51,    0,  -81,  -55,    0,   83,    0,
            0,    0,    0,  107,   68,    0,    0,    0,    0,    0,
            54,    6,   27,    0,  -96,
    };
    final static int YYTABLESIZE=409;
    static short yytable[];
    static { yytable();}
    static void yytable(){
        yytable = new short[]{                         46,
                56,   46,   46,   46,   52,   63,  111,  115,   93,  110,
                122,    1,    4,   90,  125,    5,   31,   34,   46,   16,
                46,   35,   32,   44,   36,   44,   44,   44,   52,  137,
                77,   41,  128,   42,  112,   78,   75,   33,   76,   90,
                47,   37,   44,   38,   44,   39,   48,   45,  140,   45,
                45,   45,   40,   75,   44,   76,  155,   43,   79,  112,
                75,   49,   76,   89,   90,  129,   45,   58,   45,   64,
                84,   65,   85,  136,  112,  126,  127,   66,   41,  146,
                100,  101,    3,   46,   57,    4,   59,   60,    5,    6,
                7,    8,    9,   10,   11,   40,   67,   12,   13,   70,
                14,  112,   73,  102,  103,   68,   88,   44,   69,   72,
                71,   74,   43,    6,    7,    8,    9,   10,   86,   87,
                95,   12,   13,   96,   99,   94,  114,  116,  118,   42,
                117,   45,  119,  104,  105,  106,  107,  108,  109,  120,
                110,  121,  124,    4,  132,  133,    5,  135,   10,  141,
                96,    7,  138,  139,  142,  143,   62,  144,   92,   41,
                147,  148,  145,    3,  153,  150,    4,  141,  151,    5,
                6,    7,    8,    9,   10,   11,   40,  154,   12,   13,
                3,   14,   30,    4,   57,   58,    5,    6,    7,    8,
                9,   10,   11,   43,   61,   12,   13,   88,   14,   62,
                59,   60,  134,  123,    6,    7,    8,    9,   10,  149,
                42,    0,   12,   13,    0,   46,   50,   51,   46,   46,
                46,   46,   46,   46,   46,   46,   46,   46,    0,   10,
                46,   46,    0,   46,    0,   46,   46,   46,   46,   44,
                50,   51,   44,   44,   44,   44,   44,   44,   44,   44,
                44,   44,   30,    0,   44,   44,    0,   44,  152,   44,
                44,   44,   44,   45,    0,    0,   45,   45,   45,   45,
                45,   45,   45,   45,   45,   45,   46,    0,   45,   45,
                0,   45,    0,   45,   45,   45,   45,   80,   81,   82,
                83,   41,    0,    0,   41,   41,   41,   41,   41,   41,
                41,   41,   41,   41,    0,    0,   41,   41,   40,   41,
                0,   40,   40,   40,   40,   40,   40,   40,   40,   40,
                40,    0,    0,   40,   40,   43,   40,    0,   43,   43,
                43,   43,   43,   43,   43,   43,   43,   43,    0,    0,
                43,   43,   42,   43,    0,   42,   42,   42,   42,   42,
                42,   42,   42,   42,   42,    0,    0,   42,   42,    0,
                42,   10,    0,    0,   10,    0,    0,   10,   10,   10,
                10,   10,   10,   10,   88,    0,   10,   10,   88,   10,
                0,    0,    0,    8,    9,   10,    0,    8,    9,   10,
                3,    0,    0,    4,    0,    0,    5,    6,    7,    8,
                9,   10,   11,    0,    0,   12,   13,    0,   14,
        };
    }
    static short yycheck[];
    static { yycheck(); }
    static void yycheck() {
        yycheck = new short[] {                         41,
                41,   43,   44,   45,   45,  123,  123,   89,   64,  257,
                125,  123,  260,   63,  111,  263,   40,   40,   60,    1,
                62,  259,   46,   41,  257,   43,   44,   45,   45,  126,
                42,  125,  114,   15,   86,   47,   43,   61,   45,   89,
                40,  257,   60,  257,   62,  269,   46,   41,  125,   43,
                44,   45,  257,   43,  257,   45,  153,   44,   41,  111,
                43,   61,   45,  123,  114,  115,   60,  257,   62,   40,
                60,  270,   62,  125,  126,  261,  262,  257,   44,  135,
                75,   76,  257,  125,   31,  260,   33,   34,  263,  264,
                265,  266,  267,  268,  269,   44,  123,  272,  273,   46,
                275,  153,   49,   77,   78,   44,  257,  125,   59,  257,
                41,  258,   44,  264,  265,  266,  267,  268,   41,  257,
                58,  272,  273,  265,  257,  271,  123,  125,  257,   44,
                123,  125,   40,   80,   81,   82,   83,   84,   85,  123,
                257,  257,   44,  260,   41,  258,  263,   40,   44,  131,
                265,  265,  125,  125,  123,  258,  274,  125,   41,  125,
                125,  262,   41,  257,   41,  258,  260,  149,  257,  263,
                264,  265,  266,  267,  268,  269,  125,   41,  272,  273,
                257,  275,  257,  260,   41,   41,  263,  264,  265,  266,
                267,  268,  269,  125,   41,  272,  273,  257,  275,   41,
                41,   41,  120,   97,  264,  265,  266,  267,  268,  142,
                125,   -1,  272,  273,   -1,  257,  257,  258,  260,  261,
                262,  263,  264,  265,  266,  267,  268,  269,   -1,  125,
                272,  273,   -1,  275,   -1,  277,  278,  279,  280,  257,
                257,  258,  260,  261,  262,  263,  264,  265,  266,  267,
                268,  269,  276,   -1,  272,  273,   -1,  275,  125,  277,
                278,  279,  280,  257,   -1,   -1,  260,  261,  262,  263,
                264,  265,  266,  267,  268,  269,  276,   -1,  272,  273,
                -1,  275,   -1,  277,  278,  279,  280,  277,  278,  279,
                280,  257,   -1,   -1,  260,  261,  262,  263,  264,  265,
                266,  267,  268,  269,   -1,   -1,  272,  273,  257,  275,
                -1,  260,  261,  262,  263,  264,  265,  266,  267,  268,
                269,   -1,   -1,  272,  273,  257,  275,   -1,  260,  261,
                262,  263,  264,  265,  266,  267,  268,  269,   -1,   -1,
                272,  273,  257,  275,   -1,  260,  261,  262,  263,  264,
                265,  266,  267,  268,  269,   -1,   -1,  272,  273,   -1,
                275,  257,   -1,   -1,  260,   -1,   -1,  263,  264,  265,
                266,  267,  268,  269,  257,   -1,  272,  273,  257,  275,
                -1,   -1,   -1,  266,  267,  268,   -1,  266,  267,  268,
                257,   -1,   -1,  260,   -1,   -1,  263,  264,  265,  266,
                267,  268,  269,   -1,   -1,  272,  273,   -1,  275,
        };
    }
    final static short YYFINAL=2;
    final static short YYMAXTOKEN=280;
    final static String yyname[] = {
            "end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
            null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
            null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'","','",
            "'-'","'.'","'/'",null,null,null,null,null,null,null,null,null,null,"':'","';'",
            "'<'","'='","'>'",null,null,null,null,null,null,null,null,null,null,null,null,
            null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
            null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
            null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
            "'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,null,null,
            null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
            null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
            null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
            null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
            null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
            null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
            null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
            null,null,null,null,null,null,null,"ID","CTE","CADENA","IF","ELSE","END_IF",
            "PRINT","CLASS","VOID","SHORT","ULONG","DOUBLE","FOR","IN","RANGE","IMPL",
            "INTERFACE","IMPLEMENT","RETURN","MENOS_IGUAL","MAYOR_IGUAL","MENOR_IGUAL",
            "IGUAL","DISTINTO",
    };
    final static String yyrule[] = {
            "$accept : programa",
            "programa : '{' bloque_sentencias '}'",
            "bloque_sentencias : bloque_sentencias sentencia ','",
            "bloque_sentencias : sentencia ','",
            "sentencia : sentencia_declarativa",
            "sentencia : sentencia_ejecutable",
            "sentencia : sentencia_control",
            "sentencia : RETURN",
            "bloque_declarativo : sentencia_declarativa",
            "bloque_declarativo : '{' bloque_declarativo sentencia_declarativa '}'",
            "sentencia_declarativa : tipo lista_variables",
            "sentencia_declarativa : declaracion_funcion",
            "sentencia_declarativa : declaracion_clase",
            "sentencia_declarativa : declaracion_distribuida",
            "sentencia_declarativa : declaracion_interfaz",
            "declaracion_clase : CLASS ID '{' bloque_declarativo '}'",
            "declaracion_clase : CLASS ID IMPLEMENT ID '{' bloque_declarativo '}'",
            "declaracion_distribuida : IMPL FOR ID ':' '{' declaracion_funcion '}'",
            "declaracion_interfaz : INTERFACE ID '{' metodos_interfaz '}'",
            "metodos_interfaz : metodo_interfaz ','",
            "metodos_interfaz : metodos_interfaz metodo_interfaz",
            "metodo_interfaz : VOID ID '(' tipo ID ')'",
            "metodo_interfaz : VOID ID '(' ')'",
            "declaracion_funcion : VOID ID '(' ')' '{' cuerpo_funcion '}'",
            "declaracion_funcion : VOID ID '(' tipo ID ')' '{' cuerpo_funcion '}'",
            "cuerpo_funcion : sentencia",
            "cuerpo_funcion : cuerpo_funcion sentencia",
            "tipo : SHORT",
            "tipo : ULONG",
            "tipo : DOUBLE",
            "tipo : ID",
            "lista_variables : ID",
            "lista_variables : lista_variables ';' ID",
            "sentencia_ejecutable : asignacion",
            "sentencia_ejecutable : invocacion_funcion",
            "sentencia_ejecutable : seleccion",
            "sentencia_ejecutable : imprimir",
            "sentencia_ejecutable : ref_clase '(' ')'",
            "ref_clase : ID '.' ID",
            "ref_clase : ref_clase '.' ID",
            "asignacion : ID '=' expresion",
            "asignacion : ID MENOS_IGUAL expresion",
            "asignacion : ref_clase '=' expresion",
            "asignacion : ref_clase MENOS_IGUAL expresion",
            "expresion : expresion '+' termino",
            "expresion : expresion '-' termino",
            "expresion : termino",
            "termino : termino '*' factor",
            "termino : termino '/' factor",
            "termino : factor",
            "factor : ID",
            "factor : CTE",
            "factor : '-' CTE",
            "invocacion_funcion : ID '(' expresion ')'",
            "invocacion_funcion : ID '(' ')'",
            "seleccion : IF '(' condicion ')' bloque_ejecutable ELSE bloque_ejecutable END_IF",
            "seleccion : IF '(' condicion ')' bloque_ejecutable END_IF",
            "condicion : expresion MAYOR_IGUAL expresion",
            "condicion : expresion MENOR_IGUAL expresion",
            "condicion : expresion '<' expresion",
            "condicion : expresion '>' expresion",
            "condicion : expresion IGUAL expresion",
            "condicion : expresion DISTINTO expresion",
            "bloque_ejecutable : sentencia_ejecutable",
            "bloque_ejecutable : '{' bloque_ejecutable sentencia_ejecutable '}'",
            "imprimir : PRINT CADENA",
            "sentencia_control : FOR ID IN RANGE '(' CTE CTE CTE ')' bloque_ejecutable",
    };

//#line 139 "gramatica.y"
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
    //#line 426 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
    void yylexdebug(int state,int ch)
    {
        String s=null;
        if (ch < 0) ch=0;
        if (ch <= YYMAXTOKEN) //check index bounds
            s = yyname[ch];    //now get it
        if (s==null)
            s = "illegal-symbol";
        debug("state "+state+", reading "+ch+" ("+s+")");
    }





    //The following are now global, to aid in error reporting
    int yyn;       //next next thing to do
    int yym;       //
    int yystate;   //current parsing state from state table
    String yys;    //current token string


    //###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
    int yyparse(Analizador_Lexico al)
    {
        boolean doaction;
        init_stacks();
        yynerrs = 0;
        yyerrflag = 0;
        yychar = -1;          //impossible char forces a read
        yystate=0;            //initial state
        state_push(yystate);  //save it
        val_push(yylval);     //save empty value
        while (true) //until parsing is done, either correctly, or w/error
        {
            doaction=true;
            if (yydebug) debug("loop");
            //#### NEXT ACTION (from reduction table)
            for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
            {
                if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
                if (yychar < 0)      //we want a char?
                {
                    yychar = yylex(al);  //get next token
                    if (yydebug) debug(" next yychar:"+yychar);
                    //#### ERROR CHECK ####
                    if (yychar < 0)    //it it didn't work/error
                    {
                        yychar = 0;      //change it to default string (no -1!)
                        if (yydebug)
                            yylexdebug(yystate,yychar);
                    }
                }//yychar<0
                yyn = yysindex[yystate];  //get amount to shift by (shift index)
                if ((yyn != 0) && (yyn += yychar) >= 0 &&
                        yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
                {
                    if (yydebug)
                        debug("state "+yystate+", shifting to state "+yytable[yyn]);
                    //#### NEXT STATE ####
                    yystate = yytable[yyn];//we are in a new state
                    state_push(yystate);   //save it
                    val_push(yylval);      //push our lval as the input for next rule
                    yychar = -1;           //since we have 'eaten' a token, say we need another
                    if (yyerrflag > 0)     //have we recovered an error?
                        --yyerrflag;        //give ourselves credit
                    doaction=false;        //but don't process yet
                    break;   //quit the yyn=0 loop
                }

                yyn = yyrindex[yystate];  //reduce
                if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
                        yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
                {   //we reduced!
                    if (yydebug) debug("reduce");
                    yyn = yytable[yyn];
                    doaction=true; //get ready to execute
                    break;         //drop down to actions
                }
                else //ERROR RECOVERY
                {
                    if (yyerrflag==0)
                    {
                        yyerror("syntax error");
                        yynerrs++;
                    }
                    if (yyerrflag < 3) //low error count?
                    {
                        yyerrflag = 3;
                        while (true)   //do until break
                        {
                            if (stateptr<0)   //check for under & overflow here
                            {
                                yyerror("stack underflow. aborting...");  //note lower case 's'
                                return 1;
                            }
                            yyn = yysindex[state_peek(0)];
                            if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
                            {
                                if (yydebug)
                                    debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
                                yystate = yytable[yyn];
                                state_push(yystate);
                                val_push(yylval);
                                doaction=false;
                                break;
                            }
                            else
                            {
                                if (yydebug)
                                    debug("error recovery discarding state "+state_peek(0)+" ");
                                if (stateptr<0)   //check for under & overflow here
                                {
                                    yyerror("Stack underflow. aborting...");  //capital 'S'
                                    return 1;
                                }
                                state_pop();
                                val_pop();
                            }
                        }
                    }
                    else            //discard this token
                    {
                        if (yychar == 0)
                            return 1; //yyabort
                        if (yydebug)
                        {
                            yys = null;
                            if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
                            if (yys == null) yys = "illegal-symbol";
                            debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
                        }
                        yychar = -1;  //read another
                    }
                }//end error recovery
            }//yyn=0 loop
            if (!doaction)   //any reason not to proceed?
                continue;      //skip action
            yym = yylen[yyn];          //get count of terminals on rhs
            if (yydebug)
                debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
            if (yym>0)                 //if count of rhs not 'nil'
                yyval = val_peek(yym-1); //get current semantic value
            yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
            switch(yyn)
            {
//########## USER-SUPPLIED ACTIONS ##########
                case 51:
//#line 97 "gramatica.y"
                {   chequeoRango(val_peek(0).sval);
                    al.getTablaSimbolos().getAtributos(val_peek(0).sval).sumarUso();
                }
                break;
                case 52:
//#line 100 "gramatica.y"
                {
                    chequeoRango("-"+val_peek(0).sval);
                    if (al.getTablaSimbolos().getAtributos(val_peek(0).sval).isCero()){
                        al.getTablaSimbolos().modificarClave(val_peek(0).sval, "-"+val_peek(0).sval);
                    } else {
                        if (!al.getTablaSimbolos().existeSimbolo("-"+val_peek(0).sval)){
                            al.getTablaSimbolos().insertarSimbolo("-"+val_peek(0).sval,new AtributosLexema());
                        }
                        al.getTablaSimbolos().getAtributos("-"+val_peek(0).sval).sumarUso();
                    }
                }
                break;
//#line 595 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
            }//switch
            //#### Now let's reduce... ####
            if (yydebug) debug("reduce");
            state_drop(yym);             //we just reduced yylen states
            yystate = state_peek(0);     //get new state
            val_drop(yym);               //corresponding value drop
            yym = yylhs[yyn];            //select next TERMINAL(on lhs)
            if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
            {
                if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
                yystate = YYFINAL;         //explicitly say we're done
                state_push(YYFINAL);       //and save it
                val_push(yyval);           //also save the semantic value of parsing
                if (yychar < 0)            //we want another character?
                {
                    yychar = yylex(al);        //get next character
                    if (yychar<0) yychar=0;  //clean, if necessary
                    if (yydebug)
                        yylexdebug(yystate,yychar);
                }
                if (yychar == 0)          //Good exit (if lex returns 0 ;-)
                    break;                 //quit the loop--all DONE
            }//if yystate
            else                        //else not done yet
            {                         //get next state and push, for next yydefred[]
                yyn = yygindex[yym];      //find out where to go
                if ((yyn != 0) && (yyn += yystate) >= 0 &&
                        yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
                    yystate = yytable[yyn]; //get new state
                else
                    yystate = yydgoto[yym]; //else go to new defred
                if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
                state_push(yystate);     //going again, so push state & val...
                val_push(yyval);         //for next action
            }
        }//main loop
        return 0;//yyaccept!!
    }
//## end of method parse() ######################################



//## run() --- for Thread #######################################
    /**
     * A default run method, used for operating this parser
     * object in the background.  It is intended for extending Thread
     * or implementing Runnable.  Turn off with -Jnorun .
     */
    public void run() {
        String s = "{a = 5_s, b = 5_s, c = -5_s, d = -5_s,}$";
        Analizador_Lexico al = new Analizador_Lexico(s.toCharArray());
        yyparse(al);
        System.out.println(al.getTablaSimbolos().toString());
    }
//## end of method run() ########################################



//## Constructors ###############################################
    /**
     * Default constructor.  Turn off with -Jnoconstruct .

     */
    public Parser()
    {
        //nothing to do
    }


    /**
     * Create a parser, setting the debug to true or false.
     * @param debugMe true for debugging, false for no debug.
     */
    public Parser(boolean debugMe)
    {
        yydebug=debugMe;
    }
//###############################################################



}
//################### END OF CLASS ##############################
