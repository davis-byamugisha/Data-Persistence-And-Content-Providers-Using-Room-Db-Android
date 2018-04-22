package davis.hit.persistenceandproviders.hospital_data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.content.ContentValues;
import android.provider.BaseColumns;


/**
 * Represents one record of the Hospitals table.
 */
@Entity(tableName = Hospitals.TABLE_NAME)
public class Hospitals {

    /**
     * The name of the Hospitals table.
     */
    public static final String TABLE_NAME = "hospitals";

    /**
     * The name of the ID column.
     */
    public static final String COLUMN_ID = BaseColumns._ID;

    /**
     * The name of the name column.
     */
    public static final String COLUMN_NAME = "name";

    /**
     * The unique ID of the hospital.
     */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = COLUMN_ID)
    public long id;

    /**
     * The name of the hospital.
     */
    @ColumnInfo(name = COLUMN_NAME)
    public String name;

    /**
     * Create a new {@link Hospitals} from the specified {@link ContentValues}.
     *
     * @param values A {@link ContentValues} that at least contain {@link #COLUMN_NAME}.
     * @return A newly created {@link Hospitals} instance.
     */
    public static Hospitals fromContentValues(ContentValues values) {
        final Hospitals hospitals = new Hospitals();
        if (values.containsKey(COLUMN_ID)) {
            hospitals.id = values.getAsLong(COLUMN_ID);
        }
        if (values.containsKey(COLUMN_NAME)) {
            hospitals.name = values.getAsString(COLUMN_NAME);
        }
        return hospitals;
    }

    /**
     * Dummy hospital_data.
     */
    static final String[] HOSPITALS = {
            "Mbarara Regional Referral", "Kitovu Hospital", "Mulago Hospital", "Mengo Hospital", "Itojo Hospital", "Kinoni Health Center",
            "Devine Mercy Health Center", "Mbarara Community Hospital", "Nyamitanga Health Center", "Rugarama Hospital", "Kagando Hospital",
            "Ruharo Hospital", "Holy Innocents"

    };

}
