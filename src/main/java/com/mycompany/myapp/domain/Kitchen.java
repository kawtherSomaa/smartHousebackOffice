package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Kitchen.
 */
@Entity
@Table(name = "kitchen")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Kitchen implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "libelle_kitchen")
    private String libelleKitchen;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelleKitchen() {
        return libelleKitchen;
    }

    public Kitchen libelleKitchen(String libelleKitchen) {
        this.libelleKitchen = libelleKitchen;
        return this;
    }

    public void setLibelleKitchen(String libelleKitchen) {
        this.libelleKitchen = libelleKitchen;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Kitchen)) {
            return false;
        }
        return id != null && id.equals(((Kitchen) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Kitchen{" +
            "id=" + getId() +
            ", libelleKitchen='" + getLibelleKitchen() + "'" +
            "}";
    }
}
