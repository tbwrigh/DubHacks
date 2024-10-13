import { useEffect, useState, useRef } from 'react';
import { useAuth0 } from '@auth0/auth0-react';
import WhiteboardService from '../services/WhiteboardService'; // Import the WhiteboardService
import './User.css'; // We'll create this file for styling

const User = () => {
  const { logout, user, isAuthenticated, getIdTokenClaims } = useAuth0();
  const [idToken, setIdToken] = useState(null);
  const [whiteboards, setWhiteboards] = useState([]);
  const [newWhiteboardName, setNewWhiteboardName] = useState(''); // New whiteboard name state
  const dialogRef = useRef(null); // Reference to the <dialog> element

  useEffect(() => {
    if (isAuthenticated) {
      const fetchIdToken = async () => {
        try {
          const token = (await getIdTokenClaims()).__raw;
          setIdToken(token);
        } catch {
            setIdToken(null);
        }
      };
      fetchIdToken();
    }
  }, [isAuthenticated, getIdTokenClaims])

  useEffect(() => {
    if (isAuthenticated && idToken) {
      const fetchWhiteboards = async () => {
        try {
          const { getAllWhiteboards } = WhiteboardService();
          const fetchedWhiteboards = await getAllWhiteboards(idToken);
          setWhiteboards(fetchedWhiteboards);
        } catch {
            setWhiteboards([]);
        }
      };
      fetchWhiteboards();
    }
  }, [isAuthenticated, idToken]);

  const handleLogout = () => {
    logout({ returnTo: window.location.origin });
  };

  const handleNewWhiteboard = () => {
    if (dialogRef.current) {
      dialogRef.current.showModal(); // Show the dialog
    }
  };

  const handleCloseModal = () => {
    if (dialogRef.current) {
      dialogRef.current.close(); // Close the dialog
    }
  };

  const handleCreateWhiteboard = async () => {
    const { createWhiteboard } = WhiteboardService();
    try {
      const newWhiteboard = await createWhiteboard({ name: newWhiteboardName }, idToken);
      setWhiteboards([...whiteboards, newWhiteboard]); // Add new whiteboard to the list
      setNewWhiteboardName(''); // Reset input
      handleCloseModal(); // Close the modal
    } catch {
        console.error('Failed to create whiteboard');
    }
  };

  return (
    <div className="user-page">
      <aside className="sidebar">
        <div className="sidebar-top">
          <button onClick={handleNewWhiteboard} className="new-btn">New</button>
          <div className="whiteboard-list">
            {whiteboards.length > 0 ? (
              <ul>
                {whiteboards.map((whiteboard) => (
                  <li key={whiteboard.id} className="whiteboard">
                    {whiteboard.name}
                  </li>
                ))}
              </ul>
            ) : (
              <p>No whiteboards available.</p>
            )}
          </div>
        </div>
        <div className="sidebar-bottom">
            <div className="user-info">
            <img src={user?.picture} alt="User Profile" />
            </div>
            <button onClick={handleLogout} className="logout-btn">Log Out</button>
        </div>
      </aside>
      <main className="main-content">
        <p>This is your user dashboard.</p>
      </main>


      <dialog ref={dialogRef} className="new-whiteboard-dialog">
        <h2>Create New Whiteboard</h2>
        <input
          type="text"
          value={newWhiteboardName}
          onChange={(e) => setNewWhiteboardName(e.target.value)}
          placeholder="Whiteboard Name"
        />
        <div className="dialog-buttons">
          <button onClick={handleCreateWhiteboard} className="create-btn">Create</button>
          <button onClick={handleCloseModal} className="close-btn">Cancel</button>
        </div>
      </dialog>

    </div>
  );
};

export default User;
