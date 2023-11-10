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
import GeneracionCodigoIntermedio.*;
import java.util.ArrayList;
import java.util.HashMap;
//#line 23 "Parser.java"




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
            0,    0,    0,    0,    1,    1,    2,    2,    2,    5,
            5,    6,    6,    3,    3,    3,    3,    3,    7,    7,
            7,    7,   12,   12,   12,   14,   13,   13,    8,    8,
            15,   15,   15,   15,   15,   16,   16,   17,   17,   18,
            18,   18,    9,    9,   19,   19,   19,   20,   20,   21,
            21,   22,   22,   22,   22,   10,   10,   10,   23,   23,
            24,   24,   11,   11,   25,   26,   26,   27,   27,   27,
            27,    4,    4,    4,    4,    4,    4,    4,    4,    4,
            4,    4,    4,    4,    4,    4,   28,   28,   28,   28,
            28,   28,   28,   28,   28,   28,   28,   28,   28,   28,
            28,   28,   33,   33,   33,   35,   35,   35,   36,   36,
            37,   37,   29,   29,   30,   30,   38,   38,   40,   40,
            40,   40,   40,   40,   39,   39,   41,   41,   43,   43,
            42,   42,   31,   31,   32,   32,   34,   34,   34,   44,
            44,   44,   44,   45,   45,   45,   46,
    };
    final static short yylen[] = {                            2,
            3,    2,    2,    1,    2,    1,    1,    1,    2,    2,
            1,    2,    1,    1,    1,    1,    1,    1,    3,    3,
            3,    3,    1,    1,    1,    1,    1,    3,    3,    3,
            4,    6,    5,    5,    6,    3,    3,    2,    1,    1,
            1,    1,    3,    3,    2,    4,    4,    3,    3,    2,
            1,    1,    1,    2,    2,    4,    4,    4,    3,    3,
            3,    3,    3,    3,    2,    3,    3,    2,    3,    1,
            2,    1,    2,    2,    2,    2,    2,    2,    5,    5,
            4,    4,    2,    2,    2,    2,    4,    4,    4,    4,
            6,    7,    6,    7,    3,    4,    3,    4,    4,    4,
            4,    4,    3,    3,    1,    3,    3,    1,    1,    1,
            1,    2,    4,    3,    4,    3,    3,    3,    3,    3,
            3,    3,    3,    3,    2,    1,    3,    3,    3,    3,
            4,    4,    2,    2,    3,    3,    3,    3,    3,    4,
            4,    3,    3,    7,    6,    4,    3,
    };
    final static short yydefred[] = {                         0,
            0,    0,    0,    0,    0,    0,   23,   24,   25,    0,
            0,    0,    0,    0,    0,    0,    6,    7,    8,   14,
            15,   16,   17,   18,    0,    0,    0,    0,    0,    0,
            72,    0,    0,    0,    0,    0,    0,    9,    0,    0,
            0,    0,    0,    0,    0,  134,  133,    0,    0,    0,
            0,    0,    0,   65,   86,   85,    0,    3,    5,   27,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,   74,   73,   76,   75,   78,   77,    0,    0,    0,
            0,    0,   84,   83,    0,    0,    0,    0,  109,  111,
            0,    0,    0,    0,  108,  110,   95,  114,    0,    0,
            135,    0,    0,    0,    0,    0,    0,  126,    0,    0,
            0,    0,  142,    0,   60,   59,    1,   21,   19,    0,
            22,   20,    0,   42,   40,   41,    0,   39,   30,   29,
            0,    0,   52,   53,    0,    0,   51,   44,   43,    0,
            0,    0,    0,    0,    0,    0,   64,   63,    0,    0,
            97,    0,    0,    0,  136,    0,   13,    0,    0,  139,
            137,  138,   96,  112,  100,   88,    0,    0,    0,    0,
            113,   99,   87,  118,    0,    0,    0,    0,    0,    0,
            117,    0,    0,  115,    0,  125,   47,   46,    0,   31,
            0,    0,  140,  141,   28,   37,   36,   38,   54,   49,
            55,   48,   50,   62,   61,   58,   56,   57,   67,   68,
            66,    0,   98,  102,   90,   82,    0,   81,    0,    0,
            101,   89,  147,   12,    0,    0,    0,    0,  106,  107,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,   34,   69,    0,    0,   80,    0,   79,    0,    0,
            146,    0,    0,   35,   32,   93,   91,    0,    0,    0,
            0,  132,  131,   94,   92,    0,  145,  144,
    };
    final static short yydgoto[] = {                         15,
            16,   17,   18,   19,    0,  158,   20,   21,   22,   23,
            24,   25,   61,   26,   27,   64,  127,  128,   28,   66,
            136,  137,   29,   69,   30,   71,  146,   31,   32,   33,
            34,   35,   93,   36,   94,   95,   96,   45,  106,  104,
            107,  186,  108,   37,   87,   88,
    };
    final static short yysindex[] = {                       -82,
            81,  -12,   90, -200, -117, -113,    0,    0,    0, -136,
            -156, -111,   38, -161,    0,   54,    0,    0,    0,    0,
            0,    0,    0,    0,  -99,  -99,   53,   57,   -8,   71,
            0,   39,   40,   41,   -4,   42,  -13,    0,   22,  -87,
            10,  -34,  -85,   13,   74,    0,    0,  -74,  -39, -178,
            -70,  -45,  -44,    0,    0,    0,   87,    0,    0,    0,
            -21,    3,  120,   43, -100,   44, -105,   93,  174,  -25,
            45,    0,    0,    0,    0,    0,    0,   24,  -37,   20,
            -34,   12,    0,    0, -137,  -28,  -79,  -30,    0,    0,
            35,   -2,   27,   86,    0,    0,    0,    0,  100,   30,
            0,   59,  -27,  180,  -61,   31,   55,    0, -104,  -35,
            56,   62,    0,   69,    0,    0,    0,    0,    0,   67,
            0,    0,   72,    0,    0,    0,  136,    0,    0,    0,
            291,   89,    0,    0,    8,  -62,    0,    0,    0,  101,
            212,   46,  102,  105,  298,   11,    0,    0,  108,   33,
            0,  -22,  106,   36,    0,  -12,    0,  149,   68,    0,
            0,    0,    0,    0,    0,    0,  -34,  -34,  -34,  -34,
            0,    0,    0,    0,  -34,  -34,  -34,  -34,  -34,  -34,
            0,  116,  159,    0,  226,    0,    0,    0,  332,    0,
            117,  334,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,  335,    0,    0,    0,    0,  -34,    0,  -34,   -1,
            0,    0,    0,    0,  -28,  125,   86,   86,    0,    0,
            92,   92,   92,   92,   92,   92,    0,    0,  160,  126,
            343,    0,    0,  287,  296,    0,  -34,    0,  -34,   83,
            0,  134,  187,    0,    0,    0,    0,  323,  326,  -28,
            135,    0,    0,    0,    0,  351,    0,    0,
    };
    final static short yyrindex[] = {                         0,
            0,  137,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,  397,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,  277,    0,    0,
            0,    0,    0,    0,    0,    0,  408,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,  -41,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,   47,    0,    0,    0,    0,
            0,    6,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            137,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,   23,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,   25,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,  -36,  -31,    0,    0,
            369,  372,  373,  374,  380,  384,    1,    4,    0,    0,
            -7,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,
    };
    final static short yygindex[] = {                         0,
            412,   37,    0,   34,    0,  -75,  300,  271,    0,    0,
            0,   49,  404,    0,   52,    0,    0,  304,    0,    0,
            0,  297,    0,  364,    0,    0,    0,    0,    0,    0,
            0,    0,  129,    0,   -5,  -14,  -71,    0,    0,    0,
            0,    0,    0,    0,    0,  350,
    };
    final static int YYTABLESIZE=462;
    static short yytable[];
    static { yytable();}
    static void yytable(){
        yytable = new short[]{                        105,
                110,  105,  105,  105,  103,  190,  103,  103,  103,  104,
                92,  104,  104,  104,  159,  167,   92,  168,  105,  140,
                105,  218,  119,  103,  132,  103,   86,   41,  104,  183,
                104,   40,  179,   43,  180,   80,   33,  120,  219,   79,
                14,   82,  248,   85,  130,  143,  122,  129,   42,   68,
                98,  201,   59,  102,   92,   46,   81,   92,   47,  249,
                152,  120,  202,  182,   92,   91,   92,  149,   92,  167,
                166,  168,  167,  173,  168,  167,  215,  168,  167,  222,
                168,   56,   73,   75,   77,   84,  130,  139,  148,  207,
                116,  112,  113,   59,    1,    2,  124,  111,    3,  144,
                52,    4,    5,    6,    7,    8,    9,   10,  226,   85,
                11,   12,   53,   13,   67,   33,  135,   33,  157,  156,
                50,  145,    3,  261,   38,    4,  225,  169,  143,   44,
                63,   10,  170,   51,  167,  211,  168,   13,  157,   48,
                171,  260,  167,   49,  168,   54,  220,   70,  167,   71,
                168,  187,  188,  250,  229,  230,  131,   60,  191,    6,
                124,  227,  228,  253,    6,    7,    8,    9,   97,   99,
                100,  101,  103,    1,    2,   63,  160,    3,   58,   65,
                4,    5,    6,    7,    8,    9,   10,  135,  266,   11,
                12,  224,   13,   70,  131,  156,  105,  212,    3,  109,
                114,    4,    6,    7,    8,    9,  150,   10,  153,  154,
                115,  117,  116,   13,  105,   67,  224,  143,  151,  103,
                181,  189,   89,   90,  104,  162,    7,    8,    9,   90,
                7,    8,    9,  216,  118,  105,  105,  105,  105,    6,
                103,  103,  103,  103,  123,  104,  104,  104,  104,  175,
                176,  177,  178,  217,  246,  164,  130,   33,  121,  129,
                197,  128,  130,   39,  127,  129,   89,   90,  155,   89,
                90,   78,  157,  223,  247,    6,   89,   90,   89,   90,
                89,   90,  165,  238,  252,  172,  224,   70,  214,   71,
                163,  221,  184,   55,   72,   74,   76,   83,  129,  138,
                147,  206,  116,  231,  232,  233,  234,  235,  236,    1,
                2,  263,  192,    3,  174,  185,    4,    5,    6,    7,
                8,    9,   10,  195,  194,   11,   12,  196,   13,  167,
                256,  168,  193,  126,  199,  134,  205,  141,  167,  257,
                168,  210,    1,    2,  200,  244,    3,  245,  239,    4,
                5,    6,    7,    8,    9,   10,  204,  208,   11,   12,
                209,   13,  125,  213,  133,  167,  264,  168,  167,  265,
                168,  237,  240,  241,  242,  258,    2,  259,  243,    3,
                251,  254,    4,  255,    6,    7,    8,    9,   10,  262,
                267,  268,    2,   26,   13,    3,    4,  126,    4,   45,
                6,    7,    8,    9,   10,  156,  134,    2,    3,  119,
                13,    4,  120,  123,  124,  156,  156,   10,    3,    3,
                121,    4,    4,   13,  122,   57,  125,   10,   10,   62,
                198,  142,  203,   13,   13,  133,  161,    0,    0,    0,
                0,    0,    0,  156,    0,    0,    3,    0,    0,    4,
                0,    0,    0,    0,    0,   10,    0,    0,    0,    0,
                0,   13,
        };
    }
    static short yycheck[];
    static { yycheck(); }
    static void yycheck() {
        yycheck = new short[] {                         41,
                40,   43,   44,   45,   41,   41,   43,   44,   45,   41,
                45,   43,   44,   45,   86,   43,   45,   45,   60,  125,
                62,   44,   44,   60,  125,   62,   40,   40,   60,  105,
                62,   44,   60,   46,   62,   40,   44,   59,   61,   44,
                123,   46,   44,  123,   44,   40,   44,   44,   61,   58,
                41,   44,   16,   41,   45,  256,   61,   45,  259,   61,
                41,   59,  125,  125,   45,   44,   45,   44,   45,   43,
                44,   45,   43,   44,   45,   43,   44,   45,   43,   44,
                45,   44,   44,   44,   44,   44,   44,   44,   44,   44,
                44,  270,  271,   57,  256,  257,   63,   49,  260,  125,
                257,  263,  264,  265,  266,  267,  268,  269,   41,  123,
                272,  273,  269,  275,  123,  123,   65,  125,   85,  257,
                257,   70,  260,   41,   44,  263,   59,   42,  123,   40,
                123,  269,   47,  270,   43,  125,   45,  275,  105,  257,
                41,   59,   43,  257,   45,  257,   41,  125,   43,  125,
                45,  256,  257,  225,  169,  170,  257,  257,  110,  265,
                127,  167,  168,  239,  265,  266,  267,  268,  256,   41,
                42,  257,   44,  256,  257,  123,  256,  260,  125,  123,
                263,  264,  265,  266,  267,  268,  269,  136,  260,  272,
                273,  158,  275,  123,  257,  257,  123,  146,  260,  274,
                271,  263,  265,  266,  267,  268,   78,  269,   80,   81,
                256,  125,  257,  275,  256,  123,  183,   44,  256,  256,
                41,  257,  257,  258,  256,  256,  266,  267,  268,  258,
                266,  267,  268,  256,  256,  277,  278,  279,  280,  265,
                277,  278,  279,  280,  125,  277,  278,  279,  280,  277,
                278,  279,  280,  276,  256,  258,  256,  265,  256,  256,
                125,  261,  262,  276,  261,  262,  257,  258,  257,  257,
                258,  276,  239,  125,  276,  265,  257,  258,  257,  258,
                257,  258,  256,  125,  125,  256,  253,  265,  256,  265,
                256,  256,  262,  256,  256,  256,  256,  256,  256,  256,
                256,  256,  256,  175,  176,  177,  178,  179,  180,  256,
                257,  125,  257,  260,  256,  261,  263,  264,  265,  266,
                267,  268,  269,  257,  256,  272,  273,  256,  275,   43,
                44,   45,  271,   63,   44,   65,  125,   67,   43,   44,
                45,   44,  256,  257,  256,  217,  260,  219,  123,  263,
                264,  265,  266,  267,  268,  269,  256,  256,  272,  273,
                256,  275,   63,  256,   65,   43,   44,   45,   43,   44,
                45,  256,   41,  257,   41,  247,  257,  249,   44,  260,
                256,  256,  263,   41,  265,  266,  267,  268,  269,  256,
                256,   41,  257,  257,  275,  260,    0,  127,  263,  123,
                265,  266,  267,  268,  269,  257,  136,    0,  260,   41,
                275,  263,   41,   41,   41,  257,  257,  269,  260,  260,
                41,  263,  263,  275,   41,   14,  127,  269,  269,   26,
                127,   68,  136,  275,  275,  136,   87,   -1,   -1,   -1,
                -1,   -1,   -1,  257,   -1,   -1,  260,   -1,   -1,  263,
                -1,   -1,   -1,   -1,   -1,  269,   -1,   -1,   -1,   -1,
                -1,  275,
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
            "programa : bloque_sentencias",
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
            "declaracion_variables : tipoclase lista_variables ','",
            "declaracion_variables : tipo lista_variables error",
            "declaracion_variables : tipoclase lista_variables error",
            "tipo : SHORT",
            "tipo : ULONG",
            "tipo : DOUBLE",
            "tipoclase : ID",
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
            "declaracion_distribuida : encabezado_dec_dist ':' cuerpo_dec_dist ','",
            "declaracion_distribuida : encabezado_dec_dist cuerpo_dec_dist ',' error",
            "declaracion_distribuida : encabezado_dec_dist ':' cuerpo_dec_dist error",
            "encabezado_dec_dist : IMPL FOR ID",
            "encabezado_dec_dist : IMPL ID error",
            "cuerpo_dec_dist : '{' declaracion_funcion '}'",
            "cuerpo_dec_dist : '{' '}' error",
            "declaracion_interfaz : encabezado_interfaz cuerpo_interfaz ','",
            "declaracion_interfaz : encabezado_interfaz cuerpo_interfaz error",
            "encabezado_interfaz : INTERFACE ID",
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
            "asignacion : ref_clase '(' ')' '=' expresion ','",
            "asignacion : ref_clase '(' expresion ')' '=' expresion ','",
            "asignacion : ref_clase '(' ')' MENOS_IGUAL expresion ','",
            "asignacion : ref_clase '(' expresion ')' MENOS_IGUAL expresion ','",
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
            "seleccion : IF condicion cuerpo_if",
            "condicion : '(' comparacion ')'",
            "condicion : '(' ')' error",
            "comparacion : expresion MAYOR_IGUAL expresion",
            "comparacion : expresion MENOR_IGUAL expresion",
            "comparacion : expresion '<' expresion",
            "comparacion : expresion '>' expresion",
            "comparacion : expresion IGUAL expresion",
            "comparacion : expresion DISTINTO expresion",
            "cuerpo_if : cuerpo_then_else cuerpo_else",
            "cuerpo_if : cuerpo_then",
            "cuerpo_then_else : '{' bloque_ejecutable '}'",
            "cuerpo_then_else : '{' '}' error",
            "cuerpo_then : '{' bloque_ejecutable '}'",
            "cuerpo_then : '{' '}' error",
            "cuerpo_else : ELSE '{' bloque_ejecutable '}'",
            "cuerpo_else : ELSE '{' '}' error",
            "imprimir : PRINT CADENA",
            "imprimir : PRINT error",
            "ref_clase : ID '.' ID",
            "ref_clase : ref_clase '.' ID",
            "sentencia_control : encabezado_for condicion_for cuerpo_for",
            "sentencia_control : encabezado_for cuerpo_for error",
            "sentencia_control : encabezado_for condicion_for error",
            "encabezado_for : FOR ID IN RANGE",
            "encabezado_for : FOR IN RANGE error",
            "encabezado_for : FOR ID RANGE",
            "encabezado_for : FOR ID IN",
            "condicion_for : '(' constante ';' constante ';' constante ')'",
            "condicion_for : '(' constante ';' constante ')' error",
            "condicion_for : '(' constante ')' error",
            "cuerpo_for : '{' bloque_ejecutable '}'",
    };

    //#line 838 "gramatica.y"
    /* CODE SECTION */
    public String tipo = "";
    public Pila pilaAmbito = new Pila("main");
    public String claseRef = "";
    public String funcionImpl = "";
    public boolean isImpl = false;
    public String isDecl = "";
    public HashMap<String, Integer> variables = new HashMap<>();
    public Pila pilaTercetos = new Pila();
    public Pila pilaIndices = new Pila();

    public void chequeoRango(String cte){
        if (cte.contains("_s")){
            int cteint = Integer.parseInt(cte.substring(0, cte.length()-2));
            double min = Math.pow(-2,7);
            double max = Math.pow(2,7)-1;
            if ((cteint < min)||(cteint>max)){
                System.out.println("ERROR. Linea "+Analizador_Lexico.cantLineas+": Constante fuera de rango");
                GeneradorCod.cantErrores++;
            }
        } else if (cte.contains("_ul")) {
            long cteint_largo = Integer.parseInt(cte.substring(0, cte.length()-3));
            double min = 0;
            double max = Math.pow(2,32)-1;
            if ((cteint_largo < min)||(cteint_largo>max)){
                System.out.println("ERROR. Linea "+Analizador_Lexico.cantLineas+": Constante fuera de rango");
                GeneradorCod.cantErrores++;
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
                GeneradorCod.cantErrores++;
            }
        }
    }

    public boolean hereda(String cHija, String cPadre){
        String hereda_ch = Tabla_Simbolos.getAtributos(cHija+"#main").getHereda();
        if (hereda_ch.equals("")){
            return false;
        } else if (hereda_ch.equals(cPadre)){
            return true;
        } else {
            return hereda(hereda_ch,cPadre);
        }
    }

    public String concatenarAmbito(String lexema, ArrayList<Object> elements){
        String nuevoAmb = lexema;
        String delimitador = "#";
        for(Object o: elements){
            nuevoAmb = nuevoAmb.concat(delimitador);
            nuevoAmb = nuevoAmb.concat((String)o);
        }
        return nuevoAmb;
    }

    public boolean setAmbito(String lexema){
        return Tabla_Simbolos.modificarClave(lexema, concatenarAmbito(lexema, pilaAmbito.getElements()));
        //la clave en la tabla de simbolos sera el lexema + el ambito.
        //Ej: var1 --> var1#main#f1#f2 es una variable declarada en f2, quien esta definida en f1, y en general, dentro de main
        //Si la clave ya existia (variable ya definida en el ambito), devuelve false y no inserta
        //caso contrario, devuelve true habiendo insertado la nueva clave con ambito
    }

    public String isDeclarada(String variable, ArrayList<Object> ambito){
        //devuelve la entrada a la tabla de simbolos, en caso de estar declarada
        String out = concatenarAmbito(variable, ambito);
        if (ambito.isEmpty()){ //la variable no esta definida en ningun ambito
            return "";
        } else if (Tabla_Simbolos.existeSimbolo(out)) {
            return out; //la variable esta definida en alguno de los ambitos alcanzables
        } else {
            String amb = (String)ambito.remove(ambito.size()-1);
            // para cuando se usa un atributo de una clase padre
            AtributosLexema att = Tabla_Simbolos.getAtributos(amb+"#main");
            if ((att != null)&&(att.isUso("CLASE"))){
                String clasePadre = Tabla_Simbolos.getAtributos(amb+"#main").getHereda();
                if (!clasePadre.equals("")){
                    ambito.add(clasePadre);
                }
            }
            return isDeclarada(variable, ambito); //continua la busqueda en el ambito superior, quitando el ambito actual
        }
    }

    public String obtenerTerceto(String operacion, String operador, String lexOp1, String lexOp2, String tipoResultado){
        Terceto tConversion = Conversor.getTercetoConversion(operacion, lexOp1, lexOp2);
        if (tConversion!=null){
            String refTerceto = "[" + GeneradorCod.agregarTerceto(tConversion) + "]";
            if (Conversor.operandoConvertido.equals("1")){
                return "["+GeneradorCod.agregarTerceto(operador, refTerceto, lexOp2, tipoResultado) + "]";
            } else {
                return "["+GeneradorCod.agregarTerceto(operador, lexOp1, refTerceto, tipoResultado) + "]";
            }
        } else {
            return "["+GeneradorCod.agregarTerceto(operador, lexOp1, lexOp2, tipoResultado) + "]";
        }
    }

    public String compatibilidadTipos(String operacion, String operador, String tipo_op1, String tipo_op2, String op1, String op2, String sval, Object obj){
        String tipoResultado = Conversor.getTipo(tipo_op1,tipo_op2,operacion); //en el sval esta el tipo
        if (tipoResultado.equals("error")){
            System.out.println("ERROR DE INCOMPATIBILIDAD DE TIPOS. Linea: " + Analizador_Lexico.cantLineas + " no se puede operar entre "+tipo_op1+" y "+tipo_op2);
            GeneradorCod.cantErrores++;
        } else {
            sval = tipoResultado;
            return obtenerTerceto(operacion, operador, op1, op2, tipoResultado);
        }
        return "";
    }

    public int yylex(){
        int tok = Analizador_Lexico.yylex();
        yylval = new ParserVal(Analizador_Lexico.buffer);
        return tok;
    }
    public void yyerror(String s){}
    //#line 664 "Parser.java"
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
                case 1:
//#line 15 "gramatica.y"
                {
                    if (!variables.isEmpty()){
                        System.out.print("WARNING. Existen variables sin utilizar del lado derecho: ");
                        for (String lexema : variables.keySet()) {
                            System.out.print(lexema+" ");
                        }
                    }
                    System.out.println("");
                }
                break;
                case 2:
//#line 24 "gramatica.y"
                {System.out.println("ERROR EN PROGRAMA. Linea: " + Analizador_Lexico.cantLineas + " se esperaba '}'");}
                break;
                case 3:
//#line 25 "gramatica.y"
                {System.out.println("ERROR EN PROGRAMA. Linea: " + Analizador_Lexico.cantLineas + " se esperaba '{'");}
                break;
                case 4:
//#line 26 "gramatica.y"
                {System.out.println("ERROR EN PROGRAMA. Linea: " + Analizador_Lexico.cantLineas + " se esperaban llaves");}
                break;
                case 9:
//#line 35 "gramatica.y"
                {System.out.println("ERROR EN SENTENCIA. Linea: " + Analizador_Lexico.cantLineas);}
                break;
                case 19:
//#line 54 "gramatica.y"
                {
                    System.out.println("Linea: " + Analizador_Lexico.cantLineas + " Declaracion de VARIABLE/S de TIPO " + val_peek(2).sval);
                }
                break;
                case 20:
//#line 58 "gramatica.y"
                {
                    System.out.println("Linea: " + Analizador_Lexico.cantLineas + " Declaracion de VARIABLE/S de TIPO " + val_peek(2).sval);
                }
                break;
                case 21:
//#line 61 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DE VARIABLES. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 22:
//#line 62 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DE VARIABLES. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 23:
//#line 65 "gramatica.y"
                {tipo = val_peek(0).sval;}
                break;
                case 24:
//#line 66 "gramatica.y"
                {tipo = val_peek(0).sval;}
                break;
                case 25:
//#line 67 "gramatica.y"
                {tipo = val_peek(0).sval;}
                break;
                case 26:
//#line 70 "gramatica.y"
                {
                    if (Tabla_Simbolos.getAtributos(val_peek(0).sval+"#main").isUso("CLASE")){
                        tipo = val_peek(0).sval;
                        Tabla_Simbolos.borrarSimbolo(val_peek(0).sval);
                    } else {
                        System.out.println("ERROR EN DECLARACION DE VARIABLES. Linea: "+Analizador_Lexico.cantLineas+": "+val_peek(0).sval+" no es tipo CLASE");
                    }
                }
                break;
                case 27:
//#line 80 "gramatica.y"
                {
                    variables.put(concatenarAmbito(val_peek(0).sval,pilaAmbito.getElements()),0);
                    Tabla_Simbolos.getAtributos(val_peek(0).sval).setTipo(tipo);
                    Tabla_Simbolos.getAtributos(val_peek(0).sval).setUso("VARIABLE");
                    if (!isDeclarada(val_peek(0).sval,pilaAmbito.getElements()).equals("")){
                        System.out.println("ERROR. SOBREESCRITURA DE ATRIBUTO. Linea: " + Analizador_Lexico.cantLineas);
                    }
                    if (!setAmbito(val_peek(0).sval)){
                        /*setAmbito modifica la clave, concatenando el ambito. Si ya existia arroja error, y sino, la setea*/
                        System.out.println("ERROR. REDECLARACION DE NOMBRE. Linea: " + Analizador_Lexico.cantLineas);
                    }
                }
                break;
                case 28:
//#line 93 "gramatica.y"
                {
                    variables.put(concatenarAmbito(val_peek(0).sval,pilaAmbito.getElements()),0);
                    Tabla_Simbolos.getAtributos(val_peek(0).sval).setTipo(tipo);
                    Tabla_Simbolos.getAtributos(val_peek(0).sval).setUso("VARIABLE");
                    if (!isDeclarada(val_peek(0).sval,pilaAmbito.getElements()).equals("")){
                        System.out.println("ERROR. SOBREESCRITURA DE ATRIBUTO. Linea: " + Analizador_Lexico.cantLineas);
                    }
                    if (!setAmbito(val_peek(0).sval)){
                        System.out.println("ERROR. REDECLARACION DE NOMBRE. Linea: " + Analizador_Lexico.cantLineas);
                    }

                }
                break;
                case 29:
//#line 108 "gramatica.y"
                {
                    String am = (String) pilaAmbito.desapilar();/*sino queda f1#main#f1, y necesito f1#main para los atributos*/
                    AtributosLexema att = Tabla_Simbolos.getAtributos(concatenarAmbito(am, pilaAmbito.getElements()));
                    isImpl = att.isImplementado();
                    att.setImplementado(true);
                    GeneradorCod.borrarFlag();
                    /*pone en True el booleano isImplementado para las funciones. */
                    /*Sirve para saber si:*/
                    /*--> una clase implemento la funcion o solo declaro el encabezado. */
                    /*--> la funcion puede usarse (tiene que tener cuerpo)*/
                    /*--> una clase que implementa una interfaz, implemento todos los metodos*/
                }
                break;
                case 30:
//#line 120 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DE FUNCION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 31:
//#line 125 "gramatica.y"
                {
                    /*poner ambito a IDfuncion, apilar nuevo ambito*/
                    System.out.println("Linea: " + Analizador_Lexico.cantLineas + " Declaracion funcion VOID " + val_peek(2).sval);
                    Tabla_Simbolos.getAtributos(val_peek(2).sval).setUso("FUNCION");
                    funcionImpl = val_peek(2).sval;
                    isDecl = isDeclarada(val_peek(2).sval,pilaAmbito.getElements());
                    if (!setAmbito(val_peek(2).sval)){/*si ya estaba declarado entra al if*/
                        if (Tabla_Simbolos.getAtributos(concatenarAmbito(val_peek(2).sval, pilaAmbito.getElements())).isImplementado()){
                            System.out.println("ERROR. REDECLARACION DE NOMBRE. Linea: " + Analizador_Lexico.cantLineas);
                        }
                    }
                    GeneradorCod.setFlagFuncion(concatenarAmbito(val_peek(2).sval,pilaAmbito.getElements()));
                    pilaAmbito.apilar(val_peek(2).sval);
                }
                break;
                case 32:
//#line 140 "gramatica.y"
                {
                    System.out.println("Linea: " + Analizador_Lexico.cantLineas + " Declaracion funcion VOID " + val_peek(4).sval);
                    Tabla_Simbolos.getAtributos(val_peek(4).sval).setUso("FUNCION");
                    Tabla_Simbolos.getAtributos(val_peek(4).sval).setParametro(concatenarAmbito(val_peek(1).sval,pilaAmbito.getElements()));
                    Tabla_Simbolos.getAtributos(val_peek(4).sval).setTipoParametro(val_peek(2).sval);
                    Tabla_Simbolos.getAtributos(val_peek(1).sval).setUso("PARAMETRO");
                    Tabla_Simbolos.getAtributos(val_peek(1).sval).setTipo(val_peek(2).sval);
                    funcionImpl = val_peek(4).sval;
                    isDecl = isDeclarada(val_peek(4).sval,pilaAmbito.getElements());
                    if (!setAmbito(val_peek(4).sval)){/*si ya estaba declarado entra al if*/
                        if (Tabla_Simbolos.getAtributos(concatenarAmbito(val_peek(4).sval, pilaAmbito.getElements())).isImplementado()){
                            System.out.println("ERROR. REDECLARACION DE NOMBRE. Linea: " + Analizador_Lexico.cantLineas);
                        }
                    }
                    GeneradorCod.setFlagFuncion(concatenarAmbito(val_peek(4).sval,pilaAmbito.getElements()));
                    pilaAmbito.apilar(val_peek(4).sval);
                    setAmbito(val_peek(1).sval); /*para el parametro formal*/
                }
                break;
                case 33:
//#line 158 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DE FUNCION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ')'");}
                break;
                case 34:
//#line 159 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DE FUNCION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba '('");}
                break;
                case 35:
//#line 160 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DE FUNCION. Linea: " + Analizador_Lexico.cantLineas + " falta el tipo de " + val_peek(2).sval);}
                break;
                case 37:
//#line 165 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DE FUNCION. Linea: " + Analizador_Lexico.cantLineas + " no se puede declarar una funcion sin cuerpo");}
                break;
                case 43:
//#line 177 "gramatica.y"
                {pilaAmbito.desapilar();}
                break;
                case 44:
//#line 178 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DE CLASE. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 45:
//#line 182 "gramatica.y"
                {
                    System.out.println("Linea: " + Analizador_Lexico.cantLineas + " Declaracion CLASE " + val_peek(0).sval);
                    Tabla_Simbolos.getAtributos(val_peek(0).sval).setUso("CLASE");
                    if (!setAmbito(val_peek(0).sval)){
                        System.out.println("ERROR. REDECLARACION DE NOMBRE. Linea: " + Analizador_Lexico.cantLineas);
                    };
                    pilaAmbito.apilar(val_peek(0).sval);

                }
                break;
                case 46:
//#line 192 "gramatica.y"
                {
                    System.out.println("Linea: " + Analizador_Lexico.cantLineas + " Declaracion CLASE " + val_peek(2).sval);
                    if (Tabla_Simbolos.getAtributos(val_peek(0).sval+"#main").isUso("INTERFAZ")){
                        Tabla_Simbolos.getAtributos(val_peek(2).sval).setUso("CLASE");
                        Tabla_Simbolos.getAtributos(val_peek(2).sval).setImplementa(val_peek(0).sval);
                        if (!setAmbito(val_peek(2).sval)){
                            System.out.println("ERROR. REDECLARACION DE NOMBRE. Linea: " + Analizador_Lexico.cantLineas);
                        };
                        pilaAmbito.apilar(val_peek(2).sval);
                    } else {
                        System.out.println("ERROR EN DECLARACION DE CLASE. Linea "+Analizador_Lexico.cantLineas+": "+val_peek(0).sval+" no es un INTERFACE");
                    }
                    /*se borra ID de la tabla de simbolos porque el lexico lo inserta al reconocer un identificador, */
                    /*y como el original tiene el nombre cambiado por el ambito, existiran ambos en la TS*/
                    Tabla_Simbolos.borrarSimbolo(val_peek(0).sval);
                }
                break;
                case 47:
//#line 208 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DE CLASE. Linea: " + Analizador_Lexico.cantLineas + " falta el identificador de la interfaz");}
                break;
                case 49:
//#line 212 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DE CLASE. Linea: " + Analizador_Lexico.cantLineas + " no se puede definir una clase sin cuerpo");}
                break;
                case 54:
//#line 222 "gramatica.y"
                {
                    if (Tabla_Simbolos.getAtributos(val_peek(1).sval+"#main").isUso("CLASE")){
                        Tabla_Simbolos.getAtributos((String)pilaAmbito.getTope()+"#main").setHereda(val_peek(1).sval);
                        Tabla_Simbolos.borrarSimbolo(val_peek(1).sval);
                        /*si ID es una clase, entonces la clase donde se define esta linea debe heredar de ID.*/
                    }
                }
                break;
                case 55:
//#line 230 "gramatica.y"
                {
                    pilaAmbito.desapilar();
                }
                break;
                case 56:
//#line 236 "gramatica.y"
                {
                    AtributosLexema att = Tabla_Simbolos.getAtributos(concatenarAmbito(funcionImpl,pilaAmbito.getElements()));
                    if (!isDecl.equals("")){
                        if (isImpl){
                            System.out.println("ERROR EN DECLARACION DISTRIBUIDA. FUNCION YA IMPLEMENTADA. Linea: " + Analizador_Lexico.cantLineas);
                        } else {
                            att.setImplementado(true);
                        }
                    } else {
                        Tabla_Simbolos.borrarSimbolo(concatenarAmbito(funcionImpl,pilaAmbito.getElements()));
                        System.out.println("ERROR EN DECLARACION DISTRIBUIDA. FUNCION NO DECLARADA. Linea: " + Analizador_Lexico.cantLineas);
                    }
                    /*se borra ID de la tabla de simbolos porque el lexico lo inserta al reconocer un identificador, */
                    /*y como el original tiene el nombre cambiado por el ambito, existiran ambos en la TS*/
                    Tabla_Simbolos.borrarSimbolo(funcionImpl);
                }
                break;
                case 57:
//#line 252 "gramatica.y"
                {System.out.println(". Linea: " + Analizador_Lexico.cantLineas + " se esperaba ':'");}
                break;
                case 58:
//#line 253 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DISTRIBUIDA. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 59:
//#line 257 "gramatica.y"
                {
                    System.out.println("Linea: " + Analizador_Lexico.cantLineas + " Declaracion DISTRIBUIDA para " + val_peek(0).sval);
                    AtributosLexema atributos = Tabla_Simbolos.getAtributos(val_peek(0).sval+"#main");
                    if ((atributos != null) && (atributos.isUso("CLASE"))){
                        pilaAmbito.apilar(val_peek(0).sval);
                    } else {
                        System.out.println("ERROR EN DECLARACION DISTRIBUIDA. Linea: " + Analizador_Lexico.cantLineas + " " + val_peek(0).sval + " no es una clase ");
                    }
                    Tabla_Simbolos.borrarSimbolo(val_peek(0).sval);
                }
                break;
                case 60:
//#line 267 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DISTRIBUIDA. Linea: " + Analizador_Lexico.cantLineas + " falta palabra reservada FOR");}
                break;
                case 62:
//#line 271 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DISTRIBUIDA. Linea: " + Analizador_Lexico.cantLineas + " no se puede definir una declaracion distribuida sin cuerpo");}
                break;
                case 63:
//#line 274 "gramatica.y"
                {pilaAmbito.desapilar();}
                break;
                case 64:
//#line 275 "gramatica.y"
                {System.out.println("ERROR EN INTERFAZ. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 65:
//#line 279 "gramatica.y"
                {
                    System.out.println("Linea: " + Analizador_Lexico.cantLineas + " Declaracion INTERFAZ " + val_peek(0).sval);
                    Tabla_Simbolos.getAtributos(val_peek(0).sval).setUso("INTERFAZ");
                    if (!setAmbito(val_peek(0).sval)){
                        System.out.println("ERROR. REDECLARACION DE NOMBRE. Linea: " + Analizador_Lexico.cantLineas);
                    };
                    pilaAmbito.apilar(val_peek(0).sval);
                }
                break;
                case 67:
//#line 290 "gramatica.y"
                {System.out.println("ERROR EN INTERFAZ. Linea: " + Analizador_Lexico.cantLineas + " no se puede declarar una interfaz sin metodos");}
                break;
                case 68:
//#line 293 "gramatica.y"
                {pilaAmbito.desapilar();}
                break;
                case 69:
//#line 294 "gramatica.y"
                {pilaAmbito.desapilar();}
                break;
                case 70:
//#line 295 "gramatica.y"
                {System.out.println("ERROR EN METODO DE INTERFAZ. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 71:
//#line 296 "gramatica.y"
                {System.out.println("ERROR EN METODO DE INTERFAZ. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 72:
//#line 299 "gramatica.y"
                {System.out.println("Linea: " + Analizador_Lexico.cantLineas + " ASIGNACION");}
                break;
                case 73:
//#line 300 "gramatica.y"
                {System.out.println("Linea: " + Analizador_Lexico.cantLineas + " INVOCACION FUNCION");}
                break;
                case 74:
//#line 301 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN INVOCACION A LA FUNCION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 75:
//#line 302 "gramatica.y"
                {System.out.println("Linea: " + Analizador_Lexico.cantLineas + " Sentencia IF");}
                break;
                case 76:
//#line 303 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN SENTENCIA IF. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 77:
//#line 304 "gramatica.y"
                {System.out.println("Linea: " + Analizador_Lexico.cantLineas + " SENTENCIA DE IMPRESION");}
                break;
                case 78:
//#line 305 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN SENTENCIA DE IMPRESION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 79:
//#line 307 "gramatica.y"
                {
                    System.out.println("Linea: " + Analizador_Lexico.cantLineas + " REFERENCIA A CLASE");
                    if (!val_peek(4).sval.equals("")){
                        AtributosLexema att = Tabla_Simbolos.getAtributos(val_peek(4).sval);
                        if (!att.tieneParametro()){
                            GeneradorCod.cantErrores++;
                            System.out.println("ERROR EN REFERENCIA A CLASE. Linea: " + Analizador_Lexico.cantLineas + " el numero de parametros no coincide");
                        } else{
                            if (!att.coincideTipoParametro(val_peek(2).sval)){
                                GeneradorCod.cantErrores++;
                                System.out.println("ERROR EN REFERENCIA A CLASE. Linea: " + Analizador_Lexico.cantLineas + " el tipo de parametro no coincide");
                            } else {
                                GeneradorCod.agregarTerceto("=",att.getParametro(),(String) val_peek(2).obj); /*realizaria el copia valor*/
                                GeneradorCod.agregarTerceto("CALL", val_peek(4).sval);
                            }
                        }
                    }
                }
                break;
                case 80:
//#line 325 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN REFERENCIA A CLASE. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 81:
//#line 327 "gramatica.y"
                {
                    System.out.println("Linea: " + Analizador_Lexico.cantLineas + " REFERENCIA A CLASE");
                    if (!val_peek(3).sval.equals("")){
                        AtributosLexema att = Tabla_Simbolos.getAtributos(val_peek(3).sval);
                        if (att.tieneParametro()){
                            GeneradorCod.cantErrores++;
                            System.out.println("ERROR EN REFERENCIA A CLASE. Linea: " + Analizador_Lexico.cantLineas + " el numero de parametros no coincide");
                        } else {
                            GeneradorCod.agregarTerceto("CALL", val_peek(3).sval);
                        }
                    }
                }
                break;
                case 82:
//#line 339 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN REFERENCIA A CLASE. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 84:
//#line 341 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN SENTENCIA FOR. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 85:
//#line 342 "gramatica.y"
                {GeneradorCod.agregarTerceto("RETURN","");}
                break;
                case 86:
//#line 343 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN RETURN. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 87:
//#line 347 "gramatica.y"
                {
                    String var = isDeclarada(val_peek(3).sval, pilaAmbito.getElements());
                    if (var.equals("")){
                        System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " variable "+val_peek(3).sval+" no declarada.");
                        GeneradorCod.cantErrores++;
                    } else {
                        if (!Tabla_Simbolos.getAtributos(var).isUso("VARIABLE")){
                            System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas +val_peek(3).sval+" no es una variable.");
                            GeneradorCod.cantErrores++;
                        } else {
                            String tID = Tabla_Simbolos.getAtributos(var).getTipo();
                            String tipoResultado = Conversor.getTipo(tID,val_peek(1).sval,"a");
                            if (tipoResultado.equals("error")){
                                System.out.println("ERROR DE INCOMPATIBILIDAD DE TIPOS. Linea: " + Analizador_Lexico.cantLineas + " no se puede asignar "+val_peek(1).sval+" a "+tID);
                                GeneradorCod.cantErrores++;
                            } else {
                                yyval.sval = tipoResultado;
                                String exp = (String) val_peek(1).obj;
                                String refTerceto;
                                String t;
                                if (exp.contains("[")){
                                    t = "["+GeneradorCod.agregarTerceto("=", var, exp, tipoResultado) + "]";
                                } else {
                                    Terceto tConv = Conversor.getTercetoConversion("a", var, exp);
                                    if (tConv != null){
                                        refTerceto = "["+GeneradorCod.agregarTerceto(tConv) + "]";
                                        t = "["+GeneradorCod.agregarTerceto("=", var, refTerceto, tipoResultado) + "]";
                                    } else {
                                        t = "["+GeneradorCod.agregarTerceto("=", var, exp, tipoResultado) + "]";
                                    }
                                }
                                yyval.obj = t;
                            }
                        }
                    }
                    Tabla_Simbolos.borrarSimbolo(val_peek(3).sval);
                }
                break;
                case 88:
//#line 385 "gramatica.y"
                {
                    String var = isDeclarada(val_peek(3).sval, pilaAmbito.getElements());
                    if (var.equals("")){
                        System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " variable "+val_peek(3).sval+" no declarada.");
                        GeneradorCod.cantErrores++;
                    } else {
                        if (!Tabla_Simbolos.getAtributos(var).isUso("VARIABLE")){
                            System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas +val_peek(3).sval+" no es una variable.");
                            GeneradorCod.cantErrores++;
                        } else {
                            String tID = Tabla_Simbolos.getAtributos(var).getTipo();
                            String tipoResultado = Conversor.getTipo(tID,val_peek(1).sval,"a");/*SEPARAR EN PRIMERO OP y DESPUES ASIG*/
                            if (tipoResultado.equals("error")){
                                System.out.println("ERROR DE INCOMPATIBILIDAD DE TIPOS. Linea: " + Analizador_Lexico.cantLineas + " no se puede asignar "+val_peek(1).sval+" a "+tID);
                                GeneradorCod.cantErrores++;
                            } else {
                                yyval.sval = tipoResultado;
                                String exp = (String) val_peek(1).obj;
                                String refTerceto;
                                if (exp.contains("[")){
                                    refTerceto = "[" +GeneradorCod.agregarTerceto("-", var, exp, tipoResultado)+"]";
                                } else {
                                    refTerceto = obtenerTerceto("o", "-", var, (String) val_peek(1).obj, tipoResultado);
                                }
                                String t = "["+GeneradorCod.agregarTerceto("=", var, refTerceto, tipoResultado) + "]";
                                yyval.obj = t;
                            }
                        }
                    }
                    Tabla_Simbolos.borrarSimbolo(val_peek(3).sval);
                }
                break;
                case 89:
//#line 417 "gramatica.y"
                {
                    if (!claseRef.equals("")){
                        /*esta asignando a una clase*/
                        System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " no se puede realizar una asignacion a una clase");
                        GeneradorCod.cantErrores++;
                    } else {
                        if (!Tabla_Simbolos.getAtributos(val_peek(3).sval).isUso("VARIABLE")){
                            System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas +val_peek(3).sval+" no es una variable.");
                            GeneradorCod.cantErrores++;
                        } else {
                            String tID = Tabla_Simbolos.getAtributos(val_peek(3).sval).getTipo();
                            String tipoResultado = Conversor.getTipo(tID,val_peek(1).sval,"a");/*SEPARAR EN PRIMERO OP y DESPUES ASIG*/
                            if (tipoResultado.equals("error")){
                                System.out.println("ERROR DE INCOMPATIBILIDAD DE TIPOS. Linea: " + Analizador_Lexico.cantLineas + " no se puede asignar "+val_peek(1).sval+" a "+tID);
                                GeneradorCod.cantErrores++;
                            } else {
                                yyval.sval = tipoResultado;
                                String exp = (String) val_peek(1).obj;
                                String refTerceto;
                                String t;
                                if (exp.contains("[")){
                                    t = "["+GeneradorCod.agregarTerceto("=", val_peek(3).sval, exp, tipoResultado) + "]";
                                } else {
                                    Terceto tConv = Conversor.getTercetoConversion("a", val_peek(3).sval, exp);
                                    if (tConv != null){
                                        refTerceto = "["+GeneradorCod.agregarTerceto(tConv) + "]";
                                        t = "["+GeneradorCod.agregarTerceto("=", val_peek(3).sval, refTerceto, tipoResultado) + "]";
                                    } else {
                                        t = "["+GeneradorCod.agregarTerceto("=", val_peek(3).sval, exp, tipoResultado) + "]";
                                    }
                                }
                                yyval.obj = t;
                            }
                        }
                    }
                }
                break;
                case 90:
//#line 454 "gramatica.y"
                {
                    if (!claseRef.equals("")){
                        /*esta asignando a una clase*/
                        System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " no se puede realizar una asignacion a una clase");
                        GeneradorCod.cantErrores++;
                    } else {
                        if (!Tabla_Simbolos.getAtributos(val_peek(3).sval).isUso("VARIABLE")){
                            System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas +" "+val_peek(3).sval+" no es una variable.");
                            GeneradorCod.cantErrores++;
                        } else {
                            String tID = Tabla_Simbolos.getAtributos(val_peek(3).sval).getTipo();
                            String tipoResultado = Conversor.getTipo(tID,val_peek(1).sval,"a");/*SEPARAR EN PRIMERO OP y DESPUES ASIG*/
                            if (tipoResultado.equals("error")){
                                System.out.println("ERROR DE INCOMPATIBILIDAD DE TIPOS. Linea: " + Analizador_Lexico.cantLineas + " no se puede asignar "+val_peek(1).sval+" a "+tID);
                                GeneradorCod.cantErrores++;
                            } else {
                                yyval.sval = tipoResultado;
                                String exp = (String) val_peek(1).obj;
                                String refTerceto;
                                if (exp.contains("[")){
                                    refTerceto = "[" +GeneradorCod.agregarTerceto("-", val_peek(3).sval, exp, tipoResultado)+"]";
                                } else {
                                    refTerceto = obtenerTerceto("o", "-", val_peek(3).sval, (String) val_peek(1).obj, tipoResultado);
                                }
                                String t = "["+GeneradorCod.agregarTerceto("=", val_peek(3).sval, refTerceto, tipoResultado) + "]";
                                yyval.obj = t;
                            }
                        }
                    }
                }
                break;
                case 91:
