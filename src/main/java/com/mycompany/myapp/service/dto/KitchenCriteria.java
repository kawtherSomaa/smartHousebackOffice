package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.Kitchen} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.KitchenResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /kitchens?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class KitchenCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter libelleKitchen;

    public KitchenCriteria() {
    }

    public KitchenCriteria(KitchenCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.libelleKitchen = other.libelleKitchen == null ? null : other.libelleKitchen.copy();
    }

    @Override
    public KitchenCriteria copy() {
        return new KitchenCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getLibelleKitchen() {
        return libelleKitchen;
    }

    public void setLibelleKitchen(StringFilter libelleKitchen) {
        this.libelleKitchen = libelleKitchen;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final KitchenCriteria that = (KitchenCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(libelleKitchen, that.libelleKitchen);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        libelleKitchen
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KitchenCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (libelleKitchen != null ? "libelleKitchen=" + libelleKitchen + ", " : "") +
            "}";
    }

}
