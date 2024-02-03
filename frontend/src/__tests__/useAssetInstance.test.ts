import { describe, expect, it, vi } from 'vitest';
import useAssetInstance from "../hooks/assetInstance/useAssetInstance.ts";
import { api } from "../hooks/api/api.ts";

vi.mock('../hooks/api/api.ts'); // Mock the api module

describe('useAssetInstance functions', () => {
    afterEach(() => {
        vi.clearAllMocks(); // Clear mocks after each test
    });

    it('handleGetReservedDays should do ...', async () => {
        // Mock
        const mockApiResponse = {
            data: [],
            headers: {},
        };
        vi.fn(api.get).mockResolvedValueOnce(mockApiResponse); // Mock api.get

        // Test
        const result = await useAssetInstance().handleGetReservedDays(12);

        // Assert
        expect(api.get).toHaveBeenCalledWith(expect.stringContaining('/lendings?assetInstanceId=12'));
        expect(api.get).toHaveBeenCalledTimes(1);
    });
});

