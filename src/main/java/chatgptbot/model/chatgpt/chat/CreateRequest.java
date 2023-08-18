package chatgptbot.model.chatgpt.chat;

import chatgptbot.model.chatgpt.chat.request.Message;
import chatgptbot.model.chatgpt.chat.request.RequestModel;

import java.util.ArrayList;
import java.util.List;

public class CreateRequest {
    private final RequestModel model = new RequestModel();

    public CreateRequest(String userContent){
        Message message1 = new Message();
        message1.setRole("assistant");
        message1.setContent("You are a helpful assistant.");

        Message message2 = new Message();
        message2.setRole("user");
        message2.setContent(userContent);

        List<Message> messages = new ArrayList<>();
        messages.add(message1);
        messages.add(message2);

        model.setMessages(messages);
    }

    public RequestModel getModel(){
        return model;
    }

}
