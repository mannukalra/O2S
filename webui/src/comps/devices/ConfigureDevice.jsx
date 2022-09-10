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
            case 'userName':
                setDeviceLocal({ ...device, userName: value });
                break;
            case 'password':
                setDeviceLocal({ ...device, password: value });
                break;
            case 'alias':
                setDeviceLocal({ ...device, alias: value });
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
                        <td><TextField id="outlined-username" label="User" name="userName" value={device.userName} onChange={handleChange} required /> </td>
                        <td><TextField id="outlined-password" label="Password" name="password" value={device.password} onChange={handleChange} required /> </td>
                    </tr>
                    <tr>
                        <td><TextField id="outlined-alias" label="Alias" name="alias" value={device.alias} onChange={handleChange} required /> </td>
                    </tr>
                </tbody>
            </table>
        </Box>
    );
}

export default ConfigureDevice;