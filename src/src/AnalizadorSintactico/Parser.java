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
            24,   24,   25,   25,   11,   11,   26,   27,   27,   28,
            28,   28,   28,    4,    4,    4,    4,    4,    4,    4,
            4,    4,    4,    4,    4,    4,    4,    4,   29,   29,
            29,   29,   29,   29,   29,   29,   29,   29,   29,   29,
            29,   29,   29,   29,   34,   34,   34,   36,   36,   36,
            37,   37,   38,   38,   30,   30,   31,   31,   31,   31,
            31,   39,   39,   41,   41,   41,   41,   41,   41,   40,
            40,   42,   42,   44,   44,   43,   43,   32,   32,   33,
            33,   35,   35,   35,   45,   45,   45,   45,   46,   46,
            46,   47,
    };
    final static short yylen[] = {                            2,
            3,    2,    2,    1,    2,    1,    1,    1,    2,    2,
            1,    2,    1,    1,    1,    1,    1,    1,    3,    3,
            3,    3,    1,    1,    1,    1,    1,    3,    3,    3,
            4,    6,    5,    5,    6,    3,    3,    2,    1,    1,
            1,    1,    3,    3,    2,    4,    4,    3,    3,    2,
            1,    1,    1,    2,    2,    4,    4,    4,    3,    3,
            3,    3,    2,    1,    3,    3,    2,    3,    3,    2,
            3,    1,    2,    1,    2,    2,    2,    2,    2,    2,
            5,    5,    4,    4,    2,    2,    2,    2,    4,    4,
            4,    4,    6,    7,    6,    7,    3,    4,    3,    4,
            4,    4,    4,    4,    3,    3,    1,    3,    3,    1,
            1,    1,    1,    2,    4,    3,    4,    4,    2,    3,
            3,    3,    3,    3,    3,    3,    3,    3,    3,    2,
            1,    3,    3,    3,    3,    4,    4,    2,    2,    3,
            3,    3,    3,    3,    4,    4,    3,    3,    7,    6,
            4,    3,
    };
    final static short yydefred[] = {                         0,
            0,    0,    0,    0,    0,    0,   23,   24,   25,    0,
            0,    0,    0,    0,    0,    0,    6,    7,    8,   14,
            15,   16,   17,   18,    0,    0,    0,    0,    0,    0,
            74,    0,    0,    0,    0,    0,    0,    9,    0,    0,
            0,    0,    0,  119,    0,    0,    0,    0,    0,  131,
            139,  138,    0,    0,    0,    0,    0,    0,   67,   88,
            87,    0,    3,    5,   27,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,   76,   75,   78,   77,
            80,   79,    0,    0,    0,    0,    0,   86,   85,    0,
            0,    0,    0,  111,  113,    0,    0,    0,    0,  110,
            112,   97,  116,    0,    0,  140,    0,    0,   13,    0,
            0,    0,    0,  120,    0,  121,    0,  130,    0,    0,
            0,    0,  147,    0,   60,   59,    1,   21,   19,    0,
            22,   20,    0,   42,   40,   41,    0,   39,   30,   29,
            0,    0,   52,   53,    0,    0,   51,   44,   43,    0,
            64,    0,    0,    0,    0,    0,    0,   66,   65,    0,
            0,   99,    0,    0,    0,  141,    0,    0,  144,  142,
            143,   98,  114,  102,   90,    0,    0,    0,    0,  115,
            101,   89,    0,    0,   12,  123,    0,    0,    0,    0,
            0,    0,  122,  118,  117,    0,   47,   46,    0,   31,
            0,    0,  145,  146,   28,   37,   36,   38,   54,   49,
            55,   48,   50,   62,   61,   63,   58,   56,   57,   69,
            70,   68,    0,  100,  104,   92,   84,    0,   83,    0,
            0,  103,   91,  152,    0,    0,    0,    0,  108,  109,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            34,   71,    0,    0,   82,    0,   81,    0,    0,  151,
            137,  136,   35,   32,   95,   93,    0,    0,    0,    0,
            96,   94,    0,  150,  149,
    };
    final static short yydgoto[] = {                         15,
            16,   17,   18,   19,    0,  110,   20,   21,   22,   23,
            24,   25,   66,   26,   27,   69,  137,  138,   28,   71,
            146,  147,   29,   74,  152,   30,   76,  157,   31,   32,
            33,   34,   35,   98,   36,   99,  100,  101,   47,   48,
            113,   49,  118,   50,   37,   92,   93,
    };
    final static short yysindex[] = {                       -59,
            52,  -12,   15, -112, -136, -132,    0,    0,    0, -130,
            -156, -125,    1, -158,    0,   47,    0,    0,    0,    0,
            0,    0,    0,    0, -121, -121,   65,   69,   32,   72,
            0,   18,   39,   40,   -4,   41,    7,    0,   22, -103,
            3,  -34,  -91,    0,  -95,    5,  -70,  -86,  -78,    0,
            0,    0,  -56,  -39, -179,  -52,   37,   48,    0,    0,
            0,   75,    0,    0,    0,  -21,   38,   92,   42,   60,
            43, -105,   98,  232,  -98,   44,    0,    0,    0,    0,
            0,    0,   24,   46,   20,  -34,   61,    0,    0, -134,
            -28,  -67,   67,    0,    0,   68,   51,   27,   86,    0,
            0,    0,    0,  113,   30,    0,  -12,   73,    0,  -73,
            74,  -27,  280,    0, -110,    0,  210,    0,  -45,  -35,
            88,   83,    0,   90,    0,    0,    0,    0,    0,   99,
            0,    0,  107,    0,    0,    0,  115,    0,    0,    0,
            321,  117,    0,    0,   14,  120,    0,    0,    0,  118,
            0,  -84,   45,  123,  136,  327,  -74,    0,    0,  137,
            33,    0,  -22,  132,   36,    0,  134,   53,    0,    0,
            0,    0,    0,    0,    0,  -34,  -34,  -34,  -34,    0,
            0,    0,    0,    0,    0,    0,  -34,  -34,  -34,  -34,
            -34,  -34,    0,    0,    0,  148,    0,    0,  335,    0,
            138,  348,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,  352,    0,    0,    0,    0,  -34,    0,  -34,
            -1,    0,    0,    0,  -28,  145,   86,   86,    0,    0,
            133,  133,  133,  133,  133,  133,  146,  159,  150,  363,
            0,    0,  325,  355,    0,  -34,    0,  -34,   63,    0,
            0,    0,    0,    0,    0,    0,  369,  381,  -28,  151,
            0,    0,  374,    0,    0,
    };
    final static short yyrindex[] = {                         0,
            0,  153,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,  418,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,  298,    0,    0,    0,    0,    0,    0,    0,
            0,  427,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,  -41,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,   19,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            153,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,  -71,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0, -113, -111,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,  -62,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,  -36,  -31,    0,    0,
            389,  391,  392,  394,  395,  396,    0,    0,    0,   -7,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,
    };
    final static short yygindex[] = {                         0,
            424,   77,    0,   89,    0,  -65,  283,  294,    0,    0,
            0,   49,  413,    0,   25,    0,    0,  304,    0,    0,
            0,  296,    0,  370,    0,    0,    0,    0,    0,    0,
            0,    0,    0,   78,    0,  111,  112,  -76,    0,  397,
            0,    0,    0,    0,    0,    0,  353,
    };
    final static int YYTABLESIZE=446;
    static short yytable[];
    static { yytable();}
    static void yytable(){
        yytable = new short[]{                        107,
                120,  107,  107,  107,  105,  200,  105,  105,  105,  106,
                97,  106,  106,  106,  168,  176,   97,  177,  107,  150,
                107,  229,  129,  105,  167,  105,  155,   41,  106,  108,
                106,   40,  191,   43,  192,   85,   33,  130,  230,   84,
                215,   87,  257,  103,   61,  111,   91,   97,   42,   97,
                222,  184,   45,   72,   46,   90,   86,  211,  148,  258,
                163,   78,   73,   14,   97,   96,   97,  160,   97,  176,
                175,  177,  176,  182,  177,  176,  226,  177,  176,  233,
                177,  132,   80,   82,   89,  140,  149,  159,  218,   73,
                122,  123,   64,  236,  145,   38,  130,    1,    2,  156,
                57,    3,  121,  270,    4,    5,    6,    7,    8,    9,
                10,  235,   58,   11,   12,   33,   13,   33,  104,  105,
                53,  269,  107,  112,   54,    3,   55,  178,    4,   90,
                248,   59,  179,  109,   10,   65,   68,   45,   64,   56,
                13,  148,  135,   51,  134,  194,   52,  133,  135,  132,
                134,  195,  102,  180,   72,  176,  134,  177,  259,    6,
                161,  107,  164,  165,    3,  106,    6,    4,  201,  116,
                145,   63,  231,   10,  176,  176,  177,  177,  109,   13,
                6,  223,  117,  107,  142,  114,    3,   68,  169,    4,
                6,   70,  273,   72,   75,   10,    1,    2,  185,  127,
                3,   13,   73,    4,    5,    6,    7,    8,    9,   10,
                197,  198,   11,   12,  107,   13,  133,  119,  124,  105,
                72,  199,   94,   95,  106,  134,    7,    8,    9,   95,
                7,    8,    9,  227,  128,  107,  107,  107,  107,  207,
                105,  105,  105,  105,  212,  106,  106,  106,  106,  187,
                188,  189,  190,  228,  255,  185,   60,   33,  234,   94,
                95,   94,   95,   39,  241,  242,  243,  244,  245,  246,
                44,   83,  247,   77,  256,  154,   94,   95,   94,   95,
                94,   95,  174,  262,  109,  181,  237,  238,  225,  239,
                240,  232,  125,  131,   79,   81,   88,  139,  148,  158,
                217,  162,    1,    2,  126,  253,    3,  254,  173,    4,
                5,    6,    7,    8,    9,   10,  141,  166,   11,   12,
                193,   13,  171,  172,    6,    7,    8,    9,  183,  186,
                1,    2,  196,  267,    3,  268,  185,    4,    5,    6,
                7,    8,    9,   10,  202,  204,   11,   12,    2,   13,
                135,    3,  143,  203,    4,  205,    6,    7,    8,    9,
                10,  136,  206,  144,  209,  151,   13,  176,  265,  177,
                221,    2,  210,  214,    3,  249,  141,    4,  219,    6,
                7,    8,    9,   10,    6,    7,    8,    9,  251,   13,
                107,  220,  224,    3,  250,  252,    4,  176,  266,  177,
                260,  261,   10,  264,  107,  263,  274,    3,   13,   26,
                4,  176,  271,  177,  275,  107,   10,    4,    3,  135,
                45,    4,   13,  176,  272,  177,    2,   10,  143,  124,
                136,  125,  128,   13,  129,  126,  127,   62,   67,  144,
                208,  213,  153,  115,  170,  216,
        };
    }
    static short yycheck[];
    static { yycheck(); }
    static void yycheck() {
        yycheck = new short[] {                         41,
                40,   43,   44,   45,   41,   41,   43,   44,   45,   41,
                45,   43,   44,   45,   91,   43,   45,   45,   60,  125,
                62,   44,   44,   60,   90,   62,  125,   40,   60,  125,
                62,   44,   60,   46,   62,   40,   44,   59,   61,   44,
                125,   46,   44,   41,   44,   41,   40,   45,   61,   45,
                125,  125,  123,  125,   40,  123,   61,   44,   40,   61,
                41,   44,  125,  123,   45,   44,   45,   44,   45,   43,
                44,   45,   43,   44,   45,   43,   44,   45,   43,   44,
                45,   44,   44,   44,   44,   44,   44,   44,   44,   58,
                270,  271,   16,   41,   70,   44,   59,  256,  257,   75,
                257,  260,   54,   41,  263,  264,  265,  266,  267,  268,
                269,   59,  269,  272,  273,  123,  275,  125,   41,   42,
                257,   59,  257,   46,  257,  260,  257,   42,  263,  123,
                196,  257,   47,   45,  269,  257,  123,  123,   62,  270,
                275,  123,  256,  256,  256,  256,  259,  261,  262,  261,
                262,  262,  256,   41,  123,   43,   68,   45,  235,  265,
                83,  257,   85,   86,  260,  257,  265,  263,  120,  256,
                146,  125,   41,  269,   43,   43,   45,   45,   90,  275,
                265,  157,  261,  257,  125,  256,  260,  123,  256,  263,
                265,  123,  269,  265,  123,  269,  256,  257,  110,  125,
                260,  275,  265,  263,  264,  265,  266,  267,  268,  269,
                256,  257,  272,  273,  256,  275,  125,  274,  271,  256,
                123,  257,  257,  258,  256,  137,  266,  267,  268,  258,
                266,  267,  268,  256,  256,  277,  278,  279,  280,  125,
                277,  278,  279,  280,  125,  277,  278,  279,  280,  277,
                278,  279,  280,  276,  256,  167,  256,  265,  125,  257,
                258,  257,  258,  276,  187,  188,  189,  190,  191,  192,
                256,  276,  125,  256,  276,   44,  257,  258,  257,  258,
                257,  258,  256,  125,  196,  256,  176,  177,  256,  178,
                179,  256,  256,  256,  256,  256,  256,  256,  256,  256,
                256,  256,  256,  257,  257,  228,  260,  230,  258,  263,
                264,  265,  266,  267,  268,  269,  257,  257,  272,  273,
                41,  275,  256,  256,  265,  266,  267,  268,  256,  256,
                256,  257,  123,  256,  260,  258,  248,  263,  264,  265,
                266,  267,  268,  269,  257,  256,  272,  273,  257,  275,
                68,  260,   70,  271,  263,  257,  265,  266,  267,  268,
                269,   68,  256,   70,   44,   72,  275,   43,   44,   45,
                44,  257,  256,  256,  260,   41,  257,  263,  256,  265,
                266,  267,  268,  269,  265,  266,  267,  268,   41,  275,
                257,  256,  256,  260,  257,   44,  263,   43,   44,   45,
                256,  256,  269,   41,  257,  256,  256,  260,  275,  257,
                263,   43,   44,   45,   41,  257,  269,    0,  260,  137,
                123,  263,  275,   43,   44,   45,    0,  269,  146,   41,
                137,   41,   41,  275,   41,   41,   41,   14,   26,  146,
                137,  146,   73,   47,   92,  152,
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
            "cuerpo_dec_dist : '{' bloque_dec_dist '}'",
            "cuerpo_dec_dist : '{' '}' error",
            "bloque_dec_dist : bloque_dec_dist declaracion_funcion",
            "bloque_dec_dist : declaracion_funcion",
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
            "seleccion : IF condicion cuerpo_if error",
            "seleccion : IF error",
            "seleccion : IF condicion error",
            "seleccion : IF cuerpo_if error",
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

    //#line 917 "gramatica.y"
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
    public boolean coincideP = false;

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
            double max_pos = 1.7976931348623157E308;
            double min_pos = 2.2250738585072014E-308;
            double max_neg = -2.2250738585072014E-308;
            double min_neg = -1.7976931348623157E308;

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
        String hereda_ch = Tabla_Simbolos.getAtributos(cHija+"@main").getHereda();
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
        String delimitador = "@";
        for(Object o: elements){
            nuevoAmb = nuevoAmb.concat(delimitador);
            nuevoAmb = nuevoAmb.concat((String)o);
        }
        return nuevoAmb;
    }

    public boolean setAmbito(String lexema){
        return Tabla_Simbolos.modificarClave(lexema, concatenarAmbito(lexema, pilaAmbito.getElements()));
        //la clave en la tabla de simbolos sera el lexema + el ambito.
        //Ej: var1 --> var1@main@f1@f2 es una variable declarada en f2, quien esta definida en f1, y en general, dentro de main
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
            AtributosLexema att = Tabla_Simbolos.getAtributos(amb+"@main");
            if ((att != null)&&(att.isUso("CLASE"))){
                String clasePadre = Tabla_Simbolos.getAtributos(amb+"@main").getHereda();
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

    public String compatibilidadTipos(String operacion, String operador, String tipo_op1, String tipo_op2, String op1, String op2){
        String tipoResultado = Conversor.getTipo(tipo_op1,tipo_op2,operacion); //en el sval esta el tipo
        if (tipoResultado.equals("error")){
            System.out.println("ERROR DE INCOMPATIBILIDAD DE TIPOS. Linea: " + Analizador_Lexico.cantLineas + " no se puede operar entre "+tipo_op1+" y "+tipo_op2);
            GeneradorCod.cantErrores++;
            yyval.sval = tipoResultado;
        } else {
            yyval.sval = tipoResultado;
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
    //#line 672 "Parser.java"
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
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN PROGRAMA. Linea: " + Analizador_Lexico.cantLineas + " se esperaba '}'");}
                break;
                case 3:
//#line 25 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN PROGRAMA. Linea: " + Analizador_Lexico.cantLineas + " se esperaba '{'");}
                break;
                case 4:
//#line 26 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN PROGRAMA. Linea: " + Analizador_Lexico.cantLineas + " se esperaban llaves");}
                break;
                case 9:
//#line 35 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN SENTENCIA. Linea: " + Analizador_Lexico.cantLineas);}
                break;
                case 21:
//#line 55 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN DECLARACION DE VARIABLES. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 22:
//#line 56 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN DECLARACION DE VARIABLES. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 23:
//#line 59 "gramatica.y"
                {tipo = val_peek(0).sval;}
                break;
                case 24:
//#line 60 "gramatica.y"
                {tipo = val_peek(0).sval;}
                break;
                case 25:
//#line 61 "gramatica.y"
                {tipo = val_peek(0).sval;}
                break;
                case 26:
//#line 64 "gramatica.y"
                {
                    AtributosLexema att = Tabla_Simbolos.getAtributos(val_peek(0).sval+"@main");
                    if (att != null){
                        if (Tabla_Simbolos.getAtributos(val_peek(0).sval+"@main").isUso("CLASE")){
                            tipo = val_peek(0).sval;
                            Tabla_Simbolos.borrarSimbolo(val_peek(0).sval);
                        } else {
                            GeneradorCod.cantErrores++;
                            System.out.println("ERROR EN DECLARACION DE VARIABLES. Linea: "+Analizador_Lexico.cantLineas+": "+val_peek(0).sval+" no es tipo CLASE");
                        }
                    } else {
                        GeneradorCod.cantErrores++;
                        System.out.println("ERROR EN DECLARACION DE VARIABLES. Linea: "+Analizador_Lexico.cantLineas+": "+val_peek(0).sval+" no es una clase declarada");
                    }

                }
                break;
                case 27:
//#line 82 "gramatica.y"
                {
                    variables.put(concatenarAmbito(val_peek(0).sval,pilaAmbito.getElements()),0); /*para el warning de que no se usa*/
                    Tabla_Simbolos.getAtributos(val_peek(0).sval).setTipo(tipo);
                    Tabla_Simbolos.getAtributos(val_peek(0).sval).setUso("VARIABLE");
                    String lexema = isDeclarada(val_peek(0).sval,pilaAmbito.getElements());
                    AtributosLexema att = Tabla_Simbolos.getAtributos(lexema);
                    AtributosLexema tope = Tabla_Simbolos.getAtributos(pilaAmbito.getTope()+"@main");
                    if (tope != null && tope.isUso("CLASE") && !lexema.equals("") && att.isUso("VARIABLE")){
                        GeneradorCod.cantErrores++;
                        System.out.println("ERROR. SOBREESCRITURA DE ATRIBUTO. Linea: " + Analizador_Lexico.cantLineas);
                    } else {
                        if (!setAmbito(val_peek(0).sval)){
                            /*setAmbito modifica la clave, concatenando el ambito. Si ya existia arroja error, y sino, la setea*/
                            GeneradorCod.cantErrores++;
                            System.out.println("ERROR. REDECLARACION DE NOMBRE. Linea: " + Analizador_Lexico.cantLineas);
                        }
                    }

                }
                break;
                case 28:
//#line 102 "gramatica.y"
                {
                    variables.put(concatenarAmbito(val_peek(0).sval,pilaAmbito.getElements()),0);
                    Tabla_Simbolos.getAtributos(val_peek(0).sval).setTipo(tipo);
                    Tabla_Simbolos.getAtributos(val_peek(0).sval).setUso("VARIABLE");
                    String lexema = isDeclarada(val_peek(0).sval,pilaAmbito.getElements());
                    AtributosLexema att = Tabla_Simbolos.getAtributos(lexema);
                    AtributosLexema tope = Tabla_Simbolos.getAtributos(pilaAmbito.getTope()+"@main");
                    if (tope != null && tope.isUso("CLASE") && !lexema.equals("") && att.isUso("VARIABLE")){
                        GeneradorCod.cantErrores++;
                        System.out.println("ERROR. SOBREESCRITURA DE ATRIBUTO. Linea: " + Analizador_Lexico.cantLineas);
                    } else {
                        if (!setAmbito(val_peek(0).sval)){
                            /*setAmbito modifica la clave, concatenando el ambito. Si ya existia arroja error, y sino, la setea*/
                            GeneradorCod.cantErrores++;
                            System.out.println("ERROR. REDECLARACION DE NOMBRE. Linea: " + Analizador_Lexico.cantLineas);
                        }
                    }

                }
                break;
                case 29:
//#line 124 "gramatica.y"
                {
                    String am = (String) pilaAmbito.desapilar();/*sino queda f1@main@f1, y necesito f1@main para los atributos*/
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
//#line 136 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN DECLARACION DE FUNCION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 31:
//#line 141 "gramatica.y"
                {
                    /*poner ambito a IDfuncion, apilar nuevo ambito*/
                    funcionImpl = val_peek(2).sval;
                    isDecl = isDeclarada(val_peek(2).sval,pilaAmbito.getElements());
                    Tabla_Simbolos.getAtributos(val_peek(2).sval).setUso("FUNCION");
                    if (!isDecl.equals("")){
                        coincideP = (false == Tabla_Simbolos.getAtributos(isDecl).tieneParametro());
                    }
                    if (!setAmbito(val_peek(2).sval)){/*si ya estaba declarado entra al if*/
                        if (Tabla_Simbolos.getAtributos(concatenarAmbito(val_peek(2).sval, pilaAmbito.getElements())).isImplementado()){
                            System.out.println("ERROR. REDECLARACION DE NOMBRE. Linea: " + Analizador_Lexico.cantLineas);
                            GeneradorCod.cantErrores++;
                        }
                    }
                    GeneradorCod.setFlagFuncion(concatenarAmbito(val_peek(2).sval,pilaAmbito.getElements()));
                    pilaAmbito.apilar(val_peek(2).sval);
                }
                break;
                case 32:
//#line 159 "gramatica.y"
                {
                    funcionImpl = val_peek(4).sval;
                    isDecl = isDeclarada(val_peek(4).sval,pilaAmbito.getElements());
                    if (!isDecl.equals("")){
                        AtributosLexema att = Tabla_Simbolos.getAtributos(isDecl);
                        coincideP = (true == att.tieneParametro()) && att.coincideTipoParametro(val_peek(2).sval);
                    }
                    Tabla_Simbolos.getAtributos(val_peek(4).sval).setUso("FUNCION");
                    Tabla_Simbolos.getAtributos(val_peek(4).sval).setTipoParametro(val_peek(2).sval);
                    Tabla_Simbolos.getAtributos(val_peek(1).sval).setUso("PARAMETRO");
                    Tabla_Simbolos.getAtributos(val_peek(1).sval).setTipo(val_peek(2).sval);
                    if (!setAmbito(val_peek(4).sval)){/*si ya estaba declarado entra al if*/
                        if (Tabla_Simbolos.getAtributos(concatenarAmbito(val_peek(4).sval, pilaAmbito.getElements())).isImplementado()){
                            System.out.println("ERROR. REDECLARACION DE NOMBRE. Linea: " + Analizador_Lexico.cantLineas);
                            GeneradorCod.cantErrores++;
                        }
                    }
                    String id_ambito = concatenarAmbito(val_peek(4).sval,pilaAmbito.getElements());
                    GeneradorCod.setFlagFuncion(id_ambito);
                    pilaAmbito.apilar(val_peek(4).sval);
                    Tabla_Simbolos.getAtributos(id_ambito).setParametro(concatenarAmbito(val_peek(1).sval,pilaAmbito.getElements()));
                    setAmbito(val_peek(1).sval); /*para el parametro formal*/
                }
                break;
                case 33:
//#line 182 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN DECLARACION DE FUNCION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ')'");}
                break;
                case 34:
//#line 183 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN DECLARACION DE FUNCION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba '('");}
                break;
                case 35:
//#line 184 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN DECLARACION DE FUNCION. Linea: " + Analizador_Lexico.cantLineas + " falta el tipo de " + val_peek(2).sval);}
                break;
                case 37:
//#line 189 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN DECLARACION DE FUNCION. Linea: " + Analizador_Lexico.cantLineas + " no se puede declarar una funcion sin cuerpo");}
                break;
                case 43:
//#line 201 "gramatica.y"
                {pilaAmbito.desapilar();}
                break;
                case 44:
//#line 202 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN DECLARACION DE CLASE. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 45:
//#line 206 "gramatica.y"
                {
                    Tabla_Simbolos.getAtributos(val_peek(0).sval).setUso("CLASE");
                    if (!setAmbito(val_peek(0).sval)){
                        GeneradorCod.cantErrores++;
                        System.out.println("ERROR. REDECLARACION DE NOMBRE. Linea: " + Analizador_Lexico.cantLineas);
                    };
                    pilaAmbito.apilar(val_peek(0).sval);

                }
                break;
                case 46:
//#line 216 "gramatica.y"
                {
                    AtributosLexema att = Tabla_Simbolos.getAtributos(val_peek(0).sval+"@main");
                    if (att != null && att.isUso("INTERFAZ")){
                        Tabla_Simbolos.getAtributos(val_peek(2).sval).setUso("CLASE");
                        Tabla_Simbolos.getAtributos(val_peek(2).sval).setImplementa(val_peek(0).sval);
                        if (!setAmbito(val_peek(2).sval)){
                            System.out.println("ERROR. REDECLARACION DE NOMBRE. Linea: " + Analizador_Lexico.cantLineas);
                            GeneradorCod.cantErrores++;
                        };
                        pilaAmbito.apilar(val_peek(2).sval);
                    } else {
                        System.out.println("ERROR EN DECLARACION DE CLASE. Linea "+Analizador_Lexico.cantLineas+": "+val_peek(0).sval+" no es una INTERFAZ declarada");
                        GeneradorCod.cantErrores++;
                    }
                    /*se borra ID de la tabla de simbolos porque el lexico lo inserta al reconocer un identificador, */
                    /*y como el original tiene el nombre cambiado por el ambito, existiran ambos en la TS*/
                    Tabla_Simbolos.borrarSimbolo(val_peek(0).sval);
                }
                break;
                case 47:
//#line 234 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN DECLARACION DE CLASE. Linea: " + Analizador_Lexico.cantLineas + " falta el identificador de la interfaz");}
                break;
                case 49:
//#line 238 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN DECLARACION DE CLASE. Linea: " + Analizador_Lexico.cantLineas + " no se puede definir una clase sin cuerpo");}
                break;
                case 54:
//#line 248 "gramatica.y"
                {
                    AtributosLexema att = Tabla_Simbolos.getAtributos(val_peek(1).sval+"@main");
                    if (att != null && att.isUso("CLASE")){
                        Tabla_Simbolos.getAtributos((String)pilaAmbito.getTope()+"@main").setHereda(val_peek(1).sval);
                        Tabla_Simbolos.borrarSimbolo(val_peek(1).sval);
                        /*si ID es una clase, entonces la clase donde se define esta linea debe heredar de ID.*/
                    } else {
                        GeneradorCod.cantErrores++;
                        System.out.println("ERROR EN SENTENCIA DE CLASE. Linea: " + Analizador_Lexico.cantLineas + " "+ val_peek(1).sval + " no se corresponde a una clase declarada");
                    }
                }
                break;
                case 55:
//#line 260 "gramatica.y"
                {
                    GeneradorCod.borrarFlag();
                    pilaAmbito.desapilar();
                }
                break;
                case 56:
//#line 267 "gramatica.y"
                {
                    AtributosLexema att = Tabla_Simbolos.getAtributos(concatenarAmbito(funcionImpl,pilaAmbito.getElements()));
                    if (!isDecl.equals("")){
                        if (isImpl){
                            GeneradorCod.cantErrores++;
                            System.out.println("ERROR EN DECLARACION DISTRIBUIDA. FUNCION YA IMPLEMENTADA. Linea: " + Analizador_Lexico.cantLineas);
                        } else {
                            if (!coincideP){
                                GeneradorCod.cantErrores++;
                                System.out.println("ERROR EN DECLARACION DISTRIBUIDA. NO COINCIDEN LA CANTIDAD O TIPO DE PARAMETROS. Linea: " + Analizador_Lexico.cantLineas);
                            } else {
                                att.setImplementado(true);
                            }
                        }
                    } else {
                        Tabla_Simbolos.borrarSimbolo(concatenarAmbito(funcionImpl,pilaAmbito.getElements()));
                        System.out.println("ERROR EN DECLARACION DISTRIBUIDA. FUNCION NO DECLARADA. Linea: " + Analizador_Lexico.cantLineas);
                        GeneradorCod.cantErrores++;
                    }
                    /*se borra ID de la tabla de simbolos porque el lexico lo inserta al reconocer un identificador, */
                    /*y como el original tiene el nombre cambiado por el ambito, existiran ambos en la TS*/
                    Tabla_Simbolos.borrarSimbolo(funcionImpl);
                    pilaAmbito.desapilar();
                }
                break;
                case 57:
//#line 291 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN DECLARACION DISTRIBUIDA. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ':'");}
                break;
                case 58:
//#line 292 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN DECLARACION DISTRIBUIDA. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 59:
//#line 296 "gramatica.y"
                {
                    AtributosLexema atributos = Tabla_Simbolos.getAtributos(val_peek(0).sval+"@main");
                    if ((atributos != null) && (atributos.isUso("CLASE"))){
                        pilaAmbito.apilar(val_peek(0).sval);
                    } else {
                        System.out.println("ERROR EN DECLARACION DISTRIBUIDA. Linea: " + Analizador_Lexico.cantLineas + " " + val_peek(0).sval + " no es una clase ");
                        GeneradorCod.cantErrores++;
                    }
                    Tabla_Simbolos.borrarSimbolo(val_peek(0).sval);
                }
                break;
                case 60:
//#line 306 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN DECLARACION DISTRIBUIDA. Linea: " + Analizador_Lexico.cantLineas + " falta palabra reservada FOR");}
                break;
                case 62:
//#line 310 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN DECLARACION DISTRIBUIDA. Linea: " + Analizador_Lexico.cantLineas + " no se puede definir una declaracion distribuida sin cuerpo");}
                break;
                case 65:
//#line 317 "gramatica.y"
                {pilaAmbito.desapilar();}
                break;
                case 66:
//#line 318 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN INTERFAZ. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 67:
//#line 322 "gramatica.y"
                {
                    Tabla_Simbolos.getAtributos(val_peek(0).sval).setUso("INTERFAZ");
                    if (!setAmbito(val_peek(0).sval)){
                        GeneradorCod.cantErrores++;
                        System.out.println("ERROR. REDECLARACION DE NOMBRE. Linea: " + Analizador_Lexico.cantLineas);
                    };
                    pilaAmbito.apilar(val_peek(0).sval);
                }
                break;
                case 69:
//#line 333 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN INTERFAZ. Linea: " + Analizador_Lexico.cantLineas + " no se puede declarar una interfaz sin metodos");}
                break;
                case 70:
//#line 337 "gramatica.y"
                {
                    GeneradorCod.borrarFlag();
                    pilaAmbito.desapilar();
                }
                break;
                case 71:
//#line 342 "gramatica.y"
                {
                    GeneradorCod.borrarFlag();
                    pilaAmbito.desapilar();
                }
                break;
                case 72:
//#line 346 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN METODO DE INTERFAZ. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 73:
//#line 347 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN METODO DE INTERFAZ. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 76:
//#line 352 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN INVOCACION A LA FUNCION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 78:
//#line 354 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN SENTENCIA IF. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 80:
//#line 356 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN SENTENCIA DE IMPRESION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 81:
//#line 358 "gramatica.y"
                {
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
                                if(att.isImplementado()){
                                    GeneradorCod.agregarTerceto("=",att.getParametro(),(String) val_peek(2).obj, val_peek(2).sval); /*realizaria el copia valor*/
                                    GeneradorCod.agregarTerceto("CALL", val_peek(4).sval);
                                } else {
                                    GeneradorCod.cantErrores++;
                                    System.out.println("ERROR EN INVOCACION A METODO. Linea: " + Analizador_Lexico.cantLineas + " el metodo no esta implementado");
                                }
                            }
                        }
                    }
                }
                break;
                case 82:
//#line 380 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN REFERENCIA A CLASE. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 83:
//#line 382 "gramatica.y"
                {
                    if (!val_peek(3).sval.equals("")){
                        AtributosLexema att = Tabla_Simbolos.getAtributos(val_peek(3).sval);
                        if (att.tieneParametro()){
                            GeneradorCod.cantErrores++;
                            System.out.println("ERROR EN REFERENCIA A CLASE. Linea: " + Analizador_Lexico.cantLineas + " el numero de parametros no coincide");
                        } else {
                            if(att.isImplementado()){
                                GeneradorCod.agregarTerceto("CALL", val_peek(3).sval);
                            } else {
                                GeneradorCod.cantErrores++;
                                System.out.println("ERROR EN INVOCACION A METODO. Linea: " + Analizador_Lexico.cantLineas + " el metodo no esta implementado");
                            }
                        }
                    }
                }
                break;
                case 84:
//#line 398 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN REFERENCIA A CLASE. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 86:
//#line 400 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN SENTENCIA FOR. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 87:
//#line 401 "gramatica.y"
                {GeneradorCod.agregarTerceto("RETURN","");}
                break;
                case 88:
//#line 402 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN RETURN. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 89:
//#line 406 "gramatica.y"
                {
                    String var = isDeclarada(val_peek(3).sval, pilaAmbito.getElements());
                    if (var.equals("")){
                        System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " variable "+val_peek(3).sval+" no declarada.");
                        GeneradorCod.cantErrores++;
                    } else {
                        if (!Tabla_Simbolos.getAtributos(var).isUso("VARIABLE") && !Tabla_Simbolos.getAtributos(var).isUso("PARAMETRO")){
                            System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas +" "+val_peek(3).sval+" no es una variable.");
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
                                /*if (exp.contains("[")){     //si opero entre numeros del mismo tipo, no  requiere conversion*/
                                /*t = "["+GeneradorCod.agregarTerceto("=", var, exp, tipoResultado) + "]";*/
                                /*} else {*/
                                Terceto tConv = Conversor.getTercetoConversion("a", var, exp);
                                if (tConv != null){
                                    refTerceto = "["+GeneradorCod.agregarTerceto(tConv) + "]";
                                    t = "["+GeneradorCod.agregarTerceto("=", var, refTerceto, tipoResultado) + "]";
                                } else {
                                    t = "["+GeneradorCod.agregarTerceto("=", var, exp, tipoResultado) + "]";
                                }
                                /*}*/
                                yyval.obj = t;
                            }
                        }
                    }
                    Tabla_Simbolos.borrarSimbolo(val_peek(3).sval);
                }
                break;
                case 90:
//#line 444 "gramatica.y"
                {
                    String var = isDeclarada(val_peek(3).sval, pilaAmbito.getElements());
                    if (var.equals("")){
                        System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " variable "+val_peek(3).sval+" no declarada.");
                        GeneradorCod.cantErrores++;
                    } else {
                        if (!Tabla_Simbolos.getAtributos(var).isUso("VARIABLE")){
                            System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas+" "+val_peek(3).sval+" no es una variable.");
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
                case 91:
//#line 476 "gramatica.y"
                {
                    if (!val_peek(3).sval.equals("")){
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
                    } else {
                        System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas +" variable no declarada.");
                        GeneradorCod.cantErrores++;
                    }
                }
                break;
                case 92:
//#line 518 "gramatica.y"
                {
                    if (!val_peek(3).sval.equals("")){
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
                    } else {
                        System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas +" variable no declarada.");
                        GeneradorCod.cantErrores++;
                    }
                }
                break;
                case 93:
//#line 553 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " no se puede realizar una asignacion a una funcion");}
                break;
                case 94:
//#line 554 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " no se puede realizar una asignacion a una funcion");}
                break;
                case 95:
//#line 555 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " no se puede realizar una asignacion a una funcion");}
                break;
                case 96:
//#line 556 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " no se puede realizar una asignacion a una funcion");}
                break;
                case 97:
//#line 557 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " Se esperaba expresion");}
                break;
                case 98:
//#line 558 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " Se esperaba expresion");}
                break;
                case 99:
//#line 559 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " Se esperaba expresion");}
                break;
                case 100:
//#line 560 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " Se esperaba expresion");}
                break;
                case 101:
//#line 561 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 102:
//#line 562 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 103:
//#line 563 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 104:
//#line 564 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 105:
//#line 568 "gramatica.y"
                {
                    yyval.obj = compatibilidadTipos("o", "+", val_peek(2).sval, val_peek(0).sval, (String) val_peek(2).obj, (String) val_peek(0).obj);
                }
                break;
                case 106:
//#line 572 "gramatica.y"
                {
                    yyval.obj = compatibilidadTipos("o", "-", val_peek(2).sval, val_peek(0).sval, (String) val_peek(2).obj, (String) val_peek(0).obj);
                }
                break;
                case 107:
//#line 576 "gramatica.y"
                {
                    yyval.sval = val_peek(0).sval;
                    yyval.obj = val_peek(0).obj;
                }
                break;
                case 108:
//#line 583 "gramatica.y"
                {
                    yyval.obj = compatibilidadTipos("o", "*", val_peek(2).sval, val_peek(0).sval, (String) val_peek(2).obj, (String) val_peek(0).obj);
                }
                break;
                case 109:
