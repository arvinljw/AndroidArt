package net.arvin.androidart.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;

import net.arvin.greendao.gen.DaoMaster;
import net.arvin.greendao.gen.UserDao;

/**
 * created by arvin on 17/2/25 15:10
 * email：1035407623@qq.com
 */
public class UserProvider extends ContentProvider {
    //mimeType
    public static final String MIME_DIR_PREFIX = "vnd.android.cursor.dir";
    public static final String MIME_ITEM_PREFIX = "vnd.android.cursor.item";
    public static final String MIME_ITEM = "user";

    public static final String MIME_TYPE_SINGLE = MIME_ITEM_PREFIX + "/" + MIME_ITEM;
    public static final String MIME_TYPE_MULTIPLE = MIME_DIR_PREFIX + "/" + MIME_ITEM;

    //有效Uri
    public static final String AUTHORITY = "net.arvin.androidart";
    public static final String PATH_SINGLE = MIME_ITEM + "/#";
    public static final String PATH_MULTIPLE = MIME_ITEM;

    private static final int MULTIPLE_PEOPLE = 1;
    private static final int SINGLE_PEOPLE = 2;
    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, PATH_MULTIPLE, MULTIPLE_PEOPLE);
        uriMatcher.addURI(AUTHORITY, PATH_SINGLE, SINGLE_PEOPLE);
    }

    public static final String CONTENT_URI_STRING = "content://" + AUTHORITY + "/" + PATH_MULTIPLE;
    public static final Uri CONTENT_URI = Uri.parse(CONTENT_URI_STRING);

    private SQLiteDatabase db;

    @Override
    public boolean onCreate() {
        DaoMaster.DevOpenHelper mHelper = new DaoMaster.DevOpenHelper(getContext(), "art-db", null);
        db = mHelper.getWritableDatabase();
        return db != null;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case MULTIPLE_PEOPLE:
                return MIME_TYPE_MULTIPLE;
            case SINGLE_PEOPLE:
                return MIME_TYPE_SINGLE;
            default:
                throw new IllegalArgumentException("UnKnow uri:" + uri);
        }
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        long id = db.insert(UserDao.TABLENAME, null, values);
        if (id > 0) {
            Uri newUri = ContentUris.withAppendedId(CONTENT_URI, id);
            notifyChange(newUri);
            return newUri;
        }
        throw new SQLException("failed to insert row into " + uri);
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor = db.query(UserDao.TABLENAME, projection, getWhere(uri, selection), selectionArgs, null, null, sortOrder);
        notifyChange(uri);
        return cursor;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        int count = db.delete(UserDao.TABLENAME, getWhere(uri, selection), selectionArgs);
        notifyChange(uri);
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int count = db.update(UserDao.TABLENAME, values, getWhere(uri, selection), selectionArgs);
        notifyChange(uri);
        return count;
    }

    private String getWhere(@NonNull Uri uri, String selection) {
        String where;
        switch (uriMatcher.match(uri)) {
            case SINGLE_PEOPLE:
                where = UserDao.Properties.Id.columnName + "=" + uri.getPathSegments().get(1);
                break;
            case MULTIPLE_PEOPLE:
                where = selection;
                break;
            default:
                throw new IllegalArgumentException("UnKnow URI: " + uri);
        }
        return where;
    }

    private void notifyChange(@NonNull Uri uri) {
        if (getContext() != null) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
    }
}
