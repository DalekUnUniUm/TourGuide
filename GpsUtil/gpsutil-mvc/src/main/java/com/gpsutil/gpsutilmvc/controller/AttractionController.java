package com.gpsutil.gpsutilmvc.controller;

import com.gpsutil.gpsutilmvc.model.Attraction;
import com.gpsutil.gpsutilmvc.service.AttractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AttractionController {

    @Autowired
    private AttractionService attractionService ;

    @GetMapping("/getAttractions")
    public List<Attraction> getAttractions(){
        return attractionService.getAttractions() ;
    }

}
