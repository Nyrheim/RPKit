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

package com.rpkit.chat.bukkit.command.chatgroup

import com.rpkit.chat.bukkit.RPKChatBukkit
import com.rpkit.chat.bukkit.chatgroup.RPKChatGroupProvider
import com.rpkit.players.bukkit.profile.RPKMinecraftProfileProvider
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

/**
 * Chat group invite command.
 * Invites a player to a chat group.
 */
class ChatGroupInviteCommand(private val plugin: RPKChatBukkit): CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender.hasPermission("rpkit.chat.command.chatgroup.invite")) {
            if (args.size >= 2) {
                val minecraftProfileProvider = plugin.core.serviceManager.getServiceProvider(RPKMinecraftProfileProvider::class)
                val chatGroupProvider = plugin.core.serviceManager.getServiceProvider(RPKChatGroupProvider::class)
                val chatGroup = chatGroupProvider.getChatGroup(args[0])
                if (chatGroup != null) {
                    if (sender is Player) {
                        val senderMinecraftProfile = minecraftProfileProvider.getMinecraftProfile(sender)
                        if (senderMinecraftProfile != null) {
                            if (chatGroup.memberMinecraftProfiles.any { memberMinecraftProfile ->
                                                memberMinecraftProfile.id == senderMinecraftProfile.id }) {
                                val bukkitPlayer = plugin.server.getPlayer(args[1])
                                if (bukkitPlayer == null) {
                                    sender.sendMessage(plugin.messages["chat-group-invite-invalid-player"])
                                    return true
                                }
                                val minecraftProfile = minecraftProfileProvider.getMinecraftProfile(bukkitPlayer)
                                if (minecraftProfile != null) {
                                    chatGroup.invite(minecraftProfile)
                                    minecraftProfile.sendMessage(plugin.messages["chat-group-invite-received", mapOf(
                                            Pair("group", chatGroup.name)
                                    )])
                                    sender.sendMessage(plugin.messages["chat-group-invite-valid", mapOf(
                                            Pair("player", minecraftProfile.minecraftUsername),
                                            Pair("group", chatGroup.name)
                                    )])
                                } else {
                                    sender.sendMessage(plugin.messages["no-minecraft-profile"])
                                }
                            } else {
                                sender.sendMessage(plugin.messages["chat-group-invite-invalid-not-a-member"])
                            }
                        } else {
                            sender.sendMessage(plugin.messages["no-minecraft-profile"])
                        }
                    } else {
                        sender.sendMessage(plugin.messages["not-from-console"])
                    }
                } else {
                    sender.sendMessage(plugin.messages["chat-group-invite-invalid-chat-group"])
                }
            } else {
                sender.sendMessage(plugin.messages["chat-group-invite-usage"])
            }
        } else {
            sender.sendMessage(plugin.messages["no-permission-chat-group-invite"])
        }
        return true
    }

}