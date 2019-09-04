package com.volanty.challenge.utils;

import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

public class ParseUtilsTest {

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void shouldReturnAListOfFormattedStringWhenRecieveASetOfString () throws Exception {

        Set<String> keys = new HashSet<>();
        keys.add("1_20190909_9");
        keys.add("2_20190905_17");
        keys.add("3_20190909_15");

        List<String> response = ParseUtils.parseKeysToDateString(keys);

        assertThat(response).isNotNull().contains("2019-09-09 09:00:00", "2019-09-05 17:00:00",
                "2019-09-09 15:00:00");

    }

    @Test
    public void shouldReturnAEmptyListWhenRecieveANull () throws Exception {
        List<String> response = ParseUtils.parseKeysToDateString(null);
        assertThat(response).isNotNull();
    }

    @Test
    public void shouldGenerateAKey() throws Exception {

        Calendar date = Calendar.getInstance();
        date.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2019-09-04 12:00:00"));

        String key = ParseUtils.generateKey(date, 1);

        assertThat(key).isNotNull().isEqualTo("1_20190904_12");
    }
}