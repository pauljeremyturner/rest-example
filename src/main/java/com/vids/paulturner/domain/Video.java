package com.vids.paulturner.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = Video.Builder.class)
public class Video {

    private static final String TO_STRING_MASK = "Video [id=%d] [title=%s] [type=%s]";

    private final Integer id;

    private final String title;

    private final VideoType type;

    private Video(final Integer inId, final String inTitle, final VideoType inType) {
        this.id = inId;
        this.title = inTitle;
        this.type = inType;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public VideoType getType() {
        return type;
    }

    public String toString() {
        return String.format(TO_STRING_MASK, id, title, type);
    }

    @JsonPOJOBuilder(buildMethodName = "build")
    public static final class Builder {

        private Integer id;

        private String title;

        private VideoType type;

        public Builder withId(Integer inId) {
            this.id = inId;
            return this;
        }

        public Builder withTitle(String inTitle) {
            this.title = inTitle;
            return this;
        }

        public Builder withType(VideoType inVideoType) {
            this.type = inVideoType;
            return this;
        }

        public Video build() {
            return new Video(id, title, type);
        }
    }
}
