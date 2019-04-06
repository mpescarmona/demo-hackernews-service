package com.mpescarmona.api.demo.controller;

import com.mpescarmona.api.demo.domain.HackerNewDecorator;
import com.mpescarmona.api.demo.service.HackerNewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "HackerNews Controller")
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/hacker-news")
@CrossOrigin(origins = "http://localhost:4200")
public class HackerNewController {

    private HackerNewService hackerNewService;

    @ApiOperation(value = "Retrieves all the active HackerNews")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Get all the active hacker news")})
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllActiveHackerNews() {
        log.info("action=getAllActiveHackerNews");
        List<HackerNewDecorator> hackerNewDecorators = hackerNewService.getAllActiveHackerNewsByCreatedTimestampDesc();
        log.info("action=getAllActiveHackerNews, hackerNews={}", hackerNewDecorators);

        return new ResponseEntity<>(hackerNewDecorators, HttpStatus.OK);
    }

    @ApiOperation(value = "Deactivates the HackerNew with the specified hckerNewId")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "The HackerNew was successfully deactivated")})
    @RequestMapping(value = "/{hackerNewId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deactivateHackeNewById(@PathVariable("hackerNewId") String hackerNewId) {
        log.info("action=deactivateHackeNewById, hackerNewId={}", hackerNewId);
        hackerNewService.deactivateHackeNewById(hackerNewId);
        log.info("action=deactivateHackeNewById, hackerNew with hackerNewId={} was deactivated", hackerNewId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
