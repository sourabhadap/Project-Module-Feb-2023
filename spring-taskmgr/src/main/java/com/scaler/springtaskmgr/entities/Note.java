package com.scaler.springtaskmgr.entities;

import lombok.Getter;
import lombok.Setter;

import javax.sql.rowset.serial.SerialStruct;
import java.util.HashMap;
import java.util.List;

@Getter
@Setter
public class Note{

    private Integer noteId;
    private String noteBody;

    public Note(Integer noteId, String noteBody) {
//        super(id,title, description, dueDate);
        this.noteId = noteId;
        this.noteBody = noteBody;
    }

    public String getNoteBody() {
        return noteBody;
    }

    public void setNoteBody(String noteBody) {
        this.noteBody = noteBody;
    }

    public Integer getNoteId() {
        return noteId;
    }

    public void setNoteId(Integer noteId) {
        this.noteId = noteId;
    }
}
