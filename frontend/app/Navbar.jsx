import React, {Component} from 'react';

export default class Navbar extends Component {
	constructor() {
		super();
		this.state = {
			policy: ""
		};
	}

	componentWillMount() {
		fetch("http://localhost:8080/policyserver/webapi/policy/get")
		.then((response) => {
			return response.text();
		}).then(policy => {
			this.setState({
				policy: policy
			});
		});
	}

	changePolicy(e) {

		let policy = e.currentTarget.value
		this.setState({
			policy: e.currentTarget.value
		});

		fetch("http://localhost:8080/policyserver/webapi/policy/set", {
			method: "PUT",
			body: policy,
			headers: {
				"Content-Type": "text/plain"
			}
		}).then(response => {
			return response.text();
		}).then(response => {
			this.setState({
				policy
			});
			this.props.commonApi.displayNotification("Policy changed Successfully");
		});
	}

	render() {
		return (
			<nav className="navbar navbar-inverse">
				<div className="container">
					<div className="container-fluid">
						<div className="navbar-header">
							<a className="navbar-brand">Happy Patients Hospital</a>
						</div>
						<ul className="nav navbar-nav">
							<li className="active"><a data-toggle="tab" href="#create">Home</a></li>
							<li>
								{(<select className="form-control" onChange={this.changePolicy.bind(this)} value={this.state.policy}>
									<option value="ONGOING"> Ongoing </option>
									<option value="ICU"> ICU </option>
									<option valur="URGENTCARE"> Urgent Care </option>
								</select>)}
							</li>
						</ul>
					</div>
				</div>
			</nav>
		)
	}
}