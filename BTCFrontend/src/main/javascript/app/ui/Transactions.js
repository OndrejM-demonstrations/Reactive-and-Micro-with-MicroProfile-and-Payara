import React from 'react'
import PropTypes from 'prop-types'
import { Table } from 'semantic-ui-react'

export default class Transactions extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            transactions: []
        };
    }

    componentDidMount() {
        const source = new EventSource(this.props.btctxServiceUrl);
        source.onmessage = (event) => {
            console.log("Received: " + event.data);
            const transaction = JSON.parse(event.data);
            this.setState((prevState, props) => ({
                transactions:[transaction].concat(prevState.transactions).slice(0,100)
            }));
        };
    }

   render() {
        const rows = this.state.transactions.map((tx) => {
            console.log(tx);
            const date = new Date(tx.timestamp * 1000).toLocaleDateString('de-DE', { year: 'numeric', month: 'numeric', day: 'numeric' })
            return (
                <Table.Row key={tx.timestamp + tx.price + tx.amount}>
                  <Table.Cell>{date}</Table.Cell>
                  <Table.Cell>{tx.price}</Table.Cell>
                  <Table.Cell>{tx.amount}</Table.Cell>
                </Table.Row>
                );
        });
        return (
            <Table celled>
                <Table.Header>
                  <Table.Row>
                    <Table.HeaderCell>Timestamp</Table.HeaderCell>
                    <Table.HeaderCell>Price</Table.HeaderCell>
                    <Table.HeaderCell>Amount</Table.HeaderCell>
                  </Table.Row>
                </Table.Header>

                <Table.Body>
                     {rows}
                </Table.Body>
            </Table>
        );
    }
}

Transactions.propTypes = {
    btctxServiceUrl: PropTypes.string.isRequired
};