//#line 484 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " no se puede realizar una asignacion a una funcion");}
                break;
                case 92:
//#line 485 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " no se puede realizar una asignacion a una funcion");}
                break;
                case 93:
//#line 486 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " no se puede realizar una asignacion a una funcion");}
                break;
                case 94:
//#line 487 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " no se puede realizar una asignacion a una funcion");}
                break;
                case 95:
//#line 488 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " Se esperaba expresion");}
                break;
                case 96:
//#line 489 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " Se esperaba expresion");}
                break;
                case 97:
//#line 490 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " Se esperaba expresion");}
                break;
                case 98:
//#line 491 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " Se esperaba expresion");}
                break;
                case 99:
//#line 492 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 100:
//#line 493 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 101:
//#line 494 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 102:
//#line 495 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 103:
//#line 499 "gramatica.y"
                {
                    System.out.println("Linea: " + Analizador_Lexico.cantLineas + " SUMA");
                    yyval.obj = compatibilidadTipos("o", "+", val_peek(2).sval, val_peek(0).sval, (String) val_peek(2).obj, (String) val_peek(0).obj, yyval.sval, yyval.obj);
                }
                break;
                case 104:
//#line 504 "gramatica.y"
                {
                    System.out.println("Linea: " + Analizador_Lexico.cantLineas + " RESTA");
                    yyval.obj = compatibilidadTipos("o", "-", val_peek(2).sval, val_peek(0).sval, (String) val_peek(2).obj, (String) val_peek(0).obj, yyval.sval, yyval.obj);
                }
                break;
                case 105:
