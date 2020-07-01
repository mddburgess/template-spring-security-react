import React from 'react';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';

export class AddNote extends React.Component {

    doAddNote = event => {
        event.preventDefault();
        this.props.doAddNote({text: event.target.elements.text.value.trim()});
        event.target.elements.text.value = '';
    };

    render() {
        return (
            <Form inline onSubmit={this.doAddNote}>
                <Form.Control name="text"/>
                <Button type="submit">Add</Button>
            </Form>
        );
    }
}
