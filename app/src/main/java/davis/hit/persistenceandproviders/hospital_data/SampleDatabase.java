package davis.hit.persistenceandproviders.hospital_data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.VisibleForTesting;


/**
 * The Room database.
 */
@Database(entities = {Hospitals.class}, version = 1)

public abstract class SampleDatabase extends RoomDatabase {

    /**
     * @return The DAO for the Hospitals table.
     */
    @SuppressWarnings("WeakerAccess")
    public abstract HopsitalsDao hospital();

    /** The only instance */
    private static SampleDatabase sInstance;

    /**
     * Gets the singleton instance of SampleDatabase.
     *
     * @param context The context.
     * @return The singleton instance of SampleDatabase.
     */
    public static synchronized SampleDatabase getInstance(Context context) {
        if (sInstance == null) {
            sInstance = Room
                    .databaseBuilder(context.getApplicationContext(), SampleDatabase.class, "ex")
                    .build();
            sInstance.populateInitialData();
        }
        return sInstance;
    }

    /**
     * Switches the internal implementation with an empty in-memory database.
     *
     * @param context The context.
     */
    @VisibleForTesting
    public static void switchToInMemory(Context context) {
        sInstance = Room.inMemoryDatabaseBuilder(context.getApplicationContext(),
                SampleDatabase.class).build();
    }

    /**
     * Inserts the dummy hospital_data into the database if it is currently empty.
     */
    private void populateInitialData() {
        if (hospital().count() == 0) {
            Hospitals hospitals = new Hospitals();
            beginTransaction();
            try {
                for (int i = 0; i < Hospitals.HOSPITALS.length; i++) {
                    hospitals.name = Hospitals.HOSPITALS[i];
                    hospital().insert(hospitals);
                }
                setTransactionSuccessful();
            } finally {
                endTransaction();
            }
        }
    }

}
