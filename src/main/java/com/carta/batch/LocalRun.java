package com.carta.batch;

import com.carta.batch.common.Constants;
import com.carta.batch.service.FileProcessingServiceImpl;

/**
 * class to run with psvm
 */
public class LocalRun {
    public static void main(String[] args) {
        if(args.length < 2){
            throw new RuntimeException("Expected Params Length is 2 or 3");
        }
        Integer fraction = Constants.DEFAULT_FRACTION;
        if(args.length == 3)
        {
            fraction = Integer.parseInt(args[2]);
        }

        FileProcessingServiceImpl fileProcessingService = new FileProcessingServiceImpl();
        fileProcessingService.readFile(  args[0],args[1],fraction);
    }
}
