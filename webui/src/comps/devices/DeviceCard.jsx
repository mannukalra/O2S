import React from 'react';
import { styled } from '@mui/material/styles';
import Card from '@mui/material/Card';
import CardHeader from '@mui/material/CardHeader';
import CardContent from '@mui/material/CardContent';
import CardActions from '@mui/material/CardActions';
import Collapse from '@mui/material/Collapse';
import Avatar from '@mui/material/Avatar';
import IconButton from '@mui/material/IconButton';
import Typography from '@mui/material/Typography';
import { blue } from '@mui/material/colors';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import MoreVertIcon from '@mui/icons-material/MoreVert';
import { Button } from '@mui/material';

const ExpandMore = styled((props) => {
  const { expand, ...other } = props;
  return <IconButton {...other} />;
})(({ theme, expand }) => ({
  transform: !expand ? 'rotate(0deg)' : 'rotate(180deg)',
  marginLeft: 'auto',
  transition: theme.transitions.create('transform', {
    duration: theme.transitions.duration.shortest,
  }),
}));

function DeviceCard(props) {
  const [expanded, setExpanded] = React.useState(false);

  const handleExpandClick = () => {
    setExpanded(!expanded);
  };

  const handleOpenTerminal = () => {
    props.setTerminalDevice(props.device);
    props.openTerminal();
  };

  return (
    <div>
      <Card sx={{ marginTop: "5px" }} >
        <CardHeader
          avatar={
            <Avatar sx={{ bgcolor: blue[500] }} aria-label="recipe">
              D
            </Avatar>
          }
          action={
            <IconButton aria-label="settings">
              {/* //TODO Edit option */}
              <MoreVertIcon />
            </IconButton>
          }
          title={props.device.host}
          subheader={props.device.alias}
        />
        <CardContent>
          <Typography variant="body2" color="text.secondary">
            "Chart Placeholder!!"
          </Typography>
          <Button variant="outlined" onClick={handleOpenTerminal}>Terminal</Button>
        </CardContent>
        <CardActions disableSpacing>
          <ExpandMore
            expand={expanded}
            onClick={handleExpandClick}
            aria-expanded={expanded}
            aria-label="show more"
          >
            <ExpandMoreIcon />
          </ExpandMore>
        </CardActions>
        <Collapse in={expanded} timeout="auto" unmountOnExit>
          <CardContent>
            <Typography paragraph>
              {props.device.user}
            </Typography>
          </CardContent>
        </Collapse>
      </Card>
    </div>
  );
}

export default DeviceCard;