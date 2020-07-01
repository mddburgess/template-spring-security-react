import React from 'react';
import {BrowserRouter, Route, Switch} from 'react-router-dom';

import {LoginPage} from './pages/LoginPage';
import {NotesPage} from './pages/NotesPage';

export const NotesApp = () => (
    <BrowserRouter>
        <Switch>
            <Route path="/login" component={LoginPage}/>
            <Route path="/notes" component={NotesPage}/>
        </Switch>
    </BrowserRouter>
);
