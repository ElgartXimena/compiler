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






//#line 2 ".\gramatica.y"

//#line 19 "Parser.java"

package AnalizadorSintactico;
import AnalizadorLexico.*;
import GeneracionCodigoIntermedio.Pila;


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
            0,    0,    0,    1,    1,    2,    2,    2,    5,    5,
            6,    6,    3,    3,    3,    3,    3,    7,    7,    7,
            7,   12,   12,   12,   13,   13,    8,    8,    8,    8,
            8,    8,    8,   14,   14,   15,   15,   16,   16,   16,
            9,    9,    9,    9,    9,   17,   17,   18,   18,   19,
            19,   19,   10,   10,   10,   10,   20,   20,   11,   11,
            21,   21,   22,   22,   22,   22,   23,   23,   23,   23,
            23,    4,    4,    4,    4,    4,    4,    4,    4,    4,
            4,    4,    4,    4,   24,   24,   24,   24,   24,   24,
            24,   24,   24,   24,   24,   24,   30,   30,   30,   31,
            31,   31,   32,   32,   33,   33,   25,   25,   26,   34,
            34,   36,   36,   36,   36,   36,   36,   35,   35,   37,
            37,   38,   38,   27,   27,   28,   28,   29,   29,   29,
            29,   29,   29,   39,   39,   39,   40,
    };
    final static short yylen[] = {                            2,
            3,    2,    2,    2,    1,    1,    1,    2,    2,    1,
            2,    1,    1,    1,    1,    1,    1,    3,    3,    3,
            3,    1,    1,    1,    1,    3,    6,    8,    7,    7,
            8,    6,    8,    3,    3,    2,    1,    1,    1,    1,
            4,    6,    6,    4,    6,    3,    3,    2,    1,    1,
            1,    2,    6,    6,    6,    6,    3,    3,    4,    4,
            3,    3,    2,    3,    1,    2,    6,    4,    5,    5,
            6,    1,    2,    2,    2,    2,    2,    2,    4,    4,
            2,    2,    2,    2,    4,    4,    4,    4,    3,    4,
            3,    4,    4,    4,    4,    4,    3,    3,    1,    3,
            3,    1,    1,    1,    1,    2,    4,    3,    4,    3,
            3,    3,    3,    3,    3,    3,    3,    2,    1,    3,
            3,    4,    4,    2,    2,    3,    3,    6,    6,    5,
            5,    6,    6,    7,    6,    4,    3,
    };
    final static short yydefred[] = {                         0,
            0,    0,    0,    0,    0,    0,   22,   23,   24,    0,
            0,    0,    0,    0,    0,    0,    5,    6,    7,   13,
            14,   15,   16,   17,    0,   72,    0,    0,    0,    0,
            0,    8,   25,    0,    0,    0,    0,    0,    0,    0,
            0,  125,  124,    0,    0,    0,    0,    0,    0,    0,
            84,   83,    0,    3,    4,    0,   74,   73,   76,   75,
            78,   77,    0,    0,    0,    0,    0,   82,   81,  103,
            105,    0,    0,    0,    0,  102,  104,   89,  108,    0,
            0,  126,   21,   19,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    1,   20,   18,    0,    0,   91,    0,
            0,  127,   90,  106,   94,   86,    0,    0,    0,    0,
            107,   93,   85,   26,  111,    0,    0,    0,    0,    0,
            0,  110,    0,    0,   12,    0,  109,    0,  118,    0,
            0,    0,    0,   50,   51,    0,   49,   44,   41,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,   60,   59,   92,   96,
            88,   80,   79,   95,   87,    0,    0,  100,  101,    0,
            0,    0,    0,    0,    0,  121,  120,   11,    0,    0,
            0,   52,   47,   46,   48,    0,    0,    0,    0,    0,
            0,    0,    0,    0,  131,  130,    0,    0,    0,    0,
            0,    0,    0,   62,   61,    0,   63,    0,    0,   45,
            42,   43,    0,    0,   40,   38,   39,    0,   37,   32,
            27,    0,    0,    0,    0,  133,  128,  132,    0,    0,
            129,   58,   57,   55,   56,   53,   54,    0,    0,   64,
            123,  122,    0,   35,   34,   36,    0,   29,   30,  137,
            0,  136,    0,   68,    0,    0,   31,   33,   28,    0,
            0,    0,    0,   70,    0,  135,   71,   67,  134,
    };
    final static short yydgoto[] = {                         15,
            16,   17,   18,   19,    0,  136,   20,   21,   22,   23,
            24,   25,   39,  198,  228,  229,   94,  146,  147,  160,
            103,  165,  166,   26,   27,   28,   29,   30,   31,   74,
            75,   76,   77,   41,   90,   88,   91,  139,  156,  203,
    };
    final static short yysindex[] = {                      -103,
            21,  -12,   69, -149, -141, -126,    0,    0,    0, -155,
            -139, -122,  -22,  177,    0,  -78,    0,    0,    0,    0,
            0,    0,    0,    0, -114,    0,   40,   41,   42,   -4,
            44,    0,    0,   23, -101,    5,  -34,  -99,  -21,   18,
            36,    0,    0,  -98,  -10, -132, -104,  113,  -84,   57,
            0,    0,  -64,    0,    0,   39,    0,    0,    0,    0,
            0,    0,   25,  -82,  134,  -34,  -74,    0,    0,    0,
            0,  -72,  -51,   28,   90,    0,    0,    0,    0,   19,
            31,    0,    0,    0,  -44,  -46,  -27,  175,   87,  -45,
            -43,  -26,  149,   45,    3,   35,  -39,  181,  181,  103,
            -2,  -59,   46,    0,    0,    0,   38,   34,    0,   47,
            37,    0,    0,    0,    0,    0,  -34,  -34,  -34,  -34,
            0,    0,    0,    0,    0,  -34,  -34,  -34,  -34,  -34,
            -34,    0,   -3,   43,    0,   89,    0,  106,    0,  165,
            247,  -29,   54,    0,    0,  161,    0,    0,    0,  270,
            190,   59,  273,   13,  -28,  199,  199,  199,  -25,  279,
            103,  280,   68,   75,   -6,  285,    0,    0,    0,    0,
            0,    0,    0,    0,    0,   90,   90,    0,    0,  101,
            101,  101,  101,  101,  101,    0,    0,    0,   97,   48,
            76,    0,    0,    0,    0,  190,   52,   49,    4,  190,
            -135,  -75,   78,   58,    0,    0,   79,   81,  218,   95,
            50,   99,   -1,    0,    0,  301,    0,  107,  108,    0,
            0,    0,  309,  111,    0,    0,    0,   73,    0,    0,
            0,  190,  315,  326,  130,    0,    0,    0,  -28,  120,
            0,    0,    0,    0,    0,    0,    0,   11,  123,    0,
            0,    0,  126,    0,    0,    0,   51,    0,    0,    0,
            67,    0,  343,    0,  128,  345,    0,    0,    0,  -28,
            132,  133,  353,    0,  354,    0,    0,    0,    0,
    };
    final static short yyrindex[] = {                         0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,  396,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,  -41,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            135,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,   20,    0,    0,    0,    0,
            0,    0,    0,    0,    0,  -36,  -31,    0,    0,  357,
            361,  362,  367,  368,  369,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,   24,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,  -38,    0,    0,    0,    0,    0,    0,
    };
    final static short yygindex[] = {                         0,
            397,   80,    0,  172,    0,  -60,  235,  233,    0,    0,
            0,    6,  387,  -76,    0,  185,  -37,    0,  274,  -47,
            0,    0,  254,    0,    0,    0,    0,    0,    0,  338,
            30,   32, -128,    0,    0,    0,    0,    0,   14,  -52,
    };
    final static int YYTABLESIZE=469;
    static short yytable[];
    static { yytable();}
    static void yytable(){
        yytable = new short[]{                         99,
                155,   99,   99,   99,   97,   69,   97,   97,   97,   98,
                73,   98,   98,   98,  192,  117,   73,  118,   99,   14,
                99,   52,   84,   97,   93,   97,  204,   36,   98,   95,
                98,   35,  130,   38,  131,   65,   36,   85,  248,   64,
                35,   67,   38,  151,  232,   79,   54,  201,   37,   73,
                96,  264,  155,  162,  141,  161,   66,   37,   86,  121,
                104,  117,   73,  118,   32,  164,   72,   73,  107,   73,
                117,  116,  118,  117,  123,  118,  117,  171,  118,  117,
                175,  118,  106,   58,   60,   62,   69,   69,  149,  168,
                173,  221,  231,  246,  269,   55,   93,   85,  240,  208,
                152,   46,  190,  205,  206,  207,   42,  271,   40,   43,
                261,  157,  158,  211,   47,   44,  239,   48,  215,  223,
                159,  133,  233,  234,    3,  270,  197,    4,  219,   49,
                45,  119,   55,   10,   50,  201,  120,   97,   98,   13,
                235,  275,   33,  117,   65,  118,  176,  177,   66,  237,
                178,  179,    1,    2,   78,  257,    3,   82,   89,    4,
                5,    6,    7,    8,    9,   10,   99,  202,   11,   12,
                100,   13,  101,  109,  110,   92,  224,    1,    2,  102,
                236,    3,  112,  113,    4,    5,    6,    7,    8,    9,
                10,    1,    2,   11,   12,    3,   13,  255,    4,    5,
                6,    7,    8,    9,   10,  163,  114,   11,   12,  125,
                13,  134,  124,  187,   99,  132,  137,  138,  249,   97,
                155,  218,   70,   71,   98,  159,   69,   33,  189,   71,
                140,  154,  252,   51,   83,   99,   99,   99,   99,    6,
                97,   97,   97,   97,   33,   98,   98,   98,   98,  126,
                127,  128,  129,  265,  260,    7,    8,    9,  163,  150,
                135,   70,   71,   34,    7,    8,    9,  263,    7,    8,
                9,   63,   34,  143,   70,   71,    7,    8,    9,   70,
                71,   70,   71,  115,   65,  194,  122,   93,   66,  170,
                191,  153,  174,  169,  105,   57,   59,   61,  186,   68,
                148,  167,  172,  220,  230,  245,  268,  188,    2,  193,
                196,    3,  197,  200,    4,  199,    6,    7,    8,    9,
                10,  201,  210,  212,  213,  145,   13,  144,  217,    2,
                214,  222,    3,  238,  241,    4,  242,    6,    7,    8,
                9,   10,  243,  133,  250,  133,    3,   13,    3,    4,
                244,    4,  253,  133,  247,   10,    3,   10,  258,    4,
                135,   13,  251,   13,  133,   10,  254,    3,  225,  259,
                4,   13,  135,   80,   81,  262,   10,   87,  145,  266,
                144,  267,   13,  272,  273,  274,  133,  276,  277,    3,
                188,  209,    4,  278,  279,    2,  119,  112,   10,  225,
                108,  113,  116,  111,   13,  142,  188,  117,  114,  115,
                53,   56,  256,    6,    7,    8,    9,  142,  216,  195,
                0,    0,    0,    0,    0,    6,    7,    8,    9,  227,
                0,  226,    1,    2,    0,    0,    3,    0,    0,    4,
                5,    6,    7,    8,    9,   10,    0,    0,   11,   12,
                0,   13,    0,    0,    0,    0,    0,    0,    0,    0,
                227,    0,  226,  180,  181,  182,  183,  184,  185,
        };
    }
    static short yycheck[];
    static { yycheck(); }
    static void yycheck() {
        yycheck = new short[] {                         41,
                40,   43,   44,   45,   41,   44,   43,   44,   45,   41,
                45,   43,   44,   45,   44,   43,   45,   45,   60,  123,
                62,   44,   44,   60,  123,   62,  155,   40,   60,   40,
                62,   44,   60,   46,   62,   40,   40,   59,   40,   44,
                44,   46,   46,   41,   41,   41,  125,  123,   61,   45,
                45,   41,   40,  101,   92,   58,   61,   61,   41,   41,
                125,   43,   45,   45,   44,  125,   44,   45,   44,   45,
                43,   44,   45,   43,   44,   45,   43,   44,   45,   43,
                44,   45,   44,   44,   44,   44,  125,   44,   44,   44,
                44,   44,   44,   44,   44,   16,  123,   59,   41,  125,
                95,  257,  140,  156,  157,  158,  256,   41,   40,  259,
                239,   98,   99,  161,  270,  257,   59,  257,  125,  196,
                123,  257,  199,  200,  260,   59,  123,  263,  189,  269,
                257,   42,   53,  269,  257,  123,   47,  270,  271,  275,
                201,  270,  257,   43,  125,   45,  117,  118,  125,  202,
                119,  120,  256,  257,  256,  232,  260,  257,  123,  263,
                264,  265,  266,  267,  268,  269,  271,  154,  272,  273,
                58,  275,  257,  256,   41,  274,  125,  256,  257,  123,
                256,  260,  257,  256,  263,  264,  265,  266,  267,  268,
                269,  256,  257,  272,  273,  260,  275,  125,  263,  264,
                265,  266,  267,  268,  269,  265,  258,  272,  273,  256,
                275,  125,  257,  125,  256,   41,  262,  261,  213,  256,
                40,  125,  257,  258,  256,  123,  265,  257,  123,  258,
                257,  271,  125,  256,  256,  277,  278,  279,  280,  265,
                277,  278,  279,  280,  257,  277,  278,  279,  280,  277,
                278,  279,  280,  248,  125,  266,  267,  268,  265,  257,
                89,  257,  258,  276,  266,  267,  268,  257,  266,  267,
                268,  276,  276,  125,  257,  258,  266,  267,  268,  257,
                258,  257,  258,  256,  265,  125,  256,  123,  265,  256,
                44,  257,  256,  256,  256,  256,  256,  256,  256,  256,
                256,  256,  256,  256,  256,  256,  256,  136,  257,  256,
                41,  260,  123,   41,  263,  257,  265,  266,  267,  268,
                269,  123,   44,   44,  257,   93,  275,   93,   44,  257,
                256,  256,  260,  256,  256,  263,  256,  265,  266,  267,
                268,  269,  125,  257,   44,  257,  260,  275,  260,  263,
                256,  263,   44,  257,  256,  269,  260,  269,   44,  263,
                189,  275,  256,  275,  257,  269,  256,  260,  197,   44,
                263,  275,  201,   36,   37,  256,  269,   40,  146,  257,
                146,  256,  275,   41,  257,   41,  257,  256,  256,  260,
                219,  159,  263,   41,   41,    0,  262,   41,  269,  228,
                63,   41,   41,   66,  275,  257,  235,   41,   41,   41,
                14,   25,  228,  265,  266,  267,  268,  257,  165,  146,
                -1,   -1,   -1,   -1,   -1,  265,  266,  267,  268,  197,
                -1,  197,  256,  257,   -1,   -1,  260,   -1,   -1,  263,
                264,  265,  266,  267,  268,  269,   -1,   -1,  272,  273,
                -1,  275,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
                228,   -1,  228,  126,  127,  128,  129,  130,  131,
        };
    }
    final static short YYFINAL=15;
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
            "sentencia : error ','",
            "bloque_declarativo : bloque_declarativo sentencia_declarativa",
            "bloque_declarativo : sentencia_declarativa",
            "bloque_ejecutable : bloque_ejecutable sentencia_ejecutable",
            "bloque_ejecutable : sentencia_ejecutable",
            "sentencia_declarativa : declaracion_variables",
            "sentencia_declarativa : declaracion_funcion",
            "sentencia_declarativa : declaracion_clase",
            "sentencia_declarativa : declaracion_distribuida",
            "sentencia_declarativa : declaracion_interfaz",
            "declaracion_variables : tipo lista_variables ','",
            "declaracion_variables : ID lista_variables ','",
            "declaracion_variables : tipo lista_variables error",
            "declaracion_variables : ID lista_variables error",
            "tipo : SHORT",
            "tipo : ULONG",
            "tipo : DOUBLE",
            "lista_variables : ID",
            "lista_variables : lista_variables ';' ID",
            "declaracion_funcion : VOID ID '(' ')' cuerpo_funcion ','",
            "declaracion_funcion : VOID ID '(' tipo ID ')' cuerpo_funcion ','",
            "declaracion_funcion : VOID ID '(' tipo ID cuerpo_funcion ','",
            "declaracion_funcion : VOID ID tipo ID ')' cuerpo_funcion ','",
            "declaracion_funcion : VOID ID '(' ID ')' cuerpo_funcion ',' error",
            "declaracion_funcion : VOID ID '(' ')' cuerpo_funcion error",
            "declaracion_funcion : VOID ID '(' tipo ID ')' cuerpo_funcion error",
            "cuerpo_funcion : '{' bloque_funcion '}'",
            "cuerpo_funcion : '{' '}' error",
            "bloque_funcion : bloque_funcion sentencia_funcion",
            "bloque_funcion : sentencia_funcion",
            "sentencia_funcion : declaracion_variables",
            "sentencia_funcion : declaracion_funcion",
            "sentencia_funcion : sentencia_ejecutable",
            "declaracion_clase : CLASS ID cuerpo_clase ','",
            "declaracion_clase : CLASS ID IMPLEMENT ID cuerpo_clase ','",
            "declaracion_clase : CLASS ID IMPLEMENT cuerpo_clase ',' error",
            "declaracion_clase : CLASS ID cuerpo_clase error",
            "declaracion_clase : CLASS ID IMPLEMENT ID cuerpo_clase error",
            "cuerpo_clase : '{' bloque_clase '}'",
            "cuerpo_clase : '{' '}' error",
            "bloque_clase : bloque_clase sentencia_clase",
            "bloque_clase : sentencia_clase",
            "sentencia_clase : declaracion_variables",
            "sentencia_clase : declaracion_funcion",
            "sentencia_clase : ID ','",
            "declaracion_distribuida : IMPL FOR ID ':' cuerpo_dec_dist ','",
            "declaracion_distribuida : IMPL FOR ID cuerpo_dec_dist ',' error",
            "declaracion_distribuida : IMPL ID ':' cuerpo_dec_dist ',' error",
            "declaracion_distribuida : IMPL FOR ID ':' cuerpo_dec_dist error",
            "cuerpo_dec_dist : '{' declaracion_funcion '}'",
            "cuerpo_dec_dist : '{' '}' error",
            "declaracion_interfaz : INTERFACE ID cuerpo_interfaz ','",
            "declaracion_interfaz : INTERFACE ID cuerpo_interfaz error",
            "cuerpo_interfaz : '{' metodos_interfaz '}'",
            "cuerpo_interfaz : '{' '}' error",
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
            "sentencia_ejecutable : invocacion_funcion ','",
            "sentencia_ejecutable : invocacion_funcion error",
            "sentencia_ejecutable : seleccion ','",
            "sentencia_ejecutable : seleccion error",
            "sentencia_ejecutable : imprimir ','",
            "sentencia_ejecutable : imprimir error",
            "sentencia_ejecutable : ref_clase '(' ')' ','",
            "sentencia_ejecutable : ref_clase '(' ')' error",
            "sentencia_ejecutable : sentencia_control ','",
            "sentencia_ejecutable : sentencia_control error",
            "sentencia_ejecutable : RETURN ','",
            "sentencia_ejecutable : RETURN error",
            "asignacion : ID '=' expresion ','",
            "asignacion : ID MENOS_IGUAL expresion ','",
            "asignacion : ref_clase '=' expresion ','",
            "asignacion : ref_clase MENOS_IGUAL expresion ','",
            "asignacion : ID ',' error",
            "asignacion : ID MENOS_IGUAL ',' error",
            "asignacion : ref_clase ',' error",
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
            "factor : constante",
            "constante : CTE",
            "constante : '-' CTE",
            "invocacion_funcion : ID '(' expresion ')'",
            "invocacion_funcion : ID '(' ')'",
            "seleccion : IF condicion cuerpo_if END_IF",
            "condicion : '(' comparacion ')'",
            "condicion : '(' ')' error",
            "comparacion : expresion MAYOR_IGUAL expresion",
            "comparacion : expresion MENOR_IGUAL expresion",
            "comparacion : expresion '<' expresion",
            "comparacion : expresion '>' expresion",
            "comparacion : expresion IGUAL expresion",
            "comparacion : expresion DISTINTO expresion",
            "cuerpo_if : cuerpo_then cuerpo_else",
            "cuerpo_if : cuerpo_then",
            "cuerpo_then : '{' bloque_ejecutable '}'",
            "cuerpo_then : '{' '}' error",
            "cuerpo_else : ELSE '{' bloque_ejecutable '}'",
            "cuerpo_else : ELSE '{' '}' error",
            "imprimir : PRINT CADENA",
            "imprimir : PRINT error",
            "ref_clase : ID '.' ID",
            "ref_clase : ref_clase '.' ID",
            "sentencia_control : FOR ID IN RANGE encabezado_for cuerpo_for",
            "sentencia_control : FOR IN RANGE encabezado_for cuerpo_for error",
            "sentencia_control : FOR ID RANGE encabezado_for cuerpo_for",
            "sentencia_control : FOR ID IN encabezado_for cuerpo_for",
            "sentencia_control : FOR ID IN RANGE cuerpo_for error",
            "sentencia_control : FOR ID IN RANGE encabezado_for error",
            "encabezado_for : '(' constante ';' constante ';' constante ')'",
            "encabezado_for : '(' constante ';' constante ')' error",
            "encabezado_for : '(' constante ')' error",
            "cuerpo_for : '{' bloque_ejecutable '}'",
    };

    //#line 327 "gramatica.y"
    /* CODE SECTION */
    public String tipo = "";
    public Pila pilaAmbito = new Pila("main");

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


    public void setAmbito(String lexema){
        String nuevoAmb = lexema;
        for(Object o: pilaAmbito.getElements()){
            nuevoAmb = nuevoAmb.concat("#"+(String)o);
        }
        Tabla_Simbolos.modificarClave(lexema, nuevoAmb);
    }

    public int yylex(){
        int tok = Analizador_Lexico.yylex();
        yylval = new ParserVal(Analizador_Lexico.buffer);
        return tok;
    }
    public void yyerror(String s){}
    //#line 572 "Parser.java"
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
                case 2:
//#line 12 "gramatica.y"
                {System.out.println("ERROR EN PROGRAMA. Linea: " + Analizador_Lexico.cantLineas + " se esperaba '}'");}
                break;
                case 3:
//#line 13 "gramatica.y"
                {System.out.println("ERROR EN PROGRAMA. Linea: " + Analizador_Lexico.cantLineas + " se esperaba '{'");}
                break;
                case 8:
//#line 22 "gramatica.y"
                {System.out.println("ERROR EN SENTENCIA. Linea: " + Analizador_Lexico.cantLineas);}
                break;
                case 18:
//#line 41 "gramatica.y"
                {
                    System.out.println("Linea: " + Analizador_Lexico.cantLineas + " Declaracion de VARIABLE/S de TIPO " + val_peek(2).sval);
                }
                break;
                case 19:
//#line 45 "gramatica.y"
                {
                    if (Tabla_Simbolos.getAtributos(val_peek(2).sval).isUso("CLASE")){
                        tipo = val_peek(2).sval;
                    } else {
                        System.out.println("ERROR EN DECLARACION DE VARIABLES. Linea: "+Analizador_Lexico.cantLineas+": "+val_peek(2).sval+" no es tipo CLASE");
                    }
                    System.out.println("Linea: " + Analizador_Lexico.cantLineas + " Declaracion de VARIABLE/S de TIPO " + val_peek(2).sval);
                }
                break;
                case 20:
