package ch.fhnw.deardevbackend.services;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class JiraService {

    @Value("${jira.api.base.url}")
    private String jiraApiBaseUrl;

    @Value("${jira.api.token}")
    private String jiraApiToken;

    private final RestTemplate restTemplate;

    public JiraService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // temp. method to test Jira API - later on the user should be able to authenticate with Jira
    public String getJiraTasks() {
        String url = jiraApiBaseUrl + "/rest/api/2/search?jql=assignee=currentuser()";

        HttpHeaders headers = new HttpHeaders();
        String auth = "smuefsmuef@gmail.com" + ":" + jiraApiToken;
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.US_ASCII));
        String authHeader = "Basic " + new String(encodedAuth);
        headers.set("Authorization", authHeader);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        return response.getBody();
    }
}
