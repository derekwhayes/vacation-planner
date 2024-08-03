package dev.derekhayes.vacationplanner.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Entity(tableName = "vacations")
public class Vacation {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String name;

    @ColumnInfo(name = "accommodation_name")
    private String accommodationName;

    @ColumnInfo(name = "start_date")
    private String startDate;

    @ColumnInfo(name = "end_date")
    private String endDate;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "excursion_ids")
    private List<String> excursionIds;

    public Vacation(String name, String accommodationName, String startDate, String endDate, String description) {
        this.name = name;
        this.accommodationName = accommodationName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.excursionIds = new ArrayList<>();
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

    public String getAccommodationName() {
        return accommodationName;
    }

    public void setAccommodationName(String accommodationName) {
        this.accommodationName = accommodationName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getExcursionIds() {
        return excursionIds;
    }

    public void setExcursionIds(List<String> excursionIds) {
        this.excursionIds = excursionIds;
    }
}
