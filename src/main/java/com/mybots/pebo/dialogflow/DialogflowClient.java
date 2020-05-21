package com.mybots.pebo.dialogflow;

import com.google.cloud.dialogflow.v2beta1.QueryResult;

public interface DialogflowClient {

    public String query(String queryText) throws Exception;
}
