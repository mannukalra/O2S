import { Grid } from '@mui/material';
import React from 'react';
import DeviceCard from './DeviceCard';



function deviceCards(devices) {
    return devices?.map((device, index) => (
        <Grid key={index} item xs={4}>
            <DeviceCard device={device} />
        </Grid>
    ));
}

function Devices(props) {
    return (
        <Grid container rowSpacing={1} columnSpacing={{ xs: 1, sm: 2, md: 3 }} >
            {deviceCards(props.devices)}
        </Grid>
    );
}

export default Devices;