//#line 587 "gramatica.y"
                {
                    yyval.obj = compatibilidadTipos("o", "/", val_peek(2).sval, val_peek(0).sval, (String) val_peek(2).obj, (String) val_peek(0).obj);
                }
                break;
                case 110:
//#line 591 "gramatica.y"
                {
                    yyval.sval = val_peek(0).sval;
                    yyval.obj = val_peek(0).obj;
                }
                break;
                case 111:
//#line 598 "gramatica.y"
                {
                    String lexema = isDeclarada(val_peek(0).sval, pilaAmbito.getElements());

                    if (lexema.equals("")){
                        System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " variable "+val_peek(0).sval+" no declarada.");
                        GeneradorCod.cantErrores++;
                        yyval.sval = "error";
                    } else {
                        yyval.sval=Tabla_Simbolos.getAtributos(lexema).getTipo();
                    }
                    yyval.obj = lexema;
                    variables.remove(lexema);
                    Tabla_Simbolos.borrarSimbolo(val_peek(0).sval);
                }
                break;
                case 112:
//#line 613 "gramatica.y"
                {
                    yyval.sval=val_peek(0).sval;
                    yyval.obj = val_peek(0).obj;
                }
                break;
                case 113:
//#line 619 "gramatica.y"
                {
                    chequeoRango(val_peek(0).sval);
                    AtributosLexema att = Tabla_Simbolos.getAtributos(val_peek(0).sval);
                    att.sumarUso();
                    att.setUso("CONSTANTE");
                    yyval.sval=att.getTipo();
                    yyval.obj = val_peek(0).sval;
                }
                break;
                case 114:
