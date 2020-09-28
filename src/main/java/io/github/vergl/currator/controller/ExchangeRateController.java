package io.github.vergl.currator.controller;

import io.github.vergl.currator.domain.ExchangeRateDto;
import io.github.vergl.currator.domain.filter.ExchangeRateDateFilter;
import io.github.vergl.currator.domain.filter.ExchangeRateHistoryFilter;
import io.github.vergl.currator.service.ExchangeRateService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ExchangeRateController {

    private final ExchangeRateService service;

    public ExchangeRateController(ExchangeRateService service) {
        this.service = service;
    }

    @ApiOperation("Get latest currency rates")
    @GetMapping("/latest")
    public ExchangeRateDto getLatest(ExchangeRateDateFilter filter) {
        return service.getByDate(filter);
    }

    @ApiOperation("Get currency rates by date")
    @GetMapping("/date/{date}")
    public ExchangeRateDto getRatesByDate(ExchangeRateDateFilter filter) {
        return service.getByDate(filter);
    }

    @ApiOperation("Get currency rates for a period of time")
    @GetMapping("/history")
    public List<ExchangeRateDto> getHistoryRates(ExchangeRateHistoryFilter filter) {
        return service.getHistoryRates(filter);
    }
}