//#line 509 "gramatica.y"
                {
                    yyval.sval = val_peek(0).sval;
                    yyval.obj = val_peek(0).obj;
                }
                break;
                case 106:
//#line 516 "gramatica.y"
                {
                    System.out.println("Linea: " + Analizador_Lexico.cantLineas + " MULTIPLICACION");
                    yyval.obj = compatibilidadTipos("o", "*", val_peek(2).sval, val_peek(0).sval, (String) val_peek(2).obj, (String) val_peek(0).obj, yyval.sval, yyval.obj);
                }
                break;
                case 107:
//#line 521 "gramatica.y"
                {
                    System.out.println("Linea: " + Analizador_Lexico.cantLineas + " DIVISION");
                    yyval.obj = compatibilidadTipos("o", "/", val_peek(2).sval, val_peek(0).sval, (String) val_peek(2).obj, (String) val_peek(0).obj, yyval.sval, yyval.obj);
                }
                break;
                case 108:
//#line 526 "gramatica.y"
                {
                    yyval.sval = val_peek(0).sval;
                    yyval.obj = val_peek(0).obj;
                }
                break;
                case 109:
//#line 533 "gramatica.y"
                {
                    String lexema = isDeclarada(val_peek(0).sval, pilaAmbito.getElements());

                    if (lexema.equals("")){
                        System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " variable "+val_peek(0).sval+" no declarada.");
                        GeneradorCod.cantErrores++;
                    } else {
                        yyval.sval=Tabla_Simbolos.getAtributos(lexema).getTipo();
                        yyval.obj = lexema;
                    }
                    variables.remove(lexema);
                    Tabla_Simbolos.borrarSimbolo(val_peek(0).sval);
                }
                break;
                case 110:
