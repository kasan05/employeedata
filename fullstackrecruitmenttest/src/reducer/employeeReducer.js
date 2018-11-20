const employeeReducer = (state={data:[]},action)=>{

    switch (action.type) {
        case 'UPDATE_EMPLOYEE':
            console.log("here");
            return {
                data: action.data
            }
        default :
            return state;
    }

}
export default employeeReducer;