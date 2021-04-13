package io.atlasmap.qe.test;

import lombok.Data;

import java.sql.Time;
import java.sql.Timestamp;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;


@Data
public class DatesObject {

    private Date standardJavaDate;
    private java.sql.Date sqlDate;
    private Timestamp timestamp;
    private ZonedDateTime zonedDateTime;
    private LocalDateTime localDateTime;
    private LocalDate localDate;
    private LocalTime localTime;
    private Time time;
    private GregorianCalendar gregorianCalendar;
    private Calendar calendar;

    public DatesObject() {
        final String date = "05-05-1989";
        init(date);
    }

    public DatesObject(String date) {
        init(date);
    }

    private void init(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            this.standardJavaDate = formatter.parse(date);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Wrong date format: " + date);
        }
        Instant i = this.standardJavaDate.toInstant();

        this.sqlDate = new java.sql.Date(i.toEpochMilli());
        this.timestamp = Timestamp.from(i);
        this.zonedDateTime = ZonedDateTime.ofInstant(i, ZoneId.systemDefault());
        this.localDateTime = LocalDateTime.ofInstant(i, ZoneId.systemDefault());
        this.localDate = localDateTime.toLocalDate();
        this.localTime = localDateTime.toLocalTime();
        this.time = Time.valueOf(localTime);
        this.gregorianCalendar = GregorianCalendar.from(this.zonedDateTime);
        this.calendar = gregorianCalendar;
    }
}
