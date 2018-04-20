package com.vids.paulturner.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import java.time.LocalDate;

public class RentedVideo {

    private static final String TO_STRING_MASK = "RentedVideo [video=%s] [dueDateTime=%s]";

    private final Video video;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    private final LocalDate dueDate;

    @JsonCreator
    public RentedVideo(@JsonProperty("video") Video inVideo, @JsonProperty("dueDateTime") LocalDate dueDate) {
        this.video = inVideo;
        this.dueDate = dueDate;
    }

    public Video getVideo() {
        return video;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    @Override
    public String toString() {
        return String.format(TO_STRING_MASK, video, dueDate);
    }
}
