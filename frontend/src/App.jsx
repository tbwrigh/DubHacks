import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Home from './pages/Home';
import User from './pages/User';
import { useAuth0 } from '@auth0/auth0-react';

import './App.css';


const App = () => {
  const { isAuthenticated } = useAuth0();

  return (
    <Router>
      <Routes>
        <Route path="/" element={!isAuthenticated ? <Home /> : <User /> } />
      </Routes>
    </Router>
  );
};

export default App;
