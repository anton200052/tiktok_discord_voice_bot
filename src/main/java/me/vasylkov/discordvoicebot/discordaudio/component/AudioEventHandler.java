package me.vasylkov.discordvoicebot.discordaudio.component;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import lombok.RequiredArgsConstructor;
import me.vasylkov.discordvoicebot.discordaudio.service.TrackQueueManager;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class AudioEventHandler extends AudioEventAdapter
{
    private final TrackQueueManager manager;

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason)
    {
        if (endReason.mayStartNext)
        {
            try
            {
                Thread.sleep(2000);
            }
            catch (InterruptedException e)
            {
                throw new RuntimeException(e);
            }

            manager.nextTrack();
        }
    }
}
