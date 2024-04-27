package fr.sqli.formation.gamelife.controller;

import fr.sqli.formation.gamelife.dto.response.GameResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class SearchRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchRestController.class);


    @GetMapping("/search/{name}")
    public ResponseEntity<List<GameResponse>> findGameContainsName(@PathVariable("name") String pName) {
        try {
            //var result = this.service.findGameContainsName(pName);
            //return new ResponseEntity<>(result, HttpStatus.OK);
        } catch(Exception pException) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return null;
    }
}
