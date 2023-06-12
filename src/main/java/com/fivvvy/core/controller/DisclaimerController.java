package com.fivvvy.core.controller;

import com.fivvvy.core.dto.DisclaimerDto;
import com.fivvvy.core.model.Disclaimer;
import com.fivvvy.core.service.DisclaimerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

;


@RestController

 
public class DisclaimerController {


    private DisclaimerService service;

    @Autowired
    public DisclaimerController(DisclaimerService service) {
        this.service = service;
    }

    @PostMapping("/disclaimers")
    
         
    public ResponseEntity<Disclaimer> createDisclaimer(@RequestBody DisclaimerDto newDisclaimer){
        // Validate the required fields of the new disclaimer
        if (newDisclaimer.getName() == null || newDisclaimer.getText() == null || newDisclaimer.getVersion() == null) {
            return ResponseEntity.badRequest().build();
        }

        // Set the fields of the disclaimer using the values from the DTO
         Disclaimer disclaimer = new Disclaimer();

        // Save the disclaimer using the service
        disclaimer = service.createDisclaimer(newDisclaimer);

        // Return a response with the created disclaimer and status code 201
        return ResponseEntity.status(HttpStatus.CREATED).body(disclaimer);
    }

    @GetMapping("/disclaimers")


    public ResponseEntity<List<Disclaimer>> getAllDisclaimers(@RequestParam(required = false) String text) {
        List<Disclaimer> allList = new ArrayList<Disclaimer>();
        try {
            if(text != null){
                service.getAllDisclaimersByText(text).forEach(allList::add);
            } else
                service.getAllDisclaimers().forEach(allList::add);

            if (allList.size() > 0) {
                return ResponseEntity.status(HttpStatus.OK).body(allList);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/disclaimers/{id}")


    public ResponseEntity<Disclaimer> getDisclaimersById(@PathVariable("id") Integer id){
        Optional<Disclaimer> disclaimers = service.getDisclaimerById(id);
        if(!disclaimers.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.status(HttpStatus.OK).body(disclaimers.get());
    }

    @PutMapping("/disclaimers/{id}")

    public ResponseEntity<Disclaimer> updateDisclaimer(@PathVariable("id") Integer id, @RequestBody DisclaimerDto updatedDisclaimer) {
        // Retrieve the existing disclaimer by ID
        Optional<Disclaimer> existingDisclaimer = service.getDisclaimerById(id);
        if (!existingDisclaimer.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        // Update the fields of the existing disclaimer using the values from the DTO
        Disclaimer disclaimer = existingDisclaimer.get();
        disclaimer.setName(updatedDisclaimer.getName());
        disclaimer.setText(updatedDisclaimer.getText());
        disclaimer.setVersion(updatedDisclaimer.getVersion());

        // Validate the required fields of the updated disclaimer
        if (disclaimer.getName() == null || disclaimer.getText() == null || disclaimer.getVersion() == null) {
            return ResponseEntity.badRequest().build();
        }

        // Save the updated disclaimer using the service
        disclaimer = service.updateDisclaimer(disclaimer);

        // Return a response with the updated disclaimer and status code 200
        return ResponseEntity.ok(disclaimer);
    }


    @DeleteMapping("/disclaimers/{id}")

    public void deleteDisclaimers(@PathVariable("id") Integer idDisclaimer){
        service.deleteDisclaimer(idDisclaimer);
    }


}


