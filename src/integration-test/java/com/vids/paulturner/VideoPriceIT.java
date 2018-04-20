package com.vids.paulturner;

import com.vids.paulturner.domain.RentVideosPrice;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

public class VideoPriceIT {

    @ClassRule
    public static final DropwizardAppRule<VideosConfiguration> RULE = new DropwizardAppRule<>(
        VideosApplication.class, "integration-test.yaml");

    @Test
    public void testGetVideo() throws Exception {
        final RentVideosPrice price = RULE.client().target("http://localhost:" + RULE.getLocalPort() + "/videoPrice?dayCount=3&videoIds=22,23")
            .request()
            .get()
            .readEntity(RentVideosPrice.class);

        Assert.assertThat(price.getPrice(), Matchers.is(150));
        Assert.assertThat(price.getPoints(), Matchers.is(3));
    }

}