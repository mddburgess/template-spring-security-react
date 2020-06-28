package com.example.demo.notes.repository;

import com.example.demo.notes.entity.Note;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class NoteRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private NoteRepository repository;

    private Note note;

    @BeforeEach
    void beforeEach() {
        note = entityManager.persist(new Note("existing note"));
    }

    @Test
    void create() {
        var createdNote = repository.save(new Note("new note"));
        assertThat(entityManager.find(Note.class, createdNote.getId())).isEqualTo(createdNote);
    }

    @Test
    void read() {
        assertThat(repository.findAll()).containsExactly(note);
    }

    @Test
    void update() {
        note.setText("updated note");
        repository.saveAndFlush(note);
        assertThat(entityManager.find(Note.class, note.getId())).isEqualTo(note);
    }

    @Test
    void delete() {
        repository.delete(note);
        repository.flush();
        assertThat(entityManager.find(Note.class, note.getId())).isNull();
    }
}
