package com.volanty.challenge.controller;

import com.volanty.challenge.dto.InspectionDTO;
import com.volanty.challenge.dto.VisitDTO;
import com.volanty.challenge.entity.Cav;
import com.volanty.challenge.entity.Inspection;
import com.volanty.challenge.entity.Visit;
import com.volanty.challenge.service.CarService;
import com.volanty.challenge.service.CavService;
import com.volanty.challenge.service.InspectionService;
import com.volanty.challenge.service.VisitService;
import javassist.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.MockitoAnnotations.initMocks;

public class CavControllerTest {

    private CavController cavController;

    @Mock
    public CavService cavService;
    @Mock
    public CarService carService;
    @Mock
    public InspectionService inspectionService;
    @Mock
    public VisitService visitService;

    @Before
    public void setUp() {
        initMocks(this);
        this.cavController = new CavController(cavService, carService, inspectionService, visitService);
    }

    @Test
    public void shouldReturnOkToGetAllCavs() throws Exception {

        Cav cav = new Cav(1, "Botafogo");
        List<Cav> cavs = new ArrayList<>();
        cavs.add(cav);

        doReturn(cavs).when(cavService).getAllCavs();
        final ResponseEntity response = cavController.getCavs();

        assertThat(response).isNotNull().extracting("status")
                .extracting("value").contains(200);
    }

    @Test
    public void shouldReturnNoContentToEmptyCavs() throws Exception {

        doReturn(new ArrayList<>()).when(cavService).getAllCavs();
        final ResponseEntity response = cavController.getCavs();

        assertThat(response).isNotNull().extracting("status")
                .extracting("value").contains(204);
    }

    @Test
    public void shouldReturnNoContentToNullCavs() throws Exception {

        doReturn(null).when(cavService).getAllCavs();
        final ResponseEntity response = cavController.getCavs();

        assertThat(response).isNotNull().extracting("status")
                .extracting("value").contains(204);
    }

    @Test
    public void shouldReturnInternalServerErrorToGetAllCavs() throws Exception {

        doThrow(new Exception()).when(cavService).getAllCavs();
        final ResponseEntity response = cavController.getCavs();

        assertThat(response).isNotNull().extracting("status")
                .extracting("value").contains(500);
    }

    @Test
    public void shouldReturnOkToGetAvailableHoursToInspection() throws Exception {

        List<String> availableHours = new ArrayList<>();
        availableHours.add("2019-09-04 12:00:00");

        doReturn(availableHours).when(inspectionService).getAvailableHoursByCav(1);
        final ResponseEntity response = cavController.getAvailableHoursToInspection(1);

        assertThat(response).isNotNull().extracting("status")
                .extracting("value").contains(200);

    }

    @Test
    public void shouldReturnNoContentToGetAvailableHoursToInspectionInNullSearch() throws Exception {

        doReturn(null).when(inspectionService).getAvailableHoursByCav(1);
        final ResponseEntity response = cavController.getAvailableHoursToInspection(1);

        assertThat(response).isNotNull().extracting("status")
                .extracting("value").contains(204);

    }

    @Test
    public void shouldReturnNoContentToGetAvailableHoursToInspectionInEmptySearch() throws Exception {

        doReturn(new ArrayList<String>()).when(inspectionService).getAvailableHoursByCav(1);
        final ResponseEntity response = cavController.getAvailableHoursToInspection(1);

        assertThat(response).isNotNull().extracting("status")
                .extracting("value").contains(204);

    }

    @Test
    public void shouldReturnInternalServerErrorToGetAvailableHoursInInspection() throws Exception {

        doThrow(new ParseException("", 1)).when(inspectionService).getAvailableHoursByCav(1);
        final ResponseEntity response = cavController.getAvailableHoursToInspection(1);

        assertThat(response).isNotNull().extracting("status")
                .extracting("value").contains(500);

    }

