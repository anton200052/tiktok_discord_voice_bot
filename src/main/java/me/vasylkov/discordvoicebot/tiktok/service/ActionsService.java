package me.vasylkov.discordvoicebot.tiktok.service;

import lombok.RequiredArgsConstructor;
import me.vasylkov.discordvoicebot.tiktok.configuration.ActionsPropertiesConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActionsService
{
    private final ActionsPropertiesConfig actionConfig;
    @Value("${audio_local_source_path}")
    private String audioFolder;

    public String getFilePathByAction(String actionName)
    {
        return audioFolder + "/" + actionConfig.getActions().getOrDefault(actionName.toUpperCase(), "default.mp3");
    }

    public boolean isActionFile(String fileName)
    {
        return actionConfig.getActions().containsValue(fileName);
    }
}