//#line 547 "gramatica.y"
                {
                    yyval.sval=val_peek(0).sval;
                    yyval.obj = val_peek(0).obj;
                }
                break;
                case 111:
//#line 553 "gramatica.y"
                {
                    chequeoRango(val_peek(0).sval);
                    Tabla_Simbolos.getAtributos(val_peek(0).sval).sumarUso();
                    yyval.sval=Tabla_Simbolos.getAtributos(val_peek(0).sval).getTipo();
                    yyval.obj = val_peek(0).sval;
                }
                break;
                case 112:
//#line 559 "gramatica.y"
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
                    yyval.sval=Tabla_Simbolos.getAtributos("-"+val_peek(0).sval).getTipo();
                    String l = "-"+val_peek(0).sval;
                    yyval.obj = l;
                }
                break;
                case 113:
//#line 576 "gramatica.y"
                {
                    String fun = isDeclarada(val_peek(3).sval, pilaAmbito.getElements());
                    if (fun.equals("")){
                        System.out.println("ERROR EN INVOCACION A FUNCION. Linea: " + Analizador_Lexico.cantLineas + " la funcion "+val_peek(3).sval+" no esta declarada.");
                        GeneradorCod.cantErrores++;
                    } else {
                        AtributosLexema at = Tabla_Simbolos.getAtributos(fun);
                        if (at.isUso("FUNCION")){
                            /*CHEQUEO DE CANTIDAD de PARAMETROS*/
                            if (at.tieneParametro()){
                                if (at.coincideTipoParametro(val_peek(1).sval)){
                                    /*la ref a terceto esta en obj*/
                                    GeneradorCod.agregarTerceto("=",Tabla_Simbolos.getAtributos(fun).getParametro(),(String) val_peek(1).obj); /*realizaria el copia valor*/
                                    GeneradorCod.agregarTerceto("CALL", fun);
                                } else {
                                    System.out.println("ERROR EN INVOCACION A FUNCION. Linea: " + Analizador_Lexico.cantLineas + " no coincide el tipo del parametro.");
                                    GeneradorCod.cantErrores++;
                                }
                            } else {
                                System.out.println("ERROR EN INVOCACION A FUNCION. Linea: " + Analizador_Lexico.cantLineas + " no coincide la cantidad de parametros.");
                                GeneradorCod.cantErrores++;
                            }
                        } else {
                            System.out.println("ERROR EN INVOCACION A FUNCION. Linea: " + Analizador_Lexico.cantLineas +val_peek(3).sval+" no es una funcion");
                            GeneradorCod.cantErrores++;
                        }
                    }
                    Tabla_Simbolos.borrarSimbolo(val_peek(3).sval);
                }
                break;
                case 114:
