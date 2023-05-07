package com.graphy.backend.global.chatgpt.dto.service;

import com.theokanning.openai.completion.CompletionResult;
import com.theokanning.openai.service.OpenAiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.graphy.backend.global.chatgpt.dto.GptCompletionDto.GptCompletionRequest;
import static com.graphy.backend.global.chatgpt.dto.GptCompletionDto.GptCompletionResponse;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GPTChatRestService {
    private final OpenAiService openAiService;

    @Transactional
    public GptCompletionResponse completion(final GptCompletionRequest request) {
        CompletionResult result = openAiService.createCompletion(GptCompletionRequest.of(request));
        GptCompletionResponse response = GptCompletionResponse.of(result);

        return response;
    }
}