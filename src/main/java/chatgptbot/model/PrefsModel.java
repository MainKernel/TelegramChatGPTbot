package chatgptbot.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PrefsModel {

    // Make variables package-private;
    public static final String BOT_KEY = "botKey";
    public static final String GPT_KEY = "gptKey";
    public static final String DB_URL = "DB_URL";
    private static Map<String, Object> PREFS = new HashMap<>();
    private static final PrefsModel INSTANCE = new PrefsModel();

    static {
        PrefsModel.readPrefsFromFile();
    }

    public static PrefsModel getINSTANCE() {
        return INSTANCE;
    }

    public String getPref(String pref) throws NullPointerException {
        return PREFS.get(pref).toString();
    }

    private static void readPrefsFromFile() {
        Gson gson = new Gson();
        TypeToken<Map<String, Object>> mapTypeToken = new TypeToken<>() {
        };
        try (FileReader reader = new FileReader("./pref.json")) {
            PREFS = gson.fromJson(reader, mapTypeToken);
        } catch (IOException e) {
            System.out.println("File not found");
        }
    }
}
