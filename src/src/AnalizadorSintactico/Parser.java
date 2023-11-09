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
            37,   37,   29,   29,   30,   38,   38,   40,   40,   40,
            40,   40,   40,   39,   39,   41,   41,   42,   42,   31,
            31,   32,   32,   34,   34,   34,   43,   43,   43,   43,
            44,   44,   44,   45,
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
            3,    3,    3,    2,    1,    3,    3,    4,    4,    2,
            2,    3,    3,    3,    3,    3,    4,    4,    3,    3,
            7,    6,    4,    3,
    };
    final static short yydefred[] = {                         0,
            0,    0,    0,    0,    0,    0,   23,   24,   25,    0,
            0,    0,    0,    0,    0,    0,    6,    7,    8,   14,
            15,   16,   17,   18,    0,    0,    0,    0,    0,    0,
            72,    0,    0,    0,    0,    0,    0,    9,    0,    0,
            0,    0,    0,    0,    0,  131,  130,    0,    0,    0,
            0,    0,    0,   65,   86,   85,    0,    3,    5,   27,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,   74,   73,   76,   75,   78,   77,    0,    0,    0,
            0,    0,   84,   83,    0,    0,    0,    0,  109,  111,
            0,    0,    0,    0,  108,  110,   95,  114,    0,    0,
            132,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,  139,    0,   60,   59,    1,   21,   19,    0,   22,
            20,    0,   42,   40,   41,    0,   39,   30,   29,    0,
            0,   52,   53,    0,    0,   51,   44,   43,    0,    0,
            0,    0,    0,    0,    0,   64,   63,    0,    0,   97,
            0,    0,    0,  133,    0,   13,    0,    0,  136,  134,
            135,   96,  112,  100,   88,    0,    0,    0,    0,  113,
            99,   87,  117,    0,    0,    0,    0,    0,    0,  116,
            0,    0,  115,    0,  124,   47,   46,    0,   31,    0,
            0,  137,  138,   28,   37,   36,   38,   54,   49,   55,
            48,   50,   62,   61,   58,   56,   57,   67,   68,   66,
            0,   98,  102,   90,   82,    0,   81,    0,    0,  101,
            89,  144,   12,    0,    0,    0,    0,  106,  107,    0,
            0,    0,    0,    0,    0,  127,  126,    0,    0,    0,
            34,   69,    0,    0,   80,    0,   79,    0,    0,  143,
            0,    0,   35,   32,   93,   91,    0,    0,    0,    0,
            129,  128,   94,   92,    0,  142,  141,
    };
    final static short yydgoto[] = {                         15,
            16,   17,   18,   19,    0,  157,   20,   21,   22,   23,
            24,   25,   61,   26,   27,   64,  126,  127,   28,   66,
            135,  136,   29,   69,   30,   71,  145,   31,   32,   33,
            34,   35,   93,   36,   94,   95,   96,   45,  106,  104,
            107,  185,   37,   87,   88,
    };
    final static short yysindex[] = {                       -96,
            61,  -12,   81, -133, -203, -132,    0,    0,    0, -162,
            -159, -127,    1, -129,    0,   50,    0,    0,    0,    0,
            0,    0,    0,    0, -125, -125,   57,   62,   32,   63,
            0,   14,   15,   17,   -4,   34,  -10,    0,    8, -104,
            3,  -34, -103,    5,   77,    0,    0, -117,  -39, -122,
            -93,  -46,  -45,    0,    0,    0,   84,    0,    0,    0,
            -21,   18,  115,   35,   67,   36,  -84,   88,  169,  -78,
            37,    0,    0,    0,    0,    0,    0,   19,  -42,   10,
            -34,  -40,    0,    0, -163,  -28,  -98,  -37,    0,    0,
            27,   -2,   22,   60,    0,    0,    0,    0,   74,   25,
            0,   30,  -27,  175,  101,   26,   44, -115,  -35,   47,
            -50,    0,   33,    0,    0,    0,    0,    0,   51,    0,
            0,   53,    0,    0,    0,  134,    0,    0,    0,  267,
            56,    0,    0,  -24,  120,    0,    0,    0,   64,  201,
            38,   71,   72,  285,  -69,    0,    0,   75,   28,    0,
            -22,   79,   31,    0,  -12,    0,  155,   52,    0,    0,
            0,    0,    0,    0,    0,  -34,  -34,  -34,  -34,    0,
            0,    0,    0,  -34,  -34,  -34,  -34,  -34,  -34,    0,
            80,  157,    0,  207,    0,    0,    0,  297,    0,   86,
            304,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            302,    0,    0,    0,    0,  -34,    0,  -34,   -1,    0,
            0,    0,    0,  -28,   98,   60,   60,    0,    0,   58,
            58,   58,   58,   58,   58,    0,    0,  177,   99,  319,
            0,    0,  146,  150,    0,  -34,    0,  -34,   55,    0,
            106,  178,    0,    0,    0,    0,  160,  163,  -28,  107,
            0,    0,    0,    0,  324,    0,    0,
    };
    final static short yyrindex[] = {                         0,
            0,  109,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,  368,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,  248,    0,    0,
            0,    0,    0,    0,    0,    0,  374,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,  -41,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,  117,    0,    0,    0,
            43,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,  109,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    4,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            20,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,  -36,  -31,    0,    0,  348,
            351,  352,  354,  355,  363,    0,    0,    0,    0,   -7,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,
    };
    final static short yygindex[] = {                         0,
            391,   76,    0,  216,    0,  -20,   24,   21,    0,    0,
            0,   42,  380,    0,   39,    0,    0,  281,    0,    0,
            0,  273,    0,  342,    0,    0,    0,    0,    0,    0,
            0,    0,  121,    0,   16,   29,  -71,    0,    0,    0,
            0,    0,    0,    0,  326,
    };
    final static int YYTABLESIZE=468;
    static short yytable[];
    static { yytable();}
    static void yytable(){
        yytable = new short[]{                        105,
                109,  105,  105,  105,  103,  189,  103,  103,  103,  104,
                92,  104,  104,  104,  158,  166,   92,  167,  105,  200,
                105,  217,  118,  103,   85,  103,   14,   41,  104,   86,
                104,   40,  178,   43,  179,   80,   33,  119,  218,   79,
                139,   82,  247,   98,   56,  102,  143,   92,   42,   92,
                151,   91,   92,   48,   92,  210,   81,   73,   75,  248,
                77,  121,  148,   92,  166,  165,  167,  166,  172,  167,
                166,  214,  167,  166,  221,  167,  119,   84,  129,  138,
                147,  206,  140,  125,  182,  133,  124,  140,  132,   68,
                110,   59,  225,  155,   50,  260,    3,   52,   63,    4,
                166,  168,  167,  134,   38,   10,  169,   51,  144,   53,
                224,   13,   85,  259,  170,   33,  166,   33,  167,  219,
                44,  166,   46,  167,   49,   47,    1,    2,   70,   54,
                3,   60,   59,    4,    5,    6,    7,    8,    9,   10,
                186,  187,   11,   12,   71,   13,  125,  111,  112,  124,
                190,   97,  249,  101,   67,  133,  108,  159,  132,    1,
                2,   99,  100,    3,  103,  140,    4,    5,    6,    7,
                8,    9,   10,  134,   58,   11,   12,  113,   13,   63,
                6,  226,  227,  211,   65,   70,    6,  265,  166,  255,
                167,  131,  166,  256,  167,    6,  228,  229,  149,  105,
                152,  153,  166,  263,  167,  166,  264,  167,  116,  114,
                67,  115,  142,  150,  105,  180,  154,  252,  161,  103,
                192,  188,   89,   90,  104,  181,    7,    8,    9,   90,
                7,    8,    9,  215,  117,  105,  105,  105,  105,  122,
                103,  103,  103,  103,  201,  104,  104,  104,  104,  174,
                175,  176,  177,  216,  245,  163,   55,   33,  196,   89,
                90,   89,   90,   39,   89,   90,   89,   90,   70,   72,
                74,   78,   76,  120,  246,   89,   90,  164,  123,  222,
                171,  237,  162,  213,   71,  173,  220,  183,  193,   83,
                128,  137,  146,  205,  230,  231,  232,  233,  234,  235,
                156,  251,  262,  191,  184,    1,    2,  194,  195,    3,
                198,  199,    4,    5,    6,    7,    8,    9,   10,  203,
                156,   11,   12,  130,   13,  204,  207,  208,  209,  238,
                212,    6,    7,    8,    9,  236,  243,  239,  244,    1,
                2,  123,  240,    3,  241,  242,    4,    5,    6,    7,
                8,    9,   10,  250,  253,   11,   12,  155,   13,  254,
                3,  261,  266,    4,  267,   26,  257,    4,  258,   10,
                45,    2,  223,    2,    3,   13,  130,    4,  125,    6,
                7,    8,    9,   10,    6,    7,    8,    9,  118,   13,
                2,  119,  122,    3,  123,  120,    4,  223,    6,    7,
                8,    9,   10,  121,   57,   62,  197,  202,   13,  141,
                0,  155,  160,  155,    3,    0,    3,    4,    0,    4,
                0,    0,    0,   10,    0,   10,    0,    0,    0,   13,
                0,   13,    0,  155,  155,    0,    3,    3,    0,    4,
                4,    0,    0,    0,    0,   10,   10,    0,    0,    0,
                0,   13,   13,  156,    0,    0,    0,    0,    0,    0,
                0,    0,    0,    0,    0,    0,    0,  223,
        };
    }
    static short yycheck[];
    static { yycheck(); }
    static void yycheck() {
        yycheck = new short[] {                         41,
                40,   43,   44,   45,   41,   41,   43,   44,   45,   41,
                45,   43,   44,   45,   86,   43,   45,   45,   60,   44,
                62,   44,   44,   60,  123,   62,  123,   40,   60,   40,
                62,   44,   60,   46,   62,   40,   44,   59,   61,   44,
                125,   46,   44,   41,   44,   41,  125,   45,   61,   45,
                41,   44,   45,  257,   45,  125,   61,   44,   44,   61,
                44,   44,   44,   45,   43,   44,   45,   43,   44,   45,
                43,   44,   45,   43,   44,   45,   59,   44,   44,   44,
                44,   44,   40,   63,  105,   65,   63,   67,   65,   58,
                49,   16,   41,  257,  257,   41,  260,  257,  123,  263,
                43,   42,   45,   65,   44,  269,   47,  270,   70,  269,
                59,  275,  123,   59,   41,  123,   43,  125,   45,   41,
                40,   43,  256,   45,  257,  259,  256,  257,  125,  257,
                260,  257,   57,  263,  264,  265,  266,  267,  268,  269,
                256,  257,  272,  273,  125,  275,  126,  270,  271,  126,
                109,  256,  224,  257,  123,  135,  274,  256,  135,  256,
                257,   41,   42,  260,   44,  123,  263,  264,  265,  266,
                267,  268,  269,  135,  125,  272,  273,  271,  275,  123,
                265,  166,  167,  145,  123,  123,  265,  259,   43,   44,
                45,  125,   43,   44,   45,  265,  168,  169,   78,  123,
                80,   81,   43,   44,   45,   43,   44,   45,  125,  256,
                123,  257,   44,  256,  256,   41,  257,  238,  256,  256,
                271,  257,  257,  258,  256,  125,  266,  267,  268,  258,
                266,  267,  268,  256,  256,  277,  278,  279,  280,  125,
                277,  278,  279,  280,  125,  277,  278,  279,  280,  277,
                278,  279,  280,  276,  256,  258,  256,  265,  125,  257,
                258,  257,  258,  276,  257,  258,  257,  258,  265,  256,
                256,  276,  256,  256,  276,  257,  258,  256,   63,  125,
                256,  125,  256,  256,  265,  256,  256,  262,  256,  256,
                256,  256,  256,  256,  174,  175,  176,  177,  178,  179,
                85,  125,  125,  257,  261,  256,  257,  257,  256,  260,
                44,  256,  263,  264,  265,  266,  267,  268,  269,  256,
                105,  272,  273,  257,  275,  125,  256,  256,   44,  123,
                256,  265,  266,  267,  268,  256,  216,   41,  218,  256,
                257,  126,  257,  260,   41,   44,  263,  264,  265,  266,
                267,  268,  269,  256,  256,  272,  273,  257,  275,   41,
                260,  256,  256,  263,   41,  257,  246,    0,  248,  269,
                123,  257,  157,    0,  260,  275,  257,  263,  262,  265,
                266,  267,  268,  269,  265,  266,  267,  268,   41,  275,
                257,   41,   41,  260,   41,   41,  263,  182,  265,  266,
                267,  268,  269,   41,   14,   26,  126,  135,  275,   68,
                -1,  257,   87,  257,  260,   -1,  260,  263,   -1,  263,
                -1,   -1,   -1,  269,   -1,  269,   -1,   -1,   -1,  275,
                -1,  275,   -1,  257,  257,   -1,  260,  260,   -1,  263,
                263,   -1,   -1,   -1,   -1,  269,  269,   -1,   -1,   -1,
                -1,  275,  275,  238,   -1,   -1,   -1,   -1,   -1,   -1,
                -1,   -1,   -1,   -1,   -1,   -1,   -1,  252,
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
            long cteint_largo = Long.parseLong(cte.substring(0, cte.length()-3));
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

    public int yylex(){
        int tok = Analizador_Lexico.yylex();
        yylval = new ParserVal(Analizador_Lexico.buffer);
        return tok;
    }
    public void yyerror(String s){}
    //#line 649 "Parser.java"
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
                    } else {
                        System.out.println("ERROR EN DECLARACION DE VARIABLES. Linea: "+Analizador_Lexico.cantLineas+": "+val_peek(0).sval+" no es tipo CLASE");
                    }
                }
                break;
                case 27:
