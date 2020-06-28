package com.example.demo.notes.rest;

import java.util.List;

import com.example.demo.notes.entity.Note;
import com.example.demo.notes.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notes")
public class NoteResource {

    @Autowired
    private NoteRepository repository;

    @PostMapping
    public Note create(@RequestBody Note note) {
        return repository.save(note);
    }

    @GetMapping
    public List<Note> read() {
        return repository.findAll();
    }

    @PutMapping("{id}")
    public Note update(@PathVariable Long id, @RequestBody Note note) {
        note.setId(id);
        return repository.save(note);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
