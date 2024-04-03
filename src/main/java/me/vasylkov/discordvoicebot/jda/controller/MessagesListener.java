package me.vasylkov.discordvoicebot.jda.controller;

import lombok.RequiredArgsConstructor;
import me.vasylkov.discordvoicebot.discordaudio.service.ChannelConnector;
import me.vasylkov.discordvoicebot.jda.ApplicationLifecycleMarkers;
import me.vasylkov.discordvoicebot.jda.service.MessagesSender;
import me.vasylkov.discordvoicebot.tiktok.service.TiktokConnector;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.entities.channel.unions.AudioChannelUnion;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessagesListener extends ListenerAdapter
{
    private final MessagesSender messagesSender;
    private final ChannelConnector channelConnector;
    private final TiktokConnector tiktokConnector;
    private final ApplicationLifecycleMarkers applicationLifecycleMarkers;

    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
        if (event.getAuthor().isBot()) return;
        if (!event.isFromGuild()) return;

        MessageChannel messageChannel = event.getChannel();
        AudioChannelUnion audioChannelUnion = event.getMember().getVoiceState().getChannel();
        if (audioChannelUnion == null)
        {
            messagesSender.sendMessage(messageChannel, "Вы должны находится в войс-канале!");
            return;
        }
        VoiceChannel voiceChannel = audioChannelUnion.asVoiceChannel();
        String content = event.getMessage().getContentRaw();
        AudioManager audioManager = event.getGuild().getAudioManager();

        if (content.equals("!connect_bot"))
        {
            if (applicationLifecycleMarkers.isConnectedToVoice()) return;

            String result = channelConnector.connect(audioManager, voiceChannel);
            messagesSender.sendMessage(messageChannel, result);
            applicationLifecycleMarkers.setConnectedToVoice(true);
        }
        else if (content.equals("!disconnect_bot"))
        {
            if (!applicationLifecycleMarkers.isConnectedToVoice()) return;

            String result = channelConnector.disconnect(audioManager);
            messagesSender.sendMessage(messageChannel, result);
            applicationLifecycleMarkers.setConnectedToVoice(false);
        }
        else if (content.equals("!connect_ticktok"))
        {
            if (applicationLifecycleMarkers.isConnectedToTickTok()) return;
            if (!applicationLifecycleMarkers.isConnectedToVoice()) return;

            String result = tiktokConnector.connect();
            messagesSender.sendMessage(messageChannel, result);
        }
        else if (content.equals("!disconnect_ticktok"))
        {
            if (!applicationLifecycleMarkers.isConnectedToTickTok()) return;
            if (!applicationLifecycleMarkers.isConnectedToVoice()) return;

            String result = tiktokConnector.disconnect();
            messagesSender.sendMessage(messageChannel, result);
        }
    }
}
