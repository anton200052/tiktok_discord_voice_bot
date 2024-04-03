package me.vasylkov.discordvoicebot.jda.configuration;

import lombok.RequiredArgsConstructor;
import me.vasylkov.discordvoicebot.jda.controller.MessagesListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class JDAConfiguration
{
    @Value("${bot_token}")
    private String token;

    private final MessagesListener messagesListener;

    @Bean
    public JDA jda()
    {
        return JDABuilder.createDefault(token)
                .addEventListeners(messagesListener)
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .build();
    }
}
