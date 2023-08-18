package chatgptbot.model.telegrambot;

import chatgptbot.model.PrefsModel;
import chatgptbot.model.chatgpt.ChatGptModel;
import chatgptbot.model.chatgpt.chat.CreateRequest;
import chatgptbot.model.chatgpt.chat.request.Message;
import chatgptbot.model.chatgpt.chat.request.RequestModel;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import kotlin.time.MeasureTimeKt;


import java.util.List;

public class TelegramBotModel {
    private static final TelegramBot bot = new TelegramBot(PrefsModel.getINSTANCE().getPref(PrefsModel.BOT_KEY));
    private static final ChatGptModel model = new ChatGptModel();


    public void setListener() {

        bot.setUpdatesListener(TelegramBotModel::answerToUser, e -> {

            if (e.response() != null) {
                // got bad response from telegram
                e.response().errorCode();
                e.response().description();
            } else {
                // probably network error
                e.printStackTrace();
            }

        });

    }

    public static int answerToUser(List<Update> updates) {

        try {
            if(updates.get(0).message().chat().id() != null){
                String userInput = updates.get(0).message().text();
                Long id = updates.get(0).message().chat().id();
                createMessage(userInput, id);
            }

        } catch (NullPointerException ex){
            ex.printStackTrace();
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    public static void createMessage(String text, long id){
        RequestModel request = new CreateRequest(text).getModel();
        String s = model.sendRequest(request);
        bot.execute(new SendMessage(id,s));

    }

}