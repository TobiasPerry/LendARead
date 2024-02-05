// __mocks__/api.ts

// Creating a mock instance of Axios
const mockAxiosInstance = {
    get: jest.fn(),
    post: jest.fn(),
    create: jest.fn(() => mockAxiosInstance), // Ensure create returns the same mock instance
};


export default mockAxiosInstance;
