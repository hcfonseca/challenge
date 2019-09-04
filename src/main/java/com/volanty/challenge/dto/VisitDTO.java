package com.volanty.challenge.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
public class VisitDTO {

    @NotNull(message = "car could not be null")
    private Integer carId;
    @NotNull(message = "date could not be null")
    private Date date;

}
