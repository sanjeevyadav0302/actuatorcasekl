package com.afkl.travel.exercise.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Translation {

    private int id;
    private String language;
    private String name;
    private String description;
    private Location locationByLocation;

    @Id
    @Column(name = "ID")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Column(name = "LANGUAGE")
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }


    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Translation that = (Translation) o;
        return id == that.id &&
                Objects.equals(language, that.language) &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, language, name, description);
    }

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "LOCATION", referencedColumnName = "ID", nullable = false)
    public Location getLocationByLocation() {
        return locationByLocation;
    }

    public void setLocationByLocation(Location locationByLocation) {
        this.locationByLocation = locationByLocation;
    }

    @Override
    public String toString() {
        return "Translation{" +
                "id=" + id +
                ", language='" + language + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", locationByLocation=" + locationByLocation +
                '}';
    }
}