//#line 627 "gramatica.y"
                {
                    chequeoRango("-"+val_peek(0).sval);
                    AtributosLexema att = Tabla_Simbolos.getAtributos(val_peek(0).sval);
                    if (att.isCero()){
                        Tabla_Simbolos.modificarClave(val_peek(0).sval, "-"+val_peek(0).sval);
                    } else {
                        if (!Tabla_Simbolos.existeSimbolo("-"+val_peek(0).sval)){
                            Tabla_Simbolos.insertarSimbolo("-"+val_peek(0).sval,att);
                            Tabla_Simbolos.getAtributos("-"+val_peek(0).sval).setUso("CONSTANTE");
                        }
                    }
                    Tabla_Simbolos.getAtributos("-"+val_peek(0).sval).sumarUso();
                    yyval.sval=Tabla_Simbolos.getAtributos("-"+val_peek(0).sval).getTipo();
                    String l = "-"+val_peek(0).sval;
                    yyval.obj = l;
                }
                break;
                case 115:
//#line 646 "gramatica.y"
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
                                    GeneradorCod.agregarTerceto("=",Tabla_Simbolos.getAtributos(fun).getParametro(),(String) val_peek(1).obj, val_peek(1).sval); /*realizaria el copia valor*/
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
                case 116:
//#line 676 "gramatica.y"
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
                            System.out.println("ERROR EN INVOCACION A FUNCION. Linea: " + Analizador_Lexico.cantLineas +" "+val_peek(2).sval+" no es una funcion.");
                            GeneradorCod.cantErrores++;
                        }
                    }
                    Tabla_Simbolos.borrarSimbolo(val_peek(2).sval);
                }
                break;
                case 118:
