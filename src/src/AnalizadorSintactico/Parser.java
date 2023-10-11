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
            0,    0,    0,    1,    1,    2,    2,    5,    5,    6,
            6,    6,    7,    7,    7,    7,    7,    7,    7,    7,
            3,    3,    3,    3,    3,    3,    3,    3,    8,    8,
            8,    9,    9,   10,   10,   10,   10,   10,   10,   10,
            10,   10,   11,   11,   11,   11,   11,   12,   12,   12,
            12,   13,   13,   14,   14,   14,   14,   15,   15,   15,
            15,   15,    4,    4,    4,    4,    4,    4,    4,    4,
            4,   16,   16,   16,   16,   16,   16,   16,   16,   16,
            16,   16,   16,   21,   21,   21,   22,   22,   22,   23,
            23,   23,   17,   17,   17,   17,   18,   18,   18,   18,
            18,   18,   18,   18,   24,   24,   24,   24,   24,   24,
            19,   19,   19,   20,   20,
    };
    final static short yylen[] = {                            2,
            3,    2,    2,    2,    1,    1,    1,    2,    1,    5,
            1,    5,   12,   10,    8,    7,   12,   11,   11,   12,
            3,    3,    1,    1,    1,    1,    3,    3,    1,    1,
            1,    1,    3,    8,   10,    9,    9,   10,   10,    8,
            8,   10,    5,    7,    5,    7,    2,    7,    6,    7,
            7,    5,    5,    2,    3,    1,    2,    6,    4,    5,
            5,    6,    1,    1,    1,    1,    4,    1,    2,    2,
            4,    4,    4,    4,    4,    4,    4,    4,    4,    4,
            4,    4,    4,    3,    3,    1,    3,    3,    1,    1,
            1,    2,    5,    4,    5,    4,    9,    7,    7,    9,
            7,    9,    9,    7,    3,    3,    3,    3,    3,    3,
            3,    3,    3,    3,    3,
    };
    final static short yydefred[] = {                         0,
            0,    0,    0,    0,    0,   29,   30,   31,    0,    0,
            0,    0,    0,    0,    0,    5,    6,    7,   68,    0,
            23,   24,   25,   26,   63,   64,   65,   66,    0,   32,
            0,    0,    0,    0,    0,    0,    0,    0,   47,    0,
            0,    0,    0,    0,    0,    0,   70,   69,    0,    3,
            4,    0,    0,    0,    0,    0,   90,   91,    0,    0,
            0,    0,   89,    0,    0,    0,    0,  114,   28,   22,
            0,    0,    0,    0,  113,  111,  112,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    1,   27,   21,
            0,    0,    0,    0,    0,  115,   77,   92,   81,   73,
            0,    0,    0,    0,   96,   94,    0,   76,   80,   72,
            33,    0,    0,   11,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    9,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,   79,   83,   75,   71,   67,   78,   82,   74,
            0,    0,   87,   88,   95,   93,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,   45,
            43,    8,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,   53,   52,    0,   54,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,   49,
            0,    0,    0,    0,   55,    0,    0,   99,    0,  101,
            0,  104,   98,   46,   44,    0,    0,    0,    0,    0,
            0,    0,    0,   16,    0,    0,    0,   51,   50,   48,
            0,   59,    0,    0,   12,   10,    0,    0,    0,    0,
            40,   41,   34,    0,    0,    0,    0,    0,   15,    0,
            0,    0,    0,    0,   61,  100,  102,  103,   97,    0,
            36,    0,    0,   37,    0,    0,    0,    0,    0,   62,
            58,   38,   39,   42,   35,    0,   14,    0,    0,    0,
            0,   19,   18,    0,   20,   13,   17,
    };
    final static short yydgoto[] = {                         14,
            15,   16,   17,   18,  127,  115,   19,   20,   35,   21,
            22,   23,   24,  141,  142,   25,   26,   27,   28,   29,
            61,   62,   63,   74,
    };
    final static short yysindex[] = {                        61,
            3,   13,  -40,  -95, -231,    0,    0,    0, -154, -209,
            -199,   17, -143,    0,   75,    0,    0,    0,    0, -138,
            0,    0,    0,    0,    0,    0,    0,    0,   -4,    0,
            28,    6,   30, -130,   49,   11,   47, -125,    0, -106,
            -38,  -99, -137,   79, -118,   25,    0,    0,  111,    0,
            0,   50,   32,  104,   34, -102,    0,    0,  -91,  -88,
            22,   86,    0,   48,   99,  -89,   37,    0,    0,    0,
            -76,  -77,  -21,  146,    0,    0,    0,  -66,  287,    9,
            -58,  -39,  161,  162,   81,  -18,  -92,    0,    0,    0,
            -50,   42,   51,  -49,   45,    0,    0,    0,    0,    0,
            38,   38,   38,   38,    0,    0,   52,    0,    0,    0,
            0,   -2,  -77,    0,  -85,   38,   38,   38,   38,   38,
            38, -111,   82, -138,  -46,    0,  317,  171,   90,  -42,
            176,  178,  -31,    7,   12,  -45,  -45,  100,    5,   43,
            -71,  240,    0,    0,    0,    0,    0,    0,    0,    0,
            86,   86,    0,    0,    0,    0, -122,  -77,  250,   59,
            59,   59,   59,   59,   59,  -77,  258,  -83,  336,    0,
            0,    0,  177,  136,  -13,  194,  -12,  260,  261,  263,
            206,  212,  -57,  -33,    0,    0,  301,    0,  221,   89,
            96,   91,   98,  -77,   53,  102,  348, -143,  305,  158,
            -143,  232, -143,    4,  -77,  105,  106,  107,  110,    0,
            113,  231,   14,  103,    0,   54,  318,    0,  326,    0,
            120,    0,    0,    0,    0,  172,  116,   55,  191,  236,
            256,  115,  -77,    0,  328,  329,  330,    0,    0,    0,
            349,    0,  134,  351,    0,    0,  138,  141,   56,  363,
            0,    0,    0,  366,  369,  273,  370,   77,    0,  159,
            170,  185,  160,  378,    0,    0,    0,    0,    0,  164,
            0,  190,   57,    0,  192,  -77,  408,  411,  412,    0,
            0,    0,    0,    0,    0,  421,    0,  -77,  -77,  -77,
            -100,    0,    0,  209,    0,    0,    0,
    };
    final static short yyrindex[] = {                         0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,  467,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,  -35,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,  -56,    0,    0,    0,    0,    0,    0,    0,    0,
            -30,  -25,    0,    0,    0,    0,    0,    0,    0,  427,
            428,  429,  430,  431,  433,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,  -14,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,  -41,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,
    };
    final static short yygindex[] = {                         0,
            -8,  -15,  188,  -51,  306,  201,    0,  -10,  456,  -24,
            0,    0,    0,    0,  337,    0,    0,    0,    0,    0,
            525,   87,   93,    0,
    };
    final static int YYTABLESIZE=646;
    static short yytable[];
    static { yytable();}
    static void yytable(){
        yytable = new short[]{                         51,
                133,   80,   60,   38,   49,   86,  213,   86,   86,   86,
                84,  113,   84,   84,   84,   85,   79,   85,   85,   85,
                114,  101,  113,  102,   86,   41,   86,  202,  205,   84,
                81,   84,  140,   51,   85,   54,   85,   32,  120,  138,
                121,   56,   32,   34,  233,  113,   64,   44,   34,  129,
                60,   72,   36,  186,  242,   60,   55,   46,   33,   45,
                48,  114,  232,   33,  101,  100,  102,  211,   56,  130,
                114,   59,   60,   66,   60,   91,   60,   94,   60,  101,
                110,  102,   60,   60,  101,  145,  102,  101,  150,  102,
                76,  106,   70,   90,  147,  156,  223,  246,  253,  269,
                285,  101,   42,  102,  137,  189,  114,   71,   71,  201,
                57,  181,  182,    1,  114,   43,    2,  276,   30,    3,
                4,    5,    6,    7,    8,    9,   68,  103,   10,   11,
                77,   12,  104,   84,  112,  275,   85,    2,   86,  107,
                3,  101,  114,  102,   93,  112,    9,   87,    2,  166,
                167,    3,   12,  114,   96,  295,  112,    9,  212,    2,
                39,   40,    3,   12,   97,  200,  108,   78,    9,   98,
                82,   83,  139,  214,   12,  158,  159,  194,  195,  112,
                111,  114,    2,   13,   51,    3,  122,  151,  152,  226,
                123,    9,  229,  139,  231,  153,  154,   12,  131,   50,
                134,  135,  243,  136,  169,  143,  148,    5,   56,  170,
                51,  173,  174,   51,  175,   51,  176,  177,   37,    5,
                86,  256,  183,   60,  114,   84,  178,    6,    7,    8,
                85,  132,    6,    7,    8,   88,  114,  114,  114,  114,
                51,   86,   86,   86,   86,  204,   84,   84,   84,   84,
                57,   85,   85,   85,   85,  116,  117,  118,  119,   30,
                199,  184,   57,   58,  179,  128,  126,   57,   58,  180,
                241,   53,   47,   31,    6,    7,    8,   99,   31,    6,
                7,    8,  228,  188,   57,   58,   57,   58,   57,   58,
                57,   58,  109,  191,   57,   58,  250,  144,  185,  198,
                149,  193,   75,  105,   69,   89,  146,  155,  222,  245,
                252,  268,  284,  157,  172,  254,  203,    1,  206,  207,
                2,  208,  168,    3,    4,    5,    6,    7,    8,    9,
                209,    1,   10,   11,    2,   12,  210,    3,    4,    5,
                6,    7,    8,    9,  215,  216,   10,   11,  227,   12,
                217,  218,  219,  220,  230,  240,  126,  224,  190,  244,
                255,  247,  235,  236,  237,  238,  192,    1,  239,  248,
                2,  251,  258,    3,    4,    5,    6,    7,    8,    9,
                257,  249,   10,   11,  172,   12,  260,  261,  262,  263,
                264,  265,    1,  266,  221,    2,  267,  273,    3,    4,
                5,    6,    7,    8,    9,  234,  270,   10,   11,  271,
                12,  125,  272,  274,    1,  280,  277,    2,  281,  282,
                3,    4,    5,    6,    7,    8,    9,  278,    1,   10,
                11,    2,   12,  259,    3,    4,    5,    6,    7,    8,
                9,  171,  279,   10,   11,  283,   12,    1,  288,  286,
                2,  289,  290,    3,    4,    5,    6,    7,    8,    9,
                196,  291,   10,   11,  297,   12,    2,  105,  106,  109,
                110,  107,  225,  108,  197,   52,  287,  187,    0,    0,
                0,    0,    0,    0,    0,    0,    0,    0,  292,  293,
                294,  296,    1,    0,    0,    2,    0,    0,    3,    4,
                5,    6,    7,    8,    9,    0,    0,   10,   11,    0,
                12,    0,    1,    0,    0,    2,    0,    0,    3,    4,
                5,    6,    7,    8,    9,    0,    0,   10,   11,    1,
                12,    0,    2,    0,    0,    3,    4,    5,    6,    7,
                8,    9,    0,  124,   10,   11,    0,   12,    0,    0,
                4,    5,    6,    7,    8,    0,   65,   67,   10,   11,
                73,    0,    0,    0,    0,    0,    0,    0,    0,    0,
                0,    0,    0,  124,    0,    0,    0,   92,    0,   95,
                4,    5,    6,    7,    8,    0,    0,    0,   10,   11,
                0,    0,  124,    0,    0,    0,    0,    0,    0,    4,
                5,    6,    7,    8,  124,    0,    0,   10,   11,    0,
                0,    4,    5,    6,    7,    8,    0,    0,    0,   10,
                11,    0,    0,    0,    0,    0,    0,    0,    0,    0,
                0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
                160,  161,  162,  163,  164,  165,
        };
    }
    static short yycheck[];
    static { yycheck(); }
    static void yycheck() {
        yycheck = new short[] {                         15,
                40,   40,   44,   44,   13,   41,   40,   43,   44,   45,
                41,  123,   43,   44,   45,   41,  123,   43,   44,   45,
                72,   43,  123,   45,   60,  257,   62,   41,   41,   60,
                41,   62,  125,   49,   60,   40,   62,   40,   60,   58,
                62,   46,   40,   46,   41,  123,   41,  257,   46,   41,
                45,   41,   40,  125,   41,   45,   61,  257,   61,  269,
                44,  113,   59,   61,   43,   44,   45,  125,  125,   80,
                122,   44,   45,   44,   45,   44,   45,   44,   45,   43,
                44,   45,   45,  125,   43,   44,   45,   43,   44,   45,
                44,   44,   44,   44,   44,   44,   44,   44,   44,   44,
                44,   43,  257,   45,  123,  157,  158,   59,   59,  123,
                125,  136,  137,  257,  166,  270,  260,   41,  257,  263,
                264,  265,  266,  267,  268,  269,  257,   42,  272,  273,
                256,  275,   47,  271,  257,   59,   58,  260,  257,   41,
                263,   43,  194,   45,   41,  257,  269,  123,  260,  261,
                262,  263,  275,  205,  257,  256,  257,  269,  183,  260,
                256,  257,  263,  275,  256,  174,  256,  274,  269,  258,
                270,  271,  265,  184,  275,  261,  262,  261,  262,  257,
                257,  233,  260,  123,  200,  263,   41,  101,  102,  198,
                257,  269,  201,  265,  203,  103,  104,  275,  257,  125,
                40,   40,  213,  123,  123,  256,  256,  265,  265,  256,
                226,   41,  123,  229,  257,  231,   41,   40,  259,  265,
                256,  230,  123,  265,  276,  256,  258,  266,  267,  268,
                256,  271,  266,  267,  268,  125,  288,  289,  290,  291,
                256,  277,  278,  279,  280,  258,  277,  278,  279,  280,
                265,  277,  278,  279,  280,  277,  278,  279,  280,  257,
                125,  257,  257,  258,  258,  257,   79,  257,  258,  258,
                257,  276,  256,  276,  266,  267,  268,  256,  276,  266,
                267,  268,  125,   44,  257,  258,  257,  258,  257,  258,
                257,  258,  256,   44,  257,  258,  125,  256,  256,  123,
                256,   44,  256,  256,  256,  256,  256,  256,  256,  256,
                256,  256,  256,  113,  127,  125,  123,  257,   59,   59,
                260,   59,  122,  263,  264,  265,  266,  267,  268,  269,
                125,  257,  272,  273,  260,  275,  125,  263,  264,  265,
                266,  267,  268,  269,   44,  125,  272,  273,   44,  275,
                262,  256,  262,  256,  123,  125,  169,  256,  158,  257,
                125,   44,  258,  258,  258,  256,  166,  257,  256,   44,
                260,  256,  258,  263,  264,  265,  266,  267,  268,  269,
                125,  262,  272,  273,  197,  275,   59,   59,   59,   41,
                257,   41,  257,  256,  194,  260,  256,  125,  263,  264,
                265,  266,  267,  268,  269,  205,   44,  272,  273,   44,
                275,  125,   44,   44,  257,  256,  258,  260,   41,  256,
                263,  264,  265,  266,  267,  268,  269,  258,  257,  272,
                273,  260,  275,  233,  263,  264,  265,  266,  267,  268,
                269,  125,  258,  272,  273,  256,  275,  257,   41,  258,
                260,   41,   41,  263,  264,  265,  266,  267,  268,  269,
                125,   41,  272,  273,  256,  275,    0,   41,   41,   41,
                41,   41,  125,   41,  169,   20,  276,  141,   -1,   -1,
                -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  288,  289,
                290,  291,  257,   -1,   -1,  260,   -1,   -1,  263,  264,
                265,  266,  267,  268,  269,   -1,   -1,  272,  273,   -1,
                275,   -1,  257,   -1,   -1,  260,   -1,   -1,  263,  264,
                265,  266,  267,  268,  269,   -1,   -1,  272,  273,  257,
                275,   -1,  260,   -1,   -1,  263,  264,  265,  266,  267,
                268,  269,   -1,  257,  272,  273,   -1,  275,   -1,   -1,
                264,  265,  266,  267,  268,   -1,   32,   33,  272,  273,
                36,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
                -1,   -1,   -1,  257,   -1,   -1,   -1,   53,   -1,   55,
                264,  265,  266,  267,  268,   -1,   -1,   -1,  272,  273,
                -1,   -1,  257,   -1,   -1,   -1,   -1,   -1,   -1,  264,
                265,  266,  267,  268,  257,   -1,   -1,  272,  273,   -1,
                -1,  264,  265,  266,  267,  268,   -1,   -1,   -1,  272,
                273,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
                -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
                116,  117,  118,  119,  120,  121,
        };
    }
    final static short YYFINAL=14;
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
            "programa : '{' bloque_sentencias",
            "programa : bloque_sentencias '}'",
            "bloque_sentencias : bloque_sentencias sentencia",
            "bloque_sentencias : sentencia",
            "sentencia : sentencia_declarativa",
            "sentencia : sentencia_ejecutable",
            "bloque_declarativo : bloque_declarativo sentencia_declarativa",
            "bloque_declarativo : sentencia_declarativa",
            "bloque_ejecutable : '{' bloque_ejecutable sentencia_ejecutable '}' ','",
            "bloque_ejecutable : sentencia_ejecutable",
            "bloque_ejecutable : '{' bloque_ejecutable sentencia_ejecutable '}' error",
            "sentencia_control : FOR ID IN RANGE '(' CTE ';' CTE ';' CTE ')' bloque_ejecutable",
            "sentencia_control : FOR ID IN RANGE '(' CTE ';' CTE ')' bloque_ejecutable",
            "sentencia_control : FOR ID IN RANGE '(' CTE ')' bloque_ejecutable",
            "sentencia_control : FOR ID IN RANGE '(' ')' bloque_ejecutable",
            "sentencia_control : FOR IN RANGE '(' CTE ';' CTE ';' CTE ')' bloque_ejecutable error",
            "sentencia_control : FOR ID RANGE '(' CTE ';' CTE ';' CTE ')' bloque_ejecutable",
            "sentencia_control : FOR ID IN '(' CTE ';' CTE ';' CTE ')' bloque_ejecutable",
            "sentencia_control : FOR ID IN RANGE '(' CTE ';' CTE ';' CTE ')' error",
            "sentencia_declarativa : tipo lista_variables ','",
            "sentencia_declarativa : ID lista_variables ','",
            "sentencia_declarativa : declaracion_funcion",
            "sentencia_declarativa : declaracion_clase",
            "sentencia_declarativa : declaracion_distribuida",
            "sentencia_declarativa : declaracion_interfaz",
            "sentencia_declarativa : tipo lista_variables error",
            "sentencia_declarativa : ID lista_variables error",
            "tipo : SHORT",
            "tipo : ULONG",
            "tipo : DOUBLE",
            "lista_variables : ID",
            "lista_variables : lista_variables ';' ID",
            "declaracion_funcion : VOID ID '(' ')' '{' bloque_sentencias '}' ','",
            "declaracion_funcion : VOID ID '(' tipo ID ')' '{' bloque_sentencias '}' ','",
            "declaracion_funcion : VOID ID '(' tipo ID '{' bloque_sentencias '}' ','",
            "declaracion_funcion : VOID ID tipo ID ')' '{' bloque_sentencias '}' ','",
            "declaracion_funcion : VOID ID '(' ID ')' '{' bloque_sentencias '}' ',' error",
            "declaracion_funcion : VOID ID '(' tipo ID ')' '{' '}' ',' error",
            "declaracion_funcion : VOID ID '(' ')' '{' '}' ',' error",
            "declaracion_funcion : VOID ID '(' ')' '{' bloque_sentencias '}' error",
            "declaracion_funcion : VOID ID '(' tipo ID ')' '{' bloque_sentencias '}' error",
            "declaracion_clase : CLASS ID '{' bloque_declarativo '}'",
            "declaracion_clase : CLASS ID IMPLEMENT ID '{' bloque_declarativo '}'",
            "declaracion_clase : CLASS ID '{' '}' error",
            "declaracion_clase : CLASS ID IMPLEMENT ID '{' '}' error",
            "declaracion_clase : CLASS error",
            "declaracion_distribuida : IMPL FOR ID ':' '{' declaracion_funcion '}'",
            "declaracion_distribuida : IMPL FOR ID '{' declaracion_funcion '}'",
            "declaracion_distribuida : IMPL FOR ID ':' '{' '}' error",
            "declaracion_distribuida : IMPL ID ':' '{' declaracion_funcion '}' error",
            "declaracion_interfaz : INTERFACE ID '{' metodos_interfaz '}'",
            "declaracion_interfaz : INTERFACE ID '{' '}' error",
            "metodos_interfaz : metodo_interfaz ','",
            "metodos_interfaz : metodos_interfaz metodo_interfaz ','",
            "metodos_interfaz : metodo_interfaz",
            "metodos_interfaz : metodos_interfaz metodo_interfaz",
            "metodo_interfaz : VOID ID '(' tipo ID ')'",
            "metodo_interfaz : VOID ID '(' ')'",
            "metodo_interfaz : VOID ID '(' tipo ID",
            "metodo_interfaz : VOID ID tipo ID ')'",
            "metodo_interfaz : VOID ID '(' ID ')' error",
            "sentencia_ejecutable : asignacion",
            "sentencia_ejecutable : invocacion_funcion",
            "sentencia_ejecutable : seleccion",
            "sentencia_ejecutable : imprimir",
            "sentencia_ejecutable : ref_clase '(' ')' ','",
            "sentencia_ejecutable : sentencia_control",
            "sentencia_ejecutable : RETURN ','",
            "sentencia_ejecutable : RETURN error",
            "sentencia_ejecutable : ref_clase '(' ')' error",
            "asignacion : ID '=' expresion ','",
            "asignacion : ID MENOS_IGUAL expresion ','",
            "asignacion : ref_clase '=' expresion ','",
            "asignacion : ref_clase MENOS_IGUAL expresion ','",
            "asignacion : ID '=' ',' error",
            "asignacion : ID MENOS_IGUAL ',' error",
            "asignacion : ref_clase '=' ',' error",
            "asignacion : ref_clase MENOS_IGUAL ',' error",
            "asignacion : ID '=' expresion error",
            "asignacion : ID MENOS_IGUAL expresion error",
            "asignacion : ref_clase '=' expresion error",
            "asignacion : ref_clase MENOS_IGUAL expresion error",
            "expresion : expresion '+' termino",
            "expresion : expresion '-' termino",
            "expresion : termino",
            "termino : termino '*' factor",
            "termino : termino '/' factor",
            "termino : factor",
            "factor : ID",
            "factor : CTE",
            "factor : '-' CTE",
            "invocacion_funcion : ID '(' expresion ')' ','",
            "invocacion_funcion : ID '(' ')' ','",
            "invocacion_funcion : ID '(' expresion ')' error",
            "invocacion_funcion : ID '(' ')' error",
            "seleccion : IF '(' condicion ')' bloque_ejecutable ELSE bloque_ejecutable END_IF ','",
            "seleccion : IF '(' condicion ')' bloque_ejecutable END_IF ','",
            "seleccion : IF '(' ')' bloque_ejecutable END_IF ',' error",
            "seleccion : IF '(' ')' bloque_ejecutable ELSE bloque_ejecutable END_IF ',' error",
            "seleccion : IF '(' condicion ')' END_IF ',' error",
            "seleccion : IF '(' condicion ')' ELSE bloque_ejecutable END_IF ',' error",
            "seleccion : IF '(' condicion ')' bloque_ejecutable ELSE bloque_ejecutable END_IF error",
            "seleccion : IF '(' condicion ')' bloque_ejecutable END_IF error",
            "condicion : expresion MAYOR_IGUAL expresion",
            "condicion : expresion MENOR_IGUAL expresion",
            "condicion : expresion '<' expresion",
            "condicion : expresion '>' expresion",
            "condicion : expresion IGUAL expresion",
            "condicion : expresion DISTINTO expresion",
            "imprimir : PRINT CADENA ','",
            "imprimir : PRINT ',' error",
            "imprimir : PRINT CADENA error",
            "ref_clase : ID '.' ID",
            "ref_clase : ref_clase '.' ID",
    };

