package com.mybots.pebo.controller;

import com.google.cloud.dialogflow.v2beta1.QueryResult;
import com.mybots.pebo.dialogflow.DialogflowClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.mybots.pebo.util.PeboConstants.DEFAULT_QUERY_VALUE;

@RestController
@RequestMapping("/api")
@Slf4j
public class PeboController {

    @Autowired
    private DialogflowClient dialogflowClient;

    @GetMapping("/chat")
    public String chatWithPebo(@RequestParam(name="query", defaultValue = "Hi") String queryText) {
        //queryText = StringUtils.isNotBlank(queryText) ? queryText : DEFAULT_QUERY_VALUE;
        try {
            return dialogflowClient.query(queryText);
        } catch (Exception ex) {
            log.error("Error occured while communicating with dialogflow message:{}", ex.getMessage(), ex);
            return null;
        }
    }
}
