package com.carta.batch.controller;

import com.carta.batch.common.Constants;
import com.carta.batch.common.DateUtils;
import com.carta.batch.entity.TradeCustomerRequest;
import com.carta.batch.service.FileProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

/**
 * Controller to initiate process
 */
@RestController
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private FileProcessingService fileProcessingService;

    @PostMapping("/processCustomer")
    public String processCSV(@RequestBody TradeCustomerRequest tradeCustomerRequest) throws ParseException {
        DateUtils.validateDate(tradeCustomerRequest.getDate());
        fileProcessingService.readFile(tradeCustomerRequest.getFilePath(),
                tradeCustomerRequest.getDate(),
                tradeCustomerRequest.getFraction() == null ? Constants.DEFAULT_FRACTION : tradeCustomerRequest.getFraction());

        return "Successfully Initiated";
        }
    }