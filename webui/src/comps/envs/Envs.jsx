import { Grid } from '@mui/material';
import React from 'react';
import EnvCard from './EnvCard';



function envCards(envs, selectEnv) {
    return envs.map((env, index) => (
        <Grid key={index} item xs={4}>
            <EnvCard env={env} selectEnv={selectEnv} />
        </Grid>
    ));
}

function Envs(props) {
    return (
        <Grid container rowSpacing={1} columnSpacing={{ xs: 1, sm: 2, md: 3 }} >
            {envCards(props.envs, props.selectEnv)}
        </Grid>
    );
}

export default Envs;
