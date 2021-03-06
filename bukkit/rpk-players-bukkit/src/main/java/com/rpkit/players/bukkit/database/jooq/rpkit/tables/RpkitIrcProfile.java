/*
 * Copyright 2020 Ren Binden
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * This file is generated by jOOQ.
 */
package com.rpkit.players.bukkit.database.jooq.rpkit.tables;


import com.rpkit.players.bukkit.database.jooq.rpkit.Keys;
import com.rpkit.players.bukkit.database.jooq.rpkit.Rpkit;
import com.rpkit.players.bukkit.database.jooq.rpkit.tables.records.RpkitIrcProfileRecord;

import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row3;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class RpkitIrcProfile extends TableImpl<RpkitIrcProfileRecord> {

    private static final long serialVersionUID = -1173036787;

    /**
     * The reference instance of <code>rpkit.rpkit_irc_profile</code>
     */
    public static final RpkitIrcProfile RPKIT_IRC_PROFILE = new RpkitIrcProfile();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<RpkitIrcProfileRecord> getRecordType() {
        return RpkitIrcProfileRecord.class;
    }

    /**
     * The column <code>rpkit.rpkit_irc_profile.id</code>.
     */
    public final TableField<RpkitIrcProfileRecord, Integer> ID = createField(DSL.name("id"), org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>rpkit.rpkit_irc_profile.profile_id</code>.
     */
    public final TableField<RpkitIrcProfileRecord, Integer> PROFILE_ID = createField(DSL.name("profile_id"), org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>rpkit.rpkit_irc_profile.nick</code>.
     */
    public final TableField<RpkitIrcProfileRecord, String> NICK = createField(DSL.name("nick"), org.jooq.impl.SQLDataType.VARCHAR(256), this, "");

    /**
     * Create a <code>rpkit.rpkit_irc_profile</code> table reference
     */
    public RpkitIrcProfile() {
        this(DSL.name("rpkit_irc_profile"), null);
    }

    /**
     * Create an aliased <code>rpkit.rpkit_irc_profile</code> table reference
     */
    public RpkitIrcProfile(String alias) {
        this(DSL.name(alias), RPKIT_IRC_PROFILE);
    }

    /**
     * Create an aliased <code>rpkit.rpkit_irc_profile</code> table reference
     */
    public RpkitIrcProfile(Name alias) {
        this(alias, RPKIT_IRC_PROFILE);
    }

    private RpkitIrcProfile(Name alias, Table<RpkitIrcProfileRecord> aliased) {
        this(alias, aliased, null);
    }

    private RpkitIrcProfile(Name alias, Table<RpkitIrcProfileRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public <O extends Record> RpkitIrcProfile(Table<O> child, ForeignKey<O, RpkitIrcProfileRecord> key) {
        super(child, key, RPKIT_IRC_PROFILE);
    }

    @Override
    public Schema getSchema() {
        return Rpkit.RPKIT;
    }

    @Override
    public Identity<RpkitIrcProfileRecord, Integer> getIdentity() {
        return Keys.IDENTITY_RPKIT_IRC_PROFILE;
    }

    @Override
    public UniqueKey<RpkitIrcProfileRecord> getPrimaryKey() {
        return Keys.KEY_RPKIT_IRC_PROFILE_PRIMARY;
    }

    @Override
    public List<UniqueKey<RpkitIrcProfileRecord>> getKeys() {
        return Arrays.<UniqueKey<RpkitIrcProfileRecord>>asList(Keys.KEY_RPKIT_IRC_PROFILE_PRIMARY);
    }

    @Override
    public RpkitIrcProfile as(String alias) {
        return new RpkitIrcProfile(DSL.name(alias), this);
    }

    @Override
    public RpkitIrcProfile as(Name alias) {
        return new RpkitIrcProfile(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public RpkitIrcProfile rename(String name) {
        return new RpkitIrcProfile(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public RpkitIrcProfile rename(Name name) {
        return new RpkitIrcProfile(name, null);
    }

    // -------------------------------------------------------------------------
    // Row3 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row3<Integer, Integer, String> fieldsRow() {
        return (Row3) super.fieldsRow();
    }
}
