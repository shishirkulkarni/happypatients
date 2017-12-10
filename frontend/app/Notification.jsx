import React, {Component} from 'react';
import '../styles/Notification.css';

export default class Notification extends Component {
	constructor() {
		super();
		this.state = {
			currentStyle: {
				position: "fixed",
				top: "-100%",
				left: "50%",
				transform: "translateX(-50%)"	
			}
		}
	}

	componentWillReceiveProps() {
		let origStyle = {
			position: "fixed",
			top: "-100px",
			left: "50%",
			transform: "translateX(-50%)"	
		};
		
		let finalStyle = {
			position: "fixed",
			top: "50px",
			left: "50%",
			transform: "translateX(-50%)"	
		}
		
		setTimeout(() => {
			this.setState({
				currentStyle: finalStyle
			});
		}, 100);

		setTimeout(() => {
			this.setState({
				currentStyle: origStyle
			});
		}, 3000);
	}

	render() {
		if(!this.props.message) {
			return null;
		}

		return (
			<div className="notification alert" style={this.state.currentStyle}>
				<div className="alert alert-info">
  					{this.props.message}
				</div>
			</div>
		);
	}
}