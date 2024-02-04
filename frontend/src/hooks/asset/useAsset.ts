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

    return {
        uploadAsset
    }
}

export default useAsset;
