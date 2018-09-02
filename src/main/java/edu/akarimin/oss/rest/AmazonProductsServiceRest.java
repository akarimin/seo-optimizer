package edu.akarimin.oss.rest;

import edu.akarimin.oss.domain.Estimate;
import edu.akarimin.oss.service.AmazonProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.lang.String.format;

/**
 * @author akarimin
 */

@RestController
@RequestMapping("/api/")
public class AmazonProductsServiceRest {

    @Autowired
    private AmazonProductsService<String, Estimate> amazonProductsService;

    @GetMapping("/estimate/{keyword}")
    public ResponseEntity<Estimate> estimate(@PathVariable String keyword) {
        long start = System.currentTimeMillis();
        if (keyword.length() > 10)
            keyword = keyword.substring(0, 10);
        Estimate response = amazonProductsService.asyncEstimate(keyword.trim().replaceAll(" ", ""));
        long end = System.currentTimeMillis();
        System.out.println(format("\nRESPONSE: %s \nExecution took %d milliSeconds.", response, (end - start)));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
