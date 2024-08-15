package com.jameswhupp.demo;

import com.jameswhupp.demo.data.ToolRepository;
import com.jameswhupp.demo.model.RentalAgreement;
import com.jameswhupp.demo.model.Tool;
import com.jameswhupp.demo.service.RentalAgreementService;
import com.jameswhupp.demo.service.ToolService;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Month;

import static com.jameswhupp.demo.utility.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class RentalAgreementTests {

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Mock
    private ToolRepository toolRepository;

    @Mock
    private ToolService toolService;

    @InjectMocks
    private RentalAgreementService rentalAgreementService;

    private static Tool ridgidJackhammer;
    private static Tool dewaltJackhammer;
    private static Tool wernerLadder;
    private static Tool stihlChainsaw;

    @BeforeAll
    public static void beforeTests() {
        ridgidJackhammer = new Tool(TOOL_CODE_RIDGID_JACKHAMMER, TOOL_TYPE_JACKHAMMER, TOOL_BRAND_RIDGID);
        dewaltJackhammer = new Tool(TOOL_CODE_DEWALT_JACKHAMMER, TOOL_TYPE_JACKHAMMER, TOOL_BRAND_DEWALT);
        wernerLadder = new Tool(TOOL_CODE_WERNER_LADDER, TOOL_TYPE_LADDER, TOOL_BRAND_WERNER);
        stihlChainsaw = new Tool(TOOL_CODE_STIHL_CHAINSAW, TOOL_TYPE_CHAINSAW, TOOL_BRAND_STIHL);


    }

    @Test
    void Test1() {
        //tool code JAKR
        //checkout date 9/3/15
        //rental days 5
        //discount 101%
        try {
            RentalAgreement rentalAgreement = rentalAgreementService.checkout(TOOL_CODE_RIDGID_JACKHAMMER, 5, 101, LocalDate.of(2015, Month.SEPTEMBER, 3));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            expectedEx.expect(Exception.class);
            expectedEx.expectMessage("Invalid Rental Agreement : Discount percentage must be a number between 0 and 100.  Entered Discount Percentage value 101% does not meet this criteria.");
        }
    }

    @Test
    void Test2() {
        //tool code LADW
        //checkout date 7/2/20
        //rental days 3
        //discount 10%
        Mockito.when(toolService.getToolByToolCode(TOOL_CODE_WERNER_LADDER)).thenReturn(wernerLadder);
        try {
            RentalAgreement rentalAgreement = rentalAgreementService.checkout(TOOL_CODE_WERNER_LADDER, 3, 10, LocalDate.of(2020, Month.JULY, 2));
            assertEquals(LocalDate.of(2020, Month.JULY, 5), rentalAgreement.getDueDate());
            assertEquals(2, rentalAgreement.getChargeDays());
            assertEquals(BigDecimal.valueOf(3.98).setScale(2, RoundingMode.HALF_UP), rentalAgreement.getPreDiscountCharge());
            assertEquals(BigDecimal.valueOf(0.40).setScale(2, RoundingMode.HALF_UP), rentalAgreement.getDiscountAmount());
            assertEquals(BigDecimal.valueOf(3.58).setScale(2, RoundingMode.HALF_UP), rentalAgreement.getFinalCharge());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void Test3() {
        //tool code CHNS
        //checkout date 7/2/15
        //rental days 5
        //discount 25%
        Mockito.when(toolService.getToolByToolCode(TOOL_CODE_STIHL_CHAINSAW)).thenReturn(stihlChainsaw);
        try {
            RentalAgreement rentalAgreement = rentalAgreementService.checkout(TOOL_CODE_STIHL_CHAINSAW, 5, 25, LocalDate.of(2015, Month.JULY, 2));
            assertEquals(LocalDate.of(2015, Month.JULY, 7), rentalAgreement.getDueDate());
            assertEquals(3, rentalAgreement.getChargeDays());
            assertEquals(BigDecimal.valueOf(4.47).setScale(2, RoundingMode.HALF_UP), rentalAgreement.getPreDiscountCharge());
            assertEquals(BigDecimal.valueOf(1.12).setScale(2, RoundingMode.HALF_UP), rentalAgreement.getDiscountAmount());
            assertEquals(BigDecimal.valueOf(3.35).setScale(2, RoundingMode.HALF_UP), rentalAgreement.getFinalCharge());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void Test4() {
        //tool code JAKD
        //checkout date 9/3/15
        //rental days 6
        //discount 0%
        Mockito.when(toolService.getToolByToolCode(TOOL_CODE_DEWALT_JACKHAMMER)).thenReturn(dewaltJackhammer);
        try {
            RentalAgreement rentalAgreement = rentalAgreementService.checkout(TOOL_CODE_DEWALT_JACKHAMMER, 6, 0, LocalDate.of(2015, Month.SEPTEMBER, 3));
            assertEquals(LocalDate.of(2015, Month.SEPTEMBER, 9), rentalAgreement.getDueDate());
            assertEquals(3, rentalAgreement.getChargeDays());
            assertEquals(BigDecimal.valueOf(8.97).setScale(2, RoundingMode.HALF_UP), rentalAgreement.getPreDiscountCharge());
            assertEquals(BigDecimal.valueOf(0.00).setScale(2, RoundingMode.HALF_UP), rentalAgreement.getDiscountAmount());
            assertEquals(BigDecimal.valueOf(8.97).setScale(2, RoundingMode.HALF_UP), rentalAgreement.getFinalCharge());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void Test5() {
        //tool code JAKR
        //checkout date 7/2/15
        //rental days 9
        //discount 0%
        Mockito.when(toolService.getToolByToolCode(TOOL_CODE_RIDGID_JACKHAMMER)).thenReturn(ridgidJackhammer);
        try {
            RentalAgreement rentalAgreement = rentalAgreementService.checkout(TOOL_CODE_RIDGID_JACKHAMMER, 9, 0, LocalDate.of(2015, Month.JULY, 2));
            assertEquals(LocalDate.of(2015, Month.JULY, 11), rentalAgreement.getDueDate());
            assertEquals(5, rentalAgreement.getChargeDays());
            assertEquals(BigDecimal.valueOf(14.95).setScale(2, RoundingMode.HALF_UP), rentalAgreement.getPreDiscountCharge());
            assertEquals(BigDecimal.valueOf(0.0).setScale(2, RoundingMode.HALF_UP), rentalAgreement.getDiscountAmount());
            assertEquals(BigDecimal.valueOf(14.95).setScale(2, RoundingMode.HALF_UP), rentalAgreement.getFinalCharge());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void Test6() {
        //tool code JAKR
        //checkout date 7/2/20
        //rental days 4
        //discount 50%
        Mockito.when(toolService.getToolByToolCode(TOOL_CODE_RIDGID_JACKHAMMER)).thenReturn(ridgidJackhammer);
        try {
            RentalAgreement rentalAgreement = rentalAgreementService.checkout(TOOL_CODE_RIDGID_JACKHAMMER, 4, 50, LocalDate.of(2020, Month.JULY, 2));
            assertEquals(LocalDate.of(2020, Month.JULY, 6), rentalAgreement.getDueDate());
            assertEquals(1, rentalAgreement.getChargeDays());
            assertEquals(BigDecimal.valueOf(2.99).setScale(2, RoundingMode.HALF_UP), rentalAgreement.getPreDiscountCharge());
            assertEquals(BigDecimal.valueOf(1.50).setScale(2, RoundingMode.HALF_UP), rentalAgreement.getDiscountAmount());
            assertEquals(BigDecimal.valueOf(1.49).setScale(2, RoundingMode.HALF_UP), rentalAgreement.getFinalCharge());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void TestRentalAgreementPrint() {
        Mockito.when(toolService.getToolByToolCode(TOOL_CODE_RIDGID_JACKHAMMER)).thenReturn(ridgidJackhammer);
        try {
            RentalAgreement rentalAgreement = rentalAgreementService.checkout(TOOL_CODE_RIDGID_JACKHAMMER, 4, 50, LocalDate.of(2020, Month.SEPTEMBER, 3));
            rentalAgreement.printRentalAgreementToConsole();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
