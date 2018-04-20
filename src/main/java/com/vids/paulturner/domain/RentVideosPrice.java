package com.vids.paulturner.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.Collections;
import java.util.List;

@JsonDeserialize(builder = RentVideosPrice.Builder.class)
public class RentVideosPrice {

    private static final String TO_STRING_MASK = "RentVideoPrice [dayCount=%d] [points=%d] [videos=%s] [price=%d]";

    private final Integer dayCount;
    private final Integer points;
    private final List<Video> videos;
    private Integer price;

    private RentVideosPrice(final Integer inPrice, final Integer inTotalPoints, final List<Video> inVideos, final Integer inDayCount) {
        this.price = inPrice;
        this.points = inTotalPoints;
        this.videos = inVideos;
        this.dayCount = inDayCount;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getPoints() {
        return points;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public Integer getDayCount() {
        return dayCount;
    }

    public static Builder builder() {
        return new Builder();
    }


    public String toString() {
        return String.format(TO_STRING_MASK, dayCount, points, videos, price);
    }

    @JsonPOJOBuilder(buildMethodName = "build")
    public static final class Builder {

        private Integer dayCount;
        private Integer points;
        private List<Video> videos;
        private Integer price;

        public Builder withDayCount(Integer inDayCount) {
            this.dayCount = inDayCount;
            return this;
        }

        public Builder withPoints(Integer inPoints) {
            this.points = inPoints;
            return this;
        }

        public Builder withVideos(List<Video> inVideos) {
            this.videos = Collections.unmodifiableList(inVideos);
            return this;
        }

        public Builder withPrice(Integer inPrice) {
            this.price = inPrice;
            return this;
        }

        public RentVideosPrice build() {
            return new RentVideosPrice(price, points, videos, dayCount);
        }
    }

}
