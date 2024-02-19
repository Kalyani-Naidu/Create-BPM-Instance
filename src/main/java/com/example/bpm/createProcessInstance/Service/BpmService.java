package com.example.bpm.createProcessInstance.Service;


import java.net.URI;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;


import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BpmService {
    
    private WebClient webclient;
    
    public BpmService(WebClient webclient) {
        this.webclient = webclient;
    }
    
    Logger log =  LoggerFactory.getLogger(BpmService.class);
    
    public String invokeBPMEndpoint(String baseUrl, String credentials, String params) {
        log.info(" in bpm service"+params);
        try {
            String token = generateBasicAuthToken(credentials);
            log.info("token: "+token);
            
            // Construct the URI with the additional parameter
        //    URI expandedURI = new UriTemplate(baseUrl).expand(new ObjectMapper().writeValueAsString(params));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl);
                        
            URI expandedURI = builder
            						.queryParam("action", "start")
            						.queryParam("params", params)
            						.queryParam("snapshotId", "2064.8bfd5125-d7bb-4f07-b5cf-57f7656d579e")
            						.queryParam("createTask", "true")
            						.queryParam("parts", "all")
            						.build()
            						.toUri();
            		
            // You can add the additional parameter to the URI here
            
            log.info("Expanded URI: "+expandedURI);
            String response = webclient
                                .post()
                                .uri(expandedURI)
                                .header("Authorization", token)
                                .retrieve()
                                .bodyToMono(String.class)
                                .block();
            log.info("End of invokeBPMEndpoint: "+response);
            return response;
        }
        catch(Exception e){
            log.error("Error in invokeBPMEndpoint: "+params, e);
            throw new InvalidDataAccessApiUsageException("Bpm API Communication failed ", e);
        }
        
    }

    private String generateBasicAuthToken(String credentials) {
        return "Basic "+Base64.getEncoder().encodeToString(credentials.getBytes());
    }   

}
