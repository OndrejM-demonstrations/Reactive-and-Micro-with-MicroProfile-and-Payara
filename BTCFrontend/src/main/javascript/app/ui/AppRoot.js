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
            
    componentDidMount() {
        this.updateConfig();
    }
            
    updateConfig() {
        var url = this.props.rootUrl + "config";
        fetch(url)
            .then(result => result.json())
            .then(config => this.setState({config: config}));
    }

    render() {
        var rateSvcUrl = this.props.rootUrl + "rate";
        var btctxSvcUrl = this.props.rootUrl + "btctx";
        return (
                <Container>
                    <Label className="top-header">
                        <Header as="h1">
                            Bitcoin exchange rate
                        </Header>
                    </Label>
                    <div>
                        <Label>
                            BTC / USD
                        </Label>
                        <ExchangeRate rateServiceUrl={rateSvcUrl} 
                          updateIntervalInMillis={this.state.config.rateUpdateIntervalInMillis}/>
                    </div>
                    <div>
                        <Transactions btctxServiceUrl={btctxSvcUrl}/>
                    </div>
                </Container>
                );
    }
    
}

AppRoot.propTypes = {
    rootUrl: PropTypes.string // root url for subsequent requests to the server
};

AppRoot.defaultProps = {
    rootUrl: "rest/" // root url for subsequent requests to the server
};