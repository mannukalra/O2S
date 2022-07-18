import './App.css';
import { useEffect, useState } from 'react';
import Env from './comps/Env';

function getEnvs(envs){
  return envs.map( (env, index) =>{
    return <Env key={index} name={env.name}></Env>
  });
}


function App() {

  const [envs, setEnvs] = useState([]);

  useEffect(() => {
    fetch('http://localhost:8080/envs/all')
      .then(response => response.json())
      .then(data => {
        setEnvs(data);
        // console.log(data)
      })
  }, []);


  return (
    <div className="App">
      <header className="App-header">
        {getEnvs(envs)}
      </header>
      
    </div>
  );
}

export default App;
