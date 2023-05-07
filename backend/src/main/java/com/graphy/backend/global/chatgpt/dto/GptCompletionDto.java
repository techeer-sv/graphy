package com.graphy.backend.global.chatgpt.dto;

import com.theokanning.openai.completion.CompletionChoice;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.CompletionResult;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

public class GptCompletionDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GptCompletionRequest {

        private String model;
        private String prompt;
        private Integer maxToken;

        public static CompletionRequest of(GptCompletionRequest restRequest) {
            return CompletionRequest.builder()
                    .model(restRequest.getModel())
                    .prompt(restRequest.getPrompt())
                    .maxTokens(restRequest.getMaxToken())
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GptCompletionResponse {

        private String id;
        private String object;
        private Long created;
        private String model;
        private List<Message> messages;

        private Usage usage;

        public static List<Message> toResponseListBy(List<CompletionChoice> choices) {
            return choices.stream()
                    .map(Message::of)
                    .collect(Collectors.toList());
        }

        public static GptCompletionResponse of(CompletionResult result) {
            return new GptCompletionResponse(
                    result.getId(),
                    result.getObject(),
                    result.getCreated(),
                    result.getModel(),
                    toResponseListBy(result.getChoices()),
                    Usage.of(result.getUsage())
            );
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Message {

        private String text;
        private Integer index;
        private String finishReason;

        public static Message of(CompletionChoice choice) {
            return new Message(
                    choice.getText(),
                    choice.getIndex(),
                    choice.getFinish_reason()
            );
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Usage {

        private Long promptTokens;
        private Long completionTokens;
        private Long totalTokens;

        public static Usage of(com.theokanning.openai.Usage usage) {
            return new Usage(
                    usage.getPromptTokens(),
                    usage.getCompletionTokens(),
                    usage.getTotalTokens()
            );
        }
    }
}
