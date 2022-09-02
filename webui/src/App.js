import './App.css';
import { useEffect, useState } from 'react';
import MiniDrawer from './comps/MiniDrawer';


function App() {

  const [envs, setEnvs] = useState([]);
  const url = 'http://localhost:8080/o2s/envs';

  
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
      <MiniDrawer envs={envs}/>
    </div>
  );
}

export default App;
