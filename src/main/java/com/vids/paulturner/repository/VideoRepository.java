package com.vids.paulturner.repository;

import com.vids.paulturner.domain.Video;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.ws.rs.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VideoRepository {

    private static final String IMDB_TOP_50_JSON = "/imdb-top-50.json";

    private static final Logger LOGGER = LoggerFactory.getLogger(VideoRepository.class);

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Map<Integer, Video> videos;

    public VideoRepository() {
        try (InputStream is = VideoRepository.class.getResourceAsStream(IMDB_TOP_50_JSON)) {
            List<Video> tmpvideos =
                objectMapper.readValue(is, objectMapper.getTypeFactory().constructCollectionType(List.class, Video.class));
            videos = Collections.unmodifiableMap(tmpvideos.stream().collect(Collectors.toMap(Video::getId, v -> v)));
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    public Video getVideo(Integer id) {
        Video video = videos.get(id);
        if (Objects.isNull(video)) {
            throw new NotFoundException();
        }
        LOGGER.info("Get Video [id={}] [video={}]", id, video);
        return video;
    }

}
