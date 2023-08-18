import chatgptbot.model.PrefsModel;
import chatgptbot.model.telegrambot.TelegramBotModel;
import okhttp3.*;

import java.io.IOException;

public class LaunchApp {
    public static void main(String[] args) {

        TelegramBotModel botModel = new TelegramBotModel();
        botModel.setListener();
    }
}
