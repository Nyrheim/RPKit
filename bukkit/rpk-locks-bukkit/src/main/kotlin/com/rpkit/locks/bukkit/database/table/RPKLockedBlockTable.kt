package com.rpkit.locks.bukkit.database.table

import com.rpkit.core.database.Database
import com.rpkit.core.database.Table
import com.rpkit.locks.bukkit.RPKLocksBukkit
import com.rpkit.locks.bukkit.database.jooq.rpkit.Tables.RPKIT_LOCKED_BLOCK
import com.rpkit.locks.bukkit.lock.RPKLockedBlock
import org.bukkit.block.Block
import org.ehcache.config.builders.CacheConfigurationBuilder
import org.ehcache.config.builders.ResourcePoolsBuilder
import org.jooq.impl.DSL.constraint
import org.jooq.impl.SQLDataType


class RPKLockedBlockTable(database: Database, private val plugin: RPKLocksBukkit): Table<RPKLockedBlock>(database, RPKLockedBlock::class) {

    private val cache = if (plugin.config.getBoolean("caching.rpkit_locked_block.id.enabled")) {
        database.cacheManager.createCache("rpk-locks-bukkit.rpkit_locked_block.id",
                CacheConfigurationBuilder.newCacheConfigurationBuilder(Int::class.javaObjectType, RPKLockedBlock::class.java,
                        ResourcePoolsBuilder.heap(plugin.config.getLong("caching.rpkit_locked_block.id.size"))))
    } else {
        null
    }

    override fun create() {
        database.create
                .createTableIfNotExists(RPKIT_LOCKED_BLOCK)
                .column(RPKIT_LOCKED_BLOCK.ID, SQLDataType.INTEGER.identity(true))
                .column(RPKIT_LOCKED_BLOCK.WORLD, SQLDataType.VARCHAR(256))
                .column(RPKIT_LOCKED_BLOCK.X, SQLDataType.INTEGER)
                .column(RPKIT_LOCKED_BLOCK.Y, SQLDataType.INTEGER)
                .column(RPKIT_LOCKED_BLOCK.Z, SQLDataType.INTEGER)
                .constraints(
                        constraint("pk_rpkit_locked_block").primaryKey(RPKIT_LOCKED_BLOCK.ID)
                )
                .execute()
    }

    override fun applyMigrations() {
        if (database.getTableVersion(this) == null) {
            database.setTableVersion(this, "1.1.0")
        }
    }

    override fun insert(entity: RPKLockedBlock): Int {
        database.create
                .insertInto(
                        RPKIT_LOCKED_BLOCK,
                        RPKIT_LOCKED_BLOCK.WORLD,
                        RPKIT_LOCKED_BLOCK.X,
                        RPKIT_LOCKED_BLOCK.Y,
                        RPKIT_LOCKED_BLOCK.Z
                )
                .values(
                        entity.block.world.name,
                        entity.block.x,
                        entity.block.y,
                        entity.block.z
                )
                .execute()
        val id = database.create.lastID().toInt()
        entity.id = id
        cache?.put(id, entity)
        return id
    }

    override fun update(entity: RPKLockedBlock) {
        database.create
                .update(RPKIT_LOCKED_BLOCK)
                .set(RPKIT_LOCKED_BLOCK.WORLD, entity.block.world.name)
                .set(RPKIT_LOCKED_BLOCK.X, entity.block.x)
                .set(RPKIT_LOCKED_BLOCK.Y, entity.block.y)
                .set(RPKIT_LOCKED_BLOCK.Z, entity.block.z)
                .where(RPKIT_LOCKED_BLOCK.ID.eq(entity.id))
                .execute()
        cache?.put(entity.id, entity)
    }

    override fun get(id: Int): RPKLockedBlock? {
        if (cache?.containsKey(id) == true) {
            return cache[id]
        } else {
            val result = database.create
                    .select(
                            RPKIT_LOCKED_BLOCK.WORLD,
                            RPKIT_LOCKED_BLOCK.X,
                            RPKIT_LOCKED_BLOCK.Y,
                            RPKIT_LOCKED_BLOCK.Z
                    )
                    .from(RPKIT_LOCKED_BLOCK)
                    .where(RPKIT_LOCKED_BLOCK.ID.eq(id))
                    .fetchOne() ?: return null
            val block = plugin.server.getWorld(result.get(RPKIT_LOCKED_BLOCK.WORLD))?.getBlockAt(
                    result.get(RPKIT_LOCKED_BLOCK.X),
                    result.get(RPKIT_LOCKED_BLOCK.Y),
                    result.get(RPKIT_LOCKED_BLOCK.Z)
            )
            if (block == null) {
                database.create
                        .deleteFrom(RPKIT_LOCKED_BLOCK)
                        .where(RPKIT_LOCKED_BLOCK.ID.eq(id))
                        .execute()
                cache?.remove(id)
                return null
            }
            val lockedBlock = RPKLockedBlock(
                    id,
                    block
            )
            cache?.put(id, lockedBlock)
            return lockedBlock
        }
    }

    fun get(block: Block): RPKLockedBlock? {
        val result = database.create
                .select(RPKIT_LOCKED_BLOCK.ID)
                .from(RPKIT_LOCKED_BLOCK)
                .where(RPKIT_LOCKED_BLOCK.WORLD.eq(block.world.name))
                .and(RPKIT_LOCKED_BLOCK.X.eq(block.x))
                .and(RPKIT_LOCKED_BLOCK.Y.eq(block.y))
                .and(RPKIT_LOCKED_BLOCK.Z.eq(block.z))
                .fetchOne() ?: return null
        return get(result[RPKIT_LOCKED_BLOCK.ID])
    }

    override fun delete(entity: RPKLockedBlock) {
        database.create
                .deleteFrom(RPKIT_LOCKED_BLOCK)
                .where(RPKIT_LOCKED_BLOCK.ID.eq(entity.id))
                .execute()
        cache?.remove(entity.id)
    }
}