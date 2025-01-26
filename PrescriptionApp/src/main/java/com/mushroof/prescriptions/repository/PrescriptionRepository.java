package com.mushroof.prescriptions.repository;

import com.mushroof.prescriptions.model.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    List<Prescription> findByPrescriptionDateBetween(LocalDate startDate, LocalDate endDate);
}