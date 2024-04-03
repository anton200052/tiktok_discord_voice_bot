package me.vasylkov.discordvoicebot.tiktok.configuration;

import io.github.jwdeveloper.tiktok.TikTokLive;
import io.github.jwdeveloper.tiktok.live.LiveClient;
import lombok.RequiredArgsConstructor;
import me.vasylkov.discordvoicebot.tiktok.controller.TiktokListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class TiktokClientConfiguration
{
    @Value("${tiktok_hostname}")
    private String hostName;

    private final TiktokListener tiktokListener;

    @Bean
    public LiveClient liveClient()
    {
        return TikTokLive.newClient(hostName).addListener(tiktokListener).build();
    }
}
