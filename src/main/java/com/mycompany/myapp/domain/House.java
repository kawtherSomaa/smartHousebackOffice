package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * The House entity.
 */
@Entity
@Table(name = "house")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class House implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The firstname attribute.
     */
    @Column(name = "libelle_house")
    private String libelleHouse;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @OneToOne
    @JoinColumn(unique = true)
    private LivingRoom livingrooms;

    @OneToOne
    @JoinColumn(unique = true)
    private Door doors;

    @OneToOne
    @JoinColumn(unique = true)
    private BathRoom bathrooms;

    @OneToOne
    @JoinColumn(unique = true)
    private Kitchen kitchens;

    @OneToOne
    @JoinColumn(unique = true)
    private Room rooms;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelleHouse() {
        return libelleHouse;
    }

    public House libelleHouse(String libelleHouse) {
        this.libelleHouse = libelleHouse;
        return this;
    }

    public void setLibelleHouse(String libelleHouse) {
        this.libelleHouse = libelleHouse;
    }

    public User getUser() {
        return user;
    }

    public House user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LivingRoom getLivingrooms() {
        return livingrooms;
    }

    public House livingrooms(LivingRoom livingRoom) {
        this.livingrooms = livingRoom;
        return this;
    }

    public void setLivingrooms(LivingRoom livingRoom) {
        this.livingrooms = livingRoom;
    }

    public Door getDoors() {
        return doors;
    }

    public House doors(Door door) {
        this.doors = door;
        return this;
    }

    public void setDoors(Door door) {
        this.doors = door;
    }

    public BathRoom getBathrooms() {
        return bathrooms;
    }

    public House bathrooms(BathRoom bathRoom) {
        this.bathrooms = bathRoom;
        return this;
    }

    public void setBathrooms(BathRoom bathRoom) {
        this.bathrooms = bathRoom;
    }

    public Kitchen getKitchens() {
        return kitchens;
    }

    public House kitchens(Kitchen kitchen) {
        this.kitchens = kitchen;
        return this;
    }

    public void setKitchens(Kitchen kitchen) {
        this.kitchens = kitchen;
    }

    public Room getRooms() {
        return rooms;
    }

    public House rooms(Room room) {
        this.rooms = room;
        return this;
    }

    public void setRooms(Room room) {
        this.rooms = room;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof House)) {
            return false;
        }
        return id != null && id.equals(((House) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "House{" +
            "id=" + getId() +
            ", libelleHouse='" + getLibelleHouse() + "'" +
            "}";
    }
}
