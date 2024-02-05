import { describe, expect, it, vi } from 'vitest';
import useAsset from '../hooks/asset/useAsset.ts'
import { api } from '../hooks/api/api.ts'

vi.mock('../hooks/api/api.ts'); // Mock the api module
describe('useAsset functions', () => {
    it('Test uploading an asset correctly', async () => {
        const assetData = {
            isbn: "978-3-16-148410-0",
            title: "The Great Gatsby",
            author: "F. Scott Fitzgerald",
            language: "eng",
            description: "A book about the American Dream, and the 1920s in America, and the story of Jay Gatsby. The book is a classic and is often read in high school, and is considered one of the best books of all time.",
        }

        vi.mocked(api.post).mockResolvedValue({
            status: 201,
            data: {
                isbn: "9780786838653",
                title: "Percy Jackson and the Olympians",
                author: "Rick Riordan",
                language: "eng",
                description: "This is a book. It certainly a book",
            },
            headers: {
                location: "http://example.com/api/assets/60",
            }
        });

        const res = await useAsset().uploadAsset(assetData.isbn, assetData.title, assetData.author, assetData.language, assetData.description);
        expect(res).toEqual(60);
    });

    it('Test uploading an asset with a failure', async () => {
        const assetData = {
            isbn: "9780786838653",
            title: "Percy Jackson and the Olympians",
        }

        vi.mocked(api.post).mockResolvedValue({
            status: 400
        });

        const res = await useAsset().uploadAsset(assetData.isbn, assetData.title, "", "", "");
        expect(res).toEqual(-1);
    })

    it('Test getting an asset correctly', async () => {
        const isbn = "9780786838653";
        vi.mocked(api.get).mockResolvedValue({
            status: 200,
            data: [{
                "author": "Rick Riordan",
                "id": 60,
                "isbn": "9780786838653",
                "language": "http://example.com/api/languages/eng",
                "selfUrl": "http://example.com/api/assets/60",
                "title": "Percy Jackson and the Olympians"
            }],
            headers: {}
        });

        const res = await useAsset().getAssetByIsbn(isbn);
        expect(res).toEqual({
            "author": "Rick Riordan",
            "id": 60,
            "isbn": "9780786838653",
            "language": "http://example.com/api/languages/eng",
            "selfUrl": "http://example.com/api/assets/60",
            "title": "Percy Jackson and the Olympians"
        });
    })


    it ('Test not getting any book with that isbn', async () => {
        const isbn = "9780786838653";
        vi.mocked(api.get).mockResolvedValue({
            status: 204,
            data: {},
            headers: {}
        })
        
        const res = await useAsset().getAssetByIsbn(isbn);
        expect(res).toEqual(null);
    })
})


