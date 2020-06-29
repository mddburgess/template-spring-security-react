package com.example.demo.notes.rest;

import java.util.List;

import com.example.demo.notes.entity.Note;
import com.example.demo.notes.repository.NoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class NoteResourceTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private NoteRepository repository;

    private final Note note = new Note("text");

    @BeforeEach
    void beforeEach() {
        reset(repository);
    }

    @Nested
    @WithAnonymousUser
    class Unauthenticated {
        @Test
        void create() throws Exception {
            mvc.perform(post("/api/notes"))
                    .andExpect(status().isUnauthorized())
                    .andExpect(header().string("WWW-Authenticate", "Bearer"));
            verifyNoInteractions(repository);
        }

        @Test
        void read() throws Exception {
            mvc.perform(get("/api/notes"))
                    .andExpect(status().isUnauthorized())
                    .andExpect(header().string("WWW-Authenticate", "Bearer"));
            verifyNoInteractions(repository);
        }

        @Test
        void update() throws Exception {
            mvc.perform(put("/api/notes/1"))
                    .andExpect(status().isUnauthorized())
                    .andExpect(header().string("WWW-Authenticate", "Bearer"));
            verifyNoInteractions(repository);
        }

        @Test
        void remove() throws Exception {
            mvc.perform(delete("/api/notes/1"))
                    .andExpect(status().isUnauthorized())
                    .andExpect(header().string("WWW-Authenticate", "Bearer"));
            verifyNoInteractions(repository);
        }
    }

    @Nested
    @WithMockUser(authorities = "notes:read")
    class AuthenticatedReadOnly {

        @Test
        void create() throws Exception {
            mvc.perform(post("/api/notes")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{}"))
                    .andExpect(status().isForbidden());
            verifyNoInteractions(repository);
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
            mvc.perform(put("/api/notes/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{}"))
                    .andExpect(status().isForbidden());
            verifyNoInteractions(repository);
        }

        @Test
        void remove() throws Exception {
            mvc.perform(delete("/api/notes/1"))
                    .andExpect(status().isForbidden());
            verifyNoInteractions(repository);
        }
    }

    @Nested
    @WithMockUser(authorities = {"notes:read", "notes:write"})
    class AuthenticatedReadWrite {

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
}
