package com.ihtasham.Listeners;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ihtasham.Client.ApiClient;
import com.ihtasham.Client.DatHostApiClient;
import com.ihtasham.Utils.MessageUtils;
import com.ihtasham.model.ApiResponse;
import com.ihtasham.model.Constants;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

@Slf4j
public class MessageListener extends ListenerAdapter {

  private final String serverId = System.getenv(Constants.SERVER_ID);
  final ObjectMapper objectMapper = new ObjectMapper();
  final ApiClient apiClient = new DatHostApiClient();

  @Override
  public void onMessageReceived(@NotNull MessageReceivedEvent event) {

    if (serverId == null || serverId.isEmpty()) {
      log.error("Server ID is not defined, cannot continue.");
      return;
    }

    if (event.getAuthor().isBot()) {
      log.debug("Bot messaged, ignoring event");
      return;
    }

    final String message = event.getMessage().getContentRaw();

    if (message.charAt(0) != Constants.COMMAND_PREFIX) {
      log.debug("Message received was not a command, ignoring event");
      return;
    }

    final String[] receivedMessage = message.substring(1).split(" ");

    try {
      if (Constants.ACTION_COMMAND.equals(receivedMessage[0])) {

        if (receivedMessage.length > 1) {
          if (receivedMessage[1].equalsIgnoreCase("start")) {
            ApiResponse response = apiClient.start(serverId);
            MessageUtils.sendMessage(
                event.getChannel(),
                String.format(
                    "Server successfully started, response: ```%s```",
                    response.getResponseSummary()));
          } else if (receivedMessage[1].equalsIgnoreCase("stop")) {
            ApiResponse response = apiClient.stop(serverId);
            MessageUtils.sendMessage(
                event.getChannel(),
                String.format(
                    "Server successfully stopped, response: ```%s```",
                    response.getResponseSummary()));
          } else if (receivedMessage[1].equalsIgnoreCase("status")) {
            ApiResponse response = apiClient.status(serverId);

            final Map<String, Object> responseMap =
                objectMapper.readValue(response.getResponseRaw(), Map.class);

            final String returnString =
                String.format(
                    "Name: %s\nGame: %s\nBooting: %s\nOnline: %s\nPlayers Online: %s\nMatch Id: %s",
                    responseMap.get("name"),
                    responseMap.get("game"),
                    responseMap.get("booting"),
                    responseMap.get("on"),
                    responseMap.get("players_online"),
                    responseMap.get("match_id"));

            MessageUtils.sendMessage(
                event.getChannel(),
                String.format(
                    "Server status queried successfully, response: ```%s```", returnString));
          } else {
            MessageUtils.sendMessage(
                event.getChannel(), "Option not supported, see: " + Constants.HELP_COMMAND);
          }
        } else {
          MessageUtils.sendMessage(
              event.getChannel(),
              "An option must be passed to this command, see: " + Constants.HELP_COMMAND);
        }
      }

      if (Constants.HELP_COMMAND.equals(receivedMessage[0])) {
        getHelpMessage(event);
      }

    } catch (Exception e) {
      log.error(e.getMessage());
    }
  }

  private void getHelpMessage(MessageReceivedEvent event) {
    log.warn("Generating help message");
    final String message =
        String.format(
            "Usage: `%s%s start|stop|status` to action the server!",
            Constants.COMMAND_PREFIX, Constants.ACTION_COMMAND);
    MessageUtils.sendMessage(event.getChannel(), message);
  }
}
