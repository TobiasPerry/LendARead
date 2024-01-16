import BookCardPlaceholder from "../../components/BookCardPlaceholder.tsx";

import ReviewCard from "../../components/reviews/ReviewCard.tsx";
import {useTranslation} from "react-i18next";

export default function ReviewLender () {
    const {t} = useTranslation();

    return(
        <>
            <div className="main-class"
                style={{display: 'flex', justifyContent: 'center', alignItems: 'center', flexDirection: 'column'}}>


                <div className="container-row-wrapped">
                    <div className="d-flex align-items-center justify-content-center">
                        <BookCardPlaceholder/>
                    </div>
                    <div className="">
                        <div style={{display: 'flex', flexDirection: 'column', justifyContent: 'space-between', maxWidth: '800px'}}>
                            <ReviewCard
                                title={t('reviews.lender.user.title', {user: "USERNAME"})}
                                error_stars={t('reviews.lender.user.error_stars')}
                                error_description={t('reviews.lender.user.error_text')}
                                placeholder={t('reviews.lender.user.placeholder')}
                            />
                        </div>
                    </div>

                </div>

            </div>
        </>
    )
}

//"reviews": {
//     "lender": {
//       "user": {
//         "title": "How do you rate {{user}} as a book borrower?",
//         "placeholder": "Add a short description",
//         "error_stars": "Add a rating",
//         "error_text": "The field is mandatory"
//       }
//     },
//     "borrower": {
//       "user": {
//         "title": "How do you rate {{user}} as a book lender? ",
//         "placeholder": "Add a short description",
//         "error_stars": "Add a rating",
//         "error_text": "The field is mandatory"
//       },
//       "book": {
//         "title": "How do you rate the book and its physical condition?",
//         "placeholder": "I loved/hated the book, the cover was damaged",
//         "error_stars": "Add a rating",
//         "error_text": "The field is mandatory"
//       }
//     }
//   }