package com.example.demo.notes.rest;

import java.util.List;

import com.example.demo.notes.entity.Note;
import com.example.demo.notes.repository.NoteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class NoteResourceTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private NoteRepository repository;

    private Note note = new Note("text");

    @Test
    void create() throws Exception {
        given(repository.save(any())).willReturn(note);

        mvc.perform(post("/api/notes")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text", is("text")));
    }

    @Test
    void read() throws Exception {
        given(repository.findAll()).willReturn(List.of(note));

        mvc.perform(get("/api/notes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].text", is("text")));
    }

    @Test
    void update() throws Exception {
        given(repository.save(any())).willReturn(note);

        mvc.perform(put("/api/notes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text", is("text")));
    }

    @Test
    void remove() throws Exception {
        mvc.perform(delete("/api/notes/1"))
                .andExpect(status().isOk());
        verify(repository).deleteById(1L);
    }
}
