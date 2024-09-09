package com.luigivismara.serviceuser.services;

import com.luigivismara.modeldomain.http.HttpResponse;
import com.luigivismara.modeldomain.utils.PageableTools;
import com.luigivismara.serviceuser.dto.response.UserDtoResponse;
import com.luigivismara.serviceuser.dto.resquest.UserDto;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface UserServices {
    HttpResponse<UserDtoResponse> create(UserDto user);

    HttpResponse<PageableTools.PaginationDto>list(PageRequest pageRequest);
}
