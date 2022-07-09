package com.carta.batch.common;

import com.carta.batch.entity.TradeCustomer;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Date utility class to validate/compare dates
 */
@Slf4j
public class DateUtils {

   private static final SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT_YYYY_MM_DD);

    /**
     * Method to check Date falls under requested/target date
     * @param targetDate target date
     * @param customer customer object
     *
     * @return boolean
     */
    public static boolean dateCheck(String targetDate, TradeCustomer customer){
        try {
            return !sdf.parse(customer.getDate()).after(sdf.parse(targetDate));
        } catch (ParseException e) {
            log.error("records did not process :  "+customer.getEmployeeId() + " for date "+customer.getDate());
        }
        return false;
    }

    /**
     * Method to validate date
     *
     * @param targetDate targetDate
     * @throws ParseException ParseException
     */
    public static void validateDate(String targetDate) throws ParseException {
        try {
             sdf.parse(targetDate);
        } catch (ParseException e) {
            log.error("Can not Parse date "+targetDate);
            throw new ParseException("Can not Parse date "+targetDate,0);
        }
    }
}
