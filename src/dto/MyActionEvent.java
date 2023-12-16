package dto;

import lombok.Getter;

import java.awt.event.ActionEvent;

@Getter
public class MyActionEvent extends ActionEvent {
    Message message;
    public MyActionEvent(Object source, int id, String command, Message message) {
        super(source, id, command);
        this.message = message;
    }
}
