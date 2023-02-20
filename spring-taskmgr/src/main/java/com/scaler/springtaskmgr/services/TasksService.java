package com.scaler.springtaskmgr.services;

import com.scaler.springtaskmgr.entities.Note;
import com.scaler.springtaskmgr.entities.Task;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class TasksService {
    private final List<Task> taskList;

    private final HashMap<Integer, List<Note>> noteTaskMap;
    private final AtomicInteger taskId = new AtomicInteger(0);

    private final AtomicInteger noteId = new AtomicInteger(0);

    public static class TaskNotFoundException extends IllegalArgumentException {
        public TaskNotFoundException(Integer id) {
            super("Task with id " + id + " not found");
        }
    }

    public TasksService() {
        taskList = new ArrayList<>();
        taskList.add(new Task(taskId.incrementAndGet(), "Task 1", "Description 1", "2021-01-01"));
        taskList.add(new Task(taskId.incrementAndGet(), "Task 2", "Description 2", "2021-01-01"));
        taskList.add(new Task(taskId.incrementAndGet(), "Task 3", "Description 3", "2021-01-01"));

        noteTaskMap = new HashMap<>();
        noteTaskMap.put(1, new ArrayList<>());
        for (int i=0;i<3; i++) {
            noteTaskMap.get(1).add(new Note(noteId.incrementAndGet(), "Note - "+i));
        }
        noteTaskMap.put(2, new ArrayList<>());
        for (int i=0;i<3; i++) {
            noteTaskMap.get(2).add(new Note(noteId.incrementAndGet(), "Note - "+i));
        }
    }

    public List<Task> getTasks() {
        return taskList;
    }

    public Task createTask(
            String title,
            String description,
            String dueDate
    ) {
        // TODO: ensure date is not before today
        var newTask = new Task(taskId.incrementAndGet(), title, description, dueDate);
        taskList.add(newTask);
        return newTask;
    }

    public Task getTaskById(Integer id) {
        return taskList.stream()
                .filter(task -> task.getId().equals(id)).findFirst()
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    public Task updateTask(
            Integer id,
            String title,
            String description,
            String dueDate
    ) {
        var task = getTaskById(id);
        if (title != null) task.setTitle(title);
        if (description != null) task.setDescription(description);
        if (dueDate != null) task.setDueDate(dueDate);
        return task;
    }

    public Task deleteTask(Integer id) {
        var task = getTaskById(id);
        taskList.remove(task);
        return task;
    }


    // Notes related methods

    public static class NoteNotFoundException extends IllegalArgumentException {
        public NoteNotFoundException(Integer id) {
            super("Note with id " + id + " not found");
        }
    }

    public Note addNoteToTask(Integer id, String note) {
        Note newNote = new Note(noteId.incrementAndGet(),note);
        if (noteTaskMap.containsKey(id)) {
            noteTaskMap.get(id).add(newNote);
        } else {
            noteTaskMap.put(id, new ArrayList<>());
            noteTaskMap.get(id).add(newNote);
        }
        return newNote;
    }

    public List<Note> getNotesByTaskId(Integer id) {
        return noteTaskMap.get(id);
    }

    public Note deleteNoteFromTask(Integer taskId, Integer noteId) {
        var note = noteTaskMap.get(taskId).stream()
                .filter(n -> n.getNoteId().equals(noteId)).findFirst()
                .orElseThrow(() -> new NoteNotFoundException(noteId));
        noteTaskMap.get(taskId).remove(note);
        return note;
    }
}
