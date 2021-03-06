package com.rpkit.locks.bukkit.command

import com.rpkit.locks.bukkit.RPKLocksBukkit
import com.rpkit.locks.bukkit.lock.RPKLockProvider
import com.rpkit.players.bukkit.profile.RPKMinecraftProfileProvider
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player


class GetKeyCommand(private val plugin: RPKLocksBukkit): CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender.hasPermission("rpkit.locks.command.getkey")) {
            if (sender is Player) {
                val minecraftProfileProvider = plugin.core.serviceManager.getServiceProvider(RPKMinecraftProfileProvider::class)
                val lockProvider = plugin.core.serviceManager.getServiceProvider(RPKLockProvider::class)
                val minecraftProfile = minecraftProfileProvider.getMinecraftProfile(sender)
                if (minecraftProfile != null) {
                    lockProvider.setGettingKey(minecraftProfile, true)
                    sender.sendMessage(plugin.messages["get-key-valid"])
                } else {
                    sender.sendMessage(plugin.messages["no-minecraft-profile"])
                }
            } else {
                sender.sendMessage(plugin.messages["not-from-console"])
            }
        } else {
            sender.sendMessage(plugin.messages["no-permission-get-key"])
        }
        return true
    }
}