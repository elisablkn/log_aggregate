package com.sonatus.intern;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;


@Service
public class LogsService {
    private static Cache<String, Log> logs; //a data structure that keeps one log per entry
    
        public LogsService() {
            this.logs = Caffeine.newBuilder().expireAfterWrite(1, TimeUnit.HOURS).build(); //use Caffeine Cache to support concurrent writing and retrieval and set logs to auto delete after 1 hour
        }

        //add a new log
        public static void addLog(Log log) {
            String name = log.getServiceName() + "_" + log.getTimestamp().toString() + "_" + UUID.randomUUID().toString(); //to ensure that all names are unique and entries are thread-safe
            logs.put(name, log);
        }
        
        //returns all log messages for the given serviceName with the time range [start, end]
        public static List<Response> getLogsByServiceAndTime(String serviceName, Instant start, Instant end) {
            //filter logs by serviceName and timestamp
            List<Log> logsFiltered = getLogs().values().stream().filter(log -> log.getServiceName().equals(serviceName))
                                            .filter(log -> (log.getTimestamp().isAfter(start) && log.getTimestamp().isBefore(end)))
                                            .collect(Collectors.toList());
            
            //map filtered logs to responses by only keepin timestamp and message fields for each log                                
            List<Response> responsesUnsorted = logsFiltered.stream().map(log -> new Response(log.getTimestamp(), log.getMessage())).collect(Collectors.toList());

            //return list sorted by timestamps
            return responsesUnsorted.stream().sorted(Comparator.comparing(Response::getTimestamp)).collect(Collectors.toList());
        }
        
        //get all logs
        public static Map<String, Log> getLogs() {
            return logs.asMap();
        }

}