//#line 606 "gramatica.y"
                {
                    String fun = isDeclarada(val_peek(2).sval, pilaAmbito.getElements());
                    if (fun.equals("")){
                        System.out.println("ERROR EN INVOCACION A FUNCION. Linea: " + Analizador_Lexico.cantLineas + " la funcion "+val_peek(2).sval+" no esta declarada.");
                        GeneradorCod.cantErrores++;
                    } else {
                        AtributosLexema at = Tabla_Simbolos.getAtributos(fun);
                        if (at.isUso("FUNCION")){
                            /*CHEQUEO DE CANTIDAD de PARAMETROS*/
                            if (at.tieneParametro()){
                                System.out.println("ERROR EN INVOCACION A FUNCION. Linea: " + Analizador_Lexico.cantLineas + " no coincide la cantidad de parametros.");
                                GeneradorCod.cantErrores++;
                            } else {
                                GeneradorCod.agregarTerceto("CALL", fun);
                            }
                        } else {
                            System.out.println("ERROR EN INVOCACION A FUNCION. Linea: " + Analizador_Lexico.cantLineas +val_peek(2).sval+" no es una funcion.");
                            GeneradorCod.cantErrores++;
                        }
                    }
                    Tabla_Simbolos.borrarSimbolo(val_peek(2).sval);
                }
                break;
                case 116:
