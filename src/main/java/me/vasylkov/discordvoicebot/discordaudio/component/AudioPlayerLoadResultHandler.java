package me.vasylkov.discordvoicebot.discordaudio.component;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import lombok.RequiredArgsConstructor;
import me.vasylkov.discordvoicebot.discordaudio.service.TrackQueueManager;
import me.vasylkov.discordvoicebot.jda.service.MessagesSender;
import me.vasylkov.discordvoicebot.tiktok.service.ActionsService;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
@RequiredArgsConstructor
public class AudioPlayerLoadResultHandler implements AudioLoadResultHandler
{
    private final TrackQueueManager manager;
    private final MessagesSender sender;
    private final ActionsService actionsService;

    @Override
    public void trackLoaded(AudioTrack track)
    {
        if (actionsService.isActionFile(Path.of(track.getIdentifier()).getFileName().toString()))
        {
            manager.playTrackImmediately(track);
        }
        else
        {
            manager.addToQueue(track);
        }
    }

    @Override
    public void playlistLoaded(AudioPlaylist playlist)
    {
        // Можно реализовать логику воспроизведения всего плейлиста или первого трека из плейлиста
        /*AudioTrack firstTrack = playlist.getSelectedTrack() != null ? playlist.getSelectedTrack() : playlist.getTracks().get(0);
        audioPlayer.playTrack(firstTrack);*/
    }

    @Override
    public void noMatches()
    {
        sender.sendMessageToLastChannel("Ошибка загрузки трека. Трек НЕ найден");
    }

    @Override
    public void loadFailed(FriendlyException exception)
    {
        sender.sendMessageToLastChannel("Ошибка загрузки трека. С причиной: " + exception.getMessage());
    }
}
