import React from 'react'
        import PropTypes from 'prop-types'

export default class Transactions extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            transactions: [0]
        };
    }

    componentDidMount() {
        var source = new EventSource(this.props.btctxServiceUrl);
        source.onmessage = function (event) {
            console.log("Received: " + event.data);
        };
    }

   render() {
        return (
            <span></span>
        );
    }
}

Transactions.propTypes = {
    btctxServiceUrl: PropTypes.string.isRequired
};