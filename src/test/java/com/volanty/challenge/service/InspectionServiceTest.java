package com.volanty.challenge.service;

import com.volanty.challenge.dto.InspectionDTO;
import com.volanty.challenge.entity.Car;
import com.volanty.challenge.entity.Cav;
import com.volanty.challenge.entity.Inspection;
import com.volanty.challenge.repository.CarRepository;
import com.volanty.challenge.repository.CavRepository;
import com.volanty.challenge.repository.InspectionRepository;
import com.volanty.challenge.repository.cache.InspectionCacheRepository;
import javassist.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.TimeZone;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;

public class InspectionServiceTest {

    private InspectionService inspectionService;

    @Mock
    public CavRepository cavRepository;
    @Mock
    public CarRepository carRepository;
    @Mock
    public InspectionRepository inspectionRepository;
    @Mock
    public InspectionCacheRepository inspectionCacheRepository;

    @Before
    public void setUp() {
        initMocks(this);
        this.inspectionService = new InspectionService(cavRepository, carRepository, inspectionRepository,
                inspectionCacheRepository);
    }

    @Test
    public void shouldReturnAnInspectionWhenScheduleInspection () throws Exception {

        final Optional<Cav> cav = Optional.of(new Cav(1, "Botafogo"));
        final Optional<Car> car = Optional.of(new Car(1, "VW", "Gol"));

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("GMT-3"));
        final Date date = format.parse("2019-09-04 12:00:00");

        doReturn(cav).when(cavRepository).findById(1);
        doReturn(car).when(carRepository).findById(1);
        doReturn(Boolean.TRUE).when(inspectionCacheRepository).deleteItem("1_20190904_12");
        doReturn(1111L).when(inspectionCacheRepository).getExpire("1_20190904_12");
        doReturn(new Inspection()).when(inspectionRepository).save(new Inspection(date, car.get(), cav.get()));

        final Inspection inspection = inspectionService.scheduleInspection(new InspectionDTO(1,
                date ), 1);

        assertThat(inspection)
                .isNotNull();

    }

    @Test(expected = NotFoundException.class)
    public void shouldReturnNotFoundExceptionWhenScheduleInspectionWithoutCar () throws Exception {

        final Optional<Car> car = Optional.of(new Car(1, "VW", "Gol"));
        final Optional<Cav> cav = Optional.of(new Cav(1, "Botafogo"));

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("GMT-3"));
        final Date date = format.parse("2019-09-04 12:00:00");

        doReturn(cav).when(cavRepository).findById(1);
        doReturn(Optional.empty()).when(carRepository).findById(1);
        doReturn(Boolean.TRUE).when(inspectionCacheRepository).deleteItem("1_20190904_12");
        doReturn(1111L).when(inspectionCacheRepository).getExpire("1_20190904_12");
        doReturn(new Inspection()).when(inspectionRepository).save(new Inspection(date, car.get(), cav.get()));

        final Inspection inspection = inspectionService.scheduleInspection(new InspectionDTO(1, date), 1);
    }

    @Test(expected = NotFoundException.class)
    public void shouldReturnNotFoundExceptionWhenScheduleInspectionWithoutCav () throws Exception {

        final Optional<Car> car = Optional.of(new Car(1, "VW", "Gol"));
        final Optional<Cav> cav = Optional.of(new Cav(1, "Botafogo"));

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("GMT-3"));
        final Date date = format.parse("2019-09-04 12:00:00");

        doReturn(Optional.empty()).when(cavRepository).findById(1);
        doReturn(car).when(carRepository).findById(1);
        doReturn(Boolean.TRUE).when(inspectionCacheRepository).deleteItem("1_20190904_12");
        doReturn(1111L).when(inspectionCacheRepository).getExpire("1_20190904_12");
        doReturn(new Inspection()).when(inspectionRepository).save(new Inspection(date, car.get(), cav.get()));

        final Inspection inspection = inspectionService.scheduleInspection(new InspectionDTO(1, date), 1);
    }

    @Test(expected = NotFoundException.class)
    public void shouldReturnNotFoundExceptionWhenScheduleInspectionWithoutAAvailableTime () throws Exception {

        final Optional<Car> car = Optional.of(new Car(1, "VW", "Gol"));
        final Optional<Cav> cav = Optional.of(new Cav(1, "Botafogo"));

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("GMT-3"));
        final Date date = format.parse("2019-09-04 12:00:00");

        doReturn(cav).when(cavRepository).findById(1);
        doReturn(car).when(carRepository).findById(1);
        doReturn(Boolean.FALSE).when(inspectionCacheRepository).deleteItem("1_20190904_12");
        doReturn(1111L).when(inspectionCacheRepository).getExpire("1_20190904_12");
        doReturn(new Inspection()).when(inspectionRepository).save(new Inspection(date, car.get(), cav.get()));

        final Inspection inspection = inspectionService.scheduleInspection(new InspectionDTO(1, date), 1);
    }

    @Test(expected = RuntimeException.class)
    public void shouldReturnRuntimeExceptionWhenScheduleInspectionWithAnError () throws Exception {

        final Optional<Car> car = Optional.of(new Car(1, "VW", "Gol"));
        final Optional<Cav> cav = Optional.of(new Cav(1, "Botafogo"));

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("GMT-3"));
        final Date date = format.parse("2019-09-04 12:00:00");

        doReturn(cav).when(cavRepository).findById(1);
        doReturn(car).when(carRepository).findById(1);
        doReturn(Boolean.TRUE).when(inspectionCacheRepository).deleteItem("1_20190904_12");
        doReturn(1111L).when(inspectionCacheRepository).getExpire("1_20190904_12");
        doReturn(null).when(inspectionRepository).save(new Inspection(date, car.get(), cav.get()));

        final Inspection inspection = inspectionService.scheduleInspection(new InspectionDTO(1, date), 1);
    }

}