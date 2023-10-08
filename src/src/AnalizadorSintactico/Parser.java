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
            0,    0,    0,    1,    1,    1,    2,    2,    2,    2,
            6,    6,    6,    6,    3,    3,    3,    3,    3,    3,
            10,   10,   10,   10,   10,   11,   11,   11,   11,   12,
            12,   13,   13,   13,   13,   14,   14,   14,   14,   14,
            9,    9,    9,    9,    9,    9,    9,   15,   15,    7,
            7,    7,    8,    8,    4,    4,    4,    4,    4,   20,
            20,   16,   16,   16,   16,   16,   16,   16,   16,   16,
            16,   21,   21,   21,   22,   22,   22,   23,   23,   23,
            17,   17,   18,   18,   18,   18,   18,   24,   24,   24,
            24,   24,   24,   24,   25,   25,   19,   19,    5,    5,
            5,    5,    5,    5,    5,    5,
    };
    final static short yylen[] = {                            2,
            3,    2,    2,    3,    2,    1,    1,    1,    1,    1,
            1,    4,    4,    4,    2,    2,    1,    1,    1,    1,
            5,    7,    5,    7,    2,    7,    6,    7,    7,    5,
            5,    2,    3,    1,    2,    6,    4,    5,    5,    6,
            7,    9,    8,    8,    9,    9,    7,    1,    2,    1,
            1,    1,    1,    3,    1,    1,    1,    1,    3,    3,
            3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
            3,    3,    3,    1,    3,    3,    1,    1,    1,    2,
            4,    3,    8,    6,    5,    7,    5,    3,    3,    3,
            3,    3,    3,    2,    1,    4,    2,    2,   10,    9,
            8,    7,   10,    9,    9,   10,
    };
    final static short yydefred[] = {                         0,
            0,    0,    0,    0,    0,   50,   51,   52,    0,    0,
            0,   10,    0,    0,    0,    0,    7,    8,    9,    0,
            17,   18,   19,   20,   55,   56,   57,   58,    0,    0,
            79,    0,    0,    0,    0,    0,    0,    0,    0,   77,
            0,   98,   97,   25,    0,    0,    0,    0,    0,    0,
            0,    0,    3,    0,    5,   53,    0,   78,    0,    0,
            0,    0,    0,   70,    0,   82,    0,   60,   68,    0,
            80,    0,   66,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            1,    4,   71,    0,   59,   61,   69,    0,   67,   81,
            54,    0,    0,   75,   76,    0,    0,   95,    0,   94,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,   11,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            85,    0,    0,    0,    0,    0,    0,   87,    0,    0,
            0,   23,   21,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,   31,   30,    0,   32,
            0,    0,    0,   84,    0,    0,    0,    0,    0,    0,
            48,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,   27,    0,    0,    0,    0,   33,   96,   86,    0,
            24,   22,   13,    0,   14,    0,   47,   41,   49,    0,
            0,    0,    0,    0,  102,    0,    0,    0,   29,   28,
            26,    0,   37,    0,    0,   83,    0,   43,    0,    0,
            44,    0,    0,  101,    0,    0,    0,    0,    0,   39,
            45,   46,   42,    0,  100,  105,  104,    0,   40,   36,
            106,   99,  103,
    };
    final static short yydgoto[] = {                         14,
            15,  181,   17,   18,   19,  123,   20,   37,   21,   22,
            23,   24,  137,  138,  182,   25,   26,   27,   28,   29,
            38,   39,   40,   80,  109,
    };
    final static short yysindex[] = {                       -69,
            -37,   10, -176, -158, -221,    0,    0,    0, -222, -179,
            -183,    0, -135,    0,  262,   37,    0,    0,    0, -172,
            0,    0,    0,    0,    0,    0,    0,    0,  -33,    0,
            0,  -39,   -2, -166,  137, -165,   44,    4,   18,    0,
            27,    0,    0,    0, -119,  -35, -169, -163,   52, -145,
            -7,  276,    0,   80,    0,    0,   44,    0,  166,   95,
            -130,  252,   21,    0,   39,    0,  -11,    0,    0,   39,
            0, -118,    0,   64,   64,   64,   64,  -71,  -43,  102,
            -112,  487,  -16, -105,  -26,  116,  124,   42,  -36,  -92,
            0,    0,    0,   39,    0,    0,    0,   39,    0,    0,
            0,   18,   18,    0,    0,  -30,  -71,    0, -155,    0,
            64,   64,   64,   64,   64,   64, -103,   54, -172,  409,
            -78,    0,  -98,  138,   60,  -73,  164,  167,  -50,  -48,
            -46,  -51,  -51,   93,  -34,  -18,  -85,  182, -113,  -71,
            0,   39,   39,   39,   39,   39,   39,    0, -147,  500,
            545,    0,    0,  115,  136,  295,  -23,  177,   -4,   67,
            69,   77,  184,  212,  -84,  -19,    0,    0,  258,    0,
            213,   85,  -71,    0,   94,  467, -114,   96, -135,  100,
            0,  322, -135,  244, -135,   14,  -71,  110,  113,  118,
            119,    0,  135,  271,  131,  143,    0,    0,    0,  142,
            0,    0,    0,   96,    0,  336,    0,    0,    0,  367,
            381,  429,   17,  -71,    0,  364,  375,  376,    0,    0,
            0,  385,    0,  168,  388,    0,  174,    0,  200,  448,
            0,  418,  -71,    0,  -71,  -71,  -71,  207,  423,    0,
            0,    0,    0,  223,    0,    0,    0,  211,    0,    0,
            0,    0,    0,
    };
    final static short yyrindex[] = {                         0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,  230,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,   76,
            0,    0,    0,    0,    0,    0,  185,    0,    1,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,  471,    0,    0,    0,    0,  209,    0,    0,    0,
            0,    0,    0,    0,   97,    0,    0,    0,    0,  117,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,  146,    0,    0,    0,  171,    0,    0,
            0,   26,   51,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,  -72,    0,    0,
            0,  444,  447,  459,  463,  470,  472,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,  -63,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,  518,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,  -21,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,
    };
    final static short yygindex[] = {                         0,
            476,  455,  430,  384,    0,  -91,   -8,  494,  -76,    0,
            0,    0,    0,  378, -106,    0,    0,    0,    0,    0,
            760,   74,   86,    0,  563,
    };
    final static int YYTABLESIZE=876;
    static short yytable[];
    static { yytable();}
    static void yytable(){
        yytable = new short[]{                         74,
                74,   75,   33,   82,   83,   36,   60,   36,   34,   33,
                204,   36,   61,  129,   36,   34,  115,  184,  116,  107,
                195,  134,   38,   35,  125,   72,  153,   62,  151,  100,
                35,   74,  136,   75,   47,   46,  187,   84,   66,  168,
                193,   74,   36,   74,   74,   74,   74,   48,   75,   41,
                73,  107,   34,   13,  214,  163,  164,  233,  176,   76,
                74,   35,   74,   74,   77,   75,   72,   78,   72,   72,
                72,   36,  206,   51,  126,   53,  210,   49,  212,   42,
                55,   74,   43,   75,   56,   72,  133,   72,  194,   50,
                68,   73,   71,   73,   73,   73,   63,   44,   45,  183,
                85,   86,   72,   38,  230,  140,  141,   87,   36,   88,
                73,   89,   73,  173,  174,   90,   62,   78,   78,   53,
                78,    1,   78,   92,    2,   74,   96,    3,    4,    5,
                6,    7,    8,    9,   53,   95,   10,   11,  101,   12,
                63,  203,  117,  106,  118,   65,    2,  102,  103,    3,
                72,  127,  148,  106,   81,  130,    2,  196,  119,    3,
                62,  104,  105,  131,  132,    4,    5,    6,    7,    8,
                64,  223,  135,   10,   11,   73,  150,  152,  155,  135,
                5,   36,  156,  157,   16,  106,  224,    1,    2,   65,
                2,    3,   34,    3,    4,    5,    6,    7,    8,    9,
                53,   35,   10,   11,  158,   12,  159,  160,   15,  161,
                36,  162,  110,    5,   64,  165,   64,   58,   31,   30,
                31,   63,  166,   58,   31,  170,   58,   31,   16,    6,
                6,    7,    8,  111,  112,  113,  114,  167,   32,  178,
                124,   62,   59,   38,  128,   32,    6,    7,    8,    6,
                7,    8,   15,  186,   58,   31,   74,   74,  179,   73,
                74,   74,   74,   74,   74,   74,   74,   74,   74,   74,
                65,  213,   74,   74,  232,   74,   99,   74,   74,   74,
                74,   72,   72,   58,   31,   72,   72,   72,   72,   72,
                72,   72,   72,   72,   72,   64,   36,   72,   72,  185,
                72,  197,   72,   72,   72,   72,   73,   73,  191,   16,
                73,   73,   73,   73,   73,   73,   73,   73,   73,   73,
                58,   31,   73,   73,  188,   73,  189,   73,   73,   73,
                73,   78,   53,   15,  190,   53,  192,  198,   53,   53,
                53,   53,   53,   53,   53,  107,  199,   53,   53,  201,
                53,  205,   63,   63,    6,  207,   63,   63,   63,   63,
                63,   63,   63,   63,   63,   63,  211,  216,   63,   63,
                217,   63,   62,   62,  219,  218,   62,   62,   62,   62,
                62,   62,   62,   62,   62,   62,   53,  222,   62,   62,
                220,   62,   69,   58,   31,  221,    6,    7,    8,  225,
                91,   65,   65,  226,  235,   65,   65,   65,   65,   65,
                65,   65,   65,   65,   65,  236,  237,   65,   65,  180,
                65,   93,   58,   31,  239,  238,   64,   64,  240,  241,
                64,   64,   64,   64,   64,   64,   64,   64,   64,   64,
                16,   16,   64,   64,   16,   64,  208,   16,   16,   16,
                16,   16,   16,   16,   16,  242,   16,   16,  244,   16,
                227,  108,  249,  250,   15,   15,  253,   16,   15,   54,
                2,   15,   15,   15,   15,   15,   15,   15,  251,  106,
                15,   15,    2,   15,   88,    3,    6,   89,   52,    6,
                108,  228,    6,    6,    6,    6,    6,    6,    6,   92,
                108,    6,    6,   93,    6,  229,   54,   97,   58,   31,
                90,  122,   91,   57,  169,    0,    0,    0,    1,    0,
                0,    2,  171,  108,    3,    4,    5,    6,    7,    8,
                9,  120,    1,   10,   11,    2,   12,    0,    3,    4,
                5,    6,    7,    8,    9,    0,    0,   10,   11,  122,
                12,    1,  154,  231,    2,    0,  108,    3,    4,    5,
                6,    7,    8,    9,    0,    0,   10,   11,    0,   12,
                108,    0,  243,    0,    0,    0,    0,    0,    1,  122,
                177,    2,    0,    0,    3,    4,    5,    6,    7,    8,
                9,  202,    1,   10,   11,    2,   12,  108,    3,    4,
                5,    6,    7,    8,    9,  154,    0,   10,   11,  120,
                12,  121,    0,    0,    0,    0,  108,    0,  108,  108,
                108,    0,  120,    1,  175,    0,    2,  108,    0,    3,
                4,    5,    6,    7,    8,    9,  209,    1,   10,   11,
                2,   12,   12,    3,    4,    5,    6,    7,    8,    9,
                0,    0,   10,   11,    0,   12,    0,    0,    0,    0,
                209,    0,    0,    0,  209,  119,  209,    0,    0,  139,
                0,    0,    4,    5,    6,    7,    8,    0,    0,  149,
                10,   11,    0,    0,  209,    1,    0,    0,    2,    0,
                0,    3,    4,    5,    6,    7,    8,    9,    0,    0,
                10,   11,  172,   12,    1,    0,    0,    2,    0,    0,
                3,    4,    5,    6,    7,    8,    9,    0,    0,   10,
                11,    0,   12,  119,    0,    0,    0,    0,    0,    0,
                4,    5,    6,    7,    8,  200,    0,    0,   10,   11,
                0,    0,    0,  119,    0,    0,    0,    0,    0,  215,
                4,    5,    6,    7,    8,    0,  119,    0,   10,   11,
                0,    0,    0,    4,    5,    6,    7,    8,    0,    0,
                0,   10,   11,    0,   12,    0,  234,    0,    0,    0,
                0,   12,   12,   12,   12,   12,    0,    0,   63,   12,
                12,   65,   67,    0,   70,  245,    0,  246,  247,  248,
                79,  119,    0,    0,    0,    0,  252,    0,    4,    5,
                6,    7,    8,    0,    0,    0,   10,   11,   94,    0,
                0,   98,    0,    0,    0,    0,    0,    0,    0,    0,
                0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
                0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
                0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
                0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
                142,  143,  144,  145,  146,  147,
        };
    }
    static short yycheck[];
    static { yycheck(); }
    static void yycheck() {
        yycheck = new short[] {                         43,
                0,   45,   40,  123,   40,   45,   40,   45,   46,   40,
                125,   45,   46,   40,   45,   46,   60,   41,   62,  123,
                40,   58,   44,   61,   41,    0,  125,   61,  120,   41,
                61,   43,  125,   45,  257,  257,   41,   46,   41,  125,
                125,   41,   45,   43,   44,   45,   43,  270,   45,   40,
                0,  123,  125,  123,   41,  132,  133,   41,  150,   42,
                60,  125,   62,   43,   47,   45,   41,   41,   43,   44,
                45,   45,  179,  257,   83,    0,  183,  257,  185,  256,
                44,   43,  259,   45,  257,   60,  123,   62,  165,  269,
                257,   41,  258,   43,   44,   45,    0,  256,  257,  123,
                270,  271,   59,  125,  211,  261,  262,  271,   45,   58,
                60,  257,   62,  261,  262,  123,    0,   42,   43,   44,
                45,  257,   47,   44,  260,  125,  257,  263,  264,  265,
                266,  267,  268,  269,   59,   41,  272,  273,  257,  275,
                44,  256,   41,  257,  257,    0,  260,   74,   75,  263,
                125,  257,  256,  257,  274,   40,  260,  166,  257,  263,
                44,   76,   77,   40,  123,  264,  265,  266,  267,  268,
                0,   41,  265,  272,  273,  125,  123,  256,   41,  265,
                265,   45,  123,  257,    0,  257,  195,  257,  260,   44,
                260,  263,  265,  263,  264,  265,  266,  267,  268,  269,
                125,  265,  272,  273,   41,  275,   40,  258,    0,  258,
                45,  258,  256,  265,   44,  123,  256,  257,  258,  257,
                258,  125,  257,  257,  258,   44,  257,  258,   44,    0,
                266,  267,  268,  277,  278,  279,  280,  256,  276,  125,
                257,  125,  276,  265,  271,  276,  266,  267,  268,  266,
                267,  268,   44,  258,  257,  258,  256,  257,  123,  256,
                260,  261,  262,  263,  264,  265,  266,  267,  268,  269,
                125,  258,  272,  273,  258,  275,  256,  277,  278,  279,
                280,  256,  257,  257,  258,  260,  261,  262,  263,  264,
                265,  266,  267,  268,  269,  125,   45,  272,  273,  123,
                275,   44,  277,  278,  279,  280,  256,  257,  125,  125,
                260,  261,  262,  263,  264,  265,  266,  267,  268,  269,
                257,  258,  272,  273,  258,  275,  258,  277,  278,  279,
                280,  256,  257,  125,  258,  260,  125,  125,  263,  264,
                265,  266,  267,  268,  269,  123,  262,  272,  273,  256,
                275,  256,  256,  257,  125,  256,  260,  261,  262,  263,
                264,  265,  266,  267,  268,  269,  123,  258,  272,  273,
                258,  275,  256,  257,  256,  258,  260,  261,  262,  263,
                264,  265,  266,  267,  268,  269,  125,  257,  272,  273,
                256,  275,  256,  257,  258,  125,  266,  267,  268,  257,
                125,  256,  257,  262,   41,  260,  261,  262,  263,  264,
                265,  266,  267,  268,  269,   41,   41,  272,  273,  125,
                275,  256,  257,  258,  257,   41,  256,  257,   41,  256,
                260,  261,  262,  263,  264,  265,  266,  267,  268,  269,
                256,  257,  272,  273,  260,  275,  125,  263,  264,  265,
                266,  267,  268,  269,    0,  256,  272,  273,   41,  275,
                125,   78,  256,   41,  256,  257,  256,   13,  260,   15,
                0,  263,  264,  265,  266,  267,  268,  269,  256,  257,
                272,  273,  260,  275,   41,  263,  257,   41,   13,  260,
                107,  125,  263,  264,  265,  266,  267,  268,  269,   41,
                117,  272,  273,   41,  275,  125,   52,  256,  257,  258,
                41,   82,   41,   20,  137,   -1,   -1,   -1,  257,   -1,
                -1,  260,  139,  140,  263,  264,  265,  266,  267,  268,
                269,  123,  257,  272,  273,  260,  275,   -1,  263,  264,
                265,  266,  267,  268,  269,   -1,   -1,  272,  273,  120,
                275,  257,  123,  125,  260,   -1,  173,  263,  264,  265,
                266,  267,  268,  269,   -1,   -1,  272,  273,   -1,  275,
                187,   -1,  125,   -1,   -1,   -1,   -1,   -1,  257,  150,
                151,  260,   -1,   -1,  263,  264,  265,  266,  267,  268,
                269,  125,  257,  272,  273,  260,  275,  214,  263,  264,
                265,  266,  267,  268,  269,  176,   -1,  272,  273,  123,
                275,  125,   -1,   -1,   -1,   -1,  233,   -1,  235,  236,
                237,   -1,  123,  257,  125,   -1,  260,  244,   -1,  263,
                264,  265,  266,  267,  268,  269,  182,  257,  272,  273,
                260,  275,  125,  263,  264,  265,  266,  267,  268,  269,
                -1,   -1,  272,  273,   -1,  275,   -1,   -1,   -1,   -1,
                206,   -1,   -1,   -1,  210,  257,  212,   -1,   -1,  107,
                -1,   -1,  264,  265,  266,  267,  268,   -1,   -1,  117,
                272,  273,   -1,   -1,  230,  257,   -1,   -1,  260,   -1,
                -1,  263,  264,  265,  266,  267,  268,  269,   -1,   -1,
                272,  273,  140,  275,  257,   -1,   -1,  260,   -1,   -1,
                263,  264,  265,  266,  267,  268,  269,   -1,   -1,  272,
                273,   -1,  275,  257,   -1,   -1,   -1,   -1,   -1,   -1,
                264,  265,  266,  267,  268,  173,   -1,   -1,  272,  273,
                -1,   -1,   -1,  257,   -1,   -1,   -1,   -1,   -1,  187,
                264,  265,  266,  267,  268,   -1,  257,   -1,  272,  273,
                -1,   -1,   -1,  264,  265,  266,  267,  268,   -1,   -1,
                -1,  272,  273,   -1,  257,   -1,  214,   -1,   -1,   -1,
                -1,  264,  265,  266,  267,  268,   -1,   -1,   29,  272,
                273,   32,   33,   -1,   35,  233,   -1,  235,  236,  237,
                41,  257,   -1,   -1,   -1,   -1,  244,   -1,  264,  265,
                266,  267,  268,   -1,   -1,   -1,  272,  273,   59,   -1,
                -1,   62,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
                -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
                -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
                -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
                -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
                111,  112,  113,  114,  115,  116,
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
            "sentencia : RETURN",
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
            "declaracion_funcion : VOID ID '(' ')' '{' cuerpo_funcion '}'",
            "declaracion_funcion : VOID ID '(' tipo ID ')' '{' cuerpo_funcion '}'",
            "declaracion_funcion : VOID ID '(' tipo ID '{' cuerpo_funcion '}'",
            "declaracion_funcion : VOID ID tipo ID ')' '{' cuerpo_funcion '}'",
            "declaracion_funcion : VOID ID '(' ID ')' '{' cuerpo_funcion '}' error",
            "declaracion_funcion : VOID ID '(' tipo ID ')' '{' '}' error",
            "declaracion_funcion : VOID ID '(' ')' '{' '}' error",
            "cuerpo_funcion : sentencia",
            "cuerpo_funcion : cuerpo_funcion sentencia",
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
            "ref_clase : ID '.' ID",
            "ref_clase : ref_clase '.' ID",
            "asignacion : ID '=' expresion",
            "asignacion : ID MENOS_IGUAL expresion",
            "asignacion : ref_clase '=' expresion",
            "asignacion : ref_clase MENOS_IGUAL expresion",
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
            "bloque_ejecutable : sentencia_ejecutable",
            "bloque_ejecutable : '{' bloque_ejecutable sentencia_ejecutable '}'",
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

//#line 207 "gramatica.y"
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
    //#line 598 "Parser.java"
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
//#line 11 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba '}'");}
                break;
                case 3:
//#line 12 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba '{'");}
                break;
                case 6:
//#line 17 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba ','");}
                break;
                case 13:
//#line 28 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba '}'");}
                break;
                case 14:
//#line 29 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba '{'");}
                break;
                case 16:
//#line 34 "gramatica.y"
                {
                    /* accion que chequee si el tipo es “CLASE” (id no puede ser un tipo si no es una clase)*/
                    if (!al.getTablaSimbolos().getAtributos(val_peek(1).sval).isTipo("CLASE")){
                        System.out.println("Error linea "+al.getLinea()+": "+val_peek(1).sval+" no es tipo CLASE");
                    }
                }
                break;
                case 21:
//#line 46 "gramatica.y"
                {
                    System.out.println("Linea: " + al.getLinea() + " Declaracion CLASE " + val_peek(3).sval);
                    /* accion para ID que establezca como tipo “CLASE” */
                    al.getTablaSimbolos().getAtributos(val_peek(3).sval).setTipo("CLASE");
                }
                break;
                case 22:
//#line 51 "gramatica.y"
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
                case 23:
//#line 60 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " no se puede definir una clase sin cuerpo");}
                break;
                case 24:
//#line 61 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " no se puede definir una clase sin cuerpo");}
                break;
                case 25:
//#line 62 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " revisar declaracion de clase");}
                break;
                case 26:
//#line 64 "gramatica.y"
                {
                    System.out.println("Linea: " + al.getLinea() + " Declaracion DISTRIBUIDA para " + val_peek(4).sval);
                    /* accion para fijarse que id sea una clase */
                    if (!al.getTablaSimbolos().getAtributos(val_peek(4).sval).isTipo("CLASE")){
                        System.out.println("Error linea "+al.getLinea()+": "+val_peek(4).sval+" no es una clase");
                    }
                }
                break;
                case 27:
//#line 71 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba ':'");}
                break;
                case 28:
//#line 72 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " no se puede definir una declaracion distribuida sin cuerpo");}
                break;
                case 29:
//#line 73 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " falta palabra reservada FOR");}
                break;
                case 30:
//#line 77 "gramatica.y"
                {/* accion para ID que establezca como tipo “INTERFACE” */
                    al.getTablaSimbolos().getAtributos(val_peek(3).sval).setTipo("INTERFACE");
                }
                break;
                case 31:
//#line 80 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " no se puede declarar una interfaz sin metodos");}
                break;
                case 34:
//#line 85 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba ','");}
                break;
                case 35:
//#line 86 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba ','");}
                break;
                case 36:
//#line 89 "gramatica.y"
                {System.out.println("Linea: " + al.getLinea() + " Declaracion metodo VOID " + val_peek(4).sval);}
                break;
                case 37:
//#line 90 "gramatica.y"
                {System.out.println("Linea: " + al.getLinea() + " Declaracion metodo VOID " + val_peek(2).sval);}
                break;
                case 38:
//#line 91 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba ')'");}
                break;
                case 39:
//#line 92 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba '('");}
                break;
                case 40:
//#line 93 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " falta el tipo de " + val_peek(2).sval);}
                break;
                case 41:
//#line 96 "gramatica.y"
                {System.out.println("Linea: " + al.getLinea() + " Declaracion funcion VOID " + val_peek(5).sval);}
                break;
                case 42:
//#line 97 "gramatica.y"
                {System.out.println("Linea: " + al.getLinea() + " Declaracion funcion VOID " + val_peek(7).sval);}
                break;
                case 43:
//#line 98 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba ')'");}
                break;
                case 44:
//#line 99 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba '('");}
                break;
                case 45:
//#line 100 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " falta el tipo de " + val_peek(5).sval);}
                break;
                case 46:
//#line 101 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " no se puede declarar una funcion sin cuerpo");}
                break;
                case 47:
//#line 102 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " no se puede declarar una funcion sin cuerpo");}
                break;
                case 55:
//#line 118 "gramatica.y"
                {System.out.println("Linea: " + al.getLinea() + " ASIGNACION");}
                break;
                case 56:
//#line 119 "gramatica.y"
                {System.out.println("Linea: " + al.getLinea() + " INVOCACION FUNCION");}
                break;
                case 57:
//#line 120 "gramatica.y"
                {System.out.println("Linea: " + al.getLinea() + " Sentencia IF");}
                break;
                case 66:
//#line 133 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " asignacion mal definida");}
                break;
                case 67:
//#line 134 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " asignacion mal definida");}
                break;
                case 68:
//#line 135 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " asignacion mal definida");}
                break;
                case 69:
//#line 136 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " asignacion mal definida");}
                break;
                case 70:
//#line 137 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " asignacion mal definida");}
                break;
                case 71:
//#line 138 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " asignacion mal definida");}
                break;
                case 79:
//#line 153 "gramatica.y"
                {   chequeoRango(val_peek(0).sval, al);
                    al.getTablaSimbolos().getAtributos(val_peek(0).sval).sumarUso();
                }
                break;
                case 80:
//#line 156 "gramatica.y"
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
                case 85:
//#line 175 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " falta condicion");}
                break;
                case 86:
//#line 176 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " falta condicion");}
                break;
                case 87:
//#line 177 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " cuerpo de IF vacio");}
                break;
                case 94:
//#line 186 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba una comparacion");}
                break;
                case 98:
//#line 194 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba una cadena de caracteres");}
                break;
                case 99:
//#line 197 "gramatica.y"
                {System.out.println("Linea: " + al.getLinea() + " Sentencia FOR");}
                break;
                case 100:
//#line 198 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " falta una constante");}
                break;
                case 101:
//#line 199 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " faltan constantes");}
                break;
                case 102:
//#line 200 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " faltan constantes");}
                break;
                case 103:
//#line 201 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " se esperaba un identificador");}
                break;
                case 104:
//#line 202 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " falta la palabra reservada IN");}
                break;
                case 105:
//#line 203 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " falta la palabra reservada RANGE");}
                break;
                case 106:
//#line 204 "gramatica.y"
                {System.out.println("ERROR. Linea: " + al.getLinea() + " no se puede definir un FOR sin cuerpo");}
                break;
//#line 1004 "Parser.java"
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
    public void run() {
        String s = "{a = -5_s, b = 5_s, c = -5_s, d = -5_s,}$";
        Analizador_Lexico al = new Analizador_Lexico(s.toCharArray());
        yyparse(al);
        System.out.println(al.getTablaSimbolos().toString());
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
