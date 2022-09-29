import React, {useEffect, useState} from 'react';
import {over} from 'stompjs';
import SockJS from 'sockjs-client';
import { Dialog, DialogContent, DialogTitle, IconButton, InputBase } from "@mui/material";
import CloseIcon from '@mui/icons-material/Close';


const url = 'https://localhost:8443/ws/';
var stompClient = null;

function TerminalWS(props){
    const [hostData, setHostData] = useState({
        host: props.device.host,
        status: "",
        message: ""
    })
    const [cmdResult, setCmdResult] = useState("this will be result");

    
    useEffect(() => {
        connect();
        console.log(hostData);
    }, [hostData.host]);

    const connect = () =>{
        let sock = new SockJS(url);
        stompClient = over(sock);
        stompClient.connect({}, onConnected, onError);
    }

    const onConnected = () =>{
        setHostData({...hostData, "connected": true});
        stompClient.subscribe("/topic/allhosts", onMessageReceived)
        hostJoin();
    }

    const onMessageReceived = (payload) =>{
        let payloadData = JSON.parse(payload.body);
        console.log("onMessageReceived >>>>>>>>>>>>>>>>>>>>>>>>>", payloadData);
        switch(payloadData.status){
            case "JOIN":
                break;
            case "MESSAGE":
                // cmdResults.push(payloadData);
                // setCmdResults([...cmdResults])
                setCmdResult(payloadData.message);
                break;
        }
    }

    const onError = (err) =>{
        // debugger;
        console.log(">>>>>>>>>>>>>>>>>>>"+err);
    }

    const hostJoin=()=>{
        var message = {
          host: hostData.host,
          status:"JOIN"
        };
        stompClient.send("/app/cmd", {}, JSON.stringify(message));
    }

    const handleMessage =(event)=>{
        const {value}=event.target;
        setHostData({...hostData,"message": value});
    }

    const sendCmd=(event)=>{
        if (event.key === "Enter" && stompClient) {
          var chatMessage = {
            host: hostData.host,
            message: hostData.message,
            status:"MESSAGE"
          };
          console.log(chatMessage);
          stompClient.send("/app/cmd", {}, JSON.stringify(chatMessage));
          setHostData({...hostData,"message": ""});
        }
    }

    //send on app/cmd
    //receive on /topic/allhosts
    return (
		<Dialog open={props.open} onClose={props.handleClose} maxWidth='xl'
			fullWidth
			hideBackdrop
			disableEnforceFocus
			disableScrollLock
			PaperProps={{ sx: { alignSelf: "end", marginBottom: ".5rem", maxHeight: "60vh", bgcolor: "#333" } }}
		>
			<DialogTitle sx={{ color: "white", display: "flex", justifyContent: "space-between", paddingBottom: "inherit" }}>
				<div>{"Shell "+props.device?.host}</div>
				<IconButton sx={{ color: "white" }} onClick={props.closeTerminal}>
					<CloseIcon />
				</IconButton>
			</DialogTitle>
			<DialogContent>
				{cmdResult ? <div style={{ color: "white", whiteSpace: "pre-wrap" }}>
					{cmdResult}
					</div> : null}
				<InputBase
					rows={8}
					variant="standard"
					value={hostData.message}
					onChange={handleMessage}
					onKeyDown={sendCmd}
					inputProps={{
						style: { color: 'white' }
					}}
					multiline fullWidth />
			</DialogContent>
		</Dialog>
	);
};

export default TerminalWS;