//#line 79 "gramatica.y"
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
//#line 92 "gramatica.y"
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
//#line 107 "gramatica.y"
                {
                    String am = (String) pilaAmbito.desapilar();/*sino queda f1#main#f1, y necesito f1#main para los atributos*/
                    AtributosLexema att = Tabla_Simbolos.getAtributos(concatenarAmbito(am, pilaAmbito.getElements()));
                    isImpl = att.isImplementado();
                    att.setImplementado(true);
                    /*pone en True el booleano isImplementado para las funciones. */
                    /*Sirve para saber si:*/
                    /*--> una clase implemento la funcion o solo declaro el encabezado. */
                    /*--> la funcion puede usarse (tiene que tener cuerpo)*/
                    /*--> una clase que implementa una interfaz, implemento todos los metodos*/
                }
                break;
                case 30:
//#line 118 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DE FUNCION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 31:
//#line 123 "gramatica.y"
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
                    pilaAmbito.apilar(val_peek(2).sval);
                }
                break;
                case 32:
//#line 137 "gramatica.y"
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
                    pilaAmbito.apilar(val_peek(4).sval);
                    setAmbito(val_peek(1).sval); /*para el parametro formal*/
                }
                break;
                case 33:
//#line 154 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DE FUNCION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ')'");}
                break;
                case 34:
//#line 155 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DE FUNCION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba '('");}
                break;
                case 35:
//#line 156 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DE FUNCION. Linea: " + Analizador_Lexico.cantLineas + " falta el tipo de " + val_peek(2).sval);}
                break;
                case 37:
//#line 161 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DE FUNCION. Linea: " + Analizador_Lexico.cantLineas + " no se puede declarar una funcion sin cuerpo");}
                break;
                case 43:
//#line 173 "gramatica.y"
                {pilaAmbito.desapilar();}
                break;
                case 44:
//#line 174 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DE CLASE. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 45:
//#line 178 "gramatica.y"
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
//#line 188 "gramatica.y"
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
//#line 204 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DE CLASE. Linea: " + Analizador_Lexico.cantLineas + " falta el identificador de la interfaz");}
                break;
                case 49:
//#line 208 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DE CLASE. Linea: " + Analizador_Lexico.cantLineas + " no se puede definir una clase sin cuerpo");}
                break;
                case 54:
//#line 218 "gramatica.y"
                {
                    if (Tabla_Simbolos.getAtributos(val_peek(1).sval+"#main").isUso("CLASE")){
                        Tabla_Simbolos.getAtributos((String)pilaAmbito.getTope()+"#main").setHereda(val_peek(1).sval);
                        Tabla_Simbolos.borrarSimbolo(val_peek(1).sval);
                        /*si ID es una clase, entonces la clase donde se define esta linea debe heredar de ID.*/
                    }
                }
                break;
                case 55:
