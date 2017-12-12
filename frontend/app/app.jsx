import React, {Component} from 'react';
import ReactDOM from 'react-dom';
import {config} from './config.jsx';
import '../node_modules/bootstrap/dist/css/bootstrap.min.css';
import Patients from "./Patients.jsx";
import NewPatient from "./newPatient.jsx";
import EditPatient from "./EditPatient.jsx";
import Navbar from "./Navbar.jsx";
import Notification from "./Notification.jsx";
import '../styles/App.css';


export default class App extends Component {
	constructor() {
		super();
		this.state = {
			loading: true,
			patients: [],
			currentFragment: "home",
			currentPatient: {},
			notification: ""
		};
	}

	fetchAllUsers() {
		return fetch("http://localhost:8080/happypatients/webapi/patients")
		.then((response) => {
			return response.json();
		})
	}

	componentWillMount() {
		this.setLoading();
		this.fetchAllUsers()
		.then((data) => {
			
			this.setState({
				patients: data
			});

			this.unsetLoading();
		});
	}

	setLoading() {
		this.setState({
			loading: true
		});
	}

	unsetLoading() {
		this.setState({
			loading: false
		});
	}

	changeFragment(fragment, uuid) {

		//Hack for clearing notification
		this.setState({
			notification: ""
		});
		
		switch(fragment) {
			case "home":
				this.setLoading();
				this.fetchAllUsers()
				.then((data) => {
					this.setState({
						patients: data,
						currentFragment: fragment
					});
					this.unsetLoading();
				});
				break;
			case "add-patient":
				this.setState({
					currentFragment: fragment
				});
				break;
			case "edit-patient":
				this.setState({
					currentFragment: fragment,
					currrentPatient: this.state.patients.filter((patient) => uuid === patient.uuid ? patient : null)[0]
				});
		}
	}

	displayNotification(message) {
		this.setState({
			notification: message
		});
	}

	clearNotification() {
		this.setState({
			notification: ""
		});
	}

	render() {
		let commonApi = {
			displayNotification: this.displayNotification.bind(this),
			changeFragment: this.changeFragment.bind(this),
			clearNotification: this.clearNotification.bind(this)
		}

		return (
			<div className={"happypatients " + (this.state.loading ? "loading" : "")}>
				<Notification message={this.state.notification} commonApi={commonApi} />
				<Navbar commonApi={commonApi} />
				<div className="container">
					{
						this.state.loading ? <div className="loading-overlay"></div> : 
						(this.state.currentFragment === "home" && <Patients commonApi={commonApi} patients={this.state.patients} changeFragment={this.changeFragment.bind(this)} />) ||
						(this.state.currentFragment === "add-patient" && <NewPatient commonApi={commonApi} changeFragment = {this.changeFragment.bind(this)}/>) ||
						(this.state.currentFragment === "edit-patient" && <EditPatient commonApi={commonApi} patient={this.state.currrentPatient} changeFragment={this.changeFragment.bind(this)} />)
					}
				</div>
			</div>
		);
	}
}

ReactDOM.render(<App />, document.getElementById("app-root"));
