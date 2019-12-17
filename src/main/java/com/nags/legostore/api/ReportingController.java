package com.nags.legostore.api;

import com.nags.legostore.model.AvgRatingModel;
import com.nags.legostore.persistence.ReportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/legostore/api/reports")
public class ReportingController
{
    ReportService reportService;

    public ReportingController(ReportService reportService)
    {
        this.reportService = reportService;
    }
    @GetMapping("/avgRatingsReport")
    public List<AvgRatingModel> avgRatingReport()
    {
        return this.reportService.getAvgRatingReport();
    }
}