//#line 218 "gramatica.y"
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
    //#line 575 "Parser.java"
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
                case 2:
//#line 12 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba '}'");}
                break;
                case 3:
//#line 13 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba '{'");}
                break;
                case 12:
//#line 30 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba ','");}
                break;
                case 13:
//#line 33 "gramatica.y"
                {System.out.println("Linea: " + al.getLinea() + " Sentencia FOR");}
                break;
                case 14:
//#line 34 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " falta una constante");}
                break;
                case 15:
//#line 35 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " faltan constantes");}
                break;
                case 16:
//#line 36 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " faltan constantes");}
                break;
                case 17:
//#line 37 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba un identificador");}
                break;
                case 18:
//#line 38 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " falta la palabra reservada IN");}
                break;
                case 19:
//#line 39 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " falta la palabra reservada RANGE");}
                break;
                case 20:
//#line 40 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " no se puede definir un FOR sin cuerpo");}
                break;
                case 22:
//#line 45 "gramatica.y"
                {
                    /* accion que chequee si el tipo es “CLASE” (id no puede ser un tipo si no es una clase)*/
                    if (!al.getTablaSimbolos().getAtributos(val_peek(2).sval).isTipo("CLASE")){
                        System.out.println("Error linea "+al.getLinea()+": "+val_peek(2).sval+" no es tipo CLASE");
                    }
                }
                break;
                case 27:
