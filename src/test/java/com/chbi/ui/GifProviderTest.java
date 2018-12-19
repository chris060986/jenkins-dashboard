package com.chbi.ui;

import com.chbi.ApplicationConfiguration;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.mockito.Mockito;

public class GifProviderTest {

    @Test
    public void testRandomValues() {
        GifProvider gifProvider = new GifProvider(Mockito.mock(ApplicationConfiguration.class));

        for (int i = 0; i < 1000; i++) {
            int random = gifProvider.getRandomInt(7);
            System.out.println(random);
            Assertions.assertThat(random).isBetween(0, 6);
        }


        for (int i = 0; i < 1000; i++) {
            int random = gifProvider.getRandomInt(10);
            System.out.println(random);
            Assertions.assertThat(random).isBetween(0, 9);
        }
    }
}