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
            10,   10,   10,   10,   11,   11,   11,   11,   11,   12,
            12,   12,   12,   13,   13,   14,   14,   14,   14,   15,
            15,   15,   15,   15,    4,    4,    4,    4,    4,    4,
            4,    4,    4,    4,   16,   16,   16,   16,   16,   16,
            16,   16,   16,   16,   16,   16,   22,   22,   22,   23,
            23,   23,   24,   24,   24,   17,   17,   17,   17,   18,
            18,   18,   18,   18,   18,   25,   25,   25,   25,   25,
            25,   19,   19,   19,   20,   20,   21,   21,   21,   21,
            21,   21,   21,   21,
    };
    final static short yylen[] = {                            2,
            3,    2,    2,    2,    1,    1,    1,    2,    1,    2,
            1,    3,    0,    4,    1,    1,    1,    1,    3,    3,
            1,    1,    1,    1,    3,    8,   10,    9,    9,   10,
            10,    8,    8,   10,    5,    7,    5,    7,    2,    7,
            6,    7,    7,    5,    5,    2,    3,    1,    2,    6,
            4,    5,    5,    6,    1,    1,    2,    2,    1,    4,
            1,    2,    2,    4,    4,    4,    4,    4,    4,    4,
            4,    4,    4,    4,    4,    4,    3,    3,    1,    3,
            3,    1,    1,    1,    2,    5,    4,    5,    4,   12,
            8,    8,   12,    6,   10,    3,    3,    3,    3,    3,
            3,    3,    3,    3,    3,    3,   14,   12,   10,    9,
            14,   13,   13,   12,
    };
    final static short yydefred[] = {                         0,
            0,    0,    0,    0,    0,   21,   22,   23,    0,    0,
            0,    0,    0,    0,    0,    5,    6,    7,    0,   15,
            16,   17,   18,   55,   56,    0,   59,    0,   61,   24,
            0,    0,    0,    0,    0,    0,    0,    0,   39,    0,
            0,    0,    0,    0,    0,    0,   63,   62,    0,    3,
            4,    0,   58,   57,    0,    0,    0,    0,   83,   84,
            0,    0,    0,    0,   82,    0,    0,    0,    0,  105,
            20,   13,    0,    0,    0,    0,  104,  102,  103,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    1,
            19,   12,    0,    0,    0,    0,    0,  106,   70,   85,
            74,   66,    0,    0,    0,    0,   89,   87,    0,   69,
            73,   65,   14,   25,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    9,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,   72,   76,   68,   64,   60,   71,   75,   67,
            0,    0,   80,   81,   88,   86,    0,   11,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,   37,
            35,    8,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,   45,   44,    0,   46,    0,   10,
            0,   94,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,   41,    0,    0,
            0,    0,   47,    0,    0,    0,    0,   38,   36,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,   43,   42,   40,    0,   51,    0,    0,    0,   92,
            0,    0,   91,    0,   32,   33,   26,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,   53,
            0,    0,    0,    0,   28,    0,    0,   29,    0,    0,
            0,  110,    0,    0,    0,   54,   50,    0,   95,    0,
            30,   31,   34,   27,    0,    0,  109,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,   93,   90,  114,
            0,  108,    0,    0,    0,    0,  113,  112,    0,  107,
            111,
    };
    final static short yydgoto[] = {                         14,
            15,   16,   17,   18,  127,  159,   19,   35,  113,   20,
            21,   22,   23,  141,  142,   24,   25,   26,   27,   28,
            29,   63,   64,   65,   76,
    };
    final static short yysindex[] = {                       -98,
            -12,    8,  -33, -191, -151,    0,    0,    0, -154, -119,
            -149,   11, -140,    0,  -84,    0,    0,    0, -146,    0,
            0,    0,    0,    0,    0,   52,    0,  -10,    0,    0,
            24,   19,   33, -142,   50,   22,   53, -134,    0,  -96,
            3, -157, -125,   89, -109,   31,    0,    0,   61,    0,
            0,   51,    0,    0,   35,  115,   39, -100,    0,    0,
            -95,  -94,   27,   34,    0,   54,   -1,  -93,   42,    0,
            0,    0,  -99,   49,  -27,  154,    0,    0,    0,  -61,
            -64,   18,  -59,  -17,  159,  167,   90,  -19, -110,    0,
            0,    0,  -42,   45,   55,  -40,   48,    0,    0,    0,
            0,    0,    2,    2,    2,    2,    0,    0,   56,    0,
            0,    0,    0,    0, -126,    2,    2,    2,    2,    2,
            2,  -50,   95, -146,  -37,    0,  356,  180,  111,  -22,
            199,  215,    4,   37,   44,   67,   67,  138,   43,    9,
            -75,  271,    0,    0,    0,    0,    0,    0,    0,    0,
            34,   34,    0,    0,    0,    0,   -8,    0,   99,   62,
            62,   62,   62,   62,   62,  194,   63, -126,  375,    0,
            0,    0,  200,   85,   -4,  212,  -35,  263,  280,  284,
            221,  241,  -73,    6,    0,    0,  293,    0, -122,    0,
            -126,    0,  104,  119,  389, -140,  327,  133, -140,  253,
            -140,   71,  254,  120,  122,  123,  126,    0,  128,  260,
            21,  129,    0,  264,  132,  174, -120,    0,    0,  230,
            135,   57,  244,  258,  278,  131,  269, -126,  335,  345,
            350,    0,    0,    0,  369,    0,  155,  370, -126,    0,
            151,  291,    0,  371,    0,    0,    0,  372,  376,  292,
            377,   77, -126,  178,  161,  164,  165,  183,  404,    0,
            191,  205, -126,  206,    0,  207,   58,    0,  172,  333,
            195,    0,  428,  430,  431,    0,    0,  216,    0,  311,
            0,    0,    0,    0,  438, -126,    0,  354,  363,  365,
            233,  229, -103,  319, -126, -126, -126,    0,    0,    0,
            -126,    0,  321,  332,  342,  343,    0,    0,  236,    0,
            0,
    };
    final static short yyrindex[] = {                         0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,  506,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,  -41,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,  -71,    0,    0,    0,    0,    0,    0,    0,    0,
            -36,  -31,    0,    0,    0,    0,    0,    0,    0,  479,
            487,  488,  491,  493,  495,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,  -68,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,  -43,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,
    };
    final static short yygindex[] = {                         0,
            32,    7,  136,  179,  368,  436,  -24,  520,    0,  -62,
            0,    0,    0,    0,  399,    0,    0,    0,    0,    0,
            0,  308,   41,   46,    0,
    };
    final static int YYTABLESIZE=737;
    static short yytable[];
    static { yytable();}
    static void yytable(){
        yytable = new short[]{                         79,
                52,   79,   79,   79,   77,  203,   77,   77,   77,   78,
                38,   78,   78,   78,  140,  103,   83,  104,   79,  301,
                79,   51,  133,   77,   13,   77,   81,   32,   78,   56,
                78,   32,  120,   34,  121,   58,  200,   34,  138,  109,
                50,  103,   82,  104,   49,  211,   62,   36,   33,  186,
                57,  209,   33,   48,   48,   51,   49,  130,  129,   66,
                125,  236,   74,   62,   39,   40,   62,   61,   62,  103,
                102,  104,  168,  181,  182,  105,   68,   62,   93,   62,
                106,   52,   96,   62,  103,  112,  104,  103,  145,  104,
                103,  150,  104,   72,   92,   54,   78,  108,  147,  156,
                247,  284,   42,  137,  103,   41,  104,   46,   73,   73,
                30,  227,   84,   85,   70,   43,    1,  270,  199,    2,
                210,   79,    3,    4,    5,    6,    7,    8,    9,  226,
                157,   10,   11,    2,   12,  269,    3,   44,  214,  215,
                242,  243,    9,  151,  152,   86,   87,   88,   12,   45,
                153,  154,  300,   89,  139,   95,   98,  114,    1,  212,
                99,    2,  110,  100,    3,    4,    5,    6,    7,    8,
                9,  115,    1,   10,   11,    2,   12,   80,    3,    4,
                5,    6,    7,    8,    9,   90,  237,   10,   11,  139,
                12,    5,  124,   48,  122,  123,   49,  131,  134,    4,
                5,    6,    7,    8,   51,  198,  135,   10,   11,  197,
                166,  167,  136,  143,   79,  148,  126,  169,  170,   77,
                173,   52,  202,  189,   78,   37,   51,  220,  217,   51,
                223,   51,  225,  174,  175,   79,   79,   79,   79,  176,
                77,   77,   77,   77,   30,   78,   78,   78,   78,  116,
                117,  118,  119,  132,  177,  250,   51,  222,   59,   60,
                183,  178,  172,   31,  185,   55,   47,   31,    6,    7,
                8,    6,    7,    8,  128,   59,   60,  235,   59,   60,
                59,   60,  101,    6,    7,    8,    6,    7,    8,   59,
                60,   59,   60,  158,  179,   59,   60,  111,  241,  184,
                144,  180,  272,  149,  126,   71,   91,   53,   77,  107,
                146,  155,  246,  283,  188,  278,  191,    1,  192,  287,
                2,  204,  196,    3,    4,    5,    6,    7,    8,    9,
                172,    5,   10,   11,  201,   12,  213,  190,  205,   67,
                69,    1,  206,   75,    2,  207,  158,    3,    4,    5,
                6,    7,    8,    9,  244,  157,   10,   11,    2,   12,
                157,    3,   94,    2,   97,  208,    3,    9,  248,  158,
                221,  190,    9,   12,  218,  224,  228,  229,   12,  230,
                231,  232,  249,  233,  234,  238,  239,  240,  252,    1,
                245,  253,    2,  255,  190,    3,    4,    5,    6,    7,
                8,    9,  251,  256,   10,   11,  158,   12,  257,  258,
                260,  259,  262,  263,  264,  265,  267,  158,  273,  266,
                268,  274,  275,  160,  161,  162,  163,  164,  165,  285,
                157,  158,  190,    2,  157,  292,    3,    2,  276,  190,
                3,  158,    9,  302,  277,  307,    9,  157,   12,  190,
                2,  157,   12,    3,    2,  286,  308,    3,  190,    9,
                279,  281,  282,    9,  158,   12,  309,  310,  288,   12,
                289,  290,  190,  158,  158,  158,  295,  291,  293,  158,
                171,  190,  190,  190,  190,  296,    1,  297,  298,    2,
                299,  311,    3,    4,    5,    6,    7,    8,    9,  194,
                1,   10,   11,    2,   12,    2,    3,    4,    5,    6,
                7,    8,    9,  219,    1,   10,   11,    2,   12,   96,
                3,    4,    5,    6,    7,    8,    9,   97,  100,   10,
                11,  101,   12,   98,    1,   99,  195,    2,   52,  187,
                3,    4,    5,    6,    7,    8,    9,    0,    1,   10,
                11,    2,   12,    0,    3,    4,    5,    6,    7,    8,
                9,    0,    0,   10,   11,    0,   12,  157,    0,    0,
                2,    0,    0,    3,    0,  157,    0,  157,    2,    9,
                2,    3,    0,    3,    0,   12,    0,    9,  157,    9,
                0,    2,    0,   12,    3,   12,    0,    0,  157,  157,
                9,    2,    2,  193,    3,    3,   12,    0,    0,    0,
                9,    9,  124,    0,    0,    0,   12,   12,    0,    4,
                5,    6,    7,    8,    0,    0,  216,   10,   11,    0,
                0,  124,    0,    0,    0,    0,    0,    0,    4,    5,
                6,    7,    8,    0,    0,  124,   10,   11,    0,    0,
                0,    0,    4,    5,    6,    7,    8,    0,    0,    0,
                10,   11,    0,  254,    0,    0,    0,    0,    0,    0,
                0,    0,    0,    0,  261,    0,    0,    0,    0,    0,
                0,    0,    0,    0,    0,    0,    0,    0,  271,    0,
                0,    0,    0,    0,    0,    0,    0,    0,  280,    0,
                0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
                0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
                0,  294,    0,    0,    0,    0,    0,    0,    0,    0,
                303,  304,  305,    0,    0,    0,  306,
        };
    }
    static short yycheck[];
    static { yycheck(); }
    static void yycheck() {
        yycheck = new short[] {                         41,
                44,   43,   44,   45,   41,   41,   43,   44,   45,   41,
                44,   43,   44,   45,  125,   43,   41,   45,   60,  123,
                62,   15,   40,   60,  123,   62,  123,   40,   60,   40,
                62,   40,   60,   46,   62,   46,   41,   46,   58,   41,
                125,   43,   40,   45,   13,   40,   45,   40,   61,  125,
                61,  125,   61,  125,   44,   49,  125,   82,   41,   41,
                125,   41,   41,   45,  256,  257,   45,   44,   45,   43,
                44,   45,  123,  136,  137,   42,   44,   45,   44,   45,
                47,  125,   44,   45,   43,   44,   45,   43,   44,   45,
                43,   44,   45,   44,   44,   44,   44,   44,   44,   44,
                44,   44,  257,  123,   43,  257,   45,  257,   59,   59,
                257,   41,  270,  271,  257,  270,  257,   41,  123,  260,
                183,  256,  263,  264,  265,  266,  267,  268,  269,   59,
                257,  272,  273,  260,  275,   59,  263,  257,  261,  262,
                261,  262,  269,  103,  104,  271,   58,  257,  275,  269,
                105,  106,  256,  123,  265,   41,  257,  257,  257,  184,
                256,  260,  256,  258,  263,  264,  265,  266,  267,  268,
                269,  123,  257,  272,  273,  260,  275,  274,  263,  264,
                265,  266,  267,  268,  269,  125,  211,  272,  273,  265,
                275,  265,  257,  265,   41,  257,  265,  257,   40,  264,
                265,  266,  267,  268,  198,  174,   40,  272,  273,  125,
                261,  262,  123,  256,  256,  256,   81,  123,  256,  256,
                41,  265,  258,  125,  256,  259,  220,  196,  125,  223,
                199,  225,  201,  123,  257,  277,  278,  279,  280,   41,
                277,  278,  279,  280,  257,  277,  278,  279,  280,  277,
                278,  279,  280,  271,   40,  224,  250,  125,  257,  258,
                123,  258,  127,  276,  256,  276,  256,  276,  266,  267,
                268,  266,  267,  268,  257,  257,  258,  257,  257,  258,
                257,  258,  256,  266,  267,  268,  266,  267,  268,  257,
                258,  257,  258,  115,  258,  257,  258,  256,  125,  257,
                256,  258,  125,  256,  169,  256,  256,  256,  256,  256,
                256,  256,  256,  256,   44,  125,  123,  257,  256,  125,
                260,   59,  123,  263,  264,  265,  266,  267,  268,  269,
                195,  265,  272,  273,  123,  275,   44,  159,   59,   32,
                33,  257,   59,   36,  260,  125,  168,  263,  264,  265,
                266,  267,  268,  269,  125,  257,  272,  273,  260,  275,
                257,  263,   55,  260,   57,  125,  263,  269,  125,  191,
                44,  193,  269,  275,  256,  123,  123,  258,  275,  258,
                258,  256,  125,  256,  125,  257,  123,  256,  258,  257,
                256,  123,  260,   59,  216,  263,  264,  265,  266,  267,
                268,  269,  125,   59,  272,  273,  228,  275,   59,   41,
                41,  257,  262,  123,   44,   44,  125,  239,  258,   44,
                44,  258,  258,  116,  117,  118,  119,  120,  121,  258,
                257,  253,  254,  260,  257,  125,  263,  260,  256,  261,
                263,  263,  269,  125,   41,  125,  269,  257,  275,  271,
                260,  257,  275,  263,  260,  123,  125,  263,  280,  269,
                256,  256,  256,  269,  286,  275,  125,  125,   41,  275,
                41,   41,  294,  295,  296,  297,  123,  262,   41,  301,
                125,  303,  304,  305,  306,  123,  257,  123,  256,  260,
                262,  256,  263,  264,  265,  266,  267,  268,  269,  125,
                257,  272,  273,  260,  275,    0,  263,  264,  265,  266,
                267,  268,  269,  125,  257,  272,  273,  260,  275,   41,
                263,  264,  265,  266,  267,  268,  269,   41,   41,  272,
                273,   41,  275,   41,  257,   41,  169,  260,   19,  141,
                263,  264,  265,  266,  267,  268,  269,   -1,  257,  272,
                273,  260,  275,   -1,  263,  264,  265,  266,  267,  268,
                269,   -1,   -1,  272,  273,   -1,  275,  257,   -1,   -1,
                260,   -1,   -1,  263,   -1,  257,   -1,  257,  260,  269,
                260,  263,   -1,  263,   -1,  275,   -1,  269,  257,  269,
                -1,  260,   -1,  275,  263,  275,   -1,   -1,  257,  257,
                269,  260,  260,  168,  263,  263,  275,   -1,   -1,   -1,
                269,  269,  257,   -1,   -1,   -1,  275,  275,   -1,  264,
                265,  266,  267,  268,   -1,   -1,  191,  272,  273,   -1,
                -1,  257,   -1,   -1,   -1,   -1,   -1,   -1,  264,  265,
                266,  267,  268,   -1,   -1,  257,  272,  273,   -1,   -1,
                -1,   -1,  264,  265,  266,  267,  268,   -1,   -1,   -1,
                272,  273,   -1,  228,   -1,   -1,   -1,   -1,   -1,   -1,
                -1,   -1,   -1,   -1,  239,   -1,   -1,   -1,   -1,   -1,
                -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  253,   -1,
                -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  263,   -1,
                -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
                -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
                -1,  286,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
                295,  296,  297,   -1,   -1,   -1,  301,
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
            "sentencia_ejecutable : seleccion ','",
            "sentencia_ejecutable : seleccion error",
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
            "sentencia_control : FOR ID IN RANGE '(' CTE ';' CTE ';' CTE ')' '{' bloque_ejecutable '}'",
            "sentencia_control : FOR ID IN RANGE '(' CTE ';' CTE ')' '{' bloque_ejecutable '}'",
            "sentencia_control : FOR ID IN RANGE '(' CTE ')' '{' bloque_ejecutable '}'",
            "sentencia_control : FOR ID IN RANGE '(' ')' '{' bloque_ejecutable '}'",
            "sentencia_control : FOR IN RANGE '(' CTE ';' CTE ';' CTE ')' '{' bloque_ejecutable '}' error",
            "sentencia_control : FOR ID RANGE '(' CTE ';' CTE ';' CTE ')' '{' bloque_ejecutable '}'",
            "sentencia_control : FOR ID IN '(' CTE ';' CTE ';' CTE ')' '{' bloque_ejecutable '}'",
            "sentencia_control : FOR ID IN RANGE '(' CTE ';' CTE ';' CTE ')' error",
    };

//#line 217 ".\gramatica.y"
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
    //#line 602 "Parser.java"
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
//#line 34 ".\gramatica.y"
                {
                    /* accion que chequee si el tipo es “CLASE” (id no puede ser un tipo si no es una clase)*/
                    if (!al.getTablaSimbolos().getAtributos(val_peek(3).sval).isTipo("CLASE")){
                        System.out.println("ERROR. Linea: "+getLinea(al)+": "+val_peek(3).sval+" no es tipo CLASE");
                    }
                }
                break;
                case 19:
//#line 44 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " se esperaba ','");}
                break;
                case 20:
//#line 45 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " se esperaba ','");}
                break;
                case 26:
//#line 57 ".\gramatica.y"
                {System.out.println("Linea: " + al.getLinea() + " Declaracion funcion VOID " + val_peek(6).sval);}
                break;
                case 27:
//#line 58 ".\gramatica.y"
                {System.out.println("Linea: " + al.getLinea() + " Declaracion funcion VOID " + val_peek(8).sval);}
                break;
                case 28:
//#line 59 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " se esperaba ')'");}
                break;
                case 29:
//#line 60 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " se esperaba '('");}
                break;
                case 30:
//#line 61 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " falta el tipo de " + val_peek(6).sval);}
                break;
                case 31:
//#line 62 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " no se puede declarar una funcion sin cuerpo");}
                break;
                case 32:
//#line 63 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " no se puede declarar una funcion sin cuerpo");}
                break;
                case 33:
//#line 64 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " se esperaba ','");}
                break;
                case 34:
//#line 65 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " se esperaba ','");}
                break;
                case 35:
//#line 68 ".\gramatica.y"
                {
                    System.out.println("Linea: " + al.getLinea() + " Declaracion CLASE " + val_peek(3).sval);
                    /* accion para ID que establezca como tipo “CLASE” */
                    al.getTablaSimbolos().getAtributos(val_peek(3).sval).setTipo("CLASE");
                }
                break;
                case 36:
//#line 73 ".\gramatica.y"
                {
                    System.out.println("Linea: " + al.getLinea() + " Declaracion CLASE " + val_peek(5).sval);
                    /* accion que chequea si ID_2 es una interfaz y asignar tipo CLASE a ID_1 */
                    if (al.getTablaSimbolos().getAtributos(val_peek(3).sval).isTipo("INTERFACE")){
                        al.getTablaSimbolos().getAtributos(val_peek(5).sval).setTipo("CLASE");
                    } else {
                        System.out.println("ERROR. Linea "+getLinea(al)+": "+val_peek(3).sval+" no es un INTERFACE");
                    }
                }
                break;
                case 37:
//#line 82 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " no se puede definir una clase sin cuerpo");}
                break;
                case 38:
//#line 83 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " no se puede definir una clase sin cuerpo");}
                break;
                case 39:
//#line 84 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " revisar declaracion de clase");}
                break;
                case 40:
//#line 87 ".\gramatica.y"
                {
                    System.out.println("Linea: " + al.getLinea() + " Declaracion DISTRIBUIDA para " + val_peek(4).sval);
                    /* accion para fijarse que id sea una clase */
                    if (!al.getTablaSimbolos().getAtributos(val_peek(4).sval).isTipo("CLASE")){
                        System.out.println("ERROR. Linea "+getLinea(al)+": "+val_peek(4).sval+" no es una clase");
                    }
                }
                break;
                case 41:
//#line 94 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " se esperaba ':'");}
                break;
                case 42:
//#line 95 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " no se puede definir una declaracion distribuida sin cuerpo");}
                break;
                case 43:
//#line 96 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " falta palabra reservada FOR");}
                break;
                case 44:
//#line 100 ".\gramatica.y"
                {/* accion para ID que establezca como tipo “INTERFACE” */
                    al.getTablaSimbolos().getAtributos(val_peek(3).sval).setTipo("INTERFACE");
                }
                break;
                case 45:
//#line 103 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " no se puede declarar una interfaz sin metodos");}
                break;
                case 48:
//#line 108 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " se esperaba ','");}
                break;
                case 49:
//#line 109 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " se esperaba ','");}
                break;
                case 50:
//#line 112 ".\gramatica.y"
                {System.out.println("Linea: " + al.getLinea() + " Declaracion metodo VOID " + val_peek(4).sval);}
                break;
                case 51:
//#line 113 ".\gramatica.y"
                {System.out.println("Linea: " + al.getLinea() + " Declaracion metodo VOID " + val_peek(2).sval);}
                break;
                case 52:
//#line 114 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " se esperaba ')'");}
                break;
                case 53:
//#line 115 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " se esperaba '('");}
                break;
                case 54:
//#line 116 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " falta el tipo de " + val_peek(2).sval);}
                break;
                case 55:
//#line 119 ".\gramatica.y"
                {System.out.println("Linea: " + al.getLinea() + " ASIGNACION");}
                break;
                case 56:
//#line 120 ".\gramatica.y"
                {System.out.println("Linea: " + al.getLinea() + " INVOCACION FUNCION");}
                break;
                case 57:
//#line 121 ".\gramatica.y"
                {System.out.println("Linea: " + al.getLinea() + " Sentencia IF");}
                break;
                case 58:
//#line 122 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " se esperaba ','");}
                break;
                case 63:
//#line 127 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " se esperaba ','");}
                break;
                case 64:
//#line 128 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " se esperaba ','");}
                break;
                case 69:
//#line 135 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " Se esperaba expresion");}
                break;
                case 70:
//#line 136 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " Se esperaba expresion");}
                break;
                case 71:
//#line 137 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " Se esperaba expresion");}
                break;
                case 72:
//#line 138 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " Se esperaba expresion");}
                break;
                case 73:
//#line 139 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " se esperaba ','");}
                break;
                case 74:
//#line 140 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " se esperaba ','");}
                break;
                case 75:
//#line 141 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " se esperaba ','");}
                break;
                case 76:
//#line 142 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " se esperaba ','");}
                break;
                case 77:
//#line 145 ".\gramatica.y"
                {System.out.println("Linea: " + al.getLinea() + " SUMA");}
                break;
                case 78:
//#line 146 ".\gramatica.y"
                {System.out.println("Linea: " + al.getLinea() + " RESTA");}
                break;
                case 80:
//#line 150 ".\gramatica.y"
                {System.out.println("Linea: " + al.getLinea() + " MULTIPLICACION");}
                break;
                case 81:
//#line 151 ".\gramatica.y"
                {System.out.println("Linea: " + al.getLinea() + " DIVISION");}
                break;
                case 84:
//#line 156 ".\gramatica.y"
                {   chequeoRango(val_peek(0).sval, al);
                    al.getTablaSimbolos().getAtributos(val_peek(0).sval).sumarUso();
                }
                break;
                case 85:
//#line 159 ".\gramatica.y"
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
                case 88:
//#line 174 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " se esperaba ','");}
                break;
                case 89:
//#line 175 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " se esperaba ','");}
                break;
                case 92:
//#line 180 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " falta condicion");}
                break;
                case 93:
//#line 181 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " falta condicion");}
                break;
                case 94:
//#line 182 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " cuerpo de IF vacio");}
                break;
                case 95:
//#line 183 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " cuerpo de IF vacio");}
                break;
                case 103:
//#line 196 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " se esperaba una cadena de caracteres");}
                break;
                case 104:
//#line 197 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " se esperaba ','");}
                break;
                case 107:
//#line 204 ".\gramatica.y"
                {System.out.println("Linea: " + al.getLinea() + " Sentencia FOR");}
                break;
                case 108:
//#line 205 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " falta una constante");}
                break;
                case 109:
//#line 206 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " faltan constantes");}
                break;
                case 110:
//#line 207 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " faltan constantes");}
                break;
                case 111:
//#line 208 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " se esperaba un identificador");}
                break;
                case 112:
//#line 209 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " falta la palabra reservada IN");}
                break;
                case 113:
//#line 210 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " falta la palabra reservada RANGE");}
                break;
                case 114:
//#line 211 ".\gramatica.y"
                {System.out.println("ERROR. Linea: " + getLinea(al) + " no se puede definir un FOR sin cuerpo");}
                break;
//#line 1068 "Parser.java"
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
