/*
 * This file is generated by jOOQ.
*/
package com.rpkit.chat.bukkit.database.jooq.rpkit.tables;


import com.rpkit.chat.bukkit.database.jooq.rpkit.Indexes;
import com.rpkit.chat.bukkit.database.jooq.rpkit.Keys;
import com.rpkit.chat.bukkit.database.jooq.rpkit.Rpkit;
import com.rpkit.chat.bukkit.database.jooq.rpkit.tables.records.LastUsedChatGroupRecord;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Identity;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.2"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class LastUsedChatGroup extends TableImpl<LastUsedChatGroupRecord> {

    private static final long serialVersionUID = -506017218;

    /**
     * The reference instance of <code>rpkit.last_used_chat_group</code>
     */
    public static final LastUsedChatGroup LAST_USED_CHAT_GROUP = new LastUsedChatGroup();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<LastUsedChatGroupRecord> getRecordType() {
        return LastUsedChatGroupRecord.class;
    }

    /**
     * The column <code>rpkit.last_used_chat_group.id</code>.
     */
    public final TableField<LastUsedChatGroupRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>rpkit.last_used_chat_group.minecraft_profile_id</code>.
     */
    public final TableField<LastUsedChatGroupRecord, Integer> MINECRAFT_PROFILE_ID = createField("minecraft_profile_id", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>rpkit.last_used_chat_group.chat_group_id</code>.
     */
    public final TableField<LastUsedChatGroupRecord, Integer> CHAT_GROUP_ID = createField("chat_group_id", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * Create a <code>rpkit.last_used_chat_group</code> table reference
     */
    public LastUsedChatGroup() {
        this(DSL.name("last_used_chat_group"), null);
    }

    /**
     * Create an aliased <code>rpkit.last_used_chat_group</code> table reference
     */
    public LastUsedChatGroup(String alias) {
        this(DSL.name(alias), LAST_USED_CHAT_GROUP);
    }

    /**
     * Create an aliased <code>rpkit.last_used_chat_group</code> table reference
     */
    public LastUsedChatGroup(Name alias) {
        this(alias, LAST_USED_CHAT_GROUP);
    }

    private LastUsedChatGroup(Name alias, Table<LastUsedChatGroupRecord> aliased) {
        this(alias, aliased, null);
    }

    private LastUsedChatGroup(Name alias, Table<LastUsedChatGroupRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Rpkit.RPKIT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.LAST_USED_CHAT_GROUP_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<LastUsedChatGroupRecord, Integer> getIdentity() {
        return Keys.IDENTITY_LAST_USED_CHAT_GROUP;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<LastUsedChatGroupRecord> getPrimaryKey() {
        return Keys.KEY_LAST_USED_CHAT_GROUP_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<LastUsedChatGroupRecord>> getKeys() {
        return Arrays.<UniqueKey<LastUsedChatGroupRecord>>asList(Keys.KEY_LAST_USED_CHAT_GROUP_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LastUsedChatGroup as(String alias) {
        return new LastUsedChatGroup(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LastUsedChatGroup as(Name alias) {
        return new LastUsedChatGroup(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public LastUsedChatGroup rename(String name) {
        return new LastUsedChatGroup(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public LastUsedChatGroup rename(Name name) {
        return new LastUsedChatGroup(name, null);
    }
}
