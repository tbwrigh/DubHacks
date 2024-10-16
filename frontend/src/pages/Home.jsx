import { useAuth0 } from '@auth0/auth0-react';
import './Home.css';

const Home = () => {
  const { loginWithRedirect } = useAuth0();

  return (
    <div className="home">
      <header className="top-bar">
        <div className="brand">PolyNote</div>
        <div className="auth-buttons">
          <button onClick={() => loginWithRedirect()}>Log In</button>
        </div>
      </header>
      <main className="home-content">
        <h1>Welcome to PolyNote!</h1>
        <p>This is a simple app to manage your notes.</p>
        <p>Log in to start using the app.</p>
      </main>
    </div>
  );
};

export default Home;
