package com.fivvvy.core.controller;

import com.fivvvy.core.dto.AcceptanceDto;
import com.fivvvy.core.model.Acceptance;
import com.fivvvy.core.service.AcceptanceService;
import io.swagger.annotations.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/acceptance")
@Api(tags = "Acceptance")
public class AcceptanceController {

    private final AcceptanceService service;

    @Autowired
    public AcceptanceController(AcceptanceService service) {
        this.service = service;
    }

    @PostMapping
    @ApiOperation("Create a new acceptance")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Acceptance created successfully"),
            @ApiResponse(code = 204, message = "No content"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<Acceptance> createAcceptance(@RequestBody AcceptanceDto newAcceptance) {
        try {
            Acceptance entity = new Acceptance();
            BeanUtils.copyProperties(newAcceptance, entity);
            Acceptance createdAcceptance = service.createAcceptance(entity);
            if (createdAcceptance != null) {
                return ResponseEntity.status(HttpStatus.OK).body(createdAcceptance);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    @ApiOperation("Get all acceptances")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 204, message = "No content")
    })
    public ResponseEntity<List<Acceptance>> getAllAcceptances() {
        List<Acceptance> allAcceptances = service.getAllAcceptances();
        if (!allAcceptances.isEmpty()) {
            return ResponseEntity.ok(allAcceptances);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/{id}")
    @ApiOperation("Get an acceptance by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not found")
    })
    public ResponseEntity<Acceptance> getAcceptanceById(@PathVariable("id") Integer id) {
        Optional<Acceptance> acceptance = service.getAcceptanceById(id);
        return acceptance.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/users/{userId}")
    @ApiOperation("Get acceptances by user ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 204, message = "No content")
    })
    public ResponseEntity<List<Acceptance>> getAcceptancesByUser(@PathVariable("userId") Integer userId) {
        List<Acceptance> userAcceptances = service.getAcceptancesByUser(userId);
        if (!userAcceptances.isEmpty()) {
            return ResponseEntity.ok(userAcceptances);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/disclaimers/{disclaimerId}")
    @ApiOperation("Get acceptances by disclaimer ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 204, message = "No content")
    })
    public ResponseEntity<List<Acceptance>> getAcceptancesByDisclaimer(@PathVariable("disclaimerId") Long disclaimerId) {
        List<Acceptance> disclaimerAcceptances = service.getAcceptancesByDisclaimer(disclaimerId);
        if (!disclaimerAcceptances.isEmpty()) {
            return ResponseEntity.ok(disclaimerAcceptances);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete an acceptance by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not found")
    })
    public ResponseEntity<Void> deleteAcceptance(@PathVariable("id") Long id) {
        service.deleteAcceptance(id);
        return ResponseEntity.ok().build();
    }
}