//#line 55 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba ','");}
                break;
                case 28:
//#line 56 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba ','");}
                break;
                case 34:
//#line 68 "gramatica.y"
                {System.out.println("Linea: " + al.getLinea() + " Declaracion funcion VOID " + val_peek(6).sval);}
                break;
                case 35:
//#line 69 "gramatica.y"
                {System.out.println("Linea: " + al.getLinea() + " Declaracion funcion VOID " + val_peek(8).sval);}
                break;
                case 36:
//#line 70 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba ')'");}
                break;
                case 37:
//#line 71 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba '('");}
                break;
                case 38:
//#line 72 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " falta el tipo de " + val_peek(6).sval);}
                break;
                case 39:
//#line 73 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " no se puede declarar una funcion sin cuerpo");}
                break;
                case 40:
//#line 74 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " no se puede declarar una funcion sin cuerpo");}
                break;
                case 41:
//#line 75 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba ','");}
                break;
                case 42:
//#line 76 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba ','");}
                break;
                case 43:
//#line 79 "gramatica.y"
                {
                    System.out.println("Linea: " + al.getLinea() + " Declaracion CLASE " + val_peek(3).sval);
                    /* accion para ID que establezca como tipo “CLASE” */
                    al.getTablaSimbolos().getAtributos(val_peek(3).sval).setTipo("CLASE");
                }
                break;
                case 44:
