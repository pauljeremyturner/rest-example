package com.vids.paulturner.service;

import org.hamcrest.Matchers;
import org.junit.Test;
import static com.vids.paulturner.domain.VideoType.NewRelease;
import static com.vids.paulturner.domain.VideoType.OldFilm;
import static com.vids.paulturner.domain.VideoType.Regular;
import static org.junit.Assert.assertThat;

public class PriceCalculatorTest {

    @Test
    public void shouldGetNewReleaseBonusPoints() throws Exception {
        assertThat(new PriceCalculator().getBonusPointsPerRental(NewRelease), Matchers.is(2));
    }

    @Test
    public void shouldGetRegularBonusPoints() throws Exception {
        assertThat(new PriceCalculator().getBonusPointsPerRental(Regular), Matchers.is(1));
    }

    @Test
    public void shouldGetOldFilmBonusPoints() throws Exception {
        assertThat(new PriceCalculator().getBonusPointsPerRental(OldFilm), Matchers.is(1));
    }

    @Test
    public void shouldGetNewReleaseBonusPricePerDay() throws Exception {
        assertThat(new PriceCalculator().price(NewRelease, 1), Matchers.is(40));
        assertThat(new PriceCalculator().price(NewRelease, 3), Matchers.is(120));
    }

    @Test
    public void shouldGetRegularPricePerDay() throws Exception {
        assertThat(new PriceCalculator().price(Regular, 1), Matchers.is(30));
        assertThat(new PriceCalculator().price(Regular, 3), Matchers.is(30));
        assertThat(new PriceCalculator().price(Regular, 4), Matchers.is(60));
    }

    @Test
    public void shouldGetOldFilmPricePerDay() throws Exception {
        assertThat(new PriceCalculator().price(OldFilm, 1), Matchers.is(30));
        assertThat(new PriceCalculator().price(OldFilm, 5), Matchers.is(30));
        assertThat(new PriceCalculator().price(OldFilm, 6), Matchers.is(60));
    }
}
