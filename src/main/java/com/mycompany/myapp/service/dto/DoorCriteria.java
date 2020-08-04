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
 * Criteria class for the {@link com.mycompany.myapp.domain.Door} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.DoorResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /doors?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DoorCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter libelleDoor;

    public DoorCriteria() {
    }

    public DoorCriteria(DoorCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.libelleDoor = other.libelleDoor == null ? null : other.libelleDoor.copy();
    }

    @Override
    public DoorCriteria copy() {
        return new DoorCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getLibelleDoor() {
        return libelleDoor;
    }

    public void setLibelleDoor(StringFilter libelleDoor) {
        this.libelleDoor = libelleDoor;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DoorCriteria that = (DoorCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(libelleDoor, that.libelleDoor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        libelleDoor
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DoorCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (libelleDoor != null ? "libelleDoor=" + libelleDoor + ", " : "") +
            "}";
    }

}
