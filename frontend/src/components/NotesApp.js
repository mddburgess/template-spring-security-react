import React from 'react';
import {Header} from "./Header";
import {NotesList} from "./NotesList";
import {AddNote} from "./AddNote";

export class NotesApp extends React.Component {

    state = {
        notes: []
    }

    doAddNote = text => {
        this.setState(prevState => ({notes: prevState.notes.concat(text)}))
    }

    doDeleteNote = text => {
        this.setState(prevState => ({notes: prevState.notes.filter(t => t !== text)}))
    }

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
            <div>
                <Header/>
                <NotesList notes={this.state.notes} doDeleteNote={this.doDeleteNote}/>
                <AddNote doAddNote={this.doAddNote}/>
            </div>
        );
    }
}