//#line 226 "gramatica.y"
                {
                    pilaAmbito.desapilar();
                }
                break;
                case 56:
//#line 232 "gramatica.y"
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
                    /*Tabla_Simbolos.borrarSimbolo(funcionImpl); */
                }
                break;
                case 57:
//#line 248 "gramatica.y"
                {System.out.println(". Linea: " + Analizador_Lexico.cantLineas + " se esperaba ':'");}
                break;
                case 58:
//#line 249 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DISTRIBUIDA. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 59:
//#line 253 "gramatica.y"
                {
                    System.out.println("Linea: " + Analizador_Lexico.cantLineas + " Declaracion DISTRIBUIDA para " + val_peek(0).sval);
                    AtributosLexema atributos = Tabla_Simbolos.getAtributos(val_peek(0).sval+"#main");
                    if ((atributos != null) && (atributos.isUso("CLASE"))){
                        pilaAmbito.apilar(val_peek(0).sval);
                    } else {
                        System.out.println("ERROR EN DECLARACION DISTRIBUIDA. Linea: " + Analizador_Lexico.cantLineas + " " + val_peek(0).sval + " no es una clase ");
                    }
                }
                break;
                case 60:
//#line 262 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DISTRIBUIDA. Linea: " + Analizador_Lexico.cantLineas + " falta palabra reservada FOR");}
                break;
                case 62:
