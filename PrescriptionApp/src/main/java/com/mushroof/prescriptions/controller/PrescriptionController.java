package com.mushroof.prescriptions.controller;

import com.mushroof.prescriptions.model.Prescription;
import com.mushroof.prescriptions.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/prescriptions")
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;

    @GetMapping("/list")
    public String listPrescriptions(@RequestParam(required = false) String startDate,
                                    @RequestParam(required = false) String endDate,
                                    Model model) {
        LocalDate start = startDate == null ? LocalDate.now().withDayOfMonth(1) : LocalDate.parse(startDate);
        LocalDate end = endDate == null ? LocalDate.now() : LocalDate.parse(endDate);
        List<Prescription> prescriptions = prescriptionService.getPrescriptions(start, end);
        model.addAttribute("prescriptions", prescriptions);
        model.addAttribute("startDate", start);
        model.addAttribute("endDate", end);
        return "prescription-list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("prescription", new Prescription());
        return "add-prescription";
    }

    @PostMapping("/save")
    public String savePrescription(@ModelAttribute Prescription prescription,
                                   @RequestParam("date") LocalDate prescriptionDate) {

        prescription.setPrescriptionDate(prescriptionDate);
        prescriptionService.savePrescription(prescription);
        return "redirect:/prescriptions/list";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        try {
            Prescription prescription = prescriptionService.getPrescriptionById(id);
            model.addAttribute("prescription", prescription);
            return "edit-prescription";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/prescriptions/list";
    }
}

    @PostMapping("/edit")
    public String updatePrescription(@ModelAttribute Prescription prescription) {
        Prescription existingPrescription = prescriptionService.getPrescriptionById(prescription.getId());

        existingPrescription.setPrescriptionDate(prescription.getPrescriptionDate());
        existingPrescription.setPatientName(prescription.getPatientName());
        existingPrescription.setPatientAge(prescription.getPatientAge());
        existingPrescription.setPatientGender(prescription.getPatientGender());
        existingPrescription.setDiagnosis(prescription.getDiagnosis());
        existingPrescription.setMedicines(prescription.getMedicines());
        existingPrescription.setNextVisitDate(prescription.getNextVisitDate());

        prescriptionService.savePrescription(existingPrescription);
        return "redirect:/prescriptions/list";
    }

    @GetMapping("/delete/{id}")
    public String deletePrescription(@PathVariable Long id) {
        prescriptionService.deletePrescription(id);
        return "redirect:/prescriptions/list";
    }
}