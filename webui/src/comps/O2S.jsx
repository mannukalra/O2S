import * as React from 'react';
import { styled, useTheme } from '@mui/material/styles';
import Box from '@mui/material/Box';
import MuiDrawer from '@mui/material/Drawer';
import MuiAppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import List from '@mui/material/List';
import CssBaseline from '@mui/material/CssBaseline';
import Typography from '@mui/material/Typography';
import Divider from '@mui/material/Divider';
import IconButton from '@mui/material/IconButton';
import MenuIcon from '@mui/icons-material/Menu';
import ChevronLeftIcon from '@mui/icons-material/ChevronLeft';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import HiveIcon from '@mui/icons-material/Hive';
import SettingsIcon from '@mui/icons-material/Settings';
import { Alert, Button, Grid, Tooltip } from '@mui/material';
import ExpandLess from '@mui/icons-material/ExpandLess';
import ExpandMore from '@mui/icons-material/ExpandMore';
import Collapse from '@mui/material/Collapse';
import MonitorHeartIcon from '@mui/icons-material/MonitorHeart';
import IntegrationInstructionsIcon from '@mui/icons-material/IntegrationInstructions';
import LogoDevIcon from '@mui/icons-material/LogoDev';
import LabelImportantIcon from '@mui/icons-material/LabelImportant';
import Logo from '../img/o2s-logo.png';
import Envs from './envs/Envs';
import Devices from './devices/Devices';
import Settings  from './settings/Settings';

const drawerWidth = 280;
const envsTxt = "Environments";
const settingsTxt = "Settings";
const evnTypeIcons = {PROD: <MonitorHeartIcon />, QA: <IntegrationInstructionsIcon />, DEV: <LogoDevIcon />, OTHER: <LabelImportantIcon />};
const evnStatusColors = {HEALTHY: "#4caf50", INFO: "#03a9f4", WARNING: "#ff9800", ERROR: "#ef5350"}; 
const typeSortOrder = {"PROD": 1, "QA": 2, "DEV": 3, "OTHER": 4};

const openedMixin = (theme) => ({
    width: drawerWidth,
    transition: theme.transitions.create('width', {
        easing: theme.transitions.easing.sharp,
        duration: theme.transitions.duration.enteringScreen,
    }),
    overflowX: 'hidden',
});

const closedMixin = (theme) => ({
    transition: theme.transitions.create('width', {
        easing: theme.transitions.easing.sharp,
        duration: theme.transitions.duration.leavingScreen,
    }),
    overflowX: 'hidden',
    width: `calc(${theme.spacing(7)} + 1px)`,
    [theme.breakpoints.up('sm')]: {
        width: `calc(${theme.spacing(8)} + 1px)`,
    },
});

const DrawerHeader = styled('div')(({ theme }) => ({
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'flex-end',
    padding: theme.spacing(0, 1),
    // necessary for content to be below app bar
    ...theme.mixins.toolbar,
}));

const AppBar = styled(MuiAppBar, {
    shouldForwardProp: (prop) => prop !== 'open',
})(({ theme, open }) => ({
    zIndex: theme.zIndex.drawer + 1,
    transition: theme.transitions.create(['width', 'margin'], {
        easing: theme.transitions.easing.sharp,
        duration: theme.transitions.duration.leavingScreen,
    }),
    ...(open && {
        // marginLeft: drawerWidth, width: `calc(100% - ${drawerWidth}px)`,
        transition: theme.transitions.create(['width', 'margin'], {
            easing: theme.transitions.easing.sharp,
            duration: theme.transitions.duration.enteringScreen,
        }),
    }),
}));

const Drawer = styled(MuiDrawer, { shouldForwardProp: (prop) => prop !== 'open' })(
    ({ theme, open }) => ({
        width: drawerWidth,
        flexShrink: 0,
        whiteSpace: 'nowrap',
        boxSizing: 'border-box',
        ...(open && {
            ...openedMixin(theme),
            '& .MuiDrawer-paper': openedMixin(theme),
        }),
        ...(!open && {
            ...closedMixin(theme),
            '& .MuiDrawer-paper': closedMixin(theme),
        }),
    }),
);


function envList(envs, selectEnv){
    return envs.map((env, index) => (
        <Tooltip key={env.id} title={env.type +" Environment "+ env.name} placement="right-start">
            <ListItemButton sx={{ pl: 4, ml: "18px" }} id={env.id} onClick={selectEnv}>
                <ListItemIcon sx={{ mr: "-7px", color: env.status ? evnStatusColors[env.status] : null }}>
                    {evnTypeIcons[env?.type]}
                </ListItemIcon>
                <ListItemText primary={env.name?.length > 18 ? env.name.substring(0, 18) + "..." : env.name } />
            </ListItemButton>
        </Tooltip>
    ));
}

