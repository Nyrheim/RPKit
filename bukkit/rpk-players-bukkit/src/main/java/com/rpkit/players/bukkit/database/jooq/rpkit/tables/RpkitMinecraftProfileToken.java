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
import com.rpkit.players.bukkit.database.jooq.rpkit.tables.records.RpkitMinecraftProfileTokenRecord;

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
public class RpkitMinecraftProfileToken extends TableImpl<RpkitMinecraftProfileTokenRecord> {

    private static final long serialVersionUID = -2147137302;

    /**
     * The reference instance of <code>rpkit.rpkit_minecraft_profile_token</code>
     */
    public static final RpkitMinecraftProfileToken RPKIT_MINECRAFT_PROFILE_TOKEN = new RpkitMinecraftProfileToken();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<RpkitMinecraftProfileTokenRecord> getRecordType() {
        return RpkitMinecraftProfileTokenRecord.class;
    }

    /**
     * The column <code>rpkit.rpkit_minecraft_profile_token.id</code>.
     */
    public final TableField<RpkitMinecraftProfileTokenRecord, Integer> ID = createField(DSL.name("id"), org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>rpkit.rpkit_minecraft_profile_token.minecraft_profile_id</code>.
     */
    public final TableField<RpkitMinecraftProfileTokenRecord, Integer> MINECRAFT_PROFILE_ID = createField(DSL.name("minecraft_profile_id"), org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>rpkit.rpkit_minecraft_profile_token.token</code>.
     */
    public final TableField<RpkitMinecraftProfileTokenRecord, String> TOKEN = createField(DSL.name("token"), org.jooq.impl.SQLDataType.VARCHAR(36), this, "");

    /**
     * Create a <code>rpkit.rpkit_minecraft_profile_token</code> table reference
     */
    public RpkitMinecraftProfileToken() {
        this(DSL.name("rpkit_minecraft_profile_token"), null);
    }

    /**
     * Create an aliased <code>rpkit.rpkit_minecraft_profile_token</code> table reference
     */
    public RpkitMinecraftProfileToken(String alias) {
        this(DSL.name(alias), RPKIT_MINECRAFT_PROFILE_TOKEN);
    }

    /**
     * Create an aliased <code>rpkit.rpkit_minecraft_profile_token</code> table reference
     */
    public RpkitMinecraftProfileToken(Name alias) {
        this(alias, RPKIT_MINECRAFT_PROFILE_TOKEN);
    }

    private RpkitMinecraftProfileToken(Name alias, Table<RpkitMinecraftProfileTokenRecord> aliased) {
        this(alias, aliased, null);
    }

    private RpkitMinecraftProfileToken(Name alias, Table<RpkitMinecraftProfileTokenRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public <O extends Record> RpkitMinecraftProfileToken(Table<O> child, ForeignKey<O, RpkitMinecraftProfileTokenRecord> key) {
        super(child, key, RPKIT_MINECRAFT_PROFILE_TOKEN);
    }

    @Override
    public Schema getSchema() {
        return Rpkit.RPKIT;
    }

    @Override
    public Identity<RpkitMinecraftProfileTokenRecord, Integer> getIdentity() {
        return Keys.IDENTITY_RPKIT_MINECRAFT_PROFILE_TOKEN;
    }

    @Override
    public UniqueKey<RpkitMinecraftProfileTokenRecord> getPrimaryKey() {
        return Keys.KEY_RPKIT_MINECRAFT_PROFILE_TOKEN_PRIMARY;
    }

    @Override
    public List<UniqueKey<RpkitMinecraftProfileTokenRecord>> getKeys() {
        return Arrays.<UniqueKey<RpkitMinecraftProfileTokenRecord>>asList(Keys.KEY_RPKIT_MINECRAFT_PROFILE_TOKEN_PRIMARY);
    }

    @Override
    public RpkitMinecraftProfileToken as(String alias) {
        return new RpkitMinecraftProfileToken(DSL.name(alias), this);
    }

    @Override
    public RpkitMinecraftProfileToken as(Name alias) {
        return new RpkitMinecraftProfileToken(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public RpkitMinecraftProfileToken rename(String name) {
        return new RpkitMinecraftProfileToken(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public RpkitMinecraftProfileToken rename(Name name) {
        return new RpkitMinecraftProfileToken(name, null);
    }

    // -------------------------------------------------------------------------
    // Row3 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row3<Integer, Integer, String> fieldsRow() {
        return (Row3) super.fieldsRow();
    }
}