//#line 631 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN SENTENCIA IF. Linea: " + Analizador_Lexico.cantLineas +" se esperaba END_IF");}
                break;
                case 117:
//#line 635 "gramatica.y"
                {
                    Terceto t = new Terceto("BF", "");
                    pilaTercetos.apilar(t);
                    GeneradorCod.agregarTerceto(t);
                }
                break;
                case 118:
//#line 640 "gramatica.y"
                {GeneradorCod.cantErrores++;  System.out.println("ERROR EN SENTENCIA IF. Linea: " + Analizador_Lexico.cantLineas + " falta condicion");}
                break;
                case 119:
//#line 643 "gramatica.y"
                {compatibilidadTipos("o", ">=", val_peek(2).sval, val_peek(0).sval, (String) val_peek(2).obj, (String) val_peek(0).obj, yyval.sval, yyval.obj);}
                break;
                case 120:
//#line 644 "gramatica.y"
                {compatibilidadTipos("o", ">=", val_peek(2).sval, val_peek(0).sval, (String) val_peek(2).obj, (String) val_peek(0).obj, yyval.sval, yyval.obj);}
                break;
                case 121:
//#line 645 "gramatica.y"
                {compatibilidadTipos("o", "<", val_peek(2).sval, val_peek(0).sval, (String) val_peek(2).obj, (String) val_peek(0).obj, yyval.sval, yyval.obj);}
                break;
                case 122:
