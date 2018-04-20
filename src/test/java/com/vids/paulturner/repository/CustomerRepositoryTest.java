package com.vids.paulturner.repository;

import com.vids.paulturner.domain.Customer;
import com.vids.paulturner.domain.RentedVideo;
import com.vids.paulturner.domain.Video;
import com.vids.paulturner.domain.VideoType;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import javax.ws.rs.NotFoundException;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

public class CustomerRepositoryTest {
    @Test
    public void shouldGetCustomerWithCorrectProperties() throws Exception {
        CustomerRepository repository = new CustomerRepository();
        Customer customer = repository.getCustomer(1);
        Assert.assertThat(customer.getName(), Matchers.is("Alice"));
        Assert.assertThat(customer.getId(), Matchers.is(1));
    }

    @Test(expected = NotFoundException.class)
    public void shouldThrowNotFoundExceptionWhenNotFound() throws Exception {
        CustomerRepository repository = new CustomerRepository();
        repository.getCustomer(Integer.MIN_VALUE);
    }

    @Test
    public void shouldAddBonusPoints() throws Exception {
        CustomerRepository repository = new CustomerRepository();
        Customer customer = repository.getCustomer(1);
        Assert.assertThat(customer.getPoints(), Matchers.is(10));
        repository.rentVideos(1, new ArrayList<>(), 7);
        Assert.assertThat(repository.getCustomer(1).getPoints(), Matchers.is(17));
    }

    @Test
    public void shouldAddRentedVideo() throws Exception {
        CustomerRepository repository = new CustomerRepository();
        Customer customer = repository.getCustomer(1);
        Assert.assertThat(customer.getPoints(), Matchers.is(10));
        LocalDate nextWeek = LocalDate.now().plusDays(7L);
        Video video = Video.builder().withId(42).withTitle("Gru3").withType(VideoType.NewRelease).build();
        RentedVideo rentedVideo = new RentedVideo(video, nextWeek);
        repository.rentVideos(1, Arrays.asList(rentedVideo), 7);
        Assert.assertEquals(1, repository.getCustomer(1).getRentedVideos().size());
    }
}
