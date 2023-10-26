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
import AnalizadorLexico.*;
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
            6,    6,    3,    3,    3,    3,    3,    3,    3,    3,
            7,    7,    7,    8,    8,    9,    9,    9,    9,    9,
            9,    9,    9,    9,   13,   13,   14,   14,   14,   14,
            14,   14,   10,   10,   10,   10,   10,   10,   10,   15,
            15,   16,   16,   16,   16,   16,   11,   11,   11,   11,
            11,   12,   12,   12,   17,   17,   17,   17,   18,   18,
            18,   18,   18,    4,    4,    4,    4,    4,    4,    4,
            4,    4,    4,    4,    4,    4,   19,   19,   19,   19,
            19,   19,   19,   19,   19,   19,   19,   19,   25,   25,
            25,   26,   26,   26,   27,   27,   28,   28,   20,   20,
            21,   21,   21,   21,   21,   21,   29,   29,   29,   29,
            29,   29,   22,   22,   23,   23,   24,   24,   24,   24,
            24,   24,   30,   30,   30,
    };
    final static short yylen[] = {                            2,
            3,    2,    2,    2,    1,    1,    1,    2,    2,    1,
            2,    1,    3,    3,    3,    3,    1,    1,    1,    1,
            1,    1,    1,    1,    3,    8,   10,    9,    9,   10,
            10,    8,    8,   10,    2,    1,    3,    3,    3,    3,
            1,    1,    6,    8,    6,    8,    8,    6,    8,    2,
            1,    3,    3,    3,    3,    1,    8,    8,    8,    8,
            8,    6,    6,    6,    2,    3,    1,    2,    6,    4,
            5,    5,    6,    1,    2,    2,    2,    2,    2,    2,
            4,    4,    2,    2,    2,    2,    4,    4,    4,    4,
            3,    4,    3,    4,    4,    4,    4,    4,    3,    3,
            1,    3,    3,    1,    1,    1,    1,    2,    4,    3,
            12,    8,    8,   12,    6,   10,    3,    3,    3,    3,
            3,    3,    2,    2,    3,    3,   10,    9,   10,    9,
            9,    8,    5,    4,    2,
    };
    final static short yydefred[] = {                         0,
            0,    0,    0,    0,    0,    0,   21,   22,   23,    0,
            0,    0,    0,    0,    0,    0,    5,    6,    7,    0,
            17,   18,   19,   20,   74,    0,    0,    0,    0,    0,
            8,   24,    0,    0,    0,    0,    0,    0,    0,  124,
            123,    0,    0,    0,    0,    0,    0,    0,   86,   85,
            0,    3,    4,    0,   76,   75,   78,   77,   80,   79,
            0,    0,    0,    0,    0,   84,   83,  105,  107,    0,
            0,    0,    0,  104,  106,   91,  110,    0,    0,  125,
            16,   14,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    1,   15,   13,    0,
            0,   93,    0,    0,  126,   92,  108,   96,   88,    0,
            0,    0,    0,  109,   95,   87,   25,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            56,    0,   51,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,   94,   98,
            90,   82,   81,   97,   89,    0,    0,  102,  103,    0,
            12,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,   50,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,   65,    0,   11,    0,  115,    0,
            0,    0,    0,   55,   53,   45,   54,   52,   48,   43,
            0,    0,    0,   42,    0,   41,    0,   36,    0,    0,
            0,    0,    0,  135,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,   63,   64,   62,   66,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            35,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,   70,    0,    0,    0,  113,
            0,    0,  112,   46,   49,   44,   47,    0,   40,   38,
            32,   39,   37,   33,   26,    0,    0,    0,    0,    0,
            132,    0,  134,    0,    0,    0,    0,   60,   58,   59,
            61,   57,    0,    0,   72,    0,    0,    0,    0,   28,
            0,    0,   29,  128,    0,  133,  131,  130,    0,   73,
            69,    0,  116,    0,   30,   31,   34,   27,  127,  129,
            0,    0,  114,  111,
    };
    final static short yydgoto[] = {                         15,
            16,   17,   18,  161,    0,  162,  215,   38,  216,   22,
            23,   24,  217,  218,  132,  133,  147,  148,   25,   26,
            27,   28,   29,   30,   72,   73,   74,   75,   86,  185,
    };
    final static short yysindex[] = {                        85,
            42,    3,    8, -181, -131, -119,    0,    0,    0, -185,
            -134, -113,   -4,  433,    0,  140,    0,    0,    0,  -83,
            0,    0,    0,    0,    0,   52,   54,   62,  -35,   63,
            0,    0,   29, -180,   15,  -32,  -67,   55,   18,    0,
            0,  -91,  -34, -103,  -99,  129,  -61,   71,    0,    0,
            170,    0,    0,   56,    0,    0,    0,    0,    0,    0,
            35,  -58,  162,  -32,  -46,    0,    0,    0,    0,  -50,
            -39,   22,   94,    0,    0,    0,    0,  112,   38,    0,
            0,    0,  -36,  100,  -29,  189,  -62,  -88,   14,   11,
            -28,  237,  264,  183,   10,  -90,    0,    0,    0,   70,
            46,    0,   64,   49,    0,    0,    0,    0,    0,  -32,
            -32,  -32,  -32,    0,    0,    0,    0, -126,  -32,  -32,
            -32,  -32,  -32,  -32,  -70,  205, -106,  -83,  283,  -83,
            0,  387,    0,  291,  212,   79,  297,  300,   32,   32,
            32,   81,   81,  221,   90,  311,  -68,  312,    0,    0,
            0,    0,    0,    0,    0,   94,   94,    0,    0,  -10,
            0,  -87,  113,  113,  113,  113,  113,  113,  238,  107,
            -126,  393,  406,   57,  108,   58,   65,    0,  250,  102,
            4,  253,    9,  -51,  345,  346,  348,  266,  268,  -63,
            -22,  138,   66,  354,    0,  -97,    0, -126,    0,  184,
            355,  412,  357,    0,    0,    0,    0,    0,    0,    0,
            115,    3,  358,    0,  -83,    0,  204,    0,  115,  272,
            115,  287,  375,    0,   32,  294,  295,  296,  376,  377,
            378,  298,   17,  167,    0,    0,    0,    0,  302,  175,
            303,  -45,  176,   68,  190,  218,   59,  192,   61,   75,
            0,  241,  254,  267, -126,  -52,  -49, -126, -126, -126,
            196,  198,  201,   77,  388,    0,  203,  399, -126,    0,
            200,  335,    0,    0,    0,    0,    0,  419,    0,    0,
            0,    0,    0,    0,    0,  424,  430,  289,  436,  324,
            0, -126,    0,   32,  326,  331,  351,    0,    0,    0,
            0,    0,  226,  447,    0,  352,  233, -126,  235,    0,
            236,   78,    0,    0,  365,    0,    0,    0,  243,    0,
            0,  232,    0,  372,    0,    0,    0,    0,    0,    0,
            244,  240,    0,    0,
    };
    final static short yyrindex[] = {                         0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            503,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,  -41,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,  -56,    0,    0,
            0,    0,    0,    0,    0,  -21,  -16,    0,    0,    0,
            0,    0,  464,  474,  485,  487,  497,  498,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,  -55,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,  -37,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,
    };
    final static short yygindex[] = {                         0,
            526,   88,    0,    1,    0,  426,   97,  173,  323,    0,
            0,    0,  -69, -122,  -85,  -48,    0,  394,    0,    0,
            0,    0,    0,    0,  603,   89,  101,    6,    0,  -12,
    };
    final static int YYTABLESIZE=734;
    static short yytable[];
    static { yytable();}
    static void yytable(){
        yytable = new short[]{                        101,
                19,  101,  101,  101,   63,   89,   71,  225,   62,  294,
                65,  139,   71,  110,   19,  111,   19,  233,  101,   99,
                101,   99,   99,   99,  100,   64,  100,  100,  100,   35,
                123,   88,  124,   34,  146,   37,  129,  196,   99,   50,
                99,  173,   35,  100,  220,  100,   34,   39,   37,  222,
                36,   19,  171,   71,  135,   77,  193,  266,   84,   71,
                127,  231,   71,   36,  110,  109,  111,  144,   67,   68,
                292,   44,   70,   71,   40,   76,   71,   41,  100,   71,
                110,  116,  111,  178,   45,   31,  202,   71,  110,  151,
                111,  110,  155,  111,  251,   56,   20,   58,   82,   99,
                205,  208,  280,   53,  283,   60,   67,  153,  210,  237,
                20,  276,   20,   83,   83,   83,   83,   83,  285,   83,
                302,  328,   46,  251,  178,   42,  219,  186,  187,  251,
                160,  251,  143,    3,   47,  112,    4,   43,   53,   90,
                113,  246,   10,   48,  184,  184,  184,   20,   13,  252,
                128,  254,  114,  178,  110,  110,  111,  111,    6,    7,
                8,    9,  197,  239,  240,  251,   91,   92,  128,  160,
                223,   93,    3,   32,  145,    4,    6,    7,    8,    9,
                214,   10,   87,  288,  130,  136,   94,   13,  184,   80,
                169,  170,   54,   96,  126,   95,  145,  102,  156,  157,
                197,    6,  103,  291,  224,  106,  293,   14,   67,   68,
                105,  214,  158,  159,  101,  272,  273,  214,  107,  214,
                117,  214,  118,  130,   68,   69,  213,   71,  130,  125,
                257,    7,    8,    9,   99,  101,  101,  101,  101,  100,
                61,  197,  138,    7,    8,    9,  214,  119,  120,  121,
                122,   49,  214,  214,  214,   99,   99,   99,   99,   32,
                100,  100,  100,  100,   52,   33,   69,  137,  130,  130,
                134,   68,   69,  265,   68,   69,  140,  108,   33,    7,
                8,    9,    7,    8,    9,   68,   69,  234,  214,   69,
                197,   68,   69,  115,   97,  197,  197,  197,  130,  316,
                174,  150,  176,  141,  154,  142,  197,   55,  242,   57,
                81,   98,  204,  207,  279,  197,  282,   59,   66,  152,
                209,  236,   21,  275,  197,  149,  175,  172,  250,  267,
                284,  179,  301,  327,  180,  181,   21,  182,   21,  183,
                1,    2,  278,  190,    3,    6,  191,    4,    5,    6,
                7,    8,    9,   10,  192,  195,   11,   12,  212,   13,
                198,    3,  199,  206,    4,  286,    6,    7,    8,    9,
                10,  212,  211,   21,    3,  221,   13,    4,  287,    6,
                7,    8,    9,   10,  247,  226,  227,  249,  228,   13,
                229,  289,  230,  235,  253,    1,    2,  238,  243,    3,
                245,  248,    4,    5,    6,    7,    8,    9,   10,  255,
                131,   11,   12,  312,   13,  256,  258,  259,  260,  261,
                262,  263,  264,  268,  269,    1,    2,  271,  303,    3,
                270,  274,    4,    5,    6,    7,    8,    9,   10,  305,
                160,   11,   12,    3,   13,  277,    4,  281,  314,  131,
                317,  298,   10,  299,  131,  318,  300,  308,   13,  304,
                212,  307,  309,    3,  188,  189,    4,  310,    6,    7,
                8,    9,   10,  311,  212,  319,  322,    3,   13,  313,
                4,  320,    6,    7,    8,    9,   10,  321,  323,  329,
                325,  326,   13,  331,  131,  131,  332,  212,  330,  333,
                3,  334,    2,    4,  117,    6,    7,    8,    9,   10,
                212,  177,  232,    3,  118,   13,    4,  201,    6,    7,
                8,    9,   10,  212,  131,  121,    3,  122,   13,    4,
                203,    6,    7,    8,    9,   10,  244,  119,  120,   51,
                194,   13,    0,    0,    0,  212,    0,    0,    3,    0,
                0,    4,    0,    6,    7,    8,    9,   10,    0,  160,
                0,    0,    3,   13,    0,    4,    0,    0,    0,    0,
                0,   10,    0,    0,    0,    0,    0,   13,    0,    0,
                160,    0,  160,    3,    0,    3,    4,  160,    4,    0,
                3,    0,   10,    4,   10,    0,  200,    0,   13,   10,
                13,    0,    0,    0,    0,   13,    0,  160,  160,    0,
                3,    3,    0,    4,    4,    0,    0,    0,    0,   10,
                10,  160,    0,  241,    3,   13,   13,    4,  160,    0,
                0,    3,    0,   10,    4,    0,    0,   78,   79,   13,
                10,   85,    0,  128,    0,    0,   13,    0,    0,  128,
                0,    6,    7,    8,    9,    0,    0,    6,    7,    8,
                9,    0,  128,  101,    0,    0,  104,    0,  128,    0,
                6,    7,    8,    9,    0,    0,    6,    7,    8,    9,
                290,    0,    0,  295,  296,  297,    0,    0,    1,    2,
                0,    0,    3,    0,  306,    4,    5,    6,    7,    8,
                9,   10,    0,    0,   11,   12,    0,   13,    0,    0,
                0,    0,    0,    0,    0,    0,    0,  315,    0,    0,
                0,  163,  164,  165,  166,  167,  168,    0,    0,    0,
                0,    0,    0,  324,
        };
    }
    static short yycheck[];
    static { yycheck(); }
    static void yycheck() {
        yycheck = new short[] {                         41,
                0,   43,   44,   45,   40,   40,   44,   59,   44,   59,
                46,   40,   45,   43,   14,   45,   16,   40,   60,   41,
                62,   43,   44,   45,   41,   61,   43,   44,   45,   40,
                60,  123,   62,   44,  125,   46,  125,  125,   60,   44,
                62,  127,   40,   60,   41,   62,   44,   40,   46,   41,
                61,   51,  123,   45,   41,   41,  125,   41,   41,   45,
                123,  125,   45,   61,   43,   44,   45,   58,  125,  125,
                123,  257,   44,   45,  256,  256,   45,  259,   44,   45,
                43,   44,   45,  132,  270,   44,  172,  125,   43,   44,
                45,   43,   44,   45,  217,   44,    0,   44,   44,   44,
                44,   44,   44,   16,   44,   44,   44,   44,   44,   44,
                14,   44,   16,   59,   59,   59,   59,   59,   44,   59,
                44,   44,  257,  246,  173,  257,  123,  140,  141,  252,
                257,  254,  123,  260,  269,   42,  263,  257,   51,   43,
                47,  211,  269,  257,  139,  140,  141,   51,  275,  219,
                257,  221,   41,  202,   43,   43,   45,   45,  265,  266,
                267,  268,  162,  261,  262,  288,  270,  271,  257,  257,
                183,  271,  260,  257,  265,  263,  265,  266,  267,  268,
                180,  269,  274,  253,   88,   89,   58,  275,  183,  257,
                261,  262,   20,  123,  257,  257,  265,  256,  110,  111,
                200,  265,   41,  256,  256,  256,  256,  123,  265,  265,
                257,  211,  112,  113,  256,  261,  262,  217,  258,  219,
                257,  221,  123,  127,  257,  258,  125,  265,  132,   41,
                225,  266,  267,  268,  256,  277,  278,  279,  280,  256,
                276,  241,  271,  266,  267,  268,  246,  277,  278,  279,
                280,  256,  252,  253,  254,  277,  278,  279,  280,  257,
                277,  278,  279,  280,  125,  276,  258,  257,  172,  173,
                257,  257,  258,  257,  257,  258,   40,  256,  276,  266,
                267,  268,  266,  267,  268,  257,  258,  191,  288,  258,
                290,  257,  258,  256,  125,  295,  296,  297,  202,  294,
                128,  256,  130,   40,  256,  123,  306,  256,  125,  256,
                256,  256,  256,  256,  256,  315,  256,  256,  256,  256,
                256,  256,    0,  256,  324,  256,   44,  123,  125,  233,
                256,   41,  256,  256,  123,  257,   14,   41,   16,   40,
                256,  257,  125,  123,  260,  265,  257,  263,  264,  265,
                266,  267,  268,  269,   44,   44,  272,  273,  257,  275,
                123,  260,  256,  256,  263,  125,  265,  266,  267,  268,
                269,  257,  123,   51,  260,  123,  275,  263,  125,  265,
                266,  267,  268,  269,  212,   41,   41,  215,   41,  275,
                125,  125,  125,  256,  123,  256,  257,   44,   44,  260,
                44,   44,  263,  264,  265,  266,  267,  268,  269,  123,
                88,  272,  273,  125,  275,   41,  123,  123,  123,   44,
                44,   44,  125,  257,  123,  256,  257,  125,   41,  260,
                256,  256,  263,  264,  265,  266,  267,  268,  269,   41,
                257,  272,  273,  260,  275,  256,  263,  256,  125,  127,
                125,  256,  269,  256,  132,  125,  256,  123,  275,  257,
                257,  262,   44,  260,  142,  143,  263,   44,  265,  266,
                267,  268,  269,   44,  257,  125,  125,  260,  275,   44,
                263,  256,  265,  266,  267,  268,  269,   41,  256,  125,
                256,  256,  275,  262,  172,  173,  125,  257,  256,  256,
                260,  262,    0,  263,   41,  265,  266,  267,  268,  269,
                257,  125,  190,  260,   41,  275,  263,  125,  265,  266,
                267,  268,  269,  257,  202,   41,  260,   41,  275,  263,
                125,  265,  266,  267,  268,  269,  125,   41,   41,   14,
                147,  275,   -1,   -1,   -1,  257,   -1,   -1,  260,   -1,
                -1,  263,   -1,  265,  266,  267,  268,  269,   -1,  257,
                -1,   -1,  260,  275,   -1,  263,   -1,   -1,   -1,   -1,
                -1,  269,   -1,   -1,   -1,   -1,   -1,  275,   -1,   -1,
                257,   -1,  257,  260,   -1,  260,  263,  257,  263,   -1,
                260,   -1,  269,  263,  269,   -1,  171,   -1,  275,  269,
                275,   -1,   -1,   -1,   -1,  275,   -1,  257,  257,   -1,
                260,  260,   -1,  263,  263,   -1,   -1,   -1,   -1,  269,
                269,  257,   -1,  198,  260,  275,  275,  263,  257,   -1,
                -1,  260,   -1,  269,  263,   -1,   -1,   35,   36,  275,
                269,   39,   -1,  257,   -1,   -1,  275,   -1,   -1,  257,
                -1,  265,  266,  267,  268,   -1,   -1,  265,  266,  267,
                268,   -1,  257,   61,   -1,   -1,   64,   -1,  257,   -1,
                265,  266,  267,  268,   -1,   -1,  265,  266,  267,  268,
                255,   -1,   -1,  258,  259,  260,   -1,   -1,  256,  257,
                -1,   -1,  260,   -1,  269,  263,  264,  265,  266,  267,
                268,  269,   -1,   -1,  272,  273,   -1,  275,   -1,   -1,
                -1,   -1,   -1,   -1,   -1,   -1,   -1,  292,   -1,   -1,
                -1,  119,  120,  121,  122,  123,  124,   -1,   -1,   -1,
                -1,   -1,   -1,  308,
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
            "sentencia_declarativa : tipo lista_variables ','",
            "sentencia_declarativa : ID lista_variables ','",
            "sentencia_declarativa : tipo lista_variables error",
            "sentencia_declarativa : ID lista_variables error",
            "sentencia_declarativa : declaracion_funcion",
            "sentencia_declarativa : declaracion_clase",
            "sentencia_declarativa : declaracion_distribuida",
            "sentencia_declarativa : declaracion_interfaz",
            "tipo : SHORT",
            "tipo : ULONG",
            "tipo : DOUBLE",
            "lista_variables : ID",
            "lista_variables : lista_variables ';' ID",
            "declaracion_funcion : VOID ID '(' ')' '{' bloque_funcion '}' ','",
            "declaracion_funcion : VOID ID '(' tipo ID ')' '{' bloque_funcion '}' ','",
            "declaracion_funcion : VOID ID '(' tipo ID '{' bloque_funcion '}' ','",
            "declaracion_funcion : VOID ID tipo ID ')' '{' bloque_funcion '}' ','",
            "declaracion_funcion : VOID ID '(' ID ')' '{' bloque_funcion '}' ',' error",
            "declaracion_funcion : VOID ID '(' tipo ID ')' '{' '}' ',' error",
            "declaracion_funcion : VOID ID '(' ')' '{' '}' ',' error",
            "declaracion_funcion : VOID ID '(' ')' '{' bloque_funcion '}' error",
            "declaracion_funcion : VOID ID '(' tipo ID ')' '{' bloque_funcion '}' error",
            "bloque_funcion : bloque_funcion sentencia_funcion",
            "bloque_funcion : sentencia_funcion",
            "sentencia_funcion : tipo lista_variables ','",
            "sentencia_funcion : ID lista_variables ','",
            "sentencia_funcion : tipo lista_variables error",
            "sentencia_funcion : ID lista_variables error",
            "sentencia_funcion : declaracion_funcion",
            "sentencia_funcion : sentencia_ejecutable",
            "declaracion_clase : CLASS ID '{' bloque_clase '}' ','",
            "declaracion_clase : CLASS ID IMPLEMENT ID '{' bloque_clase '}' ','",
            "declaracion_clase : CLASS ID '{' '}' ',' error",
            "declaracion_clase : CLASS ID IMPLEMENT ID '{' '}' ',' error",
            "declaracion_clase : CLASS ID IMPLEMENT '{' bloque_clase '}' ',' error",
            "declaracion_clase : CLASS ID '{' bloque_clase '}' error",
            "declaracion_clase : CLASS ID IMPLEMENT ID '{' bloque_clase '}' error",
            "bloque_clase : bloque_clase sentencia_clase",
            "bloque_clase : sentencia_clase",
            "sentencia_clase : tipo lista_variables ','",
            "sentencia_clase : ID lista_variables ','",
            "sentencia_clase : tipo lista_variables error",
            "sentencia_clase : ID lista_variables error",
            "sentencia_clase : declaracion_funcion",
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
            "imprimir : PRINT CADENA",
            "imprimir : PRINT error",
            "ref_clase : ID '.' ID",
            "ref_clase : ref_clase '.' ID",
            "sentencia_control : FOR ID IN RANGE '(' encabezado_for ')' '{' bloque_ejecutable '}'",
            "sentencia_control : FOR ID IN RANGE '(' ')' '{' bloque_ejecutable '}'",
            "sentencia_control : FOR IN RANGE '(' encabezado_for ')' '{' bloque_ejecutable '}' error",
            "sentencia_control : FOR ID RANGE '(' encabezado_for ')' '{' bloque_ejecutable '}'",
            "sentencia_control : FOR ID IN '(' encabezado_for ')' '{' bloque_ejecutable '}'",
            "sentencia_control : FOR ID IN RANGE '(' encabezado_for ')' error",
            "encabezado_for : constante ';' constante ';' constante",
            "encabezado_for : constante ';' constante error",
            "encabezado_for : constante error",
    };

    //#line 225 "gramatica.y"
    /* CODE SECTION */
    public String tipo = "";
    public String ambito = "";

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

    public int yylex(){
        int tok = Analizador_Lexico.yylex();
        yylval = new ParserVal(Analizador_Lexico.buffer);
        return tok;
    }
    public void yyerror(String s){}
    //#line 631 "Parser.java"
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
                case 13:
//#line 34 "gramatica.y"
                {
                    System.out.println("Linea: " + Analizador_Lexico.cantLineas + " Declaracion de VARIABLE/S de TIPO " + val_peek(2).sval);
                }
                break;
                case 14:
//#line 37 "gramatica.y"
                {System.out.println("Linea: " + Analizador_Lexico.cantLineas + " Declaracion de VARIABLE/S de TIPO " + val_peek(2).sval);}
                break;
                case 15:
//#line 38 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DE VARIABLES. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 16:
//#line 39 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DE VARIABLES. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 21:
//#line 46 "gramatica.y"
                {tipo = val_peek(0).sval;}
                break;
                case 22:
//#line 47 "gramatica.y"
                {tipo = val_peek(0).sval;}
                break;
                case 23:
//#line 48 "gramatica.y"
                {tipo = val_peek(0).sval;}
                break;
                case 24:
//#line 51 "gramatica.y"
                {Tabla_Simbolos.getAtributos(val_peek(0).sval).setTipo(tipo);}
                break;
                case 25:
//#line 52 "gramatica.y"
                {Tabla_Simbolos.getAtributos(val_peek(0).sval).setTipo(tipo);}
                break;
                case 26:
//#line 55 "gramatica.y"
                {System.out.println("Linea: " + Analizador_Lexico.cantLineas + " Declaracion funcion VOID " + val_peek(6).sval);}
                break;
                case 27:
//#line 56 "gramatica.y"
                {System.out.println("Linea: " + Analizador_Lexico.cantLineas + " Declaracion funcion VOID " + val_peek(8).sval);}
                break;
                case 28:
//#line 57 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DE FUNCION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ')'");}
                break;
                case 29:
//#line 58 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DE FUNCION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba '('");}
                break;
                case 30:
//#line 59 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DE FUNCION. Linea: " + Analizador_Lexico.cantLineas + " falta el tipo de " + val_peek(6).sval);}
                break;
                case 31:
//#line 60 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DE FUNCION. Linea: " + Analizador_Lexico.cantLineas + " no se puede declarar una funcion sin cuerpo");}
                break;
                case 32:
//#line 61 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DE FUNCION. Linea: " + Analizador_Lexico.cantLineas + " no se puede declarar una funcion sin cuerpo");}
                break;
                case 33:
//#line 62 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DE FUNCION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 34:
//#line 63 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DE FUNCION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 39:
//#line 72 "gramatica.y"
                {System.out.println("ERROR EN SENTENCIA DE FUNCION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 40:
//#line 73 "gramatica.y"
                {System.out.println("ERROR EN SENTENCIA DE FUNCION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 43:
//#line 78 "gramatica.y"
                {System.out.println("Linea: " + Analizador_Lexico.cantLineas + " Declaracion CLASE " + val_peek(4).sval);}
                break;
                case 44:
//#line 79 "gramatica.y"
                {System.out.println("Linea: " + Analizador_Lexico.cantLineas + " Declaracion CLASE " + val_peek(6).sval);}
                break;
                case 45:
//#line 80 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DE CLASE. Linea: " + Analizador_Lexico.cantLineas + " no se puede definir una clase sin cuerpo");}
                break;
                case 46:
//#line 81 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DE CLASE. Linea: " + Analizador_Lexico.cantLineas + " no se puede definir una clase sin cuerpo");}
                break;
                case 47:
//#line 82 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DE CLASE. Linea: " + Analizador_Lexico.cantLineas + " falta el identificador de la interfaz");}
                break;
                case 48:
//#line 83 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DE CLASE. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 49:
//#line 84 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DE CLASE. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 54:
//#line 93 "gramatica.y"
                {System.out.println("ERROR EN SENTENCIA DE CLASE. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 55:
//#line 94 "gramatica.y"
                {System.out.println("ERROR EN SENTENCIA DE CLASE. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 57:
//#line 98 "gramatica.y"
                {System.out.println("Linea: " + Analizador_Lexico.cantLineas + " Declaracion DISTRIBUIDA para " + val_peek(5).sval);}
                break;
                case 58:
//#line 99 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DISTRIBUIDA. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ':'");}
                break;
                case 59:
//#line 100 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DISTRIBUIDA. Linea: " + Analizador_Lexico.cantLineas + " no se puede definir una declaracion distribuida sin cuerpo");}
                break;
                case 60:
//#line 101 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DISTRIBUIDA. Linea: " + Analizador_Lexico.cantLineas + " falta palabra reservada FOR");}
                break;
                case 61:
//#line 102 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DISTRIBUIDA. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 62:
//#line 105 "gramatica.y"
                {System.out.println("Linea: " + Analizador_Lexico.cantLineas + " Declaracion INTERFAZ " + val_peek(4).sval);}
                break;
                case 63:
//#line 106 "gramatica.y"
                {System.out.println("ERROR EN INTERFAZ. Linea: " + Analizador_Lexico.cantLineas + " no se puede declarar una interfaz sin metodos");}
                break;
                case 64:
//#line 107 "gramatica.y"
                {System.out.println("ERROR EN INTERFAZ. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 67:
//#line 112 "gramatica.y"
                {System.out.println("ERROR EN METODO DE INTERFAZ. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 68:
//#line 113 "gramatica.y"
                {System.out.println("ERROR EN METODO DE INTERFAZ. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 69:
//#line 116 "gramatica.y"
                {System.out.println("Linea: " + Analizador_Lexico.cantLineas + " Declaracion metodo VOID " + val_peek(4).sval);}
                break;
                case 70:
//#line 117 "gramatica.y"
                {System.out.println("Linea: " + Analizador_Lexico.cantLineas + " Declaracion metodo VOID " + val_peek(2).sval);}
                break;
                case 71:
//#line 118 "gramatica.y"
                {System.out.println("ERROR EN METODO DE INTERFAZ. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ')'");}
                break;
                case 72:
//#line 119 "gramatica.y"
                {System.out.println("ERROR EN METODO DE INTERFAZ. Linea: " + Analizador_Lexico.cantLineas + " se esperaba '('");}
                break;
                case 73:
//#line 120 "gramatica.y"
                {System.out.println("ERROR EN METODO DE INTERFAZ. Linea: " + Analizador_Lexico.cantLineas + " falta el tipo de " + val_peek(2).sval);}
                break;
                case 74:
//#line 123 "gramatica.y"
                {System.out.println("Linea: " + Analizador_Lexico.cantLineas + " ASIGNACION");}
                break;
                case 75:
//#line 124 "gramatica.y"
                {System.out.println("Linea: " + Analizador_Lexico.cantLineas + " INVOCACION FUNCION");}
                break;
                case 76:
//#line 125 "gramatica.y"
                {System.out.println("ERROR EN INVOCACION A LA FUNCION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 77:
//#line 126 "gramatica.y"
                {System.out.println("Linea: " + Analizador_Lexico.cantLineas + " Sentencia IF");}
                break;
                case 78:
//#line 127 "gramatica.y"
                {System.out.println("ERROR EN SENTENCIA IF. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 79:
//#line 128 "gramatica.y"
                {System.out.println("Linea: " + Analizador_Lexico.cantLineas + " SENTENCIA DE IMPRESION");}
                break;
                case 80:
//#line 129 "gramatica.y"
                {System.out.println("ERROR EN SENTENCIA DE IMPRESION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 81:
//#line 130 "gramatica.y"
                {System.out.println("Linea: " + Analizador_Lexico.cantLineas + " REFERENCIA A CLASE");}
                break;
                case 82:
//#line 131 "gramatica.y"
                {System.out.println("ERROR EN REFERENCIA A CLASE. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 84:
//#line 133 "gramatica.y"
                {System.out.println("ERROR EN SENTENCIA FOR. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 86:
//#line 135 "gramatica.y"
                {System.out.println("ERROR EN RETURN. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 91:
//#line 142 "gramatica.y"
                {System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " Se esperaba expresion");}
                break;
                case 92:
//#line 143 "gramatica.y"
                {System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " Se esperaba expresion");}
                break;
                case 93:
//#line 144 "gramatica.y"
                {System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " Se esperaba expresion");}
                break;
                case 94:
//#line 145 "gramatica.y"
                {System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " Se esperaba expresion");}
                break;
                case 95:
//#line 146 "gramatica.y"
                {System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 96:
//#line 147 "gramatica.y"
                {System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 97:
//#line 148 "gramatica.y"
                {System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 98:
//#line 149 "gramatica.y"
                {System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 99:
//#line 152 "gramatica.y"
                {System.out.println("Linea: " + Analizador_Lexico.cantLineas + " SUMA");}
                break;
                case 100:
//#line 153 "gramatica.y"
                {System.out.println("Linea: " + Analizador_Lexico.cantLineas + " RESTA");}
                break;
                case 102:
//#line 157 "gramatica.y"
                {System.out.println("Linea: " + Analizador_Lexico.cantLineas + " MULTIPLICACION");}
                break;
                case 103:
//#line 158 "gramatica.y"
                {System.out.println("Linea: " + Analizador_Lexico.cantLineas + " DIVISION");}
                break;
                case 107:
//#line 165 "gramatica.y"
                { chequeoRango(val_peek(0).sval);
                    Tabla_Simbolos.getAtributos(val_peek(0).sval).sumarUso();
                }
                break;
                case 108:
//#line 168 "gramatica.y"
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
                case 113:
//#line 187 "gramatica.y"
                {System.out.println("ERROR EN SENTENCIA IF. Linea: " + Analizador_Lexico.cantLineas + " falta condicion");}
                break;
                case 114:
//#line 188 "gramatica.y"
                {System.out.println("ERROR EN SENTENCIA IF. Linea: " + Analizador_Lexico.cantLineas + " falta condicion");}
                break;
                case 115:
//#line 189 "gramatica.y"
                {System.out.println("ERROR EN SENTENCIA IF. Linea: " + Analizador_Lexico.cantLineas + " cuerpo de IF vacio");}
                break;
                case 116:
//#line 190 "gramatica.y"
                {System.out.println("ERROR EN SENTENCIA IF. Linea: " + Analizador_Lexico.cantLineas + " cuerpo de IF vacio");}
                break;
                case 124:
//#line 203 "gramatica.y"
                {System.out.println("ERROR EN SENTENCIA DE IMPRESION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba una cadena de caracteres");}
                break;
                case 127:
//#line 210 "gramatica.y"
                {System.out.println("Linea: " + Analizador_Lexico.cantLineas + " Sentencia FOR");}
                break;
                case 128:
//#line 211 "gramatica.y"
                {System.out.println("ERROR EN SENTENCIA FOR. Linea: " + Analizador_Lexico.cantLineas + " falta encabezado");}
                break;
                case 129:
//#line 212 "gramatica.y"
                {System.out.println("ERROR EN SENTENCIA FOR. Linea: " + Analizador_Lexico.cantLineas + " se esperaba un identificador");}
                break;
                case 130:
//#line 213 "gramatica.y"
                {System.out.println("ERROR EN SENTENCIA FOR. Linea: " + Analizador_Lexico.cantLineas + " falta la palabra reservada IN");}
                break;
                case 131:
//#line 214 "gramatica.y"
                {System.out.println("ERROR EN SENTENCIA FOR. Linea: " + Analizador_Lexico.cantLineas + " falta la palabra reservada RANGE");}
                break;
                case 132:
//#line 215 "gramatica.y"
                {System.out.println("ERROR EN SENTENCIA FOR. Linea: " + Analizador_Lexico.cantLineas + " no se puede definir un FOR sin cuerpo");}
                break;
                case 133:
//#line 218 "gramatica.y"
                {System.out.println("Linea: " + Analizador_Lexico.cantLineas + " ENCABEZADO FOR");}
                break;
                case 134:
//#line 219 "gramatica.y"
                {System.out.println("ERROR EN ENCABEZADO FOR. Linea: " + Analizador_Lexico.cantLineas + " falta una constante");}
                break;
                case 135:
//#line 220 "gramatica.y"
                {System.out.println("ERROR EN ENCABEZADO FOR. Linea: " + Analizador_Lexico.cantLineas + " faltan constantes");}
                break;
//#line 1138 "Parser.java"
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
