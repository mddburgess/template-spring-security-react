import React from 'react';
import ReactDOM from 'react-dom';
import {NotesApp} from './components/NotesApp';

import 'bootstrap/dist/css/bootstrap.min.css';
import './index.css';

ReactDOM.render(
    <React.StrictMode>
        <NotesApp/>
    </React.StrictMode>,
    document.getElementById('root')
);
