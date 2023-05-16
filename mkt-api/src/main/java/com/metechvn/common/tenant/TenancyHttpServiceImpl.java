package com.metechvn.common.tenant;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.metechvn.tenancy.TenancyHttpService;
import com.metechvn.tenancy.Tenant;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class TenancyHttpServiceImpl implements TenancyHttpService {

    @Value("${services.tenancy.url}")
    private String tenancyUrl;

    @Value("${services.tenancy.path:}")
    private String tenancyPath;

    @Override
    public Tenant find(String name) {
        try {
            var client = new OkHttpClient();
            var builder = Objects.requireNonNull(HttpUrl.parse(tenancyUrl)).newBuilder();
            if (StringUtils.isNotEmpty(tenancyPath)) builder.addPathSegments(tenancyPath);

            builder
                    .addPathSegments("/tenant/connection")
                    .addQueryParameter("name", name);

            var request = new Request.Builder()
                    .url(removeDoubleSlashes(builder.build().toString()))
                    .build();

            try (var response = client.newCall(request).execute()) {
                return new ObjectMapper()
                        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                        .readValue(Objects.requireNonNull(response.body()).byteStream(), Tenant.class);
            }
        } catch (Exception e) {
            return null;
        }
    }

    private String removeDoubleSlashes(String url) {
        return url.replaceAll("(?<!:)//", "/");
    }
}
