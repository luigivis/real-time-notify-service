package com.luigivismara.modeldomain.exception;

import com.luigivismara.modeldomain.http.HttpResponse;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class ExceptionController implements ErrorController {

    @RequestMapping("/error")
    public ResponseEntity<?> handleError(HttpServletRequest request) {
        var status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
            var statusCode = Integer.parseInt(status.toString());
            return new HttpResponse<Object>(HttpStatusCode.valueOf(statusCode));
        }
        return new HttpResponse<Object>(HttpStatus.BAD_REQUEST);
    }
}