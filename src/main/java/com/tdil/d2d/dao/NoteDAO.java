package com.tdil.d2d.dao;

import com.tdil.d2d.persistence.Note;

import java.util.List;
import java.util.Map;

public interface NoteDAO {

	Note save(Note note);

	List<Note> getNotes(Map<String, Object> params);

	Note getNoteById(Long id);
}
