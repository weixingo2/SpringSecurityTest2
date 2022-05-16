package com.springSecurity.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 腾讯云cos上传文件配置
 */
@Data
@Component
@ConfigurationProperties(prefix = "tencent.cos")
public class TencentCosProperties4Picture{

    private String appId;
    private String secretId;
    private String secretKey;
    private String bucketName;
    private String regionId;
    private String baseUrl;

}
