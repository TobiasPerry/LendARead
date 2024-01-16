import BookCardPlaceholder from "../../components/BookCardPlaceholder.tsx";
import ReviewCard from "../../components/reviews/ReviewCard.tsx";
import {useTranslation} from "react-i18next";

export default function ReviewBorrower () {

    const {t} = useTranslation()

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
                                title={t('reviews.borrower.user.title', {user: "USERNAME"})}
                                error_stars={t('reviews.borrower.user.error_stars')}
                                error_description={t('reviews.borrower.user.error_text')}
                                placeholder={t('reviews.borrower.user.placeholder')}
                            />
                            <ReviewCard
                                title={t('reviews.borrower.book.title', {user: "USERNAME"})}
                                error_stars={t('reviews.borrower.book.error_stars')}
                                error_description={t('reviews.borrower.book.error_text')}
                                placeholder={t('reviews.borrower.book.placeholder')}
                            />
                        </div>
                    </div>
                </div>
            </div>
        </>
    )
}