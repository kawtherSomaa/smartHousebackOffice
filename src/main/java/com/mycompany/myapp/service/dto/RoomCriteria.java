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
 * Criteria class for the {@link com.mycompany.myapp.domain.Room} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.RoomResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /rooms?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class RoomCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter libelleRoom;

    public RoomCriteria() {
    }

    public RoomCriteria(RoomCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.libelleRoom = other.libelleRoom == null ? null : other.libelleRoom.copy();
    }

    @Override
    public RoomCriteria copy() {
        return new RoomCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getLibelleRoom() {
        return libelleRoom;
    }

    public void setLibelleRoom(StringFilter libelleRoom) {
        this.libelleRoom = libelleRoom;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final RoomCriteria that = (RoomCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(libelleRoom, that.libelleRoom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        libelleRoom
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RoomCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (libelleRoom != null ? "libelleRoom=" + libelleRoom + ", " : "") +
            "}";
    }

}