//#line 84 "gramatica.y"
                {
                    System.out.println("Linea: " + al.getLinea() + " Declaracion CLASE " + val_peek(5).sval);
                    /* accion que chequea si ID_2 es una interfaz y asignar tipo CLASE a ID_1 */
                    if (al.getTablaSimbolos().getAtributos(val_peek(3).sval).isTipo("INTERFACE")){
                        al.getTablaSimbolos().getAtributos(val_peek(5).sval).setTipo("CLASE");
                    } else {
                        System.out.println("Error linea "+al.getLinea()+": "+val_peek(3).sval+" no es un INTERFACE");
                    }
                }
                break;
                case 45:
//#line 93 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " no se puede definir una clase sin cuerpo");}
                break;
                case 46:
//#line 94 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " no se puede definir una clase sin cuerpo");}
                break;
                case 47:
//#line 95 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " revisar declaracion de clase");}
                break;
                case 48:
//#line 98 "gramatica.y"
                {
                    System.out.println("Linea: " + al.getLinea() + " Declaracion DISTRIBUIDA para " + val_peek(4).sval);
                    /* accion para fijarse que id sea una clase */
                    if (!al.getTablaSimbolos().getAtributos(val_peek(4).sval).isTipo("CLASE")){
                        System.out.println("Error linea "+al.getLinea()+": "+val_peek(4).sval+" no es una clase");
                    }
                }
                break;
                case 49:
