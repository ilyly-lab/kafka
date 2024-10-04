package com.example;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MetricsController {

    @Timed(value = "method.execution.time", description = "Time taken to execute the method")
    @Counted(value = "method.invocation.count", description = "Number of method invocations")
    @GetMapping("/trigger-metrics")
    public String triggerMetrics() {
        //бизнес
        return "Metrics triggered!";
    }

}
