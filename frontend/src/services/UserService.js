import ApiService from "./ApiService";

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL + '/me';

/**
 * Service to interact with the User API
 */
const UserService = () => {
    const { authenticatedFetch } = ApiService();

  // Get the authenticated user's information
  const getUserInfo = async (token) => {
    const url = `${API_BASE_URL}`;
    return authenticatedFetch(url, token, {
      method: 'GET',
    });
  };

  return {
    getUserInfo
  };
}

export default UserService;