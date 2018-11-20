import React from 'react';
import axios,{post} from 'axios';
import ReactTable from "react-table";
import * as employeeAction from '../action/employeeAction'
import {connect} from 'react-redux'

class Main extends React.Component {

    constructor(props){
        super(props);
        this.state = {
            file : null
        }
    }

    componentDidMount() {
        const url = "http://localhost:8080/employees";
        axios.get(url).then((res)=>{
            this.props.updateEmployee(res.data);
        })

    }

    downloadReport(){
        const url = "http://localhost:8080/employees/error";
        window.location.href = url;
    }

    upload(){
        const url = "http://localhost:8080/employees";
        const formData =  new FormData();
        formData.append("file",this.state.file);

        const config = {
            headers : {
                    'content-type': 'multipart/form-data'
            }
        }
        post(url, formData,config).then(res=>{
            console.log(res);
            this.props.updateEmployee(res.data);
        });
        console.log("sa");

        console.log();
    }

    onChange(e){
        this.state.file = e.target.files[0];
        console.log(this.state.file);
    }

    render() {


        const data = this.props.employee;

        const columns = [{
            Header: 'Name',
            accessor: 'name'
        }, {
            Header: 'Department',
            accessor: 'department'
        }, {
            Header: 'Designation',
            accessor: 'designation',

        }, {
            Header: 'Salary',
            accessor: 'salary'
        },
            {
                Header: 'Joining Date',
                accessor: 'joiningDate'
            }];
        return (
            <div className="Main">
                <div> <h4>Employee Information</h4>
                    <input type="file" onChange={(e)=>this.onChange(e)} style={{'paddingRight':'5px'}}/>
                    <button type="submit" className="btn btn-primary" onClick={(e)=>{e.preventDefault();
                    this.upload()}}>upload</button>
                    <button type="button" className="btn btn-link" onClick={this.downloadReport}>Error Report</button>
                </div>
                <ReactTable
                    data={data}
                    columns={columns}
                    defaultPageSize={10}
                />
            </div>
        );
    }
}

const mapStateToProps = (state,ownProps) => {
    return {
        employee : state.employee.data
    }
};

const mapDispatchToProps = (dispatch)=>{
    return {
        updateEmployee : (data) => dispatch(employeeAction.updateEmployee(data))
    }
};

export default connect(mapStateToProps,mapDispatchToProps)(Main);
