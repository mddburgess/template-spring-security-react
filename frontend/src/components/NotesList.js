import React from "react";
import {Note} from "./Note";

export const NotesList = ({notes, doDeleteNote}) => (
    <div>
        {notes.map((text, idx) => <Note key={idx} text={text} doDeleteNote={doDeleteNote}/>)}
    </div>
);
