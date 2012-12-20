package com.moodspaces.model;

import java.util.List;
import java.util.ArrayList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.DaoConfig;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.SqlUtils;

import com.moodspaces.model.MoodEntryPersonMap;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table MOOD_ENTRY_PERSON_MAP.
*/
public class MoodEntryPersonMapDao extends AbstractDao<MoodEntryPersonMap, Long> {

    public static final String TABLENAME = "MOOD_ENTRY_PERSON_MAP";

    /**
     * Properties of entity MoodEntryPersonMap.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property EntryId = new Property(1, Long.class, "entryId", false, "ENTRY_ID");
        public final static Property PersonId = new Property(2, Long.class, "personId", false, "PERSON_ID");
    };

    private DaoSession daoSession;


    public MoodEntryPersonMapDao(DaoConfig config) {
        super(config);
    }
    
    public MoodEntryPersonMapDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'MOOD_ENTRY_PERSON_MAP' (" + //
                "'_id' INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "'ENTRY_ID' INTEGER," + // 1: entryId
                "'PERSON_ID' INTEGER);"); // 2: personId
        // Add Indexes
        db.execSQL("CREATE INDEX " + constraint + "IDX_MOOD_ENTRY_PERSON_MAP_ENTRY_ID ON MOOD_ENTRY_PERSON_MAP" +
                " (ENTRY_ID);");
        db.execSQL("CREATE INDEX " + constraint + "IDX_MOOD_ENTRY_PERSON_MAP_PERSON_ID ON MOOD_ENTRY_PERSON_MAP" +
                " (PERSON_ID);");
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'MOOD_ENTRY_PERSON_MAP'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, MoodEntryPersonMap entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Long entryId = entity.getEntryId();
        if (entryId != null) {
            stmt.bindLong(2, entryId);
        }
 
        Long personId = entity.getPersonId();
        if (personId != null) {
            stmt.bindLong(3, personId);
        }
    }

    @Override
    protected void attachEntity(MoodEntryPersonMap entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public MoodEntryPersonMap readEntity(Cursor cursor, int offset) {
        MoodEntryPersonMap entity = new MoodEntryPersonMap( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // entryId
            cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2) // personId
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, MoodEntryPersonMap entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setEntryId(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setPersonId(cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(MoodEntryPersonMap entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(MoodEntryPersonMap entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getMoodEntryDao().getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T1", daoSession.getMoodPersonDao().getAllColumns());
            builder.append(" FROM MOOD_ENTRY_PERSON_MAP T");
            builder.append(" LEFT JOIN MOOD_ENTRY T0 ON T.'ENTRY_ID'=T0.'_id'");
            builder.append(" LEFT JOIN MOOD_PERSON T1 ON T.'PERSON_ID'=T1.'_id'");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected MoodEntryPersonMap loadCurrentDeep(Cursor cursor, boolean lock) {
        MoodEntryPersonMap entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        MoodEntry moodEntry = loadCurrentOther(daoSession.getMoodEntryDao(), cursor, offset);
        entity.setMoodEntry(moodEntry);
        offset += daoSession.getMoodEntryDao().getAllColumns().length;

        MoodPerson moodPerson = loadCurrentOther(daoSession.getMoodPersonDao(), cursor, offset);
        entity.setMoodPerson(moodPerson);

        return entity;    
    }

    public MoodEntryPersonMap loadDeep(Long key) {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();
        
        String[] keyArray = new String[] { key.toString() };
        Cursor cursor = db.rawQuery(sql, keyArray);
        
        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            } else if (!cursor.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + cursor.getCount());
            }
            return loadCurrentDeep(cursor, true);
        } finally {
            cursor.close();
        }
    }
    
    /** Reads all available rows from the given cursor and returns a list of new ImageTO objects. */
    public List<MoodEntryPersonMap> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<MoodEntryPersonMap> list = new ArrayList<MoodEntryPersonMap>(count);
        
        if (cursor.moveToFirst()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(cursor, false));
                } while (cursor.moveToNext());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }
    
    protected List<MoodEntryPersonMap> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<MoodEntryPersonMap> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}