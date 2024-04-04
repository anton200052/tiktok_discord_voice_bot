package me.vasylkov.discordvoicebot.tiktok.controller;

import io.github.jwdeveloper.tiktok.annotations.TikTokEventObserver;
import io.github.jwdeveloper.tiktok.data.events.TikTokCommentEvent;
import io.github.jwdeveloper.tiktok.data.events.TikTokConnectedEvent;
import io.github.jwdeveloper.tiktok.data.events.TikTokDisconnectedEvent;
import io.github.jwdeveloper.tiktok.data.events.TikTokErrorEvent;
import io.github.jwdeveloper.tiktok.data.events.gift.TikTokGiftEvent;
import io.github.jwdeveloper.tiktok.data.events.social.TikTokFollowEvent;
import io.github.jwdeveloper.tiktok.data.events.social.TikTokShareEvent;
import io.github.jwdeveloper.tiktok.listener.TikTokEventListener;
import io.github.jwdeveloper.tiktok.live.LiveClient;
import lombok.RequiredArgsConstructor;
import me.vasylkov.discordvoicebot.discordaudio.service.AudioLoader;
import me.vasylkov.discordvoicebot.jda.ApplicationLifecycleMarkers;
import me.vasylkov.discordvoicebot.jda.service.MessagesSender;
import me.vasylkov.discordvoicebot.amazonpolly.service.TextToAudioService;
import me.vasylkov.discordvoicebot.tiktok.service.ActionsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TiktokListener implements TikTokEventListener
{
    private final TextToAudioService textToAudioService;
    private final ActionsService actionsService;
    private final MessagesSender messagesSender;
    private final ApplicationLifecycleMarkers applicationLifecycleMarkers;
    private final AudioLoader audioLoader;


    @TikTokEventObserver
    public synchronized void onComment(LiveClient liveClient, TikTokCommentEvent event)
    {
        String text = event.getText();
        String filePath = textToAudioService.convertCommentToAudioFile(text);
        audioLoader.loadAudio(filePath);
    }

    @TikTokEventObserver
    public synchronized void onConnect(LiveClient liveClient, TikTokConnectedEvent event)
    {
        messagesSender.sendMessageToLastChannel("OnConnect event connection: " + liveClient.getRoomInfo().getConnectionState());
        if (!applicationLifecycleMarkers.isConnectedToTickTok())
            applicationLifecycleMarkers.setConnectedToTickTok(true);
    }

    @TikTokEventObserver
    public synchronized void onGift(LiveClient liveClient, TikTokGiftEvent event)
    {
        audioLoader.loadAudio(actionsService.getFilePathByAction(event.getGift().toString()));
    }

    @TikTokEventObserver
    public synchronized void onFollow(LiveClient liveClient, TikTokFollowEvent event)
    {
        audioLoader.loadAudio(actionsService.getFilePathByAction("FOLLOW"));
    }

    /*@TikTokEventObserver
    public synchronized void onShare(LiveClient liveClient, TikTokShareEvent event)
    {
        audioLoader.loadAudio(actionsService.getFilePathByAction("SHARE"));
    }*/

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
