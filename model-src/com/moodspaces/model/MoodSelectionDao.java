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
import de.greenrobot.dao.Query;
import de.greenrobot.dao.QueryBuilder;

import com.moodspaces.model.MoodSelection;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table MOOD_SELECTION.
*/
public class MoodSelectionDao extends AbstractDao<MoodSelection, Long> {

    public static final String TABLENAME = "MOOD_SELECTION";

    /**
     * Properties of entity MoodSelection.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property R = new Property(1, double.class, "r", false, "R");
        public final static Property Theta = new Property(2, double.class, "theta", false, "THETA");
        public final static Property Entry = new Property(3, Long.class, "entry", false, "ENTRY");
    };

    private DaoSession daoSession;

    private Query<MoodSelection> moodEntry_SelectionsQuery;

    public MoodSelectionDao(DaoConfig config) {
        super(config);
    }
    
    public MoodSelectionDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'MOOD_SELECTION' (" + //
                "'_id' INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "'R' REAL NOT NULL ," + // 1: r
                "'THETA' REAL NOT NULL ," + // 2: theta
                "'ENTRY' INTEGER);"); // 3: entry
        // Add Indexes
        db.execSQL("CREATE INDEX " + constraint + "IDX_MOOD_SELECTION_THETA ON MOOD_SELECTION" +
                " (THETA);");
        db.execSQL("CREATE INDEX " + constraint + "IDX_MOOD_SELECTION_ENTRY ON MOOD_SELECTION" +
                " (ENTRY);");
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'MOOD_SELECTION'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, MoodSelection entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindDouble(2, entity.getR());
        stmt.bindDouble(3, entity.getTheta());
 
        Long entry = entity.getEntry();
        if (entry != null) {
            stmt.bindLong(4, entry);
        }
    }

    @Override
    protected void attachEntity(MoodSelection entity) {
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
    public MoodSelection readEntity(Cursor cursor, int offset) {
        MoodSelection entity = new MoodSelection( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getDouble(offset + 1), // r
            cursor.getDouble(offset + 2), // theta
            cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3) // entry
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, MoodSelection entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setR(cursor.getDouble(offset + 1));
        entity.setTheta(cursor.getDouble(offset + 2));
        entity.setEntry(cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(MoodSelection entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(MoodSelection entity) {
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
    
    /** Internal query to resolve the "selections" to-many relationship of MoodEntry. */
    public synchronized List<MoodSelection> _queryMoodEntry_Selections(Long entry) {
        if (moodEntry_SelectionsQuery == null) {
            QueryBuilder<MoodSelection> queryBuilder = queryBuilder();
            queryBuilder.where(Properties.Entry.eq(entry));
            moodEntry_SelectionsQuery = queryBuilder.build();
        } else {
            moodEntry_SelectionsQuery.setParameter(0, entry);
        }
        return moodEntry_SelectionsQuery.list();
    }

    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getMoodEntryDao().getAllColumns());
            builder.append(" FROM MOOD_SELECTION T");
            builder.append(" LEFT JOIN MOOD_ENTRY T0 ON T.'ENTRY'=T0.'_id'");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected MoodSelection loadCurrentDeep(Cursor cursor, boolean lock) {
        MoodSelection entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        MoodEntry moodEntry = loadCurrentOther(daoSession.getMoodEntryDao(), cursor, offset);
        entity.setMoodEntry(moodEntry);

        return entity;    
    }

    public MoodSelection loadDeep(Long key) {
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
    public List<MoodSelection> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<MoodSelection> list = new ArrayList<MoodSelection>(count);
        
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
    
    protected List<MoodSelection> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<MoodSelection> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}
