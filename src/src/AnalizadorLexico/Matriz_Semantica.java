package AnalizadorLexico;

import AnalizadorLexico.Acciones_Semanticas.*;

public class Matriz_Semantica extends Matriz {

    public Matriz_Semantica() {
        AS0 as0 = new AS0();
        AS1 as1 = new AS1();
        AS2 as2 = new AS2();
        AS3 as3 = new AS3();
        AS4 as4 = new AS4();
        AS7 as7 = new AS7();
        AS8 as8 = new AS8();
        AS9 as9 = new AS9();
        AS11 as11 = new AS11();
        AS12 as12 = new AS12();
        AS13 as13 = new AS13();
        AS14 as14 = new AS14();
        ERR err = new ERR();
        matrix = new Accion_Semantica[][]{
    //       punt    L     l     d     _     .     u     s    'd'    l     D    /n    bl    tab    /     *     +     -     =     <     >     %     !    ot     $
    //         0     1     2     3     4     5     6     7     8     9     10    11   12    13     14    15   16    17    18    19    20    21    22    23    24
    /*0*/   {as12,  as1,  as1,  as1,  as1,  as1,  as1,  as1,  as1,  as1,  err,  as9,  as9,  as9, as12,  as1, as12,  as1,  as1,  as1,  as1,  as1,  as1,  err,  as0},
    /*1*/   { as3,  as3,  as2,  as2,  as2,  as3,  as2,  as2,  as2,  as2,  as3,  as3,  as3,  as3,  as3,  as3,  as3,  as3,  as3,  as3,  as3,  as3,  as3,  as3,  as0},
    /*2*/   { err,  err,  err,  as2,  as2,  as2,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  as0},
    /*3*/   { err,  err,  err,  err,  err,  err,  as2,  as7,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  as0},
    /*4*/   { err,  err,  err,  err,  err,  err,  err,  err,  err,  as7,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  as0},
    /*5*/   { err,  err,  as14,  as2,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  as0},
    /*6*/   { as8,  as8,  as8,  as2,  as8,  as8,  as8,  as8,  as2,  as8,  as2,  as8,  as8,  as8,  as8,  as8,  as8,  as8,  as8,  as8,  as8,  as8,  as8,  as8,  as0},
    /*7*/   { err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  as2,  as2,  err,  err,  err,  err,  err,  err,  as0},
    /*8*/   { err,  err,  err,  as2,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  as0},
    /*9*/   { as8,  as8,  as8,  as2,  as8,  as8,  as8,  as8,  as8,  as8,  as8,  as8,  as8,  as8,  as8,  as8,  as8,  as8,  as8,  as8,  as8,  as8,  as8,  as8,  as0},
    /*10*/  {as14, as14, as14, as14, as14,  as14, as14,  as14,  as14,  as14,  as14,  as14,  as14,  as14,  as14,  as2,  as14,  as14,  as14,  as14,  as14,  as14,  as14,  as14,  as0},
    /*11*/  { as2,  as2,  as2,  as2,  as2,  as2,  as2,  as2,  as2,  as2,  as2,  as4,  as2,  as2,  as2,  as2,  as2,  as2,  as2,  as2,  as2,  as2,  as2,  as2,  as2},
    /*12*/  {as14, as14, as14, as14, as14, as14, as14, as14, as14, as14, as14, as14, as14, as14, as14, as14, as14, as14, as13, as14, as14, as14, as14, as14, as0},
    /*13*/  {as14, as14, as14, as14, as14, as14, as14, as14, as14, as14, as14, as14, as14, as14, as14, as14, as14, as14, as13, as14, as14, as14, as14, as14, as0},
    /*14*/  {as14, as14, as14, as14, as14, as14, as14, as14, as14, as14, as14, as14, as14, as14, as14, as14, as14, as14, as13, as14, as14, as14, as14, as14, as0},
    /*15*/  {as14, as14, as14, as14, as14, as14, as14, as14, as14, as14, as14, as14, as14, as14, as14, as14, as14, as14, as13, as14, as14, as14, as14, as14, as0},
    /*16*/  { as2,  as2,  as2,  as2,  as2,  as2,  as2,  as2,  as2,  as2,  as2,  as2,  as2,  as2,  as2,  as2,  as2,  as2,  as2,  as2,  as2,  as13,  as2,  as2,  as0},
    /*17*/  { err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  as13,  err,  as0},
    /*18*/  {as11,  as2, as11, as11,  as2, as11, as11, as11, as11, as11,  as2, as11, as11, as11, as11, as11, as11, as11, as11, as11, as11, as11, as11, as11,  as0},
        };
    }
}
