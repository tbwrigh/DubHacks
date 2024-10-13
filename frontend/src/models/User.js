class User {
    constructor(id, name, email) {
        this.id = id || null;  // Use null for new users without IDs
        this.name = name || '';  // Default to empty string
        this.email = email || '';  // Email of the user
    }

    toJSON() {
        return {
            id: this.id,
            name: this.name,
            email: this.email,
        };
    }
}

export default User;