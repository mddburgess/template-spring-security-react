import React from 'react';
import {render} from '@testing-library/react';

import {NotesPage} from './NotesPage';

it('renders NotesPage', () => {
    const {getByText} = render(<NotesPage/>);
    const header = getByText('Notes');
    expect(header).toBeInTheDocument();
});
