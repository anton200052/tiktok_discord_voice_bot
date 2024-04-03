package me.vasylkov.discordvoicebot.tiktok.service;

import lombok.RequiredArgsConstructor;
import me.vasylkov.discordvoicebot.amazonpolly.service.PollySynthesizer;
import me.vasylkov.discordvoicebot.discordaudio.service.AudioLoader;
import me.vasylkov.discordvoicebot.discordaudio.service.TempSynthAudioFileManager;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

@Service
@RequiredArgsConstructor
public class CommentToAudioService
{
    private final PollySynthesizer pollySynthesizer;
    private final AudioLoader audioLoader;
    private final TempSynthAudioFileManager audioFileManager;

    public void convertCommentToAudio(String comment)
    {
        try (InputStream synthText = pollySynthesizer.synthesize(comment))
        {
            Path path = audioFileManager.createTempFile(synthText);
            audioLoader.loadAudio(path.toAbsolutePath().toString());
            /*audioFileManager.deleteTempFile(path);*/
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
