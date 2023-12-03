package dto;

import lombok.Getter;

import java.awt.event.ActionEvent;

@Getter
public class MyActionEvent extends ActionEvent {
    Object data;
    public MyActionEvent(Object source, int id, String command, Object data) {
        super(source, id, command);
        this.data = data;
    }
}
