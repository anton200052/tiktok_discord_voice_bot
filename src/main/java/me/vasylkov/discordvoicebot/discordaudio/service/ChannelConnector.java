package me.vasylkov.discordvoicebot.discordaudio.service;

import lombok.RequiredArgsConstructor;
import me.vasylkov.discordvoicebot.discordaudio.component.AudioPlayerSendHandler;
import net.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import net.dv8tion.jda.api.managers.AudioManager;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChannelConnector
{
    private final AudioPlayerSendHandler sendHandler;

    public String connect(AudioManager audioManager, AudioChannel audioChannel)
    {
        if (audioManager.getSendingHandler() == null)
        {
            audioManager.setSendingHandler(sendHandler);
        }
        audioManager.openAudioConnection(audioChannel);
        return "Connected";
    }

    public String disconnect(AudioManager audioManager)
    {
        audioManager.closeAudioConnection();
        return "Disconnected";
    }
}
