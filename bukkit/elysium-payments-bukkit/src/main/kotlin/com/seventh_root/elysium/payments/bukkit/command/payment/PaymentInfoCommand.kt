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

package com.seventh_root.elysium.payments.bukkit.command.payment

import com.seventh_root.elysium.characters.bukkit.character.ElysiumCharacter
import com.seventh_root.elysium.characters.bukkit.character.ElysiumCharacterProvider
import com.seventh_root.elysium.payments.bukkit.ElysiumPaymentsBukkit
import com.seventh_root.elysium.payments.bukkit.group.ElysiumPaymentGroupProvider
import com.seventh_root.elysium.players.bukkit.player.ElysiumPlayerProvider
import mkremins.fanciful.FancyMessage
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.text.SimpleDateFormat
import java.util.*

/**
 * Payment info command.
 * Displays information on a payment group.
 */
class PaymentInfoCommand(private val plugin: ElysiumPaymentsBukkit): CommandExecutor {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss zzz")
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender.hasPermission("elysium.payments.command.payment.info")) {
            if (sender is Player) {
                if (args.isNotEmpty()) {
                    val playerProvider = plugin.core.serviceManager.getServiceProvider(ElysiumPlayerProvider::class)
                    val characterProvider = plugin.core.serviceManager.getServiceProvider(ElysiumCharacterProvider::class)
                    val paymentGroupProvider = plugin.core.serviceManager.getServiceProvider(ElysiumPaymentGroupProvider::class)
                    val player = playerProvider.getPlayer(sender)
                    val character = characterProvider.getActiveCharacter(player)
                    val paymentGroup = paymentGroupProvider.getPaymentGroup(args[0])
                    if (paymentGroup != null) {
                        if (paymentGroup.owners.contains(character)) {
                            for (line in plugin.config.getStringList("messages.payment-info")) {
                                val message = FancyMessage("")
                                var chatColor: ChatColor? = null
                                var chatFormat: ChatColor? = null
                                var i = 0
                                while (i < line.length) {
                                    if (line[i] === '&') {
                                        val colourOrFormat = ChatColor.getByChar(line[i + 1])
                                        if (colourOrFormat.isColor) {
                                            chatColor = colourOrFormat
                                            chatFormat = null
                                        }
                                        if (colourOrFormat.isFormat) chatFormat = colourOrFormat
                                        i += 1
                                    } else {
                                        var fieldFound = false
                                        if (line.length >= i + "\$name".length) {
                                            if (line.substring(i, i + "\$name".length) == "\$name") {
                                                message.then(paymentGroup.name)
                                                if (chatColor != null) {
                                                    message.color(chatColor)
                                                }
                                                if (chatFormat != null) {
                                                    message.style(chatFormat)
                                                }
                                                i += "\$name".length - 1
                                                fieldFound = true
                                            }
                                        }
                                        if (!fieldFound) {
                                            if (line.length >= i + "\$owners".length) {
                                                if (line.substring(i, i + "\$owners".length) == "\$owners") {
                                                    message.then(paymentGroup.owners
                                                            .filter { owner -> !owner.isNameHidden }
                                                            .map(ElysiumCharacter::name)
                                                            .joinToString(", "))
                                                    if (chatColor != null) {
                                                        message.color(chatColor)
                                                    }
                                                    if (chatFormat != null) {
                                                        message.style(chatFormat)
                                                    }
                                                    i += "\$owners".length - 1
                                                    fieldFound = true
                                                }
                                            }
                                        }
                                        if (!fieldFound) {
                                            if (line.length >= i + "\$members".length) {
                                                if (line.substring(i, i + "\$members".length) == "\$members") {
                                                    message.then(paymentGroup.members
                                                            .filter { member -> !member.isNameHidden }
                                                            .map(ElysiumCharacter::name)
                                                            .joinToString(", "))
                                                    if (chatColor != null) {
                                                        message.color(chatColor)
                                                    }
                                                    if (chatFormat != null) {
                                                        message.style(chatFormat)
                                                    }
                                                    i += "\$members".length - 1
                                                    fieldFound = true
                                                }
                                            }
                                        }
                                        if (!fieldFound) {
                                            if (line.length >= i + "\$invites".length) {
                                                if (line.substring(i, i + "\$invites".length) == "\$invites") {
                                                    message.then(paymentGroup.invites
                                                            .filter { invite -> !invite.isNameHidden }
                                                            .map(ElysiumCharacter::name)
                                                            .joinToString(", "))
                                                    if (chatColor != null) {
                                                        message.color(chatColor)
                                                    }
                                                    if (chatFormat != null) {
                                                        message.style(chatFormat)
                                                    }
                                                    i += "\$invites".length - 1
                                                    fieldFound = true
                                                }
                                            }
                                        }
                                        if (!fieldFound) {
                                            if (line.length >= i + "\$amount".length) {
                                                if (line.substring(i, i + "\$amount".length) == "\$amount") {
                                                    if (paymentGroup.currency != null) {
                                                        message.then("${paymentGroup.amount} ${if (paymentGroup.balance == 1) paymentGroup.currency?.nameSingular ?: "" else paymentGroup.currency?.namePlural ?: ""}")
                                                    } else {
                                                        message.then("(Currency unset)")
                                                    }
                                                    if (chatColor != null) {
                                                        message.color(chatColor)
                                                    }
                                                    if (chatFormat != null) {
                                                        message.style(chatFormat)
                                                    }
                                                    i += "\$amount".length - 1
                                                    fieldFound = true
                                                }
                                            }
                                        }
                                        if (!fieldFound) {
                                            if (line.length >= i + "\$currency".length) {
                                                if (line.substring(i, i + "\$currency".length) == "\$currency") {
                                                    val currency = paymentGroup.currency
                                                    if (currency != null) {
                                                        message.then(currency.name)
                                                    } else {
                                                        message.then("unset")
                                                    }
                                                    if (chatColor != null) {
                                                        message.color(chatColor)
                                                    }
                                                    if (chatFormat != null) {
                                                        message.style(chatFormat)
                                                    }
                                                    i += "\$currency".length - 1
                                                    fieldFound = true
                                                }
                                            }
                                        }
                                        if (!fieldFound) {
                                            if (line.length >= i + "\$interval".length) {
                                                if (line.substring(i, i + "\$interval".length) == "\$interval") {
                                                    message.then("${paymentGroup.interval / 1000} seconds")
                                                    if (chatColor != null) {
                                                        message.color(chatColor)
                                                    }
                                                    if (chatFormat != null) {
                                                        message.style(chatFormat)
                                                    }
                                                    i += "\$interval".length - 1
                                                    fieldFound = true
                                                }
                                            }
                                        }
                                        if (!fieldFound) {
                                            if (line.length >= i + "\$last-payment-time".length) {
                                                if (line.substring(i, i + "\$last-payment-time".length) == "\$last-payment-time") {
                                                    message.then(dateFormat.format(Date(paymentGroup.lastPaymentTime)))
                                                    if (chatColor != null) {
                                                        message.color(chatColor)
                                                    }
                                                    if (chatFormat != null) {
                                                        message.style(chatFormat)
                                                    }
                                                    i += "\$last-payment-time".length - 1
                                                    fieldFound = true
                                                }
                                            }
                                        }
                                        if (!fieldFound) {
                                            if (line.length >= i + "\$balance".length) {
                                                if (line.substring(i, i + "\$balance".length) == "\$balance") {
                                                    message.then(
                                                            if (paymentGroup.currency != null) {
                                                                "${paymentGroup.balance} ${if (paymentGroup.balance == 1) paymentGroup.currency?.nameSingular ?: "" else paymentGroup.currency?.namePlural ?: ""}"
                                                            } else {
                                                                "unset"
                                                            }
                                                    )
                                                    if (chatColor != null) {
                                                        message.color(chatColor)
                                                    }
                                                    if (chatFormat != null) {
                                                        message.style(chatFormat)
                                                    }
                                                    i += "\$balance".length - 1
                                                    fieldFound = true
                                                }
                                            }
                                        }
                                        if (!fieldFound) {
                                            var editFound = false
                                            if (line.length >= i + "\$edit(name)".length) {
                                                if (line.substring(i, i + "\$edit(name)".length) == "\$edit(name)") {
                                                    message.then("Edit")
                                                            .command("/payment set name ${paymentGroup.name}")
                                                            .tooltip("Click here to change the payment group name")
                                                    if (chatColor != null) {
                                                        message.color(chatColor)
                                                    }
                                                    if (chatFormat != null) {
                                                        message.style(chatFormat)
                                                    }
                                                    i += "\$edit(name)".length - 1
                                                    editFound = true
                                                }
                                            }
                                            if (!editFound) {
                                                if (line.length >= i + "\$edit(amount)".length) {
                                                    if (line.substring(i, i + "\$edit(amount)".length) == "\$edit(amount)") {
                                                        message.then("Edit")
                                                                .command("/payment set amount ${paymentGroup.name}")
                                                                .tooltip("Click here to change the payment group amount")
                                                        if (chatColor != null) {
                                                            message.color(chatColor)
                                                        }
                                                        if (chatFormat != null) {
                                                            message.style(chatFormat)
                                                        }
                                                        i += "\$edit(amount)".length - 1
                                                        editFound = true
                                                    }
                                                }
                                            }
                                            if (!editFound) {
                                                if (line.length >= i + "\$edit(currency)".length) {
                                                    if (line.substring(i, i + "\$edit(currency)".length) == "\$edit(currency)") {
                                                        message.then("Edit")
                                                                .command("/payment set currency ${paymentGroup.name}")
                                                                .tooltip("Click here to change the payment group currency")
                                                        if (chatColor != null) {
                                                            message.color(chatColor)
                                                        }
                                                        if (chatFormat != null) {
                                                            message.style(chatFormat)
                                                        }
                                                        i += "\$edit(currency)".length - 1
                                                        editFound = true
                                                    }
                                                }
                                            }
                                            if (!editFound) {
                                                if (line.length >= i + "\$edit(interval)".length) {
                                                    if (line.substring(i, i + "\$edit(interval)".length) == "\$edit(interval)") {
                                                        message.then("Edit")
                                                                .command("/payment set interval ${paymentGroup.name}")
                                                                .tooltip("Click here to change the payment group interval")
                                                        if (chatColor != null) {
                                                            message.color(chatColor)
                                                        }
                                                        if (chatFormat != null) {
                                                            message.style(chatFormat)
                                                        }
                                                        i += "\$edit(interval)".length - 1
                                                        editFound = true
                                                    }
                                                }
                                            }
                                            if (!editFound) {
                                                message.then(Character.toString(line[i]))
                                                if (chatColor != null) {
                                                    message.color(chatColor)
                                                }
                                                if (chatFormat != null) {
                                                    message.style(chatFormat)
                                                }
                                            }
                                        }
                                    }
                                    i++
                                }
                                message.send(sender)
                            }
                        } else {
                            for (line in plugin.config.getStringList("messages.payment-info")) {
                                val message = FancyMessage("")
                                var chatColor: ChatColor? = null
                                var chatFormat: ChatColor? = null
                                var i = 0
                                while (i < line.length) {
                                    if (line[i] === '&') {
                                        val colourOrFormat = ChatColor.getByChar(line[i + 1])
                                        if (colourOrFormat.isColor) {
                                            chatColor = colourOrFormat
                                            chatFormat = null
                                        }
                                        if (colourOrFormat.isFormat) chatFormat = colourOrFormat
                                        i += 1
                                    } else {
                                        var fieldFound = false
                                        if (line.length >= i + "\$name".length) {
                                            if (line.substring(i, i + "\$name".length) == "\$name") {
                                                message.then(paymentGroup.name)
                                                if (chatColor != null) {
                                                    message.color(chatColor)
                                                }
                                                if (chatFormat != null) {
                                                    message.style(chatFormat)
                                                }
                                                i += "\$name".length - 1
                                                fieldFound = true
                                            }
                                        }
                                        if (!fieldFound) {
                                            if (line.length >= i + "\$owners".length) {
                                                if (line.substring(i, i + "\$owners".length) == "\$owners") {
                                                    message.then(paymentGroup.owners.joinToString(", "))
                                                    if (chatColor != null) {
                                                        message.color(chatColor)
                                                    }
                                                    if (chatFormat != null) {
                                                        message.style(chatFormat)
                                                    }
                                                    i += "\$owners".length - 1
                                                    fieldFound = true
                                                }
                                            }
                                        }
                                        if (!fieldFound) {
                                            if (line.length >= i + "\$members".length) {
                                                if (line.substring(i, i + "\$members".length) == "\$members") {
                                                    message.then(paymentGroup.members.joinToString(", "))
                                                    if (chatColor != null) {
                                                        message.color(chatColor)
                                                    }
                                                    if (chatFormat != null) {
                                                        message.style(chatFormat)
                                                    }
                                                    i += "\$members".length - 1
                                                    fieldFound = true
                                                }
                                            }
                                        }
                                        if (!fieldFound) {
                                            if (line.length >= i + "\$invites".length) {
                                                if (line.substring(i, i + "\$invites".length) == "\$invites") {
                                                    message.then(paymentGroup.invites.joinToString(", "))
                                                    if (chatColor != null) {
                                                        message.color(chatColor)
                                                    }
                                                    if (chatFormat != null) {
                                                        message.style(chatFormat)
                                                    }
                                                    i += "\$invites".length - 1
                                                    fieldFound = true
                                                }
                                            }
                                        }
                                        if (!fieldFound) {
                                            if (line.length >= i + "\$amount".length) {
                                                if (line.substring(i, i + "\$amount".length) == "\$amount") {
                                                    if (paymentGroup.currency != null) {
                                                        message.then("${paymentGroup.amount} ${if (paymentGroup.balance == 1) paymentGroup.currency?.nameSingular ?: "" else paymentGroup.currency?.namePlural ?: ""}")
                                                    } else {
                                                        message.then("(Currency unset)")
                                                    }
                                                    if (chatColor != null) {
                                                        message.color(chatColor)
                                                    }
                                                    if (chatFormat != null) {
                                                        message.style(chatFormat)
                                                    }
                                                    i += "\$amount".length - 1
                                                    fieldFound = true
                                                }
                                            }
                                        }
                                        if (!fieldFound) {
                                            if (line.length >= i + "\$currency".length) {
                                                if (line.substring(i, i + "\$currency".length) == "\$currency") {
                                                    val currency = paymentGroup.currency
                                                    if (currency != null) {
                                                        message.then(currency.name)
                                                    } else {
                                                        message.then("unset")
                                                    }
                                                    if (chatColor != null) {
                                                        message.color(chatColor)
                                                    }
                                                    if (chatFormat != null) {
                                                        message.style(chatFormat)
                                                    }
                                                    i += "\$currency".length - 1
                                                    fieldFound = true
                                                }
                                            }
                                        }
                                        if (!fieldFound) {
                                            if (line.length >= i + "\$interval".length) {
                                                if (line.substring(i, i + "\$interval".length) == "\$interval") {
                                                    message.then("${paymentGroup.interval / 1000} seconds")
                                                    if (chatColor != null) {
                                                        message.color(chatColor)
                                                    }
                                                    if (chatFormat != null) {
                                                        message.style(chatFormat)
                                                    }
                                                    i += "\$interval".length - 1
                                                    fieldFound = true
                                                }
                                            }
                                        }
                                        if (!fieldFound) {
                                            if (line.length >= i + "\$last-payment-time".length) {
                                                if (line.substring(i, i + "\$last-payment-time".length) == "\$last-payment-time") {
                                                    message.then(dateFormat.format(Date(paymentGroup.lastPaymentTime)))
                                                    if (chatColor != null) {
                                                        message.color(chatColor)
                                                    }
                                                    if (chatFormat != null) {
                                                        message.style(chatFormat)
                                                    }
                                                    i += "\$last-payment-time".length - 1
                                                    fieldFound = true
                                                }
                                            }
                                        }
                                        if (!fieldFound) {
                                            if (line.length >= i + "\$balance".length) {
                                                if (line.substring(i, i + "\$balance".length) == "\$balance") {
                                                    message.then(
                                                            if (paymentGroup.currency != null) {
                                                                "${paymentGroup.balance} ${if (paymentGroup.balance == 1) paymentGroup.currency?.nameSingular ?: "" else paymentGroup.currency?.namePlural ?: ""}"
                                                            } else {
                                                                "unset"
                                                            }
                                                    )
                                                    if (chatColor != null) {
                                                        message.color(chatColor)
                                                    }
                                                    if (chatFormat != null) {
                                                        message.style(chatFormat)
                                                    }
                                                    i += "\$balance".length - 1
                                                    fieldFound = true
                                                }
                                            }
                                        }
                                        if (!fieldFound) {
                                            message.then(Character.toString(line[i]))
                                            if (chatColor != null) {
                                                message.color(chatColor)
                                            }
                                            if (chatFormat != null) {
                                                message.style(chatFormat)
                                            }
                                        }
                                    }
                                    i++
                                }
                                message.send(sender)
                            }
                        }
                    } else {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.config.getString("messages.payment-info-invalid-group")))
                    }
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.config.getString("messages.payment-info-usage")))
                }
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.config.getString("messages.not-from-console")))
            }
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.config.getString("no-permission-payment-info")))
        }
        return true
    }
}
