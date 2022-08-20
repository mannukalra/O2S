// import csc from 'country-state-city';
import { Box, Button, Dialog, DialogActions, DialogContent, DialogTitle, MenuItem, TextField } from '@mui/material';
import React from 'react';
import { Country, State, City }  from 'country-state-city';


function allCountries(){
    return Country.getAllCountries().map((country, index) => (
        <MenuItem key={index} value={country.isoCode}>{country.name}</MenuItem>
    ));
}

function allStates(country){
    return State.getStatesOfCountry(country).map((state, index) => (                               
        <MenuItem key={index} value={state.isoCode}>{state.name}</MenuItem>
    ));
}

function allCities(country, statee){
    return City.getCitiesOfState(country, statee).map((city, index) => (                               
        <MenuItem key={index} value={city.name}>{city.name}</MenuItem>
    ));
}


function AddEnv(props) {
    // console.log(Country.getAllCountries())
    const [name, setName] = React.useState(undefined);
    const [type, setType] = React.useState("PROD");
    const [country, setCountry] = React.useState('');
    const [statee, setStatee] = React.useState('');
    const [city, setCity] = React.useState('');
    const [description, setDescription] = React.useState(undefined);

    const handleNameChange = (event) => {
        setName(event.target.value);
    };

    const handleTypeChange = (event) => {
        setType(event.target.value);
    };

    const handleCountryChange = (event) => {
        setCountry(event.target.value);
    };

    const handleStateeChange = (event) => {
        setStatee(event.target.value);
    };

    const handleCityChange = (event) => {
        setCity(event.target.value);
    };

    const handleDescChange = (event) => {
        setDescription(event.target.value);
    };

    return (
        <Dialog open={props.open} onClose={props.handleClose} maxWidth='lg'>
            <DialogTitle>Add Environment</DialogTitle>
            <DialogContent>
            <Box
                component="form" 
                sx={{ '& .MuiTextField-root': { m: 1.2, width: '36ch' } }}
                noValidate
                autoComplete="off"
                >
                <table>
                    <tbody>
                    <tr>
                        <td>
                            <TextField id="outlined-name" label="Name" value={name} onChange={handleNameChange} required />
                        </td>
                        <td>
                            <TextField  id="outlined-type" label="Type"
                                value={type} onChange={handleTypeChange} select required >
                                <MenuItem key={1} value={'PROD'}>Production</MenuItem>
                                <MenuItem key={2} value={'QA'}>Quality Assurance</MenuItem>
                                <MenuItem key={3} value={'DEV'}>Development</MenuItem>
                                <MenuItem key={4} value={'OTHER'}>Other</MenuItem>
                            </TextField>
                        </td>
                    </tr>
                    <tr>
                        <td rowSpan={4}>
                            <TextField id="outlined-multiline-flexible" label="Description" 
                                rows={8} value={description} onChange={handleDescChange} multiline />
                        </td>
                        <td>
                            <TextField id="outlined-country" select required
                                label="Country" value={country} onChange={handleCountryChange} variant="outlined">
                                    {allCountries()}
                            </TextField>
                        </td>                           
                    </tr>
                    <tr>
                        <td>
                            <TextField id="outlined-state" select
                                label="State" value={statee} onChange={handleStateeChange} variant="outlined">
                                {allStates(country)}
                            </TextField>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <TextField id="outlined-city" select
                                label="City" value={city} onChange={handleCityChange} variant="outlined">
                                {allCities(country, statee)}
                            </TextField>
                        </td>
                    </tr>
                    </tbody>
                </table>            
            </Box>
            </DialogContent>
            <DialogActions>
                <Button variant="outlined" onClick={props.handleClose}>Cancel</Button>
                <Button variant="outlined" onClick={props.handleClose}>Save</Button>
            </DialogActions>
        </Dialog>);
}

export default AddEnv;
