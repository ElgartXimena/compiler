package AnalizadorSintactico;
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






//#line 2 "C:\Users\leole\Desktop\Facultad\4to 2c\Diseño de Compiladores\TP\compiler\resources\gramatica.y"

//#line 19 "Parser.java"




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
            23,   17,   17,   18,   18,   24,   24,   24,   24,   24,
            24,   25,   25,   19,    5,
    };
    final static short yylen[] = {                            2,
            3,    2,    2,    1,    1,    1,    1,    1,    4,    2,
            1,    1,    1,    1,    5,    7,    7,    5,    2,    2,
            6,    4,    7,    9,    1,    2,    1,    1,    1,    1,
            1,    3,    1,    1,    1,    1,    3,    3,    3,    3,
            3,    3,    3,    3,    3,    1,    3,    3,    1,    1,
            1,    4,    3,    8,    6,    3,    3,    3,    3,    3,
            3,    1,    4,    2,   10,
    };
    final static short yydefred[] = {                         0,
            0,    0,    0,    0,    0,    0,    0,   27,   28,   29,
            0,    0,    0,    7,    0,    0,    4,    5,    6,    0,
            11,   12,   13,   14,   33,   34,   35,   36,    0,    0,
            0,    0,    0,    0,   64,    0,    0,    0,    0,    0,
            1,    2,    3,   31,    0,    0,    0,    0,    0,   50,
            51,    0,    0,   49,   53,    0,   38,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,   37,   39,
            0,    0,    0,    0,    0,   52,    0,    0,    0,    0,
            0,    0,    0,    0,   30,    0,    8,    0,    0,    0,
            0,    0,    0,    0,    0,   32,    0,    0,   47,   48,
            0,    0,    0,    0,    0,    0,    0,    0,   62,    0,
            0,    0,   15,    0,    0,    0,    0,    0,   18,   20,
            19,    0,    0,   55,    0,    0,   25,    0,    0,    0,
            0,    0,    0,    0,   16,    9,   23,   26,    0,    0,
            17,   22,    0,   63,   54,    0,    0,    0,   24,    0,
            21,   65,
    };
    final static short yydgoto[] = {                          2,
            15,  127,   17,   18,   19,   88,   20,   45,   21,   22,
            23,   24,   94,   95,  128,   25,   26,   27,   28,   29,
            52,   53,   54,   60,  110,
    };
    final static short yysindex[] = {                      -113,
            -148,    0,  -23,  -18, -234, -227, -220,    0,    0,    0,
            -218, -223, -215,    0,  -93,   14,    0,    0,    0, -196,
            0,    0,    0,    0,    0,    0,    0,    0,    1, -244,
            -40, -194, -244, -244,    0, -118,   28, -200, -175,  -51,
            0,    0,    0,    0,   24, -244,   44, -170, -244,    0,
            0,  -12,  -13,    0,    0,   12,    0,  -12,   11,   48,
            -163,  -59,  118, -176,   39, -167, -158,  -12,    0,    0,
            -12, -244, -244, -244, -244,    0, -244, -244, -244, -244,
            -244, -244, -116,  -22,    0,  -59,    0,  -25,  -19, -152,
            67,   -9, -147, -117,   78,    0,  -13,  -13,    0,    0,
            -12,  -12,  -12,  -12,  -12,  -12,  -23, -116,    0, -185,
            -59,  147,    0, -148,   70, -135, -139,   88,    0,    0,
            0, -245, -116,    0,    4,    6,    0,  -76,   17, -115,
            20,  122,   21, -120,    0,    0,    0,    0, -148, -108,
            0,    0, -105,    0,    0,  134,  112,  113,    0, -116,
            0,    0,
    };
    final static short yyrindex[] = {                         0,
            0,    0, -102,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,  105,    0,    0,    0,    0,    0,
            0,   35,  -41,    0,    0,    0,    0,   52,    0,    0,
            0,    0,    0,    0,    0,    0,    0,   69,    0,    0,
            86,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,  -17,    7,    0,    0,
            116,  117,  120,  121,  124,  125,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,
    };
    final static short yygindex[] = {                         0,
            0,    5,  -46,  -48,    0,  -75,  -54,    0,   51,    0,
            0,    0,    0,   75,   46,    0,    0,    0,    0,    0,
            57,    8,   18,    0,  -64,
    };
    final static int YYTABLESIZE=420;
    static short yytable[];
    static { yytable();}
    static void yytable(){
        yytable = new short[]{                         46,
                55,   46,   46,   46,   62,   16,  108,  119,   90,    1,
                112,  107,   50,   51,    4,   87,   31,    5,   46,   42,
                46,   34,   32,   44,   35,   44,   44,   44,   74,   36,
                72,   41,   73,   75,  109,  125,   37,   33,   38,   87,
                47,   40,   44,  122,   44,   39,   48,   45,  137,   45,
                45,   45,   76,   72,   72,   73,   73,   43,  134,  109,
                44,   49,   57,   86,   87,  126,   45,   63,   45,   64,
                81,   66,   82,  133,  109,  123,  124,  143,   41,   97,
                98,   65,   67,   46,   69,  152,   70,   56,   83,   58,
                59,   99,  100,   84,   91,   40,   92,   93,   96,  113,
                111,  109,   68,  114,  115,   71,  116,   44,    3,  118,
                129,    4,   43,  117,    5,    6,    7,    8,    9,   10,
                11,  121,  130,   12,   13,    7,   14,  132,  135,   42,
                136,   45,  138,  101,  102,  103,  104,  105,  106,  139,
                107,  145,  140,    4,  141,  144,    5,   93,   10,  147,
                138,  148,  150,  151,   30,   61,   56,   57,   89,   41,
                60,   61,  142,    3,   58,   59,    4,  131,  120,    5,
                6,    7,    8,    9,   10,   11,   40,    0,   12,   13,
                3,   14,    0,    4,  146,    0,    5,    6,    7,    8,
                9,   10,   11,   43,    0,   12,   13,   85,   14,    0,
                0,    0,    0,    0,    6,    7,    8,    9,   10,    0,
                42,    0,   12,   13,    0,   46,   50,   51,   46,   46,
                46,   46,   46,   46,   46,   46,   46,   46,    0,   10,
                46,   46,    0,   46,    0,   46,   46,   46,   46,   44,
                0,    0,   44,   44,   44,   44,   44,   44,   44,   44,
                44,   44,   30,    0,   44,   44,    0,   44,  149,   44,
                44,   44,   44,   45,    0,    0,   45,   45,   45,   45,
                45,   45,   45,   45,   45,   45,   46,    0,   45,   45,
                0,   45,    0,   45,   45,   45,   45,   77,   78,   79,
                80,   41,    0,    0,   41,   41,   41,   41,   41,   41,
                41,   41,   41,   41,    0,    0,   41,   41,   40,   41,
                0,   40,   40,   40,   40,   40,   40,   40,   40,   40,
                40,    0,    0,   40,   40,   43,   40,    0,   43,   43,
                43,   43,   43,   43,   43,   43,   43,   43,    0,    0,
                43,   43,   42,   43,    0,   42,   42,   42,   42,   42,
                42,   42,   42,   42,   42,    0,    0,   42,   42,    0,
                42,   10,    0,    0,   10,    0,    0,   10,   10,   10,
                10,   10,   10,   10,   85,    0,   10,   10,   85,   10,
                0,    0,    0,    8,    9,   10,    0,    8,    9,   10,
                3,    0,    0,    4,    0,    0,    5,    6,    7,    8,
                9,   10,   11,   85,    0,   12,   13,    0,   14,    0,
                6,    7,    8,    9,   10,    0,    0,    0,   12,   13,
        };
    }
    static short yycheck[];
    static { yycheck(); }
    static void yycheck() {
        yycheck = new short[] {                         41,
                41,   43,   44,   45,  123,    1,  123,  125,   63,  123,
                86,  257,  257,  258,  260,   62,   40,  263,   60,   15,
                62,   40,   46,   41,  259,   43,   44,   45,   42,  257,
                43,  125,   45,   47,   83,  111,  257,   61,  257,   86,
                40,  257,   60,  108,   62,  269,   46,   41,  125,   43,
                44,   45,   41,   43,   43,   45,   45,   44,  123,  108,
                257,   61,  257,  123,  111,  112,   60,   40,   62,  270,
                60,  123,   62,  122,  123,  261,  262,  132,   44,   72,
                73,  257,   59,  125,   41,  150,  257,   31,   41,   33,
                34,   74,   75,  257,  271,   44,   58,  265,  257,  125,
                123,  150,   46,  123,  257,   49,   40,  125,  257,  257,
                41,  260,   44,  123,  263,  264,  265,  266,  267,  268,
                269,   44,  258,  272,  273,  265,  275,   40,  125,   44,
                125,  125,  128,   77,   78,   79,   80,   81,   82,  123,
                257,  262,  258,  260,  125,  125,  263,  265,   44,  258,
                146,  257,   41,   41,  257,  274,   41,   41,   41,  125,
                41,   41,   41,  257,   41,   41,  260,  117,   94,  263,
                264,  265,  266,  267,  268,  269,  125,   -1,  272,  273,
                257,  275,   -1,  260,  139,   -1,  263,  264,  265,  266,
                267,  268,  269,  125,   -1,  272,  273,  257,  275,   -1,
                -1,   -1,   -1,   -1,  264,  265,  266,  267,  268,   -1,
                125,   -1,  272,  273,   -1,  257,  257,  258,  260,  261,
                262,  263,  264,  265,  266,  267,  268,  269,   -1,  125,
                272,  273,   -1,  275,   -1,  277,  278,  279,  280,  257,
                -1,   -1,  260,  261,  262,  263,  264,  265,  266,  267,
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
                267,  268,  269,  257,   -1,  272,  273,   -1,  275,   -1,
                264,  265,  266,  267,  268,   -1,   -1,   -1,  272,  273,
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
            "bloque_sentencias : bloque_sentencias sentencia",
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

    //#line 126 "C:\Users\leole\Desktop\Facultad\4to 2c\Diseño de Compiladores\TP\compiler\resources\gramatica.y"
    /* CODE SECTION */
//#line 387 "Parser.java"
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
    int yyparse()
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
                    yychar = yylex();  //get next token
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
                    yychar = yylex();        //get next character
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
    public void run()
    {
        yyparse();
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
    public int yylex(){

        return 0;
    }
    public void yyerror(String s){}

}
//################### END OF CLASS ##############################
