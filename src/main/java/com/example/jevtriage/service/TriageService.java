package com.example.jevtriage.service;

import com.example.jevtriage.model.TriageResult;
import org.springaicommunity.typesafe.TypeSafeClient;
import org.springaicommunity.typesafe.question.Choice;
import org.springaicommunity.typesafe.question.Score;
import org.springaicommunity.typesafe.response.SystemOneResponse;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TriageService {

    private final TypeSafeClient typeSafeClient;

    public TriageService(TypeSafeClient typeSafeClient) {
        this.typeSafeClient = typeSafeClient;
    }

    public TriageResult triage(String ticket) {

        if (ticket == null || ticket.isBlank()) {
            throw new IllegalArgumentException("Ticket must not be blank");
        }

        SystemOneResponse response = typeSafeClient.systemOne(
                ticket,
                Map.of(
                        "category",
                        Choice.builder()
                              .instructions("Which team should handle this ticket?")
                              .option(
                                      "technical",
                                      "Bugs, outages, errors, integrations and technical problems")
                              .option(
                                      "billing",
                                      "Payments, invoices, charges and refunds")
                              .option(
                                      "security",
                                      "Credentials, suspicious access and security incidents")
                              .option(
                                      "other",
                                      "Anything that does not belong to the other categories")
                              .build(),

                        "action",
                        Choice.builder()
                              .instructions("What should we do with this ticket?")
                              .option(
                                      "auto-route",
                                      "A normal ticket that can be automatically routed")
                              .option(
                                      "human-review",
                                      "An ambiguous or sensitive ticket requiring human review")
                              .option(
                                      "escalate",
                                      "An urgent or high-impact problem requiring escalation")
                              .build(),

                        "severity",
                        Score.of(
                                "How severe is the impact of this ticket?",
                                "Low",
                                "Medium",
                                "High",
                                "Critical" )
                      )
                                                             );

        return new TriageResult(
                response.choiceValue("category"),
                response.choiceValue("action"),
                response.scoreValue("severity")
        );
    }
}