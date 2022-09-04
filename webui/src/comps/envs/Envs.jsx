import { Button, Grid } from '@mui/material';
import React from 'react';
import AddEnv from './AddEnv';
import EnvCard from './EnvCard';



function envCards(envs, selectEnv) {
    return envs.map((env, index) => (
        <Grid key={index} item xs={4}>
            <EnvCard env={env} selectEnv={selectEnv} />
        </Grid>
    ));
}


function Envs(props) {
    const [addEnvOpen, setAddEnvOpen] = React.useState(false);
    const handleOpenAddEnv = () => {
        setAddEnvOpen(true);
    };
    
    const handleCloseAddEnv = () => {
        setAddEnvOpen(false);
    };

    return (
        <div>
            <Grid container rowSpacing={1} columnSpacing={{ xs: 1, sm: 2, md: 3 }} >
                {envCards(props.envs, props.selectEnv)}
            </Grid>
            <br />
            <Button variant="outlined" onClick={handleOpenAddEnv}>Add Environment</Button> 
            <AddEnv 
                label="Add Environment"
                env={{name:"", type:"", country:"", state:"", city:""}}
                open={addEnvOpen}
                openAlert={props.openAlert}
                handleClose={handleCloseAddEnv}/>
        </div>
    );
}

export default Envs;
