import Axios from 'axios';
import React from 'react';
import Container from 'react-bootstrap/Container';

import {AddNote} from '../AddNote';
import {Header} from '../Header';
import {NotesList} from '../NotesList';

export class NotesPage extends React.Component {

    state = {
        notes: []
    };

    doAddNote = async note => {
        try {
            const response = await Axios.post('/api/notes', note);
            this.setState(prevState => ({notes: prevState.notes.concat(response.data)}));
        } catch (error) {
            console.log(error);
        }
    };

    doDeleteNote = async note => {
        try {
            await Axios.delete(`/api/notes/${note.id}`);
            this.setState(prevState => ({notes: prevState.notes.filter(n => n !== note)}));
        } catch (error) {
            console.log(error);
        }
    };

    fetchNotes = async () => {
        try {
            const response = await Axios.get('/api/notes');
            return response.data;
        } catch (error) {
            console.log(error);
        }
    };

    componentDidMount() {
        this.fetchNotes().then(notes => this.setState(() => ({notes})));
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        if (this.state.notes.length !== prevState.notes.length) {
            localStorage.setItem('notes', JSON.stringify(this.state.notes));
        }
    }

    render() {
        return (
            <>
                <Header/>
                <Container>
                    <NotesList notes={this.state.notes} doDeleteNote={this.doDeleteNote}/>
                    <AddNote doAddNote={this.doAddNote}/>
                </Container>
            </>
        );
    }
}
