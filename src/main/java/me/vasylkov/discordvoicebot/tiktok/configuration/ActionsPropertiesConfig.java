package me.vasylkov.discordvoicebot.tiktok.configuration;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.HashMap;
import java.util.Map;

@Data
@ConfigurationProperties()
public class ActionsPropertiesConfig
{
    private Map<String, String> actions = new HashMap<>();
}
