// import csc from 'country-state-city';
import { Box, Button, MenuItem, Modal, TextField } from '@mui/material';
import React from 'react';



function Overview(props) {

    const [name, setName] = React.useState(null);
    const [type, setType] = React.useState("PROD");
    const [country, setCountry] = React.useState(null);
    const [city, setCity] = React.useState(null);
    const [description, setDescription] = React.useState(null);
    const [modalIsOpen, setIsOpen] = React.useState(true);

    const handleNameChange = (event) => {
        setName(event.target.value);
    };

    const handleTypeChange = (event) => {
        setType(event.target.value);
    };

    const handleCountryChange = (event) => {
        setCountry(event.target.value);
    };

    const handleCityChange = (event) => {
        setCity(event.target.value);
    };

    const handleDescChange = (event) => {
        setDescription(event.target.value);
    };

    function openModal() {
        setIsOpen(true);
    }

    function afterOpenModal() {
    // references are now sync'd and can be accessed.
    // subtitle.style.color = '#f00';
    }

    function closeModal() {
        setIsOpen(false);
    }

    return (
        <Modal isOpen={modalIsOpen}>
            <Box
                component="form" 
                sx={{ '& .MuiTextField-root': { m: 1, width: '25ch' } }}
                noValidate
                autoComplete="off"
                >
                <td>
                    <tr>
                        <tc>
                            <TextField id="outlined-name" label="Name" value={name} onChange={handleNameChange} required />
                        </tc>
                        <tc>
                            <TextField  id="outlined-type" labelId="type-select-label" label="Type"
                                value={type} onChange={handleTypeChange} select required >
                                <MenuItem key={1} value={'PROD'}>Production</MenuItem>
                                <MenuItem key={2} value={'QA'}>Quality Assurance</MenuItem>
                                <MenuItem key={3} value={'DEV'}>Development</MenuItem>
                                <MenuItem key={4} value={'OTHER'}>Other</MenuItem>
                            </TextField>
                        </tc>
                    </tr>
                    <tr>
                        <tc>
                            <TextField id="outlined-multiline-flexible" label="Description" 
                                rows={4.5} value={description} onChange={handleDescChange} multiline />
                        </tc>
                        <tc>
                            <td>
                                <tr>
                                    <TextField id="outlined-country" labelId="country-select-label" select required
                                        label="Country" value={country} onChange={handleCountryChange} variant="outlined">
                                            <MenuItem key={1} value={'IND'}>India</MenuItem>
                                            <MenuItem key={2} value={'ITA'}>Italy</MenuItem>
                                            <MenuItem key={3} value={'USA'}>USA</MenuItem>
                                    </TextField>
                                </tr>
                                <tr>
                                    <TextField id="outlined-city" labelId="city-select-label" select required
                                        label="City" value={city} onChange={handleCityChange} variant="outlined">
                                        <MenuItem key={1} value={'BGLR'}>Bangalore</MenuItem>
                                        <MenuItem key={2} value={'ROME'}>Rome</MenuItem>
                                        <MenuItem key={3} value={'NYK'}>Newyork</MenuItem>
                                    </TextField>
                                </tr>
                            </td>
                        </tc>
                    </tr>
                    <div style={{alignItems: "right"}} >
                        <Button variant="outlined" >Cancel</Button>
                        <Button variant="outlined" >Save</Button>
                    </div>
                </td>            
            </Box>
        </Modal>);
}

export default Overview;
