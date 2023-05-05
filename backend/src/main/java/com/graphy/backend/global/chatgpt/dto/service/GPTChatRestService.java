package com.graphy.backend.global.chatgpt.dto.service;

import com.graphy.backend.domain.plan.domain.Plan;
import com.graphy.backend.domain.plan.repository.PlanRepository;
import com.theokanning.openai.completion.CompletionResult;
import com.theokanning.openai.service.OpenAiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.graphy.backend.global.chatgpt.dto.GptCompletionDto.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GPTChatRestService {
    private final OpenAiService openAiService;
    private final PlanRepository planRepository;

    @Transactional
    public GptCompletionResponse completion(final GptCompletionRequest request) {
        CompletionResult result = openAiService.createCompletion(GptCompletionRequest.of(request));
        GptCompletionResponse response = GptCompletionResponse.of(result);

        List<String> messages = response.getMessages().stream()
                .map(Message::getText)
                .collect(Collectors.toList());

        // 저장 로직
        Plan plan = saveAnswer(messages);

        return response;
    }

    private Plan saveAnswer(List<String> response) {

        String answer = response.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.joining());

        return planRepository.save(new Plan(answer));
    }
}
