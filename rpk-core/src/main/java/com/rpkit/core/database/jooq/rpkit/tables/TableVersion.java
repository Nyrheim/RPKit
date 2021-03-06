/*
 * This file is generated by jOOQ.
*/
package com.rpkit.core.database.jooq.rpkit.tables;


import com.rpkit.core.database.jooq.rpkit.Indexes;
import com.rpkit.core.database.jooq.rpkit.Keys;
import com.rpkit.core.database.jooq.rpkit.Rpkit;
import com.rpkit.core.database.jooq.rpkit.tables.records.TableVersionRecord;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Identity;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.2"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TableVersion extends TableImpl<TableVersionRecord> {

    private static final long serialVersionUID = -322537484;

    /**
     * The reference instance of <code>rpkit.table_version</code>
     */
    public static final TableVersion TABLE_VERSION = new TableVersion();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TableVersionRecord> getRecordType() {
        return TableVersionRecord.class;
    }

    /**
     * The column <code>rpkit.table_version.id</code>.
     */
    public final TableField<TableVersionRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>rpkit.table_version.table_name</code>.
     */
    public final TableField<TableVersionRecord, String> TABLE_NAME = createField("table_name", org.jooq.impl.SQLDataType.VARCHAR(256), this, "");

    /**
     * The column <code>rpkit.table_version.version</code>.
     */
    public final TableField<TableVersionRecord, String> VERSION = createField("version", org.jooq.impl.SQLDataType.VARCHAR(32), this, "");

    /**
     * Create a <code>rpkit.table_version</code> table reference
     */
    public TableVersion() {
        this(DSL.name("table_version"), null);
    }

    /**
     * Create an aliased <code>rpkit.table_version</code> table reference
     */
    public TableVersion(String alias) {
        this(DSL.name(alias), TABLE_VERSION);
    }

    /**
     * Create an aliased <code>rpkit.table_version</code> table reference
     */
    public TableVersion(Name alias) {
        this(alias, TABLE_VERSION);
    }

    private TableVersion(Name alias, Table<TableVersionRecord> aliased) {
        this(alias, aliased, null);
    }

    private TableVersion(Name alias, Table<TableVersionRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Rpkit.RPKIT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.TABLE_VERSION_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<TableVersionRecord, Integer> getIdentity() {
        return Keys.IDENTITY_TABLE_VERSION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<TableVersionRecord> getPrimaryKey() {
        return Keys.KEY_TABLE_VERSION_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TableVersionRecord>> getKeys() {
        return Arrays.<UniqueKey<TableVersionRecord>>asList(Keys.KEY_TABLE_VERSION_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TableVersion as(String alias) {
        return new TableVersion(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TableVersion as(Name alias) {
        return new TableVersion(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public TableVersion rename(String name) {
        return new TableVersion(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public TableVersion rename(Name name) {
        return new TableVersion(name, null);
    }
}
