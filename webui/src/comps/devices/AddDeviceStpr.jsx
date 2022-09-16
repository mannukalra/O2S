import { Button, Dialog, DialogActions, DialogContent, DialogTitle, LinearProgress, MenuItem, Step, StepLabel, Stepper, TextField, Typography } from '@mui/material';
import { Box, margin } from '@mui/system';
import React, { useState } from 'react';
import RetrieveDevice from './RetrieveDevice';
import ConfigureDevice from './ConfigureDevice';


//TODO Consts for env/device status
const protocols = [ { value: 'SSH', label: 'SSH'}, { value: 'WINRM', label: 'WINRM'}];
const steps = ['Retrieve', 'Configure'];


export default function AddDeviceStpr(props) {
    const [device, setDevice] = React.useState(props.device);
    const [activeStep, setActiveStep] = useState(0);
    const [loading, setLoading] = React.useState(false);

    function saveDevice(event) {
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
          
            console.log("saved device with id- "+content);
            props.handleClose();
            props.openAlert("success", "Saved Device Successfully!");
          })();
    };

    function retrieveDevice(event) {
        event.preventDefault();
        setLoading(true);
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
        
            console.log("Retieved device with result -"+content.os);
            setDevice({ ...device, os: content.os, alias: "", type: content.type, basePath: content.basePath, status: content.status });
            setLoading(false);
            setActiveStep(1);
        })();
    };

    return (
        <Dialog open={props.open} onClose={props.handleClose} maxWidth='lg'>
            <DialogTitle>{props.label}</DialogTitle>
            <LinearProgress sx={{ display: loading ? undefined : 'none', width: '84%', alignSelf: "center" }}/>
            <DialogContent>
                <React.Fragment>
                    <Stepper activeStep={activeStep} sx={{margin: "1.5rem"}}>
                        {steps.map(label => (
                        <Step key={label}>
                            <StepLabel>{label}</StepLabel>
                        </Step>
                        ))}
                    </Stepper>
                </React.Fragment>
                { activeStep === 0  ?  
                    <RetrieveDevice device={props.device} setDevice={setDevice} /> : 
                    <ConfigureDevice device={device} setDevice={setDevice} /> }
            </DialogContent>
            <DialogActions>
                <Button variant="outlined" onClick={props.handleClose} >Cancel</Button>
                <Button variant="outlined" disabled={loading} onClick={activeStep === 0  ? retrieveDevice : saveDevice}>{activeStep === 0  ? "Retrieve" : "Save"}</Button>
            </DialogActions>
        </Dialog>
    );
}