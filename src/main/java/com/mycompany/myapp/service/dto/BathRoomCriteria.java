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
 * Criteria class for the {@link com.mycompany.myapp.domain.BathRoom} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.BathRoomResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /bath-rooms?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class BathRoomCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter libelleBathRoom;

    public BathRoomCriteria() {
    }

    public BathRoomCriteria(BathRoomCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.libelleBathRoom = other.libelleBathRoom == null ? null : other.libelleBathRoom.copy();
    }

    @Override
    public BathRoomCriteria copy() {
        return new BathRoomCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getLibelleBathRoom() {
        return libelleBathRoom;
    }

    public void setLibelleBathRoom(StringFilter libelleBathRoom) {
        this.libelleBathRoom = libelleBathRoom;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final BathRoomCriteria that = (BathRoomCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(libelleBathRoom, that.libelleBathRoom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        libelleBathRoom
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BathRoomCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (libelleBathRoom != null ? "libelleBathRoom=" + libelleBathRoom + ", " : "") +
            "}";
    }

}
