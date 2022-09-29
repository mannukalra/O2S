import { Dialog, DialogContent, DialogTitle, IconButton, InputBase } from "@mui/material";
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
			};
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
					console.error("Command failed -"+content.message);
				}
				console.log(">>>"+input+"<<<");
				setOutput(output+"$" +input +"\n"+content.message+"\n");
				setInput("");
			})();
		}
    };

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
				{output ? <div style={{ color: "white", whiteSpace: "pre-wrap" }}>
					{output}
					</div> : null}
				<InputBase
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
	);
};

export default Terminal;
