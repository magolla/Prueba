package com.tdil.d2d.dao;

import java.util.List;
import java.util.Map;

import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.persistence.Note;
import com.tdil.d2d.persistence.Subscription;
import com.tdil.d2d.persistence.User;

public interface NoteDAO {

	Note save(Note note);

	List<Note> getNotes(int page, int size, Map<String, Object> params);

	Note getNoteById(Long id);

	List<Note> getNotesForUser(int page, int size, List<Long> ocuppations, List<Long> specialities, User user, Subscription userSubscription);

	List<Note> getAll() throws DAOException;

	Note getLastNote(); 
}
