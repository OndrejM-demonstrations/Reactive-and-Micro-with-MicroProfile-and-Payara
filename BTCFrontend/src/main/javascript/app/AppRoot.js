import React from 'react';
import { Header, Container, Icon, Label } from 'semantic-ui-react'

export default class AppRoot extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
        };
    }

    render() {
        return (
        <Container>
            <Label className="top-header">
                <Header as="h1">
                    Bitcoin exchange rate
                </Header>
            </Label>
            <p>
                <Label>
                    BTC / USD
                </Label>
                <Label className="red">
                    $ 0.0
                </Label>
            </p>
        </Container>
        );
    }
}

