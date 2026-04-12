package org.keirobm.rpisweethome.utils;

import com.tvdb.v4.ApiException;
import lombok.experimental.UtilityClass;
import org.keirobm.rpisweethome.exceptions.RestClientException;

@UtilityClass
public class ToRestClientException {

    public RestClientException toRestClientException(ApiException apiException) {
        return new RestClientException(apiException.getCode(),
                apiException.getResponseHeaders(), apiException.getResponseBody(),
                apiException);
    }

}
