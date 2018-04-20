package com.vids.paulturner.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@JsonDeserialize(builder = Customer.Builder.class)
public class Customer {

    public static final String TO_STRING_MASK = "Customer [id=%d] [name=%s] [points=%d]";

    private final Integer id;

    private final String name;

    private final Integer points;

    private final List<RentedVideo> rentedVideos;

    private Customer(@JsonProperty final Integer id, @JsonProperty final String name, @JsonProperty final Integer points) {
        this(id, name, points, new ArrayList<>());
    }

    private Customer(final Integer id, final String name, final Integer points, final List<RentedVideo> inRentedVideos) {
        this.id = id;
        this.name = name;
        this.points = points;
        this.rentedVideos = inRentedVideos;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getPoints() {
        return points;
    }

    public List<RentedVideo> getRentedVideos() {
        return rentedVideos;
    }

    public String toString() {
        return String.format(TO_STRING_MASK, id, name, points);
    }

    @JsonPOJOBuilder(buildMethodName = "build")
    public static final class Builder {

        private Integer id;

        private String name;

        private Integer points;

        private List<RentedVideo> rentedVideos = new ArrayList<>();

        public Builder withCustomer(Customer c) {
            this.id = c.id;
            this.name = c.name;
            this.points = c.points;
            this.rentedVideos = Collections.unmodifiableList(c.rentedVideos);
            return this;
        }

        public Builder withId(Integer inId) {
            this.id = inId;
            return this;
        }

        public Builder withName(String inName) {
            this.name = inName;
            return this;
        }

        public Builder withPoints(Integer inPoints) {
            this.points = inPoints;
            return this;
        }

        public Builder incrementPoints(Integer increment) {
            this.points = this.points + increment;
            return this;
        }

        public Builder withRentedVideos(List<RentedVideo> inRentedVideos) {
            this.rentedVideos = inRentedVideos;
            return this;
        }

        public Customer build() {
            return new Customer(id, name, points, rentedVideos);
        }
    }
}
