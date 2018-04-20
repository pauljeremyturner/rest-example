package com.vids.paulturner;

import com.vids.paulturner.domain.Customer;
import com.vids.paulturner.domain.RentedVideo;
import com.google.common.base.Charsets;
import io.dropwizard.testing.junit.DropwizardAppRule;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Iterator;
import javax.ws.rs.client.Entity;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

public class CustomersIT {

    @ClassRule
    public static final DropwizardAppRule<VideosConfiguration> RULE = new DropwizardAppRule<>(
        VideosApplication.class, "integration-test.yaml");

    @Test
    public void testGetCustomer() throws Exception {
        final Customer alice = RULE.client().target("http://localhost:" + RULE.getLocalPort() + "/customers/1")
            .request()
            .get()
            .readEntity(Customer.class);
        Assert.assertThat(alice.getName(), Matchers.is("Alice"));
        Assert.assertThat(alice.getId(), Matchers.is(1));
    }

    @Test
    public void testRentVideos() throws Exception {
        URI uri = ClassLoader.getSystemResource("rentVideosRequest.json").toURI();
        String postBody = new String(Files.readAllBytes(Paths.get(uri)), Charsets.UTF_8);
        final Customer alice = RULE.client().target("http://localhost:" + RULE.getLocalPort() + "/customers/1")
            .request()
            .header("Content-Type", "application/json")
            .post(Entity.json(postBody))
            .readEntity(Customer.class);

        Assert.assertThat(alice.getId(), Matchers.is(1));
        Assert.assertThat(alice.getRentedVideos().size(), Matchers.is(3));
        Iterator<RentedVideo> actualVideos = alice.getRentedVideos().iterator();
        Assert.assertThat(actualVideos.next().getVideo().getTitle(), Matchers.is("The Shawshank Redemption"));
        Assert.assertThat(actualVideos.next().getVideo().getTitle(), Matchers.is("The Godfather"));
        Assert.assertThat(actualVideos.next().getDueDate(), Matchers.is(LocalDate.of(2017, 8, 8)));

    }

}