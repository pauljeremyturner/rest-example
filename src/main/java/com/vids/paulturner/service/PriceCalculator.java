package com.vids.paulturner.service;


import com.vids.paulturner.domain.VideoType;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PriceCalculator {

    private static final Logger LOGGER = LoggerFactory.getLogger(PriceCalculator.class);


    public int price(VideoType type, int dayCount) {
        Integer pricePerChargeableDay = getPricePerChargeableDay(type);
        int price = 0;
        int chargeableDays = 0;
        switch (type) {
            case NewRelease:
                chargeableDays = dayCount;
                break;
            case Regular:
                if (dayCount > 3) {
                    chargeableDays = dayCount - 3 + 1;
                } else if (dayCount <= 3) {
                    chargeableDays = 1;
                }
                break;
            case OldFilm:
                if (dayCount > 5) {
                    chargeableDays = dayCount - 5 + 1;
                } else if (dayCount <= 5) {
                    chargeableDays = 1;
                }
                break;
            default:
                throw new InternalServerErrorException();
        }

        price = pricePerChargeableDay * chargeableDays;
        LOGGER.info("Get video price [type={}] [dayCount={}] [price={}]", type, dayCount, price);
        return price;
    }

    private int getPricePerChargeableDay(VideoType type) {
        final int pricePerDay;
        switch (type) {
            case NewRelease:
                pricePerDay = 40;
                break;
            case OldFilm:
                pricePerDay = 30;
                break;
            case Regular:
                pricePerDay = 30;
                break;
            default:
                throw new BadRequestException(String.format("Unrecognised Video Type: %s", type.toString()));
        }
        LOGGER.info("Get price per chargeable day [type={}] [price={}]", type, pricePerDay);
        return pricePerDay;
    }

    public int getBonusPointsPerRental(VideoType type) {
        final int points;
        switch (type) {
            case NewRelease:
                points = 2;
                break;
            case OldFilm:
                points = 1;
                break;
            case Regular:
                points = 1;
                break;
            default:
                throw new BadRequestException(String.format("Unrecognised Video Type: %s", type.toString()));
        }
        LOGGER.info("Get bonus points per rental [type={}] [points={}]", type, points);
        return points;
    }

}
