package me.vasylkov.discordvoicebot.discordaudio.service;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import lombok.RequiredArgsConstructor;
import me.vasylkov.discordvoicebot.discordaudio.component.AudioPlayerLoadResultHandler;
import me.vasylkov.discordvoicebot.discordaudio.component.AudioPlayerSendHandler;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AudioLoader
{
    private final AudioPlayerManager audioPlayerManager;
    private final AudioPlayerLoadResultHandler resultHandler;

    public void loadAudio(String identifier)
    {
        audioPlayerManager.loadItem(identifier, resultHandler);
    }
}
