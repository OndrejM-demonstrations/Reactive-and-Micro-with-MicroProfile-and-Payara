import React from 'react'
import PropTypes from 'prop-types'
import { Label } from 'semantic-ui-react'

export default class ExchangeRate extends React.Component {
        
    constructor(props) {
        super(props);
        this.state = {
            updateIntervalInMillis : props.updateIntervalInMillis
        };
    }
    
    componentDidMount() {
        this.fetchRate();
        this.startTimer();
    }

    componentWillReceiveProps(props) {
        this.setState({updateIntervalInMillis: props.updateIntervalInMillis});
    }
    
    componentWillUpdate(props) {
        this.stopTimer();
        this.startTimer();
    }

    componentWillUnmount() {
        this.stopTimer();
    }

    render() {
        return (
                <Label className="red">
                    $ {this.state.value}
                </Label>
                );
    }

    fetchRate() {
        fetch(this.props.rateServiceUrl)
                .then(result => result.json())
                .then(value => {
                    console.log("BTC Exchange rate: " + value)
                    this.setState({"value": value})
                });
    }

    startTimer() {
        this.timerID = setInterval(
            () => this.fetchRate(),
            this.state.updateIntervalInMillis
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