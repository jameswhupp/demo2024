package com.jameswhupp.demo.model;

import com.jameswhupp.demo.utility.Constants;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Getter @Setter
public class Tool implements Serializable {

    public Tool(String toolCode, String toolType, String brand){
        this.toolCode = toolCode;
        this.toolType = toolType;
        this.brand = brand;
        switch (toolType){
            case (Constants.TOOL_TYPE_LADDER):
                this.weekdayCharge = true;
                this.weekendCharge = true;
                this.holidayCharge = false;
                this.dailyCharge = BigDecimal.valueOf(1.99);
                break;
            case (Constants.TOOL_TYPE_CHAINSAW):
                this.weekdayCharge = true;
                this.weekendCharge = false;
                this.holidayCharge = true;
                this.dailyCharge = BigDecimal.valueOf(1.49);
                break;
            case (Constants.TOOL_TYPE_JACKHAMMER):
                this.weekdayCharge = true;
                this.weekendCharge = false;
                this.holidayCharge = false;
                this.dailyCharge = BigDecimal.valueOf(2.99);
                break;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String toolCode;
    private String toolType;
    private String brand;
    private Boolean weekdayCharge;
    private Boolean weekendCharge;
    private Boolean holidayCharge;
    private BigDecimal dailyCharge;
}
