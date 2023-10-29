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
package AnalizadorSintactico;
import AnalizadorLexico.*;
import GeneracionCodigoIntermedio.Pila;
//#line 21 "Parser.java"




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
            7,   12,   12,   12,   13,   13,    8,    8,   14,   14,
            14,   14,   14,   15,   15,   16,   16,   17,   17,   17,
            9,    9,   18,   18,   18,   19,   19,   20,   20,   21,
            21,   21,   21,   10,   10,   10,   10,   22,   22,   11,
            11,   23,   23,   24,   24,   24,   24,    4,    4,    4,
            4,    4,    4,    4,    4,    4,    4,    4,    4,    4,
            4,    4,   25,   25,   25,   25,   25,   25,   25,   25,
            25,   25,   25,   25,   30,   30,   30,   32,   32,   32,
            33,   33,   34,   34,   26,   26,   27,   35,   35,   37,
            37,   37,   37,   37,   37,   36,   36,   38,   38,   39,
            39,   28,   28,   29,   29,   31,   31,   31,   31,   31,
            31,   40,   40,   40,   41,
    };
    final static short yylen[] = {                            2,
            3,    2,    2,    2,    1,    1,    1,    2,    2,    1,
            2,    1,    1,    1,    1,    1,    1,    3,    3,    3,
            3,    1,    1,    1,    1,    3,    3,    3,    4,    6,
            5,    5,    6,    3,    3,    2,    1,    1,    1,    1,
            3,    3,    2,    4,    4,    3,    3,    2,    1,    1,
            1,    2,    2,    6,    6,    6,    6,    3,    3,    4,
            4,    3,    3,    2,    3,    1,    2,    1,    2,    2,
            2,    2,    2,    2,    5,    5,    4,    4,    2,    2,
            2,    2,    4,    4,    4,    4,    3,    4,    3,    4,
            4,    4,    4,    4,    3,    3,    1,    3,    3,    1,
            1,    1,    1,    2,    4,    3,    4,    3,    3,    3,
            3,    3,    3,    3,    3,    2,    1,    3,    3,    4,
            4,    2,    2,    3,    3,    6,    6,    5,    5,    6,
            6,    7,    6,    4,    3,
    };
    final static short yydefred[] = {                         0,
            0,    0,    0,    0,    0,    0,   22,   23,   24,    0,
            0,    0,    0,    0,    0,    0,    5,    6,    7,   13,
            14,   15,   16,   17,    0,    0,    0,   68,    0,    0,
            0,    0,    0,    8,   25,    0,    0,    0,    0,    0,
            0,    0,    0,  123,  122,    0,    0,    0,    0,    0,
            0,    0,   82,   81,    0,    3,    4,    0,    0,    0,
            0,    0,   70,   69,   72,   71,   74,   73,    0,    0,
            0,    0,    0,   80,   79,  101,  103,    0,    0,    0,
            0,  100,  102,   87,  106,    0,    0,  124,   21,   19,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    1,   20,   18,
            0,   40,   38,   39,    0,   37,   28,   27,    0,    0,
            50,   51,    0,    0,   49,   42,   41,    0,    0,   89,
            0,    0,    0,  125,   88,  104,   92,   84,    0,    0,
            0,    0,  105,   91,   83,   26,  109,    0,    0,    0,
            0,    0,    0,  108,    0,    0,   12,    0,  107,    0,
            116,   45,   44,    0,   29,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,   61,
            60,   35,   34,   36,   52,   47,   53,   46,   48,   90,
            94,   86,   78,   77,    0,   93,   85,    0,    0,   98,
            99,    0,    0,    0,    0,    0,    0,  119,  118,   11,
            0,    0,    0,   32,    0,    0,    0,    0,  129,  128,
            0,    0,    0,    0,    0,    0,   63,   64,   62,    0,
            76,   75,    0,    0,   33,   30,    0,  131,  126,  130,
            0,    0,  127,   59,   58,   56,   57,   54,   55,   65,
            121,  120,  135,    0,  134,    0,    0,    0,  133,  132,
    };
    final static short yydgoto[] = {                         15,
            16,   17,   18,   19,    0,  158,   20,   21,   22,   23,
            24,   25,   41,   26,   60,  115,  116,   27,   62,  124,
            125,  174,  107,  179,   28,   29,   30,   31,   32,   80,
            33,   81,   82,   83,   43,   96,   94,   97,  161,  170,
            217,
    };
    final static short yysindex[] = {                      -103,
            54,  -12,   78, -130, -132, -126,    0,    0,    0, -162,
            -158, -120,  -22,  151,    0,  -80,    0,    0,    0,    0,
            0,    0,    0,    0,  -99,   52,   67,    0,   35,   36,
            37,   -4,   38,    0,    0,    8,  -48,    5,  -34,  -58,
            -21,   10,   93,    0,    0,  -56,  -10, -123,  -52,  163,
            -35,  103,    0,    0,   49,    0,    0,   34,  -63,   39,
            130,   40,    0,    0,    0,    0,    0,    0,   20,   24,
            18,  -34,    4,    0,    0,    0,    0,   25,   28,   23,
            59,    0,    0,    0,    0,   95,   26,    0,    0,    0,
            27,   31,  -27,  188,   84,   21,   43, -107,    3,   51,
            -39,  249,  249,  174,   33,  -98,   42,    0,    0,    0,
            69,    0,    0,    0,   71,    0,    0,    0,  -29,   70,
            0,    0,   48,  134,    0,    0,    0,   73,   29,    0,
            44,  100,   32,    0,    0,    0,    0,    0,  -34,  -34,
            -34,  -34,    0,    0,    0,    0,    0,  -34,  -34,  -34,
            -34,  -34,  -34,    0,   -3,   76,    0,   85,    0,  176,
            0,    0,    0,  262,    0,   53,  278,    7,  -28,  197,
            197,  197,  -86,  279,  174,  283,   79,  299,  -64,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,   45,    0,    0,   59,   59,    0,
            0,   64,   64,   64,   64,   64,   64,    0,    0,    0,
            92,   94,  310,    0, -136,  -75,  101,   61,    0,    0,
            102,  114,  231,  118,   46,  122,    0,    0,    0,  318,
            0,    0,  123,  106,    0,    0,  108,    0,    0,    0,
            -28,  124,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,   63,    0,  -28,  126,  323,    0,    0,
    };
    final static short yyrindex[] = {                         0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,  261,    0,    0,    0,    0,
            0,    0,    0,    0,  385,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            -41,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,  127,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,  -25,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,  -36,  -31,    0,
            0,  345,  347,  349,  351,  352,  353,    0,    0,    0,
            0,    0,  -38,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    9,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    };
    final static short yygindex[] = {                         0,
            390,   80,    0,   96,    0,  -83,   83,   -5,    0,    0,
            0,   47,  384,   -1,    0,    0,  295,    0,    0,    0,
            288,   -2,    0,    0,    0,    0,    0,    0,    0,  334,
            0,   12,   72, -144,    0,    0,    0,    0,    0,   14,
            -57,
    };
    final static int YYTABLESIZE=487;
    static short yytable[];
    static { yytable();}
    static void yytable(){
        yytable = new short[]{                         97,
                169,   97,   97,   97,   95,   31,   95,   95,   95,   96,
                79,   96,   96,   96,  185,  139,   79,  140,   97,   14,
                97,   54,   90,   95,  218,   95,  177,   38,   96,   99,
                96,   37,  152,   40,  153,   71,   38,   91,  222,   70,
                37,   73,   40,  165,   56,   85,  169,  215,   39,   79,
                92,   78,   79,  114,   79,  122,   72,   39,  131,  123,
                229,  111,   79,  128,   79,  139,  138,  140,  139,  145,
                140,  139,  192,  140,  139,  197,  140,  110,   64,   66,
                68,   75,  118,  127,   31,  181,   31,  194,  232,  248,
                175,  187,   91,  100,   48,   57,  254,   34,   50,   66,
                141,  242,  176,  257,  178,  142,  139,   49,  140,  114,
                51,  258,  219,  220,  221,  171,  172,   42,  122,  241,
                155,  256,  123,    3,   46,   44,    4,  234,   45,  215,
                47,  237,   10,   67,   57,  143,   52,  139,   13,  140,
                195,  113,  139,  121,  140,  166,  101,  102,  162,  163,
                198,  199,    1,    2,  112,  173,    3,   35,  239,    4,
                5,    6,    7,    8,    9,   10,    6,  223,   11,   12,
                59,   13,  225,  108,   59,    1,    2,  230,    6,    3,
                238,  216,    4,    5,    6,    7,    8,    9,   10,   61,
                157,   11,   12,    2,   13,  183,    3,  113,   88,    4,
                6,    6,    7,    8,    9,   10,  121,   84,  156,  209,
                112,   13,  200,  201,   97,   95,  233,   98,  103,   95,
                104,  105,   76,   77,   96,  106,   31,   35,  154,   77,
                252,  168,  253,   53,   89,   97,   97,   97,   97,   66,
                95,   95,   95,   95,   35,   96,   96,   96,   96,  148,
                149,  150,  151,  210,  120,    7,    8,    9,  188,  164,
                134,   76,   77,   36,   76,   77,   76,   77,    7,    8,
                9,   69,   36,   67,   76,   77,   76,   77,  137,  130,
                135,  144,  159,  146,  191,  136,  147,  196,  169,  109,
                63,   65,   67,   74,  117,  126,  173,  180,  211,  193,
                231,  247,  212,  160,    1,    2,  157,  167,    3,  213,
                157,    4,    5,    6,    7,    8,    9,   10,  214,  215,
                11,   12,  224,   13,  182,  186,  226,    2,  190,  210,
                3,  208,  210,    4,  227,    6,    7,    8,    9,   10,
                155,  155,  228,    3,    3,   13,    4,    4,  155,  235,
                236,    3,   10,   10,    4,  245,  240,  243,   13,   13,
                10,  250,  155,  260,  155,    3,   13,    3,    4,  244,
                4,   86,   87,  246,   10,   93,   10,  249,  251,  255,
                13,  259,   13,   43,    2,  110,  119,  111,  117,  114,
                119,  115,  112,  113,    6,    7,    8,    9,    6,    7,
                8,    9,  129,   55,  132,  133,    1,    2,   58,  184,
                3,  189,    0,    4,    5,    6,    7,    8,    9,   10,
                0,    0,   11,   12,    0,   13,    0,    0,    0,    0,
                0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
                0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
                0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
                0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
                0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
                0,  202,  203,  204,  205,  206,  207,
        };
    }
    static short yycheck[];
    static { yycheck(); }
    static void yycheck() {
        yycheck = new short[] {                         41,
                40,   43,   44,   45,   41,   44,   43,   44,   45,   41,
                45,   43,   44,   45,   44,   43,   45,   45,   60,  123,
                62,   44,   44,   60,  169,   62,  125,   40,   60,   40,
                62,   44,   60,   46,   62,   40,   40,   59,  125,   44,
                44,   46,   46,   41,  125,   41,   40,  123,   61,   45,
                41,   44,   45,   59,   45,   61,   61,   61,   41,   61,
                125,  125,   45,   44,   45,   43,   44,   45,   43,   44,
                45,   43,   44,   45,   43,   44,   45,   44,   44,   44,
                44,   44,   44,   44,  123,   44,  125,   44,   44,   44,
                58,   44,   59,   47,  257,   16,  241,   44,  257,  125,
                42,   41,  105,   41,  106,   47,   43,  270,   45,  115,
                269,  256,  170,  171,  172,  102,  103,   40,  124,   59,
                257,   59,  124,  260,  257,  256,  263,  211,  259,  123,
                257,  215,  269,  125,   55,   41,  257,   43,  275,   45,
                41,   59,   43,   61,   45,   99,  270,  271,  256,  257,
                139,  140,  256,  257,   59,  123,  260,  257,  216,  263,
                264,  265,  266,  267,  268,  269,  265,  173,  272,  273,
                123,  275,  175,  125,  123,  256,  257,  179,  265,  260,
                256,  168,  263,  264,  265,  266,  267,  268,  269,  123,
                95,  272,  273,  257,  275,  125,  260,  115,  257,  263,
                265,  265,  266,  267,  268,  269,  124,  256,  125,  125,
                115,  275,  141,  142,  256,  123,  125,  274,  271,  256,
                58,  257,  257,  258,  256,  123,  265,  257,   41,  258,
                125,  271,  125,  256,  256,  277,  278,  279,  280,  265,
                277,  278,  279,  280,  257,  277,  278,  279,  280,  277,
                278,  279,  280,  158,  125,  266,  267,  268,  125,  257,
                257,  257,  258,  276,  257,  258,  257,  258,  266,  267,
                268,  276,  276,  265,  257,  258,  257,  258,  256,  256,
                256,  256,  262,  257,  256,  258,  256,  256,   40,  256,
                256,  256,  256,  256,  256,  256,  123,  256,  123,  256,
                256,  256,   41,  261,  256,  257,  211,  257,  260,  257,
                215,  263,  264,  265,  266,  267,  268,  269,   41,  123,
                272,  273,   44,  275,  256,  256,   44,  257,  256,  234,
                260,  256,  237,  263,  256,  265,  266,  267,  268,  269,
                257,  257,   44,  260,  260,  275,  263,  263,  257,  256,
                41,  260,  269,  269,  263,  125,  256,  256,  275,  275,
                269,   44,  257,   41,  257,  260,  275,  260,  263,  256,
                263,   38,   39,  256,  269,   42,  269,  256,  256,  256,
                275,  256,  275,  123,    0,   41,  257,   41,  262,   41,
                257,   41,   41,   41,  265,  266,  267,  268,  265,  266,
                267,  268,   69,   14,   71,   72,  256,  257,   25,  115,
                260,  124,   -1,  263,  264,  265,  266,  267,  268,  269,
                -1,   -1,  272,  273,   -1,  275,   -1,   -1,   -1,   -1,
                -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
                -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
                -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
                -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
                -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
                -1,  148,  149,  150,  151,  152,  153,
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
            "declaracion_funcion : encabezado_funcion cuerpo_funcion ','",
            "declaracion_funcion : encabezado_funcion cuerpo_funcion error",
            "encabezado_funcion : VOID ID '(' ')'",
            "encabezado_funcion : VOID ID '(' tipo ID ')'",
            "encabezado_funcion : VOID ID '(' tipo ID",
            "encabezado_funcion : VOID ID tipo ID ')'",
            "encabezado_funcion : VOID ID '(' ID ')' error",
            "cuerpo_funcion : '{' bloque_funcion '}'",
            "cuerpo_funcion : '{' '}' error",
            "bloque_funcion : bloque_funcion sentencia_funcion",
            "bloque_funcion : sentencia_funcion",
            "sentencia_funcion : declaracion_variables",
            "sentencia_funcion : declaracion_funcion",
            "sentencia_funcion : sentencia_ejecutable",
            "declaracion_clase : encabezado_clase cuerpo_clase ','",
            "declaracion_clase : encabezado_clase cuerpo_clase error",
            "encabezado_clase : CLASS ID",
            "encabezado_clase : CLASS ID IMPLEMENT ID",
            "encabezado_clase : CLASS ID IMPLEMENT error",
            "cuerpo_clase : '{' bloque_clase '}'",
            "cuerpo_clase : '{' '}' error",
            "bloque_clase : bloque_clase sentencia_clase",
            "bloque_clase : sentencia_clase",
            "sentencia_clase : declaracion_variables",
            "sentencia_clase : declaracion_funcion",
            "sentencia_clase : ID ','",
            "sentencia_clase : encabezado_funcion ','",
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
            "metodos_interfaz : encabezado_funcion ','",
            "metodos_interfaz : metodos_interfaz encabezado_funcion ','",
            "metodos_interfaz : encabezado_funcion",
            "metodos_interfaz : metodos_interfaz encabezado_funcion",
            "sentencia_ejecutable : asignacion",
            "sentencia_ejecutable : invocacion_funcion ','",
            "sentencia_ejecutable : invocacion_funcion error",
            "sentencia_ejecutable : seleccion ','",
            "sentencia_ejecutable : seleccion error",
            "sentencia_ejecutable : imprimir ','",
            "sentencia_ejecutable : imprimir error",
            "sentencia_ejecutable : ref_clase '(' expresion ')' ','",
            "sentencia_ejecutable : ref_clase '(' expresion ')' error",
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

    //#line 332 "gramatica.y"
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
        String delimitador = "#";
        for(Object o: pilaAmbito.getElements()){
            nuevoAmb = nuevoAmb.concat(delimitador);
            nuevoAmb = nuevoAmb.concat((String)o);
        }
        Tabla_Simbolos.modificarClave(lexema, nuevoAmb);
    }

    public int yylex(){
        int tok = Analizador_Lexico.yylex();
        yylval = new ParserVal(Analizador_Lexico.buffer);
        return tok;
    }
    public void yyerror(String s){}
    //#line 574 "Parser.java"
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
//#line 14 "gramatica.y"
                {System.out.println("ERROR EN PROGRAMA. Linea: " + Analizador_Lexico.cantLineas + " se esperaba '}'");}
                break;
                case 3:
//#line 15 "gramatica.y"
                {System.out.println("ERROR EN PROGRAMA. Linea: " + Analizador_Lexico.cantLineas + " se esperaba '{'");}
                break;
                case 8:
//#line 24 "gramatica.y"
                {System.out.println("ERROR EN SENTENCIA. Linea: " + Analizador_Lexico.cantLineas);}
                break;
                case 18:
//#line 43 "gramatica.y"
                {
                    System.out.println("Linea: " + Analizador_Lexico.cantLineas + " Declaracion de VARIABLE/S de TIPO " + val_peek(2).sval);
                }
                break;
                case 19:
//#line 47 "gramatica.y"
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
//#line 55 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DE VARIABLES. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 21:
//#line 56 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DE VARIABLES. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 22:
//#line 59 "gramatica.y"
                {tipo = val_peek(0).sval;}
                break;
                case 23:
//#line 60 "gramatica.y"
                {tipo = val_peek(0).sval;}
                break;
                case 24:
//#line 61 "gramatica.y"
                {tipo = val_peek(0).sval;}
                break;
                case 25:
//#line 65 "gramatica.y"
                {
                    /*poner ambito a ID*/
                    Tabla_Simbolos.getAtributos(val_peek(0).sval).setTipo(tipo);
                    Tabla_Simbolos.getAtributos(val_peek(0).sval).setUso("VARIABLE");
                }
                break;
                case 26:
//#line 71 "gramatica.y"
                {
                    /*poner ambito a ID*/
                    Tabla_Simbolos.getAtributos(val_peek(0).sval).setTipo(tipo);
                    Tabla_Simbolos.getAtributos(val_peek(0).sval).setUso("VARIABLE");
                }
                break;
                case 28:
//#line 79 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DE FUNCION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 29:
//#line 82 "gramatica.y"
                {
                    /*poner ambito a IDfuncion, apilar nuevo ambito*/
                    System.out.println("Linea: " + Analizador_Lexico.cantLineas + " Declaracion funcion VOID " + val_peek(2).sval);
                    Tabla_Simbolos.getAtributos(val_peek(2).sval).setUso("FUNCION");
                    setAmbito(val_peek(2).sval);
                    pilaAmbito.insertar(val_peek(2).sval);
                    System.out.println(pilaAmbito.toString());
                }
                break;
                case 30:
//#line 91 "gramatica.y"
                {
                    System.out.println("Linea: " + Analizador_Lexico.cantLineas + " Declaracion funcion VOID " + val_peek(4).sval);
                    Tabla_Simbolos.getAtributos(val_peek(4).sval).setUso("FUNCION");
                    Tabla_Simbolos.getAtributos(val_peek(1).sval).setUso("PARAMETRO");
                    Tabla_Simbolos.getAtributos(val_peek(1).sval).setTipo(val_peek(2).sval);
                    setAmbito(val_peek(4).sval);
                    pilaAmbito.insertar(val_peek(4).sval);
                    setAmbito(val_peek(1).sval);
                }
                break;
                case 31:
//#line 100 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DE FUNCION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ')'");}
                break;
                case 32:
//#line 101 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DE FUNCION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba '('");}
                break;
                case 33:
//#line 102 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DE FUNCION. Linea: " + Analizador_Lexico.cantLineas + " falta el tipo de " + val_peek(2).sval);}
                break;
                case 34:
//#line 106 "gramatica.y"
                {pilaAmbito.eliminarTope();
                }
                break;
                case 35:
//#line 107 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DE FUNCION. Linea: " + Analizador_Lexico.cantLineas + " no se puede declarar una funcion sin cuerpo");}
                break;
                case 42:
//#line 120 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DE CLASE. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 43:
//#line 124 "gramatica.y"
                {
                    System.out.println("Linea: " + Analizador_Lexico.cantLineas + " Declaracion CLASE " + val_peek(0).sval);
                    Tabla_Simbolos.getAtributos(val_peek(0).sval).setUso("CLASE");

                }
                break;
                case 44:
//#line 130 "gramatica.y"
                {
                    System.out.println("Linea: " + Analizador_Lexico.cantLineas + " Declaracion CLASE " + val_peek(2).sval);
                    if (Tabla_Simbolos.getAtributos(val_peek(0).sval).isUso("INTERFAZ")){
                        Tabla_Simbolos.getAtributos(val_peek(2).sval).setUso("CLASE");
                        Tabla_Simbolos.getAtributos(val_peek(2).sval).setImplementa(val_peek(0).sval);
                    } else {
                        System.out.println("ERROR EN DECLARACION DE CLASE. Linea "+Analizador_Lexico.cantLineas+": "+val_peek(0).sval+" no es un INTERFACE");
                    }
                }
                break;
                case 45:
//#line 139 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DE CLASE. Linea: " + Analizador_Lexico.cantLineas + " falta el identificador de la interfaz");}
                break;
                case 47:
//#line 145 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DE CLASE. Linea: " + Analizador_Lexico.cantLineas + " no se puede definir una clase sin cuerpo");}
                break;
                case 52:
//#line 155 "gramatica.y"
                {
                    /*accion que ID sea una clase, poner que la clase hereda de ID,*/
                    /**/
                }
                break;
                case 53:
//#line 160 "gramatica.y"
                { /*hay que ponerle un bool que diga si esta implementado o no*/
                }
                break;
                case 54:
//#line 165 "gramatica.y"
                {
                    if (Tabla_Simbolos.getAtributos(val_peek(3).sval).isUso("CLASE")){
                        /*ambito = $3.sval;*/
                    } else {
                        System.out.println("ERROR EN DECLARACION DISTRIBUIDA. Linea: " + Analizador_Lexico.cantLineas + " " + val_peek(3).sval + " no es una clase ");
                    }
                    System.out.println("Linea: " + Analizador_Lexico.cantLineas + " Declaracion DISTRIBUIDA para " + val_peek(3).sval);
                }
                break;
                case 55:
//#line 173 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DISTRIBUIDA. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ':'");}
                break;
                case 56:
//#line 174 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DISTRIBUIDA. Linea: " + Analizador_Lexico.cantLineas + " falta palabra reservada FOR");}
                break;
                case 57:
//#line 175 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DISTRIBUIDA. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 59:
//#line 179 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DISTRIBUIDA. Linea: " + Analizador_Lexico.cantLineas + " no se puede definir una declaracion distribuida sin cuerpo");}
                break;
                case 60:
//#line 183 "gramatica.y"
                {
                    System.out.println("Linea: " + Analizador_Lexico.cantLineas + " Declaracion INTERFAZ " + val_peek(2).sval);
                    Tabla_Simbolos.getAtributos(val_peek(2).sval).setUso("INTERFAZ");
                }
                break;
                case 61:
//#line 187 "gramatica.y"
                {System.out.println("ERROR EN INTERFAZ. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 63:
//#line 191 "gramatica.y"
                {System.out.println("ERROR EN INTERFAZ. Linea: " + Analizador_Lexico.cantLineas + " no se puede declarar una interfaz sin metodos");}
                break;
                case 66:
//#line 196 "gramatica.y"
                {System.out.println("ERROR EN METODO DE INTERFAZ. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 67:
//#line 197 "gramatica.y"
                {System.out.println("ERROR EN METODO DE INTERFAZ. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 68:
//#line 200 "gramatica.y"
                {System.out.println("Linea: " + Analizador_Lexico.cantLineas + " ASIGNACION");}
                break;
                case 69:
//#line 201 "gramatica.y"
                {System.out.println("Linea: " + Analizador_Lexico.cantLineas + " INVOCACION FUNCION");}
                break;
                case 70:
//#line 202 "gramatica.y"
                {System.out.println("ERROR EN INVOCACION A LA FUNCION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 71:
//#line 203 "gramatica.y"
                {System.out.println("Linea: " + Analizador_Lexico.cantLineas + " Sentencia IF");}
                break;
                case 72:
//#line 204 "gramatica.y"
                {System.out.println("ERROR EN SENTENCIA IF. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 73:
//#line 205 "gramatica.y"
                {System.out.println("Linea: " + Analizador_Lexico.cantLineas + " SENTENCIA DE IMPRESION");}
                break;
                case 74:
//#line 206 "gramatica.y"
                {System.out.println("ERROR EN SENTENCIA DE IMPRESION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 75:
//#line 208 "gramatica.y"
                {
                    System.out.println("Linea: " + Analizador_Lexico.cantLineas + " REFERENCIA A CLASE");
                    /*chequear que coincida con lo declarado en el metodo, sea tipo o cantidad*/
                }
                break;
                case 76:
//#line 212 "gramatica.y"
                {System.out.println("ERROR EN REFERENCIA A CLASE. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 77:
//#line 214 "gramatica.y"
                {
                    System.out.println("Linea: " + Analizador_Lexico.cantLineas + " REFERENCIA A CLASE");
                    /*chequear que coincida en cantidad de parametros*/
                }
                break;
                case 78:
//#line 218 "gramatica.y"
                {System.out.println("ERROR EN REFERENCIA A CLASE. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 80:
//#line 220 "gramatica.y"
                {System.out.println("ERROR EN SENTENCIA FOR. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 82:
//#line 222 "gramatica.y"
                {System.out.println("ERROR EN RETURN. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 87:
//#line 229 "gramatica.y"
                {System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " Se esperaba expresion");}
                break;
                case 88:
//#line 230 "gramatica.y"
                {System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " Se esperaba expresion");}
                break;
                case 89:
//#line 231 "gramatica.y"
                {System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " Se esperaba expresion");}
                break;
                case 90:
//#line 232 "gramatica.y"
                {System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " Se esperaba expresion");}
                break;
                case 91:
//#line 233 "gramatica.y"
                {System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 92:
//#line 234 "gramatica.y"
                {System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 93:
//#line 235 "gramatica.y"
                {System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 94:
//#line 236 "gramatica.y"
                {System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 95:
//#line 239 "gramatica.y"
                {System.out.println("Linea: " + Analizador_Lexico.cantLineas + " SUMA");}
                break;
                case 96:
//#line 240 "gramatica.y"
                {System.out.println("Linea: " + Analizador_Lexico.cantLineas + " RESTA");}
                break;
                case 98:
//#line 244 "gramatica.y"
                {System.out.println("Linea: " + Analizador_Lexico.cantLineas + " MULTIPLICACION");}
                break;
                case 99:
//#line 245 "gramatica.y"
                {System.out.println("Linea: " + Analizador_Lexico.cantLineas + " DIVISION");}
                break;
                case 103:
//#line 253 "gramatica.y"
                { chequeoRango(val_peek(0).sval);
                    Tabla_Simbolos.getAtributos(val_peek(0).sval).sumarUso();
                }
                break;
                case 104:
//#line 256 "gramatica.y"
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
                case 109:
//#line 277 "gramatica.y"
                {System.out.println("ERROR EN SENTENCIA IF. Linea: " + Analizador_Lexico.cantLineas + " falta condicion");}
                break;
                case 119:
//#line 293 "gramatica.y"
                {System.out.println("ERROR EN SENTENCIA IF. Linea: " + Analizador_Lexico.cantLineas + " cuerpo de IF vacio");}
                break;
                case 121:
//#line 297 "gramatica.y"
                {System.out.println("ERROR EN SENTENCIA IF. Linea: " + Analizador_Lexico.cantLineas + " cuerpo de ELSE vacio");}
                break;
                case 123:
//#line 301 "gramatica.y"
                {System.out.println("ERROR EN SENTENCIA DE IMPRESION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba una cadena de caracteres");}
                break;
                case 124:
//#line 305 "gramatica.y"
                {/* chequear que ID1 sea una clase. Luego, si ID2 es un att o fun, chequear*/
                    /*que tenga como ambito ID1. Si ID2 es una CLASE, apilar Ambito*/
                }
                break;
                case 125:
//#line 309 "gramatica.y"
                {
                    /*Si ID es un att o fun, chequear que el ambito sea el tope de pila de ambito*/
                    /*Si ID es una CLASE, apilar Ambito*/
                }
                break;
                case 126:
//#line 315 "gramatica.y"
                {System.out.println("Linea: " + Analizador_Lexico.cantLineas + " Sentencia FOR");}
                break;
                case 127:
//#line 316 "gramatica.y"
                {System.out.println("ERROR EN SENTENCIA FOR. Linea: " + Analizador_Lexico.cantLineas + " se esperaba un identificador");}
                break;
                case 128:
//#line 317 "gramatica.y"
                {System.out.println("ERROR EN SENTENCIA FOR. Linea: " + Analizador_Lexico.cantLineas + " falta la palabra reservada IN");}
                break;
                case 129:
//#line 318 "gramatica.y"
                {System.out.println("ERROR EN SENTENCIA FOR. Linea: " + Analizador_Lexico.cantLineas + " falta la palabra reservada RANGE");}
                break;
                case 130:
//#line 319 "gramatica.y"
                {System.out.println("ERROR EN SENTENCIA FOR. Linea: " + Analizador_Lexico.cantLineas + " falta encabezado");}
                break;
                case 131:
//#line 320 "gramatica.y"
                {System.out.println("ERROR EN SENTENCIA FOR. Linea: " + Analizador_Lexico.cantLineas + " no se puede definir un FOR sin cuerpo");}
                break;
                case 132:
//#line 323 "gramatica.y"
                {System.out.println("Linea: " + Analizador_Lexico.cantLineas + " ENCABEZADO FOR");}
                break;
                case 133:
//#line 324 "gramatica.y"
                {System.out.println("ERROR EN ENCABEZADO FOR. Linea: " + Analizador_Lexico.cantLineas + " falta una constante");}
                break;
                case 134:
//#line 325 "gramatica.y"
                {System.out.println("ERROR EN ENCABEZADO FOR. Linea: " + Analizador_Lexico.cantLineas + " faltan constantes");}
                break;
//#line 1120 "Parser.java"
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
