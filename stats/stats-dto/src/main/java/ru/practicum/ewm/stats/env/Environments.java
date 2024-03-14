package ru.practicum.ewm.stats.env;

import java.time.format.DateTimeFormatter;

public class Environments {
   public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
   public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);

   public Environments() {
   }
}
