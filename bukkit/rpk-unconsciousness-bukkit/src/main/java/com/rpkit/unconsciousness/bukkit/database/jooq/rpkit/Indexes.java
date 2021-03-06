/*
 * This file is generated by jOOQ.
*/
package com.rpkit.unconsciousness.bukkit.database.jooq.rpkit;


import com.rpkit.unconsciousness.bukkit.database.jooq.rpkit.tables.RpkitUnconsciousState;

import javax.annotation.Generated;

import org.jooq.Index;
import org.jooq.OrderField;
import org.jooq.impl.AbstractKeys;


/**
 * A class modelling indexes of tables of the <code>rpkit</code> schema.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.2"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Indexes {

    // -------------------------------------------------------------------------
    // INDEX definitions
    // -------------------------------------------------------------------------

    public static final Index RPKIT_UNCONSCIOUS_STATE_PRIMARY = Indexes0.RPKIT_UNCONSCIOUS_STATE_PRIMARY;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Indexes0 extends AbstractKeys {
        public static Index RPKIT_UNCONSCIOUS_STATE_PRIMARY = createIndex("PRIMARY", RpkitUnconsciousState.RPKIT_UNCONSCIOUS_STATE, new OrderField[] { RpkitUnconsciousState.RPKIT_UNCONSCIOUS_STATE.ID }, true);
    }
}
