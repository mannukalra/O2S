// import csc from 'country-state-city';
import { Box, Button, Dialog, DialogActions, DialogContent, DialogTitle, MenuItem, TextField } from '@mui/material';
import React from 'react';
import { Country, State, City } from 'country-state-city';


function allCountries() {
    return Country.getAllCountries().map((country, index) => (
        <MenuItem key={index} value={country.isoCode}>{country.name}</MenuItem>
    ));
}

function allStates(country) {
    return State.getStatesOfCountry(country).map((state, index) => (
        <MenuItem key={index} value={state.isoCode}>{state.name}</MenuItem>
    ));
}

function allCities(country, statee) {
    return City.getCitiesOfState(country, statee).map((city, index) => (
        <MenuItem key={index} value={city.name}>{city.name}</MenuItem>
    ));
}

function AddEnv(props) {
    const [env, setEnv] = React.useState(props.env);

    function handleChange(e) {
        const { name, value } = e.target;

        switch (name) {
            case 'name':
                setEnv({ ...env, name: value });
                break;
            case 'type':
                setEnv({ ...env, type: value });
                break;
            case 'country':
                setEnv({ ...env, country: value });
                break;
            case 'state':
                setEnv({ ...env, state: value });
                break;
            case 'city':
                setEnv({ ...env, city: value });
                break;
            case 'description':
                setEnv({ ...env, description: value });
                break;
            default:
                break;
        }
    }

    function saveEnv(event) {
        event.preventDefault();
        (async () => {
            const rawResponse = await fetch('http://localhost:8080/env/envs', {
              method: 'POST',
              headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
              },
              body: JSON.stringify(env)
            });
            const content = await rawResponse.json();
          
            console.log("saved env with id -"+content.id);
            props.handleClose();
            props.openAlert("success", "Saved Environment Successfully!");
          })();
    };
   
    return (
        <Dialog open={props.open} onClose={props.handleClose} maxWidth='lg'>
            <DialogTitle>{props.label}</DialogTitle>
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
                                    <TextField id="outlined-name" label="Name" name="name" value={env.name} onChange={handleChange} required />
                                </td>
                                <td>
                                    <TextField id="outlined-type" label="Type" name="type"
                                        value={env.type} onChange={handleChange} select required >
                                        <MenuItem key={1} value={'PROD'}>Production</MenuItem>
                                        <MenuItem key={2} value={'QA'}>Quality Assurance</MenuItem>
                                        <MenuItem key={3} value={'DEV'}>Development</MenuItem>
                                        <MenuItem key={4} value={'OTHER'}>Other</MenuItem>
                                    </TextField>
                                </td>
                            </tr>
                            <tr>
                                <td rowSpan={4}>
                                    <TextField id="outlined-multiline-flexible" label="Description" name="description"
                                        rows={8} value={env.description} onChange={handleChange} multiline />
                                </td>
                                <td>
                                    <TextField id="outlined-country" name="country" select required 
                                        label="Country" value={env.country} onChange={handleChange} variant="outlined">
                                        {allCountries()}
                                    </TextField>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <TextField id="outlined-state" name="state" select
                                        label="State" value={env.state} onChange={handleChange} variant="outlined">
                                        {allStates(env.country)}
                                    </TextField>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <TextField id="outlined-city" name="city" select
                                        label="City" value={env.city} onChange={handleChange} variant="outlined">
                                        {allCities(env.country, env.state)}
                                    </TextField>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </Box>
            </DialogContent>
            <DialogActions>
                <Button variant="outlined" onClick={props.handleClose}>Cancel</Button>
                <Button variant="outlined" onClick={saveEnv}>Save</Button>
            </DialogActions>
        </Dialog>);
}

export default AddEnv;
