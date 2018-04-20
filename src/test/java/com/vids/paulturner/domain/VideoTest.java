package com.vids.paulturner.domain;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

public class VideoTest {

    @Test
    public void shouldStoreId() throws Exception {
        Assert.assertThat(Video.builder().withId(123).build().getId(), Matchers.is(123));
    }

    @Test
    public void shouldStoreTitle() throws Exception {
        Assert.assertThat(Video.builder().withTitle("title").build().getTitle(), Matchers.is("title"));
    }

    @Test
    public void shouldStoreType() throws Exception {
        Assert.assertThat(Video.builder().withType(VideoType.Regular).build().getType(), Matchers.is(VideoType.Regular));
    }

}
