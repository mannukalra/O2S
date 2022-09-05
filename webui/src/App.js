import './App.css';
import { useEffect, useState } from 'react';
import O2S from './comps/O2S';


function App() {

  const [envs, setEnvs] = useState([]);
  const url = 'https://localhost:8443/env/envs';

  
  useEffect(() => {
    async function fetchEnvs() {
      fetch(url)
        .then(response => response.json())
        .then(data => {
          setEnvs(data);
          // console.log(data)
        });
    }
    fetchEnvs();
    // const interval = setInterval(fetchEnvs, 5000);
    // return () => clearInterval(interval);
  }, [url]);


  return (
    <div className="App">
      <O2S envs={envs}/>
    </div>
  );
}

export default App;
