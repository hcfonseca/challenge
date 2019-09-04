package com.volanty.challenge.service;

import com.volanty.challenge.dto.VisitDTO;
import com.volanty.challenge.entity.Car;
import com.volanty.challenge.entity.Cav;
import com.volanty.challenge.entity.Visit;
import com.volanty.challenge.repository.CarRepository;
import com.volanty.challenge.repository.CavRepository;
import com.volanty.challenge.repository.VisitRepository;
import com.volanty.challenge.repository.cache.VisitCacheRepository;
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

public class VisitServiceTest {

    private VisitService visitService;

    @Mock
    public CavRepository cavRepository;
    @Mock
    public CarRepository carRepository;
    @Mock
    public VisitRepository visitRepository;
    @Mock
    public VisitCacheRepository visitCacheRepository;

    @Before
    public void setUp() {
        initMocks(this);
        this.visitService = new VisitService(cavRepository, carRepository, visitRepository,
                visitCacheRepository);
    }

    @Test
    public void shouldReturnAVisitWhenscheduleVisit () throws Exception {

        final Optional<Cav> cav = Optional.of(new Cav(1, "Botafogo"));
        final Optional<Car> car = Optional.of(new Car(1, "VW", "Gol"));

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("GMT-3"));
        final Date date = format.parse("2019-09-04 12:00:00");

        doReturn(cav).when(cavRepository).findById(1);
        doReturn(car).when(carRepository).findById(1);
        doReturn(Boolean.TRUE).when(visitCacheRepository).deleteItem("1_20190904_12");
        doReturn(1111L).when(visitCacheRepository).getExpire("1_20190904_12");
        doReturn(new Visit()).when(visitRepository).save(new Visit(date, car.get(), cav.get()));

        final Visit visit = visitService.scheduleVisit(new VisitDTO(1,
                date ), 1);

        assertThat(visit)
                .isNotNull();

    }

    @Test(expected = NotFoundException.class)
    public void shouldReturnNotFoundExceptionWhenscheduleVisitWithoutCar () throws Exception {

        final Optional<Car> car = Optional.of(new Car(1, "VW", "Gol"));
        final Optional<Cav> cav = Optional.of(new Cav(1, "Botafogo"));

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("GMT-3"));
        final Date date = format.parse("2019-09-04 12:00:00");

        doReturn(cav).when(cavRepository).findById(1);
        doReturn(Optional.empty()).when(carRepository).findById(1);
        doReturn(Boolean.TRUE).when(visitCacheRepository).deleteItem("1_20190904_12");
        doReturn(1111L).when(visitCacheRepository).getExpire("1_20190904_12");
        doReturn(new Visit()).when(visitRepository).save(new Visit(date, car.get(), cav.get()));

        final Visit visit = visitService.scheduleVisit(new VisitDTO(1, date), 1);
    }

    @Test(expected = NotFoundException.class)
    public void shouldReturnNotFoundExceptionWhenscheduleVisitWithoutCav () throws Exception {

        final Optional<Car> car = Optional.of(new Car(1, "VW", "Gol"));
        final Optional<Cav> cav = Optional.of(new Cav(1, "Botafogo"));

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("GMT-3"));
        final Date date = format.parse("2019-09-04 12:00:00");

        doReturn(Optional.empty()).when(cavRepository).findById(1);
        doReturn(car).when(carRepository).findById(1);
        doReturn(Boolean.TRUE).when(visitCacheRepository).deleteItem("1_20190904_12");
        doReturn(1111L).when(visitCacheRepository).getExpire("1_20190904_12");
        doReturn(new Visit()).when(visitRepository).save(new Visit(date, car.get(), cav.get()));

        final Visit visit = visitService.scheduleVisit(new VisitDTO(1, date), 1);
    }

    @Test(expected = NotFoundException.class)
    public void shouldReturnNotFoundExceptionWhenscheduleVisitWithoutAAvailableTime () throws Exception {

        final Optional<Car> car = Optional.of(new Car(1, "VW", "Gol"));
        final Optional<Cav> cav = Optional.of(new Cav(1, "Botafogo"));

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("GMT-3"));
        final Date date = format.parse("2019-09-04 12:00:00");

        doReturn(cav).when(cavRepository).findById(1);
        doReturn(car).when(carRepository).findById(1);
        doReturn(Boolean.FALSE).when(visitCacheRepository).deleteItem("1_20190904_12");
        doReturn(1111L).when(visitCacheRepository).getExpire("1_20190904_12");
        doReturn(new Visit()).when(visitRepository).save(new Visit(date, car.get(), cav.get()));

        final Visit visit = visitService.scheduleVisit(new VisitDTO(1, date), 1);
    }

    @Test(expected = RuntimeException.class)
    public void shouldReturnRuntimeExceptionWhenscheduleVisitWithAnError () throws Exception {

        final Optional<Car> car = Optional.of(new Car(1, "VW", "Gol"));
        final Optional<Cav> cav = Optional.of(new Cav(1, "Botafogo"));

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("GMT-3"));
        final Date date = format.parse("2019-09-04 12:00:00");

        doReturn(cav).when(cavRepository).findById(1);
        doReturn(car).when(carRepository).findById(1);
        doReturn(Boolean.TRUE).when(visitCacheRepository).deleteItem("1_20190904_12");
        doReturn(1111L).when(visitCacheRepository).getExpire("1_20190904_12");
        doReturn(null).when(visitRepository).save(new Visit(date, car.get(), cav.get()));

        final Visit visit = visitService.scheduleVisit(new VisitDTO(1, date), 1);
    }
}