package com.luigivismara.modeldomain.http;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenericResponse<DATA> implements Serializable {

    @JsonIgnore
    private HttpStatus httpStatus;

    @Schema(example = "200")
    private int code;

    @Schema(example = "OK")
    private String message;

    private DATA data;

    public static <DATA> ResponseEntity<GenericResponse<DATA>> GenerateHttpResponse(GenericResponse<DATA> genericResponse) {
        return ResponseEntity.status(genericResponse.httpStatus).body(genericResponse);
    }

    public GenericResponse(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        this.code = httpStatus.value();
        this.message = httpStatus.getReasonPhrase();
    }

    public GenericResponse(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.code = httpStatus.value();
        this.message = message;
    }

    public GenericResponse(HttpStatus httpStatus, DATA data) {
        this.httpStatus = httpStatus;
        this.code = httpStatus.value();
        this.message = httpStatus.getReasonPhrase();
        this.data = data;
    }

    public GenericResponse(HttpStatus httpStatus, String message, DATA data) {
        this.httpStatus = httpStatus;
        this.code = httpStatus.value();
        this.message = message;
        this.data = data;
    }

}