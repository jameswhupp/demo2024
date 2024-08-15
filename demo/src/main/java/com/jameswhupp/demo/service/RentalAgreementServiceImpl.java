package com.jameswhupp.demo.service;

import com.jameswhupp.demo.data.RentalAgreementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RentalAgreementServiceImpl implements RentalAgreementService {
    @Autowired
    RentalAgreementRepository rentalAgreementRepository;
}