//#line 266 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DISTRIBUIDA. Linea: " + Analizador_Lexico.cantLineas + " no se puede definir una declaracion distribuida sin cuerpo");}
                break;
                case 63:
//#line 269 "gramatica.y"
                {pilaAmbito.desapilar();}
                break;
                case 64:
//#line 270 "gramatica.y"
                {System.out.println("ERROR EN INTERFAZ. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 65:
//#line 274 "gramatica.y"
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
//#line 285 "gramatica.y"
                {System.out.println("ERROR EN INTERFAZ. Linea: " + Analizador_Lexico.cantLineas + " no se puede declarar una interfaz sin metodos");}
                break;
                case 68:
//#line 288 "gramatica.y"
                {pilaAmbito.desapilar();}
                break;
                case 69:
//#line 289 "gramatica.y"
                {pilaAmbito.desapilar();}
                break;
                case 70:
//#line 290 "gramatica.y"
                {System.out.println("ERROR EN METODO DE INTERFAZ. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 71:
//#line 291 "gramatica.y"
                {System.out.println("ERROR EN METODO DE INTERFAZ. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 72:
//#line 294 "gramatica.y"
                {System.out.println("Linea: " + Analizador_Lexico.cantLineas + " ASIGNACION");}
                break;
                case 73:
//#line 295 "gramatica.y"
                {System.out.println("Linea: " + Analizador_Lexico.cantLineas + " INVOCACION FUNCION");}
                break;
                case 74:
//#line 296 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN INVOCACION A LA FUNCION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 75:
//#line 297 "gramatica.y"
                {System.out.println("Linea: " + Analizador_Lexico.cantLineas + " Sentencia IF");}
                break;
                case 76:
//#line 298 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN SENTENCIA IF. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 77:
//#line 299 "gramatica.y"
                {System.out.println("Linea: " + Analizador_Lexico.cantLineas + " SENTENCIA DE IMPRESION");}
                break;
                case 78:
//#line 300 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN SENTENCIA DE IMPRESION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 79:
//#line 302 "gramatica.y"
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
//#line 320 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN REFERENCIA A CLASE. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 81:
//#line 322 "gramatica.y"
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
//#line 334 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN REFERENCIA A CLASE. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 84:
//#line 336 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN SENTENCIA FOR. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 85:
//#line 337 "gramatica.y"
                {GeneradorCod.agregarTerceto("RETURN","");}
                break;
                case 86:
//#line 338 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN RETURN. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 87:
//#line 342 "gramatica.y"
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
                }
                break;
                case 88:
//#line 379 "gramatica.y"
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
                }
                break;
                case 89:
//#line 410 "gramatica.y"
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
//#line 447 "gramatica.y"
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
//#line 477 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " no se puede realizar una asignacion a una funcion");}
                break;
                case 92:
//#line 478 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " no se puede realizar una asignacion a una funcion");}
                break;
                case 93:
//#line 479 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " no se puede realizar una asignacion a una funcion");}
                break;
                case 94:
//#line 480 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " no se puede realizar una asignacion a una funcion");}
                break;
                case 95:
//#line 481 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " Se esperaba expresion");}
                break;
                case 96:
//#line 482 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " Se esperaba expresion");}
                break;
                case 97:
//#line 483 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " Se esperaba expresion");}
                break;
                case 98:
//#line 484 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " Se esperaba expresion");}
                break;
                case 99:
//#line 485 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 100:
//#line 486 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 101:
//#line 487 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 102:
//#line 488 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 103:
//#line 492 "gramatica.y"
                {
                    System.out.println("Linea: " + Analizador_Lexico.cantLineas + " SUMA");
                    String tipoResultado = Conversor.getTipo(val_peek(2).sval,val_peek(0).sval,"o");
                    if (tipoResultado.equals("error")){
                        System.out.println("ERROR DE INCOMPATIBILIDAD DE TIPOS. Linea: " + Analizador_Lexico.cantLineas + " no se puede sumar entre "+val_peek(2).sval+" y "+val_peek(0).sval);
                        GeneradorCod.cantErrores++;
                    } else {
                        yyval.sval = tipoResultado;
                        yyval.obj = obtenerTerceto("o", "+", (String) val_peek(2).obj, (String) val_peek(0).obj, tipoResultado);
                    }
                }
                break;
                case 104:
//#line 504 "gramatica.y"
                {
                    System.out.println("Linea: " + Analizador_Lexico.cantLineas + " RESTA");
                    String tipoResultado = Conversor.getTipo(val_peek(2).sval,val_peek(0).sval,"o");
                    if (tipoResultado.equals("error")){
                        System.out.println("ERROR DE INCOMPATIBILIDAD DE TIPOS. Linea: " + Analizador_Lexico.cantLineas + " no se puede restar entre "+val_peek(2).sval+" y "+val_peek(0).sval);
                        GeneradorCod.cantErrores++;
                    } else {
                        yyval.sval = tipoResultado;
                        yyval.obj = obtenerTerceto("o", "-", (String) val_peek(2).obj, (String) val_peek(0).obj, tipoResultado);
                    }
                }
                break;
                case 105:
//#line 516 "gramatica.y"
                {
                    yyval.sval = val_peek(0).sval;
                    yyval.obj = val_peek(0).obj;
                }
                break;
                case 106:
//#line 523 "gramatica.y"
                {
                    System.out.println("Linea: " + Analizador_Lexico.cantLineas + " MULTIPLICACION");
                    String tipoResultado = Conversor.getTipo(val_peek(2).sval,val_peek(0).sval,"o"); /*en el sval esta el tipo*/
                    if (tipoResultado.equals("error")){
                        System.out.println("ERROR DE INCOMPATIBILIDAD DE TIPOS. Linea: " + Analizador_Lexico.cantLineas + " no se puede multiplicar entre "+val_peek(2).sval+" y "+val_peek(0).sval);
                        GeneradorCod.cantErrores++;
                    } else {
                        yyval.sval = tipoResultado;
                        yyval.obj = obtenerTerceto("o", "*", (String) val_peek(2).obj, (String) val_peek(0).obj, tipoResultado);
                    }
                }
                break;
                case 107:
//#line 535 "gramatica.y"
                {
                    System.out.println("Linea: " + Analizador_Lexico.cantLineas + " DIVISION");
                    String tipoResultado = Conversor.getTipo(val_peek(2).sval,val_peek(0).sval,"o");
                    if (tipoResultado.equals("error")){
                        System.out.println("ERROR DE INCOMPATIBILIDAD DE TIPOS. Linea: " + Analizador_Lexico.cantLineas + " no se puede dividir entre "+val_peek(2).sval+" y "+val_peek(0).sval);
                        GeneradorCod.cantErrores++;
                    } else {
                        yyval.sval = tipoResultado;
                        yyval.obj = obtenerTerceto("o", "/", (String) val_peek(2).obj, (String) val_peek(0).obj, tipoResultado);
                    }
                }
                break;
                case 108:
//#line 547 "gramatica.y"
                {
                    yyval.sval = val_peek(0).sval;
                    yyval.obj = val_peek(0).obj;
                }
                break;
                case 109:
//#line 554 "gramatica.y"
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
                }
                break;
                case 110:
//#line 567 "gramatica.y"
                {
                    yyval.sval=val_peek(0).sval;
                    yyval.obj = val_peek(0).obj;
                }
                break;
                case 111:
//#line 573 "gramatica.y"
                {
                    chequeoRango(val_peek(0).sval);
                    Tabla_Simbolos.getAtributos(val_peek(0).sval).sumarUso();
                    yyval.sval=Tabla_Simbolos.getAtributos(val_peek(0).sval).getTipo();
                    yyval.obj = val_peek(0).sval;
                }
                break;
                case 112:
//#line 579 "gramatica.y"
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
//#line 596 "gramatica.y"
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
                }
                break;
                case 114:
//#line 625 "gramatica.y"
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
                }
                break;
                case 116:
//#line 652 "gramatica.y"
                {
                    Terceto t = new Terceto("BF", "");
                    pilaTercetos.apilar(t);
                    GeneradorCod.agregarTerceto(t);
                }
                break;
                case 117:
//#line 657 "gramatica.y"
                {System.out.println("ERROR EN SENTENCIA IF. Linea: " + Analizador_Lexico.cantLineas + " falta condicion");}
                break;
                case 118:
//#line 660 "gramatica.y"
                {GeneradorCod.agregarTerceto(">=", (String) val_peek(2).obj, (String) val_peek(0).obj);}
                break;
                case 119:
//#line 661 "gramatica.y"
                {GeneradorCod.agregarTerceto("<=", (String) val_peek(2).obj, (String) val_peek(0).obj);}
                break;
                case 120:
//#line 662 "gramatica.y"
                {GeneradorCod.agregarTerceto("<", (String) val_peek(2).obj, (String) val_peek(0).obj);}
                break;
                case 121:
//#line 663 "gramatica.y"
                {GeneradorCod.agregarTerceto(">", (String) val_peek(2).obj, (String) val_peek(0).obj);}
                break;
                case 122:
//#line 664 "gramatica.y"
                {GeneradorCod.agregarTerceto("==", (String) val_peek(2).obj, (String) val_peek(0).obj);}
                break;
                case 123:
//#line 665 "gramatica.y"
                {GeneradorCod.agregarTerceto("!!", (String) val_peek(2).obj, (String) val_peek(0).obj);}
                break;
                case 126:
//#line 673 "gramatica.y"
                {
                    /*obtener el nro de terceto a completar*/
                    Terceto bf = (Terceto) pilaTercetos.desapilar();
                    /*ponerle en operando 1, el nro de terceto actual +2*/
                    bf.setOperando_1("["+ String.valueOf(GeneradorCod.getIndexActual()+2)+"]");
                    /*generar terceto BI con operando1 incompleto*/
                    Terceto t = new Terceto("BI", "");
                    /*apilar nro de terceto BI*/
                    pilaTercetos.apilar(t);
                    GeneradorCod.agregarTerceto(t);

                }
                break;
                case 127:
//#line 685 "gramatica.y"
                {System.out.println("ERROR EN SENTENCIA IF. Linea: " + Analizador_Lexico.cantLineas + " cuerpo de IF vacio");}
                break;
                case 128:
//#line 689 "gramatica.y"
                {
                    /*obtener nro de terceto de BI*/
                    Terceto bi = (Terceto) pilaTercetos.desapilar();
                    /*completar con nro terceto actual +1*/
                    bi.setOperando_1("["+String.valueOf(GeneradorCod.getIndexActual()+1)+"]");
                }
                break;
                case 129:
//#line 695 "gramatica.y"
                {System.out.println("ERROR EN SENTENCIA IF. Linea: " + Analizador_Lexico.cantLineas + " cuerpo de ELSE vacio");}
                break;
                case 130:
//#line 699 "gramatica.y"
                {
                    String cad = val_peek(0).sval;
                    cad = cad.substring(1,cad.length()-1);
                    GeneradorCod.agregarTerceto("PRINT", cad);
                }
                break;
                case 131:
//#line 704 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN SENTENCIA DE IMPRESION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba una cadena de caracteres");}
                break;
                case 132:
//#line 708 "gramatica.y"
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
                }
                break;
                case 133:
