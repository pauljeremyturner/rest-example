package com.vids.paulturner.repository;

import com.vids.paulturner.domain.Video;
import com.vids.paulturner.domain.VideoType;
import javax.ws.rs.NotFoundException;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

public class VideoRepositoryTest {

    @Test
    public void shouldGetVideoWithCorrectProperties() throws Exception {
        VideoRepository repository = new VideoRepository();
        Video video = repository.getVideo(14);
        Assert.assertThat(video.getTitle(), Matchers.is("Star Wars: Episode V - The Empire Strikes Back"));
        Assert.assertThat(video.getType(), Matchers.is(VideoType.OldFilm));
    }

    @Test(expected = NotFoundException.class)
    public void shouldThrowNotFoundExceptionWhenNotFound() throws Exception {
        VideoRepository repository = new VideoRepository();
        repository.getVideo(Integer.MIN_VALUE);
    }

}