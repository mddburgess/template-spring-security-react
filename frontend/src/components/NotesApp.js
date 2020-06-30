import React from 'react';
import Container from 'react-bootstrap/Container';
import {AddNote} from './AddNote';
import {Header} from './Header';
import {NotesList} from './NotesList';

export class NotesApp extends React.Component {

    state = {
        notes: []
    };

    doAddNote = text => {
        this.setState(prevState => ({notes: prevState.notes.concat(text)}));
    };

    doDeleteNote = text => {
        this.setState(prevState => ({notes: prevState.notes.filter(t => t !== text)}));
    };

    componentDidMount() {
        try {
            const notes = JSON.parse(localStorage.getItem('notes'));
            if (notes) {
                this.setState(() => ({notes}));
            }
        } catch (error) {
        }
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
