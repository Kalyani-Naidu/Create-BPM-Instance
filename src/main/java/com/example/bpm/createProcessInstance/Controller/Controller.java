package com.example.bpm.createProcessInstance.Controller;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bpm.createProcessInstance.Service.BpmService;


@RestController
@RequestMapping("/bpm")
public class Controller {
	
	Logger log =  LoggerFactory.getLogger(Controller.class);
	
	private final BpmService bpmService;

    public Controller(BpmService bpmService) {
        this.bpmService = bpmService;
    }
    
    @Value("${bpm.base-url}")
    String baseUrl;
    
    @Value("${bpm.credentials}")
    String credentials;
    
    Random random = new Random();
    int randomNumber = random.nextInt(99999999); 
    
    String referenceNum = "DEMO_"+String.valueOf(randomNumber);
    
   String params = "{\"referenceNum\":\"" + referenceNum + "\"}";

    @PostMapping("/start-process")
    public String startBpmProcess() {
        log.info("Start BPM Process");
        
        return bpmService.invokeBPMEndpoint(baseUrl, credentials, params);
    }

}
