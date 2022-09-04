import { Button, Grid } from '@mui/material';
import React from 'react';
import DeviceCard from './DeviceCard';
import AddDevice from './AddDevice';


function deviceCards(devices) {
    return devices?.map((device, index) => (
        <Grid key={index} item xs={12}>
            <DeviceCard device={device} />
        </Grid>
    ));
}

function Devices(props) {
    const [addDeviceOpen, setAddDeviceOpen] = React.useState(false);
    const handleOpenAddDevice = () => {
        setAddDeviceOpen(true);
    };
    
    const handleCloseAddDevice = () => {
        setAddDeviceOpen(false);
    };

    return (
        <div>
            <Grid container rowSpacing={1} >
                {deviceCards(props.devices)}
            </Grid>
            <br />
            <Button variant="outlined" onClick={handleOpenAddDevice}>Add Device</Button> 
            <AddDevice 
                label="Add Device"
                open={addDeviceOpen}
                device={{envId: props.envId, host: "", protocol: "", userName: "", password: ""}}
                openAlert={props.openAlert}
                handleClose={handleCloseAddDevice}/>
        </div>
    );
}

export default Devices;
