package me.vasylkov.discordvoicebot;

import me.vasylkov.discordvoicebot.tiktok.configuration.ActionsPropertiesConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@SpringBootApplication
@EnableConfigurationProperties({ActionsPropertiesConfig.class})
public class DiscordvoicebotApplication
{

    public static void main(String[] args) throws IOException
    {
        SpringApplication.run(DiscordvoicebotApplication.class, args);
    }

}
