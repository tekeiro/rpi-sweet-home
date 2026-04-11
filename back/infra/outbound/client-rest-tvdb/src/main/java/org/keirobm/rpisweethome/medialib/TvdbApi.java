package org.keirobm.rpisweethome.medialib;


import com.tvdb.v4.api.LoginApi;
import com.tvdb.v4.api.SearchApi;
import com.tvdb.v4.model.LoginPostRequest;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keirobm.rpisweethome.config.TvdbApiConfigProps;
import org.springframework.stereotype.Component;

@Component
@Getter
@RequiredArgsConstructor
@Slf4j
public class TvdbApi {

    private final LoginApi loginApi;

    private final TvdbApiConfigProps configProps;

    private final SearchApi searchApi;

    @PostConstruct
    public void login() {
        log.info("Login to TVDB....");
        final var loginRequest = new LoginPostRequest();
        loginRequest.setApikey(this.configProps.getApiKey());
        loginRequest.setPin(null);

        final var response = loginApi.loginPost(loginRequest);
        final var token = response.getData().getToken();
        loginApi.getApiClient().setBearerToken(token);
        log.info("Login to TVDB successful!");
    }

}
