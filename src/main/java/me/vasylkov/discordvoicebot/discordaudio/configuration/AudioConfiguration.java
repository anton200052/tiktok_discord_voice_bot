package me.vasylkov.discordvoicebot.discordaudio.configuration;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventListener;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import me.vasylkov.discordvoicebot.discordaudio.component.AudioEventHandler;
import me.vasylkov.discordvoicebot.discordaudio.service.TrackQueueManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AudioConfiguration
{
    private final AudioEventHandler audioEventHandler;

    @Bean
    public AudioPlayerManager audioPlayerManager()
    {
        AudioPlayerManager manager = new DefaultAudioPlayerManager();
        AudioSourceManagers.registerLocalSource(manager);
        return manager;
    }

    @Bean
    public AudioPlayer audioPlayer(AudioPlayerManager audioPlayerManager)
    {
        AudioPlayer audioPlayer = audioPlayerManager.createPlayer();
        audioPlayer.addListener(audioEventHandler);
        return audioPlayer;
    }
}
