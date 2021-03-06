/*
 * Copyright 2017 Ross Binden
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

package com.rpkit.payments.bukkit.command.payment

import com.rpkit.characters.bukkit.character.RPKCharacter
import com.rpkit.characters.bukkit.character.RPKCharacterProvider
import com.rpkit.payments.bukkit.RPKPaymentsBukkit
import com.rpkit.payments.bukkit.group.RPKPaymentGroupProvider
import com.rpkit.players.bukkit.profile.RPKMinecraftProfileProvider
import net.md_5.bungee.api.chat.BaseComponent
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.TextComponent
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
class PaymentInfoCommand(private val plugin: RPKPaymentsBukkit): CommandExecutor {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss zzz")
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender.hasPermission("rpkit.payments.command.payment.info")) {
            if (sender is Player) {
                if (args.isNotEmpty()) {
                    val minecraftProfileProvider = plugin.core.serviceManager.getServiceProvider(RPKMinecraftProfileProvider::class)
                    val characterProvider = plugin.core.serviceManager.getServiceProvider(RPKCharacterProvider::class)
                    val paymentGroupProvider = plugin.core.serviceManager.getServiceProvider(RPKPaymentGroupProvider::class)
                    val minecraftProfile = minecraftProfileProvider.getMinecraftProfile(sender)
                    if (minecraftProfile != null) {
                        val character = characterProvider.getActiveCharacter(minecraftProfile)
                        val paymentGroup = paymentGroupProvider.getPaymentGroup(args.joinToString(" "))
                        if (paymentGroup != null) {
                            if (paymentGroup.owners.contains(character)) {
                                for (line in plugin.messages.getList("payment-info-owner")) {
                                    val messageComponents = mutableListOf<BaseComponent>()
                                    var chatColor: ChatColor? = null
                                    var chatFormat: ChatColor? = null
                                    var i = 0
                                    while (i < line.length) {
                                        if (line[i] == ChatColor.COLOR_CHAR) {
                                            val colourOrFormat = ChatColor.getByChar(line[i + 1])
                                            if (colourOrFormat?.isColor == true) {
                                                chatColor = colourOrFormat
                                                chatFormat = null
                                            }
                                            if (colourOrFormat?.isFormat == true) chatFormat = colourOrFormat
                                            i += 1
                                        } else {
                                            var fieldFound = false
                                            if (line.length >= i + "\$name".length) {
                                                if (line.substring(i, i + "\$name".length) == "\$name") {
                                                    val textComponent = TextComponent(paymentGroup.name)
                                                    if (chatColor != null) {
                                                        textComponent.color = chatColor.asBungee()
                                                    }
                                                    if (chatFormat != null) {
                                                        textComponent.isObfuscated = chatFormat == ChatColor.MAGIC
                                                        textComponent.isBold = chatFormat == ChatColor.BOLD
                                                        textComponent.isStrikethrough = chatFormat == ChatColor.STRIKETHROUGH
                                                        textComponent.isUnderlined = chatFormat == ChatColor.UNDERLINE
                                                        textComponent.isItalic = chatFormat == ChatColor.ITALIC
                                                    }
                                                    messageComponents.add(textComponent)
                                                    i += "\$name".length - 1
                                                    fieldFound = true
                                                }
                                            }
                                            if (!fieldFound) {
                                                if (line.length >= i + "\$owners".length) {
                                                    if (line.substring(i, i + "\$owners".length) == "\$owners") {
                                                        val hiddenOwners = paymentGroup.owners.filter { it.isNameHidden }.size
                                                        val textComponent = TextComponent(
                                                                paymentGroup.owners
                                                                        .filter { owner -> !owner.isNameHidden }
                                                                        .map(RPKCharacter::name)
                                                                        .joinToString(", ")
                                                                + if (hiddenOwners > 0) " (plus $hiddenOwners hidden)" else ""
                                                        )
                                                        if (chatColor != null) {
                                                            textComponent.color = chatColor.asBungee()
                                                        }
                                                        if (chatFormat != null) {
                                                            textComponent.isObfuscated = chatFormat == ChatColor.MAGIC
                                                            textComponent.isBold = chatFormat == ChatColor.BOLD
                                                            textComponent.isStrikethrough = chatFormat == ChatColor.STRIKETHROUGH
                                                            textComponent.isUnderlined = chatFormat == ChatColor.UNDERLINE
                                                            textComponent.isItalic = chatFormat == ChatColor.ITALIC
                                                        }
                                                        messageComponents.add(textComponent)
                                                        i += "\$owners".length - 1
                                                        fieldFound = true
                                                    }
                                                }
                                            }
                                            if (!fieldFound) {
                                                if (line.length >= i + "\$members".length) {
                                                    if (line.substring(i, i + "\$members".length) == "\$members") {
                                                        val hiddenMembers = paymentGroup.members.filter { it.isNameHidden }.size
                                                        val textComponent = TextComponent(
                                                                paymentGroup.members
                                                                        .filter { member -> !member.isNameHidden }
                                                                        .map(RPKCharacter::name)
                                                                        .joinToString(", ")
                                                                + if (hiddenMembers > 0) " (plus $hiddenMembers hidden)" else ""
                                                        )
                                                        if (chatColor != null) {
                                                            textComponent.color = chatColor.asBungee()
                                                        }
                                                        if (chatFormat != null) {
                                                            textComponent.isObfuscated = chatFormat == ChatColor.MAGIC
                                                            textComponent.isBold = chatFormat == ChatColor.BOLD
                                                            textComponent.isStrikethrough = chatFormat == ChatColor.STRIKETHROUGH
                                                            textComponent.isUnderlined = chatFormat == ChatColor.UNDERLINE
                                                            textComponent.isItalic = chatFormat == ChatColor.ITALIC
                                                        }
                                                        messageComponents.add(textComponent)
                                                        i += "\$members".length - 1
                                                        fieldFound = true
                                                    }
                                                }
                                            }
                                            if (!fieldFound) {
                                                if (line.length >= i + "\$invites".length) {
                                                    if (line.substring(i, i + "\$invites".length) == "\$invites") {
                                                        val textComponent = TextComponent(
                                                                paymentGroup.invites
                                                                        .filter { invite -> !invite.isNameHidden }
                                                                        .map(RPKCharacter::name)
                                                                        .joinToString(", ")
                                                        )
                                                        if (chatColor != null) {
                                                            textComponent.color = chatColor.asBungee()
                                                        }
                                                        if (chatFormat != null) {
                                                            textComponent.isObfuscated = chatFormat == ChatColor.MAGIC
                                                            textComponent.isBold = chatFormat == ChatColor.BOLD
                                                            textComponent.isStrikethrough = chatFormat == ChatColor.STRIKETHROUGH
                                                            textComponent.isUnderlined = chatFormat == ChatColor.UNDERLINE
                                                            textComponent.isItalic = chatFormat == ChatColor.ITALIC
                                                        }
                                                        messageComponents.add(textComponent)
                                                        i += "\$invites".length - 1
                                                        fieldFound = true
                                                    }
                                                }
                                            }
                                            if (!fieldFound) {
                                                if (line.length >= i + "\$amount".length) {
                                                    if (line.substring(i, i + "\$amount".length) == "\$amount") {
                                                        if (paymentGroup.currency != null) {
                                                            messageComponents.add(
                                                                    TextComponent(
                                                                            "${paymentGroup.amount} ${if (paymentGroup.balance == 1)
                                                                                paymentGroup.currency?.nameSingular ?: ""
                                                                            else
                                                                                paymentGroup.currency?.namePlural ?: ""}"
                                                                    )
                                                            )
                                                        } else {
                                                            messageComponents.add(TextComponent("(Currency unset)"))
                                                        }
                                                        if (chatColor != null) {
                                                            messageComponents.last().color = chatColor.asBungee()
                                                        }
                                                        if (chatFormat != null) {
                                                            messageComponents.last().isObfuscated = chatFormat == ChatColor.MAGIC
                                                            messageComponents.last().isBold = chatFormat == ChatColor.BOLD
                                                            messageComponents.last().isStrikethrough = chatFormat == ChatColor.STRIKETHROUGH
                                                            messageComponents.last().isUnderlined = chatFormat == ChatColor.UNDERLINE
                                                            messageComponents.last().isItalic = chatFormat == ChatColor.ITALIC
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
                                                            messageComponents.add(TextComponent(currency.name))
                                                        } else {
                                                            messageComponents.add(TextComponent("unset"))
                                                        }
                                                        if (chatColor != null) {
                                                            messageComponents.last().color = chatColor.asBungee()
                                                        }
                                                        if (chatFormat != null) {
                                                            messageComponents.last().isObfuscated = chatFormat == ChatColor.MAGIC
                                                            messageComponents.last().isBold = chatFormat == ChatColor.BOLD
                                                            messageComponents.last().isStrikethrough = chatFormat == ChatColor.STRIKETHROUGH
                                                            messageComponents.last().isUnderlined = chatFormat == ChatColor.UNDERLINE
                                                            messageComponents.last().isItalic = chatFormat == ChatColor.ITALIC
                                                        }
                                                        i += "\$currency".length - 1
                                                        fieldFound = true
                                                    }
                                                }
                                            }
                                            if (!fieldFound) {
                                                if (line.length >= i + "\$interval".length) {
                                                    if (line.substring(i, i + "\$interval".length) == "\$interval") {
                                                        val textComponent = TextComponent("${paymentGroup.interval / 1000} seconds")
                                                        if (chatColor != null) {
                                                            textComponent.color = chatColor.asBungee()
                                                        }
                                                        if (chatFormat != null) {
                                                            textComponent.isObfuscated = chatFormat == ChatColor.MAGIC
                                                            textComponent.isBold = chatFormat == ChatColor.BOLD
                                                            textComponent.isStrikethrough = chatFormat == ChatColor.STRIKETHROUGH
                                                            textComponent.isUnderlined = chatFormat == ChatColor.UNDERLINE
                                                            textComponent.isItalic = chatFormat == ChatColor.ITALIC
                                                        }
                                                        messageComponents.add(textComponent)
                                                        i += "\$interval".length - 1
                                                        fieldFound = true
                                                    }
                                                }
                                            }
                                            if (!fieldFound) {
                                                if (line.length >= i + "\$last-payment-time".length) {
                                                    if (line.substring(i, i + "\$last-payment-time".length) == "\$last-payment-time") {
                                                        val textComponent = TextComponent(dateFormat.format(Date(paymentGroup.lastPaymentTime)))
                                                        if (chatColor != null) {
                                                            textComponent.color = chatColor.asBungee()
                                                        }
                                                        if (chatFormat != null) {
                                                            textComponent.isObfuscated = chatFormat == ChatColor.MAGIC
                                                            textComponent.isBold = chatFormat == ChatColor.BOLD
                                                            textComponent.isStrikethrough = chatFormat == ChatColor.STRIKETHROUGH
                                                            textComponent.isUnderlined = chatFormat == ChatColor.UNDERLINE
                                                            textComponent.isItalic = chatFormat == ChatColor.ITALIC
                                                        }
                                                        messageComponents.add(textComponent)
                                                        i += "\$last-payment-time".length - 1
                                                        fieldFound = true
                                                    }
                                                }
                                            }
                                            if (!fieldFound) {
                                                if (line.length >= i + "\$balance".length) {
                                                    if (line.substring(i, i + "\$balance".length) == "\$balance") {
                                                        val textComponent = TextComponent(
                                                                if (paymentGroup.currency != null) {
                                                                    "${paymentGroup.balance} ${if (paymentGroup.balance == 1) paymentGroup.currency?.nameSingular ?: "" else paymentGroup.currency?.namePlural ?: ""}"
                                                                } else {
                                                                    "unset"
                                                                }
                                                        )
                                                        if (chatColor != null) {
                                                            textComponent.color = chatColor.asBungee()
                                                        }
                                                        if (chatFormat != null) {
                                                            textComponent.isObfuscated = chatFormat == ChatColor.MAGIC
                                                            textComponent.isBold = chatFormat == ChatColor.BOLD
                                                            textComponent.isStrikethrough = chatFormat == ChatColor.STRIKETHROUGH
                                                            textComponent.isUnderlined = chatFormat == ChatColor.UNDERLINE
                                                            textComponent.isItalic = chatFormat == ChatColor.ITALIC
                                                        }
                                                        messageComponents.add(textComponent)
                                                        i += "\$balance".length - 1
                                                        fieldFound = true
                                                    }
                                                }
                                            }
                                            if (!fieldFound) {
                                                var editFound = false
                                                if (line.length >= i + "\$edit(name)".length) {
                                                    if (line.substring(i, i + "\$edit(name)".length) == "\$edit(name)") {
                                                        val textComponent = TextComponent("Edit")
                                                        textComponent.clickEvent = ClickEvent(ClickEvent.Action.RUN_COMMAND, "/payment set name ${paymentGroup.name}")
                                                        textComponent.hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, arrayOf(TextComponent("Click here to change the payment group name")))
                                                        if (chatColor != null) {
                                                            textComponent.color = chatColor.asBungee()
                                                        }
                                                        if (chatFormat != null) {
                                                            textComponent.isObfuscated = chatFormat == ChatColor.MAGIC
                                                            textComponent.isBold = chatFormat == ChatColor.BOLD
                                                            textComponent.isStrikethrough = chatFormat == ChatColor.STRIKETHROUGH
                                                            textComponent.isUnderlined = chatFormat == ChatColor.UNDERLINE
                                                            textComponent.isItalic = chatFormat == ChatColor.ITALIC
                                                        }
                                                        messageComponents.add(textComponent)
                                                        i += "\$edit(name)".length - 1
                                                        editFound = true
                                                    }
                                                }
                                                if (!editFound) {
                                                    if (line.length >= i + "\$edit(amount)".length) {
                                                        if (line.substring(i, i + "\$edit(amount)".length) == "\$edit(amount)") {
                                                            val textComponent = TextComponent("Edit")
                                                            textComponent.clickEvent = ClickEvent(ClickEvent.Action.RUN_COMMAND, "/payment set amount ${paymentGroup.name}")
                                                            textComponent.hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, arrayOf(TextComponent("Click here to change the payment group amount")))
                                                            if (chatColor != null) {
                                                                textComponent.color = chatColor.asBungee()
                                                            }
                                                            if (chatFormat != null) {
                                                                textComponent.isObfuscated = chatFormat == ChatColor.MAGIC
                                                                textComponent.isBold = chatFormat == ChatColor.BOLD
                                                                textComponent.isStrikethrough = chatFormat == ChatColor.STRIKETHROUGH
                                                                textComponent.isUnderlined = chatFormat == ChatColor.UNDERLINE
                                                                textComponent.isItalic = chatFormat == ChatColor.ITALIC
                                                            }
                                                            messageComponents.add(textComponent)
                                                            i += "\$edit(amount)".length - 1
                                                            editFound = true
                                                        }
                                                    }
                                                }
                                                if (!editFound) {
                                                    if (line.length >= i + "\$edit(currency)".length) {
                                                        if (line.substring(i, i + "\$edit(currency)".length) == "\$edit(currency)") {
                                                            val textComponent = TextComponent("Edit")
                                                            textComponent.clickEvent = ClickEvent(ClickEvent.Action.RUN_COMMAND, "/payment set currency ${paymentGroup.name}")
                                                            textComponent.hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, arrayOf(TextComponent("Click here to change the payment group currency")))
                                                            if (chatColor != null) {
                                                                textComponent.color = chatColor.asBungee()
                                                            }
                                                            if (chatFormat != null) {
                                                                textComponent.isObfuscated = chatFormat == ChatColor.MAGIC
                                                                textComponent.isBold = chatFormat == ChatColor.BOLD
                                                                textComponent.isStrikethrough = chatFormat == ChatColor.STRIKETHROUGH
                                                                textComponent.isUnderlined = chatFormat == ChatColor.UNDERLINE
                                                                textComponent.isItalic = chatFormat == ChatColor.ITALIC
                                                            }
                                                            messageComponents.add(textComponent)
                                                            i += "\$edit(currency)".length - 1
                                                            editFound = true
                                                        }
                                                    }
                                                }
                                                if (!editFound) {
                                                    if (line.length >= i + "\$edit(interval)".length) {
                                                        if (line.substring(i, i + "\$edit(interval)".length) == "\$edit(interval)") {
                                                            val textComponent = TextComponent("Edit")
                                                            textComponent.clickEvent = ClickEvent(ClickEvent.Action.RUN_COMMAND, "/payment set interval ${paymentGroup.name}")
                                                            textComponent.hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, arrayOf(TextComponent("Click here to change the payment group interval")))
                                                            if (chatColor != null) {
                                                                textComponent.color = chatColor.asBungee()
                                                            }
                                                            if (chatFormat != null) {
                                                                textComponent.isObfuscated = chatFormat == ChatColor.MAGIC
                                                                textComponent.isBold = chatFormat == ChatColor.BOLD
                                                                textComponent.isStrikethrough = chatFormat == ChatColor.STRIKETHROUGH
                                                                textComponent.isUnderlined = chatFormat == ChatColor.UNDERLINE
                                                                textComponent.isItalic = chatFormat == ChatColor.ITALIC
                                                            }
                                                            messageComponents.add(textComponent)
                                                            i += "\$edit(interval)".length - 1
                                                            editFound = true
                                                        }
                                                    }
                                                }
                                                if (!editFound) {
                                                    val textComponent = TextComponent(Character.toString(line[i]))
                                                    if (chatColor != null) {
                                                        textComponent.color = chatColor.asBungee()
                                                    }
                                                    if (chatFormat != null) {
                                                        textComponent.isObfuscated = chatFormat == ChatColor.MAGIC
                                                        textComponent.isBold = chatFormat == ChatColor.BOLD
                                                        textComponent.isStrikethrough = chatFormat == ChatColor.STRIKETHROUGH
                                                        textComponent.isUnderlined = chatFormat == ChatColor.UNDERLINE
                                                        textComponent.isItalic = chatFormat == ChatColor.ITALIC
                                                    }
                                                    messageComponents.add(textComponent)
                                                }
                                            }
                                        }
                                        i++
                                    }
                                    sender.spigot().sendMessage(*messageComponents.toTypedArray())
                                }
                            } else {
                                for (line in plugin.messages.getList("payment-info-not-owner")) {
                                    val messageComponents = mutableListOf<BaseComponent>()
                                    var chatColor: ChatColor? = null
                                    var chatFormat: ChatColor? = null
                                    var i = 0
                                    while (i < line.length) {
                                        if (line[i] == ChatColor.COLOR_CHAR) {
                                            val colourOrFormat = ChatColor.getByChar(line[i + 1])
                                            if (colourOrFormat?.isColor == true) {
                                                chatColor = colourOrFormat
                                                chatFormat = null
                                            }
                                            if (colourOrFormat?.isFormat == true) chatFormat = colourOrFormat
                                            i += 1
                                        } else {
                                            var fieldFound = false
                                            if (line.length >= i + "\$name".length) {
                                                if (line.substring(i, i + "\$name".length) == "\$name") {
                                                    val textComponent = TextComponent(paymentGroup.name)
                                                    if (chatColor != null) {
                                                        textComponent.color = chatColor.asBungee()
                                                    }
                                                    if (chatFormat != null) {
                                                        textComponent.isObfuscated = chatFormat == ChatColor.MAGIC
                                                        textComponent.isBold = chatFormat == ChatColor.BOLD
                                                        textComponent.isStrikethrough = chatFormat == ChatColor.STRIKETHROUGH
                                                        textComponent.isUnderlined = chatFormat == ChatColor.UNDERLINE
                                                        textComponent.isItalic = chatFormat == ChatColor.ITALIC
                                                    }
                                                    messageComponents.add(textComponent)
                                                    i += "\$name".length - 1
                                                    fieldFound = true
                                                }
                                            }
                                            if (!fieldFound) {
                                                if (line.length >= i + "\$owners".length) {
                                                    if (line.substring(i, i + "\$owners".length) == "\$owners") {
                                                        val hiddenOwners = paymentGroup.owners.filter { it.isNameHidden }.size
                                                        val textComponent = TextComponent(
                                                                paymentGroup.owners
                                                                        .filter { owner -> !owner.isNameHidden }
                                                                        .map(RPKCharacter::name)
                                                                        .joinToString(", ")
                                                                        + if (hiddenOwners > 0) " (plus $hiddenOwners hidden)" else ""
                                                        )
                                                        if (chatColor != null) {
                                                            textComponent.color = chatColor.asBungee()
                                                        }
                                                        if (chatFormat != null) {
                                                            textComponent.isObfuscated = chatFormat == ChatColor.MAGIC
                                                            textComponent.isBold = chatFormat == ChatColor.BOLD
                                                            textComponent.isStrikethrough = chatFormat == ChatColor.STRIKETHROUGH
                                                            textComponent.isUnderlined = chatFormat == ChatColor.UNDERLINE
                                                            textComponent.isItalic = chatFormat == ChatColor.ITALIC
                                                        }
                                                        messageComponents.add(textComponent)
                                                        i += "\$owners".length - 1
                                                        fieldFound = true
                                                    }
                                                }
                                            }
                                            if (!fieldFound) {
                                                if (line.length >= i + "\$members".length) {
                                                    if (line.substring(i, i + "\$members".length) == "\$members") {
                                                        val hiddenMembers = paymentGroup.members.filter { it.isNameHidden }.size
                                                        val textComponent = TextComponent(
                                                                paymentGroup.members
                                                                        .filter { member -> !member.isNameHidden }
                                                                        .map(RPKCharacter::name)
                                                                        .joinToString(", ")
                                                                        + if (hiddenMembers > 0) " (plus $hiddenMembers hidden)" else ""
                                                        )
                                                        if (chatColor != null) {
                                                            textComponent.color = chatColor.asBungee()
                                                        }
                                                        if (chatFormat != null) {
                                                            textComponent.isObfuscated = chatFormat == ChatColor.MAGIC
                                                            textComponent.isBold = chatFormat == ChatColor.BOLD
                                                            textComponent.isStrikethrough = chatFormat == ChatColor.STRIKETHROUGH
                                                            textComponent.isUnderlined = chatFormat == ChatColor.UNDERLINE
                                                            textComponent.isItalic = chatFormat == ChatColor.ITALIC
                                                        }
                                                        messageComponents.add(textComponent)
                                                        i += "\$members".length - 1
                                                        fieldFound = true
                                                    }
                                                }
                                            }
                                            if (!fieldFound) {
                                                if (line.length >= i + "\$invites".length) {
                                                    if (line.substring(i, i + "\$invites".length) == "\$invites") {
                                                        val textComponent = TextComponent(paymentGroup.invites.joinToString(", "))
                                                        if (chatColor != null) {
                                                            textComponent.color = chatColor.asBungee()
                                                        }
                                                        if (chatFormat != null) {
                                                            textComponent.isObfuscated = chatFormat == ChatColor.MAGIC
                                                            textComponent.isBold = chatFormat == ChatColor.BOLD
                                                            textComponent.isStrikethrough = chatFormat == ChatColor.STRIKETHROUGH
                                                            textComponent.isUnderlined = chatFormat == ChatColor.UNDERLINE
                                                            textComponent.isItalic = chatFormat == ChatColor.ITALIC
                                                        }
                                                        messageComponents.add(textComponent)
                                                        i += "\$invites".length - 1
                                                        fieldFound = true
                                                    }
                                                }
                                            }
                                            if (!fieldFound) {
                                                if (line.length >= i + "\$amount".length) {
                                                    if (line.substring(i, i + "\$amount".length) == "\$amount") {
                                                        if (paymentGroup.currency != null) {
                                                            messageComponents.add(TextComponent("${paymentGroup.amount} ${if (paymentGroup.balance == 1) paymentGroup.currency?.nameSingular ?: "" else paymentGroup.currency?.namePlural ?: ""}"))
                                                        } else {
                                                            messageComponents.add(TextComponent("(Currency unset)"))
                                                        }
                                                        if (chatColor != null) {
                                                            messageComponents.last().color = chatColor.asBungee()
                                                        }
                                                        if (chatFormat != null) {
                                                            messageComponents.last().isObfuscated = chatFormat == ChatColor.MAGIC
                                                            messageComponents.last().isBold = chatFormat == ChatColor.BOLD
                                                            messageComponents.last().isStrikethrough = chatFormat == ChatColor.STRIKETHROUGH
                                                            messageComponents.last().isUnderlined = chatFormat == ChatColor.UNDERLINE
                                                            messageComponents.last().isItalic = chatFormat == ChatColor.ITALIC
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
                                                            messageComponents.add(TextComponent(currency.name))
                                                        } else {
                                                            messageComponents.add(TextComponent("unset"))
                                                        }
                                                        if (chatColor != null) {
                                                            messageComponents.last().color = chatColor.asBungee()
                                                        }
                                                        if (chatFormat != null) {
                                                            messageComponents.last().isObfuscated = chatFormat == ChatColor.MAGIC
                                                            messageComponents.last().isBold = chatFormat == ChatColor.BOLD
                                                            messageComponents.last().isStrikethrough = chatFormat == ChatColor.STRIKETHROUGH
                                                            messageComponents.last().isUnderlined = chatFormat == ChatColor.UNDERLINE
                                                            messageComponents.last().isItalic = chatFormat == ChatColor.ITALIC
                                                        }
                                                        i += "\$currency".length - 1
                                                        fieldFound = true
                                                    }
                                                }
                                            }
                                            if (!fieldFound) {
                                                if (line.length >= i + "\$interval".length) {
                                                    if (line.substring(i, i + "\$interval".length) == "\$interval") {
                                                        val textComponent = TextComponent("${paymentGroup.interval / 1000} seconds")
                                                        if (chatColor != null) {
                                                            textComponent.color = chatColor.asBungee()
                                                        }
                                                        if (chatFormat != null) {
                                                            textComponent.isObfuscated = chatFormat == ChatColor.MAGIC
                                                            textComponent.isBold = chatFormat == ChatColor.BOLD
                                                            textComponent.isStrikethrough = chatFormat == ChatColor.STRIKETHROUGH
                                                            textComponent.isUnderlined = chatFormat == ChatColor.UNDERLINE
                                                            textComponent.isItalic = chatFormat == ChatColor.ITALIC
                                                        }
                                                        messageComponents.add(textComponent)
                                                        i += "\$interval".length - 1
                                                        fieldFound = true
                                                    }
                                                }
                                            }
                                            if (!fieldFound) {
                                                if (line.length >= i + "\$last-payment-time".length) {
                                                    if (line.substring(i, i + "\$last-payment-time".length) == "\$last-payment-time") {
                                                        val textComponent = TextComponent(dateFormat.format(Date(paymentGroup.lastPaymentTime)))
                                                        if (chatColor != null) {
                                                            textComponent.color = chatColor.asBungee()
                                                        }
                                                        if (chatFormat != null) {
                                                            textComponent.isObfuscated = chatFormat == ChatColor.MAGIC
                                                            textComponent.isBold = chatFormat == ChatColor.BOLD
                                                            textComponent.isStrikethrough = chatFormat == ChatColor.STRIKETHROUGH
                                                            textComponent.isUnderlined = chatFormat == ChatColor.UNDERLINE
                                                            textComponent.isItalic = chatFormat == ChatColor.ITALIC
                                                        }
                                                        messageComponents.add(textComponent)
                                                        i += "\$last-payment-time".length - 1
                                                        fieldFound = true
                                                    }
                                                }
                                            }
                                            if (!fieldFound) {
                                                if (line.length >= i + "\$balance".length) {
                                                    if (line.substring(i, i + "\$balance".length) == "\$balance") {
                                                        val textComponent = TextComponent(
                                                                if (paymentGroup.currency != null) {
                                                                    "${paymentGroup.balance} ${if (paymentGroup.balance == 1) paymentGroup.currency?.nameSingular ?: "" else paymentGroup.currency?.namePlural ?: ""}"
                                                                } else {
                                                                    "unset"
                                                                }
                                                        )
                                                        if (chatColor != null) {
                                                            textComponent.color = chatColor.asBungee()
                                                        }
                                                        if (chatFormat != null) {
                                                            textComponent.isObfuscated = chatFormat == ChatColor.MAGIC
                                                            textComponent.isBold = chatFormat == ChatColor.BOLD
                                                            textComponent.isStrikethrough = chatFormat == ChatColor.STRIKETHROUGH
                                                            textComponent.isUnderlined = chatFormat == ChatColor.UNDERLINE
                                                            textComponent.isItalic = chatFormat == ChatColor.ITALIC
                                                        }
                                                        messageComponents.add(textComponent)
                                                        i += "\$balance".length - 1
                                                        fieldFound = true
                                                    }
                                                }
                                            }
                                            if (!fieldFound) {
                                                val textComponent = TextComponent(Character.toString(line[i]))
                                                if (chatColor != null) {
                                                    textComponent.color = chatColor.asBungee()
                                                }
                                                if (chatFormat != null) {
                                                    textComponent.isObfuscated = chatFormat == ChatColor.MAGIC
                                                    textComponent.isBold = chatFormat == ChatColor.BOLD
                                                    textComponent.isStrikethrough = chatFormat == ChatColor.STRIKETHROUGH
                                                    textComponent.isUnderlined = chatFormat == ChatColor.UNDERLINE
                                                    textComponent.isItalic = chatFormat == ChatColor.ITALIC
                                                }
                                                messageComponents.add(textComponent)
                                            }
                                        }
                                        i++
                                    }
                                    sender.spigot().sendMessage(*messageComponents.toTypedArray())
                                }
                            }
                        } else {
                            sender.sendMessage(plugin.messages["payment-info-invalid-group"])
                        }
                    } else {
                        sender.sendMessage(plugin.messages["no-minecraft-profile"])
                    }
                } else {
                    sender.sendMessage(plugin.messages["payment-info-usage"])
                }
            } else {
                sender.sendMessage(plugin.messages["not-from-console"])
            }
        } else {
            sender.sendMessage(plugin.messages["no-permission-payment-info"])
        }
        return true
    }
}
