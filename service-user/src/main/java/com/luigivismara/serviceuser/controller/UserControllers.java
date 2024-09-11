package com.luigivismara.serviceuser.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.luigivismara.modeldomain.http.HttpResponse;
import com.luigivismara.modeldomain.utils.PageableTools;
import com.luigivismara.serviceuser.dto.request.UserDto;
import com.luigivismara.serviceuser.dto.response.UserDtoResponse;
import com.luigivismara.serviceuser.services.UserServices;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.luigivismara.modeldomain.configuration.ConstantsVariables.API_V1;

@RestController
@RequestMapping(API_V1 + "/users")
@RequiredArgsConstructor
public class UserControllers {

    private final UserServices services;

    @PostMapping("/register")
    public HttpResponse<UserDtoResponse> register(@Valid @RequestBody UserDto user) throws JsonProcessingException {
        return services.create(user);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/list")
    public HttpResponse<PageableTools.PaginationDto> list(@RequestParam(defaultValue = "3") int size,
                                                          @RequestParam(defaultValue = "0") int page) {
        return services.list(PageRequest.of(page, size));
    }
}
