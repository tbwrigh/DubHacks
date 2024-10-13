import { useAuth0 } from '@auth0/auth0-react';
import './User.css'; // We'll create this file for styling

const User = () => {
  const { logout, user } = useAuth0();

  const handleLogout = () => {
    logout({ returnTo: window.location.origin });
  };

  return (
    <div className="user-page">
      <aside className="sidebar">
        <div className="sidebar-top">
          <button className="new-btn">New</button>
        </div>
        <div className="sidebar-bottom">
          <img src={user?.picture} alt="User Profile" className="user-picture" />
          <p>{user?.name}</p>
          <button onClick={handleLogout} className="logout-btn">Log Out</button>
        </div>
      </aside>
      <main className="main-content">
        <p>This is your user dashboard.</p>
      </main>
    </div>
  );
};

export default User;
