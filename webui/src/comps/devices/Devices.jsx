import { Button, Grid } from '@mui/material';
import { useEffect, useState } from 'react';
import DeviceCard from './DeviceCard';
import AddDevice from './AddDeviceStpr';


function deviceCards(devices) {
    return devices?.map((device, index) => (
        <Grid key={index} item xs={12}>
            <DeviceCard device={device} />
        </Grid>
    ));
}

function Devices(props) {
    // const [envId, setEnvId] = useState(props.envId);
    const [devices, setDevices] = useState([]);
    const [addDeviceOpen, setAddDeviceOpen] = useState(false);

    const url = "https://localhost:8443/device/devices/"+ props.envId;

    
    useEffect(() => {
        async function fetchDevices() {
        fetch(url)
            .then(response => response.json())
            .then(data => {
                if(data)
                    setDevices(data);
                // console.log(data)
            });
        }
        fetchDevices();
        // const interval = setInterval(fetchDevices, 5000);
        // return () => clearInterval(interval);
    }, [url]);

    
    const handleOpenAddDevice = () => {
        setAddDeviceOpen(true);
    };
    
    const handleCloseAddDevice = () => {
        setAddDeviceOpen(false);
    };

    return (
        <div>
            <Grid container rowSpacing={1} >
                {deviceCards(devices)}
            </Grid>
            <br />
            <Button variant="outlined" onClick={handleOpenAddDevice}>Add Device</Button> 
            {   addDeviceOpen &&
                <AddDevice 
                    label="Add Device"
                    open={addDeviceOpen}
                    device={{envId: props.envId, host: "", protocol: "SSH", userName: "", password: ""}}
                    openAlert={props.openAlert}
                    handleClose={handleCloseAddDevice}/>
            }
        </div>
    );
}

export default Devices;
