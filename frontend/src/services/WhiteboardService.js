import ApiService from './ApiService';

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL + '/whiteboards';

/**
 * Service to interact with the Whiteboard API
 */
const WhiteboardService = () => {
    const { authenticatedFetch } = ApiService();

  // Get all whiteboards for the authenticated user
  const getAllWhiteboards = async () => {
    const url = `${API_BASE_URL}`;
    return authenticatedFetch(url, {
      method: 'GET',
    });
  };

  // Create a new whiteboard
  const createWhiteboard = async (whiteboard) => {
    const url = `${API_BASE_URL}`;
    return authenticatedFetch(url, {
      method: 'POST',
      body: JSON.stringify(whiteboard),
    });
  };

  // Update an existing whiteboard
  const updateWhiteboard = async (id, updatedWhiteboard) => {
    const url = `${API_BASE_URL}/${id}`;
    return authenticatedFetch(url, {
      method: 'PUT',
      body: JSON.stringify(updatedWhiteboard),
    });
  };

  // Delete a whiteboard
  const deleteWhiteboard = async (id) => {
    const url = `${API_BASE_URL}/${id}`;
    return authenticatedFetch(url, {
      method: 'DELETE',
    });
  };

  return {
    getAllWhiteboards,
    createWhiteboard,
    updateWhiteboard,
    deleteWhiteboard
  };
};

export default WhiteboardService;
