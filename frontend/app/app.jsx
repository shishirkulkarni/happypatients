import React, {Component} from 'react';
import ReactDOM from 'react-dom';
import {config} from './config.jsx';
import '../node_modules/bootstrap/dist/css/bootstrap.min.css';
import Patients from "./Patients.jsx";
import NewPatient from "./newPatient.jsx";
import EditPatient from "./EditPatient.jsx";
import Navbar from "./Navbar.jsx";


export default class App extends Component {
	constructor() {
		super();
		this.state = {
			loading: true,
			patients: [],
			currentFragment: "home",
			currentPatient: {}
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

	render() {
		return (
			<div className="happypatients">
				<Navbar />
				<div className="container">
					{
						this.state.loading ? <div className="loading-overlay"></div> : 
						(this.state.currentFragment === "home" && <Patients patients = {this.state.patients} changeFragment={this.changeFragment.bind(this)} />) ||
						(this.state.currentFragment === "add-patient" && <NewPatient changeFragment = {this.changeFragment.bind(this)}/>) ||
						(this.state.currentFragment === "edit-patient" && <EditPatient patient={this.state.currrentPatient} changeFragment={this.changeFragment.bind(this)} />)
					}
				</div>
			</div>
		);
	}
}

ReactDOM.render(<App />, document.getElementById("app-root"));
