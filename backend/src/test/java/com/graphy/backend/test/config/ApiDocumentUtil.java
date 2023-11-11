package com.graphy.backend.test.config;
import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;

public interface ApiDocumentUtil {
    static OperationRequestPreprocessor getDocumentRequest() {
        return preprocessRequest(
                removeHeaders("Content-Type", "Accept", "Content-Length", "Host"),
                prettyPrint()
        );
    }

    static OperationResponsePreprocessor getDocumentResponse() {
        return preprocessResponse(
                removeHeaders("Content-Type", "Vary", "Accept", "Content-Length", "Host"),
                prettyPrint()
        );
    }
}