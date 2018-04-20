package com.vids.paulturner.resource;

import com.vids.paulturner.domain.RentVideosPrice;
import com.vids.paulturner.domain.Video;
import com.vids.paulturner.repository.VideoRepository;
import com.vids.paulturner.service.PriceCalculator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/videoPrice")
public class VideoPriceResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(VideoPriceResource.class);

    private final VideoRepository videoRepository;

    private final PriceCalculator priceCalculator;

    public VideoPriceResource(VideoRepository inVr, PriceCalculator inRdr) {
        this.videoRepository = inVr;
        this.priceCalculator = inRdr;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public RentVideosPrice rentalPrice(
        @QueryParam("videoIds") final String videoIds,
        @QueryParam("dayCount") final Integer dayCount
    ) {
        parameterCheck(videoIds, dayCount);
        int totalPrice = 0;
        int totalPoints = 0;
        List<Video> videos = new ArrayList<>();
        for (Integer videoId : getVideoIds(videoIds)) {
            Video video = videoRepository.getVideo(videoId);
            videos.add(video);
            int videoPrice = priceCalculator.price(video.getType(), dayCount);
            totalPrice += videoPrice;
            totalPoints += priceCalculator.getBonusPointsPerRental(video.getType());
        }
        return RentVideosPrice.
            builder().
            withPrice(totalPrice).
            withPoints(totalPoints).
            withVideos(videos).
            withDayCount(dayCount).
            build();
    }

    private void parameterCheck(final String videoIds, final Integer dayCount) {
        if (StringUtils.isBlank(videoIds)) {
            throw new BadRequestException("videoIds mandatory parameter");
        }
        if (Objects.isNull(dayCount)) {
            throw new BadRequestException("dayCount is mandatory parameter");
        }
        if (dayCount < 1) {
            throw new BadRequestException("dayCount cannot be negative");
        }
    }

    private List<Integer> getVideoIds(String csv) {
        try {
            return Arrays.stream(csv.split(",")).map(s -> Integer.valueOf(s)).collect(Collectors.toList());
        } catch (NumberFormatException nfe) {
            throw new BadRequestException(nfe);
        }
    }

}
