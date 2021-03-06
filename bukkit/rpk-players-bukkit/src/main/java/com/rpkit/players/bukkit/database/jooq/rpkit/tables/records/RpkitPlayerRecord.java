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
package com.rpkit.players.bukkit.database.jooq.rpkit.tables.records;


import com.rpkit.players.bukkit.database.jooq.rpkit.tables.RpkitPlayer;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record5;
import org.jooq.Row5;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class RpkitPlayerRecord extends UpdatableRecordImpl<RpkitPlayerRecord> implements Record5<Integer, String, String, String, String> {

    private static final long serialVersionUID = 1447865013;

    /**
     * Setter for <code>rpkit.rpkit_player.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>rpkit.rpkit_player.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>rpkit.rpkit_player.name</code>.
     */
    public void setName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>rpkit.rpkit_player.name</code>.
     */
    public String getName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>rpkit.rpkit_player.minecraft_uuid</code>.
     */
    public void setMinecraftUuid(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>rpkit.rpkit_player.minecraft_uuid</code>.
     */
    public String getMinecraftUuid() {
        return (String) get(2);
    }

    /**
     * Setter for <code>rpkit.rpkit_player.irc_nick</code>.
     */
    public void setIrcNick(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>rpkit.rpkit_player.irc_nick</code>.
     */
    public String getIrcNick() {
        return (String) get(3);
    }

    /**
     * Setter for <code>rpkit.rpkit_player.last_known_ip</code>.
     */
    public void setLastKnownIp(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>rpkit.rpkit_player.last_known_ip</code>.
     */
    public String getLastKnownIp() {
        return (String) get(4);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record5 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row5<Integer, String, String, String, String> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    @Override
    public Row5<Integer, String, String, String, String> valuesRow() {
        return (Row5) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return RpkitPlayer.RPKIT_PLAYER.ID;
    }

    @Override
    public Field<String> field2() {
        return RpkitPlayer.RPKIT_PLAYER.NAME;
    }

    @Override
    public Field<String> field3() {
        return RpkitPlayer.RPKIT_PLAYER.MINECRAFT_UUID;
    }

    @Override
    public Field<String> field4() {
        return RpkitPlayer.RPKIT_PLAYER.IRC_NICK;
    }

    @Override
    public Field<String> field5() {
        return RpkitPlayer.RPKIT_PLAYER.LAST_KNOWN_IP;
    }

    @Override
    public Integer component1() {
        return getId();
    }

    @Override
    public String component2() {
        return getName();
    }

    @Override
    public String component3() {
        return getMinecraftUuid();
    }

    @Override
    public String component4() {
        return getIrcNick();
    }

    @Override
    public String component5() {
        return getLastKnownIp();
    }

    @Override
    public Integer value1() {
        return getId();
    }

    @Override
    public String value2() {
        return getName();
    }

    @Override
    public String value3() {
        return getMinecraftUuid();
    }

    @Override
    public String value4() {
        return getIrcNick();
    }

    @Override
    public String value5() {
        return getLastKnownIp();
    }

    @Override
    public RpkitPlayerRecord value1(Integer value) {
        setId(value);
        return this;
    }

    @Override
    public RpkitPlayerRecord value2(String value) {
        setName(value);
        return this;
    }

    @Override
    public RpkitPlayerRecord value3(String value) {
        setMinecraftUuid(value);
        return this;
    }

    @Override
    public RpkitPlayerRecord value4(String value) {
        setIrcNick(value);
        return this;
    }

    @Override
    public RpkitPlayerRecord value5(String value) {
        setLastKnownIp(value);
        return this;
    }

    @Override
    public RpkitPlayerRecord values(Integer value1, String value2, String value3, String value4, String value5) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached RpkitPlayerRecord
     */
    public RpkitPlayerRecord() {
        super(RpkitPlayer.RPKIT_PLAYER);
    }

    /**
     * Create a detached, initialised RpkitPlayerRecord
     */
    public RpkitPlayerRecord(Integer id, String name, String minecraftUuid, String ircNick, String lastKnownIp) {
        super(RpkitPlayer.RPKIT_PLAYER);

        set(0, id);
        set(1, name);
        set(2, minecraftUuid);
        set(3, ircNick);
        set(4, lastKnownIp);
    }
}
