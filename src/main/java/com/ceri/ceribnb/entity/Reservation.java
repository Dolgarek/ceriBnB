package com.ceri.ceribnb.entity;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.util.Objects;

public class Reservation {
    private ObjectId objectId;
    private ObjectId sejourId;
    private ObjectId userId;
    private String status;

    public ObjectId getObjectId() {
        return objectId;
    }

    public void setObjectId(ObjectId objectId) {
        this.objectId = objectId;
    }

    public ObjectId getSejourId() {
        return sejourId;
    }

    public void setSejourId(ObjectId sejourId) {
        this.sejourId = sejourId;
    }

    public ObjectId getUserId() {
        return userId;
    }

    public void setUserId(ObjectId userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(getObjectId(), that.getObjectId()) && Objects.equals(getSejourId(), that.getSejourId()) && Objects.equals(getUserId(), that.getUserId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getObjectId(), getSejourId(), getUserId());
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "objectId=" + objectId +
                ", sejourId=" + sejourId +
                ", userId=" + userId +
                ", status='" + status + '\'' +
                '}';
    }
}
