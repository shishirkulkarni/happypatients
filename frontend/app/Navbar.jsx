import React, {Component} from 'react';

export default class Navbar extends Component {
	constructor() {
		super();
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
						</ul>
					</div>
				</div>
			</nav>
		)
	}
}