//#line 646 "gramatica.y"
                {compatibilidadTipos("o", ">", val_peek(2).sval, val_peek(0).sval, (String) val_peek(2).obj, (String) val_peek(0).obj, yyval.sval, yyval.obj);}
                break;
                case 123:
//#line 647 "gramatica.y"
                {compatibilidadTipos("o", "==", val_peek(2).sval, val_peek(0).sval, (String) val_peek(2).obj, (String) val_peek(0).obj, yyval.sval, yyval.obj);}
                break;
                case 124:
//#line 648 "gramatica.y"
                {compatibilidadTipos("o", "!!", val_peek(2).sval, val_peek(0).sval, (String) val_peek(2).obj, (String) val_peek(0).obj, yyval.sval, yyval.obj);}
                break;
                case 125:
//#line 651 "gramatica.y"
                {GeneradorCod.agregarTercetoLabel();}
                break;
                case 127:
//#line 656 "gramatica.y"
                {
                    /*obtener el nro de terceto a completar*/
                    Terceto bf = (Terceto) pilaTercetos.desapilar();
                    /*ponerle en operando 1, el nro de terceto actual +2*/
                    bf.setOperando_1("["+ String.valueOf(GeneradorCod.getIndexActual()+2)+"]");
                    /*generar terceto BI con operando1 incompleto*/
                    Terceto bi = new Terceto("BI", "");
                    /*apilar nro de terceto BI*/
                    pilaTercetos.apilar(bi);
                    GeneradorCod.agregarTerceto(bi);
                    GeneradorCod.agregarTercetoLabel();

                }
                break;
                case 128:
//#line 669 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN SENTENCIA IF. Linea: " + Analizador_Lexico.cantLineas + " cuerpo de IF vacio");}
                break;
                case 129:
//#line 673 "gramatica.y"
                {
                    /*obtener el nro de terceto a completar*/
                    Terceto bf = (Terceto) pilaTercetos.desapilar();
                    /*ponerle en operando 1, el nro de terceto actual +2*/
                    bf.setOperando_1("["+ String.valueOf(GeneradorCod.getIndexActual()+1)+"]");
                    GeneradorCod.agregarTercetoLabel();
                }
                break;
                case 130:
//#line 680 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN SENTENCIA IF. Linea: " + Analizador_Lexico.cantLineas + " cuerpo de IF vacio");}
                break;
                case 131:
//#line 684 "gramatica.y"
                {
                    /*obtener nro de terceto de BI*/
                    Terceto bi = (Terceto) pilaTercetos.desapilar();
                    /*completar con nro terceto actual +1*/
                    bi.setOperando_1("["+String.valueOf(GeneradorCod.getIndexActual()+1)+"]");
                }
                break;
                case 132:
