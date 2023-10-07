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
            0,    1,    1,    2,    2,    2,    2,    2,    6,    6,
            3,    3,    3,    3,    3,   10,   10,   11,   12,   13,
            13,   14,   14,    9,    9,   15,   15,    7,    7,    7,
            7,    8,    8,    4,    4,    4,    4,    4,   20,   20,
            16,   16,   16,   16,   21,   21,   21,   22,   22,   22,
            23,   23,   23,   17,   17,   18,   18,   24,   24,   24,
            24,   24,   24,   25,   25,   19,    5,
    };
    final static short yylen[] = {                            2,
            3,    3,    2,    1,    1,    1,    1,    2,    1,    4,
            2,    1,    1,    1,    1,    5,    7,    7,    5,    2,
            2,    6,    4,    7,    9,    1,    2,    1,    1,    1,
            1,    1,    3,    1,    1,    1,    1,    3,    3,    3,
            3,    3,    3,    3,    3,    3,    1,    3,    3,    1,
            1,    1,    2,    4,    3,    8,    6,    3,    3,    3,
            3,    3,    3,    1,    4,    2,   10,
    };
    final static short yydefred[] = {                         0,
            0,    0,    0,    0,    0,    0,    0,    0,   28,   29,
            30,    0,    0,    0,    7,    0,    0,    4,    5,    6,
            0,   12,   13,   14,   15,   34,   35,   36,   37,    0,
            8,    0,    0,    0,    0,    0,   66,    0,    0,    0,
            0,    0,    1,    0,    3,   32,    0,    0,    0,    0,
            0,   51,   52,    0,    0,    0,   50,   55,    0,   39,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    2,
            0,    0,   38,   40,    0,   53,    0,    0,    0,    0,
            54,    0,    0,    0,    0,    0,    0,    0,    0,   31,
            0,    9,    0,    0,    0,    0,    0,    0,    0,    0,
            33,    0,    0,   48,   49,    0,    0,    0,    0,    0,
            0,    0,    0,   64,    0,    0,    0,   16,    0,    0,
            0,    0,    0,   19,   21,   20,    0,    0,   57,    0,
            0,   26,    0,    0,    0,    0,    0,    0,    0,   17,
            10,   24,   27,    0,    0,   18,   23,    0,   65,   56,
            0,    0,    0,   25,    0,   22,   67,
    };
    final static short yydgoto[] = {                          2,
            16,  132,   18,   19,   20,   93,   21,   47,   22,   23,
            24,   25,   99,  100,  133,   26,   27,   28,   29,   30,
            55,   56,   57,   63,  115,
    };
    final static short yysindex[] = {                      -106,
            -168,    0,  -18,  -22,   -9, -217, -209, -206,    0,    0,
            0, -194, -203, -187,    0, -114,   28,    0,    0,    0,
            -181,    0,    0,    0,    0,    0,    0,    0,    0,    3,
            0,   10,  -40, -172,   10,   10,    0, -117,   46, -180,
            -166,  -30,    0,   50,    0,    0,   44,   10,   65, -149,
            10,    0,    0, -148,   -8,  -33,    0,    0,   36,    0,
            -8,   13,   70, -145,  -66,   91, -158,   63, -142,    0,
            -133,   -8,    0,    0,   -8,    0,   10,   10,   10,   10,
            0,   10,   10,   10,   10,   10,   10,  -89,    2,    0,
            -66,    0,    1,   16, -124,   97,   17, -112, -109,  116,
            0,  -33,  -33,    0,    0,   -8,   -8,   -8,   -8,   -8,
            -8,  -22,  -89,    0, -249,  -66, -137,    0, -168,  123,
            -92,  -98,  135,    0,    0,    0, -119,  -89,    0,   59,
            62,    0,  -87,   66,  -65,   67,  131,   69,  -72,    0,
            0,    0,    0, -168,  -63,    0,    0,  -61,    0,    0,
            155,  156,  163,    0,  -89,    0,    0,
    };
    final static short yyrindex[] = {                         0,
            0,    0,    0,  -52,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,  118,    0,    0,    0,
            0,    0,    0,    0,   38,  -41,    0,    0,    0,    0,
            58,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,   78,    0,    0,  104,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,  -16,    9,    0,    0,  167,  168,  169,  170,  171,
            172,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,
    };
    final static short yygindex[] = {                         0,
            0,   14,  -55,  -68,    0,  -83,  -59,    0,   92,    0,
            0,    0,    0,  134,   86,    0,    0,    0,    0,    0,
            32,  -37,  -57,    0,  -81,
    };
    final static int YYTABLESIZE=430;
    static short yytable[];
    static { yytable();}
    static void yytable(){
        yytable = new short[]{                         47,
                58,   47,   47,   47,   54,   65,   95,  117,   79,   92,
                43,  128,  129,   80,   17,  124,    1,   33,   47,  114,
                47,  104,  105,   34,   45,   31,   45,   45,   45,   44,
                36,  127,  130,  113,   77,   92,   78,  142,   35,  102,
                103,   37,   49,   45,  114,   45,  139,   38,   50,   46,
                39,   46,   46,   46,   54,   77,   91,   78,  138,  114,
                92,  131,   40,   51,   59,   41,   61,   62,   46,   42,
                46,   45,   86,  157,   87,   46,   81,  148,   77,   72,
                78,   42,   75,   47,   60,   66,  114,    3,    4,   67,
                68,    5,   69,   70,    6,    7,    8,    9,   10,   11,
                12,   41,   71,   13,   14,   73,   15,   74,   45,   76,
                88,   89,   96,  106,  107,  108,  109,  110,  111,   90,
                97,   44,   98,  101,  116,  118,    7,    8,    9,   10,
                11,   94,  120,   46,   13,   14,  121,  112,  119,  122,
                5,    3,    4,    6,  123,    5,  143,   43,    6,    7,
                8,    9,   10,   11,   12,   98,   64,   13,   14,  126,
                15,   11,   42,  134,  143,  135,    8,  112,    3,    4,
                5,  147,    5,    6,  137,    6,    7,    8,    9,   10,
                11,   12,   41,  140,   13,   14,  141,   15,  144,  150,
                90,  146,  145,  149,  152,  153,  155,    7,    8,    9,
                10,   11,   44,  156,   31,   13,   14,   58,   59,   62,
                63,   60,   61,  136,   47,   47,   52,   53,   47,   47,
                47,   47,   47,   47,   47,   47,   47,   47,   43,  151,
                47,   47,  125,   47,    0,   47,   47,   47,   47,   45,
                45,    0,   11,   45,   45,   45,   45,   45,   45,   45,
                45,   45,   45,   32,    0,   45,   45,    0,   45,    0,
                45,   45,   45,   45,   46,   46,   52,   53,   46,   46,
                46,   46,   46,   46,   46,   46,   46,   46,   48,  154,
                46,   46,    0,   46,    0,   46,   46,   46,   46,   82,
                83,   84,   85,   42,   42,    0,    0,   42,   42,   42,
                42,   42,   42,   42,   42,   42,   42,    0,    0,   42,
                42,    0,   42,   41,   41,    0,    0,   41,   41,   41,
                41,   41,   41,   41,   41,   41,   41,    0,    0,   41,
                41,    0,   41,   44,   44,    0,    0,   44,   44,   44,
                44,   44,   44,   44,   44,   44,   44,   90,    0,   44,
                44,    0,   44,    0,    0,    0,    9,   10,   11,   43,
                43,    0,    0,   43,   43,   43,   43,   43,   43,   43,
                43,   43,   43,   11,   11,   43,   43,   11,   43,    0,
                11,   11,   11,   11,   11,   11,   11,   90,    0,   11,
                11,    0,   11,    0,    0,    0,    9,   10,   11,    0,
                0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
                3,    4,    0,    0,    5,    0,    0,    6,    7,    8,
                9,   10,   11,   12,    0,    0,   13,   14,    0,   15,
        };
    }
    static short yycheck[];
    static { yycheck(); }
    static void yycheck() {
        yycheck = new short[] {                         41,
                41,   43,   44,   45,   45,  123,   66,   91,   42,   65,
                125,  261,  262,   47,    1,  125,  123,   40,   60,   88,
                62,   79,   80,   46,   41,   44,   43,   44,   45,   16,
                40,  113,  116,  123,   43,   91,   45,  125,   61,   77,
                78,  259,   40,   60,  113,   62,  128,  257,   46,   41,
                257,   43,   44,   45,   45,   43,  123,   45,  127,  128,
                116,  117,  257,   61,   33,  269,   35,   36,   60,  257,
                62,   44,   60,  155,   62,  257,   41,  137,   43,   48,
                45,   44,   51,  125,  257,   40,  155,  256,  257,  270,
                257,  260,  123,   44,  263,  264,  265,  266,  267,  268,
                269,   44,   59,  272,  273,   41,  275,  257,  125,  258,
                41,  257,  271,   82,   83,   84,   85,   86,   87,  257,
                58,   44,  265,  257,  123,  125,  264,  265,  266,  267,
                268,   41,  257,  125,  272,  273,   40,  257,  123,  123,
                260,  256,  257,  263,  257,  260,  133,   44,  263,  264,
                265,  266,  267,  268,  269,  265,  274,  272,  273,   44,
                275,   44,  125,   41,  151,  258,  265,  257,  256,  257,
                260,   41,  260,  263,   40,  263,  264,  265,  266,  267,
                268,  269,  125,  125,  272,  273,  125,  275,  123,  262,
                257,  125,  258,  125,  258,  257,   41,  264,  265,  266,
                267,  268,  125,   41,  257,  272,  273,   41,   41,   41,
                41,   41,   41,  122,  256,  257,  257,  258,  260,  261,
                262,  263,  264,  265,  266,  267,  268,  269,  125,  144,
                272,  273,   99,  275,   -1,  277,  278,  279,  280,  256,
                257,   -1,  125,  260,  261,  262,  263,  264,  265,  266,
                267,  268,  269,  276,   -1,  272,  273,   -1,  275,   -1,
                277,  278,  279,  280,  256,  257,  257,  258,  260,  261,
                262,  263,  264,  265,  266,  267,  268,  269,  276,  125,
                272,  273,   -1,  275,   -1,  277,  278,  279,  280,  277,
                278,  279,  280,  256,  257,   -1,   -1,  260,  261,  262,
                263,  264,  265,  266,  267,  268,  269,   -1,   -1,  272,
                273,   -1,  275,  256,  257,   -1,   -1,  260,  261,  262,
                263,  264,  265,  266,  267,  268,  269,   -1,   -1,  272,
                273,   -1,  275,  256,  257,   -1,   -1,  260,  261,  262,
                263,  264,  265,  266,  267,  268,  269,  257,   -1,  272,
                273,   -1,  275,   -1,   -1,   -1,  266,  267,  268,  256,
                257,   -1,   -1,  260,  261,  262,  263,  264,  265,  266,
                267,  268,  269,  256,  257,  272,  273,  260,  275,   -1,
                263,  264,  265,  266,  267,  268,  269,  257,   -1,  272,
                273,   -1,  275,   -1,   -1,   -1,  266,  267,  268,   -1,
                -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
                256,  257,   -1,   -1,  260,   -1,   -1,  263,  264,  265,
                266,  267,  268,  269,   -1,   -1,  272,  273,   -1,  275,
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
            "sentencia : error ','",
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

//#line 165 "gramatica.y"
    /* CODE SECTION */

    public void chequeoRango(String cte, Analizador_Lexico al){
        if (cte.contains("_s")){
            int cteint = Integer.parseInt(cte.substring(0, cte.length()-2));
            double min = Math.pow(-2,7);
            double max = Math.pow(2,7)-1;
            if ((cteint < min)||(cteint>max)){
                System.out.println("Error linea "+al.getLinea()+": Constante fuera de rango");
            }
        } else if (cte.contains("_ul")) {
            long cteint_largo = Integer.parseInt(cte.substring(0, cte.length()-3));
            double min = 0;
            double max = Math.pow(2,32)-1;
            if ((cteint_largo < min)||(cteint_largo>max)){
                System.out.println("Error linea "+al.getLinea()+": Constante fuera de rango");
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
                System.out.println("Error linea "+al.getLinea()+": Constante fuera de rango");
            }
        }
    }

    public int yylex(Analizador_Lexico al){
        int tok = al.yylex();
        yylval = new ParserVal(al.getBuffer());
        return tok;
    }
    public void yyerror(String s){}
    //#line 431 "Parser.java"
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
                case 16:
//#line 35 "gramatica.y"
                {/* accion para ID que establezca como tipo “CLASE” */
                    al.getTablaSimbolos().getAtributos(val_peek(3).sval).setTipo("CLASE");
                }
                break;
                case 17:
//#line 39 "gramatica.y"
                {
                    /* accion que chequea si ID_2 es una interfaz y asignar tipo CLASE a ID_1 */
                    if (al.getTablaSimbolos().getAtributos(val_peek(3).sval).isTipo("INTERFACE")){
                        al.getTablaSimbolos().getAtributos(val_peek(5).sval).setTipo("CLASE");
                    } else {
                        System.out.println("Error linea "+al.getLinea()+": "+val_peek(3).sval+" no es un INTERFACE");
                    }
                }
                break;
                case 18:
//#line 49 "gramatica.y"
                {
                    /* accion para fijarse que id sea una clase */
                    if (!al.getTablaSimbolos().getAtributos(val_peek(4).sval).isTipo("CLASE")){
                        System.out.println("Error linea "+al.getLinea()+": "+val_peek(4).sval+" no es una clase");
                    }
                }
                break;
                case 19:
//#line 58 "gramatica.y"
                {/* accion para ID que establezca como tipo “INTERFACE” */
                    al.getTablaSimbolos().getAtributos(val_peek(3).sval).setTipo("INTERFACE");
                }
                break;
                case 31:
//#line 83 "gramatica.y"
                {
                    /* accion que chequee si el tipo es “CLASE” (id no puede ser un tipo si no es una clase)*/
                    if (!al.getTablaSimbolos().getAtributos(val_peek(0).sval).isTipo("CLASE")){
                        System.out.println("Error linea "+al.getLinea()+": "+val_peek(0).sval+" no es tipo CLASE");
                    }
                }
                break;
                case 52:
//#line 123 "gramatica.y"
                {   chequeoRango(val_peek(0).sval, al);
                    al.getTablaSimbolos().getAtributos(val_peek(0).sval).sumarUso();
                }
                break;
                case 53:
//#line 126 "gramatica.y"
                {
                    chequeoRango("-"+val_peek(0).sval, al);
                    if (al.getTablaSimbolos().getAtributos(val_peek(0).sval).isCero()){
                        al.getTablaSimbolos().modificarClave(val_peek(0).sval, "-"+val_peek(0).sval);
                    } else {
                        if (!al.getTablaSimbolos().existeSimbolo("-"+val_peek(0).sval)){
                            al.getTablaSimbolos().insertarSimbolo("-"+val_peek(0).sval,new AtributosLexema());
                        }
                    }
                    al.getTablaSimbolos().getAtributos("-"+val_peek(0).sval).sumarUso();
                }
                break;
//#line 641 "Parser.java"
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
        String s = "{a = -5_s, b = 5_s, c = -5_s, d = -5_s,}$";
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
