package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A LivingRoom.
 */
@Entity
@Table(name = "living_room")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class LivingRoom implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "libelle_living_room")
    private String libelleLivingRoom;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelleLivingRoom() {
        return libelleLivingRoom;
    }

    public LivingRoom libelleLivingRoom(String libelleLivingRoom) {
        this.libelleLivingRoom = libelleLivingRoom;
        return this;
    }

    public void setLibelleLivingRoom(String libelleLivingRoom) {
        this.libelleLivingRoom = libelleLivingRoom;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LivingRoom)) {
            return false;
        }
        return id != null && id.equals(((LivingRoom) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LivingRoom{" +
            "id=" + getId() +
            ", libelleLivingRoom='" + getLibelleLivingRoom() + "'" +
            "}";
    }
}
