package com.mybots.pebo.dialogflow;

import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.dialogflow.v2beta1.*;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.util.UUID;

import static com.mybots.pebo.util.PeboConstants.DEFAULT_LANGUAGE_CODE;

@Component
public class DialogflowClientImpl implements DialogflowClient {

    String projectId = "";

    String key = "";

    public String query(String queryText) throws Exception {
        CredentialsProvider credentialsProvider = FixedCredentialsProvider.create(GoogleCredentials.fromStream(new ByteArrayInputStream(key.getBytes())));
        SessionsSettings sessionsSettings = SessionsSettings.newBuilder().setCredentialsProvider(credentialsProvider).build();
        try (SessionsClient sessionsClient = SessionsClient.create(sessionsSettings)) {
            String sessionId = UUID.randomUUID().toString();
            SessionName session = SessionName.of(projectId, sessionId);
            System.out.println("Session Path: " + session.toString());
            TextInput.Builder textInput =
                    TextInput.newBuilder().setText(queryText).setLanguageCode(DEFAULT_LANGUAGE_CODE);
            QueryInput queryInput = QueryInput.newBuilder().setText(textInput).build();
            DetectIntentResponse response = sessionsClient.detectIntent(session, queryInput);
            QueryResult queryResult = response.getQueryResult();
            System.out.println("====================");
            System.out.format("Query Text: '%s'\n", queryResult.getQueryText());
            System.out.format("Detected Intent: %s (confidence: %f)\n",
                    queryResult.getIntent().getDisplayName(), queryResult.getIntentDetectionConfidence());
            System.out.format("Fulfillment Text: '%s'\n", queryResult.getFulfillmentText());
            return queryResult.getFulfillmentText();
        } catch (Exception ex) {
            throw ex;
        }
    }
}
