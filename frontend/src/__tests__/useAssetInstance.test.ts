import { describe, expect, it, vi } from 'vitest';
import useAssetInstance from "../hooks/assetInstance/useAssetInstance.ts";
import { api, api_ } from "../hooks/api/api.ts";

import axios from 'axios';
import MockAdapter from 'axios-mock-adapter';

vi.mock('../hooks/api/api.ts'); // Mock the api module

const currentDate = new Date();
const year = currentDate.getFullYear();
const month = String(currentDate.getMonth() + 1).padStart(2, '0');
const day = String(currentDate.getDate()).padStart(2, '0');

describe('useAssetInstance functions', () => {

    let mockApi;
    beforeEach(() => {
        mockApi = new MockAdapter(api);
    });

    afterEach(() => {
        mockApi.reset();
        vi.clearAllMocks(); // Clear mocks after each test
    });

    it('handleGetReservedDays should make the correct URL and query params', async () => {
        // Mock
        const mockApiResponse = {
            data: [],
            headers: {},
        };
        // @ts-ignore
        vi.fn(api.get).mockResolvedValueOnce(mockApiResponse); // Mock api.get

        // Test
        const result = await useAssetInstance().handleGetReservedDays(12);

        // Assert
        const currentDate = new Date();
        const year = currentDate.getFullYear();
        const month = String(currentDate.getMonth() + 1).padStart(2, '0');
        const day = String(currentDate.getDate()).padStart(2, '0');
        // Validate that the path is correct
        expect(api.get).toHaveBeenCalledWith(expect.stringContaining(`/lendings?assetInstanceId=12&state=ACTIVE&state=DELIVERED&state=REJECTED&endAfter=${year}-${month}-${day}&itemsPerPage=20`));
        // Validate that there's only one API call
        expect(api.get).toHaveBeenCalledTimes(1);
        expect(api_.get).toHaveBeenCalledTimes(0);
    });

    it('handleGetReservedDays should return error for an invalid assetInstanceId', async  () => {
        // Mock
        const mockApiResponse = {
            data: [],
            headers: {},
        };
        // @ts-ignore
        vi.fn(api.get).mockResolvedValueOnce(mockApiResponse); // Mock api.get

        // Test
        const result = await useAssetInstance().handleGetReservedDays(-1)

        //Assert
        expect(result).toBeNull()
    });


    const mock = new MockAdapter(axios);
    mock.onGet(`/lendings?assetInstanceId=12&state=ACTIVE&state=DELIVERED&state=REJECTED&endAfter=${year}-${month}-${day}&itemsPerPage=20`)
        .reply(200, {
            data: [
                {assetInstance:"http://localhost:8080/api/assetInstances/2",borrowerUrl:"http://localhost:8080/api/users/2",devolutionDate:"2024-02-24",id:8,lendDate:"2024-02-19",lenderUrl:"http://localhost:8080/api/users/1",selfUrl:"http://localhost:8080/api/lendings/8",state:"ACTIVE"},
                {assetInstance:"http://localhost:8080/api/assetInstances/2",borrowerUrl:"http://localhost:8080/api/users/3",devolutionDate:"2024-02-15",id:8,lendDate:"2024-02-07",lenderUrl:"http://localhost:8080/api/users/1",selfUrl:"http://localhost:8080/api/lendings/9",state:"ACTIVE"}
            ]
        }, {
            link: '<http://localhost:8080/api/lendings?itemsPerPage=2&state=ACTIVE&state=DELIVERED&state=REJECTED&page=2&assetInstanceId=2&endAfter=2024-02-03>; rel="last"'
        })
    it('handleGetReservedDays should return as expected upon a correct assetInstanceId', async () => {
        // Mock
        // const mockApiResponse = {
        //     data: [
        //         {assetInstance:"http://localhost:8080/api/assetInstances/2",borrowerUrl:"http://localhost:8080/api/users/2",devolutionDate:"2024-02-24",id:8,lendDate:"2024-02-19",lenderUrl:"http://localhost:8080/api/users/1",selfUrl:"http://localhost:8080/api/lendings/8",state:"ACTIVE"},
        //         {assetInstance:"http://localhost:8080/api/assetInstances/2",borrowerUrl:"http://localhost:8080/api/users/3",devolutionDate:"2024-02-15",id:8,lendDate:"2024-02-07",lenderUrl:"http://localhost:8080/api/users/1",selfUrl:"http://localhost:8080/api/lendings/9",state:"ACTIVE"}
        //     ],
        //     headers: {
        //
        //     },
        // };
        //
        // vi.fn(api.get).mockResolvedValueOnce(mockAxiosResponse); // Mock api.get


        // Test
        //const result = await useAssetInstance().handleGetReservedDays(2)
        const response = await axios.get(`/lendings?assetInstanceId=12&state=ACTIVE&state=DELIVERED&state=REJECTED&endAfter=${year}-${month}-${day}&itemsPerPage=20`)

        //Assert
        //expect(result).not.toBeNull()
        console.log(response.data)
    });




    test('handleGetReservedDays should do ...', async () => {
        // Mock
        const assetInstanceId = 12;

        const mockApiResponse = {
            data: [
                {assetInstance:"http://localhost:8080/api/assetInstances/2",borrowerUrl:"http://localhost:8080/api/users/2",devolutionDate:"2024-02-24",id:8,lendDate:"2024-02-19",lenderUrl:"http://localhost:8080/api/users/1",selfUrl:"http://localhost:8080/api/lendings/8",state:"ACTIVE"},
                {assetInstance:"http://localhost:8080/api/assetInstances/2",borrowerUrl:"http://localhost:8080/api/users/3",devolutionDate:"2024-02-15",id:8,lendDate:"2024-02-07",lenderUrl:"http://localhost:8080/api/users/1",selfUrl:"http://localhost:8080/api/lendings/9",state:"ACTIVE"}
            ],
            headers: {
            }
        };

        mockApi.onGet(`/lendings?assetInstanceId=${assetInstanceId}&state=ACTIVE&state=DELIVERED&state=REJECTED&endAfter=${year}-${month}-${day}&itemsPerPage=20`).reply(200,
            [
                {assetInstance:"http://localhost:8080/api/assetInstances/2",borrowerUrl:"http://localhost:8080/api/users/2",devolutionDate:"2024-02-24",id:8,lendDate:"2024-02-19",lenderUrl:"http://localhost:8080/api/users/1",selfUrl:"http://localhost:8080/api/lendings/8",state:"ACTIVE"},
                {assetInstance:"http://localhost:8080/api/assetInstances/2",borrowerUrl:"http://localhost:8080/api/users/3",devolutionDate:"2024-02-15",id:8,lendDate:"2024-02-07",lenderUrl:"http://localhost:8080/api/users/1",selfUrl:"http://localhost:8080/api/lendings/9",state:"ACTIVE"}
            ],
            {
                link: `<http://localhost:8080/api/lendings?itemsPerPage=2&state=ACTIVE&state=DELIVERED&state=REJECTED&page=2&assetInstanceId=${assetInstanceId}&endAfter=${year}-${month}-${day}>; rel="last"`
            }
            );

        // Test
        //const result = await useAssetInstance().handleGetReservedDays(assetInstanceId);
        console.log("?: " + await api.get(`/lendings?assetInstanceId=${assetInstanceId}&state=ACTIVE&state=DELIVERED&state=REJECTED&endAfter=${year}-${month}-${day}&itemsPerPage=20`))
        // Assert
        // Verify that the api.get function was called with the expected URL
        expect(api.get).toHaveBeenCalledWith(expect.stringContaining(`/lendings?assetInstanceId=${assetInstanceId}&state=ACTIVE&state=DELIVERED&state=REJECTED&endAfter=${year}-${month}-${day}&itemsPerPage=20`));
        // Verify that api.get was called the expected number of times
        expect(api.get).toHaveBeenCalledTimes(1);

        // Your additional assertions based on the mocked data and test logic
    });
});

