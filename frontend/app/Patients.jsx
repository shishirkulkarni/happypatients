import React, {Component} from 'react';

export default class Patients extends Component {

	constructor() {
		super();
		this.state = {
			patients: []
		}
	}

	componentWillMount() {
		fetch("http://localhost:8080/happypatients/webapi/patients")
		.then((response) => {
			return response.json();
		}).then((data) => {
			this.setState({
				patients: data
			})
		});
	}

	deletePatient(uuid) {
		fetch("http://localhost:8080/happypatients/webapi/patients/" + uuid, {
			method: "DELETE"
		}).then((data) => {
			this.props.changeFragment("home");
		});
	}

	updatePatient(uuid) {
		this.props.changeFragment("edit-patient", uuid);
	}

	addPatient() {
		this.props.changeFragment("add-patient");
	}

	render() {
		return (
			<div className="patient-list">
				<div className="row">
					<div className="col-md-12">
						<h1>Patients</h1>
						<button className="btn btn-primary pull-right" onClick={this.addPatient.bind(this)}>Add New Patient</button>
					</div>
				</div>
				<div className="row">
					<div className="col-md-12 col-lg-12 col-sm-12">
						<table className="table table-striped">
							<thead>
								<tr>
									<th>
										Name
									</th>
									<th>
										Address
									</th>
									<th>
										Phone Number
									</th>
									<th>
										Diagnosis
									</th>
									<th>
										Treatment
									</th>
									<th>
									</th>
									<th>
									</th>
								</tr>
							</thead>
							<tbody>
								{this.state.patients.map((patient) => {
									return (
										<tr key={patient.uuid}>
											<td>
												{patient.name}
											</td>
											<td>
												{patient.address}
											</td>
											<td>
												{patient.phone}
											</td>
											<td>
												{patient.diagnosis}
											</td>
											<td>
												{patient.treatment}
											</td>
											<td>
												<span className="glyphicon glyphicon-pencil edit-patient" onClick={this.updatePatient.bind(this, patient.uuid)}></span>
											</td>
											<td>
												<span className="glyphicon glyphicon-remove delete-patient" onClick={this.deletePatient.bind(this, patient.uuid)}></span>
											</td>
										</tr>
									)}
								)}								
							</tbody>
						</table>
					</div>
				</div>
			</div>
		);
	}
}