package davis.hit.persistenceandproviders;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Davis on 07/04/2018.
 */

@Entity
class AppointmentListItem {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "time")
    private String time;

    @ColumnInfo(name = "title")
    private String title;

    public AppointmentListItem() {

    }

    public AppointmentListItem(String time, String title) {
        this.time = time;
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}