export default function O2S(props) {
    // const theme = useTheme();
    const [drawerOpen, setDrawerOpen] = React.useState(false);
    const [alert, setAlert] = React.useState({open: false, severity: "", message: ""});
    const [envDDOpen, setEnvDDOpen] = React.useState(false);
    const [selectedEnv, setSelectedEnv] = React.useState(null);
    const [selectedPage, setSelectedPage] = React.useState("E");

    const handleDrawerOpen = () => {
        setDrawerOpen(true);
        setEnvDDOpen(true);
    };

    const handleDrawerClose = () => {
        setDrawerOpen(false);
        setEnvDDOpen(false);
    };

    const handleEnvDDClick = (e) => {
        setEnvDDOpen(!envDDOpen);
    };

    const openAlert = (severity, message)=>{
        setAlert({ ...alert, open: true, severity: severity, message: message });
    }

    const closeAlert = ()=>{
        setAlert({ ...alert, open: false, severity: "", message: "" });
    }

    const selectEnv = (e) => {
        setSelectedPage("E");
        if(e.type != "click"){
            setSelectedEnv(e);
        }else if(e.currentTarget.id){
            let currSelectedEnv = props.envs.find((env) => {
                return env.id == e.currentTarget.id;
            });//TODO ID based map to find fast
            setSelectedEnv(currSelectedEnv);
        }else{
            setSelectedEnv(null);
        }
    };

    const selectSettings = (e) => {
        setSelectedPage("S");
    };

    return (
        <Box sx={{ display: 'flex' }}>
            <CssBaseline />
            <AppBar position="fixed" open={drawerOpen} sx={{ background: '#002F6C' }}>
                <Toolbar>
                    <IconButton
                        color="inherit"
                        aria-label="open drawer"
                        onClick={handleDrawerOpen}
                        edge="start"
                        sx={{ marginRight: 1.6, ...(drawerOpen && { display: 'none' }) }}
                    >
                        <MenuIcon />
                    </IconButton>
                    <IconButton
                        color="inherit"
                        aria-label="close drawer"
                        onClick={handleDrawerClose}
                        edge="start"
                        sx={{ marginRight: 1.6, ...(!drawerOpen && { display: 'none' }) }}
                    >
                        <ChevronLeftIcon />
                    </IconButton>
                    <Box component="img" sx={{ height: 56 }} alt="O2S logo" src={Logo} />
                    {/* <Typography variant="h6" noWrap component="div">
                        O2S
                    </Typography> */}
                </Toolbar>
            </AppBar>
            <Drawer variant="permanent" open={drawerOpen}>
                <DrawerHeader />
                <Divider />
                <List>
                    <ListItem key={envsTxt} disablePadding sx={{ display: 'block' }}>
                        <ListItemButton sx={{ minHeight: 48, justifyContent: drawerOpen ? 'initial' : 'center', px: 2.5 }} >
                            <ListItemIcon sx={{ minWidth: 0, mr: drawerOpen ? 2 : 'auto', justifyContent: 'center' }} onClick={selectEnv} >
                                <Tooltip title={envsTxt} placement="right-start">
                                    <IconButton>
                                        <HiveIcon />
                                    </IconButton>
                                </Tooltip>
                            </ListItemIcon>
                            <ListItemText primary={envsTxt} sx={{ opacity: drawerOpen ? 1 : 0 }} onClick={selectEnv} />
                            {drawerOpen ? envDDOpen ? <ExpandLess onClick={handleEnvDDClick} /> : <ExpandMore onClick={handleEnvDDClick} /> : ""}
                        </ListItemButton>
                        <Collapse in={envDDOpen} timeout="auto" unmountOnExit>
                            <List component="div" disablePadding >
                                {envList(props.envs?.sort((a, b) => (typeSortOrder[a.type] < typeSortOrder[b.type]) ? -1 : (typeSortOrder[a.type] > typeSortOrder[b.type]) ? 1 : (a.name > b.name) ? 1 : -1 ), selectEnv)}
                            </List>
                        </Collapse>
                    </ListItem>
                    <Divider />
                    <ListItem key={settingsTxt} disablePadding sx={{ display: 'block' }} onClick={selectSettings} >
                        <ListItemButton sx={{ minHeight: 48, justifyContent: drawerOpen ? 'initial' : 'center', px: 2.5 }} >
                            <ListItemIcon sx={{ minWidth: 0, mr: drawerOpen ? 2 : 'auto', justifyContent: 'center' }} >
                                <Tooltip title={settingsTxt} placement="right-start">
                                    <IconButton>
                                        <SettingsIcon />
                                    </IconButton>
                                </Tooltip>
                            </ListItemIcon>
                            <ListItemText primary={settingsTxt} sx={{ opacity: drawerOpen ? 1 : 0 }} />
                        </ListItemButton>
                    </ListItem>
                </List>
            </Drawer>
            <Box component="main" sx={{ flexGrow: 1, p: 3 }}>
                <DrawerHeader />
                <div>
                    {alert.open && <Alert severity={alert.severity} onClose={() => {closeAlert()}}>{alert.message}</Alert>}
                </div>
                {
                selectedPage == "S" ? <Settings />:
                selectedPage == "E" && selectedEnv ? 
                    <Devices devices={selectedEnv?.devices} envId={selectedEnv.id} openAlert={openAlert} /> : 
                    <Envs envs={props.envs} selectEnv={selectEnv} openAlert={openAlert}/>
                }
                
            </Box>
        </Box>
    );
}
