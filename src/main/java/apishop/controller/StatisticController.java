package apishop.controller;

import apishop.exception.core.ArchitectureException;
import apishop.facade.StatisticFacade;
import apishop.model.common.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

import static apishop.util.api.ConstantsApi.Statistic.STATISTIC_PATH;

@RestController
@RequiredArgsConstructor
@RequestMapping(STATISTIC_PATH)
public class StatisticController {

    private final StatisticFacade statisticFacade;
//    @GetMapping("/top-account")
//    public ResponseEntity<Object> getTopAccount(@RequestParam(defaultValue = "10") Integer top) throws ArchitectureException {
//        return ResponseHandler.response(HttpStatus.OK, statisticFacade.getTopAccount(top), true);
//    }
//
//    @GetMapping("/top-product")
//    @Operation(summary = "Get top product", description = "Get top product hava highest revenue, anyone can access")
//    public ResponseEntity<Object> getTopProduct(@RequestParam(defaultValue = "10") Integer top) throws ArchitectureException {
//        return ResponseHandler.response(HttpStatus.OK, statisticFacade.getTopProduct(top), true);
//    }

    @GetMapping("/income")
    public ResponseEntity<Object> getIncome(@RequestParam String year,
                                            @RequestParam(required = false) String month) throws ArchitectureException {
        return ResponseHandler.response(HttpStatus.OK, statisticFacade.getIncome(year, month), true);
    }
}
