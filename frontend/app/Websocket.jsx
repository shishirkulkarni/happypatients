import React, {Component} from 'react';
import "../styles/Websocket.css";

export default class Websocket extends Component {
	constructor() {
		super();
		this.state = {
			message: ""
		}
	}

	onMessage(event) {
		this.setState({
			message: event.data
		});

		setTimeout(() => {
			this.setState({
				message: ""
			});
		}, 3000);
	}

	componentWillMount() {
		var websocket = new WebSocket("ws://localhost:8080/happypatients/messages");
		websocket.onmessage = this.onMessage.bind(this);
	}

	render() {
		if(!this.state.message)
			return null;

		return (
			<div className="socket-message">
				<div className="alert alert-info">
					<pre className="message-body">{this.state.message}</pre>
				</div>
			</div>
		)
	}

}