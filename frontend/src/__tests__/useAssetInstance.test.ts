//
// import useAssetInstance from "../hooks/assetInstance/useAssetInstance.ts";
// import {api} from "../hooks/api/api.ts"
//
// jest.mock('../hooks/api/api.ts');
//
// describe('useAssetInstance functions', () => {
//
//     afterEach(() => {
//         jest.clearAllMocks()
//     });
//
//     test('handleGetReservedDays should do ...', async () => {
//         // Mock
//         const mockApiResponse = {
//             data:[
//
//             ],
//             headers: {
//
//             }
//         };
//         api.get.mockResolvedValueOnce(mockApiResponse)
//
//         // Test
//         const result = await useAssetInstance().handleGetReservedDays(12)
//
//         // Assert
//         // Verify that the api.get function was called with the expected URL
//         expect(api.get).toHaveBeenCalledWith(expect.stringContaining('/lendings?assetInstanceId=12'));
//         // Verify that api.get was called the expected number of times
//         expect(api.get).toHaveBeenCalledTimes(1);
//
//
//     })
//
// })

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

// import { afterEach, beforeEach, describe, expect, it, vi } from 'vitest'
//
// const businessHours = [9, 17]
//
// function purchase() {
//     const currentHour = new Date().getHours()
//     const [open, close] = businessHours
//
//     if (currentHour > open && currentHour < close)
//         return { message: 'Success' }
//
//     return { message: 'Error' }
// }
//
// describe('purchasing flow', () => {
//     beforeEach(() => {
//         // tell vitest we use mocked time
//         vi.useFakeTimers()
//     })
//
//     afterEach(() => {
//         // restoring date after each test run
//         vi.useRealTimers()
//     })
//
//     it('allows purchases within business hours', () => {
//         // set hour within business hours
//         const date = new Date(2000, 1, 1, 13)
//         vi.setSystemTime(date)
//
//         // access Date.now() will result in the date set above
//         expect(purchase()).toEqual({ message: 'Success' })
//     })
//
//     it('disallows purchases outside of business hours', () => {
//         // set hour outside business hours
//         const date = new Date(2000, 1, 1, 19)
//         vi.setSystemTime(date)
//
//         // access Date.now() will result in the date set above
//         expect(purchase()).toEqual({ message: 'Error' })
//     })
// })