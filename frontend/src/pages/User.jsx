import { useEffect, useState, useRef } from 'react';
import { useAuth0 } from '@auth0/auth0-react';
import WhiteboardService from '../services/WhiteboardService'; // Import the WhiteboardService
import WhiteboardObjectService from '../services/WhiteboardObjectService';
import './User.css'; // We'll create this file for styling

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'; // Import FontAwesome component
import { faTrashAlt, faPencilAlt, faCheck, faTimes } from '@fortawesome/free-solid-svg-icons'; // Import specific icons
import { debounce } from 'lodash'; // Import debounce
import Draggable from 'react-draggable'; // Import Draggable


const User = () => {
  const { logout, user, isAuthenticated, getIdTokenClaims } = useAuth0();
  const [idToken, setIdToken] = useState(null);
  const [whiteboards, setWhiteboards] = useState([]);
  const [editingWhiteboard, setEditingWhiteboard] = useState(null); // Track which whiteboard is being edited
  const [newWhiteboardName, setNewWhiteboardName] = useState(''); // New whiteboard name state
  const [selectedWhiteboard, setSelectedWhiteboard] = useState(null); // Selected whiteboard state
  const [squares, setSquares] = useState([]); // Track added squares
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

  useEffect(() => {
    if (isAuthenticated && idToken && selectedWhiteboard) {
      const fetchWhiteboardObjects = async () => {
        const { getAllWhiteboardObjects } = WhiteboardObjectService();
        try {
          const fetchedSquares = await getAllWhiteboardObjects(selectedWhiteboard, idToken);
          setSquares(fetchedSquares.map((square) => ({ ...square, data: JSON.parse(square.data) })));
        } catch {
            setSquares([]);
        }
      };
      fetchWhiteboardObjects();
    }
  }, [isAuthenticated, idToken, selectedWhiteboard]);

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

    const handleEditWhiteboard = (whiteboardId, currentName) => {
        setEditingWhiteboard({ id: whiteboardId, name: currentName });
    };

    const handleCancelEdit = () => {
        setEditingWhiteboard(null); // Cancel editing and exit
    };    

    const handleSaveWhiteboard = async (whiteboardId) => {
        const { updateWhiteboard } = WhiteboardService();
        try {
            await updateWhiteboard(whiteboardId, { name: editingWhiteboard.name }, idToken);
        }catch {
            console.error('Failed to update whiteboard');
        }
        setWhiteboards(whiteboards.map(wb => wb.id === whiteboardId ? { ...wb, name: editingWhiteboard.name } : wb));
        setEditingWhiteboard(null); // Exit editing mode after saving
    };

    const handleNameChange = (event) => {
        setEditingWhiteboard({ ...editingWhiteboard, name: event.target.value });
      };    

    const handleDeleteWhiteboard = async (id) => {
        const { deleteWhiteboard } = WhiteboardService();
        try {
            await deleteWhiteboard(id, idToken);
        } catch {
            console.error('Failed to delete whiteboard');
        }
        setWhiteboards(whiteboards.filter((whiteboard) => whiteboard.id !== id));
    };

    const handleAddSquare = async () => {
        const { createWhiteboardObject } = WhiteboardObjectService();
        try {
            let newSquare = await createWhiteboardObject(
                { whiteboardId: selectedWhiteboard, posX: 0, posY: 0, data: JSON.stringify({width: 100, height: 100}) }, idToken);
            newSquare.data = JSON.parse(newSquare.data);
            setSquares([...squares, newSquare]);
        } catch {
            console.error('Failed to add square');
        }
      };

      const saveSquareResize = debounce(async (id, newWidth, newHeight, selectedWhiteboard, idToken) => {
        const square = squares.find((square) => square.id === id);
        if (square) {
          const { updateWhiteboardObject } = WhiteboardObjectService();
          try {
            await updateWhiteboardObject(
              id,
              {
                whiteboardId: selectedWhiteboard,
                data: JSON.stringify({ width: newWidth, height: newHeight }),
                posX: square.posX,
                posY: square.posY,
              },
              idToken
            );
          } catch (error) {
            console.error('Failed to resize square', error);
          }
        }
      }, 300); // 300ms debounce delay      
    

      const handleResizeSquare = async (id, newWidth, newHeight) => {
        setSquares(
            squares.map((square) => (square.id === id ? { ...square, data: {width: newWidth, height: newHeight} } : square))
          );
        saveSquareResize(id, newWidth, newHeight, selectedWhiteboard, idToken);
      };

        const changePosition = async (e,data,id) => {
            setSquares(
                squares.map((square) => (square.id === id ? { ...square, posX: data.x, posY: data.y } : square))
              );
            const square = squares.find((square) => square.id === id);

            if (square) {
                const { updateWhiteboardObject } = WhiteboardObjectService();
                try {
                    await updateWhiteboardObject(id, 
                        { whiteboardId: selectedWhiteboard, posX: data.x, posY: data.y, data: JSON.stringify(square.data) }, idToken);
                } catch {
                    console.error('Failed to update square position');
                }
            }
        }

  return (
    <div className="user-page">
      <aside className="sidebar">
        <div className="sidebar-top">
          <button onClick={handleNewWhiteboard} className="new-btn">New</button>
          <div className="whiteboard-list">
            {whiteboards.length > 0 ? (
              <ul>
                {whiteboards.map((whiteboard) => (
                  <li key={whiteboard.id} className="whiteboard" onClick={() => {setSelectedWhiteboard(whiteboard.id)}}>
                    {editingWhiteboard?.id === whiteboard.id ? (
                      <input 
                        type="text" 
                        value={editingWhiteboard.name} 
                        onChange={handleNameChange} 
                      />
                    ) : (
                      whiteboard.name
                    )}
                    <div className="whiteboard-actions">
                      {editingWhiteboard?.id === whiteboard.id ? (
                        <>
                          <FontAwesomeIcon 
                            icon={faCheck} 
                            className="save-icon" 
                            onClick={() => handleSaveWhiteboard(whiteboard.id)} 
                          />
                          <FontAwesomeIcon 
                            icon={faTimes} 
                            className="cancel-icon" 
                            onClick={handleCancelEdit} 
                          />
                        </>
                      ) : (
                        <>
                          <FontAwesomeIcon 
                            icon={faPencilAlt} 
                            className="edit-icon" 
                            onClick={() => handleEditWhiteboard(whiteboard.id, whiteboard.name)} 
                          />
                          <FontAwesomeIcon 
                            icon={faTrashAlt} 
                            className="delete-icon" 
                            onClick={() => handleDeleteWhiteboard(whiteboard.id)} 
                          />
                        </>
                      )}
                    </div>
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
        { selectedWhiteboard ? (
            <div>
                <button className="fab-btn" onClick={handleAddSquare}>+</button>
                {squares.map((square) => (
                    <Draggable 
                    key={square.id}
                    cancel=".resize-handle"
                    bounds=".main-content"
                    onStop={(e, data)=>{changePosition(e,data,square.id)}}
                    position={{ x: square.posX, y: square.posY }}
                    >
                        <div
                        className="draggable-square"
                        style={{ width: `${square.data.width}px`, height: `${square.data.height}px` }}
                        >
                        <div
                            className="resize-handle"
                            onMouseDown={(e) => {
                            e.preventDefault();
                            const startX = e.clientX;
                            const startY = e.clientY;

                            const handleMouseMove = (moveEvent) => {
                                const newWidth = square.data.width + (moveEvent.clientX - startX);
                                const newHeight = square.data.height + (moveEvent.clientY - startY);
                                handleResizeSquare(square.id, newWidth, newHeight);
                            };

                            const handleMouseUp = () => {
                                window.removeEventListener('mousemove', handleMouseMove);
                                window.removeEventListener('mouseup', handleMouseUp);
                            };

                            window.addEventListener('mousemove', handleMouseMove);
                            window.addEventListener('mouseup', handleMouseUp);
                            }}
                        />
                        </div>
                    </Draggable>
                    ))}
            </div>
        ) : (
            <p>Select a whiteboard to begin</p>
        ) }
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
