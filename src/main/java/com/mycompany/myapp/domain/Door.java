package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Door.
 */
@Entity
@Table(name = "door")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Door implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "libelle_door")
    private String libelleDoor;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelleDoor() {
        return libelleDoor;
    }

    public Door libelleDoor(String libelleDoor) {
        this.libelleDoor = libelleDoor;
        return this;
    }

    public void setLibelleDoor(String libelleDoor) {
        this.libelleDoor = libelleDoor;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Door)) {
            return false;
        }
        return id != null && id.equals(((Door) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Door{" +
            "id=" + getId() +
            ", libelleDoor='" + getLibelleDoor() + "'" +
            "}";
    }
}
