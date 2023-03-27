package com.metechvn.common.util;

import org.apache.commons.lang3.StringUtils;

public class TenantUtil {
    private TenantUtil() {
    }

    public static String tenantFromDomain(String domain) {
        if (StringUtils.isEmpty(domain)) return null;

        return domain.split("\\.")[0];
    }
}
