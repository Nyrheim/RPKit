/*
 * Copyright 2016 Ross Binden
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

package com.rpkit.payments.bukkit.database.table

import com.rpkit.characters.bukkit.character.RPKCharacterProvider
import com.rpkit.core.database.Database
import com.rpkit.core.database.Table
import com.rpkit.payments.bukkit.RPKPaymentsBukkit
import com.rpkit.payments.bukkit.database.jooq.rpkit.Tables.RPKIT_PAYMENT_GROUP_OWNER
import com.rpkit.payments.bukkit.group.RPKPaymentGroup
import com.rpkit.payments.bukkit.group.RPKPaymentGroupProvider
import com.rpkit.payments.bukkit.group.owner.RPKPaymentGroupOwner
import org.ehcache.config.builders.CacheConfigurationBuilder
import org.ehcache.config.builders.ResourcePoolsBuilder
import org.jooq.impl.DSL.constraint
import org.jooq.impl.SQLDataType

/**
 * Represents payment group owner table.
 */
class RPKPaymentGroupOwnerTable(
        database: Database,
        private val plugin: RPKPaymentsBukkit
): Table<RPKPaymentGroupOwner>(database, RPKPaymentGroupOwner::class) {

    private val cache = if (plugin.config.getBoolean("caching.rpkit_payment_group_owner.id.enabled")) {
        database.cacheManager.createCache("rpk-payments-bukkit.rpkit_payment_group_owner.id", CacheConfigurationBuilder
                .newCacheConfigurationBuilder(Int::class.javaObjectType, RPKPaymentGroupOwner::class.java,
                        ResourcePoolsBuilder.heap(plugin.config.getLong("caching.rpkit_payment_group_owner.id.size"))))
    } else {
        null
    }

    override fun create() {
        database.create
                .createTableIfNotExists(RPKIT_PAYMENT_GROUP_OWNER)
                .column(RPKIT_PAYMENT_GROUP_OWNER.ID, SQLDataType.INTEGER.identity(true))
                .column(RPKIT_PAYMENT_GROUP_OWNER.PAYMENT_GROUP_ID, SQLDataType.INTEGER)
                .column(RPKIT_PAYMENT_GROUP_OWNER.CHARACTER_ID, SQLDataType.INTEGER)
                .constraints(
                        constraint("pk_rpkit_payment_group_owner").primaryKey(RPKIT_PAYMENT_GROUP_OWNER.ID)
                )
                .execute()
    }

    override fun applyMigrations() {
        if (database.getTableVersion(this) == null) {
            database.setTableVersion(this, "0.4.0")
        }
    }

    override fun insert(entity: RPKPaymentGroupOwner): Int {
        database.create
                .insertInto(
                        RPKIT_PAYMENT_GROUP_OWNER,
                        RPKIT_PAYMENT_GROUP_OWNER.PAYMENT_GROUP_ID,
                        RPKIT_PAYMENT_GROUP_OWNER.CHARACTER_ID
                )
                .values(
                        entity.paymentGroup.id,
                        entity.character.id
                )
                .execute()
        val id = database.create.lastID().toInt()
        entity.id = id
        cache?.put(id, entity)
        return id
    }

    override fun update(entity: RPKPaymentGroupOwner) {
        database.create
                .update(RPKIT_PAYMENT_GROUP_OWNER)
                .set(RPKIT_PAYMENT_GROUP_OWNER.PAYMENT_GROUP_ID, entity.paymentGroup.id)
                .set(RPKIT_PAYMENT_GROUP_OWNER.CHARACTER_ID, entity.character.id)
                .where(RPKIT_PAYMENT_GROUP_OWNER.ID.eq(entity.id))
                .execute()
        cache?.put(entity.id, entity)
    }

    override fun get(id: Int): RPKPaymentGroupOwner? {
        if (cache?.containsKey(id) == true) {
            return cache[id]
        } else {
            val result = database.create
                    .select(
                            RPKIT_PAYMENT_GROUP_OWNER.PAYMENT_GROUP_ID,
                            RPKIT_PAYMENT_GROUP_OWNER.CHARACTER_ID
                    )
                    .from(RPKIT_PAYMENT_GROUP_OWNER)
                    .where(RPKIT_PAYMENT_GROUP_OWNER.ID.eq(id))
                    .fetchOne() ?: return null
            val paymentGroupProvider = plugin.core.serviceManager.getServiceProvider(RPKPaymentGroupProvider::class)
            val paymentGroupId = result.get(RPKIT_PAYMENT_GROUP_OWNER.PAYMENT_GROUP_ID)
            val paymentGroup = paymentGroupProvider.getPaymentGroup(paymentGroupId)
            val characterProvider = plugin.core.serviceManager.getServiceProvider(RPKCharacterProvider::class)
            val characterId = result.get(RPKIT_PAYMENT_GROUP_OWNER.CHARACTER_ID)
            val character = characterProvider.getCharacter(characterId)
            if (paymentGroup != null && character != null) {
                val paymentGroupOwner = RPKPaymentGroupOwner(
                        id,
                        paymentGroup,
                        character
                )
                cache?.put(id, paymentGroupOwner)
                return paymentGroupOwner
            } else {
                database.create
                        .deleteFrom(RPKIT_PAYMENT_GROUP_OWNER)
                        .where(RPKIT_PAYMENT_GROUP_OWNER.ID.eq(id))
                        .execute()
                cache?.remove(id)
                return null
            }
        }
    }

    fun get(paymentGroup: RPKPaymentGroup): List<RPKPaymentGroupOwner> {
        val results = database.create
                .select(RPKIT_PAYMENT_GROUP_OWNER.ID)
                .from(RPKIT_PAYMENT_GROUP_OWNER)
                .where(RPKIT_PAYMENT_GROUP_OWNER.PAYMENT_GROUP_ID.eq(paymentGroup.id))
                .fetch()
        return results.map { result -> get(result.get(RPKIT_PAYMENT_GROUP_OWNER.ID)) }
                .filterNotNull()
    }

    override fun delete(entity: RPKPaymentGroupOwner) {
        database.create
                .deleteFrom(RPKIT_PAYMENT_GROUP_OWNER)
                .where(RPKIT_PAYMENT_GROUP_OWNER.ID.eq(entity.id))
                .execute()
        cache?.remove(entity.id)
    }

}