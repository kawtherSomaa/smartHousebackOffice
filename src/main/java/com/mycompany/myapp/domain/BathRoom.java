package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * not an ignored comment
 */
@Entity
@Table(name = "bath_room")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BathRoom implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "libelle_bath_room")
    private String libelleBathRoom;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelleBathRoom() {
        return libelleBathRoom;
    }

    public BathRoom libelleBathRoom(String libelleBathRoom) {
        this.libelleBathRoom = libelleBathRoom;
        return this;
    }

    public void setLibelleBathRoom(String libelleBathRoom) {
        this.libelleBathRoom = libelleBathRoom;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BathRoom)) {
            return false;
        }
        return id != null && id.equals(((BathRoom) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BathRoom{" +
            "id=" + getId() +
            ", libelleBathRoom='" + getLibelleBathRoom() + "'" +
            "}";
    }
}
