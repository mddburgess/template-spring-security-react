import React from 'react';
import ListGroup from 'react-bootstrap/ListGroup';
import {Note} from './Note';

export const NotesList = ({notes, doDeleteNote}) => (
    <ListGroup>
        {notes.map((text, idx) => <Note key={idx} text={text} doDeleteNote={doDeleteNote}/>)}
    </ListGroup>
);
