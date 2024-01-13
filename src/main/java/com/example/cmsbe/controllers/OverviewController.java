package com.example.cmsbe.controllers;

import com.example.cmsbe.models.Overview;
import com.example.cmsbe.services.interfaces.IOverviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/overview")
@RequiredArgsConstructor
public class OverviewController {
    private final IOverviewService overviewService;

    @GetMapping
    public Overview getOverview() {
        return overviewService.getOverview();
    }
}

