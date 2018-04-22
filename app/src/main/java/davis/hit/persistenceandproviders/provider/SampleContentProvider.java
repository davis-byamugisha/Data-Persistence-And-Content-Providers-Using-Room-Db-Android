
package davis.hit.persistenceandproviders.provider;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import davis.hit.persistenceandproviders.hospital_data.HopsitalsDao;
import davis.hit.persistenceandproviders.hospital_data.Hospitals;
import davis.hit.persistenceandproviders.hospital_data.SampleDatabase;

import java.util.ArrayList;


/**
 * A {@link ContentProvider} based on a Room database.
 *
 * <p>Note that you don't need to implement a ContentProvider unless you want to expose the hospital_data
 * outside your process or your application already uses a ContentProvider.</p>
 */
public class SampleContentProvider extends ContentProvider {

    /** The authority of this content provider. */
    public static final String AUTHORITY = "davis.hit.persistenceandproviders.provider";

    /** The URI for the Hospitals table. */
    public static final Uri URI_HOSPITALS = Uri.parse(
            "content://" + AUTHORITY + "/" + Hospitals.TABLE_NAME);

    /** The match code for some items in the Hospitals table. */
    private static final int CODE_HOSPITAL_DIR = 1;

    /** The match code for an item in the Hospitals table. */
    private static final int CODE_HOSPITAL_ITEM = 2;

    /** The URI matcher. */
    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        MATCHER.addURI(AUTHORITY, Hospitals.TABLE_NAME, CODE_HOSPITAL_DIR);
        MATCHER.addURI(AUTHORITY, Hospitals.TABLE_NAME + "/*", CODE_HOSPITAL_ITEM);
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final int code = MATCHER.match(uri);
        if (code == CODE_HOSPITAL_DIR || code == CODE_HOSPITAL_ITEM) {
            final Context context = getContext();
            if (context == null) {
                return null;
            }
            HopsitalsDao hospital = SampleDatabase.getInstance(context).hospital();
            final Cursor cursor;
            if (code == CODE_HOSPITAL_DIR) {
                cursor = hospital.selectAll();
            } else {
                cursor = hospital.selectById(ContentUris.parseId(uri));
            }
            cursor.setNotificationUri(context.getContentResolver(), uri);
            return cursor;
        } else {
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (MATCHER.match(uri)) {
            case CODE_HOSPITAL_DIR:
                return "vnd.android.cursor.dir/" + AUTHORITY + "." + Hospitals.TABLE_NAME;
            case CODE_HOSPITAL_ITEM:
                return "vnd.android.cursor.item/" + AUTHORITY + "." + Hospitals.TABLE_NAME;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        switch (MATCHER.match(uri)) {
            case CODE_HOSPITAL_DIR:
                final Context context = getContext();
                if (context == null) {
                    return null;
                }
                final long id = SampleDatabase.getInstance(context).hospital()
                        .insert(Hospitals.fromContentValues(values));
                context.getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(uri, id);
            case CODE_HOSPITAL_ITEM:
                throw new IllegalArgumentException("Invalid URI, cannot insert with ID: " + uri);
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        switch (MATCHER.match(uri)) {
            case CODE_HOSPITAL_DIR:
                throw new IllegalArgumentException("Invalid URI, cannot update without ID" + uri);
            case CODE_HOSPITAL_ITEM:
                final Context context = getContext();
                if (context == null) {
                    return 0;
                }
                final int count = SampleDatabase.getInstance(context).hospital()
                        .deleteById(ContentUris.parseId(uri));
                context.getContentResolver().notifyChange(uri, null);
                return count;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        switch (MATCHER.match(uri)) {
            case CODE_HOSPITAL_DIR:
                throw new IllegalArgumentException("Invalid URI, cannot update without ID" + uri);
            case CODE_HOSPITAL_ITEM:
                final Context context = getContext();
                if (context == null) {
                    return 0;
                }
                final Hospitals hospitals = Hospitals.fromContentValues(values);
                hospitals.id = ContentUris.parseId(uri);
                final int count = SampleDatabase.getInstance(context).hospital()
                        .update(hospitals);
                context.getContentResolver().notifyChange(uri, null);
                return count;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @NonNull
    @Override
    public ContentProviderResult[] applyBatch(
            @NonNull ArrayList<ContentProviderOperation> operations)
            throws OperationApplicationException {
        final Context context = getContext();
        if (context == null) {
            return new ContentProviderResult[0];
        }
        final SampleDatabase database = SampleDatabase.getInstance(context);
        database.beginTransaction();
        try {
            final ContentProviderResult[] result = super.applyBatch(operations);
            database.setTransactionSuccessful();
            return result;
        } finally {
            database.endTransaction();
        }
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] valuesArray) {
        switch (MATCHER.match(uri)) {
            case CODE_HOSPITAL_DIR:
                final Context context = getContext();
                if (context == null) {
                    return 0;
                }
                final SampleDatabase database = SampleDatabase.getInstance(context);
                final Hospitals[] hospitals = new Hospitals[valuesArray.length];
                for (int i = 0; i < valuesArray.length; i++) {
                    hospitals[i] = Hospitals.fromContentValues(valuesArray[i]);
                }
                return database.hospital().insertAll(hospitals).length;
            case CODE_HOSPITAL_ITEM:
                throw new IllegalArgumentException("Invalid URI, cannot insert with ID: " + uri);
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

}
