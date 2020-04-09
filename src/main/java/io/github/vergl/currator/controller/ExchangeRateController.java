package io.github.vergl.currator.controller;

import io.github.vergl.currator.domain.ExchangeRateDto;
import io.github.vergl.currator.domain.filter.ExchangeRateDateFilter;
import io.github.vergl.currator.domain.filter.ExchangeRateHistoryFilter;
import io.github.vergl.currator.service.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ExchangeRateController {

    private final ExchangeRateService service;

    // find latest
    @GetMapping("/latest")
    public ExchangeRateDto getLatest(ExchangeRateDateFilter filter) {
        return service.getByDate(filter);
    }

    // find by date
    @GetMapping("/{date}")
    public ExchangeRateDto getRatesByDate(ExchangeRateDateFilter filter) {
        return service.getByDate(filter);
    }


    // find by history
    @GetMapping("/history")
    public List<ExchangeRateDto> getHistoryRates(ExchangeRateHistoryFilter filter) {
        return service.getHistoryRates(filter);
    }
}
