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
import java.util.ArrayList;
//#line 22 "Parser.java"




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
            7,   12,   12,   12,   14,   13,   13,    8,    8,   15,
            15,   15,   15,   15,   16,   16,   17,   17,   18,   18,
            18,    9,    9,   19,   19,   19,   20,   20,   21,   21,
            22,   22,   22,   22,   10,   10,   10,   10,   23,   23,
            11,   11,   24,   25,   25,   26,   26,   26,   26,    4,
            4,    4,    4,    4,    4,    4,    4,    4,    4,    4,
            4,    4,    4,    4,   27,   27,   27,   27,   27,   27,
            27,   27,   27,   27,   27,   27,   32,   32,   32,   34,
            34,   34,   35,   35,   36,   36,   28,   28,   29,   37,
            37,   39,   39,   39,   39,   39,   39,   38,   38,   40,
            40,   41,   41,   30,   30,   31,   31,   33,   33,   33,
            33,   33,   33,   42,   42,   42,   43,
    };
    final static short yylen[] = {                            2,
            3,    2,    2,    2,    1,    1,    1,    2,    2,    1,
            2,    1,    1,    1,    1,    1,    1,    3,    3,    3,
            3,    1,    1,    1,    1,    1,    3,    3,    3,    4,
            6,    5,    5,    6,    3,    3,    2,    1,    1,    1,
            1,    3,    3,    2,    4,    4,    3,    3,    2,    1,
            1,    1,    2,    2,    6,    6,    6,    6,    3,    3,
            3,    3,    2,    3,    3,    2,    3,    1,    2,    1,
            2,    2,    2,    2,    2,    2,    5,    5,    4,    4,
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
            14,   15,   16,   17,    0,    0,    0,    0,    0,   70,
            0,    0,    0,    0,    0,    8,    0,    0,    0,    0,
            0,    0,    0,  125,  124,    0,    0,    0,    0,    0,
            0,   63,   84,   83,    0,    3,    4,   26,    0,    0,
            0,    0,    0,    0,    0,    0,   72,   71,   74,   73,
            76,   75,    0,    0,    0,    0,    0,   82,   81,  103,
            105,    0,    0,    0,    0,  102,  104,   89,  108,    0,
            0,  126,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    1,   20,   18,    0,
            21,   19,    0,   41,   39,   40,    0,   38,   29,   28,
            0,    0,   51,   52,    0,    0,   50,   43,   42,    0,
            0,    0,   62,   61,    0,    0,   91,    0,    0,    0,
            127,   90,  106,   94,   86,    0,    0,    0,    0,  107,
            93,   85,  111,    0,    0,    0,    0,    0,    0,  110,
            0,    0,   12,    0,  109,    0,  118,   46,   45,    0,
            30,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,   27,   36,   35,   37,   53,   48,   54,   47,
            49,   65,   66,   64,    0,   92,   96,   88,   80,   79,
            0,   95,   87,    0,    0,  100,  101,    0,    0,    0,
            0,    0,    0,  121,  120,   11,    0,    0,    0,   33,
            0,    0,    0,    0,  131,  130,    0,    0,    0,    0,
            0,    0,   67,   78,   77,    0,    0,   34,   31,    0,
            133,  128,  132,    0,    0,  129,   60,   59,   57,   58,
            55,   56,  123,  122,  137,    0,  136,    0,    0,    0,
            135,  134,
    };
    final static short yydgoto[] = {                         15,
            16,   17,   18,   19,    0,  164,   20,   21,   22,   23,
            24,   25,   59,   26,   27,   62,  117,  118,   28,   64,
            126,  127,  180,   29,   66,  132,   30,   31,   32,   33,
            34,   84,   35,   85,   86,   87,   43,   97,   95,   98,
            167,  176,  223,
    };
    final static short yysindex[] = {                      -103,
            60,  -12,   81, -139, -148, -132,    0,    0,    0, -160,
            -154, -113,   15,  174,    0,  -80,    0,    0,    0,    0,
            0,    0,    0,    0, -111, -111,   29,   52,   59,    0,
            30,   31,   32,   -4,   33,    0,    8, -118,    3,  -34,
            -79,    5,   71,    0,    0, -115,  -10, -152,  -75,  141,
            -46,    0,    0,    0,   48,    0,    0,    0,  -22,  -21,
            65,   34,  148,   35,  -98,   36,    0,    0,    0,    0,
            0,    0,   19,  -44,   17,  -34,  -43,    0,    0,    0,
            0,  -40,  -45,   11,   69,    0,    0,    0,    0,   94,
            22,    0,  -37,  -27,  177,   79,  -33,  -28, -128,    2,
            -35,  -39,  181,  181,  117,  -11,    0,    0,    0,   -2,
            0,    0,   26,    0,    0,    0,   78,    0,    0,    0,
            201,   40,    0,    0,   45,  155,    0,    0,    0,   42,
            241,  -84,    0,    0,   44,   25,    0,   37,  102,   28,
            0,    0,    0,    0,    0,  -34,  -34,  -34,  -34,    0,
            0,    0,    0,  -34,  -34,  -34,  -34,  -34,  -34,    0,
            -12,   50,    0,   92,    0,  184,    0,    0,    0,  256,
            0,   53,  268,   -1,  -30,  195,  195,  195,  -74,  275,
            117,  280,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,  282,    0,    0,    0,    0,    0,
            38,    0,    0,   69,   69,    0,    0,   89,   89,   89,
            89,   89,   89,    0,    0,    0,  105,   73,  286,    0,
            -127, -106,   95,   54,    0,    0,  100,  101,  212,  103,
            39,  104,    0,    0,    0,  116,  106,    0,    0,  129,
            0,    0,    0,  -30,  120,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,   55,    0,  -30,  121,  309,
            0,    0,
    };
    final static short yyrindex[] = {                         0,
            0,  122,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,  235,    0,    0,    0,    0,
            0,    0,    0,    0,  378,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,  -41,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,  123,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            122,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            -65,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,  -64,    0,    0,    0,    0,    0,
            0,    0,    0,  -36,  -31,    0,    0,  341,  342,  343,
            346,  347,  349,    0,    0,    0,    0,    0,  -38,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,
    };
    final static short yygindex[] = {                         0,
            377,   76,    0,   62,    0, -119,  310,   23,    0,    0,
            0,   41,  367,    0,  238,    0,    0,  277,    0,    0,
            0,  269,   -7,    0,    0,    0,    0,    0,    0,    0,
            0,   51,    0,    9,   49, -150,    0,    0,    0,    0,
            0,   -3,  -71,
    };
    final static int YYTABLESIZE=449;
    static short yytable[];
    static { yytable();}
    static void yytable(){
        yytable = new short[]{                         99,
                175,   99,   99,   99,   97,   32,   97,   97,   97,   98,
                83,   98,   98,   98,   83,  146,  221,  147,   99,   14,
                99,  109,  112,   97,  224,   97,  130,   39,   98,  100,
                98,   38,  158,   41,  159,   75,  110,  110,  175,   74,
                194,   77,  171,   89,   56,   93,  181,   83,   40,   83,
                228,   82,   83,  146,  145,  147,   76,  138,   54,   68,
                69,   83,  135,   83,  146,  152,  147,  146,  198,  147,
                146,  203,  147,   68,   70,   72,   79,  120,  129,  134,
                200,  235,  251,  116,   32,  124,   32,  101,  189,   90,
                91,   57,   94,  256,  245,  259,   48,  237,  182,  177,
                178,  240,   50,   36,  225,  226,  227,  260,   46,   49,
                148,  179,  244,  258,   51,  149,   44,  102,  103,   45,
                42,  221,  114,  136,   47,  139,  140,  168,  169,  161,
                57,  146,    3,  147,  150,    4,  146,   88,  147,  116,
                172,   10,  201,   52,  146,   58,  147,   13,  124,  241,
                242,   61,    1,    2,  204,  205,    3,  163,   99,    4,
                5,    6,    7,    8,    9,   10,    6,   61,   11,   12,
                222,   13,  107,  231,   63,    1,    2,   92,  114,    3,
                6,   65,    4,    5,    6,    7,    8,    9,   10,  113,
                6,   11,   12,   96,   13,  104,  206,  207,  105,   68,
                69,  229,  185,  162,  208,  209,  210,  211,  212,  213,
                106,  137,  143,  141,   99,  142,  215,  160,  153,   97,
                175,  173,   80,   81,   98,  216,   32,   81,  165,  236,
                254,  174,  166,  108,  111,   99,   99,   99,   99,  179,
                97,   97,   97,   97,  187,   98,   98,   98,   98,  154,
                155,  156,  157,  255,  183,    7,    8,    9,  170,   80,
                81,   80,   81,   37,   80,   81,  144,    7,    8,    9,
                53,   73,  122,   80,   81,   80,   81,  151,  163,  190,
                197,  184,  163,  202,  193,   67,   69,   71,   78,  119,
                128,  133,  199,  234,  250,  188,  218,  192,  216,  196,
                125,  216,  131,    1,    2,  214,  217,    3,  220,  219,
                4,    5,    6,    7,    8,    9,   10,  221,  230,   11,
                12,    2,   13,  232,    3,  233,  239,    4,  238,    6,
                7,    8,    9,   10,    2,  161,  248,    3,    3,   13,
                4,    4,    6,    7,    8,    9,   10,   10,  161,  262,
                243,    3,   13,   13,    4,  246,  247,   44,  249,  252,
                10,  161,  161,  125,    3,    3,   13,    4,    4,  195,
                115,  253,  123,   10,   10,  257,  261,    2,   25,   13,
                13,  112,  113,  116,  119,  161,  117,  114,    3,  115,
                55,    4,   60,  186,  191,    0,    0,   10,    0,    0,
                0,    0,    0,   13,  121,    0,    0,    0,    0,    0,
                0,  121,    6,    7,    8,    9,    0,    0,    0,    6,
                7,    8,    9,    0,    0,    0,  115,    0,    0,    1,
                2,    0,    0,    3,    0,  123,    4,    5,    6,    7,
                8,    9,   10,    0,    0,   11,   12,    0,   13,
        };
    }
    static short yycheck[];
    static { yycheck(); }
    static void yycheck() {
        yycheck = new short[] {                         41,
                40,   43,   44,   45,   41,   44,   43,   44,   45,   41,
                45,   43,   44,   45,   45,   43,  123,   45,   60,  123,
                62,   44,   44,   60,  175,   62,  125,   40,   60,   40,
                62,   44,   60,   46,   62,   40,   59,   59,   40,   44,
                125,   46,   41,   41,  125,   41,   58,   45,   61,   45,
                125,   44,   45,   43,   44,   45,   61,   41,   44,  125,
                125,   45,   44,   45,   43,   44,   45,   43,   44,   45,
                43,   44,   45,   44,   44,   44,   44,   44,   44,   44,
                44,   44,   44,   61,  123,   63,  125,   47,   44,   39,
                40,   16,   42,  244,   41,   41,  257,  217,  106,  103,
                104,  221,  257,   44,  176,  177,  178,  258,  257,  270,
                42,  123,   59,   59,  269,   47,  256,  270,  271,  259,
                40,  123,   61,   73,  257,   75,   76,  256,  257,  257,
                55,   43,  260,   45,   41,  263,   43,  256,   45,  117,
                100,  269,   41,  257,   43,  257,   45,  275,  126,  256,
                222,  123,  256,  257,  146,  147,  260,   96,  274,  263,
                264,  265,  266,  267,  268,  269,  265,  123,  272,  273,
                174,  275,  125,  181,  123,  256,  257,  257,  117,  260,
                265,  123,  263,  264,  265,  266,  267,  268,  269,  125,
                265,  272,  273,  123,  275,  271,  148,  149,   58,  265,
                265,  179,  125,  125,  154,  155,  156,  157,  158,  159,
                257,  256,  258,  257,  256,  256,  125,   41,  256,  256,
                40,  257,  257,  258,  256,  164,  265,  258,  262,  125,
                125,  271,  261,  256,  256,  277,  278,  279,  280,  123,
                277,  278,  279,  280,   44,  277,  278,  279,  280,  277,
                278,  279,  280,  125,  257,  266,  267,  268,  257,  257,
                258,  257,  258,  276,  257,  258,  256,  266,  267,  268,
                256,  276,  125,  257,  258,  257,  258,  256,  217,  125,
                256,  256,  221,  256,   44,  256,  256,  256,  256,  256,
                256,  256,  256,  256,  256,  256,   41,  256,  237,  256,
                63,  240,   65,  256,  257,  256,  123,  260,   41,  257,
                263,  264,  265,  266,  267,  268,  269,  123,   44,  272,
                273,  257,  275,   44,  260,   44,   41,  263,  256,  265,
                266,  267,  268,  269,  257,  257,  125,  260,  260,  275,
                263,  263,  265,  266,  267,  268,  269,  269,  257,   41,
                256,  260,  275,  275,  263,  256,  256,  123,  256,  256,
                269,  257,  257,  126,  260,  260,  275,  263,  263,  132,
                61,  256,   63,  269,  269,  256,  256,    0,  257,  275,
                275,   41,   41,   41,  262,  257,   41,   41,  260,   41,
                14,  263,   26,  117,  126,   -1,   -1,  269,   -1,   -1,
                -1,   -1,   -1,  275,  257,   -1,   -1,   -1,   -1,   -1,
                -1,  257,  265,  266,  267,  268,   -1,   -1,   -1,  265,
                266,  267,  268,   -1,   -1,   -1,  117,   -1,   -1,  256,
                257,   -1,   -1,  260,   -1,  126,  263,  264,  265,  266,
                267,  268,  269,   -1,   -1,  272,  273,   -1,  275,
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
            "declaracion_distribuida : IMPL FOR ID ':' cuerpo_dec_dist ','",
            "declaracion_distribuida : IMPL FOR ID cuerpo_dec_dist ',' error",
            "declaracion_distribuida : IMPL ID ':' cuerpo_dec_dist ',' error",
            "declaracion_distribuida : IMPL FOR ID ':' cuerpo_dec_dist error",
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

    //#line 486 "gramatica.y"
    /* CODE SECTION */
    public String tipo = "";
    public Pila pilaAmbito = new Pila("main");
    public String claseRef = "";

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

    public int yylex(){
        int tok = Analizador_Lexico.yylex();
        yylval = new ParserVal(Analizador_Lexico.buffer);
        return tok;
    }
    public void yyerror(String s){}
    //#line 612 "Parser.java"
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
//#line 15 "gramatica.y"
                {System.out.println("ERROR EN PROGRAMA. Linea: " + Analizador_Lexico.cantLineas + " se esperaba '}'");}
                break;
                case 3:
//#line 16 "gramatica.y"
                {System.out.println("ERROR EN PROGRAMA. Linea: " + Analizador_Lexico.cantLineas + " se esperaba '{'");}
                break;
                case 8:
//#line 25 "gramatica.y"
                {System.out.println("ERROR EN SENTENCIA. Linea: " + Analizador_Lexico.cantLineas);}
                break;
                case 18:
//#line 44 "gramatica.y"
                {
                    System.out.println("Linea: " + Analizador_Lexico.cantLineas + " Declaracion de VARIABLE/S de TIPO " + val_peek(2).sval);
                }
                break;
                case 19:
//#line 48 "gramatica.y"
                {
                    System.out.println("Linea: " + Analizador_Lexico.cantLineas + " Declaracion de VARIABLE/S de TIPO " + val_peek(2).sval);
                }
                break;
                case 20:
//#line 51 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DE VARIABLES. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 21:
//#line 52 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DE VARIABLES. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 22:
//#line 55 "gramatica.y"
                {tipo = val_peek(0).sval;}
                break;
                case 23:
//#line 56 "gramatica.y"
                {tipo = val_peek(0).sval;}
                break;
                case 24:
//#line 57 "gramatica.y"
                {tipo = val_peek(0).sval;}
                break;
                case 25:
//#line 60 "gramatica.y"
                {
                    if (Tabla_Simbolos.getAtributos(val_peek(0).sval+"#main").isUso("CLASE")){
                        tipo = val_peek(0).sval;
                    } else {
                        System.out.println("ERROR EN DECLARACION DE VARIABLES. Linea: "+Analizador_Lexico.cantLineas+": "+val_peek(0).sval+" no es tipo CLASE");
                    }
                }
                break;
                case 26:
//#line 69 "gramatica.y"
                {
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
                case 27:
//#line 81 "gramatica.y"
                {
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
                case 28:
//#line 95 "gramatica.y"
                {
                    String am = (String) pilaAmbito.desapilar();
                    Tabla_Simbolos.getAtributos(concatenarAmbito(am, pilaAmbito.getElements())).setImplementado(true);
                    /*pone en True el booleano isImplementado para las funciones. */
                    /*Sirve para saber si:*/
                    /*--> una clase implemento la funcion o solo declaro el encabezado. */
                    /*--> la funcion puede usarse (tiene que tener cuerpo)*/
                    /*--> una clase que implementa una interfaz, implemento todos los metodos*/
                }
                break;
                case 29:
//#line 104 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DE FUNCION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 30:
//#line 108 "gramatica.y"
                {
                    /*poner ambito a IDfuncion, apilar nuevo ambito*/
                    System.out.println("Linea: " + Analizador_Lexico.cantLineas + " Declaracion funcion VOID " + val_peek(2).sval);
                    Tabla_Simbolos.getAtributos(val_peek(2).sval).setUso("FUNCION");
                    if (!setAmbito(val_peek(2).sval)){
                        System.out.println("ERROR. REDECLARACION DE NOMBRE. Linea: " + Analizador_Lexico.cantLineas);
                    };
                    pilaAmbito.apilar(val_peek(2).sval);
                }
                break;
                case 31:
//#line 118 "gramatica.y"
                {
                    System.out.println("Linea: " + Analizador_Lexico.cantLineas + " Declaracion funcion VOID " + val_peek(4).sval);
                    Tabla_Simbolos.getAtributos(val_peek(4).sval).setUso("FUNCION");
                    Tabla_Simbolos.getAtributos(val_peek(1).sval).setUso("PARAMETRO");
                    Tabla_Simbolos.getAtributos(val_peek(1).sval).setTipo(val_peek(2).sval);
                    if (!setAmbito(val_peek(4).sval)){
                        System.out.println("ERROR. REDECLARACION DE NOMBRE. Linea: " + Analizador_Lexico.cantLineas);
                    };
                    pilaAmbito.apilar(val_peek(4).sval);
                    setAmbito(val_peek(1).sval);
                }
                break;
                case 32:
//#line 129 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DE FUNCION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ')'");}
                break;
                case 33:
//#line 130 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DE FUNCION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba '('");}
                break;
                case 34:
//#line 131 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DE FUNCION. Linea: " + Analizador_Lexico.cantLineas + " falta el tipo de " + val_peek(2).sval);}
                break;
                case 36:
//#line 136 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DE FUNCION. Linea: " + Analizador_Lexico.cantLineas + " no se puede declarar una funcion sin cuerpo");}
                break;
                case 42:
//#line 148 "gramatica.y"
                {pilaAmbito.desapilar();}
                break;
                case 43:
//#line 149 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DE CLASE. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 44:
//#line 153 "gramatica.y"
                {
                    System.out.println("Linea: " + Analizador_Lexico.cantLineas + " Declaracion CLASE " + val_peek(0).sval);
                    Tabla_Simbolos.getAtributos(val_peek(0).sval).setUso("CLASE");
                    if (!setAmbito(val_peek(0).sval)){
                        System.out.println("ERROR. REDECLARACION DE NOMBRE. Linea: " + Analizador_Lexico.cantLineas);
                    };
                    pilaAmbito.apilar(val_peek(0).sval);

                }
                break;
                case 45:
//#line 163 "gramatica.y"
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
                case 46:
//#line 179 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DE CLASE. Linea: " + Analizador_Lexico.cantLineas + " falta el identificador de la interfaz");}
                break;
                case 48:
//#line 185 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DE CLASE. Linea: " + Analizador_Lexico.cantLineas + " no se puede definir una clase sin cuerpo");}
                break;
                case 53:
//#line 195 "gramatica.y"
                {
                    if (Tabla_Simbolos.getAtributos(val_peek(1).sval+"#main").isUso("CLASE")){
                        Tabla_Simbolos.getAtributos((String)pilaAmbito.getTope()+"#main").setHereda(val_peek(1).sval);
                        Tabla_Simbolos.borrarSimbolo(val_peek(1).sval);
                        /*si ID es una clase, entonces la clase donde se define esta linea debe heredar de ID.*/
                    }
                }
                break;
                case 54:
//#line 202 "gramatica.y"
                {pilaAmbito.desapilar();}
                break;
                case 55:
//#line 206 "gramatica.y"
                {
                    AtributosLexema atributos = Tabla_Simbolos.getAtributos(val_peek(3).sval+"#main");
                    if ((atributos != null) && (atributos.isUso("CLASE"))){
                        /*pongo como ambito la clase del metodo que voy a implementar*/
                        /*pilaAmbito.apilar($3.sval);*/
                        /*NO SIRVE APILAR ACA PORQUE LEE TARDE ESTE CODIGO. VER EN DECLARACION FUNCION.*/
                        /*me fijo que lo que estoy implementando este declarado y no este implementado.*/

                    } else {
                        System.out.println("ERROR EN DECLARACION DISTRIBUIDA. Linea: " + Analizador_Lexico.cantLineas + " " + val_peek(3).sval + " no es una clase ");
                    }
                    System.out.println("Linea: " + Analizador_Lexico.cantLineas + " Declaracion DISTRIBUIDA para " + val_peek(3).sval);
                    /*se borra ID de la tabla de simbolos porque el lexico lo inserta al reconocer un identificador, */
                    /*y como el original tiene el nombre cambiado por el ambito, existiran ambos en la TS*/
                    Tabla_Simbolos.borrarSimbolo(val_peek(3).sval);
                }
                break;
                case 56:
//#line 222 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DISTRIBUIDA. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ':'");}
                break;
                case 57:
//#line 223 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DISTRIBUIDA. Linea: " + Analizador_Lexico.cantLineas + " falta palabra reservada FOR");}
                break;
                case 58:
//#line 224 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DISTRIBUIDA. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 60:
//#line 228 "gramatica.y"
                {System.out.println("ERROR EN DECLARACION DISTRIBUIDA. Linea: " + Analizador_Lexico.cantLineas + " no se puede definir una declaracion distribuida sin cuerpo");}
                break;
                case 61:
//#line 231 "gramatica.y"
                {pilaAmbito.desapilar();}
                break;
                case 62:
//#line 232 "gramatica.y"
                {System.out.println("ERROR EN INTERFAZ. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 63:
//#line 236 "gramatica.y"
                {
                    System.out.println("Linea: " + Analizador_Lexico.cantLineas + " Declaracion INTERFAZ " + val_peek(0).sval);
                    Tabla_Simbolos.getAtributos(val_peek(0).sval).setUso("INTERFAZ");
                    if (!setAmbito(val_peek(0).sval)){
                        System.out.println("ERROR. REDECLARACION DE NOMBRE. Linea: " + Analizador_Lexico.cantLineas);
                    };
                    pilaAmbito.apilar(val_peek(0).sval);
                }
                break;
                case 65:
//#line 247 "gramatica.y"
                {System.out.println("ERROR EN INTERFAZ. Linea: " + Analizador_Lexico.cantLineas + " no se puede declarar una interfaz sin metodos");}
                break;
                case 66:
//#line 250 "gramatica.y"
                {pilaAmbito.desapilar();}
                break;
                case 67:
//#line 251 "gramatica.y"
                {pilaAmbito.desapilar();}
                break;
                case 68:
//#line 252 "gramatica.y"
                {System.out.println("ERROR EN METODO DE INTERFAZ. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 69:
//#line 253 "gramatica.y"
                {System.out.println("ERROR EN METODO DE INTERFAZ. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 70:
//#line 256 "gramatica.y"
                {System.out.println("Linea: " + Analizador_Lexico.cantLineas + " ASIGNACION");}
                break;
                case 71:
//#line 257 "gramatica.y"
                {System.out.println("Linea: " + Analizador_Lexico.cantLineas + " INVOCACION FUNCION");}
                break;
                case 72:
//#line 258 "gramatica.y"
                {System.out.println("ERROR EN INVOCACION A LA FUNCION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 73:
//#line 259 "gramatica.y"
                {System.out.println("Linea: " + Analizador_Lexico.cantLineas + " Sentencia IF");}
                break;
                case 74:
//#line 260 "gramatica.y"
                {System.out.println("ERROR EN SENTENCIA IF. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 75:
//#line 261 "gramatica.y"
                {System.out.println("Linea: " + Analizador_Lexico.cantLineas + " SENTENCIA DE IMPRESION");}
                break;
                case 76:
//#line 262 "gramatica.y"
                {System.out.println("ERROR EN SENTENCIA DE IMPRESION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 77:
//#line 264 "gramatica.y"
                {
                    System.out.println("Linea: " + Analizador_Lexico.cantLineas + " REFERENCIA A CLASE");
                    /*chequear que coincida con lo declarado en el metodo, sea tipo o cantidad*/
                }
                break;
                case 78:
//#line 268 "gramatica.y"
                {System.out.println("ERROR EN REFERENCIA A CLASE. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 79:
//#line 270 "gramatica.y"
                {
                    System.out.println("Linea: " + Analizador_Lexico.cantLineas + " REFERENCIA A CLASE");
                    /*chequear que coincida en cantidad de parametros*/
                }
                break;
                case 80:
//#line 274 "gramatica.y"
                {System.out.println("ERROR EN REFERENCIA A CLASE. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 82:
//#line 276 "gramatica.y"
                {System.out.println("ERROR EN SENTENCIA FOR. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 84:
//#line 278 "gramatica.y"
                {System.out.println("ERROR EN RETURN. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 85:
//#line 282 "gramatica.y"
                {
                    String var = isDeclarada(val_peek(3).sval, pilaAmbito.getElements());
                    if (var.equals("")){
                        System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " variable "+val_peek(3).sval+" no declarada.");
                    } else {
                        if (!Tabla_Simbolos.getAtributos(var).isUso("VARIABLE")){
                            System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas +val_peek(3).sval+" no es una variable.");
                        }
                    }
                }
                break;
                case 86:
//#line 293 "gramatica.y"
                {
                    String var = isDeclarada(val_peek(3).sval, pilaAmbito.getElements());
                    if (var.equals("")){
                        System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " variable "+val_peek(3).sval+" no declarada.");
                    } else {
                        if (!Tabla_Simbolos.getAtributos(var).isUso("VARIABLE")){
                            System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas +val_peek(3).sval+" no es una variable.");
                        }
                    }
                }
                break;
                case 89:
//#line 305 "gramatica.y"
                {System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " Se esperaba expresion");}
                break;
                case 90:
//#line 306 "gramatica.y"
                {System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " Se esperaba expresion");}
                break;
                case 91:
//#line 307 "gramatica.y"
                {System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " Se esperaba expresion");}
                break;
                case 92:
//#line 308 "gramatica.y"
                {System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " Se esperaba expresion");}
                break;
                case 93:
//#line 309 "gramatica.y"
                {System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 94:
//#line 310 "gramatica.y"
                {System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 95:
//#line 311 "gramatica.y"
                {System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 96:
//#line 312 "gramatica.y"
                {System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba ','");}
                break;
                case 97:
//#line 315 "gramatica.y"
                {System.out.println("Linea: " + Analizador_Lexico.cantLineas + " SUMA");}
                break;
                case 98:
//#line 316 "gramatica.y"
                {System.out.println("Linea: " + Analizador_Lexico.cantLineas + " RESTA");}
                break;
                case 100:
//#line 320 "gramatica.y"
                {System.out.println("Linea: " + Analizador_Lexico.cantLineas + " MULTIPLICACION");}
                break;
                case 101:
//#line 321 "gramatica.y"
                {System.out.println("Linea: " + Analizador_Lexico.cantLineas + " DIVISION");}
                break;
                case 103:
//#line 326 "gramatica.y"
                {
                    if (isDeclarada(val_peek(0).sval, pilaAmbito.getElements()).equals("")){
                        System.out.println("ERROR EN ASIGNACION. Linea: " + Analizador_Lexico.cantLineas + " variable "+val_peek(0).sval+" no declarada.");
                    };
                }
                break;
                case 105:
//#line 334 "gramatica.y"
                { chequeoRango(val_peek(0).sval);
                    Tabla_Simbolos.getAtributos(val_peek(0).sval).sumarUso();
                }
                break;
                case 106:
//#line 337 "gramatica.y"
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
                case 107:
//#line 351 "gramatica.y"
                {
                    String fun = isDeclarada(val_peek(3).sval, pilaAmbito.getElements());
                    if (fun.equals("")){
                        System.out.println("ERROR EN INVOCACION A FUNCION. Linea: " + Analizador_Lexico.cantLineas + " la funcion "+val_peek(3).sval+" no esta declarada.");
                    } else {
                        AtributosLexema at = Tabla_Simbolos.getAtributos(fun);
                        if (at.isUso("FUNCION")){
                            /*CHEQUEO DE CANTIDAD de PARAMETROS*/
                            if (at.tieneParametro()){
                                /*CHEQUEO DE TIPO DE PARAMETRO: en una variable "TIPO" debera almacenarse el tipo resultante de la operacion*/
                                /*entre los operandos que integran "expresion". PE: Conversor.getTipo(operando1, operando2) devuelve null si*/
                                /*no son compatibles o el tipo si lo son.*/
                                /*Luego verificar si coincide el tipo del parametro formal con el real*/
                                /* if (at.coincideTipoParametro(tipodelparametroreal)){*/
                                /*     //realizar COPIAVALOR*/
                                /* } else {*/
                                /*     System.out.println("ERROR EN INVOCACION A FUNCION. Linea: " + Analizador_Lexico.cantLineas + " no coinciden los tipos de los parametros.");*/
                                /* }*/
                            } else {
                                System.out.println("ERROR EN INVOCACION A FUNCION. Linea: " + Analizador_Lexico.cantLineas + " no coincide la cantidad de parametros.");
                            }
                        } else {
                            System.out.println("ERROR EN INVOCACION A FUNCION. Linea: " + Analizador_Lexico.cantLineas +val_peek(3).sval+" no es una funcion");
                        }
                    }
                }
                break;
                case 108:
//#line 378 "gramatica.y"
                {
                    String fun = isDeclarada(val_peek(2).sval, pilaAmbito.getElements());
                    if (fun.equals("")){
                        System.out.println("ERROR EN INVOCACION A FUNCION. Linea: " + Analizador_Lexico.cantLineas + " la funcion "+val_peek(2).sval+" no esta declarada.");
                    } else {
                        AtributosLexema at = Tabla_Simbolos.getAtributos(fun);
                        if (at.isUso("FUNCION")){
                            /*CHEQUEO DE CANTIDAD de PARAMETROS*/
                            if (at.tieneParametro()){
                                System.out.println("ERROR EN INVOCACION A FUNCION. Linea: " + Analizador_Lexico.cantLineas + " no coincide la cantidad de parametros.");
                            }
                        } else {
                            System.out.println("ERROR EN INVOCACION A FUNCION. Linea: " + Analizador_Lexico.cantLineas +val_peek(2).sval+" no es una funcion.");
                        }
                    }
                }
                break;
                case 111:
//#line 400 "gramatica.y"
                {System.out.println("ERROR EN SENTENCIA IF. Linea: " + Analizador_Lexico.cantLineas + " falta condicion");}
                break;
                case 121:
//#line 416 "gramatica.y"
                {System.out.println("ERROR EN SENTENCIA IF. Linea: " + Analizador_Lexico.cantLineas + " cuerpo de IF vacio");}
                break;
                case 123:
//#line 420 "gramatica.y"
                {System.out.println("ERROR EN SENTENCIA IF. Linea: " + Analizador_Lexico.cantLineas + " cuerpo de ELSE vacio");}
                break;
                case 125:
//#line 424 "gramatica.y"
                {System.out.println("ERROR EN SENTENCIA DE IMPRESION. Linea: " + Analizador_Lexico.cantLineas + " se esperaba una cadena de caracteres");}
                break;
                case 126:
//#line 428 "gramatica.y"
                {
                    String id1 = isDeclarada(val_peek(2).sval, pilaAmbito.getElements());
                    String id2 = isDeclarada(val_peek(0).sval, pilaAmbito.getElements());
                    if (!id1.equals("")){
                        String tipo = Tabla_Simbolos.getAtributos(id1).getTipo();
                        AtributosLexema atributos = Tabla_Simbolos.getAtributos(tipo+"#main");
                        if ((atributos != null) && (atributos.isUso("CLASE"))){
                            if (!id2.equals("")){
                                /*es una clase*/
                                if (hereda(tipo, val_peek(0).sval)){
                                    claseRef = val_peek(0).sval;
                                } else {
                                    System.out.println("ERROR EN REFERENCIA A CLASE. Linea: " + Analizador_Lexico.cantLineas +" "+tipo+" no hereda de "+val_peek(0).sval);
                                }
                            } else {
                                if (!Tabla_Simbolos.existeSimbolo(val_peek(0).sval + "#main#"+tipo)){
                                    System.out.println("ERROR EN REFERENCIA A CLASE. Linea: " + Analizador_Lexico.cantLineas +" "+val_peek(0).sval+" no esta declarado dentro de "+val_peek(2).sval);
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
                case 127:
//#line 455 "gramatica.y"
                {
                    String id2 = isDeclarada(val_peek(0).sval, pilaAmbito.getElements());
                    /* cuando no da vacio?? (Cuando entra al if)*/
                    if (!id2.equals("")){
                        System.out.println("ERROR EN REFERENCIA A CLASE. Linea: " + Analizador_Lexico.cantLineas + " " + val_peek(0).sval + " no es un metodo o atributo de " + claseRef);
                    } else {
                        String lexema = val_peek(0).sval + "#main#" +claseRef;
                        if (!Tabla_Simbolos.existeSimbolo(lexema)){
                            System.out.println("ERROR EN REFERENCIA A CLASE. Linea: " + Analizador_Lexico.cantLineas + " " + val_peek(0).sval + " no esta declarado dentro de " + claseRef);
                        }
                    }
                }
                break;
                case 128:
//#line 469 "gramatica.y"
                {System.out.println("Linea: " + Analizador_Lexico.cantLineas + " Sentencia FOR");}
                break;
                case 129:
//#line 470 "gramatica.y"
                {System.out.println("ERROR EN SENTENCIA FOR. Linea: " + Analizador_Lexico.cantLineas + " se esperaba un identificador");}
                break;
                case 130:
//#line 471 "gramatica.y"
                {System.out.println("ERROR EN SENTENCIA FOR. Linea: " + Analizador_Lexico.cantLineas + " falta la palabra reservada IN");}
                break;
                case 131:
//#line 472 "gramatica.y"
                {System.out.println("ERROR EN SENTENCIA FOR. Linea: " + Analizador_Lexico.cantLineas + " falta la palabra reservada RANGE");}
                break;
                case 132:
//#line 473 "gramatica.y"
                {System.out.println("ERROR EN SENTENCIA FOR. Linea: " + Analizador_Lexico.cantLineas + " falta encabezado");}
                break;
                case 133:
//#line 474 "gramatica.y"
                {System.out.println("ERROR EN SENTENCIA FOR. Linea: " + Analizador_Lexico.cantLineas + " no se puede definir un FOR sin cuerpo");}
                break;
                case 134:
//#line 477 "gramatica.y"
                {System.out.println("Linea: " + Analizador_Lexico.cantLineas + " ENCABEZADO FOR");}
                break;
                case 135:
//#line 478 "gramatica.y"
                {System.out.println("ERROR EN ENCABEZADO FOR. Linea: " + Analizador_Lexico.cantLineas + " falta una constante");}
                break;
                case 136:
//#line 479 "gramatica.y"
                {System.out.println("ERROR EN ENCABEZADO FOR. Linea: " + Analizador_Lexico.cantLineas + " faltan constantes");}
                break;
//#line 1340 "Parser.java"
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
