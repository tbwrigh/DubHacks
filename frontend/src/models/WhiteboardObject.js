class WhiteboardObject {
    constructor(id, whiteboardId, posX, posY, data) {
      this.id = id || null;  // Use null for new objects without IDs
      this.whiteboardId = whiteboardId;  // The ID of the whiteboard this object belongs to
      this.posX = posX || 0;  // Default X position
      this.posY = posY || 0;  // Default Y position
      this.data = data || {};  // Data of the object (could be JSON or string depending on the use case)
    }
  
    // Example method to update the position of the object
    updatePosition(newPosX, newPosY) {
      this.posX = newPosX;
      this.posY = newPosY;
    }
  
    // Example method to update the object data
    updateData(newData) {
      this.data = newData;
    }
    
    // Convert to a JSON object for API requests
    toJSON() {
      return {
        id: this.id,
        whiteboardId: this.whiteboardId,
        pos_x: this.posX,
        pos_y: this.posY,
        data: this.data,
      };
    }
  }
  
  export default WhiteboardObject;
  