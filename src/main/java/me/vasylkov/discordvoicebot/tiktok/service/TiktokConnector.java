package me.vasylkov.discordvoicebot.tiktok.service;

import io.github.jwdeveloper.tiktok.live.LiveClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TiktokConnector
{
    private final LiveClient liveClient;

    public String connect()
    {
        liveClient.connect();
        return "Статус подключения: " + liveClient.getRoomInfo().getConnectionState();
    }

    public String disconnect()
    {
        liveClient.disconnect();
        return "Статус подключения: " + liveClient.getRoomInfo().getConnectionState();
    }
}
