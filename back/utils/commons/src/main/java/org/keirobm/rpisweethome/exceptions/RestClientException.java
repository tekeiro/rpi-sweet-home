package org.keirobm.rpisweethome.exceptions;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@RequiredArgsConstructor
@Builder( toBuilder = true )
public class RestClientException extends RuntimeException {
    private final int httpCode;
    private final Map<String, List<String>> responseHeaders;
    private final String responseBody;
    private final Exception cause;

}
