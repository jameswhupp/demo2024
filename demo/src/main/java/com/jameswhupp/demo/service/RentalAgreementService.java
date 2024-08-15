package com.jameswhupp.demo.service;

import com.jameswhupp.demo.model.RentalAgreement;
import com.jameswhupp.demo.model.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Service
public class RentalAgreementService {

    @Autowired
    private ToolService toolService;

    @Autowired
    private RentalCalendarService rentalCalendarService;

    public RentalAgreement checkout(String toolCode, int rentalDayCount, int discountPercentage, LocalDate checkoutDate) throws Exception {

        double discountPercentageAsDouble = ((double) discountPercentage) / 100;

        if (rentalDayCount < 1)
        {
            throw new Exception("Invalid Rental Agreement : Tools must be rented for at least one full day. Entered Rental Day Count value " + rentalDayCount + " does not meet this criteria.");
        }
        if (discountPercentage > 100 || discountPercentage < 0)
        {
            throw new Exception("Invalid Rental Agreement : Discount percentage must be a number between 0 and 100.  Entered Discount Percentage value " + discountPercentage + "% does not meet this criteria.");
        }

        RentalAgreement rentalAgreement = new RentalAgreement();
        Tool toolFromCode = toolService.getToolByToolCode(toolCode);
        rentalAgreement.setToolBrand(toolFromCode.getBrand());
        rentalAgreement.setToolType(toolFromCode.getToolType());
        rentalAgreement.setToolCode(toolCode);
        rentalAgreement.setRentalDays(rentalDayCount);
        rentalAgreement.setCheckOutDate(checkoutDate);
        rentalAgreement.setDueDate(checkoutDate.plusDays(rentalDayCount));
        rentalAgreement.setChargeDays(calculateChargeDays(rentalAgreement, toolFromCode));
        rentalAgreement.setPreDiscountCharge(toolFromCode.getDailyCharge().multiply(BigDecimal.valueOf(rentalAgreement.getChargeDays())).setScale(2, RoundingMode.HALF_UP));
        rentalAgreement.setDiscountPercentage(discountPercentage);
        rentalAgreement.setDiscountAmount(rentalAgreement.getPreDiscountCharge().multiply(BigDecimal.valueOf(discountPercentageAsDouble)).setScale(2, RoundingMode.HALF_UP));
        rentalAgreement.setFinalCharge(rentalAgreement.getPreDiscountCharge().subtract(rentalAgreement.getDiscountAmount()).setScale(2, RoundingMode.HALF_UP));

        return rentalAgreement;
    }

    private int calculateChargeDays(RentalAgreement rentalAgreement, Tool tool)
    {
        LocalDate currentDate = rentalAgreement.getCheckOutDate();
        int chargeDays = 0;
        for (int dayOfContract = 1; dayOfContract <= rentalAgreement.getRentalDays(); dayOfContract++)
        {
            currentDate = currentDate.plusDays(1);
            //weekday check
            if (RentalCalendarService.isHoliday(currentDate))
            {
                if (tool.getHolidayCharge())
                {
                    chargeDays++;
                }
            }
            else if (RentalCalendarService.isWeekEnd(currentDate) && tool.getWeekendCharge())
            {
                chargeDays++;
            }
            else if (RentalCalendarService.isWeekDay(currentDate) && tool.getWeekdayCharge())
            {
                chargeDays++;
            }
        }
        return chargeDays;
    }

}
