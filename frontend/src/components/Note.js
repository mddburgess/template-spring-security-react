import React from 'react';
import Button from 'react-bootstrap/Button';
import ListGroupItem from 'react-bootstrap/ListGroupItem';

export const Note = ({text, doDeleteNote}) => (
    <ListGroupItem>
        {text}
        <Button size="sm" onClick={() => doDeleteNote(text)}>&times;</Button>
    </ListGroupItem>
);
