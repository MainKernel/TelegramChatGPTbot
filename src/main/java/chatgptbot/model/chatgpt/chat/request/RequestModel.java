package chatgptbot.model.chatgpt.chat.request;

import java.util.List;

public class RequestModel {
    private String model = "gpt-3.5-turbo";
    private List<Message> messages;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
