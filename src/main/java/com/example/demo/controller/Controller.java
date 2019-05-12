package com.example.demo.controller;

import com.example.demo.bl.OperationService;
import com.example.demo.controller.model.MoneyRequest;
import com.example.demo.controller.model.Response;
import com.example.demo.controller.model.TransferRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.exception.TransferMoneyException;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping("/bank/v1")
@Api(tags = {"Swagger Resource"})
@Slf4j
public class Controller {
    @Autowired
    OperationService operationService;

    @ApiOperation(value = "Перевод денег", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation success", response = String.class),
            @ApiResponse(code = 500, message = "Operation failed", response = String.class)
    })
    @PostMapping(value = "/transfer",
            consumes = APPLICATION_JSON_UTF8_VALUE,
            produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Response> transfer(@Valid @RequestBody @NonNull TransferRequest request) {
        Response response = new Response();
        log.info("Get request for transfer money" + request.toString());
        try {
            operationService.transferMoney(request);
            response.setMessage("Operation success");
        } catch (TransferMoneyException e) {
            response.setMessage(e.getErrorCode().getDescription());
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @ApiOperation(value = "Снятие денег", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation success", response = String.class),
            @ApiResponse(code = 500, message = "Operation failed", response = String.class)
    })
    @PostMapping(value = "/get-money",
            consumes = APPLICATION_JSON_UTF8_VALUE,
            produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Response> getMoney(@Valid @RequestBody @NonNull MoneyRequest request) {
        Response response = new Response();
        log.info("Get request for getting money" + request.toString());
        try {
            operationService.getMoney(request);
            response.setMessage("Operation success");
        } catch (TransferMoneyException e) {
            response.setMessage(e.getErrorCode().getDescription());
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ApiOperation(value = "Добавление денег", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation success", response = String.class),
            @ApiResponse(code = 500, message = "Operation failed", response = String.class)
    })
    @PostMapping(value = "/add-money",
            consumes = APPLICATION_JSON_UTF8_VALUE,
            produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity setMoney(@Valid @RequestBody @NonNull MoneyRequest request) {
        Response response = new Response();
        log.info("Get request for setting money" + request.toString());
        try {
            operationService.setMoney(request);
            response.setMessage("Operation success");
        } catch (TransferMoneyException e) {
            response.setMessage(e.getErrorCode().getDescription());
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
