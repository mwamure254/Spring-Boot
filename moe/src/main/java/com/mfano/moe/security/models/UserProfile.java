package com.mfano.moe.security.models;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.mfano.moe.security.Auditable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class UserProfile extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String fname;
    private String lname;
    private String oname;
    private String title;
    private String initials;
    private String nid;
    private String gender;
    private String tscnumber;

    @ManyToOne
    @JoinColumn(name = "stationid", insertable = false, updatable = false)
    private Station station;
    private Integer stationid;

    @ManyToOne
    @JoinColumn(name = "userid", insertable = false, updatable = false)
    private User user;
    private Integer userid;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;
    private String city;
    private String address;
    private String phone;
    private String mobile;
    private String email;
    private String photo;

    public UserProfile(Integer id, String fname, String lname, String oname, String title, String initials, String nid,
            String gender, String tscnumber, Station station, Integer stationid, User user, Integer userid,
            LocalDate dateOfBirth, String city, String address, String phone, String mobile, String email,
            String photo) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.oname = oname;
        this.title = title;
        this.initials = initials;
        this.nid = nid;
        this.gender = gender;
        this.tscnumber = tscnumber;
        this.station = station;
        this.stationid = stationid;
        this.user = user;
        this.userid = userid;
        this.dateOfBirth = dateOfBirth;
        this.city = city;
        this.address = address;
        this.phone = phone;
        this.mobile = mobile;
        this.email = email;
        this.photo = photo;
    }

    public UserProfile() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getOname() {
        return oname;
    }

    public void setOname(String oname) {
        this.oname = oname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTscnumber() {
        return tscnumber;
    }

    public void setTscnumber(String tscnumber) {
        this.tscnumber = tscnumber;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public Integer getStationid() {
        return stationid;
    }

    public void setStationid(Integer stationid) {
        this.stationid = stationid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

}
