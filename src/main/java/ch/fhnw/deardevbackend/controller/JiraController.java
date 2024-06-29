package ch.fhnw.deardevbackend.controller;

import ch.fhnw.deardevbackend.services.JiraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/jira")
public class JiraController {

    @Autowired
    private JiraService jiraService;

    @GetMapping("/tasks")
    public String getJiraTasks() {
        return jiraService.getJiraTasks();
    }
}
