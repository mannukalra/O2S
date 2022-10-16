import React from 'react';


function Grafana(props) {
    const millisNow = Date.now();
    const window = 8;//hours
    const nowMinusWindow = millisNow - (window * 3600000);
    let url = 'http://localhost:3000?orgId=1&refresh=5s&from='+nowMinusWindow+'&to='+millisNow;
    if(props.host)
        url = 'http://localhost:3000/d/'+props.host+'/'+props.host+'-insights?orgId=1&refresh=5s&from='+nowMinusWindow+'&to='+millisNow+'&kiosk=tv'
    return (
        <div>
            <iframe src={url}
                frameBorder="0" style={{width: "-webkit-fill-available", height: "92vh", margin: "-1.5rem", marginTop: "-2rem", marginBottom: "-2rem"}}
                allowFullScreen
            >
            </iframe>
        </div>
    );
}

export default Grafana;
