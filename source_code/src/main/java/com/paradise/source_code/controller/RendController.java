package com.paradise.source_code.controller;

import com.paradise.source_code.service.RenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/demo")
@RequiredArgsConstructor
public class RendController {

    private final RenderService renderService;

    @RequestMapping("/render/{textKey}")
    public String render(@PathVariable("textKey") String textKey){
        return this.renderService.render(textKey);
    }
}