//#line 105 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba ':'");}
                break;
                case 50:
//#line 106 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " no se puede definir una declaracion distribuida sin cuerpo");}
                break;
                case 51:
//#line 107 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " falta palabra reservada FOR");}
                break;
                case 52:
//#line 111 "gramatica.y"
                {/* accion para ID que establezca como tipo “INTERFACE” */
                    al.getTablaSimbolos().getAtributos(val_peek(3).sval).setTipo("INTERFACE");
                }
                break;
                case 53:
//#line 114 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " no se puede declarar una interfaz sin metodos");}
                break;
                case 56:
//#line 119 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba ','");}
                break;
                case 57:
//#line 120 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba ','");}
                break;
                case 58:
//#line 123 "gramatica.y"
                {System.out.println("Linea: " + al.getLinea() + " Declaracion metodo VOID " + val_peek(4).sval);}
                break;
                case 59:
//#line 124 "gramatica.y"
                {System.out.println("Linea: " + al.getLinea() + " Declaracion metodo VOID " + val_peek(2).sval);}
                break;
                case 60:
//#line 125 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba ')'");}
                break;
                case 61:
//#line 126 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba '('");}
                break;
                case 62:
//#line 127 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " falta el tipo de " + val_peek(2).sval);}
                break;
                case 63:
