/**
 * Shared API service for making authenticated requests
 */
const ApiService = () => {
    /**
     * Helper function to make authenticated requests
     */
    const authenticatedFetch = async (url, token, options = {}) => {
      const headers = {
        'Content-Type': 'application/json',
        ...options.headers,
        Authorization: `Bearer ${token}`, // Pass the token here
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
  