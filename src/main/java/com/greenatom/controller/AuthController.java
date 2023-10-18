package com.greenatom.controller;

import com.greenatom.controller.api.AuthApi;
import com.greenatom.domain.dto.AuthDto;
import com.greenatom.domain.dto.EmployeeDTO;
import com.greenatom.domain.dto.JwtResponse;
import com.greenatom.domain.dto.RefreshJwtRequest;
import com.greenatom.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/auth")
public class AuthController implements AuthApi {

    private final AuthService authService;

    @PostMapping("/signUp")
    @Override
    public JwtResponse registration(@RequestBody EmployeeDTO employeeDTO){
        return authService.registration(employeeDTO);
    }

    @PostMapping("/signIn")
    @Override
    public JwtResponse login(@RequestBody AuthDto authDto){
        return authService.login(authDto);
    }

    @PostMapping("/accessToken")
    @Override
    public JwtResponse getAccessToken(@RequestBody RefreshJwtRequest refreshJwtRequest){
        return authService.getAccessToken(refreshJwtRequest.getRefreshToken());
    }

    @PostMapping("/refresh")
    @Override
    public JwtResponse refresh(@RequestBody RefreshJwtRequest refreshJwtRequest){
        return authService.refresh(refreshJwtRequest.getRefreshToken());
    }
}
