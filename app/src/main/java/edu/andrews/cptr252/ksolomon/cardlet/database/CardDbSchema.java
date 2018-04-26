package edu.andrews.cptr252.ksolomon.cardlet.database;

/**
 * Created by solomonjkim on 4/22/18.
 */

public class CardDbSchema {
    public static final class CardTable {
        public static final String NAME = "cards";

        /**
         * DB column names
         */
        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String TRUE = "true";
            public static final String FALSE = "false";
        }
    }
}
