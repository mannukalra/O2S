import { Box, MenuItem, TextField } from '@mui/material';
import React, { useEffect } from 'react';


function ConfigureDevice(props) {
    const [device, setDeviceLocal] = React.useState(props.device);
    
    function handleChange(e) {
        const { name, value } = e.target;

        switch (name) {
            case 'host':
                setDeviceLocal({ ...device, host: value });
                break;
            case 'os':
                setDeviceLocal({ ...device, os: value });
                break;
            case 'type':
                setDeviceLocal({ ...device, type: value });
                break;
            case 'user':
                setDeviceLocal({ ...device, user: value });
                break;
            case 'basePath':
                setDeviceLocal({ ...device, basePath: value });
                break;
            case 'alias':
                setDeviceLocal({ ...device, alias: value });
                break;
            case 'status':
                setDeviceLocal({ ...device, status: value });
                break;
            default:
                break;
        }
    }
    useEffect(() => { props.setDevice(device); }, [device]);

    return (
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
                            <TextField id="outlined-os" label="OS" name="os" value={device.os} onChange={handleChange} required />
                        </td>
                    </tr>
                    <tr>
                        <td><TextField id="outlined-user" label="User" name="user" value={device.user} onChange={handleChange} required /> </td>
                        <td><TextField id="outlined-basePath" label="Base Path" name="basePath" value={device.basePath} onChange={handleChange} required /> </td>
                    </tr>
                    <tr>
                        <td><TextField id="outlined-alias" label="Alias" name="alias" value={device.alias} onChange={handleChange} required /> </td>
                        <td><TextField id="outlined-type" label="Type" name="type" value={device.type} onChange={handleChange} required /> </td>
                        <td><TextField id="outlined-status" label="Status" name="status" value={device.status} onChange={handleChange} required /> </td>
                    </tr>
                </tbody>
            </table>
        </Box>
    );
}

export default ConfigureDevice;