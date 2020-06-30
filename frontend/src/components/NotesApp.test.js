import React from 'react';
import {render} from '@testing-library/react';
import {NotesApp} from './NotesApp';

test('renders NotesApp', () => {
    const {getByText} = render(<NotesApp/>);
    const header = getByText('Notes');
    expect(header).toBeInTheDocument();
});
