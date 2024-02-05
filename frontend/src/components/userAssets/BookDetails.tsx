import { useState, useEffect } from 'react';
import { useTranslation } from 'react-i18next';

const BookDetails = ({data}) => {
    const { t } = useTranslation();

    return (
        <div style={{
            backgroundColor: '#f0f5f0',
            margin: '0 50px',
            borderRadius: '20px',
            padding: '20px',
            minWidth: '600px',
            width: '50%'
        }}>
            <div style={{display: 'flex', flexFlow: 'row', width: '100%', justifyContent: 'start'}}>
                <img src={data.imageUrl} alt="Book cover"
                     style={{
                         marginLeft: '0',
                         marginRight: '50px',
                         height: '500px',
                         width: '300px',
                         objectFit: 'cover',
                         borderRadius: '10px'
                     }}/>
                <div className="mx-2">

                    <h1 className="textOverflow" title="text">
                        {data.title}
                    </h1>


                    <h3 className="textOverflow" id="authorClick" data-author="data-author">
                        <span className="ml-2">
                                                {data.author}
                                            </span>
                    </h3>
                    <h6 id="physicalConditionClick" >
                        <i><u>
                            {data.condition}
                        </u></i>
                    </h6>

                    <h6 id="languageClick" data-language="data-language"
                        style={{color: '#7d7c7c'}}>
                        {t('language')}: <span > {data.language} </span>
                    </h6>

                    <h6 style={{color: '#7d7c7c'}}>
                        {t('isbn')}: {data.isbn} </h6>
                </div>
            </div>
        </div>

    );
};

export default BookDetails;
