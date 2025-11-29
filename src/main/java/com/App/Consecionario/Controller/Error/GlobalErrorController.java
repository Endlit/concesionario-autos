package com.App.Consecionario.Controller.Error;

import org.springframework.boot.webmvc.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class GlobalErrorController implements ErrorController {

    @GetMapping("/error")
    public String handleCustom(HttpServletRequest request) {

        Object status = request.getAttribute("jakarta.servlet.error.status_code");

        if (status != null) {
            int code = Integer.parseInt(status.toString());

            return switch (code) {
                case 300 -> "error/300";
                case 400 -> "error/400";
                case 401 -> "error/401";
                case 403 -> "error/403";
                case 404 -> "error/404";
                case 500 -> "error/500";
                default -> "error/error";
            };
        }

        return "error/error";
    }
}
