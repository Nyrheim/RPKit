/*
 * This file is generated by jOOQ.
*/
package com.rpkit.auctions.bukkit.database.jooq.rpkit;


import com.rpkit.auctions.bukkit.database.jooq.rpkit.tables.RpkitAuction;
import com.rpkit.auctions.bukkit.database.jooq.rpkit.tables.RpkitBid;

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

    public static final Index RPKIT_AUCTION_PRIMARY = Indexes0.RPKIT_AUCTION_PRIMARY;
    public static final Index RPKIT_BID_PRIMARY = Indexes0.RPKIT_BID_PRIMARY;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Indexes0 extends AbstractKeys {
        public static Index RPKIT_AUCTION_PRIMARY = createIndex("PRIMARY", RpkitAuction.RPKIT_AUCTION, new OrderField[] { RpkitAuction.RPKIT_AUCTION.ID }, true);
        public static Index RPKIT_BID_PRIMARY = createIndex("PRIMARY", RpkitBid.RPKIT_BID, new OrderField[] { RpkitBid.RPKIT_BID.ID }, true);
    }
}