//#line 53 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DE VARIABLES. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 21:
//#line 54 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DE VARIABLES. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 22:
//#line 57 "gramatica.y"
                {tipo = val_peek(0).sval;}
                break;
                case 23:
//#line 58 "gramatica.y"
                {tipo = val_peek(0).sval;}
                break;
                case 24:
//#line 59 "gramatica.y"
                {tipo = val_peek(0).sval;}
                break;
                case 25:
//#line 63 "gramatica.y"
                {
                    /*poner ambito a ID*/
                    Tabla_Simbolos.getAtributos(val_peek(0).sval).setTipo(tipo);
                    Tabla_Simbolos.getAtributos(val_peek(0).sval).setUso("VARIABLE");
                }
                break;
                case 26:
//#line 69 "gramatica.y"
                {
                    /*poner ambito a ID*/
                    Tabla_Simbolos.getAtributos(val_peek(0).sval).setTipo(tipo);
                    Tabla_Simbolos.getAtributos(val_peek(0).sval).setUso("VARIABLE");
                }
                break;
                case 27:
//#line 77 "gramatica.y"
                {
                    /*poner ambito a IDfuncion, apilar nuevo ambito*/
                    System.out.println("Linea: " + Analizador_Lexico.cantLineas + " Declaracion funcion VOID " + val_peek(4).sval);
                    setAmbito(val_peek(4).sval);
                    pilaAmbito.insertar(val_peek(4).sval);
                    Tabla_Simbolos.getAtributos(val_peek(4).sval).setUso("FUNCION");
                }
                break;
                case 28:
//#line 85 "gramatica.y"
                {
                    System.out.println("Linea: " + Analizador_Lexico.cantLineas + " Declaracion funcion VOID " + val_peek(6).sval);
                    Tabla_Simbolos.getAtributos(val_peek(6).sval).setUso("FUNCION");
                    Tabla_Simbolos.getAtributos(val_peek(3).sval).setUso("PARAMETRO");
                    Tabla_Simbolos.getAtributos(val_peek(3).sval).setTipo(val_peek(4).sval);
                    setAmbito(val_peek(6).sval);
                    pilaAmbito.insertar(val_peek(6).sval);
                    setAmbito(val_peek(3).sval);
                }
                break;
                case 29:
//#line 94 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DE FUNCION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ')'");}
                break;
                case 30:
//#line 95 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DE FUNCION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba '('");}
                break;
                case 31:
//#line 96 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DE FUNCION. Linea: " + Analizador_Lexico.cantLineas + " falta el tipo de " + val_peek(4).sval);}
                break;
                case 32:
//#line 97 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DE FUNCION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 33:
//#line 98 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DE FUNCION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 34:
//#line 101 "gramatica.y"
                {pilaAmbito.eliminarTope();}
                break;
                case 35:
//#line 102 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DE FUNCION. Linea: " + Analizador_Lexico.cantLineas + " no se puede declarar una funcion sin cuerpo");}
                break;
                case 41:
