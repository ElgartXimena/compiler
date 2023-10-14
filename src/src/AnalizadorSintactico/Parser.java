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
            0,    0,    0,    1,    1,    2,    2,    2,    5,    5,
            6,    6,    3,    3,    3,    3,    3,    3,    3,    3,
            7,    7,    7,    8,    8,    9,    9,    9,    9,    9,
            9,    9,    9,    9,   10,   10,   10,   10,   10,   10,
            10,   11,   11,   11,   11,   11,   12,   12,   12,   13,
            13,   13,   13,   14,   14,   14,   14,   14,    4,    4,
            4,    4,    4,    4,    4,    4,    4,    4,    4,    4,
            4,   15,   15,   15,   15,   15,   15,   15,   15,   15,
            15,   15,   15,   21,   21,   21,   22,   22,   22,   23,
            23,   24,   24,   16,   16,   17,   17,   17,   17,   17,
            17,   25,   25,   25,   25,   25,   25,   18,   18,   19,
            19,   20,   20,   20,   20,   20,   20,   26,   26,   26,
    };
    final static short yylen[] = {                            2,
            3,    2,    2,    2,    1,    1,    1,    2,    2,    1,
            2,    1,    3,    3,    3,    3,    1,    1,    1,    1,
            1,    1,    1,    1,    3,    8,   10,    9,    9,   10,
            10,    8,    8,   10,    6,    8,    6,    8,    8,    6,
            8,    8,    8,    8,    8,    8,    6,    6,    6,    2,
            3,    1,    2,    6,    4,    5,    5,    6,    1,    2,
            2,    2,    2,    2,    2,    4,    4,    2,    2,    2,
            2,    4,    4,    4,    4,    4,    4,    4,    4,    4,
            4,    4,    4,    3,    3,    1,    3,    3,    1,    1,
            1,    1,    2,    4,    3,   12,    8,    8,   12,    6,
            10,    3,    3,    3,    3,    3,    3,    2,    2,    3,
            3,   10,    9,   10,    9,    9,    8,    5,    4,    2,
    };
    final static short yydefred[] = {                         0,
            0,    0,    0,    0,    0,    0,   21,   22,   23,    0,
            0,    0,    0,    0,    0,    0,    5,    6,    7,    0,
            17,   18,   19,   20,   59,    0,    0,    0,    0,    0,
            8,   24,    0,    0,    0,    0,    0,    0,    0,  109,
            108,    0,    0,    0,    0,    0,    0,    0,   71,   70,
            0,    3,    4,    0,   61,   60,   63,   62,   65,   64,
            0,    0,    0,    0,    0,   69,   68,   90,   92,    0,
            0,    0,    0,   89,   91,    0,   95,    0,    0,  110,
            16,   14,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    1,   15,   13,    0,
            0,    0,    0,    0,  111,   77,   93,   81,   73,    0,
            0,    0,    0,   76,   94,   80,   72,   25,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            10,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,   79,   83,   75,
            78,   67,   66,   82,   74,    0,    0,   87,   88,    0,
            12,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    9,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,   50,    0,   11,    0,  100,    0,    0,    0,
            0,   37,   40,   35,    0,    0,    0,    0,    0,    0,
            0,    0,  120,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,   48,   49,   47,   51,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,   55,    0,    0,    0,   98,    0,    0,   97,   38,
            41,   36,   39,    0,   32,   33,   26,    0,    0,    0,
            0,    0,  117,    0,  119,    0,    0,    0,    0,   45,
            43,   44,   46,   42,    0,    0,   57,    0,    0,    0,
            0,   28,    0,    0,   29,  113,    0,  118,  116,  115,
            0,   58,   54,    0,  101,    0,   30,   31,   34,   27,
            112,  114,    0,    0,   99,   96,
    };
    final static short yydgoto[] = {                         15,
            16,   17,   18,   19,  132,  162,   20,   38,   21,   22,
            23,   24,  146,  147,   25,   26,   27,   28,   29,   30,
            72,   73,   74,   75,   86,  183,
    };
    final static short yysindex[] = {                       -99,
            46,    2,   68, -144, -141, -135,    0,    0,    0, -204,
            -152, -132,  -15, -133,    0,  -60,    0,    0,    0, -111,
            0,    0,    0,    0,    0,   48,   49,   50,   -7,   51,
            0,    0,  -27,  110,   23,   44, -102,   11,   28,    0,
            0,  -92,   -6, -142, -108,  119,  -77,   65,    0,    0,
            60,    0,    0,   47,    0,    0,    0,    0,    0,    0,
            30,  140,  173,   44,  -11,    0,    0,    0,    0,  -36,
            32,   33,   72,    0,    0,   38,    0,  306,   36,    0,
            0,    0,   43,  104,  -22,  255,  -63,  333,    8,   62,
            -38,  259,  274,  195,    3,  -75,    0,    0,    0,   74,
            39,   78,   52,   42,    0,    0,    0,    0,    0,   44,
            44,   44,   44,    0,    0,    0,    0,    0, -116,   44,
            44,   44,   44,   44,   44,  -10,  213,  152, -111,  299,
            0,  345,  305,  217,   87,  309,  312,  -26,  -26,  -26,
            88,   88,  234,  102,  318,  -73,  320,    0,    0,    0,
            0,    0,    0,    0,    0,   72,   72,    0,    0,    1,
            0,  -82,  100,  100,  100,  100,  100,  100,  238,  107,
            -116,  367,  379,  111,   53,    0,  245,  109,   -5,  247,
            -29,  -33,  330,  342,  346,  261,  264,  -67,    4,  136,
            54,  350,    0, -124,    0, -116,    0,  -74,  351,  391,
            354,    0,    0,    0, -133,  356,  196, -133,  287, -133,
            288,  371,    0,  -26,  304,  307,  308,  378,  389,  392,
            315,   16,  186,    0,    0,    0,    0,  322,  190,   85,
            -112,  191,   55,  193,  216,  198,   56,  230,  254,  268,
            -116,   37,  -31, -116, -116, -116,  211,  218,  221,   57,
            410,    0,  243,  414, -116,    0,  239,  355,    0,    0,
            0,    0,    0,  462,    0,    0,    0,  463,  464,  282,
            465,  128,    0, -116,    0,  -26,  139,  166,  296,    0,
            0,    0,    0,    0,  256,  472,    0,  303,  302, -116,
            311,    0,  313,   59,    0,    0,  317,    0,    0,    0,
            314,    0,    0,  253,    0,  319,    0,    0,    0,    0,
            0,    0,  325,  290,    0,    0,
    };
    final static short yyrindex[] = {                         0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            530,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,  -40,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,  -66,    0,    0,    0,
            0,    0,    0,    0,    0,  -35,  -30,    0,    0,    0,
            0,    0,  503,  173,  520,  521,  523,  527,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,  -54,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,  -37,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,
    };
    final static short yygindex[] = {                         0,
            58,  -16,  -21,  160,  -24,  424,   13,  553,  -32,    0,
            0,    0,    0,  429,    0,    0,    0,    0,    0,    0,
            614,   61,  105,  199,    0,  -19,
    };
    final static int YYTABLESIZE=739;
    static short yytable[];
    static { yytable();}
    static void yytable(){
        yytable = new short[]{                         53,
                86,  138,   86,   86,   86,   84,   56,   84,   84,   84,
                85,  211,   85,   85,   85,   71,   70,   71,   71,   86,
                110,   86,  111,   14,   84,  214,   84,  276,   50,   85,
                88,   85,   63,   89,   53,  209,   62,  124,   65,  125,
                35,   35,  194,  222,   34,   34,   37,   37,  134,  145,
                231,  191,   44,   64,   82,   90,  252,  220,   52,  128,
                143,   36,   36,   77,   52,   45,  131,   71,   84,   83,
                53,   51,   71,  100,   71,  110,  109,  111,  110,  117,
                111,  110,  150,  111,  110,  155,  111,   56,   71,   31,
                99,   56,   58,   60,   67,  153,  204,  226,  262,  267,
                284,  135,  310,  173,   46,   83,  131,   39,  186,  187,
                176,   40,  171,  112,   41,   42,   47,  208,  113,  184,
                185,   43,    1,    2,   48,  142,    3,   91,   92,    4,
                5,    6,    7,    8,    9,   10,  228,  229,   11,   12,
                160,   13,  110,    3,  111,   32,    4,  200,  258,  259,
                131,  176,   10,   76,   80,  221,    1,    2,   13,  274,
                3,  212,   93,    4,    5,    6,    7,    8,    9,   10,
                156,  157,   11,   12,  160,   13,   94,    3,  176,   95,
                4,   87,  160,  102,   97,    3,   10,   96,    4,  144,
                53,  144,   13,  127,   10,    1,    2,    6,   52,    3,
                13,  223,    4,    5,    6,    7,    8,    9,   10,  257,
                53,   11,   12,  103,   13,   86,  158,  159,   53,  106,
                84,   53,  213,   53,  275,   85,  119,   56,   69,   68,
                69,   69,  137,  206,  253,  207,   86,   86,   86,   86,
                49,   84,   84,   84,   84,  105,   85,   85,   85,   85,
                169,  170,  296,   53,  120,  121,  122,  123,   32,    7,
                8,    9,  235,  299,  133,  238,   81,  240,   61,    7,
                8,    9,  251,    7,    8,    9,   33,   33,  161,   68,
                69,    7,    8,    9,   68,   69,   68,   69,  108,  107,
                300,  116,  273,  114,  149,  126,  270,  154,  139,  118,
                68,   69,   98,   55,   57,   59,   66,  152,  203,  225,
                261,  266,  283,  140,  309,    1,    2,  141,  136,    3,
                237,  195,    4,    5,    6,    7,    8,    9,   10,  148,
                161,   11,   12,  151,   13,  172,  182,  182,  182,  178,
                264,  160,  174,  179,    3,  177,  115,    4,  110,  180,
                111,  181,    6,   10,  268,  161,  188,  195,  189,   13,
                196,  190,  197,  193,    1,    2,  202,  205,    3,  210,
                215,    4,    5,    6,    7,    8,    9,   10,  269,  182,
                11,   12,  216,   13,  160,  218,  217,    3,  219,  195,
                4,  224,  271,  227,  232,  160,   10,  234,    3,  236,
                161,    4,   13,  161,  161,  161,  294,   10,  129,  239,
                241,  242,  243,   13,  161,    5,    6,    7,    8,    9,
                301,  247,  160,   11,   12,    3,  244,  304,    4,  245,
                246,  195,  248,  161,   10,  249,  195,  195,  195,  250,
                13,  311,  254,  314,  255,  256,  260,  195,  263,  161,
                285,    1,    2,  265,  287,    3,  195,  130,    4,    5,
                6,    7,    8,    9,   10,  195,  280,   11,   12,  175,
                13,    1,    2,  281,  298,    3,  282,  290,    4,    5,
                6,    7,    8,    9,   10,    1,    2,   11,   12,    3,
                13,  199,    4,    5,    6,    7,    8,    9,   10,  286,
                289,   11,   12,  201,   13,  291,  292,  293,  295,    1,
                2,  302,  303,    3,  313,  233,    4,    5,    6,    7,
                8,    9,   10,    1,    2,   11,   12,    3,   13,    2,
                4,    5,    6,    7,    8,    9,   10,    1,    2,   11,
                12,    3,   13,  102,    4,    5,    6,    7,    8,    9,
                10,  316,  160,   11,   12,    3,   13,  305,    4,  160,
                106,  107,    3,  104,   10,    4,  307,  105,  308,  312,
                13,   10,   54,  160,  192,  160,    3,   13,    3,    4,
                315,    4,    0,    0,    0,   10,    0,   10,    0,  129,
                0,   13,    0,   13,  198,    0,    5,    6,    7,    8,
                9,  129,    0,    0,   11,   12,    0,    0,    5,    6,
                7,    8,    9,    0,    0,    0,   11,   12,    0,  230,
                0,    0,    0,  129,    0,    0,    0,    0,    0,    0,
                5,    6,    7,    8,    9,  129,    0,    0,   11,   12,
                0,    0,    5,    6,    7,    8,    9,  129,   78,   79,
                11,   12,   85,    0,    5,    6,    7,    8,    9,    0,
                0,    0,   11,   12,  272,    0,    0,  277,  278,  279,
                0,    0,    0,    0,  101,    0,    0,  104,  288,    0,
                0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
                0,    0,    0,    0,    0,    0,    0,  297,    0,    0,
                0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
                0,    0,    0,  306,    0,    0,    0,    0,    0,    0,
                0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
                0,    0,    0,  163,  164,  165,  166,  167,  168,
        };
    }
    static short yycheck[];
    static { yycheck(); }
    static void yycheck() {
        yycheck = new short[] {                         16,
                41,   40,   43,   44,   45,   41,   44,   43,   44,   45,
                41,   41,   43,   44,   45,   45,   44,   45,   45,   60,
                43,   62,   45,  123,   60,   59,   62,   59,   44,   60,
                123,   62,   40,   40,   51,   41,   44,   60,   46,   62,
                40,   40,  125,   40,   44,   44,   46,   46,   41,  125,
                125,  125,  257,   61,   44,   43,   41,  125,  125,  123,
                58,   61,   61,   41,  125,  270,   88,   45,   41,   59,
                125,   14,   45,   44,   45,   43,   44,   45,   43,   44,
                45,   43,   44,   45,   43,   44,   45,  125,   45,   44,
                44,   44,   44,   44,   44,   44,   44,   44,   44,   44,
                44,   89,   44,  128,  257,   59,  128,   40,  141,  142,
                132,  256,  123,   42,  259,  257,  269,  123,   47,  139,
                140,  257,  256,  257,  257,  123,  260,  270,  271,  263,
                264,  265,  266,  267,  268,  269,  261,  262,  272,  273,
                257,  275,   43,  260,   45,  257,  263,  172,  261,  262,
                172,  173,  269,   44,  257,  188,  256,  257,  275,  123,
                260,  181,  271,  263,  264,  265,  266,  267,  268,  269,
                110,  111,  272,  273,  257,  275,   58,  260,  200,  257,
                263,  274,  257,   44,  125,  260,  269,  123,  263,  265,
                207,  265,  275,  257,  269,  256,  257,  265,  265,  260,
                275,  189,  263,  264,  265,  266,  267,  268,  269,  125,
                265,  272,  273,   41,  275,  256,  112,  113,  235,  256,
                256,  238,  256,  240,  256,  256,  123,  265,  258,  257,
                258,  258,  271,  125,  222,  178,  277,  278,  279,  280,
                256,  277,  278,  279,  280,  257,  277,  278,  279,  280,
                261,  262,  125,  270,  277,  278,  279,  280,  257,  266,
                267,  268,  205,  125,  257,  208,  256,  210,  276,  266,
                267,  268,  257,  266,  267,  268,  276,  276,  119,  257,
                258,  266,  267,  268,  257,  258,  257,  258,  256,  258,
                125,  256,  256,  256,  256,   41,  239,  256,   40,  257,
                257,  258,  256,  256,  256,  256,  256,  256,  256,  256,
                256,  256,  256,   40,  256,  256,  257,  123,  257,  260,
                125,  162,  263,  264,  265,  266,  267,  268,  269,  256,
                171,  272,  273,  256,  275,  123,  138,  139,  140,  123,
                125,  257,   44,  257,  260,   41,   41,  263,   43,   41,
                45,   40,  265,  269,  125,  196,  123,  198,  257,  275,
                123,   44,  256,   44,  256,  257,  256,  123,  260,  123,
                41,  263,  264,  265,  266,  267,  268,  269,  125,  181,
                272,  273,   41,  275,  257,  125,   41,  260,  125,  230,
                263,  256,  125,   44,   44,  257,  269,   44,  260,   44,
                241,  263,  275,  244,  245,  246,  125,  269,  257,  123,
                123,   41,  214,  275,  255,  264,  265,  266,  267,  268,
                125,   44,  257,  272,  273,  260,  123,  125,  263,  123,
                123,  272,   44,  274,  269,   44,  277,  278,  279,  125,
                275,  125,  257,  125,  123,  256,  256,  288,  256,  290,
                41,  256,  257,  256,   41,  260,  297,  125,  263,  264,
                265,  266,  267,  268,  269,  306,  256,  272,  273,  125,
                275,  256,  257,  256,  276,  260,  256,  123,  263,  264,
                265,  266,  267,  268,  269,  256,  257,  272,  273,  260,
                275,  125,  263,  264,  265,  266,  267,  268,  269,  257,
                262,  272,  273,  125,  275,   44,   44,   44,   44,  256,
                257,  256,   41,  260,  262,  125,  263,  264,  265,  266,
                267,  268,  269,  256,  257,  272,  273,  260,  275,    0,
                263,  264,  265,  266,  267,  268,  269,  256,  257,  272,
                273,  260,  275,   41,  263,  264,  265,  266,  267,  268,
                269,  262,  257,  272,  273,  260,  275,  256,  263,  257,
                41,   41,  260,   41,  269,  263,  256,   41,  256,  256,
                275,  269,   20,  257,  146,  257,  260,  275,  260,  263,
                256,  263,   -1,   -1,   -1,  269,   -1,  269,   -1,  257,
                -1,  275,   -1,  275,  171,   -1,  264,  265,  266,  267,
                268,  257,   -1,   -1,  272,  273,   -1,   -1,  264,  265,
                266,  267,  268,   -1,   -1,   -1,  272,  273,   -1,  196,
                -1,   -1,   -1,  257,   -1,   -1,   -1,   -1,   -1,   -1,
                264,  265,  266,  267,  268,  257,   -1,   -1,  272,  273,
                -1,   -1,  264,  265,  266,  267,  268,  257,   35,   36,
                272,  273,   39,   -1,  264,  265,  266,  267,  268,   -1,
                -1,   -1,  272,  273,  241,   -1,   -1,  244,  245,  246,
                -1,   -1,   -1,   -1,   61,   -1,   -1,   64,  255,   -1,
                -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
                -1,   -1,   -1,   -1,   -1,   -1,   -1,  274,   -1,   -1,
                -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
                -1,   -1,   -1,  290,   -1,   -1,   -1,   -1,   -1,   -1,
                -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
                -1,   -1,   -1,  120,  121,  122,  123,  124,  125,
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
            "asignacion : ID ',' ',' error",
            "asignacion : ID MENOS_IGUAL ',' error",
            "asignacion : ref_clase ',' ',' error",
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

//#line 199 ".\gramatica.y"
    /* CODE SECTION */

    public void chequeoRango(String cte, Analizador_Lexico al){
        if (cte.contains("_s")){
            int cteint = Integer.parseInt(cte.substring(0, cte.length()-2));
            double min = Math.pow(-2,7);
            double max = Math.pow(2,7)-1;
            if ((cteint < min)||(cteint>max)){
                System.out.println("ERROR. Linea "+al.getLinea()+": Constante fuera de rango");
            }
        } else if (cte.contains("_ul")) {
            long cteint_largo = Integer.parseInt(cte.substring(0, cte.length()-3));
            double min = 0;
            double max = Math.pow(2,32)-1;
            if ((cteint_largo < min)||(cteint_largo>max)){
                System.out.println("ERROR. Linea "+al.getLinea()+": Constante fuera de rango");
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
                System.out.println("ERROR. Linea "+al.getLinea()+": Constante fuera de rango");
            }
        }
    }

    public int yylex(Analizador_Lexico al){
        int tok = al.yylex();
        yylval = new ParserVal(al.getBuffer());
        return tok;
    }
    public void yyerror(String s){}
    //#line 604 "Parser.java"
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
                {System.out.println("ERROR EN PROGRAMA. Linea: " + al.getLinea() + " se esperaba '}'");}
                break;
                case 3:
//#line 13 ".\gramatica.y"
                {System.out.println("ERROR EN PROGRAMA. Linea: " + al.getLinea() + " se esperaba '{'");}
                break;
                case 8:
//#line 22 ".\gramatica.y"
                {System.out.println("ERROR EN SENTENCIA. Linea: " + al.getLinea());}
                break;
                case 13:
//#line 33 ".\gramatica.y"
                {System.out.println("Linea: " + al.getLinea() + " Declaracion de VARIABLE/S de TIPO " + val_peek(2).sval);}
                break;
                case 14:
//#line 34 ".\gramatica.y"
                {System.out.println("Linea: " + al.getLinea() + " Declaracion de VARIABLE/S de TIPO " + val_peek(2).sval);}
                break;
                case 15:
//#line 35 ".\gramatica.y"
                {System.out.println("ERROR EN DECLARACION DE VARIABLES. Linea: " + al.getLinea() + " se esperaba ','");}
                break;
                case 16:
//#line 36 ".\gramatica.y"
                {System.out.println("ERROR EN DECLARACION DE VARIABLES. Linea: " + al.getLinea() + " se esperaba ','");}
                break;
                case 26:
//#line 52 ".\gramatica.y"
                {System.out.println("Linea: " + al.getLinea() + " Declaracion funcion VOID " + val_peek(6).sval);}
                break;
                case 27:
//#line 53 ".\gramatica.y"
                {System.out.println("Linea: " + al.getLinea() + " Declaracion funcion VOID " + val_peek(8).sval);}
                break;
                case 28:
//#line 54 ".\gramatica.y"
                {System.out.println("ERROR EN DECLARACION DE FUNCION. Linea: " + al.getLinea() + " se esperaba ')'");}
                break;
                case 29:
//#line 55 ".\gramatica.y"
                {System.out.println("ERROR EN DECLARACION DE FUNCION. Linea: " + al.getLinea() + " se esperaba '('");}
                break;
                case 30:
//#line 56 ".\gramatica.y"
                {System.out.println("ERROR EN DECLARACION DE FUNCION. Linea: " + al.getLinea() + " falta el tipo de " + val_peek(6).sval);}
                break;
                case 31:
//#line 57 ".\gramatica.y"
                {System.out.println("ERROR EN DECLARACION DE FUNCION. Linea: " + al.getLinea() + " no se puede declarar una funcion sin cuerpo");}
                break;
                case 32:
//#line 58 ".\gramatica.y"
                {System.out.println("ERROR EN DECLARACION DE FUNCION. Linea: " + al.getLinea() + " no se puede declarar una funcion sin cuerpo");}
                break;
                case 33:
//#line 59 ".\gramatica.y"
                {System.out.println("ERROR EN DECLARACION DE FUNCION. Linea: " + al.getLinea() + " se esperaba ','");}
                break;
                case 34:
//#line 60 ".\gramatica.y"
                {System.out.println("ERROR EN DECLARACION DE FUNCION. Linea: " + al.getLinea() + " se esperaba ','");}
                break;
                case 35:
//#line 63 ".\gramatica.y"
                {System.out.println("Linea: " + al.getLinea() + " Declaracion CLASE " + val_peek(4).sval);}
                break;
                case 36:
//#line 64 ".\gramatica.y"
                {System.out.println("Linea: " + al.getLinea() + " Declaracion CLASE " + val_peek(6).sval);}
                break;
                case 37:
//#line 65 ".\gramatica.y"
                {System.out.println("ERROR EN DECLARACION DE CLASE. Linea: " + al.getLinea() + " no se puede definir una clase sin cuerpo");}
                break;
                case 38:
//#line 66 ".\gramatica.y"
                {System.out.println("ERROR EN DECLARACION DE CLASE. Linea: " + al.getLinea() + " no se puede definir una clase sin cuerpo");}
                break;
                case 39:
//#line 67 ".\gramatica.y"
                {System.out.println("ERROR EN DECLARACION DE CLASE. Linea: " + al.getLinea() + " falta el identificador de la interfaz");}
                break;
                case 40:
//#line 68 ".\gramatica.y"
                {System.out.println("ERROR EN DECLARACION DE CLASE. Linea: " + al.getLinea() + " se esperaba ','");}
                break;
                case 41:
//#line 69 ".\gramatica.y"
                {System.out.println("ERROR EN DECLARACION DE CLASE. Linea: " + al.getLinea() + " se esperaba ','");}
                break;
                case 42:
//#line 72 ".\gramatica.y"
                {System.out.println("Linea: " + al.getLinea() + " Declaracion DISTRIBUIDA para " + val_peek(5).sval);}
                break;
                case 43:
//#line 73 ".\gramatica.y"
                {System.out.println("ERROR EN DECLARACION DISTRIBUIDA. Linea: " + al.getLinea() + " se esperaba ':'");}
                break;
                case 44:
//#line 74 ".\gramatica.y"
                {System.out.println("ERROR EN DECLARACION DISTRIBUIDA. Linea: " + al.getLinea() + " no se puede definir una declaracion distribuida sin cuerpo");}
                break;
                case 45:
//#line 75 ".\gramatica.y"
                {System.out.println("ERROR EN DECLARACION DISTRIBUIDA. Linea: " + al.getLinea() + " falta palabra reservada FOR");}
                break;
                case 46:
//#line 76 ".\gramatica.y"
                {System.out.println("ERROR EN DECLARACION DISTRIBUIDA. Linea: " + al.getLinea() + " se esperaba ','");}
                break;
                case 47:
//#line 79 ".\gramatica.y"
                {System.out.println("Linea: " + al.getLinea() + " Declaracion INTERFAZ " + val_peek(4).sval);}
                break;
                case 48:
//#line 80 ".\gramatica.y"
                {System.out.println("ERROR EN INTERFAZ. Linea: " + al.getLinea() + " no se puede declarar una interfaz sin metodos");}
                break;
                case 49:
//#line 81 ".\gramatica.y"
                {System.out.println("ERROR EN INTERFAZ. Linea: " + al.getLinea() + " se esperaba ','");}
                break;
                case 52:
//#line 86 ".\gramatica.y"
                {System.out.println("ERROR EN METODO DE INTERFAZ. Linea: " + al.getLinea() + " se esperaba ','");}
                break;
                case 53:
//#line 87 ".\gramatica.y"
                {System.out.println("ERROR EN METODO DE INTERFAZ. Linea: " + al.getLinea() + " se esperaba ','");}
                break;
                case 54:
//#line 90 ".\gramatica.y"
                {System.out.println("Linea: " + al.getLinea() + " Declaracion metodo VOID " + val_peek(4).sval);}
                break;
                case 55:
//#line 91 ".\gramatica.y"
                {System.out.println("Linea: " + al.getLinea() + " Declaracion metodo VOID " + val_peek(2).sval);}
                break;
                case 56:
//#line 92 ".\gramatica.y"
                {System.out.println("ERROR EN METODO DE INTERFAZ. Linea: " + al.getLinea() + " se esperaba ')'");}
                break;
                case 57:
//#line 93 ".\gramatica.y"
                {System.out.println("ERROR EN METODO DE INTERFAZ. Linea: " + al.getLinea() + " se esperaba '('");}
                break;
                case 58:
//#line 94 ".\gramatica.y"
                {System.out.println("ERROR EN METODO DE INTERFAZ. Linea: " + al.getLinea() + " falta el tipo de " + val_peek(2).sval);}
                break;
                case 59:
//#line 97 ".\gramatica.y"
                {System.out.println("Linea: " + al.getLinea() + " ASIGNACION");}
                break;
                case 60:
//#line 98 ".\gramatica.y"
                {System.out.println("Linea: " + al.getLinea() + " INVOCACION FUNCION");}
                break;
                case 61:
//#line 99 ".\gramatica.y"
                {System.out.println("ERROR EN INVOCACION A LA FUNCION. Linea: " + al.getLinea() + " se esperaba ','");}
                break;
                case 62:
//#line 100 ".\gramatica.y"
                {System.out.println("Linea: " + al.getLinea() + " Sentencia IF");}
                break;
                case 63:
//#line 101 ".\gramatica.y"
                {System.out.println("ERROR EN SENTENCIA IF. Linea: " + al.getLinea() + " se esperaba ','");}
                break;
                case 64:
//#line 102 ".\gramatica.y"
                {System.out.println("Linea: " + al.getLinea() + " SENTENCIA DE IMPRESION");}
                break;
                case 65:
//#line 103 ".\gramatica.y"
                {System.out.println("ERROR EN SENTENCIA DE IMPRESION. Linea: " + al.getLinea() + " se esperaba ','");}
                break;
                case 66:
//#line 104 ".\gramatica.y"
                {System.out.println("Linea: " + al.getLinea() + " REFERENCIA A CLASE");}
                break;
                case 67:
//#line 105 ".\gramatica.y"
                {System.out.println("ERROR EN REFERENCIA A CLASE. Linea: " + al.getLinea() + " se esperaba ','");}
                break;
                case 69:
//#line 107 ".\gramatica.y"
                {System.out.println("ERROR EN SENTENCIA FOR. Linea: " + al.getLinea() + " se esperaba ','");}
                break;
                case 71:
//#line 109 ".\gramatica.y"
                {System.out.println("ERROR EN RETURN. Linea: " + al.getLinea() + " se esperaba ','");}
                break;
                case 76:
//#line 116 ".\gramatica.y"
                {System.out.println("ERROR EN ASIGNACION. Linea: " + al.getLinea() + " Se esperaba expresion");}
                break;
                case 77:
//#line 117 ".\gramatica.y"
                {System.out.println("ERROR EN ASIGNACION. Linea: " + al.getLinea() + " Se esperaba expresion");}
                break;
                case 78:
//#line 118 ".\gramatica.y"
                {System.out.println("ERROR EN ASIGNACION. Linea: " + al.getLinea() + " Se esperaba expresion");}
                break;
                case 79:
//#line 119 ".\gramatica.y"
                {System.out.println("ERROR EN ASIGNACION. Linea: " + al.getLinea() + " Se esperaba expresion");}
                break;
                case 80:
//#line 120 ".\gramatica.y"
                {System.out.println("ERROR EN ASIGNACION. Linea: " + al.getLinea() + " se esperaba ','");}
                break;
                case 81:
//#line 121 ".\gramatica.y"
                {System.out.println("ERROR EN ASIGNACION. Linea: " + al.getLinea() + " se esperaba ','");}
                break;
                case 82:
//#line 122 ".\gramatica.y"
                {System.out.println("ERROR EN ASIGNACION. Linea: " + al.getLinea() + " se esperaba ','");}
                break;
                case 83:
//#line 123 ".\gramatica.y"
                {System.out.println("ERROR EN ASIGNACION. Linea: " + al.getLinea() + " se esperaba ','");}
                break;
                case 84:
//#line 126 ".\gramatica.y"
                {System.out.println("Linea: " + al.getLinea() + " SUMA");}
                break;
                case 85:
//#line 127 ".\gramatica.y"
                {System.out.println("Linea: " + al.getLinea() + " RESTA");}
                break;
                case 87:
//#line 131 ".\gramatica.y"
                {System.out.println("Linea: " + al.getLinea() + " MULTIPLICACION");}
                break;
                case 88:
//#line 132 ".\gramatica.y"
                {System.out.println("Linea: " + al.getLinea() + " DIVISION");}
                break;
                case 92:
//#line 139 ".\gramatica.y"
                { chequeoRango(val_peek(0).sval, al);
                    al.getTablaSimbolos().getAtributos(val_peek(0).sval).sumarUso();
                }
                break;
                case 93:
//#line 142 ".\gramatica.y"
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
                case 98:
//#line 161 ".\gramatica.y"
                {System.out.println("ERROR EN SENTENCIA IF. Linea: " + al.getLinea() + " falta condicion");}
                break;
                case 99:
//#line 162 ".\gramatica.y"
                {System.out.println("ERROR EN SENTENCIA IF. Linea: " + al.getLinea() + " falta condicion");}
                break;
                case 100:
//#line 163 ".\gramatica.y"
                {System.out.println("ERROR EN SENTENCIA IF. Linea: " + al.getLinea() + " cuerpo de IF vacio");}
                break;
                case 101:
//#line 164 ".\gramatica.y"
                {System.out.println("ERROR EN SENTENCIA IF. Linea: " + al.getLinea() + " cuerpo de IF vacio");}
                break;
                case 109:
//#line 177 ".\gramatica.y"
                {System.out.println("ERROR EN SENTENCIA DE IMPRESION. Linea: " + al.getLinea() + " se esperaba una cadena de caracteres");}
                break;
                case 112:
//#line 184 ".\gramatica.y"
                {System.out.println("Linea: " + al.getLinea() + " Sentencia FOR");}
                break;
                case 113:
//#line 185 ".\gramatica.y"
                {System.out.println("ERROR EN SENTENCIA FOR. Linea: " + al.getLinea() + " falta encabezado");}
                break;
                case 114:
//#line 186 ".\gramatica.y"
                {System.out.println("ERROR EN SENTENCIA FOR. Linea: " + al.getLinea() + " se esperaba un identificador");}
                break;
                case 115:
//#line 187 ".\gramatica.y"
                {System.out.println("ERROR EN SENTENCIA FOR. Linea: " + al.getLinea() + " falta la palabra reservada IN");}
                break;
                case 116:
//#line 188 ".\gramatica.y"
                {System.out.println("ERROR EN SENTENCIA FOR. Linea: " + al.getLinea() + " falta la palabra reservada RANGE");}
                break;
                case 117:
//#line 189 ".\gramatica.y"
                {System.out.println("ERROR EN SENTENCIA FOR. Linea: " + al.getLinea() + " no se puede definir un FOR sin cuerpo");}
                break;
                case 118:
//#line 192 ".\gramatica.y"
                {System.out.println("Linea: " + al.getLinea() + " ENCABEZADO FOR");}
                break;
                case 119:
//#line 193 ".\gramatica.y"
                {System.out.println("ERROR EN ENCABEZADO FOR. Linea: " + al.getLinea() + " falta una constante");}
                break;
                case 120:
//#line 194 ".\gramatica.y"
                {System.out.println("ERROR EN ENCABEZADO FOR. Linea: " + al.getLinea() + " faltan constantes");}
                break;
//#line 1073 "Parser.java"
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
