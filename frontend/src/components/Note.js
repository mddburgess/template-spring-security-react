import React from 'react';

export const Note = ({text, doDeleteNote}) => (
    <div>
        <p>{text}</p>
        <button onClick={() => doDeleteNote(text)}>x</button>
    </div>
);
