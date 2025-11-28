package com.App.Consecionario.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("")
public class MainController {

    @RequestMapping("")
    public String index() {
        return "index";
    }

}