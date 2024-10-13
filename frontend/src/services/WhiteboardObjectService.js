import ApiService from './ApiService';

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL + '/whiteboard-objects';

/**
 * Service to interact with the WhiteboardObject API
 */
const WhiteboardObjectService = () => {
    const { authenticatedFetch } = ApiService();


  // Get all whiteboard objects for a specific whiteboard owned by the authenticated user
  const getAllWhiteboardObjects = async (whiteboardId, token) => {
    const url = `${API_BASE_URL}/${whiteboardId}`;
    return authenticatedFetch(url, token, {
      method: 'GET',
    });
  };

  // Create a new whiteboard object
  const createWhiteboardObject = async (whiteboardObject, token) => {
    const url = `${API_BASE_URL}`;
    return authenticatedFetch(url, token, {
      method: 'POST',
      body: JSON.stringify(whiteboardObject),
    });
  };

  // Update an existing whiteboard object
  const updateWhiteboardObject = async (id, updatedWhiteboardObject, token) => {
    const url = `${API_BASE_URL}/${id}`;
    return authenticatedFetch(url, token, {
      method: 'PUT',
      body: JSON.stringify(updatedWhiteboardObject),
    });
  };

  // Delete a whiteboard object
  const deleteWhiteboardObject = async (id, token) => {
    const url = `${API_BASE_URL}/${id}`;
    return authenticatedFetch(url, token, {
      method: 'DELETE',
    });
  };

  return {
    getAllWhiteboardObjects,
    createWhiteboardObject,
    updateWhiteboardObject,
    deleteWhiteboardObject
  };
};

export default WhiteboardObjectService;
