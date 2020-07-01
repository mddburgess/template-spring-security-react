import React from 'react';
import ListGroup from 'react-bootstrap/ListGroup';
import {Note} from './Note';

export const NotesList = ({notes, doDeleteNote}) => (
    <ListGroup>
        {notes.map((note, idx) => <Note key={idx} note={note} doDeleteNote={doDeleteNote}/>)}
    </ListGroup>
);
