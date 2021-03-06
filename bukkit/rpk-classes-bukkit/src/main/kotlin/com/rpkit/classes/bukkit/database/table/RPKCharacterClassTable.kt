package com.rpkit.classes.bukkit.database.table

import com.rpkit.characters.bukkit.character.RPKCharacter
import com.rpkit.characters.bukkit.character.RPKCharacterProvider
import com.rpkit.classes.bukkit.RPKClassesBukkit
import com.rpkit.classes.bukkit.classes.RPKCharacterClass
import com.rpkit.classes.bukkit.classes.RPKClassProvider
import com.rpkit.classes.bukkit.database.jooq.rpkit.Tables.RPKIT_CHARACTER_CLASS
import com.rpkit.core.database.Database
import com.rpkit.core.database.Table
import org.ehcache.config.builders.CacheConfigurationBuilder
import org.ehcache.config.builders.ResourcePoolsBuilder
import org.jooq.impl.DSL.constraint
import org.jooq.impl.SQLDataType


class RPKCharacterClassTable(database: Database, private val plugin: RPKClassesBukkit): Table<RPKCharacterClass>(database, RPKCharacterClass::class) {

    private val cache = if (plugin.config.getBoolean("caching.rpkit_character_class.id.enabled")) {
        database.cacheManager.createCache("rpk-classes-bukkit.rpkit_character_class.id",
                CacheConfigurationBuilder.newCacheConfigurationBuilder(Int::class.javaObjectType, RPKCharacterClass::class.java,
                        ResourcePoolsBuilder.heap(plugin.config.getLong("caching.rpkit_character_class.id.size"))))
    } else {
        null
    }

    override fun create() {
        database.create
                .createTableIfNotExists(RPKIT_CHARACTER_CLASS)
                .column(RPKIT_CHARACTER_CLASS.ID, SQLDataType.INTEGER.identity(true))
                .column(RPKIT_CHARACTER_CLASS.CHARACTER_ID, SQLDataType.INTEGER)
                .column(RPKIT_CHARACTER_CLASS.CLASS_NAME, SQLDataType.VARCHAR(256))
                .constraints(
                        constraint("pk_rpkit_character_class").primaryKey(RPKIT_CHARACTER_CLASS.ID)
                )
                .execute()
    }

    override fun applyMigrations() {
        if (database.getTableVersion(this) == null) {
            database.setTableVersion(this, "1.2.0")
        }
    }

    override fun insert(entity: RPKCharacterClass): Int {
        database.create
                .insertInto(
                        RPKIT_CHARACTER_CLASS,
                        RPKIT_CHARACTER_CLASS.CHARACTER_ID,
                        RPKIT_CHARACTER_CLASS.CLASS_NAME
                )
                .values(
                        entity.character.id,
                        entity.`class`.name
                )
                .execute()
        val id = database.create.lastID().toInt()
        entity.id = id
        cache?.put(id, entity)
        return id
    }

    override fun update(entity: RPKCharacterClass) {
        database.create
                .update(RPKIT_CHARACTER_CLASS)
                .set(RPKIT_CHARACTER_CLASS.CHARACTER_ID, entity.character.id)
                .set(RPKIT_CHARACTER_CLASS.CLASS_NAME, entity.`class`.name)
                .where(RPKIT_CHARACTER_CLASS.ID.eq(entity.id))
                .execute()
        cache?.put(entity.id, entity)
    }

    override fun get(id: Int): RPKCharacterClass? {
        if (cache?.containsKey(id) == true) {
            return cache.get(id)
        } else {
            val result = database.create
                    .select(
                            RPKIT_CHARACTER_CLASS.CHARACTER_ID,
                            RPKIT_CHARACTER_CLASS.CLASS_NAME
                    )
                    .from(RPKIT_CHARACTER_CLASS)
                    .where(RPKIT_CHARACTER_CLASS.ID.eq(id))
                    .fetchOne() ?: return null
            val characterProvider = plugin.core.serviceManager.getServiceProvider(RPKCharacterProvider::class)
            val characterId = result.get(RPKIT_CHARACTER_CLASS.CHARACTER_ID)
            val character = characterProvider.getCharacter(characterId)
            val classProvider = plugin.core.serviceManager.getServiceProvider(RPKClassProvider::class)
            val className = result.get(RPKIT_CHARACTER_CLASS.CLASS_NAME)
            val `class` = classProvider.getClass(className)
            return if (character != null && `class` != null) {
                val characterClass = RPKCharacterClass(
                        id,
                        character,
                        `class`
                )
                cache?.put(id, characterClass)
                characterClass
            } else {
                database.create
                        .deleteFrom(RPKIT_CHARACTER_CLASS)
                        .where(RPKIT_CHARACTER_CLASS.ID.eq(id))
                        .execute()
                null
            }
        }
    }

    fun get(character: RPKCharacter): RPKCharacterClass? {
        val result = database.create
                .select(RPKIT_CHARACTER_CLASS.ID)
                .from(RPKIT_CHARACTER_CLASS)
                .where(RPKIT_CHARACTER_CLASS.CHARACTER_ID.eq(character.id))
                .fetchOne() ?: return null
        return get(result.get(RPKIT_CHARACTER_CLASS.ID))
    }

    override fun delete(entity: RPKCharacterClass) {
        database.create
                .deleteFrom(RPKIT_CHARACTER_CLASS)
                .where(RPKIT_CHARACTER_CLASS.ID.eq(entity.id))
                .execute()
        cache?.remove(entity.id)
    }
}