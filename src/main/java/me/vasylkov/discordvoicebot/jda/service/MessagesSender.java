package me.vasylkov.discordvoicebot.jda.service;


import lombok.Getter;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import org.springframework.stereotype.Service;

@Service
@Getter
public class MessagesSender
{
    private MessageChannel lastChannel;

    public void sendMessage(MessageChannel channel, String message)
    {
        channel.sendMessage(message).queue();
        if (lastChannel == null || !lastChannel.equals(channel))
            lastChannel = channel;
    }

    public void sendMessageToLastChannel(String message)
    {
        if (lastChannel == null)
        {
            System.out.println("Could not to find channel bcs lastchannel is null");
        }
        else
        {
            lastChannel.sendMessage(message).queue();
        }
    }
}