//#line 130 "gramatica.y"
                {System.out.println("Linea: " + al.getLinea() + " ASIGNACION");}
                break;
                case 64:
//#line 131 "gramatica.y"
                {System.out.println("Linea: " + al.getLinea() + " INVOCACION FUNCION");}
                break;
                case 65:
//#line 132 "gramatica.y"
                {System.out.println("Linea: " + al.getLinea() + " Sentencia IF");}
                break;
                case 70:
//#line 137 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba ','");}
                break;
                case 71:
//#line 138 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba ','");}
                break;
                case 76:
//#line 145 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " Se esperaba expresion");}
                break;
                case 77:
//#line 146 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " Se esperaba expresion");}
                break;
                case 78:
//#line 147 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " Se esperaba expresion");}
                break;
                case 79:
//#line 148 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " Se esperaba expresion");}
                break;
                case 80:
//#line 149 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba ','");}
                break;
                case 81:
//#line 150 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba ','");}
                break;
                case 82:
//#line 151 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba ','");}
                break;
                case 83:
//#line 152 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba ','");}
                break;
                case 84:
//#line 155 "gramatica.y"
                {System.out.println("Linea: " + al.getLinea() + " SUMA");}
                break;
                case 85:
//#line 156 "gramatica.y"
                {System.out.println("Linea: " + al.getLinea() + " RESTA");}
                break;
                case 87:
