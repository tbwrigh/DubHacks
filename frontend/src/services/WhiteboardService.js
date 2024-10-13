import ApiService from './ApiService';

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL + '/whiteboards';

/**
 * Service to interact with the Whiteboard API
 */
const WhiteboardService = () => {
    const { authenticatedFetch } = ApiService();

  // Get all whiteboards for the authenticated user
  const getAllWhiteboards = async (token) => {
    const url = `${API_BASE_URL}`;
    return authenticatedFetch(url, token, {
      method: 'GET',
    });
  };

  // Create a new whiteboard
  const createWhiteboard = async (whiteboard, token) => {
    const url = `${API_BASE_URL}`;
    return authenticatedFetch(url, token, {
      method: 'POST',
      body: JSON.stringify(whiteboard),
    });
  };

  // Update an existing whiteboard
  const updateWhiteboard = async (id, updatedWhiteboard, token) => {
    const url = `${API_BASE_URL}/${id}`;
    return authenticatedFetch(url, token, {
      method: 'PUT',
      body: JSON.stringify(updatedWhiteboard),
    });
  };

  // Delete a whiteboard
  const deleteWhiteboard = async (id, token) => {
    const url = `${API_BASE_URL}/${id}`;
    return authenticatedFetch(url, token, {
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

