package com.konkerlabs.platform.registry.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class ControlPanelController {

    @RequestMapping
    public String panelPage() {
        return "panel/index";
    }

}
