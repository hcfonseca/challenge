package com.volanty.challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InspectionDTO {

    @NotNull(message = "car could not be null")
    private Integer carId;
    @NotNull(message = "date could not be null")
    private Date date;

}
