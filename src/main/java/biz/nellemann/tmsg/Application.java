/*
   Copyright 2024 mark.nellemann@gmail.com

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */

package biz.nellemann.tmsg;

import java.util.Scanner;
import java.util.concurrent.Callable;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;
import picocli.CommandLine.Command;


@Command(name = "tmsg",
        mixinStandardHelpOptions = true,
        versionProvider = biz.nellemann.tmsg.VersionProvider.class)
public class Application implements Callable<Integer> {

    private final static Logger log = LoggerFactory.getLogger(Application.class);

    private TelegramBot bot;


    @CommandLine.Option(names = { "-t", "--token"}, description = "Telegram Token.", required = true)
    private String token;

    @CommandLine.Option(names = { "-i", "--chat-id"}, description = "Telegram Chat ID.", required = true)
    private String chatId;

    @CommandLine.Option(names = { "-m", "--markdown"}, description = "Enable markdown parser.", required = false)
    private boolean optMarkdown;




    @Override
    public Integer call() {

        StringBuilder stringBuilder = new StringBuilder();
        Scanner input = new Scanner(System.in);
        while(input.hasNextLine()) {
            stringBuilder.append(input.nextLine());
            stringBuilder.append(System.lineSeparator());
        }
        input.close();

        bot = new TelegramBot(token);
        sendText(chatId, stringBuilder.toString());
        bot.shutdown();

        return 0;
    }


    public static void main(String... args) {
        int exitCode = new CommandLine(new Application()).execute(args);
        System.exit(exitCode);
    }


    private void sendText(String chatId, String text) {

        if(text.length() >= 9500) {
            log.error("sendText() - message exceeds maximum length of 9500");
            return;
        }

        log.debug("sendText() - chatId: {}, text: {}", chatId, text);
        SendMessage request = new SendMessage(chatId, text)
            .disableWebPagePreview(true)
            .disableNotification(true);

        if(optMarkdown) {
            request.parseMode(ParseMode.Markdown);
        }

        SendResponse sendResponse = bot.execute(request);
        boolean ok = sendResponse.isOk();
        if(!ok) {
            log.warn("sendText() - text was not sent, error: {} - {}", sendResponse.errorCode(), sendResponse.description());
        }

    }


}
