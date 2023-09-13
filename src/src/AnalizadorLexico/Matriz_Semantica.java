package AnalizadorLexico;

import AnalizadorLexico.Semantic_Actions.*;

public class Matriz_Semantica extends Matriz {

    public Matriz_Semantica() {
        AS1 as1 = new AS1();
        AS2 as2 = new AS2();
        AS3 as3 = new AS3();
        AS4 as4 = new AS4();
        AS7 as7 = new AS7();
        AS8 as8 = new AS8();
        AS9 as9 = new AS9();
        AS10 as10 = new AS10();
        AS11 as11 = new AS11();
        ERR err = new ERR();
        matrix = new Accion_Semantica[][]{
    //       punt   L     l     d    _     .     u     s     d     l     D    /n    bl    tab    /     *     +     -     =     <     >     %     !    ot    $
    //         0    1     2     3    4     5     6     7     8     9    10    11    12    13    14    15    16    17    18    19    20    21    22    23   24
    /*0*/   { as1, as1, as10,  as1, as1,  as1,  err,  err,  err,  err,  err,  as9,  as9,  as9,  as1,  as1,  as1,  as1,  as1,  as1,  as1,  as1,  as1,  err,  },
    /*1*/   { as3, as3,  as2,  as2, as2,  as3,  as3,  as3,  as3,  as3,  as3,  as3,  as3,  as3,  as3,  as3,  as3,  as3,  as3,  as3,  as3,  as3,  as3,  as3,  },
    /*2*/   { err, err,  err,  as2, as2,  as2,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  },
    /*3*/   { err, err,  err,  err, err,  err,  as2,  as7,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  },
    /*4*/   { err, err,  err,  err, err,  err,  err,  err,  as7,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  },
    /*5*/   { err, err,  err,  as2, err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  },
    /*6*/   { err, err,  as8,  as8, as2,  as8,  as8,  as8,  as8,  as8,  as8,  as8,  as8,  as8,  as8,  as8,  as8,  as8,  as8,  as8,  as8,  as8,  as8,  as8,  },
    /*7*/   { err, err,  err,  err, err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  as2,  as2,  err,  err,  err,  err,  err,  err,  },
    /*8*/   { err, err,  err,  as2, err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  },
    /*9*/   { err, as8,  as8,  as2, as8,  as8,  as8,  as8,  as8,  as8,  as8,  as8,  as8,  as8,  as8,  as8,  as8,  as8,  as8,  as8,  as8,  as8,  as8,  as8,  },
    /*10*/  { as4, as4,  as4,  as4, as4,  as4,  as4,  as4,  as4,  as4,  as4,  as4,  as4,  as4,  as4,  as2,  as4,  as4,  as4,  as4,  as4,  as4,  as4,  as4,  },
    /*11*/  { as2, as2,  as2,  as2, as2,  as2,  as2,  as2,  as2,  as2,  as2,  as4,  as2,  as2,  as2,  as2,  as2,  as2,  as2,  as2,  as2,  as2,  as2,  as2,  },
    /*12*/  { as4, as4,  as4,  as4, as4,  as4,  as4,  as4,  as4,  as4,  as4,  as4,  as4,  as4,  as4,  as4,  as4,  as2,  as4,  as4,  as4,  as4,  as4,  as4,  },
    /*13*/  { as2, as2,  as2,  as2, as2,  as2,  as2,  as2,  as2,  as2,  as2,  as2,  as2,  as2,  as2,  as2,  as2,  as2,  as2,  as4,  as2,  as2,  as2,  as2,  },
    /*14*/  { as2, as2,  as2,  as2, as2,  as2,  as2,  as2,  as2,  as2,  as2,  as2,  as2,  as2,  as2,  as2,  as2,  as2,  as2,  as2,  as4,  as2,  as2,  as2,  },
    /*15*/  { as4, as4,  as4,  as4, as4,  as4,  as4,  as4,  as4,  as4,  as4,  as4,  as4,  as4,  as4,  as4,  as4,  as4,  as2,  as4,  as4,  as4,  as4,  as4,  },
    /*16*/  { as2, as2,  as2,  as2, as2,  as2,  as2,  as2,  as2,  as2,  as2,  as2,  as2,  as2,  as2,  as2,  as2,  as2,  as2,  as2,  as2,  as2,  as2,  as2,  },
    /*17*/  { err, err,  err,  err, err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  err,  as2,  err,  },
    /*18*/  {as11, as2, as11, as11, as2, as11, as11, as11, as11, as11, as11, as11, as11, as11, as11, as11, as11, as11, as11, as11, as11, as11, as11, as11,  },
        };
    }
}
