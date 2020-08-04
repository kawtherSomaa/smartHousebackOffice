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
 * Criteria class for the {@link com.mycompany.myapp.domain.LivingRoom} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.LivingRoomResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /living-rooms?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class LivingRoomCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter libelleLivingRoom;

    public LivingRoomCriteria() {
    }

    public LivingRoomCriteria(LivingRoomCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.libelleLivingRoom = other.libelleLivingRoom == null ? null : other.libelleLivingRoom.copy();
    }

    @Override
    public LivingRoomCriteria copy() {
        return new LivingRoomCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getLibelleLivingRoom() {
        return libelleLivingRoom;
    }

    public void setLibelleLivingRoom(StringFilter libelleLivingRoom) {
        this.libelleLivingRoom = libelleLivingRoom;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final LivingRoomCriteria that = (LivingRoomCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(libelleLivingRoom, that.libelleLivingRoom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        libelleLivingRoom
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LivingRoomCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (libelleLivingRoom != null ? "libelleLivingRoom=" + libelleLivingRoom + ", " : "") +
            "}";
    }

}