//#line 690 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN SENTENCIA IF. Linea: " + Analizador_Lexico.cantLineas + " cuerpo de ELSE vacio");}
                break;
                case 133:
//#line 694 "gramatica.y"
                {
                    String cad = val_peek(0).sval;
                    cad = cad.substring(1, cad.length()-1);
                    GeneradorCod.agregarTerceto("PRINT", cad);
                }
                break;
                case 134:
//#line 699 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN SENTENCIA DE IMPRESION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba una cadena de caracteres");}
                break;
                case 135:
//#line 703 "gramatica.y"
                {
                    claseRef = "";
                    String id1 = isDeclarada(val_peek(2).sval, pilaAmbito.getElements());
                    String id2 = isDeclarada(val_peek(0).sval, pilaAmbito.getElements());
                    yyval.sval = id2;
                    if (!id1.equals("")){
                        String nombreClase = Tabla_Simbolos.getAtributos(id1).getTipo();
                        AtributosLexema atributos = Tabla_Simbolos.getAtributos(nombreClase+"#main");
                        if ((atributos != null) && (atributos.isUso("CLASE"))){
                            if (!id2.equals("")){
                                /*es una clase*/
                                if (hereda(nombreClase, val_peek(0).sval)){
                                    claseRef = val_peek(0).sval;
                                } else {
                                    System.out.println("ERROR EN REFERENCIA A CLASE. Linea: " + Analizador_Lexico.cantLineas +" "+nombreClase+" no hereda de "+val_peek(0).sval);
                                }
                            } else {
                                if (!Tabla_Simbolos.existeSimbolo(val_peek(0).sval + "#main#"+nombreClase)){
                                    System.out.println("ERROR EN REFERENCIA A CLASE. Linea: " + Analizador_Lexico.cantLineas +" "+val_peek(0).sval+" no esta declarado dentro de "+val_peek(2).sval);
                                } else {
                                    yyval.sval = val_peek(0).sval + "#main#" + nombreClase;
                                }
                            }
                        } else {
                            System.out.println("ERROR EN REFERENCIA A CLASE. Linea: " + Analizador_Lexico.cantLineas +" "+val_peek(2).sval+" no es de tipo clase");
                        }
                    } else {
                        System.out.println("ERROR EN REFERENCIA A CLASE. Linea: " + Analizador_Lexico.cantLineas + " variable "+val_peek(2).sval+" no declarada.");
                    }
                    Tabla_Simbolos.borrarSimbolo(val_peek(2).sval);
                    Tabla_Simbolos.borrarSimbolo(val_peek(0).sval);
                }
                break;
                case 136:
//#line 736 "gramatica.y"
                {
                    String id2 = isDeclarada(val_peek(0).sval, pilaAmbito.getElements());
                    yyval.sval = id2;
                    if (!id2.equals("")){
                        System.out.println("ERROR EN REFERENCIA A CLASE. Linea: " + Analizador_Lexico.cantLineas + " " + val_peek(0).sval + " no es un metodo o atributo de " + claseRef);
                    } else {
                        String lexema = val_peek(0).sval + "#main#" +claseRef;
                        yyval.sval = lexema;
                        if (!Tabla_Simbolos.existeSimbolo(lexema)){
                            System.out.println("ERROR EN REFERENCIA A CLASE. Linea: " + Analizador_Lexico.cantLineas + " " + val_peek(0).sval + " no esta declarado dentro de " + claseRef);
                        }
                        claseRef = "";
                    }
                    Tabla_Simbolos.borrarSimbolo(val_peek(0).sval);
                }
                break;
                case 137:
//#line 754 "gramatica.y"
                {
                    Terceto incr = (Terceto) pilaIndices.desapilar();
                    Terceto bf1 = (Terceto) pilaTercetos.desapilar(); /*me da el terceto de bif incompleto*/
                    if (incr!=null && bf1 != null){
                        String indx = "[" + GeneradorCod.agregarTerceto(incr)+"]";
                        GeneradorCod.agregarTerceto("=", incr.getOperando_1(), indx, incr.getTipo());
                        /*hasta aca, puse el incremento y asignacion*/


                        String inicial = (String) pilaTercetos.desapilar(); /*me da el terceto indice inicial*/

                        GeneradorCod.agregarTerceto("BI", inicial);
                        bf1.setOperando_1("["+String.valueOf(GeneradorCod.getIndexActual()+1)+"]");
                        GeneradorCod.agregarTercetoLabel();
                    }
                }
                break;
                case 138:
//#line 770 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN SENTENCIA FOR. Linea: " + Analizador_Lexico.cantLineas + " falta condicion");}
                break;
                case 139:
//#line 771 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN SENTENCIA FOR. Linea: " + Analizador_Lexico.cantLineas + " no se puede definir un FOR sin cuerpo");}
                break;
                case 140:
//#line 775 "gramatica.y"
                {
                    System.out.println("Linea: " + Analizador_Lexico.cantLineas + " Sentencia FOR");
                    String id = isDeclarada(val_peek(2).sval, pilaAmbito.getElements());
                    if (id.equals("")){
                        System.out.println("ERROR EN SENTENCIA FOR. Linea: " + Analizador_Lexico.cantLineas + " " + val_peek(2).sval + " no esta declarada");
                        GeneradorCod.cantErrores++;
                    } else {
                        if (Tabla_Simbolos.getAtributos(id).isTipo("DOUBLE")){
                            GeneradorCod.cantErrores++;
                            System.out.println("ERROR EN INDICE FOR. Linea: " + Analizador_Lexico.cantLineas + " el indice debe ser de tipo ULONG o SHORT");
                        } else {
                            pilaIndices.apilar(id);
                        }
                    }
                    Tabla_Simbolos.borrarSimbolo(val_peek(2).sval);
                }
                break;
                case 141:
//#line 791 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN SENTENCIA FOR. Linea: " + Analizador_Lexico.cantLineas + " se esperaba un identificador");}
                break;
                case 142:
//#line 792 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN SENTENCIA FOR. Linea: " + Analizador_Lexico.cantLineas + " falta la palabra reservada IN");}
                break;
                case 143:
//#line 793 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN SENTENCIA FOR. Linea: " + Analizador_Lexico.cantLineas + " falta la palabra reservada RANGE");}
                break;
                case 144:
//#line 797 "gramatica.y"
                {
                    System.out.println("Linea: " + Analizador_Lexico.cantLineas + " ENCABEZADO FOR");
                    String c1 = val_peek(5).sval;
                    String c2 = val_peek(3).sval;
                    String c3 = val_peek(1).sval;
                    String index = (String) pilaIndices.desapilar();
                    if (index!=null){
                        String tipo = Tabla_Simbolos.getAtributos(index).getTipo();
                        if (!c1.equals(tipo) || !c2.equals(tipo) || !c3.equals(tipo)){
                            GeneradorCod.cantErrores++;
                            System.out.println("ERROR EN CONDICION FOR. Linea: " + Analizador_Lexico.cantLineas + " las constantes deben ser del mismo tipo que el indice");
                        } else {

                            String incremento = (String) val_peek(1).obj;
                            GeneradorCod.agregarTerceto("=", index, (String) val_peek(5).obj, tipo); /*inicializacion index*/
                            GeneradorCod.agregarTercetoLabel();
                            if (incremento.contains("-")){
                                /*es negativo*/
                                GeneradorCod.agregarTerceto(">", index, (String) val_peek(3).obj); /*condicion for*/
                            } else {
                                GeneradorCod.agregarTerceto("<", index, (String) val_peek(3).obj); /*condicion for*/
                            }
                            pilaTercetos.apilar("[" + GeneradorCod.getIndexActual()+"]"); /*apila el numero de terceto inicial*/

                            Terceto incr = new Terceto("+", index, incremento, tipo);
                            pilaIndices.apilar(incr);

                            Terceto t = new Terceto("BF", "");
                            pilaTercetos.apilar(t);  /*apila primer bifurcacion incompleta*/
                            GeneradorCod.agregarTerceto(t); /*agrea la bif incompleta a los tercetos*/
                        }
                    }
                }
                break;
                case 145:
//#line 830 "gramatica.y"
                {GeneradorCod.cantErrores++;System.out.println("ERROR EN ENCABEZADO FOR. Linea: " + Analizador_Lexico.cantLineas + " falta una constante");}
                break;
                case 146:
//#line 831 "gramatica.y"
                {GeneradorCod.cantErrores++;System.out.println("ERROR EN ENCABEZADO FOR. Linea: " + Analizador_Lexico.cantLineas + " faltan constantes");}
                break;
//#line 1821 "Parser.java"
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
