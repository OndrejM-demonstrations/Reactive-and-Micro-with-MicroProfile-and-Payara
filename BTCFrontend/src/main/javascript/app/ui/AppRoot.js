import React from 'react';
import PropTypes from 'prop-types'
import { Header, Container, Icon, Label } from 'semantic-ui-react'
import ExchangeRate from 'ui/ExchangeRate'
import Transactions from 'ui/Transactions'

export default class AppRoot extends React.Component {
        
    constructor(props) {
        super(props);
        this.state = {
            config : {
                rateUpdateIntervalInMillis: 10000
            }
        };
    }
            
    updateConfig() {
        const url = this.props.rootUrl + "config";
        fetch(url)
            .then(result => result.json())
            .then(config => this.setState({config: config}));
    }

    render() {
        const rateSvcUrl = this.props.rootUrl + "rate";
        const btctxSvcUrl = this.props.rootUrl + "btctx";
        return (
            <Container>
                <div>
                    <Label className="top-header">
                        <Header as="h1">
                            Bitcoin exchange rate
                        </Header>
                    </Label>
                    <ExchangeRate rateServiceUrl={rateSvcUrl} 
                      updateIntervalInMillis={this.state.config.rateUpdateIntervalInMillis}/>
                    <Label>
                        BTC / USD
                    </Label>
                </div>


            </Container>
        );
    }
 
    componentDidMount() {
        this.updateConfig();
        this.startTimer();
    }
            
    componentWillUpdate() {
        this.stopTimer();
        this.startTimer();
    }

    componentWillUnmount() {
        this.stopTimer();
    }

    startTimer() {
        this.timerID = setInterval(
            () => this.updateConfig(),
            1000
        );

    }

    stopTimer() {
        clearInterval(this.timerID);
    }
    
}

AppRoot.propTypes = {
    rootUrl: PropTypes.string // root url for subsequent requests to the server
};

AppRoot.defaultProps = {
    rootUrl: "rest/" // root url for subsequent requests to the server
};

/*
                <div className="panel">
                  <Transactions btctxServiceUrl={btctxSvcUrl}/>
                </div>
 */
