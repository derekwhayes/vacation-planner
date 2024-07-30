package dev.derekhayes.vacationplanner.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "excursions", foreignKeys = {@ForeignKey(entity = Vacation.class,
    parentColumns = "id",
    childColumns = "vacation_id",
    onDelete = ForeignKey.CASCADE)
})
public class Excursion {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String name;

    private Date date;

    private String description;

    @ColumnInfo(name = "vacation_id")
    private long vacationId;

    public Excursion(String name, Date date, String description, long vacationId) {
        this.name = name;
        this.date = date;
        this.description = description;
        this.vacationId = vacationId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getVacationId() {
        return vacationId;
    }

    public void setVacationId(long vacationId) {
        this.vacationId = vacationId;
    }
}