import React from 'react';
import ReactDOM from 'react-dom';
import { Header, Container, Icon, Label } from 'semantic-ui-react'
import 'semantic-ui-css/semantic.min.css';
import 'main.css';

ReactDOM.render(
        <Container>
            <Header as="h1">
                Bitcoin exchange rate
            </Header>
            <Label>
                BTC / USD
            </Label>
            <Label className="red">
                $ 0.0
            </Label>
        </Container>
        ,
        document.getElementById('root')
        );