//#line 701 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN SENTENCIA IF. Linea: " + Analizador_Lexico.cantLineas +" se esperaba END_IF");}
                break;
                case 119:
//#line 702 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN SENTENCIA IF. Linea: " + Analizador_Lexico.cantLineas +" falta condicion, cuerpo y END_IF");}
                break;
                case 120:
//#line 703 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN SENTENCIA IF. Linea: " + Analizador_Lexico.cantLineas +" falta cuerpo y END_IF");}
                break;
                case 121:
//#line 704 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN SENTENCIA IF. Linea: " + Analizador_Lexico.cantLineas +" condicion y END_IF");}
                break;
                case 122:
//#line 708 "gramatica.y"
                {
                    Terceto t = new Terceto("BF", "");
                    pilaTercetos.apilar(t);
                    GeneradorCod.agregarTerceto(t);
                }
                break;
                case 123:
//#line 713 "gramatica.y"
                {GeneradorCod.cantErrores++;  System.out.println("ERROR EN SENTENCIA IF. Linea: " + Analizador_Lexico.cantLineas + " falta condicion");}
                break;
                case 124:
//#line 716 "gramatica.y"
                {compatibilidadTipos("o", ">=", val_peek(2).sval, val_peek(0).sval, (String) val_peek(2).obj, (String) val_peek(0).obj);}
                break;
                case 125:
