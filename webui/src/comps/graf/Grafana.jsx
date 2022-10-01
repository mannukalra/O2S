import React from 'react';


function Grafana(props) {
    let url = 'http://localhost:3003?orgId=1&refresh=5s&from=1664587290672&to=1664608890672';
    if(props.host)
        url = 'http://localhost:3003/d/'+props.host+'/'+props.host+'-insights?orgId=1&refresh=5s&from=1664587290672&to=1664608890672&kiosk=tv'
    return (
        <div>
            <iframe src={url}
                frameBorder="0" style={{width: "95vw",  height: "92vh", margin: "-2rem", marginLeft: "-2.2rem"}}
                allowFullScreen
            >
            </iframe>
        </div>
    );
}

export default Grafana;
