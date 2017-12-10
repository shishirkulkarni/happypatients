import React, {Component} from 'react';

export default class NewPatient extends Component {
	constructor() {
		super();
		this.state = {
			name: "",
			address: "",
			dob: "",
			diagnosisDate: "",
			diagnosis: "",
			treatment: "",
			email: "",
			diagnosisDate: "",
			phone: ""
		}
	}

	submitForm(e) {
		e.preventDefault();
		fetch("http://localhost:8080/happypatients/webapi/patients", {
			method: "POST",
			body: JSON.stringify(this.state),
			headers: {
				"Content-Type": "application/json"
			}
		}).then((response) => {
			this.props.changeFragment("home");
			this.props.commonApi.displayNotification( "Success!!! Patient added");
		});
	}

	render() {
		return (
			<div>
				<div className="row">
					<div className="col-md-12 col-lg-12 col-sm-12">
						<h1>Add New Patient</h1>
					</div>
					<div className="col-md-12 col-lg-12 col-sm-12">
						<form>
							<div className="form-group">
								<label htmlFor="name">Name: </label>
								<input type="text" placeholder="Patient's name" id="name" className="form-control"
								onChange={(event)=> this.setState({name: event.currentTarget.value})}/>
							</div>
							<div className="form-group">
								<label htmlFor="address">Address: </label>
								<input type="text" placeholder="Patient's address" id="address" className="form-control"
								onChange={(event)=> this.setState({address: event.currentTarget.value})}/>
							</div>
							<div className="form-group">
								<label htmlFor="email">Email: </label>
								<input type="email" placeholder="Patient's email" id="email" className="form-control"
								onChange={(event)=> this.setState({email: event.currentTarget.value})}/>
							</div>
							<div className="form-group">
								<label htmlFor="phone">Phone: </label>
								<input type="number" placeholder="Patient's Phone" id="phone" className="form-control"
								onChange={(event)=> this.setState({phone: event.currentTarget.value})}/>
							</div>
							<div className="form-group">
								<label htmlFor="dob">Date of Birth: </label>
								<input type="text" placeholder="Patient's Date of Birth" id="dob" className="form-control"
								onChange={(event)=> this.setState({dob: event.currentTarget.value})}/>
							</div>
							<div className="form-group">
								<label htmlFor="diagnosis">Diagnosis</label>
								<input type="text" placeholder="Patient's Diagnosis" id="diagnosis" className="form-control"
								onChange={(event)=> this.setState({diagnosis: event.currentTarget.value})}/>
							</div>
							<div className="form-group">
								<label htmlFor="diagnosis-date">Diagnosis Date:</label>
								<input type="text" placeholder="Patient's Diagnosis Date" id="diagnosis-date" className="form-control"
								onChange={(event)=> this.setState({diagnosisDate: event.currentTarget.value})}/>
							</div>
							<div className="form-group">
								<label htmlFor="treatment">Treatment: </label>
								<input type="text" placeholder="Treatment" id="treatment" className="form-control"
								onChange={(event)=> this.setState({treatment: event.currentTarget.value})}/>
							</div>
							<div className="form-group">
								<button className="btn btn-primary" onClick={this.submitForm.bind(this)}>submit</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		);
	}
}