package com.carta.batch.service;

import com.carta.batch.common.Constants;
import com.carta.batch.common.DateUtils;
import com.carta.batch.entity.TradeCustomer;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * Service Impl class
 */
@Service
@Slf4j
public class FileProcessingServiceImpl implements FileProcessingService {
    /**
     * Map to save process element before writing to file
     */
    private static  Map<String, Set<TradeCustomer>> VEST_MAP = null;

    /**
     * Set to check if employee has processed
     */
    private static  Set<String> PROCESSED  = null;

    /**
     * count variable
     */
    private static Integer count = 0;

    /**
     * Method to read file
     *
     * @param path file path
     * @param date target date
     * @param fraction fraction
     */
    public  void readFile(String path, String date, Integer fraction) {
        try (CSVReader reader = new CSVReader(new FileReader(path)))
        {
            VEST_MAP = new HashMap<>(Constants.BATCH_LOAD);
           String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                putObjectToMap(nextLine,date,fraction);
            }

        } catch ( CsvValidationException | IOException |RuntimeException e) {
            throw new RuntimeException(e);
        }
        writeResultToFile(Constants.RESULT_PATH);
    }

  //  public static void main(String[] args) {
    //    readFile("/Users/gauravtyagi/Downloads/Python/Java/spring-batch-example-main/src/main/resources/test.csv","2019-01-01",1);
    //}

    /**
     * Method to convert row to object
     *
     * @param line line array
     * @param date date
     * @param fraction fraction
     */
    private  void putObjectToMap(String[] line,String date,Integer fraction) {
        count++;
        if(line.length >= 5) {
            TradeCustomer tradeCustomer = TradeCustomer.builder()
                    .date(line[4])
                    .awardId(line[3])
                    .employeeId(line[1])
                    .employeeName(line[2])
                    .quantity(new BigDecimal(line[5].trim()).setScale(fraction,BigDecimal.ROUND_HALF_DOWN))
                    .vest(line[0])
                    .build();
            if(!DateUtils.dateCheck(date,tradeCustomer)){
                tradeCustomer.setQuantity(new BigDecimal(0).setScale(fraction,BigDecimal.ROUND_HALF_DOWN));
            }
             Set<TradeCustomer> localSet =  VEST_MAP.get(tradeCustomer.getEmployeeId());
               if(localSet == null) {
                   localSet =  new HashSet<>();
               }
               localSet.add(tradeCustomer);
                VEST_MAP.put(tradeCustomer.getEmployeeId(),localSet);
        }

        if(count == Constants.BATCH_LOAD){
           count=0;
            writeResultToFile(Constants.RESULT_PATH);
            VEST_MAP = new HashMap<>();
        }
    }

    /**
     * Method to write Java class
     *
     * @param path path
     */
    private static void writeResultToFile(String path) {
            try (CSVWriter writer = new CSVWriter(new FileWriter(path,true))) {
                for (String key : VEST_MAP.keySet()) {
                  for(TradeCustomer tradeCustomer : calculateStockForCustomer(VEST_MAP.get(key)).values()) {
                      writer.writeNext(
                              Arrays.stream(new String[]{tradeCustomer.getVest(), tradeCustomer.getEmployeeId(),
                                              tradeCustomer.getEmployeeName(), tradeCustomer.getAwardId(),
                                              tradeCustomer.getDate(), String.valueOf(tradeCustomer.getQuantity())})
                                      .toArray(String[]::new)
                      );

                  }
                }
            }
            catch (IOException e) {
                log.error("issue with writing the files: ");
                throw new RuntimeException(e);
            }
    }

    /**
     * Method to calculate stocke vest vs cancel
     *
     * @param tradeCustomers tradeCustomers
     * @return processed map
     */
    private static Map<String, TradeCustomer> calculateStockForCustomer(Set<TradeCustomer> tradeCustomers) {
        Map<String, TradeCustomer> result = new HashMap<>(tradeCustomers.size());

        for(TradeCustomer tradeCustomer : tradeCustomers){
            if(!result.containsKey(tradeCustomer.getAwardId())){
                result.put(tradeCustomer.getAwardId(),tradeCustomer);
            }
            else{
               TradeCustomer processedObject =  result.get(tradeCustomer.getAwardId());
                if(processedObject.getVest().trim().equalsIgnoreCase(tradeCustomer.getVest().trim())){
                    processedObject.setQuantity(processedObject.getQuantity().add(tradeCustomer.getQuantity()));
                }
                else{
                    if(processedObject.getQuantity().compareTo(tradeCustomer.getQuantity())>=0) {
                        processedObject.setQuantity(processedObject.getQuantity().subtract(tradeCustomer.getQuantity()));
                    }
                    else{
                        processedObject.setQuantity(tradeCustomer.getQuantity().subtract(processedObject.getQuantity()));
                        processedObject.setVest(tradeCustomer.getVest());
                    }
                }
                result.put(tradeCustomer.getAwardId(),processedObject);
            }
        }
        return result;
    }
}
