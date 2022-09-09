import { Box, Button, Dialog, DialogActions, DialogContent, DialogTitle, MenuItem, TextField } from '@mui/material';
import React from 'react';

//TODO Consts for env/device status
const protocols = [ { value: 'SSH', label: 'SSH'}, { value: 'WINRM', label: 'WINRM'}];

function AddDevice(props) {
    const [device, setDevice] = React.useState(props.device);
    
    function handleChange(e) {
        const { name, value } = e.target;

        switch (name) {
            case 'host':
                setDevice({ ...device, host: value });
                break;
            case 'protocol':
                setDevice({ ...device, protocol: value });
                break;
            case 'userName':
                setDevice({ ...device, userName: value });
                break;
            case 'password':
                setDevice({ ...device, password: value });
                break;
            default:
                break;
        }
    }


    function saveDevice(event) {
        debugger;
        event.preventDefault();
        (async () => {
            const rawResponse = await fetch('https://localhost:8443/device/device', {
              method: 'POST',
              headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
              },
              body: JSON.stringify(device)
            });
            const content = await rawResponse.json();
          
            console.log("saved device with id -"+content.id);
            props.handleClose();
            props.openAlert("success", "Saved Device Successfully!");
          })();
    };
    
    function retrieveDevice(event) {
        event.preventDefault();
        (async () => {
            const rawResponse = await fetch('https://localhost:8443/device/retrieve', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(device)
            });
            const content = await rawResponse.json();
        
            console.log("Retieved device with result -"+content.result);
            props.handleClose();
            props.openAlert("success", "Retieved device Successfully with result :"+content.result);
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
                                <td><TextField id="outlined-host" label="Host" name="host" value={device.host} onChange={handleChange} required /></td>
                                <td>
                                    <TextField id="outlined-protocol" label="Protocol" name="protocol" 
                                    value={device.protocol} onChange={handleChange} select required >
                                        {protocols.map((option) => (
                                            <MenuItem key={option.value} value={option.value}>
                                                {option.label}
                                            </MenuItem>
                                        ))}
                                    </TextField>
                                </td>
                            </tr>
                            <tr>
                                <td><TextField id="outlined-username" label="User" name="userName" value={device.userName} onChange={handleChange} required /> </td>
                                <td><TextField id="outlined-password" label="Password" name="password" value={device.password} onChange={handleChange} required /> </td>
                            </tr>
                        </tbody>
                    </table>
                </Box>
            </DialogContent>
            <DialogActions>
                <Button variant="outlined" onClick={props.handleClose} >Cancel</Button>
                <Button variant="outlined" onClick={saveDevice}>Retrieve</Button>
            </DialogActions>
        </Dialog>);
}

export default AddDevice;