import React from 'react'
import PropTypes from 'prop-types'
import { Label } from 'semantic-ui-react'

export default class ExchangeRate extends React.Component {
        
    constructor(props) {
        super(props);
        this.state = {
            value: 0,
            updateIntervalInMillis: this.props.updateIntervalInMillis
        };
    }
    
    componentDidMount() {
        this.fetchRate();
        this.startTimer();
    }

    componentWillReceiveProps(newProps) {
        if (this.state.updateIntervalInMillis != newProps.updateIntervalInMillis) {
            this.setState({updateIntervalInMillis : newProps.updateIntervalInMillis}, 
                () => {
            this.stopTimer();
            this.startTimer();
            });
        }
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
        console.log("new timer for interval");
        console.log(this.state);
        this.timerID = setInterval(
            () => this.fetchRate(),
            this.state.updateIntervalInMillis
        );

    }
    
    stopTimer() {
        console.log("clear timer");
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