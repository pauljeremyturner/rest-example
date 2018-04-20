package com.vids.paulturner.domain;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@JsonDeserialize(builder = RentVideosBasket.Builder.class)
public class RentVideosBasket {

    @JsonDeserialize(using = LocalDateDeserializer.class)
    private final LocalDate date;

    private final Integer rentalDays;

    private final List<Integer> videoIds;


    public RentVideosBasket(LocalDate date, Integer rentalDays, List<Integer> videoIds) {
        this.date = date;
        this.rentalDays = rentalDays;
        this.videoIds = videoIds;
    }

    public LocalDate getStartDateTime() {
        return date;
    }

    public Integer getRentalDays() {
        return rentalDays;
    }


    public List<Integer> getVideoIds() {
        return videoIds;
    }

    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder(buildMethodName = "build")
    public static class Builder {


        private LocalDate date;

        private Integer rentalDays;

        private List<Integer> videoIds;

        public Builder withRentalDays(Integer inRentalDays) {
            this.rentalDays = inRentalDays;
            return this;
        }

        public Builder withDate(LocalDate inDate) {
            this.date = inDate;
            return this;
        }

        public Builder withVideoIds(List<Integer> inVideoIds) {
            this.videoIds = Collections.unmodifiableList(inVideoIds);
            return this;
        }

        public RentVideosBasket build() {
            return new RentVideosBasket(date, rentalDays, videoIds);
        }

    }

}
