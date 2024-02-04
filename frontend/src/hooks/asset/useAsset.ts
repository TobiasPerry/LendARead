import { api } from '../api/api.ts';
import Vnd from '../api/types.ts'

const useAsset = () => {
    const uploadAsset = async (isbn: string, title: string, author: string, language: string, description: string): Promise<number> => { 
        const assetData = {
            "isbn": isbn,
            "title": title,
            "author": author,
            "language": language,
            "description": description
        };
        
        const response = await api.post('/assets', assetData, { headers: { 'Content-Type': Vnd.VND_ASSET } });
        if (response.status !== 201) {
            console.error("Error uploading asset: ", response);
            return -1;
        }
        return parseInt(response.headers.location.split('/').pop());
    }

    const getAssetByIsbn = async (isbn: string): Promise<any> => {
        const response = await api.get('/assets?itemsPerPage=2&page=1&isbn=' + isbn)
        if (response.status == 200 && response.data.length > 0) {
            const book = response.data[0]
            const langId = book.language.split('/').pop()
            const langResponse = await api.get('/languages/' + langId)
            book.lang = langResponse.data.code
            return book
        } 
        return null
    }
    return {
        uploadAsset,
        getAssetByIsbn
    }

    
}

export default useAsset;
