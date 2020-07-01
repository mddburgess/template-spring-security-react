import React from 'react';
import Button from 'react-bootstrap/Button';
import ListGroupItem from 'react-bootstrap/ListGroupItem';

export const Note = ({note, doDeleteNote}) => (
    <ListGroupItem>
        {note.text}
        <Button size="sm" onClick={() => doDeleteNote(note)}>&times;</Button>
    </ListGroupItem>
);
