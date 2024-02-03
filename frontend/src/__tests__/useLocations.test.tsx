import { describe, expect, it, vi } from 'vitest';
import axios, { AxiosInstance } from 'axios';
import { renderHook } from '@testing-library/react-hooks';
import useLocations from '../hooks/locations/useLocations';
import { AuthContext } from '../contexts/authContext.tsx'; // Replace with the correct path

// Create a mock user for AuthContext
const mockUser = ""

// Mock the axios module
vi.mock('axios', () => {
    const mockAxiosInstance: Partial<AxiosInstance> = {
        get: vi.fn().mockResolvedValue({ data: [] }), // Mock GET response
        post: vi.fn().mockResolvedValue({status: 400}), // Mock POST response
        patch: vi.fn().mockResolvedValue({}), // Mock PATCH response
        delete: vi.fn().mockResolvedValue({}), // Mock DELETE response
    };
    return {
        default: {
            create: () => mockAxiosInstance,
        },
        __esModule: true,
    };
});

describe('useLocations', () => {
    it('should perform actions correctly', async () => {
        // Provide a mock context
        const wrapper = ({ children }) => (
            <>
            {/*@ts-ignore*/}
            <AuthContext.Provider value={{ user: mockUser }}>
                {children}
            </AuthContext.Provider>
            </>
        );

        // Render the hook within the wrapper
        const { result, waitFor } = renderHook(() => useLocations(), { wrapper });

        // Trigger an action
        const res = await result.current.addLocation({ name: "", province: "", country: "", locality: "", zipcode: 0, selfUrl: "" });
        console.log('addLocation', res)

        // // Wait for state updates
        // await waitFor(() => {
        //     expect(result.current.locations).toContainEqual({ name: "", province: "", country: "", locality: "", zipcode: 0, selfUrl: "" });
        // });

        // Add more tests as necessary
    });
});
