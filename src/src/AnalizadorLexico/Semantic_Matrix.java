package AnalizadorLexico;

import AnalizadorLexico.Semantic_Actions.Semantic_Action;

public class Semantic_Matrix extends Matrix{

    public Semantic_Matrix() {
        matrix = new Semantic_Action[][]{
    //       punt   L     l     d    _     .     u     s     d     l     D    /n    bl    tab    /     *     +     -     =     <     >     %     !    ot    $
    //         0    1     2     3    4     5     6     7     8     9    10    11    12    13    14    15    16    17    18    19    20    21    22    23   24
    /*0*/   { AS1, AS1, AS10,  AS1, AS1,  AS1,  ERR,  ERR,  ERR,  ERR,  ERR,  AS9,  AS9,  AS9,  AS1,  AS1,  AS1,  AS1,  AS1,  AS1,  AS1,  AS1,  AS1,  ERR,  },
    /*1*/   { AS3, AS3,  AS2,  AS2, AS2,  AS3,  AS3,  AS3,  AS3,  AS3,  AS3,  AS3,  AS3,  AS3,  AS3,  AS3,  AS3,  AS3,  AS3,  AS3,  AS3,  AS3,  AS3,  AS3,  },
    /*2*/   { ERR, ERR,  ERR,  AS2, AS2,  AS2,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  },
    /*3*/   { ERR, ERR,  ERR,  ERR, ERR,  ERR,  AS2,  AS7,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  },
    /*4*/   { ERR, ERR,  ERR,  ERR, ERR,  ERR,  ERR,  ERR,  AS7,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  },
    /*5*/   { ERR, ERR,  ERR,  AS2, ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  },
    /*6*/   { ERR, ERR,  AS8,  AS8, AS2,  AS8,  AS8,  AS8,  AS8,  AS8,  AS8,  AS8,  AS8,  AS8,  AS8,  AS8,  AS8,  AS8,  AS8,  AS8,  AS8,  AS8,  AS8,  AS8,  },
    /*7*/   { ERR, ERR,  ERR,  ERR, ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  AS2,  AS2,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  },
    /*8*/   { ERR, ERR,  ERR,  AS2, ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  },
    /*9*/   { ERR, AS8,  AS8,  AS2, AS8,  AS8,  AS8,  AS8,  AS8,  AS8,  AS8,  AS8,  AS8,  AS8,  AS8,  AS8,  AS8,  AS8,  AS8,  AS8,  AS8,  AS8,  AS8,  AS8,  },
    /*10*/  { AS4, AS4,  AS4,  AS4, AS4,  AS4,  AS4,  AS4,  AS4,  AS4,  AS4,  AS4,  AS4,  AS4,  AS4,  AS2,  AS4,  AS4,  AS4,  AS4,  AS4,  AS4,  AS4,  AS4,  },
    /*11*/  { AS2, AS2,  AS2,  AS2, AS2,  AS2,  AS2,  AS2,  AS2,  AS2,  AS2,  AS4,  AS2,  AS2,  AS2,  AS2,  AS2,  AS2,  AS2,  AS2,  AS2,  AS2,  AS2,  AS2,  },
    /*12*/  { AS4, AS4,  AS4,  AS4, AS4,  AS4,  AS4,  AS4,  AS4,  AS4,  AS4,  AS4,  AS4,  AS4,  AS4,  AS4,  AS4,  AS2,  AS4,  AS4,  AS4,  AS4,  AS4,  AS4,  },
    /*13*/  { AS2, AS2,  AS2,  AS2, AS2,  AS2,  AS2,  AS2,  AS2,  AS2,  AS2,  AS2,  AS2,  AS2,  AS2,  AS2,  AS2,  AS2,  AS2,  AS4,  AS2,  AS2,  AS2,  AS2,  },
    /*14*/  { AS2, AS2,  AS2,  AS2, AS2,  AS2,  AS2,  AS2,  AS2,  AS2,  AS2,  AS2,  AS2,  AS2,  AS2,  AS2,  AS2,  AS2,  AS2,  AS2,  AS4,  AS2,  AS2,  AS2,  },
    /*15*/  { AS4, AS4,  AS4,  AS4, AS4,  AS4,  AS4,  AS4,  AS4,  AS4,  AS4,  AS4,  AS4,  AS4,  AS4,  AS4,  AS4,  AS4,  AS2,  AS4,  AS4,  AS4,  AS4,  AS4,  },
    /*16*/  { AS2, AS2,  AS2,  AS2, AS2,  AS2,  AS2,  AS2,  AS2,  AS2,  AS2,  AS2,  AS2,  AS2,  AS2,  AS2,  AS2,  AS2,  AS2,  AS2,  AS2,  AS2,  AS2,  AS2,  },
    /*17*/  { ERR, ERR,  ERR,  ERR, ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  AS2,  ERR,  },
    /*18*/  {AS11, AS2, AS11, AS11, AS2, AS11, AS11, AS11, AS11, AS11, AS11, AS11, AS11, AS11, AS11, AS11, AS11, AS11, AS11, AS11, AS11, AS11, AS11, AS11,  },
        };
    }
}
