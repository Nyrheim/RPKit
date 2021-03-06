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

package com.rpkit.players.bukkit.command.account

import com.rpkit.players.bukkit.RPKPlayersBukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

/**
 * Account command.
 * Parent command for all account management commands.
 */
class AccountCommand(private val plugin: RPKPlayersBukkit): CommandExecutor {

    private val accountLinkCommand = AccountLinkCommand(plugin)
    private val accountConfirmLinkCommand = AccountConfirmLinkCommand(plugin)
    private val accountDenyLinkCommand = AccountDenyLinkCommand(plugin)

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.isNotEmpty()) {
            val newArgs = args.drop(1).toTypedArray()
            return when(args[0].toLowerCase()) {
                "link" -> accountLinkCommand.onCommand(sender, command, label, newArgs)
                "confirmlink" -> accountConfirmLinkCommand.onCommand(sender, command, label, newArgs)
                "denylink" -> accountDenyLinkCommand.onCommand(sender, command, label, newArgs)
                else -> {
                    sender.sendMessage(plugin.messages["account-usage"])
                    true
                }
            }
        } else {
            sender.sendMessage(plugin.messages["account-usage"])
        }
        return true
    }


}