    @Test
    public void shouldReturnOkToGetAvailableHoursToVisit() throws Exception {

        List<String> availableHours = new ArrayList<>();
        availableHours.add("2019-09-04 12:00:00");

        doReturn(availableHours).when(visitService).getAvailableHoursByCav(1);
        final ResponseEntity response = cavController.getAvailableHoursToVisit(1);

        assertThat(response).isNotNull().extracting("status")
                .extracting("value").contains(200);

    }

    @Test
    public void shouldReturnNoContentToGetAvailableHoursToVisitInNullSearch() throws Exception {

        doReturn(null).when(visitService).getAvailableHoursByCav(1);
        final ResponseEntity response = cavController.getAvailableHoursToVisit(1);

        assertThat(response).isNotNull().extracting("status")
                .extracting("value").contains(204);

    }

    @Test
    public void shouldReturnNoContentToGetAvailableHoursToVisitInEmptySearch() throws Exception {

        doReturn(new ArrayList<String>()).when(visitService).getAvailableHoursByCav(1);
        final ResponseEntity response = cavController.getAvailableHoursToVisit(1);

        assertThat(response).isNotNull().extracting("status")
                .extracting("value").contains(204);

    }

    @Test
    public void shouldReturnInternalServerErrorToGetAvailableHoursToVisit() throws Exception {

        doThrow(new ParseException("", 1)).when(visitService).getAvailableHoursByCav(1);
        final ResponseEntity response = cavController.getAvailableHoursToVisit(1);

        assertThat(response).isNotNull().extracting("status")
                .extracting("value").contains(500);

    }

    @Test
    public void shouldReturnCreatedToScheduleInspection() throws Exception {

        doReturn(new Inspection()).when(inspectionService).scheduleInspection(new InspectionDTO(), 1);
        final ResponseEntity response = cavController.scheduleInspection(1, new InspectionDTO());

        assertThat(response).isNotNull().extracting("status")
                .extracting("value").contains(201);

    }

    @Test
    public void shouldReturnNoContentToScheduleInspectionWhenNotFoundExceptionOccur() throws Exception {

        doThrow(new NotFoundException("")).when(inspectionService).scheduleInspection(new InspectionDTO(), 1);
        final ResponseEntity response = cavController.scheduleInspection(1, new InspectionDTO());

        assertThat(response).isNotNull().extracting("status")
                .extracting("value").contains(204);

    }

    @Test
    public void shouldReturnInternalServerErrorToScheduleInspectionWhenExceptionOccur() throws Exception {

        doThrow(new Exception()).when(inspectionService).scheduleInspection(new InspectionDTO(), 1);
        final ResponseEntity response = cavController.scheduleInspection(1, new InspectionDTO());

        assertThat(response).isNotNull().extracting("status")
                .extracting("value").contains(500);

    }

    @Test
    public void shouldReturnCreatedToScheduleVisit() throws Exception {

        doReturn(new Visit()).when(visitService).scheduleVisit(new VisitDTO(), 1);
        final ResponseEntity response = cavController.scheduleVisit(1, new VisitDTO());

        assertThat(response).isNotNull().extracting("status")
                .extracting("value").contains(201);

    }

    @Test
    public void shouldReturnNoContentToScheduleVisitWhenNotFoundExceptionOccur() throws Exception {

        doThrow(new NotFoundException("")).when(visitService).scheduleVisit(new VisitDTO(), 1);
        final ResponseEntity response = cavController.scheduleVisit(1, new VisitDTO());

        assertThat(response).isNotNull().extracting("status")
                .extracting("value").contains(204);

    }

    @Test
    public void shouldReturnInternalServerErrorToScheduleVisitWhenExceptionOccur() throws Exception {

        doThrow(new Exception()).when(visitService).scheduleVisit(new VisitDTO(), 1);
        final ResponseEntity response = cavController.scheduleVisit(1, new VisitDTO());

        assertThat(response).isNotNull().extracting("status")
                .extracting("value").contains(500);

    }
}