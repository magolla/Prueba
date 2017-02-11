package com.tdil.d2d.dao;

import java.util.List;
import java.util.Map;

import com.tdil.d2d.persistence.Note;

public interface NoteDAO {

	Note save(Note note);

	List<Note> getNotes(int page, int size, Map<String, Object> params);

	Note getNoteById(Long id);

	List<Note> getNotesForUser(List<Long> ocuppations, List<Long> specialities);
}
