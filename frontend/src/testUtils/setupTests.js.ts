export const setupTests = () => {
    beforeAll(() => {
        Object.defineProperty(window, 'localStorage', {
            value: new LocalStorageMock()
        });
        Object.defineProperty(window, 'sessionStorage', {
            value: new LocalStorageMock()
        });
    });
}

// Storage Mock
class LocalStorageMock {
    private store: {};
    constructor() {
        this.store = {};
    }

    clear() {
        this.store = {};
    }

    getItem(key) {
        return this.store[key] || null;
    }

    setItem(key, value) {
        this.store[key] = String(value);
    }

    removeItem(key) {
        delete this.store[key];
    }
}