//#line 717 "gramatica.y"
                {compatibilidadTipos("o", "<=", val_peek(2).sval, val_peek(0).sval, (String) val_peek(2).obj, (String) val_peek(0).obj);}
                break;
                case 126:
//#line 718 "gramatica.y"
                {compatibilidadTipos("o", "<", val_peek(2).sval, val_peek(0).sval, (String) val_peek(2).obj, (String) val_peek(0).obj);}
                break;
                case 127:
//#line 719 "gramatica.y"
                {compatibilidadTipos("o", ">", val_peek(2).sval, val_peek(0).sval, (String) val_peek(2).obj, (String) val_peek(0).obj);}
                break;
                case 128:
//#line 720 "gramatica.y"
                {compatibilidadTipos("o", "==", val_peek(2).sval, val_peek(0).sval, (String) val_peek(2).obj, (String) val_peek(0).obj);}
                break;
                case 129:
//#line 721 "gramatica.y"
                {compatibilidadTipos("o", "!!", val_peek(2).sval, val_peek(0).sval, (String) val_peek(2).obj, (String) val_peek(0).obj);}
                break;
                case 130:
//#line 724 "gramatica.y"
                {GeneradorCod.agregarTercetoLabel();}
                break;
                case 132:
//#line 729 "gramatica.y"
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
                case 133:
//#line 742 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN SENTENCIA IF. Linea: " + Analizador_Lexico.cantLineas + " cuerpo de IF vacio");}
                break;
                case 134:
//#line 746 "gramatica.y"
                {
                    /*obtener el nro de terceto a completar*/
                    Terceto bf = (Terceto) pilaTercetos.desapilar();
                    /*ponerle en operando 1, el nro de terceto actual +2*/
                    bf.setOperando_1("["+ String.valueOf(GeneradorCod.getIndexActual()+1)+"]");
                    GeneradorCod.agregarTercetoLabel();
                }
                break;
                case 135:
//#line 753 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN SENTENCIA IF. Linea: " + Analizador_Lexico.cantLineas + " cuerpo de IF vacio");}
                break;
                case 136:
//#line 757 "gramatica.y"
                {
                    /*obtener nro de terceto de BI*/
                    Terceto bi = (Terceto) pilaTercetos.desapilar();
                    /*completar con nro terceto actual +1*/
                    bi.setOperando_1("["+String.valueOf(GeneradorCod.getIndexActual()+1)+"]");
                }
                break;
                case 137:
//#line 763 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN SENTENCIA IF. Linea: " + Analizador_Lexico.cantLineas + " cuerpo de ELSE vacio");}
                break;
                case 138:
//#line 767 "gramatica.y"
                {
                    String cad = val_peek(0).sval;
                    cad = cad.substring(1, cad.length()-1);
                    GeneradorCod.agregarTerceto("PRINT", cad);
                }
                break;
                case 139:
//#line 772 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN SENTENCIA DE IMPRESION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba una cadena de caracteres");}
                break;
                case 140:
