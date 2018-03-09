import React from 'react'
import PropTypes from 'prop-types'
import { Label } from 'semantic-ui-react'

export default class ExchangeRate extends React.Component {
        
    constructor(props) {
        super(props);
        this.state = {
            value : 0
        };
    }
    
    componentDidMount() {
        this.tick();
        this.startTimer();
    }

    componentWillUpdate() {
        this.stopTimer();
        this.startTimer();
    }

    componentWillUnmount() {
        this.stopTimer();
    }

    render() {
        console.log("BTC Exchange rate: " + this.state.value)
        return (
                <Label className="red">
                    $ {this.state.value}
                </Label>
                );
    }

    tick() {
        fetch(this.props.rateServiceUrl)
                .then(result => result.json())
                .then(value => this.setState({"value": value}));
    }

    startTimer() {
        this.timerID = setInterval(
            () => this.tick(),
            this.props.updateIntervalInMillis
        );

    }
    
    stopTimer() {
        clearInterval(this.timerID);
    }
    
}

ExchangeRate.propTypes = {
    rateServiceUrl: PropTypes.string.isRequired,
    updateIntervalInMillis: PropTypes.number
};

ExchangeRate.defaultProps = {
    updateIntervalInMillis: 10000
}