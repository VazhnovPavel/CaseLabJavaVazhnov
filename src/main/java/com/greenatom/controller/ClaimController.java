package com.greenatom.controller;

import com.greenatom.controller.api.ClaimApi;
import com.greenatom.domain.dto.claim.ClaimCreationDTO;
import com.greenatom.domain.dto.claim.ClaimRequestDTO;
import com.greenatom.domain.dto.claim.ClaimResponseDTO;
import com.greenatom.domain.enums.ClaimStatus;
import com.greenatom.service.ClaimService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/claims")
public class ClaimController implements ClaimApi {

    private final ClaimService claimService;

    @GetMapping(value = "/{id}", produces = {"application/json"})
    @PreAuthorize(value = "hasAnyRole('ROLE_MANAGER', 'ROLE_SUPERVISOR')")
    public ResponseEntity<ClaimResponseDTO> getClaim(@PathVariable Long id) {
        return ResponseEntity.ok(claimService.findOne(id));
    }


    @GetMapping (produces = {"application/json"})
    @PreAuthorize(value = "hasRole('ROLE_MANAGER')")
    public ResponseEntity<List<ClaimResponseDTO>> getClaimsResponse(
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return ResponseEntity.ok(claimService.findUnassignedClaims(pageNumber, pageSize));
   }

    @PatchMapping(value = "/{id}", produces = {"application/json"})
    @PreAuthorize(value = "hasRole('ROLE_MANAGER')")
    public ResponseEntity<ClaimResponseDTO> updateClaim(@PathVariable Long id,
                                                        @RequestBody ClaimRequestDTO claim) {
        return ResponseEntity.ok(claimService.updateClaim(id, claim));
    }

    @PostMapping(produces = {"application/json"})
    @PreAuthorize(value = "hasRole('ROLE_MANAGER')")
    public ResponseEntity<ClaimResponseDTO> addClaim(@RequestBody ClaimCreationDTO claim) {
        return ResponseEntity.ok(claimService.save(claim));
    }

    @DeleteMapping(value = "/{id}", produces = {"application/json"})
    @PreAuthorize(value = "hasRole('ROLE_MANAGER')")
    public void deleteClaim(@PathVariable Long id) {
        claimService.deleteClaim(id);
        ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/resolve", produces = {"application/json"})
    @PreAuthorize(value = "hasRole('ROLE_MANAGER')")
    public ClaimResponseDTO resolveClaim(@RequestParam("claimId") Long claimId,
                                         @RequestParam("employeeId") Long employeeId,
                                         @RequestParam("status") String status) {
        return claimService.resolveClaim(claimId, employeeId, ClaimStatus.valueOf(status));
    }

    @PostMapping(value = "/appoint", produces = {"application/json"})
    @PreAuthorize(value = "hasRole('ROLE_MANAGER')")
    public ClaimResponseDTO appointClaim(@RequestParam("claim") Long claim,
                                         @RequestParam("employee") Long employee) {
        return claimService.appointClaim(claim, employee);
    }
}