//#line 776 "gramatica.y"
                {
                    claseRef = "";
                    String id1 = isDeclarada(val_peek(2).sval, pilaAmbito.getElements());
                    String id2 = isDeclarada(val_peek(0).sval, pilaAmbito.getElements());
                    yyval.sval = id2;
                    if (!id1.equals("")){
                        String nombreClase = Tabla_Simbolos.getAtributos(id1).getTipo();
                        AtributosLexema atributos = Tabla_Simbolos.getAtributos(nombreClase+"@main");
                        if ((atributos != null) && (atributos.isUso("CLASE"))){
                            if (!id2.equals("")){
                                /*es una clase*/
                                if (hereda(nombreClase, val_peek(0).sval)){
                                    claseRef = val_peek(0).sval;
                                } else {
                                    GeneradorCod.cantErrores++;
                                    System.out.println("ERROR EN REFERENCIA A CLASE. Linea: " + Analizador_Lexico.cantLineas +" "+nombreClase+" no hereda de "+val_peek(0).sval);
                                }
                            } else {
                                if (!Tabla_Simbolos.existeSimbolo(val_peek(0).sval + "@main@"+nombreClase)){
                                    System.out.println("ERROR EN REFERENCIA A CLASE. Linea: " + Analizador_Lexico.cantLineas +" "+val_peek(0).sval+" no esta declarado dentro de "+val_peek(2).sval);
                                    GeneradorCod.cantErrores++;
                                } else {
                                    yyval.sval = val_peek(0).sval + "@main@" + nombreClase;
                                }
                            }
                        } else {
                            System.out.println("ERROR EN REFERENCIA A CLASE. Linea: " + Analizador_Lexico.cantLineas +" "+val_peek(2).sval+" no es de tipo clase");
                            GeneradorCod.cantErrores++;
                        }
                    } else {
                        System.out.println("ERROR EN REFERENCIA A CLASE. Linea: " + Analizador_Lexico.cantLineas + " variable "+val_peek(2).sval+" no declarada.");
                        GeneradorCod.cantErrores++;
                    }
                    Tabla_Simbolos.borrarSimbolo(val_peek(2).sval);
                    Tabla_Simbolos.borrarSimbolo(val_peek(0).sval);
                }
                break;
                case 141:
//#line 813 "gramatica.y"
                {
                    String id2 = isDeclarada(val_peek(0).sval, pilaAmbito.getElements());
                    yyval.sval = id2;
                    if (!id2.equals("")){
                        System.out.println("ERROR EN REFERENCIA A CLASE. Linea: " + Analizador_Lexico.cantLineas + " " + val_peek(0).sval + " no es un metodo o atributo de " + claseRef);
                        GeneradorCod.cantErrores++;
                    } else {
                        if(!claseRef.equals("")) {
                            String lexema = val_peek(0).sval + "@main@" +claseRef;
                            yyval.sval = lexema;
                            if (!Tabla_Simbolos.existeSimbolo(lexema)){
                                System.out.println("ERROR EN REFERENCIA A CLASE. Linea: " + Analizador_Lexico.cantLineas + " " + val_peek(0).sval + " no esta declarado dentro de " + claseRef);
                                GeneradorCod.cantErrores++;
                            }
                            claseRef = "";
                        }
                    }
                    Tabla_Simbolos.borrarSimbolo(val_peek(0).sval);
                }
                break;
                case 142:
//#line 835 "gramatica.y"
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
                case 143:
//#line 851 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN SENTENCIA FOR. Linea: " + Analizador_Lexico.cantLineas + " falta condicion");}
                break;
                case 144:
//#line 852 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN SENTENCIA FOR. Linea: " + Analizador_Lexico.cantLineas + " no se puede definir un FOR sin cuerpo");}
                break;
                case 145:
//#line 856 "gramatica.y"
                {
                    String id = isDeclarada(val_peek(2).sval, pilaAmbito.getElements());
                    if (id.equals("")){
                        System.out.println("ERROR EN SENTENCIA FOR. Linea: " + Analizador_Lexico.cantLineas + " " + val_peek(2).sval + " no esta declarada");
                        GeneradorCod.cantErrores++;
                    } else {
                        if (Tabla_Simbolos.getAtributos(id).isTipo("DOUBLE")){
                            GeneradorCod.cantErrores++;
                            System.out.println("ERROR EN INDICE FOR. Linea: " + Analizador_Lexico.cantLineas + " el indice debe ser de tipo ULONG o SHORT");
                        }
                        pilaIndices.apilar(id);
                    }
                    Tabla_Simbolos.borrarSimbolo(val_peek(2).sval);
                }
                break;
                case 146:
//#line 870 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN SENTENCIA FOR. Linea: " + Analizador_Lexico.cantLineas + " se esperaba un identificador");}
                break;
                case 147:
//#line 871 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN SENTENCIA FOR. Linea: " + Analizador_Lexico.cantLineas + " falta la palabra reservada IN");}
                break;
                case 148:
//#line 872 "gramatica.y"
                {GeneradorCod.cantErrores++; System.out.println("ERROR EN SENTENCIA FOR. Linea: " + Analizador_Lexico.cantLineas + " falta la palabra reservada RANGE");}
                break;
                case 149:
//#line 876 "gramatica.y"
                {
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
                                GeneradorCod.agregarTerceto(">", index, (String) val_peek(3).obj, tipo); /*condicion for*/
                            } else {
                                GeneradorCod.agregarTerceto("<", index, (String) val_peek(3).obj, tipo); /*condicion for*/
                            }
                            int indx = GeneradorCod.getIndexActual()-1;
                            pilaTercetos.apilar("[" + indx +"]"); /*apila el numero de terceto label */

                            Terceto incr = new Terceto("+", index, incremento, tipo);
                            pilaIndices.apilar(incr);

                            Terceto t = new Terceto("BF", "");
                            pilaTercetos.apilar(t);  /*apila primer bifurcacion incompleta*/
                            GeneradorCod.agregarTerceto(t); /*agrea la bif incompleta a los tercetos*/
                        }
                    }
                }
                break;
                case 150:
//#line 909 "gramatica.y"
                {GeneradorCod.cantErrores++;System.out.println("ERROR EN ENCABEZADO FOR. Linea: " + Analizador_Lexico.cantLineas + " falta una constante");}
                break;
                case 151:
//#line 910 "gramatica.y"
                {GeneradorCod.cantErrores++;System.out.println("ERROR EN ENCABEZADO FOR. Linea: " + Analizador_Lexico.cantLineas + " faltan constantes");}
                break;
//#line 1889 "Parser.java"
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
