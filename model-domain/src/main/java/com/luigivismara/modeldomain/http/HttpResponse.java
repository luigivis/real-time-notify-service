package com.luigivismara.modeldomain.http;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.Serial;
import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiResponses({
        @ApiResponse(content = @Content(schema = @Schema(implementation = GenericResponse.class)))
})
public class HttpResponse<T> extends ResponseEntity<Object> implements Serializable {
    @Serial
    private static final long serialVersionUID = 7156526077883281625L;

    public HttpResponse(HttpStatus status, T data) {
        super(new GenericResponse<>(status, data), status);
    }

    public HttpResponse(HttpStatus status, String message){
        super(new GenericResponse<T>(status, message), status);
    }

    public HttpResponse(HttpStatus httpStatus) {
        super(new GenericResponse<T>(httpStatus), httpStatus);
    }
}