//#line 160 "gramatica.y"
                {System.out.println("Linea: " + al.getLinea() + " MULTIPLICACION");}
                break;
                case 88:
//#line 161 "gramatica.y"
                {System.out.println("Linea: " + al.getLinea() + " DIVISION");}
                break;
                case 91:
//#line 166 "gramatica.y"
                {   chequeoRango(val_peek(0).sval, al);
                    al.getTablaSimbolos().getAtributos(val_peek(0).sval).sumarUso();
                }
                break;
                case 92:
//#line 169 "gramatica.y"
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
                case 95:
//#line 184 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba ','");}
                break;
                case 96:
//#line 185 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba ','");}
                break;
                case 99:
//#line 190 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " falta condicion");}
                break;
                case 100:
//#line 191 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " falta condicion");}
                break;
                case 101:
//#line 192 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " cuerpo de IF vacio");}
                break;
                case 102:
//#line 193 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " cuerpo de IF vacio");}
                break;
                case 103:
//#line 194 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba ','");}
                break;
                case 104:
//#line 195 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba ','");}
                break;
                case 112:
//#line 207 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba una cadena de caracteres");}
                break;
                case 113:
//#line 208 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba ','");}
                break;
//#line 1041 "Parser.java"
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
    public void run(Analizador_Lexico al) {
        yyparse(al);
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
