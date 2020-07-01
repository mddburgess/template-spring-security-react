import React from 'react';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Modal from 'react-bootstrap/Modal';

export const LoginPage = () => (
    <Modal show centered animation={false} size="sm">
        <Modal.Header>
            <Modal.Title>Log in to Notes</Modal.Title>
        </Modal.Header>
        <Modal.Body>
            <Form>
                <Form.Group>
                    <Form.Control type="text" placeholder="username"/>
                </Form.Group>
                <Form.Group>
                    <Form.Control type="password" placeholder="password"/>
                </Form.Group>
                <Button block type="submit">Log in</Button>
            </Form>
        </Modal.Body>
    </Modal>
);