//#line 115 "gramatica.y"
                {
                    System.out.println("Linea: " + Analizador_Lexico.cantLineas + " Declaracion CLASE " + val_peek(2).sval);
                    Tabla_Simbolos.getAtributos(val_peek(2).sval).setUso("CLASE");

                }
                break;
                case 42:
//#line 121 "gramatica.y"
                {
                    System.out.println("Linea: " + Analizador_Lexico.cantLineas + " Declaracion CLASE " + val_peek(4).sval);
                    if (Tabla_Simbolos.getAtributos(val_peek(2).sval).isUso("INTERFAZ")){
                        Tabla_Simbolos.getAtributos(val_peek(4).sval).setUso("CLASE");
                        Tabla_Simbolos.getAtributos(val_peek(4).sval).setImplementa(val_peek(2).sval);
                    } else {
                        System.out.println("ERROR EN DECLARACION DE CLASE. Linea "+Analizador_Lexico.cantLineas+": "+val_peek(2).sval+" no es un INTERFACE");
                    }
                }
                break;
                case 43:
//#line 130 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DE CLASE. Linea: " + Analizador_Lexico.cantLineas + " falta el identificador de la interfaz");}
                break;
                case 44:
//#line 131 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DE CLASE. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 45:
//#line 132 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DE CLASE. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 47:
//#line 136 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DE CLASE. Linea: " + Analizador_Lexico.cantLineas + " no se puede definir una clase sin cuerpo");}
                break;
                case 52:
