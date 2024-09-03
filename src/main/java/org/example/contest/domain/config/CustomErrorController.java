package org.example.contest.domain.config;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
@RestController
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public ResponseEntity<Map<String, Object>> handleError(HttpServletRequest request) {
        Map<String, Object> body = new HashMap<>();
        HttpStatus status = getStatus(request);
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        return new ResponseEntity<>(body, status);
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (statusCode != null) {
            return HttpStatus.valueOf(statusCode);
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }


//    public String getErrorPath() {
//        return "/error";
//    }

}
