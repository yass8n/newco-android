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
    private String lastactive;
    private String url;
    private String about;
    private String role;
    private String phone;
    private String avatar;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLastactive() {
        return lastactive;
    }

    public void setLastactive(String lastactive) {
        this.lastactive = lastactive;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }


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
