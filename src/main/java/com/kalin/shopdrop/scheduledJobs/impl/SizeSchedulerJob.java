package com.kalin.shopdrop.scheduledJobs.impl;

import com.kalin.shopdrop.service.models.LogServiceModel;
import com.kalin.shopdrop.service.services.LogService;
import com.kalin.shopdrop.service.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@EnableAsync
@EnableScheduling
public class SizeSchedulerJob {
    private final ProductService productService;
    private final LogService logService;

    @Autowired
    public SizeSchedulerJob(ProductService productService, LogService logService) {
        this.productService = productService;
        this.logService = logService;
    }

    @Async
    @Scheduled(cron =  "0 0/1 * 1/1 * ?")
    public void scheduledJob() {
        Long productsSize = productService.getProductsSize();

        LogServiceModel logServiceModel = new LogServiceModel();
        logServiceModel.setProductsCount(productsSize);
        logServiceModel.setDescription("Current product size");
        logServiceModel.setTime(LocalDateTime.now());

        this.logService.seedLogInDB(logServiceModel);

    }
}
