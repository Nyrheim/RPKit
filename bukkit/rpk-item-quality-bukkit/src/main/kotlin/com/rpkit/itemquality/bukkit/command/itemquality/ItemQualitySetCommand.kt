/*
 * Copyright 2019 Ross Binden
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

package com.rpkit.itemquality.bukkit.command.itemquality

import com.rpkit.itemquality.bukkit.RPKItemQualityBukkit
import com.rpkit.itemquality.bukkit.itemquality.RPKItemQualityProvider
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player


class ItemQualitySetCommand(private val plugin: RPKItemQualityBukkit): CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage(plugin.messages["not-from-console"])
            return true
        }
        if (!sender.hasPermission("rpkit.itemquality.command.itemquality.set")) {
            sender.sendMessage(plugin.messages["no-permission-itemquality-set"])
            return true
        }
        if (args.isEmpty()) {
            sender.sendMessage(plugin.messages["itemquality-set-usage"])
            return true
        }
        val itemQualityProvider = plugin.core.serviceManager.getServiceProvider(RPKItemQualityProvider::class)
        val itemQuality = itemQualityProvider.getItemQuality(args.joinToString(" "))
        if (itemQuality == null) {
            sender.sendMessage(plugin.messages["itemquality-set-invalid-quality"])
            return true
        }
        val item = sender.inventory.itemInMainHand
        if (item.type == Material.AIR) {
            sender.sendMessage(plugin.messages["itemquality-set-invalid-item-none"])
            return true
        }
        itemQualityProvider.setItemQuality(item, itemQuality)
        sender.inventory.setItemInMainHand(item)
        sender.sendMessage(plugin.messages["itemquality-set-valid", mapOf(
                Pair("quality", itemQuality.name)
        )])
        return true
    }

}