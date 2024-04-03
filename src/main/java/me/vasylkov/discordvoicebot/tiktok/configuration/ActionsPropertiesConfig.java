package me.vasylkov.discordvoicebot.tiktok.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "actions")
public class ActionsPropertiesConfig
{
    private Map<String, String> actions;
}
