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
        fetch(this.props.rateServiceUrl) 
            .then(result=>result.json())
            .then(value=>this.setState({"value": value}));
    }

    render() {
        console.log(this.state.value)
        return (
                <Label className="red">
                    $ {this.state.value}
                </Label>
                );
    }
}

ExchangeRate.propTypes = {
    rateServiceUrl: PropTypes.string.isRequired
};