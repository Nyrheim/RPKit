/*
 * Copyright 2019 Ren Binden
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

package com.rpkit.skills.bukkit.command

import com.rpkit.characters.bukkit.character.RPKCharacterProvider
import com.rpkit.players.bukkit.profile.RPKMinecraftProfileProvider
import com.rpkit.skills.bukkit.RPKSkillsBukkit
import com.rpkit.skills.bukkit.skills.RPKSkillProvider
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player


class BindSkillCommand(private val plugin: RPKSkillsBukkit): CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (!sender.hasPermission("rpkit.skills.command.bindskill")) {
            sender.sendMessage(plugin.messages["no-permission-bind-skill"])
            return true
        }
        if (args.isEmpty()) {
            sender.sendMessage(plugin.messages["bind-skill-usage"])
            return true
        }
        val skillProvider = plugin.core.serviceManager.getServiceProvider(RPKSkillProvider::class)
        val skillName = args[0]
        val skill = skillProvider.getSkill(skillName)
        if (skill == null) {
            sender.sendMessage(plugin.messages["bind-skill-invalid-skill"])
            return true
        }
        if (sender !is Player) {
            sender.sendMessage(plugin.messages["not-from-console"])
            return true
        }
        val minecraftProfileProvider = plugin.core.serviceManager.getServiceProvider(RPKMinecraftProfileProvider::class)
        val minecraftProfile = minecraftProfileProvider.getMinecraftProfile(sender)
        if (minecraftProfile == null) {
            sender.sendMessage(plugin.messages["no-minecraft-profile"])
            return true
        }
        val characterProvider = plugin.core.serviceManager.getServiceProvider(RPKCharacterProvider::class)
        val character = characterProvider.getActiveCharacter(minecraftProfile)
        if (character == null) {
            sender.sendMessage(plugin.messages["no-character"])
            return true
        }
        val item = sender.inventory.itemInMainHand
        if (skillProvider.getSkillBinding(character, item) != null) {
            sender.sendMessage(plugin.messages["bind-skill-invalid-binding-already-exists"])
            return true
        }
        skillProvider.setSkillBinding(character, item, skill)
        sender.sendMessage(plugin.messages["bind-skill-valid", mapOf(
                "character" to character.name,
                "item" to item.type.toString().toLowerCase().replace('_', ' '),
                "skill" to skill.name
        )])
        return true
    }
}