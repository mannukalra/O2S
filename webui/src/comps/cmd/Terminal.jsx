import { Dialog, DialogContent, DialogTitle, IconButton, TextField, Typography } from "@mui/material";
import CloseIcon from '@mui/icons-material/Close';
import React, { useEffect, useRef } from "react";
import { useState } from "react";




function Terminal(props){
	const [input, setInput] = useState(props.input);
	const [output, setOutput] = useState(props.output);

	function runCommand(event) {
		if(event.key === "Enter"){
			let data ={
				command: input,
				host: props.device.host,
				user: props.device.user,
				password: props.device.password,
				protocol: props.device.protocol,
				isShell: props.device.type == "WINDOWS"
			}
			debugger
			(async () => {
				const rawResponse = await fetch('https://localhost:8443/device/shell', {
				method: 'POST',
				headers: {
					'Accept': 'application/json',
					'Content-Type': 'application/json'
				},
				body: JSON.stringify(data)
				});
				const content = await rawResponse.json();
				if(content.status == "error"){
					console.error("Retieval failed -"+content.message);
					alert("command failed -"+content.message);
				}else{
					console.log("command succeeded");
					setOutput(output+"\n"+ input +"-\n"+content.message);
				}
				setInput("");
			})();
		}
    };

	return (
		<div>
			<Dialog open={props.open} onClose={props.handleClose} maxWidth='xl'
				fullWidth
				hideBackdrop
				disableEnforceFocus
				disableScrollLock
				PaperProps={{ sx: { alignSelf: "end", marginBottom: ".5rem", maxHeight: "60vh", bgcolor: "#333" } }}
			>
				<DialogTitle sx={{ color: "white", display: "flex", justifyContent: "space-between" }}>
					<div>{"Shell "+props.device.host}</div>
					<IconButton sx={{ color: "white" }} onClick={props.handleClose}>
						<CloseIcon />
					</IconButton>
				</DialogTitle>
				<DialogContent>
					{output ? <p style={{ color: "white" }}>{output}</p> : null}
					<TextField
						rows={8}
						variant="standard"
						value={input}
						onChange={ e => setInput(e.target.value)}
						onKeyDown={runCommand}
						inputProps={{
							style: { color: 'white' }
						}}
						multiline fullWidth />
				</DialogContent>
			</Dialog>
		</div>
	);
};

export default Terminal;