//#line 146 "gramatica.y"
                {
                    /*accion que ID sea una clase, poner que la clase hereda de ID,*/
                    /**/
                }
                break;
                case 53:
//#line 153 "gramatica.y"
                {
                    if (Tabla_Simbolos.getAtributos(val_peek(3).sval).isUso("CLASE")){
                        //ambito = val_peek(3).sval;
                    } else {
                        System.out.println("ERROR EN DECLARACION DISTRIBUIDA. Linea: " + Analizador_Lexico.cantLineas + " " + val_peek(3).sval + " no es una clase ");
                    }
                    System.out.println("Linea: " + Analizador_Lexico.cantLineas + " Declaracion DISTRIBUIDA para " + val_peek(3).sval);
                }
                break;
                case 54:
//#line 161 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DISTRIBUIDA. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ':'");}
                break;
                case 55:
//#line 162 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DISTRIBUIDA. Linea: " + Analizador_Lexico.cantLineas + " falta palabra reservada FOR");}
                break;
                case 56:
//#line 163 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DISTRIBUIDA. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 58:
//#line 167 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DISTRIBUIDA. Linea: " + Analizador_Lexico.cantLineas + " no se puede definir una declaracion distribuida sin cuerpo");}
                break;
                case 59:
//#line 171 "gramatica.y"
                {
                    System.out.println("Linea: " + Analizador_Lexico.cantLineas + " Declaracion INTERFAZ " + val_peek(2).sval);
                    Tabla_Simbolos.getAtributos(val_peek(2).sval).setUso("INTERFAZ");
                }
                break;
                case 60:
