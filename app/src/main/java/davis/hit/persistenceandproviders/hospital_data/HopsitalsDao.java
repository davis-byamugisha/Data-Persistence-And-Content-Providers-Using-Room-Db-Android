
package davis.hit.persistenceandproviders.hospital_data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;


/**
 * Data access object for Hospitals.
 */
@Dao
public interface HopsitalsDao {

    /**
     * Counts the number of hospitals in the table.
     *
     * @return The number of hospitals.
     */
    @Query("SELECT COUNT(*) FROM " + Hospitals.TABLE_NAME)
    int count();

    /**
     * Inserts a hospitals into the table.
     *
     * @param hospitals A new hospitals.
     * @return The row ID of the newly inserted hospitals.
     */
    @Insert
    long insert(Hospitals hospitals);

    /**
     * Inserts multiple hospitals into the database
     *
     * @param hospitals An array of new hospitals.
     * @return The row IDs of the newly inserted hospitals.
     */
    @Insert
    long[] insertAll(Hospitals[] hospitals);

    /**
     * Select all hospitals.
     *
     * @return A {@link Cursor} of all the hospitals in the table.
     */
    @Query("SELECT * FROM " + Hospitals.TABLE_NAME)
    Cursor selectAll();

    /**
     * Select a hospital by the ID.
     *
     * @param id The row ID.
     * @return A {@link Cursor} of the selected hospital.
     */
    @Query("SELECT * FROM " + Hospitals.TABLE_NAME + " WHERE " + Hospitals.COLUMN_ID + " = :id")
    Cursor selectById(long id);

    /**
     * Delete a hospital by the ID.
     *
     * @param id The row ID.
     * @return A number of hospitals deleted. This should always be {@code 1}.
     */
    @Query("DELETE FROM " + Hospitals.TABLE_NAME + " WHERE " + Hospitals.COLUMN_ID + " = :id")
    int deleteById(long id);

    /**
     * Update the hospitals. The hospitals is identified by the row ID.
     *
     * @param hospitals The hospitals to update.
     * @return A number of hospitals updated. This should always be {@code 1}.
     */
    @Update
    int update(Hospitals hospitals);

}
