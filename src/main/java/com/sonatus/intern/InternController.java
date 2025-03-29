package com.sonatus.intern;

import java.time.Instant;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/logs")
public class InternController {
    
    @PostMapping
    public String ingestLog(@RequestBody Log log) {
        LogsService.addLog(log);
        return "Done";
    }
    
    @GetMapping
    public List<Response> getLogsFiltered(@RequestParam String service, @RequestParam Instant start, @RequestParam Instant end) {
        return LogsService.getLogsByServiceAndTime(service, start, end);
    }

}
