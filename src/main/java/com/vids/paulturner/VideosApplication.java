package com.vids.paulturner;

import com.vids.paulturner.repository.CustomerRepository;
import com.vids.paulturner.repository.VideoRepository;
import com.vids.paulturner.resource.CustomerResource;
import com.vids.paulturner.resource.VideoPriceResource;
import com.vids.paulturner.service.PriceCalculator;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class VideosApplication extends Application<VideosConfiguration> {

    public static void main(String[] args) throws Exception {
        new VideosApplication().run(args);
    }

    @Override
    public String getName() {
        return "videos";
    }

    @Override
    public void initialize(Bootstrap<VideosConfiguration> bootstrap) {
    }

    @Override
    public void run(VideosConfiguration configuration,
        Environment environment) {
        final VideoRepository videoRepository = new VideoRepository();
        final CustomerRepository customerRepository = new CustomerRepository();
        final PriceCalculator priceCalculator = new PriceCalculator();
        final VideoPriceResource videoPriceResource = new VideoPriceResource(videoRepository, priceCalculator);
        environment.jersey().register(videoPriceResource);
        final CustomerResource customerResource = new CustomerResource(customerRepository, videoRepository, priceCalculator);
        environment.jersey().register(customerResource);
    }

}
