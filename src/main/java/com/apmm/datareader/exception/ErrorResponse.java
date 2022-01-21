package com.apmm.datareader.exception;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ErrorResponse {

    private List<Error> errors = new ArrayList<>();

}
