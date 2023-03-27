package com.metechvn.common.tenant;

import com.metechvn.common.Constants;
import com.metechvn.common.CurrentTenantProvider;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class Domain2TenantInterceptor implements HandlerInterceptor {

    private final CurrentTenantProvider currentTenantProvider;

    @Override
    public boolean preHandle(HttpServletRequest req, @NotNull HttpServletResponse res, @NotNull Object handler) {
        if (DispatcherType.REQUEST.name().equals(req.getDispatcherType().name())) {
            var domainName = req.getHeader(Constants.DOMAIN_HEADER);
            if (StringUtils.isNotEmpty(domainName)) {
                currentTenantProvider.setTenant(domainName.split("\\.")[0]);
            }
        }

        return true;
    }

}
