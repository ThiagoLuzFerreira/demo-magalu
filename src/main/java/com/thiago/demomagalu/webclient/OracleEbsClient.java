package com.thiago.demomagalu.webclient;

import com.thiago.demomagalu.webclient.dto.OracleEbsResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@Component
@FeignClient(name = "oracleebs", url = "localhost:8081", path = "/oracle/ebs")
public interface OracleEbsClient {

    @GetMapping
    ResponseEntity<OracleEbsResponseDTO> getOracleEbsTransaction();
}
