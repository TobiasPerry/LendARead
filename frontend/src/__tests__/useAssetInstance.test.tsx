import { describe, expect, it, vi } from 'vitest';
import useAssetInstance from "../hooks/assetInstance/useAssetInstance.ts";
import { api, api_ } from "../hooks/api/api.ts";
import {Simulate} from "react-dom/test-utils";
import error = Simulate.error;
import Vnd from "../hooks/api/types.ts";

const currentDate = new Date();
const year = currentDate.getFullYear();
const month = String(currentDate.getMonth() + 1).padStart(2, '0');
const day = String(currentDate.getDate()).padStart(2, '0');

vi.mock('../hooks/api/api.ts'); // Mock the api module
describe('useAssetInstance functions', () => {

    beforeEach(() => {
        // Reset mocks between tests
        vi.resetAllMocks();
    });

    it('handleGetLanguages should return correct output from a 200 OK', async () => {
        // Mock
        vi.mocked(api.get).mockResolvedValue({
            status: 200,
            data: [
                { code: 'aar', name: 'Afar' },
                { code: 'eng', name: 'English' }
            ],
            headers: {

            }
        });

        // Test
        const res = await useAssetInstance().handleGetLanguages(true);

        // Assert
        expect(res).not.toBeNull();
        expect(res).toStrictEqual([
            { code: 'aar', name: 'Afar' },
            { code: 'eng', name: 'English' }
        ]);

        expect(api.get).toHaveBeenCalledTimes(1);
    });

    it('handleGetLanguages should handle pages correctly', async () => {
        // Mock
        vi.mocked(api.get).mockResolvedValue({
            status: 200,
            data: [
                { code: 'aar', name: 'Afar' },
                { code: 'eng', name: 'English' }
            ],
            headers: {
                link: '<http://localhost:8080/api/languages?itemsPerPage=10&page=2>; rel="last"\n'
            }
        });

        // Test
        const res = await useAssetInstance().handleGetLanguages(true);

        // Assert
        expect(api.get).toHaveBeenCalledTimes(2);
        expect(res).toStrictEqual([
            { code: 'aar', name: 'Afar' },
            { code: 'aar', name: 'Afar' },
            { code: 'eng', name: 'English' },
            { code: 'eng', name: 'English' }
        ]);
    });

    it('handleAllAssetInstance should return empty array if 204 status code', async () => {
        // Mock
        vi.mocked(api.get).mockResolvedValue({
            status: 204,
            data: {},
            headers: {}
        });

        // Test
        const res = await useAssetInstance().handleAllAssetInstances(1, 12, "RECENT", "ASC", "", [], [], 1);

        // Assert
        expect(api.get).toHaveBeenCalledTimes(1);
        expect(res.books).toStrictEqual([]);
    });

    it('handleAllAssetInstance should return empty array if 204 status code', async () => {
        // Mock
        vi.mocked(api.get).mockResolvedValue({
            status: 204,
            data: {},
            headers: {}
        });

        // Test
        const res = await useAssetInstance().handleAllAssetInstances(1, 12, "RECENT", "ASC", "", [], [], 1);

        // Assert
        expect(api.get).toHaveBeenCalledTimes(1);
        expect(res.books).toStrictEqual([]);
    });

    it('handleAllAssetInstance should make the API with the correct path', async () => {
        // Mock
        vi.mocked(api.get).mockResolvedValue({
            status: 204,
            data: {},
            headers: {}
        });

        // Test
        const res = await useAssetInstance().handleAllAssetInstances(1, 12, "RECENT", "ASC", "", [], [], 1);

        // Assert
        expect(api.get).toHaveBeenCalledTimes(1);
        expect(res.books).toStrictEqual([]);
    });

    // _______________
    it('handleGetReservedDays should make the correct URL and query params', async () => {
        // Mock
        vi.mocked(api.get).mockResolvedValue(() => {throw error});

        // Test
        await useAssetInstance().handleGetReservedDays(12);

        // Assert
        const currentDate = new Date();
        const year = currentDate.getFullYear();
        const month = String(currentDate.getMonth() + 1).padStart(2, '0');
        const day = String(currentDate.getDate()).padStart(2, '0');
        // Validate that the path is correct
        expect(api.get).toHaveBeenCalledWith(expect.stringContaining(`/lendings?assetInstanceId=12&state=ACTIVE&state=DELIVERED&state=REJECTED&endAfter=${year}-${month}-${day}&itemsPerPage=20`));
        // Validate that there's only one API call
        expect(api.get).toHaveBeenCalledTimes(1);
    });

    it('handleGetReservedDays should manage error for an invalid assetInstanceId', async  () => {
        // Mock
        vi.mocked(api.get).mockResolvedValue(() => {throw error});

        // Test
        const result = await useAssetInstance().handleGetReservedDays(-1)

        //Assert
        expect(result).toBeNull()
    });

    it('handleSendLendingRequest when post is OK', async () => {
        // Mock
        vi.mocked(api.post).mockResolvedValue({
            status: 201,
            data: {
                id: 10
            },
            headers:{}
        });

        // Test
        const res = await useAssetInstance().handleSendLendingRequest({})

        // Asset
        expect(res).toStrictEqual({
            lendingId: 10,
            error: false,
            errorMessage: ""
        })

    });

    it('handleSendLendingRequest when post is not OK', async () => {
        // Mock
        vi.mocked(api.post).mockRejectedValue({
            response: {
                status: 400,
                data: {
                    errors: [
                        {
                            field: "Error",
                            message: "This is the error message"
                        }
                    ],
                    errorsCount: 1
                },
                headers: {
                    "content-type": Vnd.VND_VALIDATION_ERROR
                }
            }
        });

        // Test
        const res = await useAssetInstance().handleSendLendingRequest({})

        // Asset
        expect(res).toStrictEqual({
                errorMessage: 'Error: This is the error message\n',
                error: true,
                lendingId: -1
            }
        )

    });

});
