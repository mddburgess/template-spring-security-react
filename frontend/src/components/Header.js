import React from 'react';
import Navbar from 'react-bootstrap/Navbar';

export const Header = ({title}) => (
    <Navbar bg="dark" variant="dark">
        <Navbar.Brand>{title}</Navbar.Brand>
    </Navbar>
);

Header.defaultProps = {
    title: 'Notes'
};
