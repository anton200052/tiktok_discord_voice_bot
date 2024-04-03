package me.vasylkov.discordvoicebot.discordaudio.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
public class TempSynthAudioFileManager
{

    public Path createTempFile(InputStream inputStream) throws IOException
    {
        Path tempFile = Files.createTempFile("temp-audio", ".mp3");
        Files.copy(inputStream, tempFile, StandardCopyOption.REPLACE_EXISTING);
        return tempFile;
    }

    public void deleteTempFile(Path path) throws IOException
    {
        Files.delete(path);
    }
}
