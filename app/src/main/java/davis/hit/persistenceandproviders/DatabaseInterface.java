package davis.hit.persistenceandproviders;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by Davis on 07/04/2018.
 */

@Dao
public interface DatabaseInterface {
    @Query("SELECT * FROM AppointmentListItem")
    List<AppointmentListItem> getAllItems();

    @Insert
    void insertAll(AppointmentListItem... appointmentListItems);
}
