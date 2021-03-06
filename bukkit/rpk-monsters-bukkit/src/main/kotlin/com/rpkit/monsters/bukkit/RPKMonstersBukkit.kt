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

package com.rpkit.monsters.bukkit

import com.rpkit.core.bukkit.plugin.RPKBukkitPlugin
import com.rpkit.core.database.Database
import com.rpkit.monsters.bukkit.command.monsterspawnarea.MonsterSpawnAreaCommand
import com.rpkit.monsters.bukkit.database.table.RPKMonsterSpawnAreaMonsterTable
import com.rpkit.monsters.bukkit.database.table.RPKMonsterSpawnAreaTable
import com.rpkit.monsters.bukkit.listener.CreatureSpawnListener
import com.rpkit.monsters.bukkit.listener.EntityDamageByEntityListener
import com.rpkit.monsters.bukkit.listener.EntityDamageListener
import com.rpkit.monsters.bukkit.listener.EntityDeathListener
import com.rpkit.monsters.bukkit.monsterexperience.RPKMonsterExperienceProviderImpl
import com.rpkit.monsters.bukkit.monsterlevel.RPKMonsterLevelProviderImpl
import com.rpkit.monsters.bukkit.monsterspawnarea.RPKMonsterSpawnAreaProviderImpl
import com.rpkit.monsters.bukkit.monsterstat.RPKMonsterStatProviderImpl
import org.bstats.bukkit.Metrics


class RPKMonstersBukkit: RPKBukkitPlugin() {

    override fun onEnable() {
        Metrics(this, 6661)
        saveDefaultConfig()
        serviceProviders = arrayOf(
                RPKMonsterExperienceProviderImpl(this),
                RPKMonsterLevelProviderImpl(this),
                RPKMonsterSpawnAreaProviderImpl(this),
                RPKMonsterStatProviderImpl(this)
        )
    }

    override fun registerCommands() {
        getCommand("monsterspawnarea")?.setExecutor(MonsterSpawnAreaCommand(this))
    }

    override fun registerListeners() {
        registerListeners(
                CreatureSpawnListener(this),
                EntityDamageByEntityListener(this),
                EntityDamageListener(this),
                EntityDeathListener(this)
        )
    }

    override fun createTables(database: Database) {
        database.addTable(RPKMonsterSpawnAreaMonsterTable(database, this))
        database.addTable(RPKMonsterSpawnAreaTable(database, this))
    }

    override fun setDefaultMessages() {
        messages.setDefault("monster-spawn-area-usage", "&cUsage: /monsterspawnarea [addmonster|create|delete]")
        messages.setDefault("not-from-console", "&cYou must be a player to perform that command.")
        messages.setDefault("monster-spawn-area-invalid-area", "&cYou must stand in the monster spawn area you wish to modify.")
        messages.setDefault("no-permission-monster-spawn-area-add-monster", "&cYou do not have permission to add monsters to spawn areas.")
        messages.setDefault("monster-spawn-area-add-monster-usage", "&cUsage: /monsterspawnarea addmonster [monster] [min-level] [max-level]")
        messages.setDefault("monster-spawn-area-add-monster-invalid-monster-type", "&cInvalid monster type.")
        messages.setDefault("monster-spawn-area-add-monster-invalid-min-level", "&cMinimum level must be an integer.")
        messages.setDefault("monster-spawn-area-add-monster-invalid-max-level", "&cMaximum level must be an integer.")
        messages.setDefault("monster-spawn-area-add-monster-valid", "&aAdded monster to spawn area.")
        messages.setDefault("no-minecraft-profile-self", "&cA Minecraft profile has not been created for you, or was unable to be retrieved. Please try relogging, and contact the server owner if this error persists.")
        messages.setDefault("no-permission-monster-spawn-area-create", "&cYou do not have permission to create monster spawn areas.")
        messages.setDefault("monster-spawn-area-create-valid", "&aMonster spawn area created.")
        messages.setDefault("no-permission-monster-spawn-area-delete", "&cYou do not have permission to delete monster")
        messages.setDefault("monster-spawn-area-delete-invalid-area", "&cYou must be standing in a monster spawn area in order to delete it.")
        messages.setDefault("monster-spawn-area-delete-valid", "&aMonster spawn area deleted.")
        messages.setDefault("no-permission-monster-spawn-area-remove-monster", "&cYou do not have permission to remove monsters from spawn areas.")
        messages.setDefault("monster-spawn-area-remove-monster-usage", "&cUsage: /monsterspawnarea removemonster [monster]")
        messages.setDefault("monster-spawn-area-remove-monster-invalid-monster-type", "&cInvalid monster type.")
        messages.setDefault("monster-spawn-area-remove-monster-valid", "&aRemoved monster from spawn area.")
        messages.setDefault("experience-gained", "&e+\$experience-gainedexp &7(\$experience/\$required-experience)")
    }

}