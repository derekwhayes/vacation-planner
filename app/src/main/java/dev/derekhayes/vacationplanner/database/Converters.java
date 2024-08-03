package dev.derekhayes.vacationplanner.database;

import androidx.room.TypeConverter;

import java.util.Arrays;
import java.util.List;

public class Converters {
    @TypeConverter
    public static List<String> fromString(String value) {
        return Arrays.asList(value.split(","));
    }

    @TypeConverter
    public static String toString(List<String> list) {
        return String.join(",", list);
    }
}
