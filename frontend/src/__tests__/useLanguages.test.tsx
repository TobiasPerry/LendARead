import { describe, expect, it, vi } from 'vitest';
import { api } from '../hooks/api/api.ts'
import useLanguages from '../hooks/languages/useLanguages.ts'
import { AuthContext } from '../contexts/authContext.tsx'; // Replace with the correct path
import { I18nextProvider } from 'react-i18next';
import i18n from "../../src/i18n.js"
import {renderHook} from '@testing-library/react';

vi.mock('../hooks/api/api.ts'); // Mock the api module
const wrapper = ({ children }) => (
    <>
        {/*@ts-ignore*/}
        <AuthContext.Provider value={{ user: "" }}>
            <I18nextProvider i18n={i18n}>
                {children}
            </I18nextProvider>
        </AuthContext.Provider>
    </>
);

describe('useLanguages functions', () => {
    it('Test getting languages correctly', async () => {
        const { result } = renderHook(() => useLanguages(), { wrapper });
        vi.mocked(api.get).mockResolvedValue({
            status: 200,
            data: [
            {
                "name": "English",
                "code": "eng",
            },
            {
                "name": "Spanish",
                "code": "spa",
            }
            ],
            headers: {}
        });
        const res = await result.current.getAllLanguages();
        expect(res).toEqual([
            {
            "name": "English",
            "code": "eng",
            },
            {
            "name": "Spanish",
            "code": "spa",
            }
        ]);
    });

    it('Test getting languages correctly', async () => {
        const { result } = renderHook(() => useLanguages(), { wrapper });
        vi.mocked(api.get).mockResolvedValue({
            status: 504,
            headers: {}
        });
        const res = await result.current.getAllLanguages();
        expect(res).toEqual([]);
    });
});

