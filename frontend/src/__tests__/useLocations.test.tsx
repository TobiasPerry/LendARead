import { describe, expect, it, vi } from 'vitest';
import axios, { AxiosInstance } from 'axios';
//import { renderHook } from '@testing-library/react-hooks';
import {renderHook, waitFor} from '@testing-library/react';
import useLocations from '../hooks/locations/useLocations';
import { AuthContext } from '../contexts/authContext.tsx'; // Replace with the correct path
import { I18nextProvider } from 'react-i18next';
import i18n from "../../src/i18n.js"
import {act} from "react-dom/test-utils";
import {api} from "../hooks/api/api.ts";

// Create a mock user for AuthContext
const mockUser = ""

// Mock the axios module
vi.mock('../hooks/api/api.ts')
const wrapper = ({ children }) => (
    <>
        {/*@ts-ignore*/}
        <AuthContext.Provider value={{ user: mockUser }}>
            <I18nextProvider i18n={i18n}>
                {children}
            </I18nextProvider>
        </AuthContext.Provider>
    </>
);

describe('useLocations', () => {
    it('should perform add empty location correctly', async () => {
        // Render the hook within the wrapper
        const { result } = renderHook(() => useLocations(), { wrapper });

        // Trigger an action
        await act(async () => {
            await result.current.addLocation({ name: "", province: "", country: "", locality: "", zipcode: 0, selfUrl: "" });
        });

        // Wait for state updates to be applied
        await waitFor(() => {
            return result.current.locations.length > 0;
        });

        // Assert the expected state
        expect(result.current.locations).toEqual([{ name: "", province: "", country: "", locality: "", zipcode: 0, id: -1 , selfUrl: "" }]);
    });


    it('should handle failure when editing a location', async () => {
        const mockLocation = { selfUrl: "/mock-url" };
        vi.mocked(api.patch).mockRejectedValue(new Error('Failed to edit location'));

        const { result} = renderHook(() => useLocations(), { wrapper });

        await act(async () => {
            await result.current.editLocation(mockLocation);
        });

        // Use waitFor to check for the expected condition
        expect(result.current.locations).toEqual([{ name: "", province: "", country: "", locality: "", zipcode: 0, id: -1, selfUrl: "" }]);

    });
});
