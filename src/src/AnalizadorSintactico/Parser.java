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
            6,    3,    9,    3,    3,    3,    3,    3,    3,    3,
            7,    7,    7,    8,    8,   10,   10,   10,   10,   10,
            10,   10,   10,   10,   11,   11,   11,   11,   11,   11,
            11,   12,   12,   12,   12,   12,   13,   13,   13,   14,
            14,   14,   14,   15,   15,   15,   15,   15,    4,    4,
            4,    4,    4,    4,    4,    4,    4,    4,    4,   16,
            16,   16,   16,   16,   16,   16,   16,   16,   16,   16,
            16,   22,   22,   22,   23,   23,   23,   24,   24,   25,
            25,   17,   17,   17,   17,   18,   18,   18,   18,   18,
            18,   26,   26,   26,   26,   26,   26,   19,   19,   19,
            20,   20,   21,   21,   21,   21,   21,   21,   21,   21,
    };
    final static short yylen[] = {                            2,
            3,    2,    2,    2,    1,    1,    1,    2,    1,    2,
            1,    3,    0,    4,    1,    1,    1,    1,    3,    3,
            1,    1,    1,    1,    3,    8,   10,    9,    9,   10,
            10,    8,    8,   10,    6,    8,    6,    8,    8,    6,
            8,    8,    8,    8,    8,    8,    6,    6,    6,    2,
            3,    1,    2,    6,    4,    5,    5,    6,    1,    1,
            2,    2,    1,    4,    2,    2,    2,    2,    4,    4,
            4,    4,    4,    4,    4,    4,    4,    4,    4,    4,
            4,    3,    3,    1,    3,    3,    1,    1,    1,    1,
            2,    5,    4,    5,    4,   12,    8,    8,   12,    6,
            10,    3,    3,    3,    3,    3,    3,    3,    3,    3,
            3,    3,   14,   12,   10,    9,   14,   13,   13,   12,
    };
    final static short yydefred[] = {                         0,
            0,    0,    0,    0,    0,   21,   22,   23,    0,    0,
            0,    0,    0,    0,    0,    5,    6,    7,    0,   15,
            16,   17,   18,   59,   60,    0,   63,    0,    0,   24,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,   68,   67,    0,    3,    4,
            0,   62,   61,    0,    0,    0,    0,   66,   65,   88,
            90,    0,    0,    0,    0,   87,   89,    0,    0,    0,
            0,  111,   20,   13,    0,    0,    0,    0,  110,  108,
            109,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    1,   19,   12,    0,    0,    0,    0,    0,  112,
            75,   91,   79,   71,    0,    0,    0,    0,   95,   93,
            0,   74,   78,   70,   14,   25,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    9,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,   77,   81,   73,   69,   64,
            76,   80,   72,    0,    0,   85,   86,   94,   92,    0,
            11,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    8,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,   50,    0,   10,    0,  100,    0,    0,    0,    0,
            37,   40,   35,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            48,   49,   47,   51,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,   55,    0,
            0,    0,   98,    0,    0,   97,   38,   41,   36,   39,
            0,   32,   33,   26,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,   45,   43,   44,   46,   42,    0,
            0,   57,    0,    0,    0,    0,   28,    0,    0,   29,
            116,    0,    0,    0,    0,    0,    0,   58,   54,    0,
            101,    0,   30,   31,   34,   27,    0,    0,  115,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,   99,
            96,  120,    0,  114,    0,    0,    0,    0,  119,  118,
            0,  113,  117,
    };
    final static short yydgoto[] = {                         14,
            15,   16,   17,   18,  130,  162,   19,   35,  115,   20,
            21,   22,   23,  144,  145,   24,   25,   26,   27,   28,
            29,   64,   65,   66,   67,   78,
    };
    final static short yysindex[] = {                      -105,
            -11,  -14,  -27, -153, -149,    0,    0,    0, -152, -148,
            -147,    7, -141,    0,   60,    0,    0,    0, -116,    0,
            0,    0,    0,    0,    0,   43,    0,  -12,   44,    0,
            21,  -29,   23, -109,   41,   18,   45, -107,  -99,    2,
            -156, -114,   95, -101,   56,    0,    0,   77,    0,    0,
            42,    0,    0,   32,  125,   34,  -92,    0,    0,    0,
            0,  -87,  -82,    3,   65,    0,    0,   46,   97,  -85,
            10,    0,    0,    0,  -83,   67,  -24,  153,    0,    0,
            0,  -80,  -84,   17,  -62,  -38,  159,  165,   84,   55,
            -69,    0,    0,    0,  -45,   26,   47,  -41,   37,    0,
            0,    0,    0,    0,  -22,  -22,  -22,  -22,    0,    0,
            48,    0,    0,    0,    0,    0, -124,  -22,  -22,  -22,
            -22,  -22,  -22,  104,   91,  496, -116,  181,    0,  423,
            190,  111,    1,  210,  271,   38,   38,   38,   57,   57,
            189,   61,  269,  -68,  277,    0,    0,    0,    0,    0,
            0,    0,    0,   65,   65,    0,    0,    0,    0,   -9,
            0,  -51,   92,   92,   92,   92,   92,   92,  207,   80,
            -124,  440,  454,  100,   49,    0,  224,  250,   31,  230,
            -1,  301,  305,  311,  243,  246,  -65,    5,  121,   50,
            334,    0, -118,    0, -124,    0,   94,  335,  473,  336,
            0,    0,    0, -141,  338,  264, -141,  260, -141,  261,
            58,   38,   38,   38,  343,  344,  354,  276,   20,  129,
            0,    0,    0,    0,  279,  151,   98, -115,  152,   51,
            154,  278,  157,   52,  295,  309,  328, -124,   38,  291,
            357,  360,  362,  162,  166,  167,   53,  385,    0,  168,
            389, -124,    0,  169,  310,    0,    0,    0,    0,    0,
            380,    0,    0,    0,  391,  392,  342,  393,  136,   70,
            -124,   38,   38,   38,    0,    0,    0,    0,    0,  182,
            398,    0,  137,  184, -124,  188,    0,  193,   54,    0,
            0,   38,  322,  206,  414,  415,  416,    0,    0,  196,
            0,  359,    0,    0,    0,    0,  418, -124,    0,  341,
            345,  349,  205,  203,  -48,  361, -124, -124, -124,    0,
            0,    0, -124,    0,  363,  383,  384,  409,    0,    0,
            217,    0,    0,
    };
    final static short yyrindex[] = {                         0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,  474,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,  -40,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,  -61,    0,    0,    0,    0,    0,
            0,    0,    0,  -35,  -30,    0,    0,    0,    0,    0,
            0,    0,  435,  436,  437,  439,  441,  442,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            -52,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            -5,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,
    };
    final static short yygindex[] = {                         0,
            -6,  -15,  255,  177,  -42,  124,   22,  468,    0,  -37,
            0,    0,    0,    0,  346,    0,    0,    0,    0,    0,
            0,  629,   81,   85,  178,    0,
    };
    final static int YYTABLESIZE=769;
    static short yytable[];
    static { yytable();}
    static void yytable(){
        yytable = new short[]{                         50,
                84,  136,   84,   84,   84,   82,   48,   82,   82,   82,
                83,   68,   83,   83,   83,   63,   38,   13,  105,   84,
                106,   84,   63,   83,   82,   36,   82,   55,   32,   83,
                32,   83,   50,   57,   34,  122,   34,  123,   56,  210,
                128,   84,  126,   63,  219,  105,  104,  106,   56,   33,
                47,   33,  105,  114,  106,  143,  190,  132,   76,  217,
                249,   85,   63,   52,   62,   63,   70,   63,  105,  148,
                106,  208,   53,  193,  323,   95,   63,   98,   63,  105,
                153,  106,   63,  173,   74,   94,   53,   59,   80,  110,
                150,  159,  203,  223,  259,  264,  279,  306,  240,   75,
                75,  185,  186,   39,   41,  133,  107,   40,   43,   45,
                293,  108,  141,   86,   87,    1,  239,   42,    2,   56,
                44,    3,    4,    5,    6,    7,    8,    9,  292,  199,
                10,   11,  160,   12,  105,    2,  106,  111,    3,  105,
                30,  106,  225,  226,    9,  255,  256,   72,   81,  218,
                12,    1,   89,  207,    2,   90,   88,    3,    4,    5,
                6,    7,    8,    9,  100,   97,   10,   11,  101,   12,
                112,  206,  127,  116,   82,  102,  125,  140,   91,    4,
                5,    6,    7,    8,   49,  154,  155,   10,   11,  117,
                50,  156,  157,  124,  134,  142,  142,  232,  137,    5,
                235,   92,  237,   52,  138,  160,  139,  322,    2,  220,
                146,    3,   53,  172,  151,   84,   50,    9,  228,   50,
                82,   50,  254,   12,  174,   83,  171,   60,   61,  267,
                177,   37,  135,  178,   60,   61,   84,   84,   84,   84,
                250,   82,   82,   82,   82,   30,   83,   83,   83,   83,
                180,   50,  118,  119,  120,  121,   61,  179,  103,   56,
                291,  300,   46,   54,   31,  113,   31,    6,    7,    8,
                6,    7,    8,  131,   60,   61,  248,   60,   61,   60,
                61,  147,    6,    7,    8,    6,    7,    8,   60,   61,
                60,   61,  152,  161,  197,   61,   73,   93,   52,   58,
                79,  109,  149,  158,  202,  222,  258,  263,  278,  305,
                181,  187,  189,  182,  183,  184,    1,  188,  227,    2,
                192,    5,    3,    4,    5,    6,    7,    8,    9,  195,
                309,   10,   11,    1,   12,  196,    2,  129,  194,    3,
                4,    5,    6,    7,    8,    9,  204,  161,   10,   11,
                160,   12,  209,    2,  160,  201,    3,    2,  211,  212,
                3,  269,    9,  213,  169,  170,    9,  215,   12,  214,
                216,  161,   12,  194,  205,  283,  221,  224,  229,  231,
                129,  233,  236,  238,  176,  251,  244,  245,  234,  241,
                242,  243,  160,  160,  294,    2,    2,  246,    3,    3,
                247,  252,  261,  194,    9,    9,  253,  257,  302,  260,
                12,   12,  262,  271,  161,  272,  270,  275,  273,  265,
                274,  276,  277,  286,  281,  280,  129,  176,  161,  282,
                284,  316,  285,  266,  287,  288,  290,  298,  299,  301,
                325,  326,  327,  303,  308,  194,  328,  161,  304,  295,
                296,  297,  268,  176,  310,  311,  312,  313,  315,  194,
                320,  161,  160,  317,  321,    2,  289,  318,    3,  307,
                194,  319,  333,    2,    9,  102,  103,  106,  194,  107,
                12,  104,  105,  314,  161,  324,   51,  329,    0,  191,
                0,    0,  194,  161,  161,  161,    0,    0,    0,  161,
                0,  194,  194,  194,  194,    0,    1,  330,  331,    2,
                0,    0,    3,    4,    5,    6,    7,    8,    9,    0,
                1,   10,   11,    2,   12,    0,    3,    4,    5,    6,
                7,    8,    9,  332,    1,   10,   11,    2,   12,    0,
                3,    4,    5,    6,    7,    8,    9,  175,    0,   10,
                11,    1,   12,    0,    2,    0,    0,    3,    4,    5,
                6,    7,    8,    9,  198,    1,   10,   11,    2,   12,
                0,    3,    4,    5,    6,    7,    8,    9,  200,    0,
                10,   11,    0,   12,    1,    0,    0,    2,    0,    0,
                3,    4,    5,    6,    7,    8,    9,  230,    1,   10,
                11,    2,   12,    0,    3,    4,    5,    6,    7,    8,
                9,    0,    0,   10,   11,  160,   12,  160,    2,  160,
                2,    3,    2,    3,    0,    3,    0,    9,    0,    9,
                0,    9,    0,   12,    0,   12,    0,   12,    0,  160,
                160,    0,    2,    2,    0,    3,    3,    0,    0,    0,
                0,    9,    9,    0,    0,    0,    0,   12,   12,    0,
                69,   71,    0,    0,   77,  160,    0,    0,    2,    0,
                0,    3,    0,    0,    0,    0,    0,    9,    0,  127,
                0,    0,   96,   12,   99,    0,    4,    5,    6,    7,
                8,    0,    0,    0,   10,   11,  127,    0,    0,    0,
                0,    0,    0,    4,    5,    6,    7,    8,    0,    0,
                127,   10,   11,    0,    0,    0,    0,    4,    5,    6,
                7,    8,    0,    0,    0,   10,   11,    0,    0,  127,
                0,    0,    0,    0,    0,    0,    4,    5,    6,    7,
                8,    0,    0,    0,   10,   11,  163,  164,  165,  166,
                167,  168,  127,    0,    0,    0,    0,    0,    0,    4,
                5,    6,    7,    8,    0,    0,    0,   10,   11,
        };
    }
    static short yycheck[];
    static { yycheck(); }
    static void yycheck() {
        yycheck = new short[] {                         15,
                41,   40,   43,   44,   45,   41,   13,   43,   44,   45,
                41,   41,   43,   44,   45,   45,   44,  123,   43,   60,
                45,   62,   45,  123,   60,   40,   62,   40,   40,   60,
                40,   62,   48,   46,   46,   60,   46,   62,   44,   41,
                125,   40,  123,   45,   40,   43,   44,   45,   61,   61,
                44,   61,   43,   44,   45,  125,  125,   41,   41,  125,
                41,   40,   45,  125,   44,   45,   44,   45,   43,   44,
                45,   41,  125,  125,  123,   44,   45,   44,   45,   43,
                44,   45,   45,  126,   44,   44,   44,   44,   44,   44,
                44,   44,   44,   44,   44,   44,   44,   44,   41,   59,
                59,  139,  140,  257,  257,   84,   42,  257,  257,  257,
                41,   47,   58,  270,  271,  257,   59,  270,  260,  125,
                269,  263,  264,  265,  266,  267,  268,  269,   59,  172,
                272,  273,  257,  275,   43,  260,   45,   41,  263,   43,
                257,   45,  261,  262,  269,  261,  262,  257,  256,  187,
                275,  257,   58,  123,  260,  257,  271,  263,  264,  265,
                266,  267,  268,  269,  257,   41,  272,  273,  256,  275,
                256,  178,  257,  257,  274,  258,  257,  123,  123,  264,
                265,  266,  267,  268,  125,  105,  106,  272,  273,  123,
                206,  107,  108,   41,  257,  265,  265,  204,   40,  265,
                207,  125,  209,  265,   40,  257,  123,  256,  260,  188,
                256,  263,  265,  123,  256,  256,  232,  269,  125,  235,
                256,  237,  125,  275,   44,  256,  123,  257,  258,  236,
                41,  259,  271,  123,  257,  258,  277,  278,  279,  280,
                219,  277,  278,  279,  280,  257,  277,  278,  279,  280,
                41,  267,  277,  278,  279,  280,  258,  257,  256,  265,
                125,  125,  256,  276,  276,  256,  276,  266,  267,  268,
                266,  267,  268,  257,  257,  258,  257,  257,  258,  257,
                258,  256,  266,  267,  268,  266,  267,  268,  257,  258,
                257,  258,  256,  117,  171,  258,  256,  256,  256,  256,
                256,  256,  256,  256,  256,  256,  256,  256,  256,  256,
                40,  123,   44,  136,  137,  138,  257,  257,  195,  260,
                44,  265,  263,  264,  265,  266,  267,  268,  269,  123,
                125,  272,  273,  257,  275,  256,  260,   83,  162,  263,
                264,  265,  266,  267,  268,  269,  123,  171,  272,  273,
                257,  275,  123,  260,  257,  256,  263,  260,  181,   59,
                263,  238,  269,   59,  261,  262,  269,  125,  275,   59,
                125,  195,  275,  197,  125,  252,  256,   44,   44,   44,
                126,   44,  123,  123,  130,  257,   44,   44,  125,  212,
                213,  214,  257,  257,  271,  260,  260,   44,  263,  263,
                125,  123,  125,  227,  269,  269,  256,  256,  285,  256,
                275,  275,  256,  123,  238,   59,  239,  256,   59,  125,
                59,  256,  256,   44,  257,   41,  172,  173,  252,   41,
                262,  308,  123,  125,   44,   44,   44,  256,   41,  256,
                317,  318,  319,  256,  123,  269,  323,  271,  256,  272,
                273,  274,  125,  199,   41,   41,   41,  262,   41,  283,
                256,  285,  257,  123,  262,  260,  125,  123,  263,  292,
                294,  123,  256,    0,  269,   41,   41,   41,  302,   41,
                275,   41,   41,  125,  308,  125,   19,  125,   -1,  144,
                -1,   -1,  316,  317,  318,  319,   -1,   -1,   -1,  323,
                -1,  325,  326,  327,  328,   -1,  257,  125,  125,  260,
                -1,   -1,  263,  264,  265,  266,  267,  268,  269,   -1,
                257,  272,  273,  260,  275,   -1,  263,  264,  265,  266,
                267,  268,  269,  125,  257,  272,  273,  260,  275,   -1,
                263,  264,  265,  266,  267,  268,  269,  125,   -1,  272,
                273,  257,  275,   -1,  260,   -1,   -1,  263,  264,  265,
                266,  267,  268,  269,  125,  257,  272,  273,  260,  275,
                -1,  263,  264,  265,  266,  267,  268,  269,  125,   -1,
                272,  273,   -1,  275,  257,   -1,   -1,  260,   -1,   -1,
                263,  264,  265,  266,  267,  268,  269,  125,  257,  272,
                273,  260,  275,   -1,  263,  264,  265,  266,  267,  268,
                269,   -1,   -1,  272,  273,  257,  275,  257,  260,  257,
                260,  263,  260,  263,   -1,  263,   -1,  269,   -1,  269,
                -1,  269,   -1,  275,   -1,  275,   -1,  275,   -1,  257,
                257,   -1,  260,  260,   -1,  263,  263,   -1,   -1,   -1,
                -1,  269,  269,   -1,   -1,   -1,   -1,  275,  275,   -1,
                32,   33,   -1,   -1,   36,  257,   -1,   -1,  260,   -1,
                -1,  263,   -1,   -1,   -1,   -1,   -1,  269,   -1,  257,
                -1,   -1,   54,  275,   56,   -1,  264,  265,  266,  267,
                268,   -1,   -1,   -1,  272,  273,  257,   -1,   -1,   -1,
                -1,   -1,   -1,  264,  265,  266,  267,  268,   -1,   -1,
                257,  272,  273,   -1,   -1,   -1,   -1,  264,  265,  266,
                267,  268,   -1,   -1,   -1,  272,  273,   -1,   -1,  257,
                -1,   -1,   -1,   -1,   -1,   -1,  264,  265,  266,  267,
                268,   -1,   -1,   -1,  272,  273,  118,  119,  120,  121,
                122,  123,  257,   -1,   -1,   -1,   -1,   -1,   -1,  264,
                265,  266,  267,  268,   -1,   -1,   -1,  272,  273,
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
            "bloque_ejecutable : bloque_ejecutable sentencia_ejecutable",
            "bloque_ejecutable : sentencia_ejecutable",
            "sentencia_declarativa : tipo lista_variables ','",
            "$$1 :",
            "sentencia_declarativa : ID lista_variables ',' $$1",
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
            "declaracion_clase : CLASS ID '{' bloque_declarativo '}' ','",
            "declaracion_clase : CLASS ID IMPLEMENT ID '{' bloque_declarativo '}' ','",
            "declaracion_clase : CLASS ID '{' '}' ',' error",
            "declaracion_clase : CLASS ID IMPLEMENT ID '{' '}' ',' error",
            "declaracion_clase : CLASS ID IMPLEMENT '{' bloque_declarativo '}' ',' error",
            "declaracion_clase : CLASS ID '{' bloque_declarativo '}' error",
            "declaracion_clase : CLASS ID IMPLEMENT ID '{' bloque_declarativo '}' error",
            "declaracion_distribuida : IMPL FOR ID ':' '{' declaracion_funcion '}' ','",
            "declaracion_distribuida : IMPL FOR ID '{' declaracion_funcion '}' ',' error",
            "declaracion_distribuida : IMPL FOR ID ':' '{' '}' ',' error",
            "declaracion_distribuida : IMPL ID ':' '{' declaracion_funcion '}' ',' error",
            "declaracion_distribuida : IMPL FOR ID ':' '{' declaracion_funcion '}' error",
            "declaracion_interfaz : INTERFACE ID '{' metodos_interfaz '}' ','",
            "declaracion_interfaz : INTERFACE ID '{' '}' ',' error",
            "declaracion_interfaz : INTERFACE ID '{' metodos_interfaz '}' error",
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
            "sentencia_ejecutable : seleccion ','",
            "sentencia_ejecutable : seleccion error",
            "sentencia_ejecutable : imprimir",
            "sentencia_ejecutable : ref_clase '(' ')' ','",
            "sentencia_ejecutable : sentencia_control ','",
            "sentencia_ejecutable : sentencia_control error",
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
            "factor : constante",
            "constante : CTE",
            "constante : '-' CTE",
            "invocacion_funcion : ID '(' expresion ')' ','",
            "invocacion_funcion : ID '(' ')' ','",
            "invocacion_funcion : ID '(' expresion ')' error",
            "invocacion_funcion : ID '(' ')' error",
            "seleccion : IF '(' condicion ')' '{' bloque_ejecutable '}' ELSE '{' bloque_ejecutable '}' END_IF",
            "seleccion : IF '(' condicion ')' '{' bloque_ejecutable '}' END_IF",
            "seleccion : IF '(' ')' '{' bloque_ejecutable '}' END_IF error",
            "seleccion : IF '(' ')' '{' bloque_ejecutable '}' ELSE '{' bloque_ejecutable '}' END_IF error",
            "seleccion : IF '(' condicion ')' END_IF error",
            "seleccion : IF '(' condicion ')' ELSE '{' bloque_ejecutable '}' END_IF error",
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
            "sentencia_control : FOR ID IN RANGE '(' constante ';' constante ';' constante ')' '{' bloque_ejecutable '}'",
            "sentencia_control : FOR ID IN RANGE '(' constante ';' constante ')' '{' bloque_ejecutable '}'",
            "sentencia_control : FOR ID IN RANGE '(' constante ')' '{' bloque_ejecutable '}'",
            "sentencia_control : FOR ID IN RANGE '(' ')' '{' bloque_ejecutable '}'",
            "sentencia_control : FOR IN RANGE '(' constante ';' constante ';' constante ')' '{' bloque_ejecutable '}' error",
            "sentencia_control : FOR ID RANGE '(' constante ';' constante ';' constante ')' '{' bloque_ejecutable '}'",
            "sentencia_control : FOR ID IN '(' constante ';' constante ';' constante ')' '{' bloque_ejecutable '}'",
            "sentencia_control : FOR ID IN RANGE '(' constante ';' constante ';' constante ')' error",
    };

//#line 223 ".\gramatica.y"
    /* CODE SECTION */

    public void chequeoRango(String cte, Analizador_Lexico al){
        if (cte.contains("_s")){
            int cteint = Integer.parseInt(cte.substring(0, cte.length()-2));
            double min = Math.pow(-2,7);
            double max = Math.pow(2,7)-1;
            if ((cteint < min)||(cteint>max)){
                System.out.println("ERROR. Linea "+getLinea(al)+": Constante fuera de rango");
            }
        } else if (cte.contains("_ul")) {
            long cteint_largo = Integer.parseInt(cte.substring(0, cte.length()-3));
            double min = 0;
            double max = Math.pow(2,32)-1;
            if ((cteint_largo < min)||(cteint_largo>max)){
                System.out.println("ERROR. Linea "+getLinea(al)+": Constante fuera de rango");
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
                System.out.println("ERROR. Linea "+getLinea(al)+": Constante fuera de rango");
            }
        }
    }

    public int getLinea(Analizador_Lexico al){
        return al.getLinea()-1;
    }

    public int yylex(Analizador_Lexico al){
        int tok = al.yylex();
        yylval = new ParserVal(al.getBuffer());
        return tok;
    }
    public void yyerror(String s){}
    //#line 620 "Parser.java"
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
//#line 12 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " se esperaba '}'");}
                break;
                case 3:
//#line 13 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " se esperaba '{'");}
                break;
                case 12:
//#line 32 ".\gramatica.y"
                {System.out.println("Linea: " + al.getLinea() + " Declaracion de VARIABLE/S");}
                break;
                case 13:
//#line 33 ".\gramatica.y"
                {System.out.println("Linea: " + al.getLinea() + " Declaracion de VARIABLE/S");}
                break;
                case 14:
//#line 33 ".\gramatica.y"
                {
                    /* accion que chequee si el tipo es “CLASE” (id no puede ser un tipo si no es una clase)*/
                    if (!al.getTablaSimbolos().getAtributos(val_peek(3).sval).isTipo("CLASE")){
                        System.out.println("ERROR. Linea: "+getLinea(al)+": "+val_peek(3).sval+" no es tipo CLASE");
                    }
                }
                break;
                case 19:
//#line 43 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " se esperaba ','");}
                break;
                case 20:
//#line 44 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " se esperaba ','");}
                break;
                case 26:
//#line 56 ".\gramatica.y"
                {System.out.println("Linea: " + al.getLinea() + " Declaracion funcion VOID " + val_peek(6).sval);}
                break;
                case 27:
//#line 57 ".\gramatica.y"
                {System.out.println("Linea: " + al.getLinea() + " Declaracion funcion VOID " + val_peek(8).sval);}
                break;
                case 28:
//#line 58 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " se esperaba ')'");}
                break;
                case 29:
//#line 59 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " se esperaba '('");}
                break;
                case 30:
//#line 60 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " falta el tipo de " + val_peek(6).sval);}
                break;
                case 31:
//#line 61 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " no se puede declarar una funcion sin cuerpo");}
                break;
                case 32:
//#line 62 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " no se puede declarar una funcion sin cuerpo");}
                break;
                case 33:
//#line 63 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " se esperaba ','");}
                break;
                case 34:
//#line 64 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " se esperaba ','");}
                break;
                case 35:
//#line 67 ".\gramatica.y"
                {
                    System.out.println("Linea: " + al.getLinea() + " Declaracion CLASE " + val_peek(4).sval);
                    /* accion para ID que establezca como tipo “CLASE” */
                    al.getTablaSimbolos().getAtributos(val_peek(4).sval).setTipo("CLASE");
                }
                break;
                case 36:
//#line 72 ".\gramatica.y"
                {
                    System.out.println("Linea: " + al.getLinea() + " Declaracion CLASE " + val_peek(6).sval);
                    /* accion que chequea si ID_2 es una interfaz y asignar tipo CLASE a ID_1 */
                    if (al.getTablaSimbolos().getAtributos(val_peek(4).sval).isTipo("INTERFACE")){
                        al.getTablaSimbolos().getAtributos(val_peek(6).sval).setTipo("CLASE");
                    } else {
                        System.out.println("ERROR. Linea "+getLinea(al)+": "+val_peek(4).sval+" no es un INTERFACE");
                    }
                }
                break;
                case 37:
//#line 81 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " no se puede definir una clase sin cuerpo");}
                break;
                case 38:
//#line 82 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " no se puede definir una clase sin cuerpo");}
                break;
                case 39:
//#line 83 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " falta el identificador de la interfaz");}
                break;
                case 40:
//#line 84 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " se esperaba ','");}
                break;
                case 41:
//#line 85 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " se esperaba ','");}
                break;
                case 42:
//#line 88 ".\gramatica.y"
                {
                    System.out.println("Linea: " + al.getLinea() + " Declaracion DISTRIBUIDA para " + val_peek(5).sval);
                    /* accion para fijarse que id sea una clase */
                    if (!al.getTablaSimbolos().getAtributos(val_peek(5).sval).isTipo("CLASE")){
                        System.out.println("ERROR. Linea "+getLinea(al)+": "+val_peek(5).sval+" no es una clase");
                    }
                }
                break;
                case 43:
//#line 95 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " se esperaba ':'");}
                break;
                case 44:
//#line 96 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " no se puede definir una declaracion distribuida sin cuerpo");}
                break;
                case 45:
//#line 97 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " falta palabra reservada FOR");}
                break;
                case 46:
//#line 98 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " se esperaba ','");}
                break;
                case 47:
//#line 101 ".\gramatica.y"
                {
                    /* accion para ID que establezca como tipo “INTERFACE” */
                    al.getTablaSimbolos().getAtributos(val_peek(4).sval).setTipo("INTERFACE");
                }
                break;
                case 48:
//#line 105 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " no se puede declarar una interfaz sin metodos");}
                break;
                case 49:
//#line 106 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " se esperaba ','");}
                break;
                case 52:
//#line 111 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " se esperaba ','");}
                break;
                case 53:
//#line 112 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " se esperaba ','");}
                break;
                case 54:
//#line 115 ".\gramatica.y"
                {System.out.println("Linea: " + al.getLinea() + " Declaracion metodo VOID " + val_peek(4).sval);}
                break;
                case 55:
//#line 116 ".\gramatica.y"
                {System.out.println("Linea: " + al.getLinea() + " Declaracion metodo VOID " + val_peek(2).sval);}
                break;
                case 56:
//#line 117 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " se esperaba ')'");}
                break;
                case 57:
//#line 118 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " se esperaba '('");}
                break;
                case 58:
//#line 119 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " falta el tipo de " + val_peek(2).sval);}
                break;
                case 59:
//#line 122 ".\gramatica.y"
                {System.out.println("Linea: " + al.getLinea() + " ASIGNACION");}
                break;
                case 60:
//#line 123 ".\gramatica.y"
                {System.out.println("Linea: " + al.getLinea() + " INVOCACION FUNCION");}
                break;
                case 61:
//#line 124 ".\gramatica.y"
                {System.out.println("Linea: " + al.getLinea() + " Sentencia IF");}
                break;
                case 62:
//#line 125 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " se esperaba ','");}
                break;
                case 66:
//#line 129 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " se esperaba ','");}
                break;
                case 68:
//#line 131 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " se esperaba ','");}
                break;
                case 69:
//#line 132 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " se esperaba ','");}
                break;
                case 74:
//#line 139 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " Se esperaba expresion");}
                break;
                case 75:
//#line 140 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " Se esperaba expresion");}
                break;
                case 76:
//#line 141 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " Se esperaba expresion");}
                break;
                case 77:
//#line 142 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " Se esperaba expresion");}
                break;
                case 78:
//#line 143 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " se esperaba ','");}
                break;
                case 79:
//#line 144 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " se esperaba ','");}
                break;
                case 80:
//#line 145 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " se esperaba ','");}
                break;
                case 81:
//#line 146 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " se esperaba ','");}
                break;
                case 82:
//#line 149 ".\gramatica.y"
                {System.out.println("Linea: " + al.getLinea() + " SUMA");}
                break;
                case 83:
//#line 150 ".\gramatica.y"
                {System.out.println("Linea: " + al.getLinea() + " RESTA");}
                break;
                case 85:
//#line 154 ".\gramatica.y"
                {System.out.println("Linea: " + al.getLinea() + " MULTIPLICACION");}
                break;
                case 86:
//#line 155 ".\gramatica.y"
                {System.out.println("Linea: " + al.getLinea() + " DIVISION");}
                break;
                case 90:
//#line 162 ".\gramatica.y"
                { chequeoRango(val_peek(0).sval, al);
                    al.getTablaSimbolos().getAtributos(val_peek(0).sval).sumarUso();
                }
                break;
                case 91:
//#line 165 ".\gramatica.y"
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
                case 94:
//#line 180 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " se esperaba ','");}
                break;
                case 95:
//#line 181 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " se esperaba ','");}
                break;
                case 98:
//#line 186 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " falta condicion");}
                break;
                case 99:
//#line 187 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " falta condicion");}
                break;
                case 100:
//#line 188 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " cuerpo de IF vacio");}
                break;
                case 101:
//#line 189 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " cuerpo de IF vacio");}
                break;
                case 109:
//#line 202 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " se esperaba una cadena de caracteres");}
                break;
                case 110:
//#line 203 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " se esperaba ','");}
                break;
                case 113:
//#line 210 ".\gramatica.y"
                {System.out.println("Linea: " + al.getLinea() + " Sentencia FOR");}
                break;
                case 114:
//#line 211 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " falta una constante");}
                break;
                case 115:
//#line 212 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " faltan constantes");}
                break;
                case 116:
//#line 213 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " faltan constantes");}
                break;
                case 117:
//#line 214 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba un identificador");}
                break;
                case 118:
//#line 215 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " falta la palabra reservada IN");}
                break;
                case 119:
//#line 216 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " falta la palabra reservada RANGE");}
                break;
                case 120:
//#line 217 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " no se puede definir un FOR sin cuerpo");}
                break;
//#line 1107 "Parser.java"
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
    public void run(Analizador_Lexico al)
    {
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
