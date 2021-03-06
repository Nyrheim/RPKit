/*
 * Copyright 2018 Ross Binden
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

package com.rpkit.store.bukkit.purchase

import com.rpkit.players.bukkit.profile.RPKProfile
import com.rpkit.store.bukkit.RPKStoresBukkit
import com.rpkit.store.bukkit.database.table.RPKConsumablePurchaseTable
import com.rpkit.store.bukkit.database.table.RPKPermanentPurchaseTable
import com.rpkit.store.bukkit.database.table.RPKPurchaseTable
import com.rpkit.store.bukkit.database.table.RPKTimedPurchaseTable
import com.rpkit.store.bukkit.event.purchase.RPKBukkitPurchaseCreateEvent
import com.rpkit.store.bukkit.event.purchase.RPKBukkitPurchaseDeleteEvent
import com.rpkit.store.bukkit.event.purchase.RPKBukkitPurchaseUpdateEvent


class RPKPurchaseProviderImpl(private val plugin: RPKStoresBukkit): RPKPurchaseProvider {

    override fun getPurchases(profile: RPKProfile): List<RPKPurchase> {
        return plugin.core.database.getTable(RPKPurchaseTable::class).get(profile)
    }

    override fun getPurchase(id: Int): RPKPurchase? {
        return plugin.core.database.getTable(RPKPurchaseTable::class)[id]
    }

    override fun addPurchase(purchase: RPKPurchase) {
        val event = RPKBukkitPurchaseCreateEvent(purchase)
        plugin.server.pluginManager.callEvent(event)
        if (event.isCancelled) return
        val eventPurchase = event.purchase
        when (eventPurchase) {
            is RPKConsumablePurchase -> plugin.core.database.getTable(RPKConsumablePurchaseTable::class).insert(eventPurchase)
            is RPKPermanentPurchase -> plugin.core.database.getTable(RPKPermanentPurchaseTable::class).insert(eventPurchase)
            is RPKTimedPurchase -> plugin.core.database.getTable(RPKTimedPurchaseTable::class).insert(eventPurchase)
        }
    }

    override fun updatePurchase(purchase: RPKPurchase) {
        val event = RPKBukkitPurchaseUpdateEvent(purchase)
        plugin.server.pluginManager.callEvent(event)
        if (event.isCancelled) return
        val eventPurchase = event.purchase
        when (eventPurchase) {
            is RPKConsumablePurchase -> plugin.core.database.getTable(RPKConsumablePurchaseTable::class).update(eventPurchase)
            is RPKPermanentPurchase -> plugin.core.database.getTable(RPKPermanentPurchaseTable::class).update(eventPurchase)
            is RPKTimedPurchase -> plugin.core.database.getTable(RPKTimedPurchaseTable::class).update(eventPurchase)
        }
    }

    override fun removePurchase(purchase: RPKPurchase) {
        val event = RPKBukkitPurchaseDeleteEvent(purchase)
        plugin.server.pluginManager.callEvent(event)
        if (event.isCancelled) return
        val eventPurchase = event.purchase
        when (eventPurchase) {
            is RPKConsumablePurchase -> plugin.core.database.getTable(RPKConsumablePurchaseTable::class).delete(eventPurchase)
            is RPKPermanentPurchase -> plugin.core.database.getTable(RPKPermanentPurchaseTable::class).delete(eventPurchase)
            is RPKTimedPurchase -> plugin.core.database.getTable(RPKTimedPurchaseTable::class).delete(eventPurchase)
        }
    }

}