//#line 739 "gramatica.y"
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
                }
                break;
                case 134:
//#line 756 "gramatica.y"
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
                    }
                }
                break;
                case 135:
//#line 771 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN SENTENCIA FOR. Linea: " + Analizador_Lexico.cantLineas + " falta condicion");}
                break;
                case 136:
//#line 772 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN SENTENCIA FOR. Linea: " + Analizador_Lexico.cantLineas + " no se puede definir un FOR sin cuerpo");}
                break;
                case 137:
//#line 776 "gramatica.y"
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
                }
                break;
                case 138:
//#line 791 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN SENTENCIA FOR. Linea: " + Analizador_Lexico.cantLineas + " se esperaba un identificador");}
                break;
                case 139:
//#line 792 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN SENTENCIA FOR. Linea: " + Analizador_Lexico.cantLineas + " falta la palabra reservada IN");}
                break;
                case 140:
//#line 793 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN SENTENCIA FOR. Linea: " + Analizador_Lexico.cantLineas + " falta la palabra reservada RANGE");}
                break;
                case 141:
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
                case 142:
//#line 830 "gramatica.y"
                {GeneradorCod.cantErrores++;System.out.println("ERROR EN ENCABEZADO FOR. Linea: " + Analizador_Lexico.cantLineas + " falta una constante");}
                break;
                case 143:
//#line 831 "gramatica.y"
                {GeneradorCod.cantErrores++;System.out.println("ERROR EN ENCABEZADO FOR. Linea: " + Analizador_Lexico.cantLineas + " faltan constantes");}
                break;
//#line 1796 "Parser.java"
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
