package io.atlasmap.qe.test;

import java.sql.Time;
import java.sql.Timestamp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;

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

    public DatesObject() throws ParseException {
        String date = "05-05-1989";
        init(date);
    }

    public DatesObject(String date) throws ParseException {
        init(date);
    }

    private void init(String date) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        this.standardJavaDate = formatter.parse(date);
        this.sqlDate = new java.sql.Date(standardJavaDate.getTime());
        this.timestamp = Timestamp.from(standardJavaDate.toInstant());
        this.zonedDateTime = ZonedDateTime.ofInstant(standardJavaDate.toInstant(), ZoneId.systemDefault());
        this.localDateTime = LocalDateTime.ofInstant(standardJavaDate.toInstant(), ZoneId.systemDefault());
        this.localDate = LocalDate.of(1989, 5, 5);
        this.localTime = LocalTime.now();
        this.time = Time.valueOf(localTime);
        this.gregorianCalendar = GregorianCalendar.from(this.zonedDateTime);
    }

    public Date getStandardJavaDate() {
        return standardJavaDate;
    }

    public void setStandardJavaDate(Date standardJavaDate) {
        this.standardJavaDate = standardJavaDate;
    }

    public java.sql.Date getSqlDate() {
        return sqlDate;
    }

    public void setSqlDate(java.sql.Date sqlDate) {
        this.sqlDate = sqlDate;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public ZonedDateTime getZonedDateTime() {
        return zonedDateTime;
    }

    public void setZonedDateTime(ZonedDateTime zonedDateTime) {
        this.zonedDateTime = zonedDateTime;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public LocalTime getLocalTime() {
        return localTime;
    }

    public void setLocalTime(LocalTime localTime) {
        this.localTime = localTime;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public GregorianCalendar getGregorianCalendar() {
        return gregorianCalendar;
    }

    public void setGregorianCalendar(GregorianCalendar gregorianCalendar) {
        this.gregorianCalendar = gregorianCalendar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DatesObject that = (DatesObject) o;
        return Objects.equals(standardJavaDate, that.standardJavaDate) &&
                Objects.equals(sqlDate, that.sqlDate) &&
                Objects.equals(timestamp, that.timestamp) &&
                Objects.equals(zonedDateTime, that.zonedDateTime) &&
                Objects.equals(localDateTime, that.localDateTime) &&
                Objects.equals(localDate, that.localDate) &&
                Objects.equals(localTime, that.localTime) &&
                Objects.equals(time, that.time) &&
                Objects.equals(gregorianCalendar, that.gregorianCalendar);
    }

    @Override
    public int hashCode() {

        return Objects.hash(standardJavaDate, sqlDate, timestamp, zonedDateTime, localDateTime, localDate, localTime, time, gregorianCalendar);
    }
}
