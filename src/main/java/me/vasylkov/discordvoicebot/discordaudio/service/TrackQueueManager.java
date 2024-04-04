package me.vasylkov.discordvoicebot.discordaudio.service;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@RequiredArgsConstructor
@Service
public class TrackQueueManager
{
    private final BlockingQueue<AudioTrack> queue = new LinkedBlockingQueue<>();
    private final ObjectProvider<AudioPlayer> audioPlayerProvider;

    public void addToQueue(AudioTrack track)
    {
        if (!getAudioPlayer().startTrack(track, true))
        {
            queue.offer(track);
        }
    }

    public void playTrackImmediately(AudioTrack track)
    {
        getAudioPlayer().startTrack(track, false);
    }

    public void nextTrack()
    {
        getAudioPlayer().startTrack(queue.poll(), false);
    }

    private AudioPlayer getAudioPlayer()
    {
        return audioPlayerProvider.getIfAvailable();
    }
}
