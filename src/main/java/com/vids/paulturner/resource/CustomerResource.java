package com.vids.paulturner.resource;

import com.vids.paulturner.domain.Customer;
import com.vids.paulturner.domain.RentVideosBasket;
import com.vids.paulturner.domain.RentedVideo;
import com.vids.paulturner.domain.VideoType;
import com.vids.paulturner.repository.CustomerRepository;
import com.vids.paulturner.repository.VideoRepository;
import com.vids.paulturner.service.PriceCalculator;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/customers")
public class CustomerResource {

    private final CustomerRepository customerRepository;

    private final VideoRepository videoRepository;

    private final PriceCalculator priceCalculator;

    public CustomerResource(CustomerRepository inCr, VideoRepository inVr, PriceCalculator inRdr) {
        this.customerRepository = inCr;
        this.videoRepository = inVr;
        this.priceCalculator = inRdr;
    }

    @POST
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Customer rentVideos(@PathParam("id") final Integer id, RentVideosBasket basket) {
        final LocalDate dueDate = basket.getStartDateTime().plusDays(basket.getRentalDays());
        final List<RentedVideo> rentedVideos = basket.getVideoIds().stream().map(videoId -> new RentedVideo(videoRepository.getVideo(videoId), dueDate)).collect(Collectors.toList());
        int points = 0;
        for (Integer videoId : basket.getVideoIds()) {
            VideoType type = videoRepository.getVideo(videoId).getType();
            points = points + priceCalculator.getBonusPointsPerRental(type);
        }
        return customerRepository.rentVideos(id, rentedVideos, points);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Customer byId(@PathParam("id") final Integer id) {
        return customerRepository.getCustomer(id);
    }

}
