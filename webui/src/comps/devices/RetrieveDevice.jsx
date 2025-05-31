import { Box, MenuItem, TextField } from '@mui/material';
import React, { useEffect } from 'react';

//TODO Consts for env/device status
const protocols = [ { value: 'SSH', label: 'SSH'}, { value: 'WINRM', label: 'WINRM'}, { value: 'HTTP', label: 'HTTP'}];
const types = [ { value: 'LINUX', label: 'Linux'}, { value: 'WINDOWS', label: 'Windows'}];

function AddDevice(props) {
    const [device, setDeviceLocal] = React.useState(props.device);
    
    function handleChange(e) {
        const { name, value } = e.target;

        switch (name) {
            case 'host':
                setDeviceLocal({ ...device, host: value });
                break;
            case 'protocol':
                setDeviceLocal({ ...device, protocol: value });
                break;
            case 'type':
                setDeviceLocal({ ...device, type: value });
                break;
            case 'user':
                setDeviceLocal({ ...device, user: value });
                break;
            case 'password':
                setDeviceLocal({ ...device, password: value });
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
                            <TextField id="outlined-type" label="Type" name="type" 
                            value={device.type} onChange={handleChange} margin="normal" select required fullWidth >
                                {types.map((option) => (
                                    <MenuItem key={option.value} value={option.value}>
                                        {option.label}
                                    </MenuItem>
                                ))}
                            </TextField>
                        </td>
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
                        <td><TextField id="outlined-user" label="User" name="user" value={device.user} onChange={handleChange} required /> </td>
                        <td><TextField id="outlined-password" label="Password" name="password" value={device.password} onChange={handleChange} required /> </td>
                    </tr>
                </tbody>
            </table>
        </Box>
    );
}

export default AddDevice;