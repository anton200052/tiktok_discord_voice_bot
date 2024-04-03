package me.vasylkov.discordvoicebot.tiktok.controller;

import io.github.jwdeveloper.tiktok.annotations.TikTokEventObserver;
import io.github.jwdeveloper.tiktok.data.events.TikTokCommentEvent;
import io.github.jwdeveloper.tiktok.data.events.TikTokConnectedEvent;
import io.github.jwdeveloper.tiktok.data.events.TikTokDisconnectedEvent;
import io.github.jwdeveloper.tiktok.data.events.TikTokErrorEvent;
import io.github.jwdeveloper.tiktok.listener.TikTokEventListener;
import io.github.jwdeveloper.tiktok.live.LiveClient;
import lombok.RequiredArgsConstructor;
import me.vasylkov.discordvoicebot.jda.ApplicationLifecycleMarkers;
import me.vasylkov.discordvoicebot.jda.service.MessagesSender;
import me.vasylkov.discordvoicebot.tiktok.service.CommentToAudioService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TiktokListener implements TikTokEventListener
{
    private final CommentToAudioService service;
    private final MessagesSender messagesSender;
    private final ApplicationLifecycleMarkers applicationLifecycleMarkers;

    @TikTokEventObserver
    public synchronized void onComment(LiveClient liveClient, TikTokCommentEvent event)
    {
        String text = event.getText();
        service.convertCommentToAudio(text);
    }

    @TikTokEventObserver
    public synchronized void onConnect(LiveClient liveClient, TikTokConnectedEvent event)
    {
        messagesSender.sendMessageToLastChannel("OnConnect event connection: " + liveClient.getRoomInfo().getConnectionState());
        if (!applicationLifecycleMarkers.isConnectedToTickTok())
            applicationLifecycleMarkers.setConnectedToTickTok(true);
    }

    @TikTokEventObserver
    public synchronized void onDisconnect(LiveClient liveClient, TikTokDisconnectedEvent event)
    {
        messagesSender.sendMessageToLastChannel("OnDisconnect event connection: " + liveClient.getRoomInfo().getConnectionState());
        if (applicationLifecycleMarkers.isConnectedToTickTok())
            applicationLifecycleMarkers.setConnectedToTickTok(false);
    }

    @TikTokEventObserver
    public synchronized void onError(LiveClient liveClient, TikTokErrorEvent event)
    {
        messagesSender.sendMessageToLastChannel("TiktokError with reason: " + event.getException().getMessage());
        messagesSender.sendMessageToLastChannel("Connection state after error: " + liveClient.getRoomInfo().getConnectionState());
    }
}
