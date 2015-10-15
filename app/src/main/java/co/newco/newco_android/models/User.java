package co.newco.newco_android.models;

import java.util.List;

/**
 * Created by jayd on 10/15/15.
 */
public class User {
    private String username;
    private String name;
    private String email;
    private String twitter_uid;

    private String fb_uid;
    private String position;
    private String location;
    private String company;
    private String privacy_mode;
    private List<Ticket> tickets;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTwitter_uid() {
        return twitter_uid;
    }

    public void setTwitter_uid(String twitter_uid) {
        this.twitter_uid = twitter_uid;
    }

    public String getFb_uid() {
        return fb_uid;
    }

    public void setFb_uid(String fb_uid) {
        this.fb_uid = fb_uid;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPrivacy_mode() {
        return privacy_mode;
    }

    public void setPrivacy_mode(String privacy_mode) {
        this.privacy_mode = privacy_mode;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }


}