//#line 175 "gramatica.y"
                {System.out.println("ERROR EN INTERFAZ. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 62:
//#line 179 "gramatica.y"
                {System.out.println("ERROR EN INTERFAZ. Linea: " + Analizador_Lexico.cantLineas + " no se puede declarar una interfaz sin metodos");}
                break;
                case 65:
//#line 184 "gramatica.y"
                {System.out.println("ERROR EN METODO DE INTERFAZ. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 66:
//#line 185 "gramatica.y"
                {System.out.println("ERROR EN METODO DE INTERFAZ. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 67:
//#line 189 "gramatica.y"
                {
                    System.out.println("Linea: " + Analizador_Lexico.cantLineas + " Declaracion metodo VOID " + val_peek(4).sval);
                    Tabla_Simbolos.getAtributos(val_peek(4).sval).setUso("FUNCION");
                    Tabla_Simbolos.getAtributos(val_peek(1).sval).setUso("PARAMETRO");
                    Tabla_Simbolos.getAtributos(val_peek(1).sval).setTipo(val_peek(2).sval);
                }
                break;
                case 68:
//#line 196 "gramatica.y"
                {
                    System.out.println("Linea: " + Analizador_Lexico.cantLineas + " Declaracion metodo VOID " + val_peek(2).sval);
                    Tabla_Simbolos.getAtributos(val_peek(2).sval).setUso("FUNCION");
                }
                break;
                case 69:
//#line 200 "gramatica.y"
                {System.out.println("ERROR EN METODO DE INTERFAZ. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ')'");}
                break;
                case 70:
//#line 201 "gramatica.y"
                {System.out.println("ERROR EN METODO DE INTERFAZ. Linea: " + Analizador_Lexico.cantLineas + " se esperaba '('");}
                break;
                case 71:
//#line 202 "gramatica.y"
                {System.out.println("ERROR EN METODO DE INTERFAZ. Linea: " + Analizador_Lexico.cantLineas + " falta el tipo de " + val_peek(2).sval);}
                break;
                case 72:
//#line 205 "gramatica.y"
                {System.out.println("Linea: " + Analizador_Lexico.cantLineas + " ASIGNACION");}
                break;
                case 73:
//#line 206 "gramatica.y"
                {System.out.println("Linea: " + Analizador_Lexico.cantLineas + " INVOCACION FUNCION");}
                break;
                case 74:
//#line 207 "gramatica.y"
                {System.out.println("ERROR EN INVOCACION A LA FUNCION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 75:
//#line 208 "gramatica.y"
                {System.out.println("Linea: " + Analizador_Lexico.cantLineas + " Sentencia IF");}
                break;
                case 76:
//#line 209 "gramatica.y"
                {System.out.println("ERROR EN SENTENCIA IF. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 77:
//#line 210 "gramatica.y"
                {System.out.println("Linea: " + Analizador_Lexico.cantLineas + " SENTENCIA DE IMPRESION");}
                break;
                case 78:
//#line 211 "gramatica.y"
                {System.out.println("ERROR EN SENTENCIA DE IMPRESION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 79:
//#line 212 "gramatica.y"
                {System.out.println("Linea: " + Analizador_Lexico.cantLineas + " REFERENCIA A CLASE");}
                break;
                case 80:
//#line 213 "gramatica.y"
                {System.out.println("ERROR EN REFERENCIA A CLASE. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 82:
//#line 215 "gramatica.y"
                {System.out.println("ERROR EN SENTENCIA FOR. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 84:
//#line 217 "gramatica.y"
                {System.out.println("ERROR EN RETURN. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 89:
//#line 224 "gramatica.y"
                {System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " Se esperaba expresion");}
                break;
                case 90:
//#line 225 "gramatica.y"
                {System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " Se esperaba expresion");}
                break;
                case 91:
//#line 226 "gramatica.y"
                {System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " Se esperaba expresion");}
                break;
                case 92:
//#line 227 "gramatica.y"
                {System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " Se esperaba expresion");}
                break;
                case 93:
//#line 228 "gramatica.y"
                {System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 94:
//#line 229 "gramatica.y"
                {System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 95:
//#line 230 "gramatica.y"
                {System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 96:
//#line 231 "gramatica.y"
                {System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 97:
//#line 234 "gramatica.y"
                {System.out.println("Linea: " + Analizador_Lexico.cantLineas + " SUMA");}
                break;
                case 98:
//#line 235 "gramatica.y"
                {System.out.println("Linea: " + Analizador_Lexico.cantLineas + " RESTA");}
                break;
                case 100:
//#line 239 "gramatica.y"
                {System.out.println("Linea: " + Analizador_Lexico.cantLineas + " MULTIPLICACION");}
                break;
                case 101:
//#line 240 "gramatica.y"
                {System.out.println("Linea: " + Analizador_Lexico.cantLineas + " DIVISION");}
                break;
                case 105:
//#line 248 "gramatica.y"
                { chequeoRango(val_peek(0).sval);
                    Tabla_Simbolos.getAtributos(val_peek(0).sval).sumarUso();
                }
                break;
                case 106:
//#line 251 "gramatica.y"
                {
                    chequeoRango("-"+val_peek(0).sval);
                    if (Tabla_Simbolos.getAtributos(val_peek(0).sval).isCero()){
                        Tabla_Simbolos.modificarClave(val_peek(0).sval, "-"+val_peek(0).sval);
                    } else {
                        if (!Tabla_Simbolos.existeSimbolo("-"+val_peek(0).sval)){
                            Tabla_Simbolos.insertarSimbolo("-"+val_peek(0).sval,new AtributosLexema());
                        }
                    }
                    Tabla_Simbolos.getAtributos("-"+val_peek(0).sval).sumarUso();
                }
                break;
                case 111:
//#line 272 "gramatica.y"
                {System.out.println("ERROR EN SENTENCIA IF. Linea: " + Analizador_Lexico.cantLineas + " falta condicion");}
                break;
                case 121:
//#line 288 "gramatica.y"
                {System.out.println("ERROR EN SENTENCIA IF. Linea: " + Analizador_Lexico.cantLineas + " cuerpo de IF vacio");}
                break;
                case 123:
//#line 292 "gramatica.y"
                {System.out.println("ERROR EN SENTENCIA IF. Linea: " + Analizador_Lexico.cantLineas + " cuerpo de ELSE vacio");}
                break;
                case 125:
//#line 296 "gramatica.y"
                {System.out.println("ERROR EN SENTENCIA DE IMPRESION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba una cadena de caracteres");}
                break;
                case 126:
//#line 300 "gramatica.y"
                {/* chequear que ID1 sea una clase. Luego, si ID2 es un att o fun, chequear*/
                    /*que tenga como ambito ID1. Si ID2 es una CLASE, apilar Ambito*/
                }
                break;
                case 127:
//#line 304 "gramatica.y"
                {
                    /*Si ID es un att o fun, chequear que el ambito sea el tope de pila de ambito*/
                    /*Si ID es una CLASE, apilar Ambito*/
                }
                break;
                case 128:
//#line 310 "gramatica.y"
                {System.out.println("Linea: " + Analizador_Lexico.cantLineas + " Sentencia FOR");}
                break;
                case 129:
//#line 311 "gramatica.y"
                {System.out.println("ERROR EN SENTENCIA FOR. Linea: " + Analizador_Lexico.cantLineas + " se esperaba un identificador");}
                break;
                case 130:
//#line 312 "gramatica.y"
                {System.out.println("ERROR EN SENTENCIA FOR. Linea: " + Analizador_Lexico.cantLineas + " falta la palabra reservada IN");}
                break;
                case 131:
//#line 313 "gramatica.y"
                {System.out.println("ERROR EN SENTENCIA FOR. Linea: " + Analizador_Lexico.cantLineas + " falta la palabra reservada RANGE");}
                break;
                case 132:
//#line 314 "gramatica.y"
                {System.out.println("ERROR EN SENTENCIA FOR. Linea: " + Analizador_Lexico.cantLineas + " falta encabezado");}
                break;
                case 133:
//#line 315 "gramatica.y"
                {System.out.println("ERROR EN SENTENCIA FOR. Linea: " + Analizador_Lexico.cantLineas + " no se puede definir un FOR sin cuerpo");}
                break;
                case 134:
//#line 318 "gramatica.y"
                {System.out.println("Linea: " + Analizador_Lexico.cantLineas + " ENCABEZADO FOR");}
                break;
                case 135:
//#line 319 "gramatica.y"
                {System.out.println("ERROR EN ENCABEZADO FOR. Linea: " + Analizador_Lexico.cantLineas + " falta una constante");}
                break;
                case 136:
//#line 320 "gramatica.y"
                {System.out.println("ERROR EN ENCABEZADO FOR. Linea: " + Analizador_Lexico.cantLineas + " faltan constantes");}
                break;
//#line 1134 "Parser.java"
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



}
//################### END OF CLASS ##############################
