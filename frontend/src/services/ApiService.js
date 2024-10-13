import { useAuth0 } from '@auth0/auth0-react';

/**
 * Shared API service for making authenticated requests
 */
const ApiService = () => {
  const { getAccessTokenSilently } = useAuth0();

  /**
   * Helper function to make authenticated requests
   */
  const authenticatedFetch = async (url, options = {}) => {
    const token = await getAccessTokenSilently(); // Get the JWT token using Auth0's SDK

    const headers = {
      'Content-Type': 'application/json',
      ...options.headers,
      Authorization: `Bearer ${token}` // Add JWT to Authorization header
    };

    return fetch(url, {
      ...options,
      headers,
    }).then(response => {
      if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
      }
      return response.json();
    });
  };

  return {
    authenticatedFetch,
  };
};

export default ApiService;
