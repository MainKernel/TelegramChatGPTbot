package chatgptbot.model.chatgpt;

import chatgptbot.model.PrefsModel;
import chatgptbot.model.chatgpt.chat.request.RequestModel;
import chatgptbot.model.chatgpt.chat.response.Choice;
import chatgptbot.model.chatgpt.chat.response.ResponseModel;
import com.google.gson.Gson;
import okhttp3.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class ChatGptModel {
    private final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(240, TimeUnit.SECONDS) // Example timeout value (adjust as needed)
            .readTimeout(240, TimeUnit.SECONDS)
            .writeTimeout(240, TimeUnit.SECONDS)
            .build();
    private final Gson gson = new Gson();

    public String sendRequest(RequestModel request) {

        String req = gson.toJson(request);
        ResponseModel responseModel;

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        String apiUrl = "https://api.openai.com/v1/chat/completions";

        RequestBody requestBody = RequestBody.create(JSON, req);
        Request httprequest = new Request.Builder().addHeader("Authorization", "Bearer "
                + PrefsModel.getINSTANCE().getPref(PrefsModel.GPT_KEY))
                .url(apiUrl).post(requestBody).build();

        try (Response response = client
                .newCall(httprequest).execute()) {

            ResponseBody responseBody = response.body();
            String responseBodyString = responseBody.string();
            responseModel = gson.fromJson(responseBodyString, ResponseModel.class);
            client.dispatcher().executorService().shutdown();
            client.connectionPool().evictAll();

            if (responseModel != null && responseModel.getChoices() != null && !responseModel.getChoices().isEmpty()) {
                Choice choice = responseModel.getChoices().get(0);
                if (choice != null && choice.getMessage() != null) {
                    return choice.getMessage().getContent();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        client.dispatcher().executorService().shutdown();
        client.connectionPool().evictAll();
        return null;
    }
}
