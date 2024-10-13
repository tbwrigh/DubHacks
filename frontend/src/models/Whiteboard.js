class Whiteboard {
    constructor(id, name, ownerEmail) {
      this.id = id || null;  // Use null for new whiteboards without IDs
      this.name = name || '';  // Default to empty string
      this.ownerEmail = ownerEmail || '';  // Owner email of the whiteboard
    }
  
    // Example method to update the name of the whiteboard
    updateName(newName) {
      this.name = newName;
    }
    
    // Convert to a JSON object for API requests
    toJSON() {
      return {
        id: this.id,
        name: this.name,
        ownerEmail: this.ownerEmail,
      };
    }
  }
  
  export default Whiteboard;
  