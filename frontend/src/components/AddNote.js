import React from "react";

export class AddNote extends React.Component {

    doAddNote = event => {
        event.preventDefault();
        this.props.doAddNote(event.target.elements.text.value.trim());
        event.target.elements.text.value = '';
    }

    render() {
        return (
            <div>
                <form onSubmit={this.doAddNote}>
                    <input type="text" name="text"/>
                    <button>Add</button>
                </form>
            </div>
        );
    }
}
