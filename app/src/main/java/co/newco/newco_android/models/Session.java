package co.newco.newco_android.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import co.newco.newco_android.Network.SessionData;

/**
 * Created by jayd on 10/13/15.
 */
public class Session{
    private String event_key;
    private String active;
    private String name;
    private String event_start;
    private String event_end;
    private String event_type;
    private String event_subtype;
    private String description;
    private int goers;
    private String media_url;
    private int seats;
    private String invite_only;
    private String venue;
    private String address;
    private String audience;
    private String tags;
    private String custom5;
    private String id;
    private String venue_id;

    private ArrayList<Artist> artists;
    private ArrayList<Speaker> speakers;
    private ArrayList<Volunteer> volunteers;

    private String event_start_year;
    private String event_start_month;
    private String event_start_month_short;
    private String event_start_day;
    private String event_start_weekday;
    private String event_start_weekday_short;
    private String event_start_time;
    private String event_end_year;
    private String event_end_month;
    private String event_end_month_short;
    private String event_end_day;
    private String event_end_weekday;
    private String event_end_weekday_short;
    private String event_end_time;
    private String start_date;
    private String start_time;
    private String start_time_ts;
    private String end_date;
    private String end_time;
    private String lat;
    private String lon;
    private String event_type_sort;
    @SerializedName("seats-status") private String seats_status;
    @SerializedName("seats-title") private String seats_title;

    public String getSessionColor(){
       return SessionData.getInstance().getColorsHash().get(event_type.split(", ")[0].trim());
    }

    public String getEvent_key() {
        return event_key;
    }

    public void setEvent_key(String event_key) {
        this.event_key = event_key;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEvent_start() {
        return event_start;
    }

    public void setEvent_start(String event_start) {
        this.event_start = event_start;
    }

    public String getEvent_end() {
        return event_end;
    }

    public void setEvent_end(String event_end) {
        this.event_end = event_end;
    }

    public String getEvent_type() {
        return event_type;
    }

    public void setEvent_type(String event_type) {
        this.event_type = event_type;
    }

    public String getEvent_subtype() {
        return event_subtype;
    }

    public void setEvent_subtype(String event_subtype) {
        this.event_subtype = event_subtype;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getGoers() {
        return goers;
    }

    public void setGoers(int goers) {
        this.goers = goers;
    }

    public String getMedia_url() {
        return media_url;
    }

    public void setMedia_url(String media_url) {
        this.media_url = media_url;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public String getInvite_only() {
        return invite_only;
    }

    public void setInvite_only(String invite_only) {
        this.invite_only = invite_only;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAudience() {
        return audience;
    }

    public void setAudience(String audience) {
        this.audience = audience;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getCustom5() {
        return custom5;
    }

    public void setCustom5(String custom5) {
        this.custom5 = custom5;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVenue_id() {
        return venue_id;
    }

    public void setVenue_id(String venue_id) {
        this.venue_id = venue_id;
    }

    public ArrayList<Artist> getArtists() {
        return artists;
    }

    public void setArtists(ArrayList<Artist> artists) {
        this.artists = artists;
    }

    public ArrayList<Speaker> getSpeakers() {
        return speakers;
    }

    public void setSpeakers(ArrayList<Speaker> speakers) {
        this.speakers = speakers;
    }

    public ArrayList<Volunteer> getVolunteers() {
        return volunteers;
    }

    public void setVolunteers(ArrayList<Volunteer> volunteers) {
        this.volunteers = volunteers;
    }

    public String getEvent_start_year() {
        return event_start_year;
    }

    public void setEvent_start_year(String event_start_year) {
        this.event_start_year = event_start_year;
    }

    public String getEvent_start_month() {
        return event_start_month;
    }

    public void setEvent_start_month(String event_start_month) {
        this.event_start_month = event_start_month;
    }

    public String getEvent_start_month_short() {
        return event_start_month_short;
    }

    public void setEvent_start_month_short(String event_start_month_short) {
        this.event_start_month_short = event_start_month_short;
    }

    public String getEvent_start_day() {
        return event_start_day;
    }

    public void setEvent_start_day(String event_start_day) {
        this.event_start_day = event_start_day;
    }

    public String getEvent_start_weekday() {
        return event_start_weekday;
    }

    public void setEvent_start_weekday(String event_start_weekday) {
        this.event_start_weekday = event_start_weekday;
    }

    public String getEvent_start_weekday_short() {
        return event_start_weekday_short;
    }

    public void setEvent_start_weekday_short(String event_start_weekday_short) {
        this.event_start_weekday_short = event_start_weekday_short;
    }

    public String getEvent_start_time() {
        return event_start_time;
    }

    public void setEvent_start_time(String event_start_time) {
        this.event_start_time = event_start_time;
    }

    public String getEvent_end_year() {
        return event_end_year;
    }

    public void setEvent_end_year(String event_end_year) {
        this.event_end_year = event_end_year;
    }

    public String getEvent_end_month() {
        return event_end_month;
    }

    public void setEvent_end_month(String event_end_month) {
        this.event_end_month = event_end_month;
    }

    public String getEvent_end_month_short() {
        return event_end_month_short;
    }

    public void setEvent_end_month_short(String event_end_month_short) {
        this.event_end_month_short = event_end_month_short;
    }

    public String getEvent_end_day() {
        return event_end_day;
    }

    public void setEvent_end_day(String event_end_day) {
        this.event_end_day = event_end_day;
    }

    public String getEvent_end_weekday() {
        return event_end_weekday;
    }

    public void setEvent_end_weekday(String event_end_weekday) {
        this.event_end_weekday = event_end_weekday;
    }

    public String getEvent_end_weekday_short() {
        return event_end_weekday_short;
    }

    public void setEvent_end_weekday_short(String event_end_weekday_short) {
        this.event_end_weekday_short = event_end_weekday_short;
    }

    public String getEvent_end_time() {
        return event_end_time;
    }

    public void setEvent_end_time(String event_end_time) {
        this.event_end_time = event_end_time;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getStart_time_ts() {
        return start_time_ts;
    }

    public void setStart_time_ts(String start_time_ts) {
        this.start_time_ts = start_time_ts;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getEvent_type_sort() {
        return event_type_sort;
    }

    public void setEvent_type_sort(String event_type_sort) {
        this.event_type_sort = event_type_sort;
    }

    public String getSeats_status() {
        return seats_status;
    }

    public void setSeats_status(String seats_status) {
        this.seats_status = seats_status;
    }

    public String getSeats_title() {
        return seats_title;
    }

    public void setSeats_title(String seats_title) {
        this.seats_title = seats_title;
    }



    //TODO
    public String toJSON() {
//
        return "";
    }
}