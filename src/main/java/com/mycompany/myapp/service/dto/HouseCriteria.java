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
 * Criteria class for the {@link com.mycompany.myapp.domain.House} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.HouseResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /houses?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class HouseCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter libelleHouse;

    private LongFilter userId;

    private LongFilter livingroomsId;

    private LongFilter doorsId;

    private LongFilter bathroomsId;

    private LongFilter kitchensId;

    private LongFilter roomsId;

    public HouseCriteria() {
    }

    public HouseCriteria(HouseCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.libelleHouse = other.libelleHouse == null ? null : other.libelleHouse.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.livingroomsId = other.livingroomsId == null ? null : other.livingroomsId.copy();
        this.doorsId = other.doorsId == null ? null : other.doorsId.copy();
        this.bathroomsId = other.bathroomsId == null ? null : other.bathroomsId.copy();
        this.kitchensId = other.kitchensId == null ? null : other.kitchensId.copy();
        this.roomsId = other.roomsId == null ? null : other.roomsId.copy();
    }

    @Override
    public HouseCriteria copy() {
        return new HouseCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getLibelleHouse() {
        return libelleHouse;
    }

    public void setLibelleHouse(StringFilter libelleHouse) {
        this.libelleHouse = libelleHouse;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    public LongFilter getLivingroomsId() {
        return livingroomsId;
    }

    public void setLivingroomsId(LongFilter livingroomsId) {
        this.livingroomsId = livingroomsId;
    }

    public LongFilter getDoorsId() {
        return doorsId;
    }

    public void setDoorsId(LongFilter doorsId) {
        this.doorsId = doorsId;
    }

    public LongFilter getBathroomsId() {
        return bathroomsId;
    }

    public void setBathroomsId(LongFilter bathroomsId) {
        this.bathroomsId = bathroomsId;
    }

    public LongFilter getKitchensId() {
        return kitchensId;
    }

    public void setKitchensId(LongFilter kitchensId) {
        this.kitchensId = kitchensId;
    }

    public LongFilter getRoomsId() {
        return roomsId;
    }

    public void setRoomsId(LongFilter roomsId) {
        this.roomsId = roomsId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final HouseCriteria that = (HouseCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(libelleHouse, that.libelleHouse) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(livingroomsId, that.livingroomsId) &&
            Objects.equals(doorsId, that.doorsId) &&
            Objects.equals(bathroomsId, that.bathroomsId) &&
            Objects.equals(kitchensId, that.kitchensId) &&
            Objects.equals(roomsId, that.roomsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        libelleHouse,
        userId,
        livingroomsId,
        doorsId,
        bathroomsId,
        kitchensId,
        roomsId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HouseCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (libelleHouse != null ? "libelleHouse=" + libelleHouse + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
                (livingroomsId != null ? "livingroomsId=" + livingroomsId + ", " : "") +
                (doorsId != null ? "doorsId=" + doorsId + ", " : "") +
                (bathroomsId != null ? "bathroomsId=" + bathroomsId + ", " : "") +
                (kitchensId != null ? "kitchensId=" + kitchensId + ", " : "") +
                (roomsId != null ? "roomsId=" + roomsId + ", " : "") +
            "}";
    }

}
