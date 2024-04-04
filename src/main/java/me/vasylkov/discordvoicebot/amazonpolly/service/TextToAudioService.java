package me.vasylkov.discordvoicebot.amazonpolly.service;

import lombok.RequiredArgsConstructor;
import me.vasylkov.discordvoicebot.discordaudio.service.TempSynthAudioFileManager;
import me.vasylkov.discordvoicebot.jda.service.MessagesSender;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

@Service
@RequiredArgsConstructor
public class TextToAudioService
{
    private final PollySynthesizer pollySynthesizer;
    private final TempSynthAudioFileManager audioFileManager;
    private final MessagesSender messagesSender;

    public String convertCommentToAudioFile(String comment)
    {
        try (InputStream synthText = pollySynthesizer.synthesize(comment))
        {
            Path path = audioFileManager.createTempFile(synthText);
            return path.toAbsolutePath().toString();
            /*audioFileManager.deleteTempFile(path);*/
        }
        catch (IOException e)
        {
            messagesSender.sendMessageToLastChannel("Ошибка конвертации текста в аудио с причиной: " + e.getMessage());
            return null;
        }
    }
}
