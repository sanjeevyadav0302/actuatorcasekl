package com.afkl.travel.exercise.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Location {
    private int id;
    private String code;
    private String type;
    private Double longitude;
    private Double latitude;
    private Location locationByParentId;

    @Id
    @Column(name = "ID")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "CODE" )
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    @Column(name = "TYPE")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    @Column(name = "LONGITUDE")
    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }


    @Column(name = "LATITUDE")
    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return id == location.id &&
                Objects.equals(code, location.code) &&
                Objects.equals(type, location.type) &&
                Objects.equals(longitude, location.longitude) &&
                Objects.equals(latitude, location.latitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, type, longitude, latitude);
    }

    @ManyToOne
    @JoinColumn(name = "PARENT_ID", referencedColumnName = "ID")
    public Location getLocationByParentId() {
        return locationByParentId;
    }

    public void setLocationByParentId(Location locationByParentId) {
        this.locationByParentId = locationByParentId;
    }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", type='" + type + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", locationByParentId=" + locationByParentId +
                '}';
    }
}
