package com.vids.paulturner.repository;

import com.vids.paulturner.domain.Customer;
import com.vids.paulturner.domain.RentedVideo;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.ws.rs.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CustomerRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerRepository.class);

    private static final String CUSTOMERS = "/customers.json";

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Map<Integer, Customer> customers;

    public CustomerRepository() {
        try (InputStream is = CustomerRepository.class.getResourceAsStream(CUSTOMERS)) {
            List<Customer> tmpcustomers =
                objectMapper.readValue(is, objectMapper.getTypeFactory().constructCollectionType(List.class, Customer.class));
            customers = tmpcustomers.stream().collect(Collectors.toMap(Customer::getId, c -> c));
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    public Customer rentVideos(Integer id, List<RentedVideo> customerVideos, int points) {
        LOGGER.info("Rent videos [id={}] [customerVideos={}] [points={}]", id, customerVideos, points);
        Customer customer = Customer
            .builder()
            .withCustomer(getCustomer(id))
            .withRentedVideos(customerVideos)
            .incrementPoints(points)
            .build();
        customers.put(id, customer);
        return customer;
    }

    public Customer getCustomer(Integer id) {
        Customer customer = customers.get(id);
        if (Objects.isNull(customer)) {
            throw new NotFoundException();
        }
        LOGGER.info("Get customer by id [id={}] [Customer={}]", id, customer);
        return customer;
    }

}
