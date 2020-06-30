import React from "react";

export const Header = ({title}) => (
    <div>
        <h1>{title}</h1>
    </div>
);

Header.defaultProps = {
    title: 'Notes'
}
