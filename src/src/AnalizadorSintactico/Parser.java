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
            0,    0,    0,    1,    1,    1,    2,    2,    2,    6,
            6,    6,    6,    3,    3,    3,    3,    3,    3,   10,
            10,   10,   10,   10,   11,   11,   11,   11,   12,   12,
            13,   13,   13,   13,   14,   14,   14,   14,   14,    9,
            9,    9,    9,    9,    9,    9,    7,    7,    7,    8,
            8,    4,    4,    4,    4,    4,    4,   19,   19,   15,
            15,   15,   15,   15,   15,   15,   15,   15,   15,   20,
            20,   20,   21,   21,   21,   22,   22,   22,   16,   16,
            17,   17,   17,   17,   17,   23,   23,   23,   23,   23,
            23,   23,   24,   24,   18,   18,    5,    5,    5,    5,
            5,    5,    5,    5,
    };
    final static short yylen[] = {                            2,
            3,    2,    2,    3,    2,    1,    1,    1,    1,    1,
            4,    4,    4,    2,    2,    1,    1,    1,    1,    5,
            7,    5,    7,    2,    7,    6,    7,    7,    5,    5,
            2,    3,    1,    2,    6,    4,    5,    5,    6,    7,
            9,    8,    8,    9,    9,    7,    1,    1,    1,    1,
            3,    1,    1,    1,    1,    3,    1,    3,    3,    4,
            4,    4,    4,    3,    3,    3,    3,    3,    3,    3,
            3,    1,    3,    3,    1,    1,    1,    2,    4,    3,
            8,    6,    5,    7,    5,    3,    3,    3,    3,    3,
            3,    2,    2,    5,    2,    2,   10,    9,    8,    7,
            10,    9,    9,   10,
    };
    final static short yydefred[] = {                         0,
            0,    0,    0,    0,    0,   47,   48,   49,    0,    0,
            0,   57,    0,    0,    0,    0,    7,    8,    9,    0,
            16,   17,   18,   19,   52,   53,   54,   55,    0,    0,
            77,    0,    0,    0,    0,    0,    0,    0,    0,   75,
            0,   96,   95,   24,    0,    0,    0,    0,    0,    0,
            0,    0,    3,    0,    5,   50,    0,   76,    0,    0,
            0,    0,    0,   68,    0,   66,    0,   78,   80,    0,
            58,    0,   64,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            1,    4,   69,    0,   67,    0,   56,   59,   65,   61,
            60,   79,   51,    0,    0,   73,   74,    0,    0,    0,
            0,   92,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,   10,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            63,   62,    0,   93,    0,   83,    0,    0,    0,    0,
            0,    0,   85,    0,    0,    0,   22,   20,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,   30,   29,    0,   31,    0,    0,    0,   82,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,   26,    0,    0,    0,    0,
            32,    0,   84,    0,   23,   21,   12,    0,   13,    0,
            46,   40,    0,    0,    0,    0,    0,  100,    0,    0,
            0,   28,   27,   25,    0,   36,    0,    0,   94,   81,
            0,   42,    0,    0,   43,    0,    0,   99,    0,    0,
            0,    0,    0,   38,   44,   45,   41,    0,   98,  103,
            102,    0,   39,   35,  104,   97,  101,
    };
    final static short yydgoto[] = {                         14,
            15,   16,   17,   18,   19,  125,   20,   37,   21,   22,
            23,   24,  139,  140,   25,   26,   27,   28,   29,   38,
            39,   40,   80,  111,
    };
    final static short yysindex[] = {                       -96,
            50,   18, -167, -151, -219,    0,    0,    0, -194, -188,
            -192,    0,  131,    0,  166,   28,    0,    0,    0, -175,
            0,    0,    0,    0,    0,    0,    0,    0,   53,    0,
            0,   97,  100, -170,   42, -163,   51,    7,   37,    0,
            83,    0,    0,    0,  -92,  -34, -158, -146,   69, -128,
            12,  180,    0,  109,    0,    0,   51,    0,  103,  106,
            122, -116,   21,    0,   89,    0,   94,    0,    0,   75,
            0, -102,    0,    2,    2,    2,    2,  -70,  -32,  124,
            -91,  331,   68,  -61,  -17,  168,  169,   88,  -38, -103,
            0,    0,    0,  114,    0,  157,    0,    0,    0,    0,
            0,    0,    0,   37,   37,    0,    0,   62,  -70,  170,
            -140,    0,    2,    2,    2,    2,    2,    2,  -53,   93,
            -175,  117,  -44,    0,  317,  176,   95,  -29,  178,  181,
            -35,  -33,  -28,  -39,  -39,  139,  -26,   47,  -85,  284,
            0,    0, -189,    0,  -70,    0,   35,   35,   35,   35,
            35,   35,    0, -118,  357,  144,    0,    0,  212,  216,
            202,   -4,  222,  -31,   90,   91,  115,  226,  241,  -84,
            64,    0,    0,  323,    0,  326,  116,  -70,    0,  120,
            370,  -64,  121,  131,  130,  221,  131,  257,  131,   -9,
            -70,  134,  147,  155,  146,    0,  158,  290,   76,  161,
            0,  294,    0,  160,    0,    0,    0,  121,    0,  240,
            0,    0,  254,  268,  282,   -2,  -70,    0,  379,  383,
            384,    0,    0,    0,  386,    0,  171,  395,    0,    0,
            194,    0,  195,  296,    0,  416,  -70,    0,  -70,  -70,
            -70,  204,  417,    0,    0,    0,    0,  112,    0,    0,
            0,  205,    0,    0,    0,    0,    0,
    };
    final static short yyrindex[] = {                         0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,   49,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    1,
            0,    0,    0,    0,    0,    0,   15,    0,  -41,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,  463,    0,    0,    0,    0,   29,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,  -36,  -27,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,  -74,
            0,    0,    0,    0,    0,    0,  423,  431,  432,  435,
            438,  442,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,  -71,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,  391,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,  -10,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,
    };
    final static short yygindex[] = {                         0,
            -1,  137,  -25,  -42,    0,  -80,  -21,  471,  -79,    0,
            0,    0,    0,  353,    0,    0,    0,    0,    0,  612,
            72,  108,    0,  461,
    };
    final static int YYTABLESIZE=730;
    static short yytable[];
    static { yytable();}
    static void yytable(){
        yytable = new short[]{                         72,
                50,   72,   72,   72,   70,   83,   70,   70,   70,  191,
                74,   52,   75,   71,   15,   71,   71,   71,   72,  136,
                72,  138,  131,   70,   84,   70,   13,  117,   14,  118,
                82,  217,   71,   37,   71,  110,  188,   46,  237,  173,
                197,  156,   76,   76,   50,   76,   34,   76,    6,   74,
                33,   75,  109,   34,  168,  169,  124,   41,   15,   50,
                208,  128,   47,   74,   51,   75,  110,  108,   49,  109,
                2,   55,   14,    3,  181,   48,  110,   74,   76,   75,
                50,   56,   69,   77,  135,   12,   34,   68,   42,   35,
                198,   43,   61,   71,   34,   36,  124,   34,   62,  159,
                176,   35,  110,  199,   44,   45,   34,   36,  127,   72,
                33,   85,   86,   60,   37,  102,  226,   74,  187,   75,
                145,  146,   33,   78,   87,   50,   88,   34,   89,  124,
                182,   74,  100,   75,   90,  110,   74,  101,   75,   15,
                98,   34,  178,  179,   34,  104,  105,   34,  110,  200,
                34,   54,   92,   14,  103,  159,   74,  141,   75,  186,
                1,  137,   97,    2,  119,  120,    3,    4,    5,    6,
                7,    8,    9,    6,  110,   10,   11,  227,   12,  137,
                5,   81,  210,  106,  107,  213,  108,  215,   54,    2,
                33,  207,    3,   34,  110,  129,  110,  110,  110,   74,
                142,   75,  153,  108,   12,  110,    2,  132,  133,    3,
                134,  157,  234,  144,   72,  155,  160,  161,  163,   70,
                164,   12,  165,  112,  166,    5,  190,  162,   71,  167,
                171,    6,    7,    8,  109,   72,   72,   72,   72,  122,
                70,   70,   70,   70,  113,  114,  115,  116,  216,   71,
                71,   71,   71,  130,   37,  236,   76,   50,   58,   31,
                50,  170,   73,   50,   50,   50,   50,   50,   50,   50,
                15,   15,   50,   50,   15,   50,   99,   15,   15,   15,
                15,   15,   15,   15,   14,   14,   15,   15,   14,   15,
                53,   14,   14,   14,   14,   14,   14,   14,   58,   31,
                14,   14,  172,   14,   91,    6,   30,   31,    6,   58,
                31,    6,    6,    6,    6,    6,    6,    6,   58,   31,
                6,    6,   54,    6,  126,   32,  185,  175,   59,    6,
                7,    8,  225,    6,    7,    8,  183,   32,  184,   58,
                31,    6,    7,    8,  189,  212,   54,  192,  193,   54,
                195,   54,   64,   58,   31,   66,   58,   31,   93,   58,
                31,   95,   58,   31,  231,  196,  201,  255,  108,  202,
                54,    2,  194,  121,    3,  205,  209,  203,  232,  214,
                4,    5,    6,    7,    8,  211,   12,    1,   10,   11,
                2,  219,  233,    3,    4,    5,    6,    7,    8,    9,
                121,  222,   10,   11,  220,   12,  235,    4,    5,    6,
                7,    8,  221,  223,  224,   10,   11,  228,  229,  239,
                247,  230,    1,  240,  241,    2,  242,  243,    3,    4,
                5,    6,    7,    8,    9,  244,    1,   10,   11,    2,
                12,  158,    3,    4,    5,    6,    7,    8,    9,  245,
                246,   10,   11,  122,   12,  123,  248,  254,    1,  253,
                257,    2,    2,   86,    3,    4,    5,    6,    7,    8,
                9,   87,   90,   10,   11,   91,   12,    1,   88,  122,
                2,  180,   89,    3,    4,    5,    6,    7,    8,    9,
                57,  174,   10,   11,  206,   12,    1,    0,    0,    2,
                0,    0,    3,    4,    5,    6,    7,    8,    9,    0,
                1,   10,   11,    2,   12,   11,    3,    4,    5,    6,
                7,    8,    9,    0,    1,   10,   11,    2,   12,    0,
                3,    4,    5,    6,    7,    8,    9,    0,    1,   10,
                11,    2,   12,    0,    3,    4,    5,    6,    7,    8,
                9,    0,    1,   10,   11,    2,   12,    0,    3,    4,
                5,    6,    7,    8,    9,    0,    0,   10,   11,  143,
                12,    0,    0,  121,    0,    0,    0,    0,    0,  154,
                4,    5,    6,    7,    8,    0,    0,  121,   10,   11,
                0,    0,    0,    0,    4,    5,    6,    7,    8,    0,
                0,    0,   10,   11,    0,  177,    0,    0,    0,    0,
                0,    0,    0,  121,    0,    0,    0,    0,    0,    0,
                4,    5,    6,    7,    8,    0,  121,    0,   10,   11,
                0,    0,    0,    4,    5,    6,    7,    8,  204,    0,
                63,   10,   11,   65,   67,    0,   70,   11,    0,    0,
                0,  218,   79,    0,   11,   11,   11,   11,   11,    0,
                0,    0,   11,   11,    0,    0,    0,    0,    0,    0,
                94,   96,    0,    0,    0,    0,    0,  238,    0,    0,
                0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
                0,    0,    0,    0,    0,    0,    0,  249,    0,  250,
                251,  252,    0,    0,    0,    0,    0,    0,  256,    0,
                0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
                0,    0,    0,    0,  147,  148,  149,  150,  151,  152,
        };
    }
    static short yycheck[];
    static { yycheck(); }
    static void yycheck() {
        yycheck = new short[] {                         41,
                0,   43,   44,   45,   41,   40,   43,   44,   45,   41,
                43,   13,   45,   41,    0,   43,   44,   45,   60,   58,
                62,  125,   40,   60,   46,   62,  123,   60,    0,   62,
                123,   41,   60,   44,   62,   78,   41,  257,   41,  125,
                125,  122,   42,   43,   44,   45,   45,   47,    0,   43,
                125,   45,  123,  125,  134,  135,   82,   40,   44,   59,
                125,   83,  257,   43,  257,   45,  109,  257,  257,  123,
                260,   44,   44,  263,  155,  270,  119,   43,   42,   45,
                269,  257,   41,   47,  123,  275,   45,  258,  256,   40,
                170,  259,   40,  257,   45,   46,  122,   45,   46,  125,
                143,   40,  145,   40,  256,  257,   45,   46,   41,   59,
                61,  270,  271,   61,  125,   41,   41,   43,  123,   45,
                261,  262,   61,   41,  271,  125,   58,   45,  257,  155,
                156,   43,   44,   45,  123,  178,   43,   44,   45,  125,
                257,   45,  261,  262,   45,   74,   75,   45,  191,  171,
                45,   15,   44,  125,  257,  181,   43,   44,   45,  161,
                257,  265,   41,  260,   41,  257,  263,  264,  265,  266,
                267,  268,  269,  125,  217,  272,  273,  199,  275,  265,
                265,  274,  184,   76,   77,  187,  257,  189,   52,  260,
                265,  256,  263,  265,  237,  257,  239,  240,  241,   43,
                44,   45,  256,  257,  275,  248,  260,   40,   40,  263,
                123,  256,  214,   44,  256,  123,   41,  123,   41,  256,
                40,  275,  258,  256,  258,  265,  258,  257,  256,  258,
                257,  266,  267,  268,  123,  277,  278,  279,  280,  123,
                277,  278,  279,  280,  277,  278,  279,  280,  258,  277,
                278,  279,  280,  271,  265,  258,  256,  257,  257,  258,
                260,  123,  256,  263,  264,  265,  266,  267,  268,  269,
                256,  257,  272,  273,  260,  275,  256,  263,  264,  265,
                266,  267,  268,  269,  256,  257,  272,  273,  260,  275,
                125,  263,  264,  265,  266,  267,  268,  269,  257,  258,
                272,  273,  256,  275,  125,  257,  257,  258,  260,  257,
                258,  263,  264,  265,  266,  267,  268,  269,  257,  258,
                272,  273,  186,  275,  257,  276,  125,   44,  276,  266,
                267,  268,  257,  266,  267,  268,  125,  276,  123,  257,
                258,  266,  267,  268,  123,  125,  210,  258,  258,  213,
                125,  215,  256,  257,  258,  256,  257,  258,  256,  257,
                258,  256,  257,  258,  125,  125,   44,  256,  257,   44,
                234,  260,  258,  257,  263,  256,  256,  262,  125,  123,
                264,  265,  266,  267,  268,  256,  275,  257,  272,  273,
                260,  258,  125,  263,  264,  265,  266,  267,  268,  269,
                257,  256,  272,  273,  258,  275,  125,  264,  265,  266,
                267,  268,  258,  256,  125,  272,  273,  257,  125,   41,
                125,  262,  257,   41,   41,  260,   41,  257,  263,  264,
                265,  266,  267,  268,  269,   41,  257,  272,  273,  260,
                275,  125,  263,  264,  265,  266,  267,  268,  269,  256,
                256,  272,  273,  123,  275,  125,   41,   41,  257,  256,
                256,  260,    0,   41,  263,  264,  265,  266,  267,  268,
                269,   41,   41,  272,  273,   41,  275,  257,   41,  123,
                260,  125,   41,  263,  264,  265,  266,  267,  268,  269,
                20,  139,  272,  273,  125,  275,  257,   -1,   -1,  260,
                -1,   -1,  263,  264,  265,  266,  267,  268,  269,   -1,
                257,  272,  273,  260,  275,  125,  263,  264,  265,  266,
                267,  268,  269,   -1,  257,  272,  273,  260,  275,   -1,
                263,  264,  265,  266,  267,  268,  269,   -1,  257,  272,
                273,  260,  275,   -1,  263,  264,  265,  266,  267,  268,
                269,   -1,  257,  272,  273,  260,  275,   -1,  263,  264,
                265,  266,  267,  268,  269,   -1,   -1,  272,  273,  109,
                275,   -1,   -1,  257,   -1,   -1,   -1,   -1,   -1,  119,
                264,  265,  266,  267,  268,   -1,   -1,  257,  272,  273,
                -1,   -1,   -1,   -1,  264,  265,  266,  267,  268,   -1,
                -1,   -1,  272,  273,   -1,  145,   -1,   -1,   -1,   -1,
                -1,   -1,   -1,  257,   -1,   -1,   -1,   -1,   -1,   -1,
                264,  265,  266,  267,  268,   -1,  257,   -1,  272,  273,
                -1,   -1,   -1,  264,  265,  266,  267,  268,  178,   -1,
                29,  272,  273,   32,   33,   -1,   35,  257,   -1,   -1,
                -1,  191,   41,   -1,  264,  265,  266,  267,  268,   -1,
                -1,   -1,  272,  273,   -1,   -1,   -1,   -1,   -1,   -1,
                59,   60,   -1,   -1,   -1,   -1,   -1,  217,   -1,   -1,
                -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
                -1,   -1,   -1,   -1,   -1,   -1,   -1,  237,   -1,  239,
                240,  241,   -1,   -1,   -1,   -1,   -1,   -1,  248,   -1,
                -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
                -1,   -1,   -1,   -1,  113,  114,  115,  116,  117,  118,
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
            "bloque_sentencias : bloque_sentencias sentencia ','",
            "bloque_sentencias : sentencia ','",
            "bloque_sentencias : sentencia",
            "sentencia : sentencia_declarativa",
            "sentencia : sentencia_ejecutable",
            "sentencia : sentencia_control",
            "bloque_declarativo : sentencia_declarativa",
            "bloque_declarativo : '{' bloque_declarativo sentencia_declarativa '}'",
            "bloque_declarativo : '{' bloque_declarativo sentencia_declarativa error",
            "bloque_declarativo : bloque_declarativo sentencia_declarativa '}' error",
            "sentencia_declarativa : tipo lista_variables",
            "sentencia_declarativa : ID lista_variables",
            "sentencia_declarativa : declaracion_funcion",
            "sentencia_declarativa : declaracion_clase",
            "sentencia_declarativa : declaracion_distribuida",
            "sentencia_declarativa : declaracion_interfaz",
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
            "declaracion_funcion : VOID ID '(' ')' '{' bloque_sentencias '}'",
            "declaracion_funcion : VOID ID '(' tipo ID ')' '{' bloque_sentencias '}'",
            "declaracion_funcion : VOID ID '(' tipo ID '{' bloque_sentencias '}'",
            "declaracion_funcion : VOID ID tipo ID ')' '{' bloque_sentencias '}'",
            "declaracion_funcion : VOID ID '(' ID ')' '{' bloque_sentencias '}' error",
            "declaracion_funcion : VOID ID '(' tipo ID ')' '{' '}' error",
            "declaracion_funcion : VOID ID '(' ')' '{' '}' error",
            "tipo : SHORT",
            "tipo : ULONG",
            "tipo : DOUBLE",
            "lista_variables : ID",
            "lista_variables : lista_variables ';' ID",
            "sentencia_ejecutable : asignacion",
            "sentencia_ejecutable : invocacion_funcion",
            "sentencia_ejecutable : seleccion",
            "sentencia_ejecutable : imprimir",
            "sentencia_ejecutable : ref_clase '(' ')'",
            "sentencia_ejecutable : RETURN",
            "ref_clase : ID '.' ID",
            "ref_clase : ref_clase '.' ID",
            "asignacion : ID '=' expresion ','",
            "asignacion : ID MENOS_IGUAL expresion ','",
            "asignacion : ref_clase '=' expresion ','",
            "asignacion : ref_clase MENOS_IGUAL expresion ','",
            "asignacion : ID expresion error",
            "asignacion : ref_clase expresion error",
            "asignacion : ID '=' error",
            "asignacion : ref_clase '=' error",
            "asignacion : ID MENOS_IGUAL error",
            "asignacion : ref_clase MENOS_IGUAL error",
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
            "seleccion : IF '(' ')' bloque_ejecutable END_IF",
            "seleccion : IF '(' ')' bloque_ejecutable ELSE bloque_ejecutable END_IF",
            "seleccion : IF '(' condicion ')' error",
            "condicion : expresion MAYOR_IGUAL expresion",
            "condicion : expresion MENOR_IGUAL expresion",
            "condicion : expresion '<' expresion",
            "condicion : expresion '>' expresion",
            "condicion : expresion IGUAL expresion",
            "condicion : expresion DISTINTO expresion",
            "condicion : expresion error",
            "bloque_ejecutable : sentencia_ejecutable ','",
            "bloque_ejecutable : '{' bloque_ejecutable sentencia_ejecutable ',' '}'",
            "imprimir : PRINT CADENA",
            "imprimir : PRINT error",
            "sentencia_control : FOR ID IN RANGE '(' CTE CTE CTE ')' bloque_ejecutable",
            "sentencia_control : FOR ID IN RANGE '(' CTE CTE ')' bloque_ejecutable",
            "sentencia_control : FOR ID IN RANGE '(' CTE ')' bloque_ejecutable",
            "sentencia_control : FOR ID IN RANGE '(' ')' bloque_ejecutable",
            "sentencia_control : FOR IN RANGE '(' CTE CTE CTE ')' bloque_ejecutable error",
            "sentencia_control : FOR ID RANGE '(' CTE CTE CTE ')' bloque_ejecutable",
            "sentencia_control : FOR ID IN '(' CTE CTE CTE ')' bloque_ejecutable",
            "sentencia_control : FOR ID IN RANGE '(' CTE CTE CTE ')' error",
    };

//#line 206 "gramatica.y"
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
    //#line 566 "Parser.java"
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
//#line 14 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba '}'");}
                break;
                case 3:
//#line 15 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba '{'");}
                break;
                case 6:
//#line 20 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba ','");}
                break;
                case 12:
//#line 30 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba '}'");}
                break;
                case 13:
//#line 31 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba '{'");}
                break;
                case 15:
//#line 36 "gramatica.y"
                {
                    /* accion que chequee si el tipo es “CLASE” (id no puede ser un tipo si no es una clase)*/
                    if (!al.getTablaSimbolos().getAtributos(val_peek(1).sval).isTipo("CLASE")){
                        System.out.println("Error linea "+al.getLinea()+": "+val_peek(1).sval+" no es tipo CLASE");
                    }
                }
                break;
                case 20:
//#line 48 "gramatica.y"
                {
                    System.out.println("Linea: " + al.getLinea() + " Declaracion CLASE " + val_peek(3).sval);
                    /* accion para ID que establezca como tipo “CLASE” */
                    al.getTablaSimbolos().getAtributos(val_peek(3).sval).setTipo("CLASE");
                }
                break;
                case 21:
//#line 53 "gramatica.y"
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
                case 22:
//#line 62 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " no se puede definir una clase sin cuerpo");}
                break;
                case 23:
//#line 63 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " no se puede definir una clase sin cuerpo");}
                break;
                case 24:
//#line 64 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " revisar declaracion de clase");}
                break;
                case 25:
//#line 66 "gramatica.y"
                {
                    System.out.println("Linea: " + al.getLinea() + " Declaracion DISTRIBUIDA para " + val_peek(4).sval);
                    /* accion para fijarse que id sea una clase */
                    if (!al.getTablaSimbolos().getAtributos(val_peek(4).sval).isTipo("CLASE")){
                        System.out.println("Error linea "+al.getLinea()+": "+val_peek(4).sval+" no es una clase");
                    }
                }
                break;
                case 26:
//#line 73 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba ':'");}
                break;
                case 27:
//#line 74 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " no se puede definir una declaracion distribuida sin cuerpo");}
                break;
                case 28:
//#line 75 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " falta palabra reservada FOR");}
                break;
                case 29:
//#line 79 "gramatica.y"
                {/* accion para ID que establezca como tipo “INTERFACE” */
                    al.getTablaSimbolos().getAtributos(val_peek(3).sval).setTipo("INTERFACE");
                }
                break;
                case 30:
//#line 82 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " no se puede declarar una interfaz sin metodos");}
                break;
                case 33:
//#line 87 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba ','");}
                break;
                case 34:
//#line 88 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba ','");}
                break;
                case 35:
//#line 91 "gramatica.y"
                {System.out.println("Linea: " + al.getLinea() + " Declaracion metodo VOID " + val_peek(4).sval);}
                break;
                case 36:
//#line 92 "gramatica.y"
                {System.out.println("Linea: " + al.getLinea() + " Declaracion metodo VOID " + val_peek(2).sval);}
                break;
                case 37:
//#line 93 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba ')'");}
                break;
                case 38:
//#line 94 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba '('");}
                break;
                case 39:
//#line 95 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " falta el tipo de " + val_peek(2).sval);}
                break;
                case 40:
//#line 98 "gramatica.y"
                {System.out.println("Linea: " + al.getLinea() + " Declaracion funcion VOID " + val_peek(5).sval);}
                break;
                case 41:
//#line 99 "gramatica.y"
                {System.out.println("Linea: " + al.getLinea() + " Declaracion funcion VOID " + val_peek(7).sval);}
                break;
                case 42:
//#line 100 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba ')'");}
                break;
                case 43:
//#line 101 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba '('");}
                break;
                case 44:
//#line 102 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " falta el tipo de " + val_peek(5).sval);}
                break;
                case 45:
//#line 103 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " no se puede declarar una funcion sin cuerpo");}
                break;
                case 46:
//#line 104 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " no se puede declarar una funcion sin cuerpo");}
                break;
                case 52:
//#line 116 "gramatica.y"
                {System.out.println("Linea: " + al.getLinea() + " ASIGNACION");}
                break;
                case 53:
//#line 117 "gramatica.y"
                {System.out.println("Linea: " + al.getLinea() + " INVOCACION FUNCION");}
                break;
                case 54:
//#line 118 "gramatica.y"
                {System.out.println("Linea: " + al.getLinea() + " Sentencia IF");}
                break;
                case 64:
//#line 132 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " asignacion mal definida");}
                break;
                case 65:
//#line 133 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " asignacion mal definida");}
                break;
                case 66:
//#line 134 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " asignacion mal definida");}
                break;
                case 67:
//#line 135 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " asignacion mal definida");}
                break;
                case 68:
//#line 136 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " asignacion mal definida");}
                break;
                case 69:
//#line 137 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " asignacion mal definida");}
                break;
                case 77:
//#line 152 "gramatica.y"
                {   chequeoRango(val_peek(0).sval, al);
                    al.getTablaSimbolos().getAtributos(val_peek(0).sval).sumarUso();
                }
                break;
                case 78:
//#line 155 "gramatica.y"
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
                case 83:
//#line 174 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " falta condicion");}
                break;
                case 84:
//#line 175 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " falta condicion");}
                break;
                case 85:
//#line 176 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " cuerpo de IF vacio");}
                break;
                case 92:
//#line 185 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba una comparacion");}
                break;
                case 96:
//#line 193 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba una cadena de caracteres");}
                break;
                case 97:
//#line 196 "gramatica.y"
                {System.out.println("Linea: " + al.getLinea() + " Sentencia FOR");}
                break;
                case 98:
//#line 197 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " falta una constante");}
                break;
                case 99:
//#line 198 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " faltan constantes");}
                break;
                case 100:
//#line 199 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " faltan constantes");}
                break;
                case 101:
//#line 200 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba un identificador");}
                break;
                case 102:
//#line 201 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " falta la palabra reservada IN");}
                break;
                case 103:
//#line 202 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " falta la palabra reservada RANGE");}
                break;
                case 104:
//#line 203 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " no se puede definir un FOR sin cuerpo");}
                break;
//#line 972 "Parser.java"
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
