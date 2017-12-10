import React, {Component} from 'react';

export default class EditPatient extends Component {
	constructor() {
		super();
		this.state = {};
	}

	componentWillMount() {	
		// debugger;
		if(!this.props.patient)
			throw new Error("A patient must be specified");
		
		this.setState({
			name: this.props.patient.name,
			address: this.props.patient.address,
			dob: this.props.patient.dob,
			diagnosis: this.props.patient.diagnosis,
			diagnosisDate: this.props.patient.diagnosisDate,
			email: this.props.patient.email,
			phone: this.props.patient.phone,
			treatment: this.props.patient.treatment,
			uuid: this.props.patient.uuid
		});
	}

	submitForm(e) {
		e.preventDefault();

		fetch("http://localhost:8080/happypatients/webapi/patients/" + this.state.uuid, {
			method: "PUT",
			body: JSON.stringify(this.state),
			headers: {
				"Content-Type": "application/json"
			}
		}).then(() => {
			this.props.changeFragment("home");
			this.props.commonApi.displayNotification("Success!!! Patient updated");
		});
	}

	render() {
		return (
			<div>
				<div className="row">
					<div className="col-md-12 col-lg-12 col-sm-12">
						<h1>Edit Patient</h1>
					</div>
					<div className="col-md-12 col-lg-12 col-sm-12">
						<form>
							<div className="form-group">
								<label htmlFor="name">Name: </label>
								<input type="text" placeholder="Patient's name" id="name" className="form-control"
								onChange={(event)=> this.setState({name: event.currentTarget.value})}
								value={this.state.name}/>
							</div>
							<div className="form-group">
								<label htmlFor="address">Address: </label>
								<input type="text" placeholder="Patient's address" id="address" className="form-control"
								onChange={(event)=> this.setState({address: event.currentTarget.value})}
								value={this.state.address}/>
							</div>
							<div className="form-group">
								<label htmlFor="email">Email: </label>
								<input type="email" placeholder="Patient's email" id="email" className="form-control"
								onChange={(event)=> this.setState({email: event.currentTarget.value})}
								value={this.state.email}/>
							</div>
							<div className="form-group">
								<label htmlFor="phone">Phone: </label>
								<input type="number" placeholder="Patient's Phone" id="phone" className="form-control"
								onChange={(event)=> this.setState({phone: event.currentTarget.value})}
								value={this.state.phone}/>
							</div>
							<div className="form-group">
								<label htmlFor="dob">Date of Birth: </label>
								<input type="text" placeholder="Patient's Date of Birth" id="dob" className="form-control"
								onChange={(event)=> this.setState({dob: event.currentTarget.value})}
								value={this.state.dob}/>
							</div>
							<div className="form-group">
								<label htmlFor="diagnosis">Diagnosis</label>
								<input type="text" placeholder="Patient's Diagnosis" id="diagnosis" className="form-control"
								onChange={(event)=> this.setState({diagnosis: event.currentTarget.value})}
								value={this.state.diagnosis}/>
							</div>
							<div className="form-group">
								<label htmlFor="diagnosis-date">Diagnosis Date:</label>
								<input type="text" placeholder="Patient's Diagnosis Date" id="diagnosis-date" className="form-control"
								onChange={(event)=> this.setState({diagnosisDate: event.currentTarget.value})}
								value={this.state.diagnosisDate}/>
							</div>
							<div className="form-group">
								<label htmlFor="treatment">Treatment: </label>
								<input type="text" placeholder="Treatment" id="treatment" className="form-control"
								onChange={(event)=> this.setState({treatment: event.currentTarget.value})}
								value={this.state.treatment}/>
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