package com.gnjhh.lxp_2nd.global;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

    @GetMapping("/error/403")
    public String forbidden() {
        return "error/403";
    }
}