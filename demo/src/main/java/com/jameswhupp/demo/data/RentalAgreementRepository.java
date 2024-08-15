package com.jameswhupp.demo.data;

import com.jameswhupp.demo.model.Tool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalAgreementRepository extends JpaRepository<Tool, Long> {
}